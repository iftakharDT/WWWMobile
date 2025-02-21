package com.arcis.vgil.trackapp.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.activity.CallingScreenDetail;
import com.arcis.vgil.trackapp.activity.GetDataCallBack;
import com.arcis.vgil.trackapp.activity.TerettoryCustomersList;
import com.arcis.vgil.trackapp.adapter.TerettoryCustomersAdapter;
import com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork;
import com.arcis.vgil.trackapp.data.Constants;
import com.arcis.vgil.trackapp.dialog.CallDialog;
import com.arcis.vgil.trackapp.model.TerettoryListModel;
import com.arcis.vgil.trackapp.parser.FetchingdataParser;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;


public class NavigationHomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private TerettoryCustomersAdapter terettoryCustomersAdapter;
    private EditText et_search;
    private ExpandableLayout expandableLayout;
    private ImageView iv_close_expandable;
    private Spinner  sp_state;
    private Spinner sp_city;
    private FloatingActionButton fab;
    private ArrayList<HashMap<String,Object>> arealist;
    private ArrayList<String> cityname;
    private ArrayList<String>cityidd;
    private ArrayList<String> statename;
    private ArrayList<String>stateidd;
    private  String systemDateTime;
    private String visitLogID;
    private RadioGroup radio_group;
    private RadioButton radio_primary_customer;
    private RadioButton radio_secondary_customer;

    final ArrayList<TerettoryListModel> showCustomerList = new ArrayList<TerettoryListModel>();

    private static final String CALL_BEGIN_DIALOG_TAG = "Call_dialog_tag";

    private TerettoryListModel terettoryCustomer =null;
    private static final String TAG = "NavigationHomeFragment";
    public NavigationHomeFragment() {
        // Required empty public constructor
    }

    public static NavigationHomeFragment newInstance(String param1, String param2) {
        NavigationHomeFragment fragment = new NavigationHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        expandableLayout = view.findViewById(R.id.expandable_layout);
        iv_close_expandable = view.findViewById(R.id.iv_close_expandable);
        sp_state = view.findViewById(R.id.sp_state);
        sp_city = view.findViewById(R.id.sp_city);
        radio_group = view.findViewById(R.id.radio_group);
        radio_group.setVisibility(View.GONE);
        radio_primary_customer = view.findViewById(R.id.radio_primary_customer);
        radio_secondary_customer = view.findViewById(R.id.radio_secondary_customer);

        recyclerView = view.findViewById(R.id.recycler_view);

        arealist=new ArrayList<HashMap<String,Object>>();
        statename=new ArrayList<String>();
        stateidd=new ArrayList<String>();
        cityname=new ArrayList<String>();
        cityidd=new ArrayList<String>();

        //getting the toolbar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
         et_search = (EditText) toolbar.findViewById(R.id.et_search);
         fab = toolbar.findViewById(R.id.fab);

        toolbar.setTitle("");
        turnGPSOn();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableAction();
            }
        });

        iv_close_expandable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableAction();
            }
        });



        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    // StateId=stateidd.get(statespinner.getSelectedItemPosition());
                    cityname.clear();
                    cityidd.clear();

                    GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,stateidd.get(sp_state.getSelectedItemPosition()),"0","0","");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){

                    GetAMTerettoryCustomers(cityidd.get(sp_city.getSelectedItemPosition()));
                    expandableAction();
                }
                else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        getUnclosedVisits();



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        GetAMTerettoryCustomers("0");
    }

    private void getUnclosedVisits() {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {

                    ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getUnclosedVisitsList(response.toString());
                    if (itemQuantityValueList != null) {
                        if (itemQuantityValueList.size() != 0) {

                            try {

                                showUnclosedCalls(itemQuantityValueList.get(0));


                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }

                    } else {
                        //Toast.makeText(getCurrentContext(), getResources().getString(R.string.message4) + "4", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {

            // Function GetCurrentDateAndTime(ByVal UserID As String, ByVal Password As String) As String

            request.put("ContactID", passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.username, passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.password, passworddetails.getString("password", ""));


        } catch (Exception e) {
            e.printStackTrace();
        }


        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_mis_android);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "GetUnclosedVisits";
        String soapcomponent = getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }


    private void showUnclosedCalls(final HashMap<String, String> itemToShow){

        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.show_unclosed_calls, null);

        TableLayout tableLayout1 = (TableLayout) view.findViewById(R.id.table1);
        TableLayout tableLayout2 = (TableLayout) view.findViewById(R.id.table2);
        tableLayout2.setVisibility(View.GONE);
        TextView nameTV = (TextView)view.findViewById(R.id.nameTV) ;
        TextView mobileTV = (TextView)view.findViewById(R.id.mobileTV) ;
        TextView garageNameTV = (TextView)view.findViewById(R.id.garageNameTV);
        TextView cityTV = (TextView)view.findViewById(R.id.cityTV);
        TextView stateTV = (TextView)view.findViewById(R.id.stateTV);
        TextView dateTimeTV = (TextView)view.findViewById(R.id.dateTimeTV) ;
        TextView otherActivityTV = (TextView)view.findViewById(R.id.otherActivityTV);
        TextView dateTimeTV2 = (TextView)view.findViewById(R.id.dateTimeTV2);
        final EditText remarkET = (EditText)view.findViewById(R.id.remarkET);

        if(itemToShow.get(Constants.CONTACT_TYPE).equalsIgnoreCase("0")){
            tableLayout2.setVisibility(View.VISIBLE);
            tableLayout1.setVisibility(View.GONE);
            dateTimeTV2.setText(itemToShow.get(Constants.START_DATE_TIME));
            otherActivityTV.setText(itemToShow.get(Constants.OTHER_ACTIVITY));

        }else{
            tableLayout2.setVisibility(View.GONE);
            tableLayout1.setVisibility(View.VISIBLE);

            nameTV.setText(itemToShow.get(Constants.NAME));
            mobileTV.setText(itemToShow.get(Constants.MOBILE));
            garageNameTV.setText(itemToShow.get(Constants.GARAGE_NAME));
            cityTV.setText(itemToShow.get(Constants.CITY));
            stateTV.setText(itemToShow.get(Constants.STATE));
            dateTimeTV.setText(itemToShow.get(Constants.START_DATE_TIME));

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);


        builder.setPositiveButton(
                "END CALL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        endCall(itemToShow.get(Constants.VISIT_LOG_ID),itemToShow.get(Constants.CONTACT_TYPE),itemToShow.get(Constants.START_DATE_TIME),remarkET.getText().toString());

                        dialog.dismiss();
                    }
                });

        /*builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });*/

        builder.setCancelable(true);

        AlertDialog dialog = builder.show();
        dialog.setCancelable(false);
    }


    private void endCall(String visitLogID, String contactType,String datetime,String remark) {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {

                try {

                    if (response == null) {
                        Toast.makeText(getActivity(),
                                getResources().getString(R.string.error4),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (response != null) {
                            AlertDialog.Builder errordialog = new AlertDialog.Builder(
                                    getActivity());
                            if (response.toString().equalsIgnoreCase("anyType{}")) {
                                errordialog.setMessage("Call Ended Successfully.");
                                errordialog.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                // TODO Auto-generated method stub

                                                dialog.dismiss();

                                            }
                                        });
                                AlertDialog dialog = errordialog.create();
                                dialog.show();
                            } else {
                                errordialog.setMessage(response.toString());
                                errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = errordialog.create();
                                dialog.show();

                            }

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {

            // Function GetCurrentDateAndTime(ByVal UserID As String, ByVal Password As String) As String

            request.put(Constants.VISIT_LOG_ID, visitLogID);
            request.put(Constants.CONTACT_ID, passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.CONTACT_TYPE,contactType);
            request.put(Constants.RE_OPEN_DATE, datetime);
            request.put(Constants.REMARK, remark);
            request.put(Constants.IS_RE_OPEN,"1");
            request.put(Constants.username, passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.password, passworddetails.getString("password", ""));


        } catch (Exception e) {
            e.printStackTrace();
        }


        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_mis_android);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "SaveUnclosedVisits";
        String soapcomponent = getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }






    private void GetAMTerettoryCustomers(String cityID) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
                getActivity(), ProgressDialog.STYLE_SPINNER,
                "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
               ArrayList<TerettoryListModel> terretoryList = new ArrayList<TerettoryListModel>();
              //  terettoryCustomersAdapter.notifyDataSetChanged();
                if (response == null) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.error4),
                            Toast.LENGTH_SHORT).show();
                    radio_group.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    if (response != null) {
                            terretoryList = new FetchingdataParser().getTerettoryCustomersList(response.toString());
                        if (terretoryList.size() == 0) {
                            Toast.makeText(getActivity(),
                                    response.toString(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            radio_group.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            customerSelection(terretoryList);
                        }
                    }
                }
            }
        });

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences("PASSWORD",MODE_PRIVATE);

        try {
            request.put(Constants.contactID, passworddetails.getString(Constants.USERNAME, ""));
            request.put(Constants.CONTACTTYPEID_1, passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put(Constants.cityID, cityID);
            request.put(Constants.username,passworddetails.getString(Constants.ID, ""));
            request.put(Constants.password,passworddetails.getString("password", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.webService);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName ="CityWiseCustomerForDailyCalls";
        String soapcomponent = getResources().getString(R.string.soapcomponent);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName,
                soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }

private void customerSelection(final ArrayList<TerettoryListModel>terretoryList){

    showCustomerList.clear();
    radio_primary_customer.setChecked(true);
    radio_secondary_customer.setChecked(false);

    for (TerettoryListModel customer: terretoryList) {
        if( customer.getContactType().equals("14")){
            showCustomerList.add(customer);
        }
    }

    showCustomerList(showCustomerList);
   // terettoryCustomersAdapter.notifyDataSetChanged();


    radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            showCustomerList.clear();
            if(group.getCheckedRadioButtonId() == R.id.radio_primary_customer) {

                for (TerettoryListModel customer: terretoryList) {
                    if( customer.getContactType().equals("14")){
                        showCustomerList.add(customer);
                    }

                }
            } else if(checkedId == R.id.radio_secondary_customer) {
                for (TerettoryListModel customer: terretoryList) {
                    if(!customer.getContactType().equals("14")){
                        showCustomerList.add(customer);
                    }

                }
            }


            showCustomerList(showCustomerList);
           // terettoryCustomersAdapter.notifyDataSetChanged();

        }
    });



}




private void showCustomerList( ArrayList<TerettoryListModel>showCustomerList){




    terettoryCustomersAdapter= new TerettoryCustomersAdapter(getActivity(), ((TerettoryCustomersList)getActivity()).getLatitude(),((TerettoryCustomersList)getActivity()).getLongitude(), showCustomerList, new TerettoryCustomersAdapter.CustomerAdapterListener() {
        @Override
        public void onCustomerSelected(TerettoryListModel customer) {
            // Toast.makeText(getActivity(), "item Clicked", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onCallStart(TerettoryListModel customer) {

            if (((TerettoryCustomersList)getActivity()).isLatLongcaptured()) {

                Intent intent =new Intent(getActivity(), CallingScreenDetail.class);
                intent.putExtra("DEALER_ID",customer.getID());
                intent.putExtra("DEALER_CONTACT_TYPE",customer.getContactType());
                intent.putExtra("LATITUDE",((TerettoryCustomersList)getActivity()).getLatitude());
                intent.putExtra("LONGITUDE",((TerettoryCustomersList)getActivity()).getLongitude());
                startActivity(intent);

            } else {
                Toast.makeText(getActivity(), "System is not able to detect you Geo-Location. Please check your GPS and network.", Toast.LENGTH_SHORT).show();
                if (!((TerettoryCustomersList)getActivity()).getRequestingLocationUpdates()) {
                    ((TerettoryCustomersList)getActivity()).startLocationClick();
                }

            }

        }

        @Override
        public void onCallEnd(TerettoryListModel customer) {
            //saveAMTerettoryCustomersMeetingLog(false);
        }
    });

    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(false);
    recyclerView.setAdapter(terettoryCustomersAdapter);


    et_search.addTextChangedListener(new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

            if(terettoryCustomersAdapter!=null)
                terettoryCustomersAdapter.getFilter().filter(arg0);
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

private void GetGeographyForAMVisitLog(final String geoName,String stateId,String cityID, String areaId,String ContactType) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    arealist.clear();

                    arealist=new FetchingdataParser().getarealistparser(response.toString());
                    if(arealist.size()==0){
                        Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();
                    }else {


                        for (HashMap<String, Object> entry : arealist)
                        {

                            String geoids=(String)entry.get(Constants.GeoID);
                            String geonames=(String)entry.get(Constants.GeoName);



                            if(geoids!=null && geonames!=null){

                                if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){

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

                            }
                        }

                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,statename);
                            stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp_state.setAdapter(stateAdapter);
                        }

                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item,cityname);
                            cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp_city.setAdapter(cityAdapter);
                        }

                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
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

            if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("1")) {
                request.put("LoginType", "AM");
            }else if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("3")) {
                request.put("LoginType", "SI");
            }else if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("34")) {
                request.put("LoginType", "TSR");
            }
            else {
                request.put("LoginType", "RM");
            }

            request.put(Constants.GeoName,geoName);
            //request.put("GeoID","0");
            request.put("ContactID",passworddetails.getString(Constants.USERID,""));
            request.put(Constants.stateID,stateId );
            request.put(Constants.cityID,cityID );
            request.put(Constants.areaID, areaId);
            request.put("ContactType", passworddetails.getString(Constants.CONTACTTYPEID ,""));


            request.put(Constants.username, passworddetails.getString("username",""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetGeographyForAMVisitLog";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getActivity().sendBroadcast(poke);
        }
    }


    private void expandableAction(){

        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse();
            if(terettoryCustomersAdapter!=null)
            terettoryCustomersAdapter.setIsClickable(true);
        } else {

            expandableLayout.expand();
            if(terettoryCustomersAdapter!=null)
            terettoryCustomersAdapter.setIsClickable(false);
            GetGeographyForAMVisitLog(Constants.GEOCODE_STATE,"0","0","0","0");
            GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,"0","0","0","0");
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        ((TerettoryCustomersList)getActivity()).setRequestingLocationUpdates(false);
        ((TerettoryCustomersList)getActivity()).stopLocationUpdates();
    }





}
