package com.arcis.vgil.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.PartNoAllCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.BottomSheet;
import com.arcis.vgil.model.DealerShipDetailsModel;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.arcis.vgil.helper.Utils.hideKeyboard;

/**
 * Created by jaim on 3/14/2017.
 */

public class DealerStockAvailibility  extends Fragment {

    private ArrayList<PartNoModel> partnoList;
    private PartNoAllCustomAlertAdapter partNoAdapter;
    private ListView list_part_no;
    private String ParntNo;;
    private EditText searchpartNo;
    private ImageView cfa_image,mg_image;
    private TextView mg_location,mg,cfa_location,cfa,part_no;
    private LinearLayout ll_imageshow;
    private LinearLayout ll_flotingbutton;
    private FloatingActionButton floatingActionButton;
    private Dialog dialog;

    public DealerStockAvailibility() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.check_product_stock_availibiltiy, container, false);

        searchpartNo=(EditText) rootView.findViewById(R.id.et_search);
        list_part_no= (ListView)rootView. findViewById(R.id.list_part_no);

        mg_location= (TextView)rootView. findViewById(R.id.mg_location);
        mg= (TextView)rootView. findViewById(R.id.mg);
        cfa_location= (TextView) rootView.findViewById(R.id.cfa_location);
        cfa= (TextView) rootView.findViewById(R.id.cfa);
        part_no= (TextView)rootView. findViewById(R.id.part_no);
        ll_imageshow= (LinearLayout)rootView. findViewById(R.id.ll_imageshow);

        cfa_image=(ImageView)rootView. findViewById(R.id.cfa_image);
        mg_image=(ImageView) rootView.findViewById(R.id.mg_image);
        ll_flotingbutton=(LinearLayout) rootView.findViewById(R.id.ll_flotingbutton);

        getPartNo(0,0,0);

        floatingActionButton=(FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckDealerStockExposeStatus(0);
             // ;
            }
        });

        return rootView;
    }

    private void CheckDealerStockExposeStatus(final int status) {
        {
            // TODO Auto-generated method stub
            GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
                @Override
                public void processResponse(Object response) {
                    if(response!=null){
                        if (status==0){
                            ArrayList<HashMap<String, String>> checkDealerStockExposeStatus = FetchingdataParser.getCheckDealerStockExposeStatus(response.toString());
                            if(checkDealerStockExposeStatus!=null ){

                                if (checkDealerStockExposeStatus.get(0).get(Constants.ExposeStock).equalsIgnoreCase("True")){
                                    checkStockExpose(ParntNo);
                                }else {
                                    dialog = new Dialog(getContext());
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.internet_dialoge_custome);

                                    TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                                    text.setText("Order status is off. Do you want to show your stock status ?");

                                    TextView dialogRetry= (TextView) dialog.findViewById(R.id.retry);
                                    TextView dialogexit = (TextView) dialog.findViewById(R.id.exit);
                                    dialogexit.setText("NO");
                                    dialogRetry.setText("YES");
                                    dialogexit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog.dismiss();
                                        }
                                    });
                                    dialogRetry.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CheckDealerStockExposeStatus(1);

                                        }
                                    });

                                    dialog.show();
                                }



                            }else {
                                Toast.makeText(getActivity(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

                            }

                        }else {
                             if(response.toString().equalsIgnoreCase("anyType{}") ){
                                dialog.dismiss();
                                checkStockExpose(ParntNo);
                            }else {
                                 dialog.dismiss();
                                 Toast.makeText(getActivity(),response.toString() , Toast.LENGTH_SHORT).show();

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
                //CheckDealerStockExposeStatus(ByVal DealerID As String)
                request.put("DealerID",passworddetails.getString(Constants.DEALERID,""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String methodName;
            String ipAddress=getResources().getString(R.string.ipaddress);
            String webService =getResources().getString(R.string.Webservice_mis_android);
            String nameSpace=getResources().getString(R.string.nameSpace);
            if (status==0){
                methodName="CheckDealerStockExposeStatus";
            }else {
                methodName="UpdateDealerStockExposeStatus";
            }

            String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
            dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
            dataFromNetwork.sendData(request);
            dataFromNetwork.execute();
        }
    }


    private void getPartNo(int SegmentID,int OEID,int ApplicationID) {
        // TODO Auto-generated method stub



        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    partnoList = FetchingdataParser.getPartNo(response.toString());
                    if(partnoList!=null){
                        if (partnoList.size()!=0) {
                            partNoAdapter=new PartNoAllCustomAlertAdapter(getActivity(), R.layout.am_manual_part_no_shell, partnoList);
                            list_part_no.setAdapter(partNoAdapter);
                            list_part_no.setOnItemClickListener(new AdapterView.OnItemClickListener() {



                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long arg3) {
                                    // TODO Auto-generated method stub
                                    ParntNo = partNoAdapter.getItem(position).getProductID();
                                    part_no.setText(" Part No:" + partNoAdapter.getItem(position).getCode());
                                    hideKeyboard(getActivity());
                                    //Function CheckProductStockAvailibilityStatus(ByVal DealerID As String,
                                    // ByVal ProductID As String) As String
                                    ll_flotingbutton.setVisibility(View.GONE);
                                    CheckProductStockAvailibilityStatus(partNoAdapter.getItem(position).getProductID());
  }

                                private void CheckProductStockAvailibilityStatus(String partID) {
                                    // TODO Auto-generated method stub
                                    GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
                                        @Override
                                        public void processResponse(Object response) {
                                            if(response!=null){
                                                ArrayList<HashMap<String, String>> checkProductStockAvailibilityStatus = FetchingdataParser.getCheckProductStockAvailibilityStatus(response.toString());
                                                if(checkProductStockAvailibilityStatus!=null ){
                                                    if (checkProductStockAvailibilityStatus.size()!=0) {
                                                        ll_imageshow.setVisibility(View.VISIBLE);

                                                        mg_location.setText(checkProductStockAvailibilityStatus.get(0).get(Constants.MGCity));
                                                        cfa_location.setText(checkProductStockAvailibilityStatus.get(0).get(Constants.CFACity));
                                                        if (checkProductStockAvailibilityStatus.get(0).get(Constants.MGClour).equalsIgnoreCase("RSO")) {
                                                            mg_image.setBackgroundResource(R.drawable.vgil_circular_fill_white_inner);
                                                        }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.MGClour).equalsIgnoreCase("R")) {
                                                            mg_image.setBackgroundResource(R.drawable.vgil_circular_fill_red);
                                                        }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.MGClour).equalsIgnoreCase("W")) {
                                                            mg_image.setBackgroundResource(R.drawable.vgil_circular_fill_white);

                                                        }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.MGClour).equalsIgnoreCase("Y")) {
                                                            mg_image.setBackgroundResource(R.drawable.vgil_circular_fill_yellow);

                                                        }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.MGClour).equalsIgnoreCase("G")) {
                                                            mg_image.setBackgroundResource(R.drawable.vgil_circular_fill_green);
                                                        }
                                                    }
                                                    if (checkProductStockAvailibilityStatus.get(0).get(Constants.CFAColour).equalsIgnoreCase("RSO")) {
                                                        cfa_image.setBackgroundResource(R.drawable.vgil_circular_fill_white_inner);
                                                    }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.CFAColour).equalsIgnoreCase("R")) {
                                                        cfa_image.setBackgroundResource(R.drawable.vgil_circular_fill_red);
                                                    }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.CFAColour).equalsIgnoreCase("W")) {
                                                        cfa_image.setBackgroundResource(R.drawable.vgil_circular_fill_white);

                                                    }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.CFAColour).equalsIgnoreCase("Y")) {
                                                        cfa_image.setBackgroundResource(R.drawable.vgil_circular_fill_yellow);

                                                    }else if (checkProductStockAvailibilityStatus.get(0).get(Constants.CFAColour).equalsIgnoreCase("G")) {
                                                        cfa_image.setBackgroundResource(R.drawable.vgil_circular_fill_green);
                                                    }

                                                    if (checkProductStockAvailibilityStatus.get(0).get(Constants.CFAColour).equalsIgnoreCase("W")&&
                                                        checkProductStockAvailibilityStatus.get(0).get(Constants.MGClour).equalsIgnoreCase("W")){
                                                        Toast.makeText(getActivity(),"Please click info button to check more details", Toast.LENGTH_SHORT).show();
                                                        ll_flotingbutton.setVisibility(View.VISIBLE);

                                                    }



                                                }else {
                                                    Toast.makeText(getActivity(),"Not available across india" , Toast.LENGTH_SHORT).show();

                                                }

                                            }else if(response==null){
                                                Toast.makeText(getActivity(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
                                    SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
                                    try {
                                        //	Function CheckProductStockAvailibilityStatus(ByVal DealerID As String,
                                        //ByVal ProductID As String) As String

                                        request.put("DealerID",passworddetails.getString(Constants.DEALERID,""));
                                        request.put("ProductID", partID);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    String ipAddress=getResources().getString(R.string.ipaddress);
                                    String webService =getResources().getString(R.string.Webservice_mis_android);
                                    String nameSpace=getResources().getString(R.string.nameSpace);
                                    String methodName="CheckProductStockAvailibilityStatus";
                                    String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
                                    dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
                                    dataFromNetwork.sendData(request);
                                    dataFromNetwork.execute();

                                }

                            });


                            searchpartNo.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                    // TODO Auto-generated method stub

                                    if(DealerStockAvailibility.this.partNoAdapter!=null)
                                        DealerStockAvailibility.this.partNoAdapter.getFilter().filter(arg0);
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


    private void checkStockExpose(String partID) {
        // TODO Auto-generated method stub
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){
                    ArrayList<DealerShipDetailsModel> checkProductStockAvailibilityStatus = FetchingdataParser.getCheckStockAvailabilityOnCustomers(response.toString());
                    if(checkProductStockAvailibilityStatus!=null ){
                        if (checkProductStockAvailibilityStatus.size()!=0) {
                            BottomSheet dialog = new BottomSheet(getActivity(),checkProductStockAvailibilityStatus);
                            dialog.show();

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
            //CheckStockAvailabilityOnCustomers(ByVal DealerID As String, ByVal ProductID As String)
            request.put("DealerID",passworddetails.getString(Constants.DEALERID,""));
            request.put("ProductID", partID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="CheckStockAvailabilityOnCustomers";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
            }
}