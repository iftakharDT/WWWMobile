package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
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

public class AmTerettoryCustomersList extends BaseActivity {

	ListView mlistView;
	String contType;
	String StateId;
	String CityId;
	String AreaID;
	String contactType;
	String MobileNo;
	SimpleAdapter adapter;

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.am_teretorry_customer_list);
		
		turnGPSOn();
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		if (b != null) {

			contType = b.getString("CONTACTTYPE");
			StateId = b.getString("stateID");
			CityId = b.getString("cityID");
			AreaID = b.getString("areaID");
			MobileNo = b.getString("MobileNo");
			contactType = b.getString(Constants.customerType);

		}

		mlistView = (ListView) findViewById(android.R.id.list);
		mlistView.setOnTouchListener(listTouchListener);
		mlistView.setOnItemClickListener(this);
		GetAMTerettoryCustomers();
	}

	

	private void GetAMTerettoryCustomers() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
				getCurrentContext(), ProgressDialog.STYLE_SPINNER,
				"Loading...", new GetDataCallBack() {
					@Override
					public void processResponse(Object response) {
						ArrayList<HashMap<String, String>> amTerretoryList;
						if (response == null) {
							Toast.makeText(getCurrentContext(),
									getResources().getString(R.string.error4),
									Toast.LENGTH_SHORT).show();
						} else {
							if (response != null) {
								amTerretoryList = new FetchingdataParser()
										.getAmTerettoryCustomersList(response
												.toString());
								if (amTerretoryList.size() == 0) {
									Toast.makeText(getCurrentContext(),
											response.toString(),
											Toast.LENGTH_SHORT).show();
								} else {

SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(),amTerretoryList,R.layout.visit_log_customer_cell,
new String[] {Constants.FirmName, Constants.NAME,Constants.MOBILE_NUMBER,Constants.state,Constants.city,Constants.area,Constants.Planned },
new int[] {R.id.firm_name, R.id.text1, R.id.text2,R.id.text3, R.id.text4,R.id.text5,R.id.planned });
									mlistView.setAdapter(adapter);

								}
							}
						}
					}
				});

		LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails = getSharedPreferences("PASSWORD",
				MODE_PRIVATE);

		try {
			request.put(Constants.AM_ID,
					passworddetails.getString(Constants.USERID, ""));
			request.put(Constants.stateID, StateId);
			request.put(Constants.cityID, CityId);
			request.put(Constants.areaID, AreaID);
			request.put(Constants.MOBILE_NUMBER, MobileNo);
			request.put(Constants.customerType, contactType);
			request.put(Constants.username,
					passworddetails.getString(Constants.ID, ""));
			request.put(Constants.password,
					passworddetails.getString("password", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress = getResources().getString(R.string.ipaddress);
		String webService = getResources().getString(R.string.webService);
		String nameSpace = getResources().getString(R.string.nameSpace);
		String methodName = "GetAMTerettoryCustomer";
		String soapcomponent = getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName,
				soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		HashMap<String, String> rowData = (HashMap<String, String>) arg0.getItemAtPosition(arg2);

		Intent intent = new Intent(getCurrentContext(),
				Customer_visit_metting_Activity.class);
		intent.putExtra("CONTACTTYPE", contType);
		intent.putExtra(Constants.ID, rowData.get(Constants.ID));
		intent.putExtra(Constants.NAME, rowData.get(Constants.NAME));
		startActivity(intent);
	}
	
	
	private void turnGPSOn(){
	    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(!provider.contains("gps")){ //if gps is disabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3"));
	        sendBroadcast(poke);
	    }
	}

	
	
	
}
