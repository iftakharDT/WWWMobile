package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

public class CustomerTrendSearchResultActivity  extends BaseActivity{

	
	
	private ListView mlistView;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
	
		setContentView(R.layout.customertrend_search_result_layout);
		setCurrentContext(this);
		mlistView = (ListView)findViewById(android.R.id.list);
		mlistView.setOnItemSelectedListener(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getTrendReport();
	}
	
	private void getTrendReport(){
		
		
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {
			
			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				
				if(result!=null){
					
					ArrayList<HashMap<String , String>> trendList = FetchingdataParser.getTrendList(result.toString());
					if(trendList!=null){
						
						SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(), trendList, R.layout.customer_trend_list_celll, new String[]{Constants.DIRECTDEALERNAME,
							Constants.DIRECTDEALERCITY,Constants.DIRECTDEALERQUANTITY,Constants.DIRECTDEALERVALUE,
							Constants.RETAILERNAME,Constants.RETAILERCITY,Constants.RETAILERQUANTITY,Constants.RETAILERVALUE,
							Constants.MECHNAME,Constants.MECHCITY,Constants.MECHANICQUANTITYT,Constants.MECHVALUE}, new int[]{R.id.position2,R.id.position3,R.id.position4,R.id.position5,
							R.id.position6,R.id.position7,R.id.position8,R.id.position9,
							R.id.position10,R.id.position11,R.id.position12,R.id.position13,});
						mlistView.setAdapter(adapter);
					}else{
						Util.showToast(getCurrentContext(), getStringFromResource(R.string.message4), Toast.LENGTH_SHORT).show();
					}
				}else{
					Util.showToast(getCurrentContext(), getStringFromResource(R.string.error4), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		LinkedHashMap< String, Object> requestMap = BaseActivity.getRequestDataMap();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			requestMap.put(Constants.username, passworddetails.getString(Constants.ID,""));
			requestMap.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CustomersTrendMIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
		// TODO Auto-generated method stub
		super.onItemSelected(parent, view, position, id);
		
		
	}
}
