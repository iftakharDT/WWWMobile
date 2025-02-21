package com.arcis.vgil.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.AmVisitDiaryAdapter;
import com.arcis.vgil.adapter.CustomerAdapterAutoComplete;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.ExpandableHeightListView;
import com.arcis.vgil.model.AMVisitDiaryModel;
import com.arcis.vgil.model.AmNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by jaim on 2/6/2017.
 */
public class AmVisitDiary extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private Button get_value,bt_submit;
    private AutoCompleteTextView ed_date;
    private ExpandableHeightListView list;
    private AutoCompleteTextView et_auto_name;
    private String ContactID;
    private SharedPreferences spref;
    private ArrayList<AmNameModel> amNameModelsList;
    private ArrayList<AMVisitDiaryModel> amVisitDiaryModelsList;
    String user_choseDate;
    String amID;
    private AmVisitDiaryAdapter adapter;
    private CustomerAdapterAutoComplete auto_adapter;



    @Override
    public void inti() {
        super.inti();
        setCurrentContext(this);
        setContentView(R.layout.customer_visit_diary);
        et_auto_name= (AutoCompleteTextView) findViewById(R.id.et_auto_name);
        ed_date= (AutoCompleteTextView) findViewById(R.id.ed_date);
        list= (ExpandableHeightListView) findViewById(R.id.list);
        bt_submit= (Button) findViewById(R.id.bt_submit);
        get_value= (Button) findViewById(R.id.get_value);
        ed_date.setOnClickListener(this);
        ed_date.setFocusable(false);
        ed_date.setClickable(true);
        bt_submit.setOnClickListener(this);
        get_value.setOnClickListener(this);
        amNameModelsList=new ArrayList<AmNameModel>();
        spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
        ContactID =spref.getString(Constants.USERID, "");


        if (ContactTypeAM.equalsIgnoreCase("1")) {
            GetHierarchyWiseAM(ContactTypeAM,ContactID,1);
        }

        if (ContactTypeAM.equalsIgnoreCase("4")) {
           GetHierarchyWiseAM(ContactTypeAM,ContactID,2);
        }
        if (ContactTypeAM.equalsIgnoreCase("5")) {
            GetHierarchyWiseAM(ContactTypeAM,ContactID,2);
        }

        if (ContactTypeAM.equalsIgnoreCase("27")) {
            GetHierarchyWiseAM(ContactTypeAM,ContactID,2);
        }
        if (ContactTypeAM.equalsIgnoreCase("28")) {
            GetHierarchyWiseAM(ContactTypeAM,ContactID,2);
        }

        if (ContactTypeAM.equalsIgnoreCase("3")) {
            GetHierarchyWiseAM(ContactTypeAM,ContactID,2);
        }

        et_auto_name.setOnItemClickListener(this);


    }

    private void GetHierarchyWiseAM(String contactTypeAM, String contactID, final int Type) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    amNameModelsList = FetchingdataParser.getAmNameWithID(response.toString());
                    if(amNameModelsList!=null ){
                        if (amNameModelsList.size()!=0) {
                            if (Type==1){
                             et_auto_name.setThreshold(1);
                             et_auto_name.setText(amNameModelsList.get(0).getAmname());
                             et_auto_name.setClickable(false);
                             et_auto_name.setEnabled(false);
                             et_auto_name.setFocusable(false);
                             amID= amNameModelsList.get(0).getAmidd();
                            }else if (Type==2){
                                et_auto_name.setThreshold(1);
                                auto_adapter = new CustomerAdapterAutoComplete(getCurrentContext(), R.layout.row_people, amNameModelsList);
                                et_auto_name.setAdapter(auto_adapter);


                            }


                        }


                    }
                    else if(response==null){
                        Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                    }

                }else if(response==null){
                    Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();

        try {
            // Function GetHierarchyWiseAM(ByVal ContactTypeID As String, ByVal ContactID As String,
            // ByVal UserID As String, ByVal Password As String) As String


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
        String methodName="GetHierarchyWiseAM";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.ed_date:

                Toast.makeText(getCurrentContext(),getResources().getString(R.string.date) , Toast.LENGTH_SHORT).show();

                final Calendar c_from = Calendar.getInstance();
                int hour = c_from.get(Calendar.HOUR_OF_DAY);
                int   mYear_from = c_from.get(Calendar.YEAR);
                int  mMonth_from = c_from.get(Calendar.MONTH);
                int  mDay_from = c_from.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd_from = new DatePickerDialog(getCurrentContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                user_choseDate=null;
                                String monthName=getMonthName((monthOfYear + 1));
                                user_choseDate= ((monthOfYear + 1) + "/"
                                        + dayOfMonth+ "/" +year );
                                ed_date.setText(monthName+" "+year);


                            }
                        }, mYear_from, mMonth_from, mDay_from);
                dpd_from.show();
                dpd_from.getDatePicker().setMaxDate(System.currentTimeMillis());
                break;

            case R.id.get_value:
                if (user_choseDate!=null&&et_auto_name.getText().length()!=0){
                    AMVisitDiary(user_choseDate,amID);

                }else {
                Toast.makeText(getCurrentContext(),"Name and Date should not empty", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.bt_submit:
                JSONArray jsonArray= new JSONArray();
                JSONObject manJson = null;
                for(int i=0;i<amVisitDiaryModelsList.size();i++)
                {
                if (amVisitDiaryModelsList.get(i).getActionNotes()!=null) {
                    if(  amVisitDiaryModelsList.get(i).getActionNotes().length()>0) {
                        //Toast.makeText(getCurrentContext(), amVisitDiaryModelsList.get(i).getActionNotes(),Toast.LENGTH_SHORT).show();
                        manJson= new JSONObject();
                        try {
                            manJson.put("VisitLogID",amVisitDiaryModelsList.get(i).getVisitLogID());
                            manJson.put("ActionNotes",amVisitDiaryModelsList.get(i).getActionNotes());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        jsonArray.put(manJson);

                    }
                }


            }

                UpdateActionNotes(jsonArray.toString());


                break;
        }


    }

    private void UpdateActionNotes(String strJSON) {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                        Toast.makeText(getCurrentContext(),response.toString() , Toast.LENGTH_SHORT).show();


                }else if(response==null){
                    Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();

        try {
            //Function UpdateActionNotes(ByVal ActionNotesBy As String,
            // ByVal ContactTypeID As String, ByVal strJSON As String,
            // ByVal UserID As String, ByVal Password As String) As String
            request.put("ActionNotesBy",spref.getString("username",""));
            request.put("ContactTypeID",   spref.getString(Constants.CONTACTTYPEID, ""));
            request.put("strJSON", strJSON);
            request.put(Constants.username,spref.getString("username",""));
            request.put(Constants.password,spref.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="UpdateActionNotes";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }

    private void AMVisitDiary(String user_choseDate, String amID) {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    amVisitDiaryModelsList = FetchingdataParser.getAmVisitDiaryResult(response.toString());
                    if(amNameModelsList!=null ){

                        adapter=new AmVisitDiaryAdapter(getCurrentContext(), R.layout.customer_visit_diary_shell, amVisitDiaryModelsList);
                        list.setAdapter(adapter);
                    }
                    else if(response==null){
                        Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                    }

                }else if(response==null){
                    Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();

        try {
            //  Function AMVisitDiary(ByVal ParamDate As DateTime, ByVal AMID As String,
            // ByVal UserID As String, ByVal Password As String) As String


            request.put("ParamDate", user_choseDate);
            request.put("AMID", amID);
            request.put(Constants.username,spref.getString("username",""));
            request.put(Constants.password,spref.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="AMVisitDiary";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }

    private static String getMonthName(int monthNumber) {
        String[] months = new DateFormatSymbols().getMonths();
        int n = monthNumber-1;
        return (n >= 0 && n <= 11) ? months[n] : "wrong number";
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int postion, long arg3) {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        // Show Alert
        AmNameModel selected = (AmNameModel) arg0.getAdapter().getItem(postion);
        amID=selected.getAmidd();



    }
}
