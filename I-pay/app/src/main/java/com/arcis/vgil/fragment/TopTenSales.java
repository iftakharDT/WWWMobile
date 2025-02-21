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
import com.arcis.vgil.adapter.CommonAdapterTopTen;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.CommonTopTenModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/21/2017.
 */
public class TopTenSales extends Fragment {

    private ListView mlistView;
    CommonAdapterTopTen adapter;
    ArrayList<CommonTopTenModel> amCallPannerList;
    private String type;

    public TopTenSales() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.top_ten_dealer, container, false);
        mlistView = (ListView)rootView. findViewById(R.id.listView);
        getSaleData();

        return rootView;
    }

    private void getSaleData() {
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

                        amCallPannerList = FetchingdataParser.getCommonTopTenResponce(response.toString());

                        if (amCallPannerList!=null) {
                            if (amCallPannerList.size()!=0) {
                                adapter  =new CommonAdapterTopTen(getActivity(), R.layout.top_ten_list_shell, amCallPannerList);
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
//Function SpiceTopTenDealerWiseMIS(ByVal ContactTypeID As String,
// ByVal ContactID As String, ByVal MISType As String,
// ByVal UserID As String, ByVal Password As String) As String


        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =  getActivity().getSharedPreferences("PASSWORD",MODE_PRIVATE);

        try {
            request.put("ContactTypeID",passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put("ContactID",passworddetails.getString(Constants.USERID, ""));
            request.put("MISType","SALE");
            request.put("IsPartWIse","0");
            request.put(Constants.username,passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.password,passworddetails.getString(Constants.password, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="SpiceTopTenDealerWiseMIS";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }

}
