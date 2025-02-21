package com.arcis.vgil.trackapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.adapter.HorizontalReportAddapter;
import com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork;
import com.arcis.vgil.trackapp.data.Constants;
import com.arcis.vgil.trackapp.model.Note;
import com.arcis.vgil.trackapp.model.SubSegmentType;
import com.arcis.vgil.trackapp.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Ram on 2/19/2020.
 */
public class AMDailyReport extends BaseActivity {
    private static final String TAG = "AMDailyReport";

    private EditText et_task1;
    private EditText et_task2;
    private EditText et_task3;
    private EditText et_task4;
    private EditText et_task5;
    private EditText et_task6;
    private EditText et_task7;
    private EditText et_task8;
    private EditText et_task9;
    private EditText et_task10;
    private EditText et_primary_order;
    private EditText et_secondary_order;


    private Button btn_submit;

    private RecyclerView horizontalRecyclerView;
    private RecyclerView verticalRecyclerView;
    private List<String> DataSource;
    private HorizontalReportAddapter hAdapter;
    private TextView tv_date;
    private List<Note> submitItemList=new ArrayList<>();
    private ArrayList<SubSegmentType> subSegmentList= new ArrayList<>();




    public static final String DATE_FORMAT_2 = "EEE, dd-MMM-yyyy";

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    @Override
    public void inti() {
        super.inti();
        setContentView(R.layout.daily_report);

        et_task1 =  findViewById(R.id.et_task1);
        et_task2 =  findViewById(R.id.et_task2);
        et_task3 =  findViewById(R.id.et_task3);
        et_task4 =  findViewById(R.id.et_task4);
        et_task5 =  findViewById(R.id.et_task5);
        et_task6 =  findViewById(R.id.et_task6);
        et_task7 =  findViewById(R.id.et_task7);
        et_task8 =  findViewById(R.id.et_task8);
        et_task9 =  findViewById(R.id.et_task9);
        et_task10 =  findViewById(R.id.et_task10);
        et_primary_order =  findViewById(R.id.et_primary_order);
        et_secondary_order =  findViewById(R.id.et_secondary_order);

         findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 submitNote();
             }
         });


       /* //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        //setting the title
        mTitle.setText("Daily Notes");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_date.setText(""+getCurrentDate());
        tv_date.setEnabled(false);

        horizontalRecyclerView = findViewById(R.id.rv_horizontal);
        getSubSegment();


    }



  private void getSubSegment() {

      GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(this, ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
          @Override
          public void processResponse(Object response) {
              if(response!=null){
                  subSegmentList = FetchingdataParser.getSubSegment(response.toString());
                  if(subSegmentList!=null ){
                      if (subSegmentList.size()!=0) {

                          hAdapter = new HorizontalReportAddapter(AMDailyReport.this, subSegmentList);
                          LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(AMDailyReport.this, LinearLayoutManager.HORIZONTAL, false);
                          horizontalRecyclerView.setLayoutManager(recyclerViewLayoutManager);
                          horizontalRecyclerView.setAdapter(hAdapter);
                      }
                  }
                  else if(response==null){
                      Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                  }

              }
          }
      });

      LinkedHashMap<String, Object> request=new LinkedHashMap<String, Object>();
      SharedPreferences passworddetails = getSharedPreferences("PASSWORD", MODE_PRIVATE);
      try {
          request.put(Constants.username, passworddetails.getString("username", ""));
          request.put(Constants.password, passworddetails.getString("password", ""));
      } catch (Exception e) {
          e.printStackTrace();
      }

      String ipAddress=getResources().getString(R.string.ipaddress);
      String webService =getResources().getString(R.string.Webservice_Sale);
      String nameSpace=getResources().getString(R.string.nameSpace);
      String methodName="GetSubSegment";
      String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
      dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
      dataFromNetwork.sendData(request);
      dataFromNetwork.execute();

  }


  private void submitNote(){

      submitItemList.clear();

        if(!et_task1.getText().toString().isEmpty()){
            Note note1 = new Note();
            note1.setSN(1);
            note1.setNoteContent(et_task1.getText().toString());
            submitItemList.add(note1);
        }

      if(!et_task2.getText().toString().isEmpty()){
          Note note2 = new Note();
          note2.setSN(2);
          note2.setNoteContent(et_task2.getText().toString());
          submitItemList.add(note2);
      }
      if(!et_task3.getText().toString().isEmpty()){
          Note note3 = new Note();
          note3.setSN(3);
          note3.setNoteContent(et_task3.getText().toString());
          submitItemList.add(note3);
      }
      if(!et_task4.getText().toString().isEmpty()){
          Note note4 = new Note();
          note4.setSN(4);
          note4.setNoteContent(et_task4.getText().toString());
          submitItemList.add(note4);
      }
      if(!et_task5.getText().toString().isEmpty()){
          Note note5 = new Note();
          note5.setSN(5);
          note5.setNoteContent(et_task5.getText().toString());
          submitItemList.add(note5);
      }
      if(!et_task6.getText().toString().isEmpty()){
          Note note6 = new Note();
          note6.setSN(6);
          note6.setNoteContent(et_task6.getText().toString());
          submitItemList.add(note6);
      }
      if(!et_task7.getText().toString().isEmpty()){
          Note note7 = new Note();
          note7.setSN(7);
          note7.setNoteContent(et_task7.getText().toString());
          submitItemList.add(note7);
      }
      if(!et_task8.getText().toString().isEmpty()){
          Note note8 = new Note();
          note8.setSN(8);
          note8.setNoteContent(et_task8.getText().toString());
          submitItemList.add(note8);
      }
      if(!et_task9.getText().toString().isEmpty()){
          Note note9 = new Note();
          note9.setSN(9);
          note9.setNoteContent(et_task9.getText().toString());
          submitItemList.add(note9);
      }
      if(!et_task10.getText().toString().isEmpty()){
          Note note10 = new Note();
          note10.setSN(10);
          note10.setNoteContent(et_task10.getText().toString());
          submitItemList.add(note10);
      }

      if(!submitItemList.isEmpty())
      uploadNoteToServer();
      else
          Toast.makeText(AMDailyReport.this, "Please upload At least one Task.", Toast.LENGTH_SHORT).show();

  }


    private void uploadNoteToServer() {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(this, ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object result) {
                SoapObject responce = null;
                try {
                    responce = (SoapObject) result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                AlertDialog.Builder errordialog = new AlertDialog.Builder(AMDailyReport.this);
                if (responce == null) {
                    errordialog
                            .setTitle(getStringFromResource(R.string.error6));
                    if (result != null) {
                        errordialog.setMessage(result.toString());
                    } else {
                        errordialog
                                .setMessage(getStringFromResource(R.string.error4));
                    }

                } else {
                    errordialog.setMessage(getStringFromResource(R.string.message5));

                }
                errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                finish();
                            }
                        });
                AlertDialog dialog = errordialog.create();
                dialog.show();

            }
        });

        LinkedHashMap<String, Object> request=new LinkedHashMap<String, Object>();
      SharedPreferences spref = getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
        try {

            request.put("CustomerID",spref.getString(Constants.USERID,""));
            request.put("Segment",getSubSegmentString());
            request.put("JSONData",getJsonStringForNote());
            request.put(Constants.username, spref.getString(Constants.ID,""));
            request.put(Constants.password, spref.getString("password", ""));
            request.put("PrimaryOrder",(et_primary_order.getText().toString()!=null && !et_primary_order.getText().toString().isEmpty())? et_primary_order.getText().toString():0);
            request.put("SecondaryOrder",(et_secondary_order.getText().toString()!=null && !et_secondary_order.getText().toString().isEmpty())? et_secondary_order.getText().toString():0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_Sale);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="SaveDailyNote";
        String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }

    private String getJsonStringForNote() {
        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);



        StringBuilder dataString  = new StringBuilder();
        dataString.append("[");


        for(int i=0;i<submitItemList.size();i++){
            Note note = submitItemList.get(i);

            dataString.append("{");
            dataString.append("'");
            dataString.append("SN");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(note.getSN());
            dataString.append("'");
            dataString.append(",");

            dataString.append("'");
            dataString.append("Note");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(note.getNoteContent());
            dataString.append("'");
            dataString.append("}");
            dataString.append(",");


        }
        dataString.append("]");
        Log.i("Server data String ", dataString.toString());

        return dataString.toString();

    }

  public String getSubSegmentString(){

        StringBuilder subSegmentString  = new StringBuilder();

        for(SubSegmentType subSegmentType : subSegmentList){

            if(subSegmentType.isSubSegmentSelected()){
                try {

                    subSegmentString.append(subSegmentType.getSubSegmentID());
                    subSegmentString.append(",");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

        return subSegmentString.toString();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
