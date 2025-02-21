package com.arcis.vgil.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.activity.Util;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.NotificationListModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/30/2017.
 */
public class Utils {


 /*   public static void GCMRegistration(final Context context) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(context, ProgressDialog.STYLE_SPINNER, "Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {
                    Log.i("FCM Resistered", response.toString());
                } else if (response == null) {
                    Log.i("FCM not Resistered", response.toString());
                    // Toast.makeText(context,context.getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();


                }
            }
        });
        //Function UpdateFCMID(ByVal ContactTypeID As String, ByVal FCMID As String,
        // ByVal UserID As String, ByVal Password As String) As String
        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences spref = context.getSharedPreferences("PASSWORD", MODE_PRIVATE);

        try {

            request.put("ContactTypeID", spref.getString(Constants.CONTACTTYPEID, ""));
            request.put("FCMID", spref.getString(Constants.GCMID, ""));
            request.put(Constants.username, spref.getString("username", ""));
            request.put(Constants.password, spref.getString("password", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress = context.getResources().getString(R.string.ipaddress);
        String webService = context.getResources().getString(R.string.Webservice_mis_android);
        String nameSpace = context.getResources().getString(R.string.nameSpace);
        String methodName = "UpdateFCMID";
        String soapcomponent = context.getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }*/

    public static void openApplication(Context context, String packageN) {
        try {
            Intent i = context.getPackageManager().getLaunchIntentForPackage(packageN);
            if (i == null) {
                Uri uri = Uri.parse("market://details?id=" + "com.way2webworld.spicer");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(goToMarket);
            } else {
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(i);
            }
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "com.way2webworld.spicer")));
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void CountNotification(final Context context) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
                context, ProgressDialog.STYLE_SPINNER,
                "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {

                if (response == null) {
                    Toast.makeText(context,
                            context.getString(R.string.error4),
                            Toast.LENGTH_SHORT).show();
                    SharedPreferences passworddetails = Util.getSharedPreferences(context, Constants.PREF_NAME);
                    passworddetails.edit().putString(Constants.NotificationCount, "0").commit();


                } else {
                    ArrayList<NotificationListModel> amNotificationList = FetchingdataParser.getNotificationResponce(response.toString());
                    if (amNotificationList != null) {
                        if (amNotificationList.size() != 0) {


                            SharedPreferences passworddetails = Util.getSharedPreferences(context, Constants.PREF_NAME);
                            passworddetails.edit().putString(Constants.NotificationCount, amNotificationList.get(0).getRowCount()).commit();


                        }


                    }

                }


            }
        });
        //  Function GetNotificationFormData(ByVal ContactID As String,
        // ByVal ContactTypeID As String,
        // ByVal UserID As String, ByVal Password As String) As String


        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = context.getSharedPreferences("PASSWORD", MODE_PRIVATE);

        try {
            request.put("ContactID", passworddetails.getString(Constants.USERID, ""));
            request.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put(Constants.username, passworddetails.getString(Constants.ID, ""));
            request.put(Constants.password, passworddetails.getString("password", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress = context.getString(R.string.ipaddress);
        String webService = context.getString(R.string.Webservice_mis_android);
        String nameSpace = context.getString(R.string.nameSpace);
        String methodName = "GetNotificationFormData";
        String soapcomponent = context.getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }

}
