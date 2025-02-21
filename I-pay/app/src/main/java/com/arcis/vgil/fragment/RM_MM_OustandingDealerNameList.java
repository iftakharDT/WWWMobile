package com.arcis.vgil.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.AmOutstandingCFormCreditResult;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.DealerNameListAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AmNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/14/2017.
 */

public class RM_MM_OustandingDealerNameList extends Fragment {
    private ListView am_listview;
    private Button get_data;
    private String ContactType,ContactID,amId="0";
    private ArrayList<HashMap<String,String>> arealist;
    private ArrayList<AmNameModel> amNameModelsList;
    private DealerNameListAdapter adapter;
    private TextView et_tittle;

    public RM_MM_OustandingDealerNameList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rm_mm_outstanding_new, container, false);
        am_listview=(ListView) rootView.findViewById(R.id.am_listview);
        et_tittle=(TextView)rootView. findViewById(R.id.et_tittle);
        et_tittle.setText("Dealers Name");


        arealist=new ArrayList<HashMap<String,String>>();
        amNameModelsList=new ArrayList<AmNameModel>();
        //get_data=(Button) findViewById(R.id.get);
        //get_data.setOnClickListener(this);
        SharedPreferences spref=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
        ContactID =spref.getString(Constants.USERID, "");
        if (ContactTypeAM.equalsIgnoreCase("14")) {
            ContactType="DLR";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }

        if (ContactTypeAM.equalsIgnoreCase("1")) {
            ContactType="AM";
            amId=spref.getString(Constants.USERID, "");
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }

        if (ContactTypeAM.equalsIgnoreCase("4")) {
            ContactType="RM";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }
        if (ContactTypeAM.equalsIgnoreCase("5")) {
            ContactType="MM";


            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }

        if (ContactTypeAM.equalsIgnoreCase("27")) {
            ContactType="CO";


            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }
        if (ContactTypeAM.equalsIgnoreCase("28")) {
            ContactType="HO";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }

        if (ContactTypeAM.equalsIgnoreCase("3")) {
            ContactType="SI";
            getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0",amId);
        }

        am_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent= new Intent(getActivity(),AmOutstandingCFormCreditResult.class);
                intent.putExtra("DealerID", amNameModelsList.get(position).getAmidd());
                intent.putExtra("selectedDealerName", amNameModelsList.get(position).getAmname());
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getGeographyByCode(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId, final String areaID, final String amId) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    arealist.clear();
                    arealist=new FetchingdataParser().getarealistparserCform(response.toString());
                    if(arealist.size()==0){
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
                    }else {

                        for (HashMap<String, String> entry : arealist)
                        {

                            String geoids=(String)entry.get(Constants.GeoID);
                            String geonames=(String)entry.get(Constants.GeoName);
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
                            adapter=new DealerNameListAdapter(getActivity(), R.layout.rm_mm_outsatanding_shell, amNameModelsList);
                            am_listview.setAdapter(adapter);
                        }

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
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
