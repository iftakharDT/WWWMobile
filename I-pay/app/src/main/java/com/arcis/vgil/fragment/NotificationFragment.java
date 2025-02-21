package com.arcis.vgil.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.NotiFicationAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.NotificationListModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 2/17/2017.
 */

public class NotificationFragment extends Fragment {

    private ListView notification;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<NotificationListModel> amNotificationList;
    private NotiFicationAdapter notiFicationAdapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        notification= (ListView) rootView.findViewById(R.id.notification);
        mSwipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        GetNotificationFormData();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetNotificationFormData();
            }
        });

        return rootView;
    }


    private void GetNotificationFormData() {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
                getActivity(), ProgressDialog.STYLE_SPINNER,
                "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {

                if (response == null) {
                    Toast.makeText( getActivity(),
                            getResources().getString(R.string.error4),
                            Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }


                else {
                    amNotificationList = FetchingdataParser.getNotificationResponce(response.toString());
                    if (amNotificationList!=null) {
                        if (amNotificationList.size()!=0) {
                            notiFicationAdapter  =new NotiFicationAdapter(getActivity(), R.layout.notification_shell, amNotificationList);


                            notiFicationAdapter.setdeleteToListener(new NotiFicationAdapter.deleteToListenerNotification() {
                                @Override
                                public void onDeleteToListenerNotification(int position) {
                                //    Function DeleteNotificationFormData(ByVal ContactID As String,
                                    // ByVal ContactTypeID As String, ByVal Message As String,
                                    // ByVal DeletedBy As String,
                                    // ByVal UserID As String, ByVal Password As String) As String
                                    DeleteNotificationFormData(amNotificationList.get(position).getMessage());
                                }
                            });
                            notification.setAdapter(notiFicationAdapter);
                            notiFicationAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            Toast.makeText( getActivity(),
                                    "NO DATA",
                                    Toast.LENGTH_SHORT).show();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }

                    }
                    else {

                        Toast.makeText( getActivity(),
                                "NO DATA",
                                Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }






            }
        });
      //  Function GetNotificationFormData(ByVal ContactID As String,
        // ByVal ContactTypeID As String,
        // ByVal UserID As String, ByVal Password As String) As String


        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =  getActivity().getSharedPreferences("PASSWORD",MODE_PRIVATE);

        try {
            request.put("ContactID",passworddetails.getString(Constants.USERID,""));
            request.put("ContactTypeID",passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetNotificationFormData";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

    }

    private void DeleteNotificationFormData(String message) {

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
                getActivity(), ProgressDialog.STYLE_SPINNER,
                "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {

                if (response == null) {
                    Toast.makeText( getActivity(),
                            getResources().getString(R.string.error4),
                            Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else {
                    if (response != null) {
                        GetNotificationFormData();
                    }

                }






            }
        });
        //    Function DeleteNotificationFormData(ByVal ContactID As String,
        // ByVal ContactTypeID As String, ByVal Message As String,
        // ByVal DeletedBy As String,
        // ByVal UserID As String, ByVal Password As String) As String

        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =  getActivity().getSharedPreferences("PASSWORD",MODE_PRIVATE);

        try {
            request.put("ContactID",passworddetails.getString(Constants.USERID,""));
            request.put("ContactTypeID",passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put("Message",message);
            request.put("DeletedBy",passworddetails.getString(Constants.USERID,""));

            request.put(Constants.username, passworddetails.getString(Constants.ID,""));
            request.put(Constants.password,passworddetails.getString("password",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="DeleteNotificationFormData";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }


}
