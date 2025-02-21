package com.arcis.vgil.fragment;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.DealerNameHistoryForSale;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.RM_MM_SalesAmAdapter;
import com.arcis.vgil.adapter.RM_MM_SalesPartAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.RM_MM_AMSalesModel;
import com.arcis.vgil.model.RM_MM_SalesPartModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/10/2017.
 */

public class RM_MM_Sales extends Fragment {
    private EditText et_search;
    private ListView am_listview;
    private ArrayList<RM_MM_AMSalesModel> rm_mm_amSalesModelsList;
    private ArrayList<RM_MM_SalesPartModel> rm_mm_partSalesModelsList;
    private RM_MM_SalesAmAdapter rm_mm_salesAmAdapter;
    private RM_MM_SalesPartAdapter rm_mm_salesPartAdapter;
    private LinearLayout ll_sale_header;
    private AlertDialog dialog;

    public RM_MM_Sales() {
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
        et_search= (EditText)rootView. findViewById(R.id.et_search);
        et_search.setVisibility(View.VISIBLE);
        et_search.setHint("AM Name");
        ll_sale_header= (LinearLayout)rootView. findViewById(R.id.ll_sale_header);
        ll_sale_header.setVisibility(View.VISIBLE);
        am_listview= (ListView) rootView.findViewById(R.id.am_listview);
        rm_MM_SaleAmWise();

        return rootView;
    }

    private void rm_MM_SaleAmWise() {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){


                    rm_mm_amSalesModelsList = FetchingdataParser.getAMNameWithSalesDetails(response.toString());
                    if(rm_mm_amSalesModelsList!=null){

                        if (rm_mm_amSalesModelsList.size()!=0) {
                            rm_mm_salesAmAdapter=new RM_MM_SalesAmAdapter(getActivity(), R.layout.rm_mm_sale_shell, rm_mm_amSalesModelsList);
                            rm_mm_salesAmAdapter.setpartDetailsToListenerName(new RM_MM_SalesAmAdapter.dealerDetailsToListenerName() {
                                @Override
                                public void ondealerDetailsToListenerName(int position) {
                                    Intent intent =  new Intent(getActivity(),DealerNameHistoryForSale.class);
                                    intent.putExtra("AmID",rm_mm_amSalesModelsList.get(position).getAMID()) ;
                                    startActivity(intent);

                                }

                            });
                            rm_mm_salesAmAdapter.setpartDetailsToListener(new RM_MM_SalesAmAdapter.partDetailsToListenerPart() {
                                @Override
                                public void onpartDetailsToListenerPart(int position) {
                                    PartNoHistoryForSale(rm_mm_amSalesModelsList.get(position).getAMID());
                                }

                            });
                            am_listview.setAdapter(rm_mm_salesAmAdapter);

                            et_search.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub

                                    if(RM_MM_Sales.this.rm_mm_salesAmAdapter!=null)
                                        RM_MM_Sales.this.rm_mm_salesAmAdapter.getFilter().filter(arg0);
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
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_data) , Toast.LENGTH_SHORT).show();
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
            /*IF @MISType='AM WISE'
            ELSE IF @MISType='DEALER WISE'
            ELSE IF @MISType='AM PART WISE'
            ELSE IF @MISType='DEALER PART WISE'*/

            request.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID,""));
            request.put("ContactID", passworddetails.getString(Constants.USERID,""));
            request.put("AMID","0");
            request.put("DealerID","0");// passworddetails.getString(Constants.USERID,"")
            request.put("MISType","AM WISE"); // DEALER WISE
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Function SaleYearWiseComparision(ByVal ContactTypeID As String,
        // ByVal ContactID As String, ByVal AMID As String, ByVal DealerID As String,
        // ByVal MISType As String, ByVal UserID As String, ByVal Password As String) As String

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="SaleYearWiseComparision";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();



    }

    private void PartNoHistoryForSale(String amid) {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){


                    rm_mm_partSalesModelsList = FetchingdataParser.getPartWithSalesDetails(response.toString());
                    if(rm_mm_partSalesModelsList!=null){

                        if (rm_mm_partSalesModelsList.size()!=0) {
                            // rm_mm_salesPartAdapter=new RM_MM_SalesPartAdapter(getCurrentContext(), R.layout.rm_mm_sale_shell, rm_mm_partSalesModelsList);
                            //  am_listview.setAdapter(rm_mm_salesAmAdapter);


                            LayoutInflater inflator = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
                            LinearLayout ll_pending_order_dialogue=(LinearLayout)view.findViewById(R.id.ll_pending_order_dialogue);
                            ll_pending_order_dialogue.setVisibility(View.VISIBLE);
                            ll_pending_order_dialogue.setWeightSum(3);

                            TextView tv_lyquantity=(TextView)view.findViewById(R.id.tv_lyquantity);
                            tv_lyquantity.setText("LY Quantity");

                            TextView tv_tyquantity=(TextView)view.findViewById(R.id.tv_tyquantity);
                            tv_tyquantity.setText("TY Quantity");

                            TextView et_cyvalue=(TextView)view.findViewById(R.id.et_cyvalue);
                            TextView et_lyvalue=(TextView)view.findViewById(R.id.et_lyvalue);
                            et_cyvalue.setVisibility(View.GONE);
                            et_lyvalue.setVisibility(View.GONE);

                            EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
                            ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
                            AlertDialog.Builder  builder= new AlertDialog.Builder(getActivity());
                            builder.setView(view);
                            rm_mm_salesPartAdapter=new RM_MM_SalesPartAdapter(getActivity(), R.layout.external_customer_master_cell, rm_mm_partSalesModelsList);
                            list_part_no.setAdapter(rm_mm_salesPartAdapter);
                            searchpartNo.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub
                                    if(RM_MM_Sales.this.rm_mm_salesPartAdapter!=null)
                                       RM_MM_Sales.this.rm_mm_salesPartAdapter.getFilter().filter(arg0);
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
                        else {
                            Toast.makeText(getActivity(),getResources().getString(R.string.no_data) , Toast.LENGTH_SHORT).show();
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
            /*IF @MISType='AM WISE'
            ELSE IF @MISType='DEALER WISE'
            ELSE IF @MISType='AM PART WISE'
            ELSE IF @MISType='DEALER PART WISE'*/

            request.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID,""));
            request.put("ContactID", passworddetails.getString(Constants.USERID,""));
            request.put("AMID",amid);
            request.put("DealerID","0");//passworddetails.getString(Constants.USERID,"")
            request.put("MISType","AM PART WISE");// DEALER PART WISE
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));


        } catch (Exception e) {
            e.printStackTrace();
        }
        // Function SaleYearWiseComparision(ByVal ContactTypeID As String,
        // ByVal ContactID As String, ByVal AMID As String, ByVal DealerID As String,
        // ByVal MISType As String, ByVal UserID As String, ByVal Password As String) As String

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="SaleYearWiseComparision";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

}
