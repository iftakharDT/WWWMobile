package com.arcis.vgil.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.adapter.AMOneLineSummaryAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AMOneLineSummaryModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jaim on 3/10/2017.
 */

public class AmOneLineSummaryAll extends Fragment {

    private TextView tittle_activity,user_name;
    private ListView listview_oneline_dealer;
    private AMOneLineSummaryAdapter dealerOneLineSummaryAdapter;
    private SharedPreferences sharedPreferences;
    private EditText et_search;

    public AmOneLineSummaryAll() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // run the code making use of getActivity() from here
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.am_oneline_summary, container, false);
        user_name=(TextView) rootView.findViewById(R.id.user_name);
        tittle_activity=(TextView)rootView. findViewById(R.id.tittle_activity);
        listview_oneline_dealer=(ListView)rootView. findViewById(R.id.listview_oneline_dealer);
        et_search=(EditText)rootView. findViewById(R.id.et_search);

        sharedPreferences= getContext().getSharedPreferences("PASSWORD", MODE_PRIVATE);
        user_name.append(":  "+ sharedPreferences.getString("contactname", ""));
        tittle_activity.setText(getResources().getString(R.string.am_oneline_summary));
        tittle_activity.setVisibility(View.GONE);
        et_search.setVisibility(View.GONE);

        DealerOneLineSummaryDetail();

        return rootView;
    }
    private void DealerOneLineSummaryDetail() {
        // TODO Auto-generated method stub

        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getActivity(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {
                if(response!=null){

                    ArrayList<AMOneLineSummaryModel> dealerOneLineSummaryList = FetchingdataParser.getAM_ONeLineSummay(response.toString());
                    if(dealerOneLineSummaryList!=null ){
                        if (dealerOneLineSummaryList.size()!=0) {
                            if (getActivity() != null) {
                                // Code goes here.
                                dealerOneLineSummaryAdapter=new AMOneLineSummaryAdapter(getActivity(), R.layout.am_oneline_summary_shell, dealerOneLineSummaryList);
                                listview_oneline_dealer.setAdapter(dealerOneLineSummaryAdapter);
                            }

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

            //Function DealerOneLineSummary(ByVal ContactTypeID As String, ByVal ContactID As String)

            request.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID,""));
            request.put("ContactID", passworddetails.getString(Constants.USERID,""));




        } catch (Exception e) {
            e.printStackTrace();
        }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="AMOneLineSummary";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
