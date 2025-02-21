package com.arcis.vgil.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.AM_DealerManualAdapterTwo;
import com.arcis.vgil.adapter.AvailableMenualOrderAlertAdapter;
import com.arcis.vgil.adapter.PartCustomAlertAdapterNew;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.BottomSheet;
import com.arcis.vgil.model.AM_DealerManualOrder;
import com.arcis.vgil.model.AvailableManualOrder;
import com.arcis.vgil.model.DealerDealerSale;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ram on 3/06/2019.
 */

public class DLR_Mannual_Order_New extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView partNo;
    // private Button Submit;
    private Button availability;
    private RecyclerView mListView;
    private Spinner cust_code_spinner;
    private EditText cust_name, cust_city;
    private LinearLayout dealerDetailsLL;
    PartCustomAlertAdapterNew arrayAdapter;
    String dealerId, DEALERCODE;
    String contacttypeId;
    ArrayList<DealerDealerSale> salelist;
    ArrayList<DealerDealerSale> showSalelist = new ArrayList<>();
    ArrayList<HashMap<String, Object>> arealist;
    String ContactType, ContactID;
    ArrayList<String> dealerCodeList, dealerIDList;
    AlertDialog dialog;
    //TextView pending_position, desc_position, quantity_position, sku_position;
    // private TextView git_position,
    private HashMap<String, String> dataMap;
    private ArrayList<AM_DealerManualOrder> orderList = new ArrayList<AM_DealerManualOrder>();
    private AM_DealerManualAdapterTwo mManualAdapter;
    ArrayList<AM_DealerManualOrder> requestBean = new ArrayList<AM_DealerManualOrder>();
    String productID;
    private View rootView;
    private  ArrayList<AvailableManualOrder> orderAllPartTwo = new ArrayList<>();

    public DLR_Mannual_Order_New() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.am_mannual_order_new, container, false);


        partNo = (TextView) rootView.findViewById(R.id.mannual_part_no);
        cust_city = (EditText) rootView.findViewById(R.id.mannual_city);
        cust_name = (EditText) rootView.findViewById(R.id.mannual_customer_name);
        mListView = (RecyclerView) rootView.findViewById(R.id.mannual_oder_lV);
        // addItem = (Button) rootView.findViewById(R.id.additem);
        /*Submit = (Button) rootView.findViewById(R.id.submit);
        Submit.setEnabled(false);
        Submit.setAlpha(0.5f);
*/
        availability = (Button) rootView.findViewById(R.id.availability);
        cust_code_spinner = (Spinner) rootView.findViewById(R.id.cust_code_spinner);
        //  pending_position = (TextView) rootView.findViewById(R.id.pending_position);
        // sku_position = (TextView) rootView.findViewById(R.id.sku_position);
        // git_position = (TextView) rootView.findViewById(R.id.git_position);
        //desc_position = (TextView) rootView.findViewById(R.id.desc_position);
        //quantity_position = (EditText) rootView.findViewById(R.id.quantity_position);
        dealerDetailsLL = (LinearLayout) rootView.findViewById(R.id.dealerDetailsLL);
        dealerDetailsLL.setVisibility(View.GONE);

        mManualAdapter = new AM_DealerManualAdapterTwo(getActivity(), orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mManualAdapter);

        SharedPreferences spref = getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        if (spref.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("14")) {
            ContactType = "DLR";
        } else {
            ContactType = "AM";
            dealerDetailsLL.setVisibility(View.VISIBLE);
        }
        ContactID = spref.getString(Constants.USERID, "");


        partNo.setOnClickListener(this);
        //addItem.setOnClickListener(this);
        // mListView.setOnItemClickListener(this);
        // Submit.setOnClickListener(this);
        availability.setOnClickListener(this);
        cust_code_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                if (position != 0) {
                    DEALERCODE = dealerIDList.get(cust_code_spinner.getSelectedItemPosition());
                    GetDealerDetails(dealerIDList.get(cust_code_spinner.getSelectedItemPosition()));

                } else {

                    Toast.makeText(getActivity(), "Please select customer code ", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                DEALERCODE = "0";
            }
        });
        arealist = new ArrayList<HashMap<String, Object>>();
        dealerCodeList = new ArrayList<String>();
        dealerIDList = new ArrayList<String>();

        GetMappedGeographyForLogin(ContactType, Constants.GEOCODE_DLR, "0", ContactID, "0", "0");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.mannual_part_no:

                try {
                    if (!DEALERCODE.equals(null) || !DEALERCODE.isEmpty()) {

                        partNo.setError(null);

                        if (salelist == null || salelist.isEmpty()) {

                            getProductList(DEALERCODE);
                        } else {

                            showProductList();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Select Dealer Code", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getActivity(), "Please Select Dealer Code", Toast.LENGTH_LONG).show();
                }

                break;

           /* case R.id.additem:


                if (validation()) {
                    // Capture Data

                    AM_DealerManualOrder order = CaptureData(dataMap);
                    orderList.add(order);
                    //updateItemAmount(order);
                    clearScreen();

                   *//* if (mManualAdapter == null) {
                        mManualAdapter = new AM_DealerManualAdapterTwo(getActivity(), orderList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                        mListView.setLayoutManager(mLayoutManager);
                        mListView.setAdapter(mManualAdapter);
                    } else {
                        mManualAdapter.notifyDataSetChanged();
                    }*//*
                    Toast.makeText(getActivity(), "Item added successfully!", Toast.LENGTH_LONG).show();


                }


                break;*/


            case R.id.availability:

                if (orderList.size() > 0) {
                    String jsondata = null;
                    try {
                        jsondata = getJsonStringForManualOrder(orderList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                    }
                   availabilityCheck(DEALERCODE, jsondata);
                } else {
                    Toast.makeText(getActivity(), "Please add order item!", Toast.LENGTH_LONG).show();
                }

                break;


            /*case R.id.submit:

                if (orderList.size() > 0) {
                    String jsondata = null;
                    try {
                        jsondata = getJsonStringForManualOrder(orderList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                    }
                    uploadManualOrder(DEALERCODE, jsondata);
                } else {
                    Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                }

                break;*/


            default:
                break;
        }
    }


    private void getProductList(String dealerId) {
        // TODO Auto-generated method stub


        GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

            @Override
            public void processResponse(Object result) {
                // TODO Auto-generated method stub
                if (result == null) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
                } else {
                    salelist = FetchingdataParser.getDealeMannualrSale(result.toString());
                    if (salelist == null) {
                        AlertDialog.Builder errordialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
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
                    } else {
                        if (salelist == null) {
                            AlertDialog.Builder errordialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                            errordialog.setTitle("Error!");
                            errordialog.setMessage("Data is Not available");
                            errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = errordialog.create();
                            dialog.show();
                        } else {
                            if (salelist.size() == 0) {
                                Toast.makeText(getActivity(), "No data found!", Toast.LENGTH_SHORT).show();
                            } else {
                                showProductList();

                            }
                        }


                    }

                }

            }

        });

        LinkedHashMap<String, Object> requestdata = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        try {
            //Constants.contactTypeIDForDealer
            requestdata.put(Constants.CONTACTID, dealerId);
            requestdata.put(Constants.username, passworddetails.getString("username", ""));
            requestdata.put(Constants.password, passworddetails.getString("password", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_Sale);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "GetProductList";
        String soapcomponent = getResources().getString(R.string.soapcomponent_sale);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();


    }


    private void showProductList() {

        showSalelist.clear();
        showSalelist.addAll(salelist);
        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.mannual_part_no_search_dialog_new, null);

        EditText searchpartNo = (EditText) view.findViewById(R.id.et_search);
        RecyclerView list_part_no = (RecyclerView) view.findViewById(R.id.list_part_no);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list_part_no.setItemViewCacheSize(showSalelist.size());
        list_part_no.setLayoutManager(mLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setView(view);
        arrayAdapter = new PartCustomAlertAdapterNew(getActivity(), R.layout.am_manual_part_no_shell_new, showSalelist);
        list_part_no.setAdapter(arrayAdapter);
        /*list_part_no.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long arg3) {
                // TODO Auto-generated method stub

                Log.d(">>>","item clicked");

                if(showSalelist.get(position).isChecked()) {
                    showSalelist.get(position).setChecked(false);
                    Log.d(">>>","Checked");

                }else {
                    Log.d(">>>","UNChecked");
                    showSalelist.get(position).setChecked(true);
                }
                arrayAdapter.notifyDataSetChanged();
                Log.d(">>>","notify");
                //String product = showSalelist.get(position).getCode();
                //Toast.makeText(getActivity(), product, Toast.LENGTH_LONG).show();
               // partNo.setText(product);
               // addingVlaueForList();


            }

        });*/


        searchpartNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                if (DLR_Mannual_Order_New.this.arrayAdapter != null)
                    DLR_Mannual_Order_New.this.arrayAdapter.getFilter().filter(arg0);
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

        builder.setPositiveButton(
                "ADD",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderList.clear();
                        for (int i = 0; i < showSalelist.size(); i++) {
                            if (showSalelist.get(i).getOrderQty() != null && !showSalelist.get(i).getOrderQty().isEmpty()) {

                                AM_DealerManualOrder order = new AM_DealerManualOrder();

                                order.setProductCode(showSalelist.get(i).getCode());
                                order.setPending(showSalelist.get(i).getPending_order());
                                // order.setGit(git_position.getText().toString());
                                order.setInventory(showSalelist.get(i).getInventory());
                                order.setQuantity(showSalelist.get(i).getOrderQty());
                                order.setProductId(showSalelist.get(i).getProductID());
                                order.setDescription(showSalelist.get(i).getDescription());
                                order.setSku(showSalelist.get(i).getDealerSKU());

                                orderList.add(order);

                            }

                        }

                        mManualAdapter.notifyDataSetChanged();
                        dialog.dismiss();
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


    private void GetMappedGeographyForLogin(final String contactType, final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId) {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading  Details..", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {
                    arealist.clear();
                    arealist = new FetchingdataParser().getarealistparserDealerCode(response.toString());
                    if (arealist.size() == 0) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.message4) + "4", Toast.LENGTH_SHORT).show();
                    } else {

                        for (HashMap<String, Object> entry : arealist) {

                            String geoids = (String) entry.get(Constants.GeoID);
                            String geonames = (String) entry.get(Constants.DealerCode);


                            if (geoids != null && geonames != null) {

                                if (geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)) {
                                    if (!dealerIDList.contains(geoids))
                                        dealerIDList.add(geoids);
                                    if (!dealerCodeList.contains(geonames))
                                        dealerCodeList.add(geonames);
                                }


                            }
                        }


                        if (geoName.equalsIgnoreCase(Constants.GEOCODE_DLR) && contactType.equalsIgnoreCase("AM")) {
                            ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dealerCodeList);
                            countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            cust_code_spinner.setAdapter(countryAdapter);
                        } else {

                            try {
                                DEALERCODE = dealerIDList.get(1);
                                //GetDealerDetails(countryid.get(1));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }

                } else if (response == null) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error4), Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        try {
            /*
             * Function GetGeographyByLogin
             * (ByVal ContactType As String, ByVal GeoType As String, ByVal GeoID As String,
             * ByVal ContactID As String, ByVal StateID As String, ByVal CityID As String,
             * ByVal UserID As String, ByVal Password As String) As String
             */
            request.put("ContactType", contactType);
            request.put(Constants.GeoName, geoName);
            request.put("GeoID", geoId);
            request.put("ContactID", ContactID);
            request.put(Constants.stateID, stateId);
            request.put(Constants.cityID, cityId);
            request.put(Constants.username, passworddetails.getString("username", ""));
            request.put(Constants.password, passworddetails.getString("password", ""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_mis_android);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "GetGeographyByLogin";
        String soapcomponent = getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();
    }


    protected void GetDealerDetails(String dealerId) {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {

                    ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getAm_Cus_City_Name(response.toString());
                    if (itemQuantityValueList != null) {
                        if (itemQuantityValueList.size() != 0) {

                            cust_city = (EditText) rootView.findViewById(R.id.mannual_city);
                            cust_name = (EditText) rootView.findViewById(R.id.mannual_customer_name);
                            cust_city.setText(itemQuantityValueList.get(0).get(Constants.city));
                            cust_name.setText(itemQuantityValueList.get(0).get(Constants.DEALERNAME));

                        }


                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.message4) + "4", Toast.LENGTH_SHORT).show();

                    }

                } else if (response == null) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error4), Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences("PASSWORD", MODE_PRIVATE);


        try {

            //GetDealerDetails(ByVal Type As String, ByVal DealerID As String, ByVal UserID As String, ByVal Password As String)
            request.put("External", "External");
            request.put("DealerID", dealerId);
            request.put(Constants.username, passworddetails.getString(Constants.ID, ""));
            request.put(Constants.password, passworddetails.getString("password", ""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.webService);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "GetDealerDetails";
        String soapcomponent = getResources().getString(R.string.soapcomponent);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }


  /*  public void addingVlaueForList() {

        for (int i = 0; i < showSalelist.size(); i++) {

            if (showSalelist.get(i).getCode().equalsIgnoreCase(partNo.getText().toString())) {

                pending_position.setText(salelist.get(i).getCode());
               // git_position.setText(salelist.get(i).getGitQuantity());
                desc_position.setText(salelist.get(i).getInventory());
                productID = (salelist.get(i).getProductID());
                sku_position.setText(salelist.get(i).getDealerSKU());

            }

        }

    }*/


   /* private AM_DealerManualOrder CaptureData(HashMap<String, String> productMap) {

        AM_DealerManualOrder order = new AM_DealerManualOrder();
        //order.setDealerName(partNo.getText().toString());
        order.setProductCode(partNo.getText().toString());
        order.setPending(pending_position.getText().toString());
       // order.setGit(git_position.getText().toString());
        order.setInventory(desc_position.getText().toString());
        order.setQuantity(quantity_position.getText().toString());
        order.setProductId(productID);
        //order.setUnitPrice(Double.parseDouble(productMap.get(Constants.UNITPRICE)));
        //order.setDiscountpercentage(Double.parseDouble(productMap.get(Constants.DISCOUNTPERCENTAGE)));
        //order.setProductId(productMap.get(Constants.ID));
        //order.setDealerID(getDealerId());
        return order;
    }*/

 /*   private void clearScreen() {

        partNo.setText("");
        pending_position.setText("");
      //  git_position.setText("");
        desc_position.setText("");
        quantity_position.setText("");
        sku_position.setText("");


    }*/


    public boolean validation() {
        boolean isValid = true;
        String errMsg = getResources().getString(R.string.error3);

       /* if (quantity_position.getText().toString().length() == 0) {
            quantity_position.setError(getResources().getString(R.string.quantity));
            //errMsg = errMsg.concat(" " +);
            isValid = false;
        }*/

        if (partNo.getText().toString().length() == 0 || partNo.getText().toString().length() < 10) {
            partNo.setError(getResources().getString(R.string.partno));
            isValid = false;
        }

        if (!isValid) {
            Toast.makeText(getActivity(), errMsg, Toast.LENGTH_LONG).show();
        }
        return isValid;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        // TODO Auto-generated method stub


        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //do your work here
                orderList.remove(position);
                mManualAdapter.notifyDataSetChanged();
                dialog.dismiss();


            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();


    }


    private void availabilityCheck(String dealerId, String detailsData) {
        // TODO Auto-generated method stub

        ArrayList<AvailableManualOrder> availableManualOrderList = new ArrayList<>();

        for(int i = 0;i<=8;i++) {
            AvailableManualOrder availableManualOrder1 = new AvailableManualOrder();
            availableManualOrder1.setProductID("As453");

            if(i%2==0) {
                availableManualOrder1.setAvailability("Depot");
                availableManualOrder1.setAvailableQty("6");
            }else  {
                availableManualOrder1.setAvailability("Plant");
                availableManualOrder1.setAvailableQty("4");
            }


            availableManualOrder1.setQty("110");
            availableManualOrderList.add(availableManualOrder1);
        }

        availableArrayList(availableManualOrderList, showSalelist);
        showAfterAvailabilityPO(availableManualOrderList);

      /*  GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, getResources().getString(R.string.uploaddata), new GetDataCallBack() {

            @Override
            public void processResponse(Object result) {
                // TODO Auto-generated method stub

                if (result == null) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<AvailableManualOrder> availableManualOrderList = FetchingdataParser.getAvailableManualOrder(result.toString());
                    if (availableManualOrderList == null) {
                        AlertDialog.Builder errordialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                        errordialog.setTitle("Error!");
                        errordialog.setMessage("Data is Not available");
                        errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = errordialog.create();
                        dialog.show();
                    } else {
                        if (availableManualOrderList.size() == 0) {
                            Toast.makeText(getActivity(), "No data found!", Toast.LENGTH_SHORT).show();
                        } else {
                            availableArrayList(availableManualOrderList, showSalelist);
                            showAfterAvailabilityPO(availableManualOrderList);
                        }
                    }

                }

            }

        });

        LinkedHashMap<String, Object> requestdata = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        try {
            requestdata.put(Constants.DEALERID_1, dealerId);
            requestdata.put(Constants.jsonstring, detailsData);
            requestdata.put(Constants.username, passworddetails.getString("username", ""));
            requestdata.put(Constants.password, passworddetails.getString("password", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_Sale);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "SaveDealerManualOrder";
        String soapcomponent = getResources().getString(R.string.soapcomponent_sale);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();*/

    }

    TextView tv_total_qty;
    TextView tv_amount;
    private void showAfterAvailabilityPO(final ArrayList<AvailableManualOrder> availableManualOrderList) {


        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.mannual_order_after_availability_dialog, null);

        RecyclerView list_part_no = (RecyclerView) view.findViewById(R.id.list_part_no);
        CheckBox ch_order_from_plant = view.findViewById(R.id.ch_order_from_plant);
        Button bt_order_all = view.findViewById(R.id.bt_order_all);
         tv_total_qty = view.findViewById(R.id.tv_total_qty);
         tv_amount = view.findViewById(R.id.tv_amount);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list_part_no.setItemViewCacheSize(availableManualOrderList.size());
        list_part_no.setLayoutManager(mLayoutManager);

        final  ArrayList<AvailableManualOrder> finalOrderList = new ArrayList<>();

        finalOrderList.addAll(availableManualOrderList);

        final AvailableMenualOrderAlertAdapter arrayAdapter = new AvailableMenualOrderAlertAdapter(getActivity(), R.layout.manual_order_after_availability_cell, finalOrderList);
        list_part_no.setAdapter(arrayAdapter);


        ch_order_from_plant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                finalOrderList.clear();

               if( !buttonView.isChecked()){

                   for (AvailableManualOrder orderBydepotItem : availableManualOrderList) {
                       if (!orderBydepotItem.getAvailability().equalsIgnoreCase("Plant")) {
                           finalOrderList.add(orderBydepotItem);

                       }
                   }
                   arrayAdapter.notifyDataSetChanged();
                   updateQtyPrice(finalOrderList);

               } else{
                   finalOrderList.addAll(availableManualOrderList);
                   arrayAdapter.notifyDataSetChanged();
                   updateQtyPrice(finalOrderList);
               }

            }
        });


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

       /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setView(view);*/



        /*boolean isAllItemAvailable = false;
        for (AvailableManualOrder availableManualOrder : availableManualOrderList) {
            isAllItemAvailable = availableManualOrder.getAvailability().equalsIgnoreCase("A") ? true : false;
        }

        if (isAllItemAvailable) {
            bt_order.setEnabled(true);
            bt_order.setAlpha(0.0f);

        } else {
            bt_order.setEnabled(false);
            bt_order.setAlpha(0.5f);
        }*/


        /*bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalOrderList.size() > 0) {

                    String jsondata = null;
                    try {
                        jsondata = getJsonStringForAvailableManualOrder(finalOrderList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                    }
                    uploadManualOrder(false,"order", DEALERCODE, jsondata);
                } else {
                    Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                }


                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();
            }
        });*/

      /*  bt_order_by_depot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (availableManualOrderList.size() > 0) {

                    ArrayList<AvailableManualOrder> orderBydepot = new ArrayList<>();
                    for (AvailableManualOrder orderBydepotItem : availableManualOrderList) {
                        if (orderBydepotItem.getAvailability().equalsIgnoreCase("A")) {
                            orderBydepot.add(orderBydepotItem);
                        }
                    }


                    String jsondata = null;
                    try {
                        jsondata = getJsonStringForAvailableManualOrder(availableManualOrderList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                    }


                    uploadManualOrder(false,"orderByDepot", DEALERCODE, jsondata);
                } else {
                    Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                }

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();
            }
        });*/

        bt_order_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (availableManualOrderList.size() > 0) {

                    ArrayList<AvailableManualOrder> orderAllPartOne = new ArrayList<>();

                    for (AvailableManualOrder orderallItem : availableManualOrderList) {
                        if (orderallItem.getAvailability().equalsIgnoreCase("A")) {
                            orderAllPartOne.add(orderallItem);
                        }else{

                            orderAllPartTwo.add(orderallItem) ;
                        }
                    }

                    String jsondata = null;
                    try {
                        jsondata = getJsonStringForAvailableManualOrder(orderAllPartOne);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                    }
                    uploadManualOrder(true,"orderAll", DEALERCODE, jsondata);
                } else {
                    Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                }

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();
            }
        });

        // dialog = builder.show();

    }

    private void updateQtyPrice(ArrayList<AvailableManualOrder> finalOrderList){

        double amount;
        int quantity=0;

        for (AvailableManualOrder availableManualOrder:finalOrderList){
            quantity= quantity+Integer.parseInt(availableManualOrder.getQty());
        }

        tv_total_qty.setText(""+quantity);
    }


    private void uploadManualOrder(final boolean isRequestSecondTime, String orderType, String dealerId, String detailsData) {
        // TODO Auto-generated method stub


        GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, getResources().getString(R.string.uploaddata), new GetDataCallBack() {

            @Override
            public void processResponse(Object result) {
                // TODO Auto-generated method stub
                SoapObject responce = null;
                try {

                    responce = (SoapObject) result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                AlertDialog.Builder errordialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                if (responce == null) {
                    errordialog.setTitle("Manual Order");
                    if (result != null) {
                        if (result.toString().contains("@@ERROR --->"))
                            errordialog.setMessage(result.toString().replace("@@ERROR --->", ""));
                        else
                            errordialog.setMessage(result.toString());
                    } else {
                        errordialog.setMessage(getResources().getString(R.string.error4));
                    }

                } else {

                    if(isRequestSecondTime){
                        String jsondata = null;
                        try {
                            jsondata = getJsonStringForAvailableManualOrder(orderAllPartTwo);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(getActivity(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
                        }
                        uploadManualOrder(false,"orderAll", DEALERCODE, jsondata);

                    }

                    // errordialog.setMessage(getResources().getString(R.string.message5));


                }


                errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        dialog.dismiss();

                        // clearScreen();
                        orderList.clear();
                        mManualAdapter.notifyDataSetChanged();
                    }
                });

                if(!isRequestSecondTime) {
                    AlertDialog dialog = errordialog.create();
                    dialog.show();
                }

            }

        });

        LinkedHashMap<String, Object> requestdata = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        try {
            requestdata.put(Constants.DEALERID_1, dealerId);
            requestdata.put("OrderType", orderType);
            requestdata.put(Constants.totalAmount, "0");
            requestdata.put(Constants.discount, "0");
            requestdata.put(Constants.orderamount, "0");
            requestdata.put(Constants.jsonstring, detailsData);
            requestdata.put(Constants.username, passworddetails.getString("username", ""));
            requestdata.put(Constants.password, passworddetails.getString("password", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_Sale);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "SaveDealerManualOrder";
        String soapcomponent = getResources().getString(R.string.soapcomponent_sale);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();

    }

    private String getJsonStringForManualOrder(ArrayList<AM_DealerManualOrder> requestBean) {


        StringBuilder dataString = new StringBuilder();
        dataString.append("[");


        for (int i = 0; i < requestBean.size(); i++) {
            AM_DealerManualOrder am_dealerDealerSale = requestBean.get(i);
            dataString.append("{");
            dataString.append("'");
            dataString.append("ProductID");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(am_dealerDealerSale.getProductId());
            dataString.append("'");
            dataString.append(",");


            dataString.append("'");
            dataString.append("Qty");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(am_dealerDealerSale.getQuantity());
            dataString.append("'");
            dataString.append(",");

            dataString.append("'");
            dataString.append("UnitPrice");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append("0");
            dataString.append("'");
            dataString.append("}");
            dataString.append(",");


        }
        dataString.append("]");
        Log.i("Server data String ", dataString.toString());

        return dataString.toString();
    }


    private String getJsonStringForAvailableManualOrder(ArrayList<AvailableManualOrder> requestBean) {


        StringBuilder dataString = new StringBuilder();
        dataString.append("[");


        for (int i = 0; i < requestBean.size(); i++) {
            AvailableManualOrder am_dealerDealerSale = requestBean.get(i);
            dataString.append("{");
            dataString.append("'");
            dataString.append("ProductID");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(am_dealerDealerSale.getProductID());
            dataString.append("'");
            dataString.append(",");


            dataString.append("'");
            dataString.append("Qty");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(am_dealerDealerSale.getQty());
            dataString.append("'");
            dataString.append(",");

            dataString.append("'");
            dataString.append("UnitPrice");
            dataString.append("'");
            dataString.append(":");
            dataString.append("'");
            dataString.append(am_dealerDealerSale.getAvailability());
            dataString.append("'");
            dataString.append("}");
            dataString.append(",");


        }
        dataString.append("]");
        Log.i("Server data String ", dataString.toString());

        return dataString.toString();
    }

    private void availableArrayList(ArrayList<AvailableManualOrder> availableManualOrderList, ArrayList<DealerDealerSale> showSalelist) {


        for (AvailableManualOrder availableManualOrder : availableManualOrderList) {
            // Loop arrayList1 items
            for (DealerDealerSale dealerDealerSale : showSalelist) {
                if (dealerDealerSale.getProductID().equalsIgnoreCase( availableManualOrder.getProductID())) {
                    availableManualOrder.setPending_order(dealerDealerSale.getPending_order());
                    availableManualOrder.setDescription(dealerDealerSale.getDescription());
                }
            }

        }
    }


}
