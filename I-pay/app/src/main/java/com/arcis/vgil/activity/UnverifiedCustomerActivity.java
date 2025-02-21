/**
 * 
 */
package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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


/**
 * @author munim
 *
 */
public class UnverifiedCustomerActivity extends BaseActivity {


	private ListView mlistView;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.activity_unverified_customer);
		mlistView = (ListView)findViewById(android.R.id.list);
		mlistView.setOnItemClickListener(listclicklistener);
		setCurrentContext(this);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getUnverifiedCustomerList();
	}
	private void getUnverifiedCustomerList(){

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(UnverifiedCustomerActivity.this, ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> datalist = FetchingdataParser.getUnverifiedCutomerList(response.toString());
					if(datalist!=null){

						SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(), datalist, R.layout.unverified_customer_cell, new String[]{Constants.NAME,Constants.MOBILE_NUMBER,
							Constants.BankName,Constants.AccountNumber}, new int[]{R.id.position1,R.id.position2,
							R.id.position3,R.id.position4});
						mlistView.setAdapter(adapter);
					}
				}else if(response==null){

					Toast.makeText(UnverifiedCustomerActivity.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			request.put("AMID", passworddetails.getString("username", ""));
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetUnveriedContact_WithNoDocument";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();

	}

	OnItemClickListener listclicklistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			
			@SuppressWarnings("unchecked")
            HashMap<String , String> datamap = (HashMap<String, String>) arg0.getItemAtPosition(arg2);
			Bundle bundle = new Bundle();
			
			bundle.putString(Constants.ID, datamap.get(Constants.ID));
			bundle.putString(Constants.NAME, datamap.get(Constants.NAME));
			bundle.putString(Constants.MOBILE_NUMBER, datamap.get(Constants.MOBILE_NUMBER));
			bundle.putString(Constants.address, datamap.get(Constants.address));
			bundle.putString(Constants.contacttype, datamap.get(Constants.contacttype));
			bundle.putString(Constants.BankName, datamap.get(Constants.BankName));
			bundle.putString(Constants.AccountNumber, datamap.get(Constants.AccountNumber));
			bundle.putString(Constants.BranchName, datamap.get(Constants.BranchName));
			
			Intent intent  = new Intent(getCurrentContext(),DocumentImageCaptureActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};

}