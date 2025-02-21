package com.arcis.vgil.trackapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.BaseActivity;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.activity.Summary;
import com.arcis.vgil.adapter.ShowCustomerDetailsAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;
import com.arcis.vgil.trackapp.adapter.ShowPreviousCallDetailsAdapter;
import com.arcis.vgil.trackapp.adapter.ShowReciavableDetailsAdapter;
import com.arcis.vgil.trackapp.dialog.CallDialog;
import com.arcis.vgil.trackapp.fragment.NavigationHomeFragment;
import com.arcis.vgil.trackapp.model.TerettoryListModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CallingScreenDetail extends BaseActivity {


    private String dealerID;
    private TextView tv_dealership_name;
    private TextView tv_contact_person;
    private TextView tv_this_year_target;
    private TextView tv_ytd_sale;
    private TextView tv_receivable;
    private TextView tv_pending_order;
    private String visitLogID;
    private ImageView iv_start_call;
    private ImageView iv_end_call;
    private Button btn_previous_call_details;
    private Button btn_share;

private String dealerContactType;
   private double latitude;
   private double longitude;

    private TextView tv_count_down_timer;
    private TextView tv_call_start_date_time;
    private CountDownTimer countDownTimer;
    private String   systemDateTime;
    private String shareData;


    @Override
    public void inti() {
        super.inti();
        setContentView(R.layout.activity_calling_screen_detail);
         tv_dealership_name=findViewById(R.id.tv_dealership_name);
        tv_contact_person=findViewById(R.id.tv_contact_person);
        tv_this_year_target=findViewById(R.id.tv_this_year_target);
        tv_ytd_sale=findViewById(R.id.tv_ytd_sale);
        tv_receivable=findViewById(R.id.tv_receivable);
        tv_receivable.setOnClickListener(this);
        tv_pending_order=findViewById(R.id.tv_pending_order);
        iv_start_call =findViewById(R.id.iv_start_call);
        iv_end_call =findViewById(R.id.iv_end_call);
        btn_previous_call_details =findViewById(R.id.btn_previous_call_details);
        btn_share =findViewById(R.id.btn_share);


        iv_start_call.setOnClickListener(this);
        iv_end_call.setOnClickListener(this);
        btn_previous_call_details.setOnClickListener(this);
        btn_share.setOnClickListener(this);

        tv_count_down_timer = findViewById(R.id.tv_count_down_timer);
        tv_call_start_date_time = findViewById(R.id.tv_call_start_date_time);



        dealerID = getIntent().getExtras().getString("DEALER_ID");
        dealerContactType = getIntent().getExtras().getString("DEALER_CONTACT_TYPE");
        latitude = getIntent().getExtras().getDouble("LATITUDE");
        longitude = getIntent().getExtras().getDouble("LONGITUDE");

        getDealerDetails(dealerID);

    }

    private void startAnimation(){


        //we will have 2 animator foreach view, fade in & fade out
        //prepare animators - creating array of animators & instantiating Object animators
        View[] images = {findViewById(R.id.img1),findViewById(R.id.img2), findViewById(R.id.img3),findViewById(R.id.img4)}; //array of views that we want to animate
        ArrayList<ObjectAnimator> anims = new ArrayList<>(images.length * 2);
        for (View v : images) anims.add(ObjectAnimator.ofFloat(v, View.ALPHA, 0f, 1f).setDuration(80)); //fade in animator
        for (View v : images) anims.add(ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0f).setDuration(80)); //fade out animator

        final AnimatorSet set = new AnimatorSet(); //create Animator set object
        //if we want to repeat the animations then we set listener to start again in 'onAnimationEnd' method
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                set.start(); //repeat animator set indefinitely
            }
        });

        set.setStartDelay(600); //set delay every time we start the chain of animations
        for (int i = 0; i < anims.size() - 1; i++) set.play(anims.get(i)).before(anims.get(i + 1)); //put all animations in set by order (from first to last)

        set.start();


    }


    private void  getDealerDetails(String dealerID){

        GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(CallingScreenDetail.this, ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

            @Override
            public void processResponse(Object result) {
                // TODO Auto-generated method stub
                if(result==null){
                    Toast.makeText(CallingScreenDetail.this, getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
                }else{
                    ArrayList<HashMap<String, String >> dealerDetailsList = FetchingdataParser.getDealerDetails(result.toString());
                    if(dealerDetailsList==null){
                        AlertDialog.Builder errordialog = new AlertDialog.Builder(CallingScreenDetail.this);
                        errordialog.setTitle("Error!");
                        errordialog.setMessage(result.toString());
                        errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = errordialog.create();
                        dialog.show();
                    }else{
                        if(dealerDetailsList.size()==0){
                            Toast.makeText(CallingScreenDetail.this, "No data found!", Toast.LENGTH_SHORT).show();
                        }else{


                            //{ 'Root': { 'table': [{'ThisYearTarget': '125.00','SaleYTD': '10.60','Receivables': '1.24','PO': '0.47'}] }}

                           // tv_primary_customers.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", welcomeDataList.get(0).get(Constants.PRIMARY_CUSTOMER))));
                           // tv_secondary_customers.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", welcomeDataList.get(0).get(Constants.SECONDRY_CUSTOMER))));

                            tv_dealership_name.setText(dealerDetailsList.get(0).get(Constants.DEALERSHIP_NAME));
                            tv_contact_person.setText(dealerDetailsList.get(0).get(Constants.CONTACT_PERSON_NAME));
                            tv_this_year_target.setText(dealerDetailsList.get(0).get(Constants.THIS_YEAR_TARGET));
                            tv_ytd_sale.setText(dealerDetailsList.get(0).get(Constants.SALE_YTD));
                           // tv_receivable.setText(dealerDetailsList.get(0).get(Constants.RECEIVABLE));
                            tv_receivable.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", dealerDetailsList.get(0).get(Constants.RECEIVABLE))));
                            tv_pending_order.setText(dealerDetailsList.get(0).get(Constants.PO));

                        }
                    }

                }

            }
        });

        LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {

            requestdata.put("DealerID", dealerID);
            requestdata.put(Constants.username, passworddetails.getString("username",""));
            requestdata.put(Constants.password,passworddetails.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="DealerCallingScreenDetail";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();




    }

    private void gettingSystemTime() {
        // TODO Auto-generated method stub


        com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork dataFromNetwork = new com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork(CallingScreenDetail.this, ProgressDialog.STYLE_SPINNER, "Loading...", new com.arcis.vgil.trackapp.activity.GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {

                    ArrayList<HashMap<String, String>> itemQuantityValueList = com.arcis.vgil.trackapp.parser.FetchingdataParser.gettingSystemTime(response.toString());
                    if (itemQuantityValueList != null) {
                        if (itemQuantityValueList.size() != 0) {

                          systemDateTime = itemQuantityValueList.get(0).get(com.arcis.vgil.trackapp.data.Constants.combinedDateAndTime);
                            visitLogID = itemQuantityValueList.get(0).get(Constants.visitLogID);

                          if(systemDateTime!=null)
                                tv_call_start_date_time.setText(systemDateTime);

                        }


                    } else {
                        Toast.makeText(CallingScreenDetail.this, getResources().getString(R.string.message4) + "4", Toast.LENGTH_SHORT).show();

                    }

                } else if (response == null) {
                    Toast.makeText(CallingScreenDetail.this, getResources().getString(R.string.error4), Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {

            // Function GetCurrentDateAndTime(ByVal UserID As String, ByVal Password As String) As String


            request.put(com.arcis.vgil.trackapp.data.Constants.CONTACT_ID, passworddetails.getString(com.arcis.vgil.trackapp.data.Constants.USERID, ""));
            request.put(com.arcis.vgil.trackapp.data.Constants.CUSTOMER_ID, dealerID);
            request.put(com.arcis.vgil.trackapp.data.Constants.CUSTOMER_TYPE_ID, dealerContactType);
            request.put(com.arcis.vgil.trackapp.data.Constants.OTHER_ACTIVITY_ID,"0");
            request.put(com.arcis.vgil.trackapp.data.Constants.LATTITUDE, ""+latitude);
            request.put(com.arcis.vgil.trackapp.data.Constants.LONGITUDE,""+longitude);
            request.put(com.arcis.vgil.trackapp.data.Constants.username, passworddetails.getString(com.arcis.vgil.trackapp.data.Constants.ID, ""));
            request.put(com.arcis.vgil.trackapp.data.Constants.password, passworddetails.getString("password", ""));


        } catch (Exception e) {
            e.printStackTrace();
        }


        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_mis_android);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "GetCallStartDateAndTime";
        String soapcomponent = getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){

            case R.id.iv_start_call:
                gettingSystemTime();
                startAnimation();
                startCountDownTimer();
                break;
            case R.id.iv_end_call:
                endCall();
                    break;

            case R.id.btn_previous_call_details:
                showPreviousCallDetails();
                break;

            case R.id.tv_receivable:
            case R.id.btn_share:
                showReceivableDetails();
                break;







        }
    }


    private void shareData(){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareData);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }


    private void showReceivableDetails(){

        GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(CallingScreenDetail.this, ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

            @Override
            public void processResponse(Object result) {
                // TODO Auto-generated method stub
                if(result==null){
                    Toast.makeText(CallingScreenDetail.this, getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
                }else{
                    shareData=result.toString();
                    ArrayList<HashMap<String, String >> previousCallList = com.arcis.vgil.trackapp.parser.FetchingdataParser.getReceivableDetails(result.toString());
                    if(previousCallList==null){
                        AlertDialog.Builder errordialog = new AlertDialog.Builder(CallingScreenDetail.this);
                        errordialog.setTitle("Error!");
                        errordialog.setMessage(result.toString());
                        errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = errordialog.create();
                        dialog.show();
                    }else{
                        if(previousCallList.size()==0){
                            Toast.makeText(CallingScreenDetail.this, "No data found!", Toast.LENGTH_SHORT).show();
                        }else{
                            LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view  = inflator.inflate(R.layout.dialog_ps_customer_details, null);
                            RecyclerView rv_pending=view.findViewById(R.id.rv_pending);
                           FloatingActionButton fb_share =view.findViewById(R.id.fb_share);
                            fb_share.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    shareData();
                                }
                            });
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CallingScreenDetail.this);
                            bottomSheetDialog.setContentView(view);
                            bottomSheetDialog.show();

                            LinearLayoutManager rv_attribute_lm = new LinearLayoutManager(CallingScreenDetail.this);
                            rv_attribute_lm.setOrientation(LinearLayoutManager.VERTICAL);
                            rv_pending.setLayoutManager(rv_attribute_lm);

                            ShowReciavableDetailsAdapter showReciavableDetailsAdapter = new ShowReciavableDetailsAdapter(getApplicationContext(),previousCallList);
                            rv_pending.setAdapter(showReciavableDetailsAdapter);




                        }
                    }

                }

            }
        });

        LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {

            requestdata.put("CustomerID", dealerID);
            requestdata.put(Constants.username, passworddetails.getString("username",""));
            requestdata.put(Constants.password,passworddetails.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetReceivableDetail";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();




    }

    private void showPreviousCallDetails(){

        GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(CallingScreenDetail.this, ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

            @Override
            public void processResponse(Object result) {
                // TODO Auto-generated method stub
                if(result==null){
                    Toast.makeText(CallingScreenDetail.this, getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
                }else{
                    ArrayList<HashMap<String, String >> previousCallList = com.arcis.vgil.trackapp.parser.FetchingdataParser.getPreviousCallDetails(result.toString());
                    if(previousCallList==null){
                        AlertDialog.Builder errordialog = new AlertDialog.Builder(CallingScreenDetail.this);
                        errordialog.setTitle("Error!");
                        errordialog.setMessage(result.toString());
                        errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = errordialog.create();
                        dialog.show();
                    }else{
                        if(previousCallList.size()==0){
                            Toast.makeText(CallingScreenDetail.this, "No data found!", Toast.LENGTH_SHORT).show();
                        }else{
                            LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view  = inflator.inflate(R.layout.dialog_previous_call_details, null);
                            RecyclerView rv_pending=view.findViewById(R.id.rv_pending);
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CallingScreenDetail.this);
                            bottomSheetDialog.setContentView(view);
                            bottomSheetDialog.show();

                            LinearLayoutManager rv_attribute_lm = new LinearLayoutManager(CallingScreenDetail.this);
                            rv_attribute_lm.setOrientation(LinearLayoutManager.VERTICAL);
                            rv_pending.setLayoutManager(rv_attribute_lm);

                            ShowPreviousCallDetailsAdapter showPreviousCallDetailsAdapter = new ShowPreviousCallDetailsAdapter(getApplicationContext(),previousCallList);
                            rv_pending.setAdapter(showPreviousCallDetailsAdapter);




                        }
                    }

                }

            }
        });

        LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {

            requestdata.put("CustomerID", dealerID);
            requestdata.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID, ""));
            requestdata.put(Constants.username, passworddetails.getString("username",""));
            requestdata.put(Constants.password,passworddetails.getString("password",""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetPreviousCallHistory";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();




    }


    private void startCountDownTimer(){


        countDownTimer = new CountDownTimer(2*60*60*1000, 1000) {                     //geriye sayma

            public void onTick(long millisUntilFinished) {

                try {

                    NumberFormat f = new DecimalFormat("00");
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;
                    tv_count_down_timer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            public void onFinish() {
                tv_count_down_timer.setText("00:00:00");
                saveAMTerettoryCustomersMeetingLog(true);

            }
        }.start();

    }

    private void cancelCountDownTimer(){

        if(countDownTimer!=null)
            countDownTimer.cancel();
    }

    private void endCall(){

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CallingScreenDetail.this);
        alertDialog.setMessage("Your have an Ongoing call which will be disconnected. Are you sure to exit?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                cancelCountDownTimer();
                saveAMTerettoryCustomersMeetingLog(false);
                dialog.dismiss();
                finish();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = alertDialog.create();
        dialog.setCancelable(true);
        dialog.show();
    }


    public void saveAMTerettoryCustomersMeetingLog(final boolean fromCountDownTimer) {

            com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork dataFromNetwork = new com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork(CallingScreenDetail.this, ProgressDialog.STYLE_SPINNER, "Uploading data...", new com.arcis.vgil.trackapp.activity.GetDataCallBack() {
                @Override
                public void processResponse(Object response) {

                    //{ 'Root': { 'AMVisitLog': [{'VisitLogID': '130'}] }}


                    try {

                        if (response == null) {
                            Toast.makeText(CallingScreenDetail.this,
                                    getResources().getString(R.string.error4),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (response != null) {
                                AlertDialog.Builder errordialog = new AlertDialog.Builder(
                                        CallingScreenDetail.this);
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
            SharedPreferences passworddetails = getSharedPreferences("PASSWORD", MODE_PRIVATE);


            try {

                request.put(com.arcis.vgil.trackapp.data.Constants.VISIT_LOG_ID, visitLogID);
                request.put(com.arcis.vgil.trackapp.data.Constants.CONTACT_ID, passworddetails.getString(com.arcis.vgil.trackapp.data.Constants.USERID, ""));
                request.put(com.arcis.vgil.trackapp.data.Constants.CONTACT_TYPE,dealerContactType);
                request.put(com.arcis.vgil.trackapp.data.Constants.RE_OPEN_DATE, systemDateTime);
                request.put(com.arcis.vgil.trackapp.data.Constants.REMARK, "");
                request.put(com.arcis.vgil.trackapp.data.Constants.IS_RE_OPEN,"0");
                request.put(com.arcis.vgil.trackapp.data.Constants.username, passworddetails.getString(com.arcis.vgil.trackapp.data.Constants.USERID, ""));
                request.put(com.arcis.vgil.trackapp.data.Constants.password, passworddetails.getString("password", ""));


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


    @Override
    public void onBackPressed() {

        endCall();
    }
}
