package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.PendingOrderPartNoAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PendingOrderPartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Jaim on 1/27/2017.
 */

public class RM_MM_PendingOrderDetails extends BaseActivity {
   private String DealerID;
   private String selectedDealerName;
   ArrayList<PendingOrderPartNoModel> partnoList;
   private ListView list;
   private EditText et_search;
   private PendingOrderPartNoAdapter pendingOrderPartNoAdapter;

    @Override
    public void inti() {
        super.inti();
        setContentView(R.layout.rm_mm_pending_order);
        setCurrentContext(this);
        et_search= (EditText) findViewById(R.id.et_search);
        list= (ListView) findViewById(R.id.list);
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        if (b!=null) {
            DealerID=b.getString("DealerID");
            selectedDealerName=b.getString("selectedDealerName");
            PartWisePendingOrderMIS(DealerID);
        }

    }

    private void PartWisePendingOrderMIS(final String dealerId) {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){


                        partnoList = FetchingdataParser.getPendingOrderPartNo(response.toString());
                        if(partnoList!=null){

                            if (partnoList.size()!=0) {
                                pendingOrderPartNoAdapter=new PendingOrderPartNoAdapter(getCurrentContext(), R.layout.rm_mm_sale_shell, partnoList);
                                list.setAdapter(pendingOrderPartNoAdapter);

                                et_search.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                        // TODO Auto-generated method stub
                                        if(RM_MM_PendingOrderDetails.this.pendingOrderPartNoAdapter!=null)
                                            RM_MM_PendingOrderDetails.this.pendingOrderPartNoAdapter.getFilter().filter(arg0);
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
                            else {
                                Toast.makeText(getCurrentContext(),getResources().getString(R.string.no_data) , Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                        }

                    }else if(response==null){
                        Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                    }
            }
        });

        LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
            request.put("DealerID", dealerId);
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="PartWisePendingOrderMIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();



    }

}
