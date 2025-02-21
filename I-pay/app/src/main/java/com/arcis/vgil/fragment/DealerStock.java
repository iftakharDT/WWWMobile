package com.arcis.vgil.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.PartNoWithDetailsStock;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.model.RM_MM_Stock_Model;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/14/2017.
 */

public class DealerStock extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    /*
	 * Geographical Search
	*/
    private Spinner countryspinner;
    private Spinner zonespinner;
    private Spinner statespinner;
    private Spinner districtspinner;
    private Spinner cityspinner;
    private Spinner areaspinner;
    AlertDialog dialog;
    /*
     * Stock report
    */
    private Spinner spnr_product,spnr_segment,spnr_oe,spnr_appication,spnr_partNo,spn_cfa_dealer_mg_spinner;
    private Spinner spnr_Am;
    private EditText part_no_edit;
    private TextView aplication,oe,segment,tv_partno;
    ArrayList<HashMap<String,Object>> arealist;
    private RadioGroup mRGroupTwo;
    private Button btn_get;
    private int SegmentID=0,OEID=0,ApplicationID=0;
    int ParntNo;
    private String stockLocation;
    String locationID;
    boolean isValidateGet;
    String ContactType,ContactID;
    PartNoWithDetailsStock partNoAdapter;
    ArrayList<PartNoModel> partnoListFilter;

    private TextView header,tv_am,textView19,textView9,textView6,tv_city,textView8;
    private RadioButton rb_am_cfa ,rb_am_mechanic;


    /*
     * Listing for all spinner
        */
    ArrayList<String> amname,amidd, countryname,countryid,areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,dealerName,dealerNameId;


    public DealerStock() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.am_stock_report, container, false);
        SharedPreferences spref=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
        ContactID =spref.getString(Constants.USERID, "");

        arealist=new ArrayList<HashMap<String,Object>>();
        aplication=(TextView)rootView. findViewById(R.id.aplication);
        oe=(TextView) rootView. findViewById(R.id.oe);
        segment=(TextView) rootView. findViewById(R.id.segment);
        aplication.setVisibility(View.GONE);
        oe.setVisibility(View.GONE);
        segment.setVisibility(View.GONE);
        tv_partno=(TextView)rootView.  findViewById(R.id.tv_partno);
        tv_partno.setVisibility(View.GONE);

                 header=(TextView) rootView. findViewById(R.id.header);
                tv_am=(TextView) rootView. findViewById(R.id.tv_am);
                textView19=(TextView) rootView. findViewById(R.id.textView19);
                textView9=(TextView) rootView. findViewById(R.id.textView9);
                textView6=(TextView) rootView. findViewById(R.id.textView6);
                tv_city=(TextView) rootView. findViewById(R.id.tv_city);
                textView8=(TextView) rootView. findViewById(R.id.textView8);
                        header.setVisibility(View.GONE);
                        tv_am.setVisibility(View.GONE);
                        textView19.setVisibility(View.GONE);
                        textView9.setVisibility(View.GONE);
                        textView6.setVisibility(View.GONE);
                        tv_city.setVisibility(View.GONE);
                        textView8.setVisibility(View.GONE);

        rb_am_cfa=(RadioButton) rootView. findViewById(R.id.rb_am_cfa);
        rb_am_mechanic=(RadioButton) rootView. findViewById(R.id.rb_am_mechanic);
        rb_am_cfa.setVisibility(View.GONE);
        rb_am_mechanic.setVisibility(View.GONE);



        mRGroupTwo=(RadioGroup) rootView. findViewById(R.id.rg_am2_trend);
        mRGroupTwo.setOnCheckedChangeListener(this);

        amname=new ArrayList<String>();
        amidd=new ArrayList<String>();
        countryname=new ArrayList<String>();
        countryid  =new ArrayList<String>();
        zonename=new ArrayList<String>();
        zoneid=new ArrayList<String>();
        statename=new ArrayList<String>();
        stateidd=new ArrayList<String>();
        districtname=new ArrayList<String>();
        districtid=new ArrayList<String>();
        cityname=new ArrayList<String>();
        cityidd=new ArrayList<String>();
        areaname=new ArrayList<String>();
        areaidd=new ArrayList<String>();
        dealerName=new ArrayList<String>();
        dealerNameId=new ArrayList<String>();

        part_no_edit=(EditText) rootView. findViewById(R.id.part_no);
        part_no_edit.setVisibility(View.GONE);

        btn_get = (Button)rootView. findViewById(R.id.get);
        btn_get.setOnClickListener(this);

        View trendFilterlayout = rootView. findViewById(R.id.trendfilter);

        spnr_Am=(Spinner) rootView. findViewById(R.id.dealerspinner);
        spnr_Am.setVisibility(View.GONE);
        spnr_segment = (Spinner)trendFilterlayout.findViewById(R.id.segmentspinner);
        spnr_segment.setVisibility(View.GONE);
        spnr_oe = (Spinner)trendFilterlayout.findViewById(R.id.oe_spinner);
        spnr_oe.setVisibility(View.GONE);
        spnr_appication = (Spinner)trendFilterlayout.findViewById(R.id.applicationspinner);
        spnr_appication.setVisibility(View.GONE);
        countryspinner=(Spinner)rootView. findViewById(R.id.countryspinner);
        countryspinner.setVisibility(View.GONE);
        zonespinner=(Spinner)rootView. findViewById(R.id.zonespinner);
        zonespinner.setVisibility(View.GONE);
        statespinner=(Spinner)rootView. findViewById(R.id.statespinner);
        statespinner.setVisibility(View.GONE);
        cityspinner=(Spinner)rootView. findViewById(R.id.cityspinner);
        cityspinner.setVisibility(View.GONE);
        areaspinner=(Spinner)rootView. findViewById(R.id.areaspinner);
        areaspinner.setVisibility(View.GONE);


        //cfa_dealer_mg_spinner

        spn_cfa_dealer_mg_spinner=(Spinner)rootView. findViewById(R.id.cfa_dealer_mg_spinner);
        spn_cfa_dealer_mg_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){

                    if (!stockLocation.equals("D")) {
                        HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                        locationID = data.get(Constants.ID);

                    }
                    else {
                        locationID=dealerNameId.get(spn_cfa_dealer_mg_spinner.getSelectedItemPosition());

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        if (ContactTypeAM.equalsIgnoreCase("1")) {
            ContactType="AM";
            getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0");
            getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0");
            getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0");
            getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
            getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0");

            getGeographyByCode(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0");
        } if (ContactTypeAM.equalsIgnoreCase("14")) {
            ContactType="DLR";

        }
        getSegment();
        //getPartNo(0,0,0);

        part_no_edit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.get:
                if(isValidateGet()){
                    getItemQuantityValue(SegmentID,OEID,ApplicationID);
                }



                break;



        }

    }
    private boolean isValidateGet() {
        // TODO Auto-generated method stub
        String errMgs = getActivity().getString(R.string.error3);
        boolean flag = true;
        if (stockLocation !=null && !stockLocation.isEmpty()) {
            flag = true;
        }
        else {


            flag=false;
            errMgs=errMgs.concat(getActivity().getString(R.string.please_select_cfa_dealer_mg));

        }

        if (locationID !=null && !locationID.isEmpty()) {
            flag = true;
        }
        else {
            errMgs=errMgs.concat(getActivity().getString(R.string.cfa_dealer_mg_spinner));
            flag=false;
        }

        if (!flag) {
            Toast.makeText(getActivity(), errMgs, Toast.LENGTH_LONG).show();
        }

        return flag;
    }

    private void getItemQuantityValue(int SegmentID,int OEID,int ApplicationID) {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<RM_MM_Stock_Model> partnoList = FetchingdataParser.getRM_MM_Stock_Report(response.toString());
                    if(partnoList!=null ){
                        if (partnoList.size()!=0) {


                            // TODO Auto-generated method stub

                            LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view  = inflator.inflate(R.layout.rm_mm_stock_result_search, null);

                            EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
                            ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
                            AlertDialog.Builder  builder= new AlertDialog.Builder(getActivity());
                            builder.setView(view);
                            partNoAdapter=new PartNoWithDetailsStock(getActivity(), R.layout.cforn_invoice_detail_cell, partnoList);
                            list_part_no.setAdapter(partNoAdapter);

                            searchpartNo.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub

                                    if(DealerStock.this.partNoAdapter!=null)
                                        DealerStock.this.partNoAdapter.getFilter().filter(arg0);
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
            request.put("LocationType",stockLocation);
            request.put("LocationContactID",locationID);

            String ContactType=passworddetails.getString(Constants.CONTACTTYPEID, "");
            if (ContactType.equalsIgnoreCase("1")) {
                ContactType="AM";
                request.put("ContactType",ContactType);

            }

            request.put("ContactID",ContactID);
            request.put("CountryID",countryid.get(countryspinner.getSelectedItemPosition()));
            request.put("ZoneID",zoneid.get(zonespinner.getSelectedItemPosition()));
            request.put("StateID",stateidd.get(statespinner.getSelectedItemPosition()));
            request.put("CityID",cityidd.get(cityspinner.getSelectedItemPosition()));

            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetStockReport_MIS";
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        if (checkedId==R.id.rb_am_cfa) {
            getCFA();
            stockLocation="C";
            if (partnoListFilter!=null) {
                partnoListFilter.clear();
            }

        }

        else if (checkedId==R.id.rb_am_dealer) {

            //   getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0");
            getDealer(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0");
            stockLocation="D";
            if (partnoListFilter!=null) {
                partnoListFilter.clear();
            }

        }
        else if (checkedId==R.id.rb_am_mechanic) {
            dealerName.clear();
            dealerNameId.clear();
            stockLocation="M";
            getMechanic();
            if (partnoListFilter!=null) {
                partnoListFilter.clear();
            }
        }
    }

    private void getMechanic() {
        // TODO Auto-generated method stub



        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getCFAObject(response.toString());
                    if(segmentList!=null){
                        SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), segmentList, android.R.layout.simple_spinner_item, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
                        segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        spn_cfa_dealer_mg_spinner.setAdapter(segmentAdapter);

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

        //GetAllMG(ByVal AMID As String, ByVal UserID As String, ByVal Password As String) As String

        try {
            request.put("AMID",passworddetails.getString("username",""));
            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetAllMG";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }
    //
    private void getDealer(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    arealist.clear();
                    arealist=new FetchingdataParser().getarealistparser(response.toString());
                    if(arealist.size()==0){
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
                    }else {

                        for (HashMap<String, Object> entry : arealist)
                        {

                            String geoids=(String)entry.get(Constants.GeoID);
                            String geonames=(String)entry.get(Constants.GeoName);



                            if(geoids!=null && geonames!=null){

                                if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){

                                    if(!dealerNameId.contains(geoids))
                                        dealerNameId.add(geoids);
                                    if(!dealerName.contains(geonames))
                                        dealerName.add(geonames);
                                }

                            }
                        }
                        Log.d("zonee", zonename.toString());



                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
                            ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,dealerName);
                            AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            spn_cfa_dealer_mg_spinner.setAdapter(AMAdapter);
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
            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetGeographyByLogin";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }

    private void getCFA() {
        // TODO Auto-generated method stub



        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getCFAObject(response.toString());
                    if(segmentList!=null){
                        SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), segmentList, android.R.layout.simple_spinner_item, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
                        segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        spn_cfa_dealer_mg_spinner.setAdapter(segmentAdapter);

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
        //	GetAllCFA(ByVal AMID As String, ByVal UserID As String, ByVal Password As String) As String

        try {
            request.put("AMID",passworddetails.getString("username",""));
            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetAllCFA";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }


    private void getGeographyByCode(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    arealist.clear();
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
                                else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
                                    if(!amidd.contains(geoids))
                                        amidd.add(geoids);
                                    if(!amname.contains(geonames))
                                        amname.add(geonames);
                                }
                                else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
                                    if(!areaidd.contains(geoids))
                                        areaidd.add(geoids);
                                    if(!areaname.contains(geonames))
                                        areaname.add(geonames);
                                }

                            }
                        }
                        Log.d("zonee", zonename.toString());

                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
                            ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,countryname);
                            countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            countryspinner.setAdapter(countryAdapter);



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
                            cityspinner.setAdapter(cityAdapter);
                        }
                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
                            ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,amname);
                            AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            spnr_Am.setAdapter(AMAdapter);
                        }

                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
                            ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,areaname);
                            AreaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            areaspinner.setAdapter(AreaAdapter);
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
            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetGeographyByLogin";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }



}
