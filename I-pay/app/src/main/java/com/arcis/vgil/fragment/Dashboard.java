package com.arcis.vgil.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.DashboardAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DashboardModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/10/2017.
 */

public class Dashboard extends Fragment {
    private ListView mlistView;
    DashboardAdapter adapter;
    ArrayList<DashboardModel> amCallPannerList;

    public Dashboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);
        mlistView = (ListView)rootView. findViewById(R.id.listView);
        GetDashboard();
        return rootView;
    }

    private void GetDashboard() {
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

                        amCallPannerList = FetchingdataParser.getDashboardList(response.toString());

                        if (amCallPannerList!=null) {
                            if (amCallPannerList.size()!=0) {
                                adapter  =new DashboardAdapter( getActivity(), R.layout.dashboard_cell, amCallPannerList);
                                mlistView.setAdapter(adapter);
                            }
                            else {
                                Toast.makeText( getActivity(),
                                        "NO DATA",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                        else {

                            Toast.makeText( getActivity(),
                                    "NO DATA",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =  getActivity().getSharedPreferences("PASSWORD",
                MODE_PRIVATE);

        try {
            request.put(Constants.contactType,passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put("ContactID",passworddetails.getString(Constants.USERID, ""));



        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="ShowAMDashboard";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }


}
