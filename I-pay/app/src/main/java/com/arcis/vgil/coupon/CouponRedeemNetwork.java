package com.arcis.vgil.coupon;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CouponRedeemNetwork extends AsyncTask<Void, String, Object> {


    private String ipAddress;
    private String webService;
    private String nameSpace;
    private String methodName;
    private Context context;
    private ProgressDialog progressDialog;
    private SoapObject request;
    private CouponRedeemCallBack callBack;
    String soapcomponent;
    /**
     * Set default progress Bar properties. Internal function called from
     * setConfig
     *
     * @param progressBarStyle
     *            : Progress Dialog style (spinner or horizontal)
     * @param message
     *            : Message to be displayed on progressBar
     */
    protected void setProgresDialogProperties(int progressBarStyle, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(progressBarStyle);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
    }


    public CouponRedeemNetwork(Context context, int progressBarStyle, String message, CouponRedeemCallBack callBack) {
        this.callBack = callBack;
        this.context = context;
        setProgresDialogProperties(progressBarStyle, message);
    }

    /**
     * Used for setting up the location and config of remote location/webservice
     *
     * @param ipAddress
     *            : IP of webservice
     * @param webService
     *            : Name of webservice
     * @param nameSpace
     *            : NameSpace of webService
     * @param methodName
     *            :Name of function of the particular webservice
     */
    public void setConfig(String ipAddress, String webService, String nameSpace, String methodName, String soapcomponent) {
        this.ipAddress = ipAddress;
        this.webService = webService;
        this.nameSpace = nameSpace;
        this.methodName = methodName;
        this.soapcomponent = soapcomponent ;
    }

    /**
     * Set parameter in SoapObject
     *
     * @param requestParamater
     *            : Map of request
     */
    @SuppressWarnings("rawtypes")
    public void sendData(HashMap<String, Object> requestParamater) {
        request = new SoapObject(nameSpace, methodName);
        Iterator iterator = requestParamater.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            request.addProperty(entry.getKey().toString(), entry.getValue());
            Log.i(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }


    @Override
    protected Object doInBackground(Void... params) {
        Object result = null;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        new MarshalBase64().register(envelope);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        envelope.implicitTypes = false;
        HttpTransportSE androidHttpTransport = new HttpTransportSE(ipAddress + webService);
        System.setProperty("http.keepAlive", "true");
        try {
            androidHttpTransport.call(nameSpace + soapcomponent + methodName, envelope);
            result = envelope.getResponse();
            if (result != null) {
                Log.d("GetData result ", result.toString());
                result = result.toString();
            } else {
                System.out.println("GetDataFromNetwork.doInBackground()" + result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        callBack.processResponse(result);

        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
