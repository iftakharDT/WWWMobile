package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CustomerTrendMISScreen2  extends BaseActivity{


	private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11;
	private ListView mlistView;
    public static final String REPORT_TYPE = "reporttype";
     
    @Override
	public  void inti()  {
		// TODO Auto-generated method stub
		setContentView(R.layout.customer_trend_mis_screen2);

		setCurrentContext(this);
		Bundle bundle = getIntent().getExtras();

		String geoData = null;
		txt1 = (TextView)findViewById(R.id.text1);
		
		geoData = bundle.getString(Constants.country);
		if(geoData!=null){
			txt1.setText(geoData);
		}else{
			findViewById(R.id.layout1).setVisibility(View.GONE);
		}
		

		txt2 = (TextView)findViewById(R.id.text2);
		
		geoData = bundle.getString(Constants.zone);
		if(geoData!=null){
			txt2.setText(geoData);
		}else{
			findViewById(R.id.layout2).setVisibility(View.GONE);
		}
		
		txt3 = (TextView)findViewById(R.id.text3);
		
		geoData = bundle.getString(Constants.state);
		if(geoData!=null){
			txt3.setText(geoData);
		}else{
			findViewById(R.id.layout3).setVisibility(View.GONE);
		}

		txt4 = (TextView)findViewById(R.id.text4);
		
		geoData = bundle.getString(Constants.city);
		if(geoData!=null){
			txt4.setText(geoData);
		}else{
			findViewById(R.id.layout4).setVisibility(View.GONE);
		}

		String area = bundle.getString(Constants.GEOCODE_AREA);
		if(area!=null){
			txt5 = (TextView)findViewById(R.id.text5);
			txt5.setText(bundle.getString(Constants.GEOCODE_AREA));
		}else{
			findViewById(R.id.layout5).setVisibility(View.GONE);
		}

		String partno = bundle.getString(Constants.PRODUCTCODE);
		if(partno!=null){
			txt6 = (TextView)findViewById(R.id.text6);
			txt6.setText(bundle.getString(Constants.PRODUCTCODE));
		}else{
			findViewById(R.id.layout6).setVisibility(View.GONE);
		}

		txt7 = (TextView)findViewById(R.id.text7);
		txt8 = (TextView)findViewById(R.id.text8);
		getRequestDataMap().put(Constants.CUSTID, "0");
		
		String trendType = bundle.getString(Constants.TRENDTYPE);
		int reportType= bundle.getInt(REPORT_TYPE);
		
			if(trendType.equals("Dealer")){

				txt7.setText("Dealer");
			
				if(reportType!=1){
					txt8.setText(bundle.getString(Constants.NAME));
					getRequestDataMap().put(Constants.CUSTID, bundle.getString(Constants.DIRECTDEALER));
				}else{
					txt8.setText("All Customers");
				}
			}else{
				txt7.setText("Mechanic");
				if(reportType!=1){
					txt8.setText(bundle.getString(Constants.NAME));
					getRequestDataMap().put(Constants.CUSTID, bundle.getString(Constants.MECHID));
				}else{
					txt8.setText("All Customers");
				}
			}
		
		txt9 = (TextView)findViewById(R.id.text9);
		txt10 = (TextView)findViewById(R.id.text10);
		txt11= (TextView)findViewById(R.id.text11);
		
		mlistView = (ListView)findViewById(android.R.id.list);
		getTrendReportMonthely();
	}

	private void getTrendReportMonthely(){


		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				if(result!=null){

					int quantity = 0;
					double value = 0.0;
					ArrayList<HashMap<String, String>> monthelyList = FetchingdataParser.getMonthelyTrendList(result.toString());
					if(monthelyList!=null){
						
						String[] from = {Constants.MONTH,Constants.quantity,Constants.VALUE};
						int[] to = {R.id.position1,R.id.position2,R.id.position3};
						
						SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(), monthelyList, R.layout.customer_trendlist_monthely_cell, from, to);
						mlistView.setAdapter(adapter);
						try{
							for (HashMap<String, String> datamap :monthelyList){
								quantity = quantity+ Integer.parseInt(datamap.get(Constants.quantity));
								value = value+ Double.parseDouble(datamap.get(Constants.VALUE));
							}
							txt10.setText(String.valueOf(quantity));
							txt11.setText(String.valueOf(value));
						}catch(Exception ex){
							ex.printStackTrace();
						}
						

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
		String methodName="CustomersTrendMISScreen2";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}
}
