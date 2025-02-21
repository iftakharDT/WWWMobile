package com.arcis.vgil.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.AmVisitDiaryAdapter;
import com.arcis.vgil.adapter.CustomerAdapterAutoComplete;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.ExpandableHeightListView;
import com.arcis.vgil.model.AMVisitDiaryModel;
import com.arcis.vgil.model.AmNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/17/2017.
 */

public class CustomerVisitDiary extends Fragment implements View.OnClickListener {
    private Button get_value,bt_submit;
    private AutoCompleteTextView ed_date;
    private ExpandableHeightListView list;
    private AutoCompleteTextView et_auto_name;
    private String ContactID;
    private SharedPreferences spref;
    private ArrayList<AmNameModel> amNameModelsList,CustomerVisitDiaryModelsList;
    private ArrayList<AMVisitDiaryModel> amVisitDiaryModelsList;
    private String user_choseDate;
    String cityID,customerID="0";
    private AmVisitDiaryAdapter adapter;
    private CustomerAdapterAutoComplete auto_adapter;
    String ContactTypeAM;

    public CustomerVisitDiary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customer_visit_diary, container, false);

        et_auto_name= (AutoCompleteTextView) rootView.findViewById(R.id.et_auto_name);
        et_auto_name.setHint("City");
        ed_date= (AutoCompleteTextView) rootView.findViewById(R.id.ed_date);
        ed_date.setHint("Customer Name");
        ed_date.setInputType(InputType.TYPE_CLASS_TEXT);
        list= (ExpandableHeightListView)rootView. findViewById(R.id.list);
        bt_submit= (Button)rootView. findViewById(R.id.bt_submit);
        bt_submit.setVisibility(View.GONE);
        get_value= (Button) rootView.findViewById(R.id.get_value);
        bt_submit.setOnClickListener(this);
        get_value.setOnClickListener(this);
        amNameModelsList=new ArrayList<AmNameModel>();
        spref=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
        ContactID =spref.getString(Constants.USERID, "");


        if (ContactTypeAM.equalsIgnoreCase("14")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,1);
        }
        if (ContactTypeAM.equalsIgnoreCase("1")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,2);
        }

        if (ContactTypeAM.equalsIgnoreCase("4")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,2);
        }
        if (ContactTypeAM.equalsIgnoreCase("5")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,2);
        }

        if (ContactTypeAM.equalsIgnoreCase("27")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,2);
        }
        if (ContactTypeAM.equalsIgnoreCase("28")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,2);
        }

        if (ContactTypeAM.equalsIgnoreCase("3")) {
            CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,2);
        }

        et_auto_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ed_date.setText("");
                // Show Alert
                AmNameModel selected = (AmNameModel) parent.getAdapter().getItem(position);
                cityID=selected.getAmidd();
                if (ContactTypeAM.equalsIgnoreCase("14")) {
                    CitiesForCustomerVisitDiary(ContactTypeAM,ContactID,1);
                }

                if (ContactTypeAM.equalsIgnoreCase("1")) {
                    CustomerByCityForCustomerVisitDiary(cityID,2);
                }

                if (ContactTypeAM.equalsIgnoreCase("4")) {
                    CustomerByCityForCustomerVisitDiary(cityID,2);
                }
                if (ContactTypeAM.equalsIgnoreCase("5")) {
                    CustomerByCityForCustomerVisitDiary(cityID,2);
                }

                if (ContactTypeAM.equalsIgnoreCase("27")) {
                    CustomerByCityForCustomerVisitDiary(cityID,2);
                }
                if (ContactTypeAM.equalsIgnoreCase("28")) {
                    CustomerByCityForCustomerVisitDiary(cityID,2);
                }

                if (ContactTypeAM.equalsIgnoreCase("3")) {
                    CustomerByCityForCustomerVisitDiary(cityID,2);
                }

            }
        });
        ed_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Show Alert
                AmNameModel selected = (AmNameModel) parent.getAdapter().getItem(position);
                customerID=selected.getAmidd();

            }
        });

        return rootView;
    }


    private void CitiesForCustomerVisitDiary(String contactTypeAM, String contactID, final int Type) {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    amNameModelsList = FetchingdataParser.getCustomerCityNameWithID(response.toString());
                    if(amNameModelsList!=null ){
                        if (amNameModelsList.size()!=0) {
                            if (Type==1){
                                et_auto_name.setThreshold(1);
                                et_auto_name.setText(amNameModelsList.get(0).getAmname());
                                et_auto_name.setClickable(false);
                                et_auto_name.setEnabled(false);
                                et_auto_name.setFocusable(false);
                                cityID= amNameModelsList.get(0).getAmidd();
                                CustomerByCityForCustomerVisitDiary(cityID,1);
                            }else if (Type==2){
                                et_auto_name.setThreshold(1);
                                auto_adapter = new CustomerAdapterAutoComplete(getActivity(), R.layout.row_people, amNameModelsList);
                                et_auto_name.setAdapter(auto_adapter);
                            }
                        }
                    }
                    else if(response==null){
                        Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        try {
            //Function CitiesForCustomerVisitDiary(ByVal ContactTypeID As String,
            // ByVal ContactID As String, ByVal UserID As String, ByVal Password As String) As String
            request.put("ContactTypeID", contactTypeAM);
            request.put("ContactID", contactID);
            request.put(Constants.username,spref.getString("username",""));
            request.put(Constants.password,spref.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="CitiesForCustomerVisitDiary";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }

    private void CustomerByCityForCustomerVisitDiary(String CityID, final int Type) {


        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    CustomerVisitDiaryModelsList = FetchingdataParser.getCustomerNameWithID(response.toString());
                    if(CustomerVisitDiaryModelsList!=null ){
                        if (CustomerVisitDiaryModelsList.size()!=0) {
                            if (Type==1){
                                for(int i=0;i<CustomerVisitDiaryModelsList.size();i++){
                                    if (ContactID.equalsIgnoreCase(CustomerVisitDiaryModelsList.get(i).getAmidd())){
                                        ed_date.setThreshold(1);
                                        ed_date.setText(CustomerVisitDiaryModelsList.get(i).getAmname());
                                        ed_date.setClickable(false);
                                        ed_date.setEnabled(false);
                                        ed_date.setFocusable(false);
                                        customerID= CustomerVisitDiaryModelsList.get(i).getAmidd();
                                    }

                                }

                            }else if (Type==2){
                                ed_date.setThreshold(1);
                                auto_adapter = new CustomerAdapterAutoComplete(getActivity(), R.layout.row_people, CustomerVisitDiaryModelsList);
                                ed_date.setAdapter(auto_adapter);
                            }
                        }
                    }
                    else if(response==null){
                        Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        try {
            // Function CustomerByCityForCustomerVisitDiary(ByVal CityID As String, ByVal UserID As String,
            // ByVal Password As String) As String if (ContactTypeAM.equalsIgnoreCase("14")) {

            request.put("CityID", CityID);
            request.put(Constants.username,spref.getString("username",""));
            request.put(Constants.password,spref.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="CustomerByCityForCustomerVisitDiary";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_value:
                if (ed_date.getText().length()!=0){
                    CustomerVisitDiary(customerID);
                }else {
                    Toast.makeText(getActivity(),"Cutomer name should not blank", Toast.LENGTH_SHORT).show();
                }

                break;

        }


    }

    private void CustomerVisitDiary(String customerID) {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    amVisitDiaryModelsList = FetchingdataParser.getAmVisitDiaryResult(response.toString());
                    if(amNameModelsList!=null ){
                        if (amNameModelsList.size()!=0){
                            adapter=new AmVisitDiaryAdapter(getActivity(), R.layout.customer_visit_diary_shell, amVisitDiaryModelsList);
                            list.setAdapter(adapter);
                        }
                    }
                    else if(response==null){
                        Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                    }

                }else if(response==null){
                    Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();

        try {
            // Function CustomerVisitDiary(ByVal CustomerID As String,
            // ByVal UserID As String, ByVal Password As String) As String



            request.put("CustomerID", customerID);
            request.put(Constants.username,spref.getString("username",""));
            request.put(Constants.password,spref.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="CustomerVisitDiary";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }
}
