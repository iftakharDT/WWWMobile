package com.arcis.vgil.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 2/17/2017.
 */

public class BroadcastFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    private RadioButton rb_internal, rb_External, rb_All,rb_byGeo;
    private RadioGroup rg_selection_int_external, rg_selection_external;
    private LinearLayout ll_geo;
    private Spinner sp_state,  sp_city, sp_customer;
    private EditText et_message ;
    private Button bt_braodcast;
    String ContactType;
    ArrayList<HashMap<String,Object>> arealist;
    ArrayList<String> statename,stateidd,cityname,cityidd;
    String cityVid="0",customerTypeID;

    ArrayList<String> contacttypedataIDMM=new ArrayList<String>(Arrays.asList("0","4","3","1"));
    ArrayList<String> contacttypedataIDRM=new ArrayList<String>(Arrays.asList("0","3","1"));
    ArrayList<String> contacttypedataIDSI=new ArrayList<String>(Arrays.asList("0","1"));
    ArrayList<String> contacttypedataIDAM=new ArrayList<String>(Arrays.asList("0","4","3","1"));

    ArrayList<String> contacttypedataIDEXT=new ArrayList<String>(Arrays.asList("0","14","15","16"));







    public BroadcastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_broacast, container, false);

        arealist=new ArrayList<HashMap<String,Object>>();
        statename=new ArrayList<String>();
        stateidd=new ArrayList<String>();
        cityname=new ArrayList<String>();
        cityidd=new ArrayList<String>();

        rb_internal= (RadioButton) rootView.findViewById(R.id.rb_internal);
        rb_External= (RadioButton) rootView.findViewById(R.id.rb_External);
        rb_byGeo= (RadioButton) rootView.findViewById(R.id.rb_byGeo);
        rb_All= (RadioButton) rootView.findViewById(R.id.rb_All);


        bt_braodcast= (Button) rootView.findViewById(R.id.bt_braodcast);
        bt_braodcast.setOnClickListener(this);




        rg_selection_int_external= (RadioGroup) rootView.findViewById(R.id.rg_selection_int_external);
        rg_selection_int_external.setOnCheckedChangeListener(this);
        rg_selection_external= (RadioGroup) rootView.findViewById(R.id.rg_selection_external);
        rg_selection_external.setOnCheckedChangeListener(this);



        ll_geo= (LinearLayout) rootView.findViewById(R.id.ll_geo);

        sp_state= (Spinner) rootView.findViewById(R.id.sp_state);
        sp_city= (Spinner) rootView.findViewById(R.id.sp_city);
        sp_customer= (Spinner) rootView.findViewById(R.id.sp_customer);

        et_message= (EditText) rootView.findViewById(R.id.et_message);
        bt_braodcast= (Button) rootView.findViewById(R.id.bt_braodcast);

        SharedPreferences spref=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        final String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");

        if (ContactTypeAM.equalsIgnoreCase("1")) {
            rb_internal.setVisibility(View.GONE);
            ContactType="AM";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactTypeAM,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,"0","0","0","0");
        }
        if (ContactTypeAM.equalsIgnoreCase("4")) {
            ContactType="RM";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactTypeAM,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,"0","0","0","0");
            ArrayList<String> contacttypedata=new ArrayList<String>(Arrays.asList("Please select","SI","AM"));
            customerContact(contacttypedata);

        }
        if (ContactTypeAM.equalsIgnoreCase("5")) {
            ContactType="MM";

            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactTypeAM,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,"0","0","0","0");

            ArrayList<String> contacttypedata=new ArrayList<String>(Arrays.asList("Please select","RM","SI","AM"));
            customerContact(contacttypedata);



        }

        if (ContactTypeAM.equalsIgnoreCase("27")) {
            ContactType="CO";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactTypeAM,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,"0","0","0","0");
            /*String [] contacttypedata = {"Please select","D","M"};

            customerContact(contacttypedata);*/


        }
        if (ContactTypeAM.equalsIgnoreCase("28")) {
            ContactType="HO";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactTypeAM,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,"0","0","0","0");
            /*String [] contacttypedata = {"Please select","D","M"};

            customerContact(contacttypedata);*/

        }

        if (ContactTypeAM.equalsIgnoreCase("3")) {
            ContactType="SI";
            GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactTypeAM,"0","0","0","0");
            GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,"0","0","0","0");
            ArrayList<String> contacttypedata=new ArrayList<String>(Arrays.asList("Please select","AM"));
            customerContact(contacttypedata);

        }


        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if(position!=0){
                    cityidd.clear();
                    cityname.clear();
                    GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactTypeAM,stateidd.get(sp_state.getSelectedItemPosition()),"0","0","0");

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

                    cityVid=cityidd.get(sp_city.getSelectedItemPosition());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        sp_customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (rb_External.isChecked()){
                        customerTypeID=contacttypedataIDEXT.get(sp_customer.getSelectedItemPosition());

                }else {
                    if (ContactTypeAM.equalsIgnoreCase("1")) {
                        customerTypeID=contacttypedataIDRM.get(sp_customer.getSelectedItemPosition());
                    }
                    if (ContactTypeAM.equalsIgnoreCase("4")) {
                        customerTypeID=contacttypedataIDRM.get(sp_customer.getSelectedItemPosition());
                    }
                    if (ContactTypeAM.equalsIgnoreCase("5")) {
                        customerTypeID=contacttypedataIDMM.get(sp_customer.getSelectedItemPosition());
                    }
                    if (ContactTypeAM.equalsIgnoreCase("3")) {
                        customerTypeID=contacttypedataIDSI.get(sp_customer.getSelectedItemPosition());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    private void customerContact(ArrayList<String> contacttypedata) {

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,contacttypedata);
        countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sp_customer.setAdapter(countryAdapter);





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
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,statename);
                            stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp_state.setAdapter(stateAdapter);
                        }

                        if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,cityname);
                            cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            sp_city.setAdapter(cityAdapter);
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


    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (group.getId()){
            case R.id.rg_selection_int_external:
                switch (checkedId){
                    case R.id.rb_internal:

                        break;
                    case R.id.rb_External:

                        ArrayList<String> contacttypedata=new ArrayList<String>(Arrays.asList("Please select","D","R","M"));
                        customerContact(contacttypedata);

                        break;
                }
                break;

            case R.id.rg_selection_external:
                switch (checkedId){
                    case R.id.rb_All:
                        ll_geo.setVisibility(View.GONE);

                        break;
                    case  R.id.rb_byGeo:
                        ll_geo.setVisibility(View.VISIBLE);
                        break;


                }
                default:
                    break;
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (rg_selection_int_external.getCheckedRadioButtonId()!=-1){
            if (et_message.getText().length()!=0){
                SaveBroadcastData();
            }
           else if (rg_selection_external.getCheckedRadioButtonId()!=-1 && rb_byGeo.isChecked()){
                if (sp_state.getSelectedItemPosition()!=0||sp_customer.getSelectedItemPosition()!=0){

                    if (et_message.getText().length()!=0){
                        SaveBroadcastData();
                    }else {
                        Toast.makeText(getActivity(),"Massege should not empaty", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getActivity(),"Please select either State or Customer type", Toast.LENGTH_LONG).show();
                }
            }
                else  if (et_message.getText().length()!=0){
                    SaveBroadcastData();
                }else {
                    Toast.makeText(getActivity(),"Massege should not empaty", Toast.LENGTH_LONG).show();
                }

        }else {
            Toast.makeText(getActivity(),"Please select either Internal or External type", Toast.LENGTH_LONG).show();
        }

    }

    private void SaveBroadcastData() {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
                getActivity(), ProgressDialog.STYLE_SPINNER,
                "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {

                if (response == null) {
                    Toast.makeText( getActivity(),
                            getResources().getString(R.string.error4),
                            Toast.LENGTH_SHORT).show();
                }


                else {
                    if (response != null) {
                       et_message.setText("");
                    }

                }
            }
        });
        // Function SaveBroadcastData(ByVal IsInternal As String, ByVal IsAll As String,
        // ByVal ContactTypeID As String, ByVal StateID As String, ByVal CityID As String,
        // ByVal AreaID As String, ByVal Message As String, ByVal CreatedByID As String, ByVal CreatedByTypeID As String,
        // ByVal UserID As String, ByVal Password As String) As String


        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =  getActivity().getSharedPreferences("PASSWORD",MODE_PRIVATE);

        try {
            if (rb_internal.isChecked()){
                request.put("IsInternal","1");
            }else {
                request.put("IsInternal","0");
            }
            if (rb_All.isChecked()){
                request.put("IsAll","1");
            }else {
                request.put("IsAll","0");
            }


            request.put("ContactTypeID",customerTypeID);
            request.put("StateID",stateidd.get(sp_state.getSelectedItemPosition()));
            request.put("CityID",cityidd.get(sp_city.getSelectedItemPosition()));
            request.put("AreaID","0");
            request.put("Message",et_message.getText().toString());
            request.put("CreatedByID",passworddetails.getString(Constants.ID,""));
            request.put("CreatedByTypeID",passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="SaveBroadcastData";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }




}
