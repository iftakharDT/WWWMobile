package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class AmCustomerMaster_FitmentDetails extends BaseActivity {

	ListView listView;
	String cityVid,areaNameVid,dlVid,ContactIDTwo,fromDate,toDate,stateID,couponTypeID,customerID;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.am_customermaster_fitment_details);
		setCurrentContext(this);
		listView=(ListView) findViewById(R.id.listView1);
		Intent intent= getIntent();
		Bundle bundle=intent.getExtras();
		if (bundle!=null) {


			fromDate=bundle.getString("From_Date");
			toDate=bundle.getString("To_Date");
			stateID=bundle.getString("StateID");
			couponTypeID=bundle.getString("CouponTypeID");
			customerID=bundle.getString("CustomerID");
			cityVid=bundle.getString("CityID");
			areaNameVid=bundle.getString("AreaID");
			dlVid=bundle.getString("ContactType");
			ContactIDTwo=bundle.getString("ContactID");
			
			SalesAMReport_Common(cityVid,areaNameVid,dlVid,ContactIDTwo);
			
		}
		else {
			Toast.makeText(getCurrentContext(), "Something went wrong", Toast.LENGTH_LONG).show();
		}
	
		
	//	Function ExternalContactMAsterCouponValue_MIS(ByVal CityID As String, 
	//ByVal AreaID As String, ByVal ContactType As String, ByVal ContactID As String,
	//	ByVal UserID As String, ByVal Password As String) As String

		 

	}
	
	
	private void SalesAMReport_Common(String cityVid, String areaNameVid, String dlVid,
                                      String ContactIDTwo) {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getAmCustomerMaster_FitmentDetails(response.toString());
					if(itemQuantityValueList!=null){
						
					
						
						
						SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), itemQuantityValueList, R.layout.am_customer_master_fitment_single,
								new String[]{Constants.PRODUCTCODE,Constants.DIRECTDEALERQUANTITY,Constants.CouponValue,Constants.AvgValue,Constants.SaleValue},
								new int[]{R.id.partno,R.id.quantity,R.id.coupon_value,R.id.avg_value,R.id.sale_value});
						listView.setAdapter(adapter);	
					
						
					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
	try { 
		
		//	Function ExternalContactMAsterCouponValue_MIS(ByVal CityID As String, 
		//ByVal AreaID As String, ByVal ContactType As String, ByVal ContactID As String,
		//	ByVal UserID As String, ByVal Password As String) As String


		    request.put("CountryID","0");
		    request.put("ZoneID","0");
		    request.put("StateID",stateID);
		    request.put("CityID",cityVid);
	        request.put("AreaID","0");
	        //request.put("ContactType",dlVid);
	       // request.put("ContactID",ContactIDTwo);
	        request.put("DateFrom ",fromDate);
	        request.put("DateTo",toDate);
	        request.put("CouponTypeID",couponTypeID);
	        request.put("CustomerID",customerID);
	        request.put("CustomerTypeID",dlVid);
	        request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="ExternalContactMAsterCouponValue_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}



}
