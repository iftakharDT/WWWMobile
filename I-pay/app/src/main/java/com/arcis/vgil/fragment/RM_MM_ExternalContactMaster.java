package com.arcis.vgil.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.AmCustomerMaster_FitmentDetails;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.PartNoAllCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/10/2017.
 */

public class RM_MM_ExternalContactMaster extends Fragment implements View.OnClickListener{
    String cVid="0",sVid="0",areaNameVid="0",dlVid="0",cityVid="0";
    ArrayList<HashMap<String,Object>> arealist;
    private Spinner spnr_segment,spnr_oe,spnr_appication/*,spnr_partNo*/;
    private Spinner citySpinner,statespinner,zonespinner,countryspinner;
    private Spinner areaSpinner;
    private Spinner customerSpinner;
    private Spinner Typespinner;
    private SimpleAdapter TypesegmentAdapter;
    private Button btn_get,btn_startDate,btn_startTime;
    private int SegmentID=0,OEID=0,ApplicationID=0;
    EditText et_date_from,et_date_to,mannual_part_no;
    int ParntNo;
    private TextView tv_spinner_segment, tv_spinner_oe ,tv_spinner_application;
    String ContactType,ContactID;
    String ContactIDTwo="";
    ArrayList<String> countryname,countryid,statename,stateidd,zonename,zoneid,dealerName,dealerNameId,cityname,cityidd,areaName,areaNameId,customerName,customerNameId,contactType_Fit,contactTypeFit_Id;
    PartNoAllCustomAlertAdapter partNoAdapter;
    ArrayList<PartNoModel> partnoList,partnoListFilter;
    ArrayList<HashMap<String, String>> TypespinnerList;
    AlertDialog dialog;
    private EditText et_mobile,et_address,et_email,et_bday,et_abc,et_avg_call_peryear,et_last_call_date,et_cus_value_pm,et_cus_value_tm;

    public RM_MM_ExternalContactMaster() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rm_mm_customermaster_fitment, container, false);
        tv_spinner_segment=(TextView) rootView.findViewById(R.id.tv_spinner_segment);
        tv_spinner_oe=(TextView) rootView.findViewById(R.id.tv_spinner_oe);
        tv_spinner_application=(TextView)rootView. findViewById(R.id.tv_spinner_application);
        tv_spinner_segment.setVisibility(View.GONE);
        tv_spinner_oe.setVisibility(View.GONE);
        tv_spinner_application.setVisibility(View.GONE);
        et_mobile=(EditText) rootView.findViewById(R.id.et_mobile);
        et_address=(EditText)rootView. findViewById(R.id.et_address);
        et_email=(EditText)rootView. findViewById(R.id.et_email);
        et_bday=(EditText) rootView.findViewById(R.id.et_bday);
        et_abc=(EditText) rootView.findViewById(R.id.et_abc);
        et_avg_call_peryear=(EditText)rootView. findViewById(R.id.et_avg_call_peryear);
        et_last_call_date=(EditText) rootView.findViewById(R.id.et_last_call_date);
        et_cus_value_pm=(EditText)rootView. findViewById(R.id.et_cus_value_pm);
        et_cus_value_tm=(EditText)rootView. findViewById(R.id.et_cus_value_tm);


        SharedPreferences spref=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
        ContactID =spref.getString(Constants.USERID, "");
        arealist=new ArrayList<HashMap<String,Object>>();

        countryname=new ArrayList<String>();

        countryid  =new ArrayList<String>();


        zonename=new ArrayList<String>();

        zoneid=new ArrayList<String>();


        statename=new ArrayList<String>();

        stateidd=new ArrayList<String>();




        cityname=new ArrayList<String>();

        cityidd=new ArrayList<String>();

        cityname=new ArrayList<String>();

        cityidd=new ArrayList<String>();


        areaName=new ArrayList<String>();

        areaNameId=new ArrayList<String>();

        customerName=new ArrayList<String>();

        customerNameId=new ArrayList<String>();




        et_date_from=(EditText) rootView.findViewById(R.id.et_date_from);
        et_date_to=(EditText)rootView. findViewById(R.id.et_date_to);

        btn_get=(Button) rootView.findViewById(R.id.get_button);
        btn_get.setOnClickListener(this);

        btn_startDate = (Button)rootView.findViewById(R.id.date_from);

        btn_startDate.setOnClickListener(this);

        btn_startTime = (Button)rootView.findViewById(R.id.date_to);
        btn_startTime.setOnClickListener(this);


        countryspinner=(Spinner)rootView.findViewById(R.id.spinner_contry);

        countryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                cVid=countryid.get(countryspinner.getSelectedItemPosition());



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        zonespinner=(Spinner)rootView.findViewById(R.id.spinner_zone);
        zonespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    stateidd.clear();
                    statename.clear();
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
                    GetExternalContactTypeList();
                    if (TypespinnerList!=null) {
                        TypespinnerList.clear();
                        TypesegmentAdapter.notifyDataSetChanged();
                    }
                    setEditValue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        statespinner=(Spinner)rootView.findViewById(R.id.spinner_state);
        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    cityidd.clear();
                    cityname.clear();
                    areaNameId.clear();
                    areaName.clear();
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
                    GetExternalContactTypeList();
                    if (TypespinnerList!=null) {
                        TypespinnerList.clear();
                        TypesegmentAdapter.notifyDataSetChanged();
                    }
                    setEditValue();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        citySpinner=(Spinner)rootView.findViewById(R.id.cityspinner);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    areaName.clear();
                    areaNameId.clear();
                    cityVid=cityidd.get(citySpinner.getSelectedItemPosition());
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(citySpinner.getSelectedItemPosition()),"0","0");
                    GetExternalContactTypeList();
                    if (TypespinnerList!=null) {
                        TypespinnerList.clear();
                        TypesegmentAdapter.notifyDataSetChanged();
                    }
                    setEditValue();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        areaSpinner=(Spinner)rootView. findViewById(R.id.areaspinner);
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub

                if(position!=0){
                    areaNameVid=areaNameId.get(areaSpinner.getSelectedItemPosition());
                    GetExternalContactTypeList();
                    if (TypespinnerList!=null) {
                        TypespinnerList.clear();
                        TypesegmentAdapter.notifyDataSetChanged();
                    }
                    setEditValue();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        Typespinner=(Spinner)rootView. findViewById(R.id.typespinner);
        Typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub

                if(position!=0){
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                    dlVid=data.get(Constants.ID);
                    if (TypespinnerList!=null) {
                        TypespinnerList.clear();
                        TypesegmentAdapter.notifyDataSetChanged();
                    }
                    setEditValue();
                    GetAreaManagerContacts(cityVid,areaNameVid,dlVid);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        customerSpinner = (Spinner)rootView.findViewById(R.id.customer_spinner);
        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                    ContactIDTwo = data.get(Constants.ID);
                    //	Function ExternalContactMAster_MIS(ByVal CityID As String,
                    //ByVal AreaID As String, ByVal ContactType As String,
                    //ByVal ContactID As String, ByVal UserID As String, ByVal Password As String) As String
                    ExternalContactMAster_MIS(cityVid,areaNameVid,dlVid,ContactIDTwo);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });




        spnr_segment = (Spinner)rootView.findViewById(R.id.spinner_segment);
        spnr_segment.setVisibility(View.GONE);
        spnr_segment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                    String segmentid = data.get(Constants.ID);
                    getOETroughSegment(segmentid );

                    SegmentID= Integer.parseInt(segmentid);
                    getPartNo(SegmentID,0,0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spnr_oe = (Spinner)rootView.findViewById(R.id.spinner_oe);
        spnr_oe.setVisibility(View.GONE);
        spnr_oe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                    String segmentid = data.get(Constants.ID);
                    getApplications(segmentid);
                    OEID= Integer.parseInt(segmentid);
                    getPartNo(SegmentID,OEID,0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        spnr_appication = (Spinner)rootView.findViewById(R.id.spinner_application);
        spnr_appication.setVisibility(View.GONE);
        spnr_appication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                    String applicationid = data.get(Constants.ID);
                    ApplicationID= Integer.parseInt(applicationid);
                    getPartNo(SegmentID,OEID,ApplicationID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        getSegment();
        getPartNo(0,0,0);
        mannual_part_no=(EditText) rootView.findViewById(R.id.mannual_part_no);
        mannual_part_no.setOnClickListener(this);


        if (ContactTypeAM.equalsIgnoreCase("4")) {
            ContactType="RM";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }
        if (ContactTypeAM.equalsIgnoreCase("5")) {
            ContactType="MM";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }

        if (ContactTypeAM.equalsIgnoreCase("27")) {
            ContactType="CO";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }
        if (ContactTypeAM.equalsIgnoreCase("28")) {
            ContactType="HO";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }

        if (ContactTypeAM.equalsIgnoreCase("3")) {
            ContactType="SI";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");

        }
        GetExternalContactTypeList();





        return rootView;
    }

    protected void ExternalContactMAster_MIS(String cityVid,
                                             String areaNameVid, String ContactType, String contactID) {
        // TODO Auto-generated method stub




        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getExternalContactMAster_MIS(response.toString());
                    if(itemQuantityValueList!=null ){
                        if (itemQuantityValueList.size()!=0) {
                            et_mobile.setText(itemQuantityValueList.get(0).get(Constants.MOBILE_NUMBER));
                            et_address.setText(itemQuantityValueList.get(0).get(Constants.streetaddress));
                            et_email.setText(itemQuantityValueList.get(0).get(Constants.emailid));
                            et_bday.setText(itemQuantityValueList.get(0).get(Constants.dob));
                            et_abc.setText(itemQuantityValueList.get(0).get(Constants.TYPE));
                            et_avg_call_peryear.setText(itemQuantityValueList.get(0).get(Constants.AVG_Calls_Year));
                            et_last_call_date.setText(itemQuantityValueList.get(0).get(Constants.LastCallDate));
                            et_cus_value_pm.setText(itemQuantityValueList.get(0).get(Constants.CustomerValueAvgPM));
                            et_cus_value_tm.setText(itemQuantityValueList.get(0).get(Constants.CustomerValueTM));

                        }


                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);



        try {
            //	Function ExternalContactMaster_MIS(ByVal CityID As String,
            //ByVal AreaID As String, ByVal ContactType As String,
            //ByVal ContactID As String, ByVal UserID As String, ByVal Password As String) As String

            request.put(Constants.cityID, cityVid);
            request.put(Constants.areaID, areaNameVid);
            request.put("ContactType", ContactType);
            request.put("ContactID", contactID);
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="ExternalContactMaster_MIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();




        // TODO Auto-generated method stub


        // TODO Auto-generated method stub




    }
    private void GetAreaManagerContacts(final String cityId, final String areaId, final String ContactID) {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    TypespinnerList = FetchingdataParser.getAreaManagerContacts(response.toString());
                    if(TypespinnerList!=null ){
                        if (TypespinnerList.size()!=0) {
                            TypesegmentAdapter = new SimpleAdapter(getActivity(), TypespinnerList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
                            TypesegmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            customerSpinner	.setAdapter(TypesegmentAdapter);
                        }


                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);



        try {
            //	Function GetInternalContactCustomer_Higher(ByVal LoggedInUserType As String,
            //ByVal CountryID As String, ByVal ZoneID As String, ByVal StateID As String,
            //ByVal CityID As String, ByVal AreaID As String, ByVal ContactType As String,
            //ByVal UserID As String, ByVal Password As String) As String
            request.put("LoggedInUserType", ContactType);
            request.put("ContactID", passworddetails.getString(Constants.USERID,""));
            request.put("CountryID",cVid);
            request.put("ZoneID",zoneid.get(zonespinner.getSelectedItemPosition()));
            request.put("StateID", stateidd.get(statespinner.getSelectedItemPosition()));
            request.put("CityID", cityidd.get(citySpinner.getSelectedItemPosition()));
            request.put("AreaID", areaId);
            request.put("ContactType", ContactID);
            request.put(Constants.username, passworddetails.getString(Constants.USERID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetInternalContactCustomer_Higher";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();




        // TODO Auto-generated method stub


        // TODO Auto-generated method stub

    }
    private void GetExternalContactTypeList() {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> customerList = FetchingdataParser.getExternalContactTypeList(response.toString());
                    if(customerList!=null ){
                        if (customerList.size()!=0) {
                            SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), customerList, android.R.layout.simple_spinner_item, new String[]{Constants.TYPE}, new int[]{android.R.id.text1});
                            segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            Typespinner.setAdapter(segmentAdapter);
                        }


                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
            //Function GetExternalContactTypeList(ByVal UserID As String, ByVal Password As String) As String
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetExternalContactTypeList";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();




        // TODO Auto-generated method stub

    }
    private void GetGeographyByLogin(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId, final String areaID, final String amId) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    arealist.clear();
					/**/
                    arealist=new FetchingdataParser().getarealistparser(response.toString());
                    if(arealist.size()==0){
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
                    }else {

                        for (HashMap<String, Object> entry : arealist)
                        {
                            String geoids=(String)entry.get(Constants.GeoID);
                            String geonames=(String)entry.get(Constants.GeoName);



                            if(geoids!=null && geonames!=null){

                                if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
                                    if(!countryid.contains(geoids))
                                        countryid.add(geoids);
                                    if(!countryname.contains(geonames))
                                        countryname.add(geonames);
                                }

                                if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
                                    if(!zoneid.contains(geoids))
                                        zoneid.add(geoids);
                                    if(!zonename.contains(geonames))
                                        zonename.add(geonames);
                                }

                                else if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){

                                    if(!stateidd.contains(geoids))
                                        stateidd.add(geoids);
                                    if(!statename.contains(geonames))
                                        statename.add(geonames);

                                }

                                else if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){

                                    if(!cityidd.contains(geoids))
                                        cityidd.add(geoids);
                                    if(!cityname.contains(geonames))
                                        cityname.add(geonames);
                                }
                                else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
                                    if(!areaNameId.contains(geoids))
                                        areaNameId.add(geoids);
                                    if(!areaName.contains(geonames))
                                        areaName.add(geonames);
                                }
                            }

                        }


                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
                            ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,countryname);
                            countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            countryspinner.setAdapter(countryAdapter);
                            cVid=countryid.get(countryspinner.getSelectedItemPosition());
                        }
                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
                            ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,zonename);
                            zoneAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            zonespinner.setAdapter(zoneAdapter);
                        }
                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,statename);
                            stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            statespinner.setAdapter(stateAdapter);
                        }

                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,cityname);
                            cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            citySpinner.setAdapter(cityAdapter);
                        }
                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
                            ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,areaName);
                            AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            areaSpinner.setAdapter(AMAdapter);
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
    protected void getOETroughSegment(String segmentid) {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> oeList = FetchingdataParser.getOE(response.toString());
                    if(oeList!=null ){
                        if (oeList.size()!=0) {
                            SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), oeList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
                            segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            spnr_oe.setAdapter(segmentAdapter);
                        }


                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);



        try {
            request.put("RequestType","OE");
            request.put("ParentID",segmentid);
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetProductSegmentationDetail_MIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();



    }

    protected void getApplications(String segmentID) {
        // TODO Auto-generated method stub



        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> applicationList = FetchingdataParser.getApplications(response.toString());
                    if(applicationList!=null){
                        SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), applicationList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
                        segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        spnr_appication.setAdapter(segmentAdapter);

                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
            request.put("RequestType","APPLICATION");
            request.put("ParentID",segmentID);
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetProductSegmentationDetail_MIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }
// Public Function GetAllProduct_MIS(SegmentID As Integer, OEID As Integer, ApplicationID As Integer, ByVal UserID As String, ByVal Password As String)

    private void getPartNo(int SegmentID,int OEID,int ApplicationID) {
        // TODO Auto-generated method stub



        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    partnoList = FetchingdataParser.getPartNo(response.toString());
                    if(partnoList!=null){
                        if (partnoList.size()!=0) {
                            partnoListFilter = FetchingdataParser.getPartNo(response.toString());
                        }
                        else {
                            Toast.makeText(getActivity(),"No Data", Toast.LENGTH_SHORT).show();
                        }
                        //SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
                        //segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        //spnr_partNo.setAdapter(segmentAdapter);

                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
            request.put("SegmentID",SegmentID);
            request.put("OEID",OEID);
            request.put("ApplicationID",ApplicationID);
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetAllProduct_MIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

    private void getSegment() {
        // TODO Auto-generated method stub



        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getSegment(response.toString());
                    if(segmentList!=null){
                        SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), segmentList, android.R.layout.simple_spinner_item, new String[]{Constants.SEGMENTNAME}, new int[]{android.R.id.text1});
                        segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        spnr_segment.setAdapter(segmentAdapter);

                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
            request.put("RequestType","SEGMENT");
            request.put("ParentID","0");
            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetProductSegmentationDetail_MIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub


        Calendar cal = Calendar.getInstance();


        switch (v.getId()) {
            case R.id.get_button:
//			Function ExternalContactMAsterCouponValue_MIS(ByVal CityID As String,
                //ByVal AreaID As String, ByVal ContactType As String, ByVal ContactID As String,
                //	ByVal UserID As String, ByVal Password As String) As String

                Intent intent=new Intent(getActivity(),AmCustomerMaster_FitmentDetails.class);
                intent.putExtra("CityID",cityVid );
                intent.putExtra("AreaID", areaNameVid);
                intent.putExtra("ContactType", dlVid);
                intent.putExtra("ContactID", ContactIDTwo);
                startActivity(intent);

                break;

            case R.id.date_from:
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub

                        et_date_from.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);

                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePicker.setTitle("Please select Date");
                datePicker.show();
                break;

            case R.id.date_to:


                DatePickerDialog datePicker_to = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub

                        et_date_to.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);

                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePicker_to.setTitle("Please select Date");
                datePicker_to.show();

                break;


            case R.id.mannual_part_no:

                if (partnoListFilter!=null) {
                    getPartNo(0,0,0);
                    getSerachPartDetails();
                }


                break;
            default:
                break;
        }
    }


    private void getSerachPartDetails() {
        // TODO Auto-generated method stub

        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);

        EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
        ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
        AlertDialog.Builder  builder= new AlertDialog.Builder(getActivity());
        builder.setView(view);
        partNoAdapter=new PartNoAllCustomAlertAdapter(getActivity(), R.layout.am_manual_part_no_shell, partnoList);
        list_part_no.setAdapter(partNoAdapter);
        list_part_no.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long arg3) {
                // TODO Auto-generated method stub

                ParntNo = Integer.parseInt(partnoList.get(position).getProductID());

                Toast.makeText(getActivity(),partnoList.get(position).getCode(), Toast.LENGTH_LONG).show();
                mannual_part_no.setText(partnoList.get(position).getCode());

                dialog.dismiss();

            }

        });


        searchpartNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                if(RM_MM_ExternalContactMaster.this.partNoAdapter!=null)
                    RM_MM_ExternalContactMaster.this.partNoAdapter.getFilter().filter(arg0);
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


        builder.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.show();




    }


    protected void setEditValue() {
        // TODO Auto-generated method stub
        et_mobile.setText("");
        et_email.setText("");
        et_bday.setText("");
        et_abc.setText("");
        et_avg_call_peryear.setText("");
        et_last_call_date.setText("");
        et_cus_value_pm.setText("");
        et_cus_value_tm.setText("");
        et_address.setText("");
    }


}
