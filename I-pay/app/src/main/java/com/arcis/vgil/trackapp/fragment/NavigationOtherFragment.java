package com.arcis.vgil.trackapp.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.activity.GetDataCallBack;
import com.arcis.vgil.trackapp.activity.TerettoryCustomersList;
import com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork;
import com.arcis.vgil.trackapp.data.Constants;
import com.arcis.vgil.trackapp.dialog.OtherCallDialog;
import com.arcis.vgil.trackapp.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;


public class NavigationOtherFragment extends Fragment {

    private static final String TAG = NavigationOtherFragment.class.getSimpleName();

private EditText et_comment;
private Spinner sp_agenda;
private ImageView iv_start_call;
private String combinedDateAndTime;
private String visitLogID;
    private static final String CALL_BEGIN_DIALOG_TAG = "Call_dialog_tag";


    String otherAgendaid;



    public NavigationOtherFragment() {
        // Required empty public constructor
    }

    public static NavigationOtherFragment newInstance(String param1, String param2) {
        NavigationOtherFragment fragment = new NavigationOtherFragment();
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
        View view = inflater.inflate(R.layout.fragment_other, container, false);

        et_comment = view.findViewById(R.id.et_comment);
        sp_agenda = view.findViewById(R.id.sp_agenda);
        iv_start_call = view.findViewById(R.id.iv_start_call);

        sp_agenda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                if (position != 0) {
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
                    otherAgendaid = data.get(Constants.ID);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        iv_start_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate())
                gettingSystemTime();
            }
        });
        getOtherAgenda();
        return view;
    }


    private void getOtherAgenda() {
        // TODO Auto-generated method stub


        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {

                    ArrayList<HashMap<String, String>> agendaList = FetchingdataParser.getAgenda(response.toString());
                    if (agendaList != null) {
                        SimpleAdapter segmentAdapter = new SimpleAdapter(getActivity(), agendaList, android.R.layout.simple_spinner_item, new String[]{Constants.Description}, new int[]{android.R.id.text1});
                        segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        sp_agenda.setAdapter(segmentAdapter);

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

            request.put(Constants.username, passworddetails.getString("username", ""));
            request.put(Constants.password, passworddetails.getString("password", ""));


        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress = getResources().getString(R.string.ipaddress);
        String webService = getResources().getString(R.string.Webservice_mis_android);
        String nameSpace = getResources().getString(R.string.nameSpace);
        String methodName = "DailyVisitTask";
        String soapcomponent = getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

    private void gettingSystemTime() {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if (response != null) {

                    ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.gettingSystemTime(response.toString());
                    if (itemQuantityValueList != null) {
                        if (itemQuantityValueList.size() != 0) {

                            combinedDateAndTime = itemQuantityValueList.get(0).get(Constants.combinedDateAndTime);
                            visitLogID = itemQuantityValueList.get(0).get(Constants.visitLogID);
                            OtherCallDialog dialog = OtherCallDialog.newInstance(NavigationOtherFragment.this,combinedDateAndTime);
                            dialog.setCancelable(false);
                            dialog.show(getActivity().getSupportFragmentManager(), CALL_BEGIN_DIALOG_TAG);
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

            request.put(Constants.CONTACT_ID, passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.CUSTOMER_ID, "0");
            request.put(Constants.CUSTOMER_TYPE_ID, "0");
            request.put(Constants.OTHER_ACTIVITY_ID, otherAgendaid);
            request.put(Constants.LATTITUDE, ""+((TerettoryCustomersList)getActivity()).getLatitude());
            request.put(Constants.LONGITUDE, ""+((TerettoryCustomersList)getActivity()).getLongitude());

            request.put(Constants.username, passworddetails.getString(Constants.ID, ""));
            request.put(Constants.password, passworddetails.getString("password", ""));


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


    public void saveAMTerettoryCustomersMeetingLog(final boolean fromCountDownTimer) {

        if (((TerettoryCustomersList)getActivity()).isLatLongcaptured()) {


            GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER, "Uploading data...", new GetDataCallBack() {
                @Override
                public void processResponse(Object response) {

                    //{ 'Root': { 'AMVisitLog': [{'VisitLogID': '130'}] }}


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
                                    errordialog.setMessage("Call Ended Successfully .");
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
                request.put(Constants.VISIT_LOG_ID, visitLogID);
                request.put(Constants.CONTACT_ID, passworddetails.getString(Constants.USERID, ""));
                request.put(Constants.CONTACT_TYPE,"0");
                request.put(Constants.RE_OPEN_DATE, combinedDateAndTime);

                if (et_comment.getText().toString().length()>0) {
                    request.put(Constants.REMARK,et_comment.getText().toString());
                }
                else {
                    request.put(Constants.REMARK,"");
                }
                request.put(Constants.IS_RE_OPEN,"0");
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



        } else {
            if (!((TerettoryCustomersList)getActivity()).getRequestingLocationUpdates()) {
                ((TerettoryCustomersList)getActivity()).startLocationClick();
            }

        }


    }

    public boolean flag = true;
    private boolean isValidate() {
        // TODO Auto-generated method stub

        if (sp_agenda.getSelectedItemPosition() == 0) {
            flag = false;
            Toast.makeText(getActivity(),
                    "Please select Other Agenda",
                    Toast.LENGTH_LONG).show();

        }

        return flag;
    }


}
