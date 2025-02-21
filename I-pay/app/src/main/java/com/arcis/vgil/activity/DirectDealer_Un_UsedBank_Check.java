package com.arcis.vgil.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DirectDealer_Un_UsedBank_Check extends Fragment {
	protected List<HashMap<String,Object>> checkList;
	ListView lv;
	SimpleAdapter dataadapter;
	private FragmentActivity mActivity;
	View view;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view==null) {
			setCurrentContext(getActivity());
		    view = inflater.inflate(R.layout.direct_dealer_blank_check,  container, false);
			
			
		}
		
		
		return view;
	}
	
	/**
	 * Set Current Context;
	 * @param activity
	 */
	private void setCurrentContext(FragmentActivity activity) {
		// TODO Auto-generated method stub
		mActivity = activity;
	}
	/**
	 * Get Current Context of the Activity;
	 * @return
	 */
	private FragmentActivity getCurrentContext(){
		return mActivity;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		lv=(ListView) view.findViewById(R.id.listView1);
		directDealerBlank_Un_Used_Check();
	}
	
	

	private void directDealerBlank_Un_Used_Check() {

		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<HashMap<String, String >> checkList = FetchingdataParser.getdirectDealerBlank_Un_Used_Check(result.toString());
					if(checkList==null){
						AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
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
						if(checkList.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
						       dataadapter  = new SimpleAdapter(getCurrentContext(), checkList, R.layout.directdealerblank_un_used_check_shell,
					        		new String[]{Constants.ChequeNo,Constants.BankName,Constants.BranchName,Constants.city,Constants.AccountNo},
					        		new int[]{R.id.text2,R.id.text4,R.id.text6,R.id.text8,R.id.text10});
					                lv.setAdapter(dataadapter);
						
						}
					}

				}

			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			
			requestdata.put(Constants.dealerid, passworddetails.getString(Constants.DEALERID, ""));
			requestdata.put("Usage","0" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerBlankCheque_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	
		
		
		
	}


	@Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }


}
