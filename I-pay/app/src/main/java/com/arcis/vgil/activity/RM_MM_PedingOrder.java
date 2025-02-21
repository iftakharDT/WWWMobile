package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DealerNameListAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AmNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Jaim on 1/27/2017.
 */
public class RM_MM_PedingOrder extends BaseActivity{
    private AutoCompleteTextView am_filter,dealer_filter;
    private EditText et_search;
    private ListView am_listview;
    String ContactType,ContactID;
    private ArrayList<HashMap<String,String>> arealist;
    private ArrayList<AmNameModel> amNameModelsList;
    private DealerNameListAdapter adapter;
    @Override
    public void inti() {
        super.inti();
        setContentView(R.layout.rm_mm_outstanding_new);
        setCurrentContext(this);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setVisibility(View.VISIBLE);
        et_search.setHint("Dealer Name");
        am_listview= (ListView) findViewById(R.id.am_listview);
        SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
        ContactID =spref.getString(Constants.USERID, "");
        arealist=new ArrayList<HashMap<String,String>>();
        amNameModelsList=new ArrayList<AmNameModel>();



        if (ContactTypeAM.equalsIgnoreCase("4")) {
            ContactType="RM";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");
        }
        if (ContactTypeAM.equalsIgnoreCase("5")) {
            ContactType="MM";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }

        if (ContactTypeAM.equalsIgnoreCase("27")) {
            ContactType="CO";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }

        if (ContactTypeAM.equalsIgnoreCase("28")) {
            ContactType="HO";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }

        if (ContactTypeAM.equalsIgnoreCase("3")) {
            ContactType="SI";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }
        am_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent= new Intent(RM_MM_PedingOrder.this,RM_MM_PendingOrderDetails.class);
                intent.putExtra("DealerID", amNameModelsList.get(position).getAmidd());
                intent.putExtra("selectedDealerName", amNameModelsList.get(position).getAmname());
                startActivity(intent);
            }
        });
    }

    private void getGeographyByCode(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId, final String areaID, final String amId) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    arealist.clear();
                    arealist=new FetchingdataParser().getarealistparserCform(response.toString());
                    if(arealist.size()==0){
                        Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
                    }else {

                        for (HashMap<String, String> entry : arealist)
                        {

                            String geoids=(String)entry.get(Constants.GeoID);
                            String geonames=(String)entry.get(Constants.GeoName);
                            String jai=geoName;


                            if(geoids!=null && geonames!=null){
                                AmNameModel amNameModel= new AmNameModel();
                                if (!geoids.equalsIgnoreCase("0")) {
                                    if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
									/*if(!amNameModelsList.contains(geoids))*/
                                        amNameModel.setAmidd(geoids);
								/*	if(!amNameModelsList.contains(geonames)){*/
                                        amNameModel.setAmname(geonames);
                                        //	}
                                        amNameModelsList.add(amNameModel);
                                    }
                                }

                            }
                        }
                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
                            adapter=new DealerNameListAdapter(getCurrentContext(), R.layout.rm_mm_outsatanding_shell, amNameModelsList);
                            am_listview.setAdapter(adapter);

                            et_search.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub

                                    if(RM_MM_PedingOrder.this.adapter!=null)
                                        RM_MM_PedingOrder.this.adapter.getFilter().filter(arg0);
                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                              int arg3) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void afterTextChanged(Editable arg0) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        }

                    }

                }else if(response==null){
                    Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
			/*
			 * Function GetGeographyByLogin
			 * (ByVal ContactType As String, ByVal GeoType As String, ByVal GeoID As String,
			 * ByVal ContactID As String, ByVal StateID As String, ByVal CityID As String,
			 * ByVal UserID As String, ByVal Password As String) As String
		*/
            request.put("ContactType", contactType);
            request.put(Constants.GeoName,geoName);
            request.put("GeoID",geoId);
            request.put("ContactID", ContactID);
            request.put(Constants.stateID, stateId);
            request.put(Constants.cityID, cityId);
            request.put(Constants.areaID, areaID);
            request.put(Constants.AM_ID, amId);
            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetGeographyByLogin_NEW";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }

}
