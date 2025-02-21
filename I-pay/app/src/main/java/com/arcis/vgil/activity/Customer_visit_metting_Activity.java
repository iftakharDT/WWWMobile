package com.arcis.vgil.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Questions;
import com.arcis.vgil.model.Singleton;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Customer_visit_metting_Activity  extends BaseActivity{


	private TextView txt_custName;
	private Button btn_startDate,btn_startTime,btn_endTime,btn_submit;
	
	private EditText edttxt_startDate,edttxt_startTime,edttxt_endTime,edttxt_pov,edttxt_mettingNotes;
	String mlatitude="",mlongitude="";
	private String contactType=null;
	private String combinedDateAndTime;
	/*public boolean isGPS;*/
	private static int gpssettingCounter = 0;
	public boolean flag = true;
	private LocationManager locationManager;

	public static int MAX_DISTANCE = 1;
	public static final int MAX_TIME = 10000;

	public boolean isLatLongcaptured = false;

	public boolean isLatLongcaptured() {
		return isLatLongcaptured;
	}

	public void setLatLongcaptured(boolean isLatLongcaptured) {
		this.isLatLongcaptured = isLatLongcaptured;
	}
	
	@Override
	public void inti() {
		
		// TODO Auto-generated method stub
		
		setContentView(R.layout.visit_log_metting_details);
		setCurrentContext(this);
		Intent intent = getIntent();
		
		contactType=intent.getStringExtra("CONTACTTYPE");
		txt_custName = (TextView)findViewById(R.id.customername);
		txt_custName.setText(intent.getStringExtra(Constants.NAME));
		
		btn_startDate = (Button)findViewById(R.id.startdate);
		btn_startDate.setOnClickListener(this);
		
		btn_submit = (Button)findViewById(R.id.submit);
		btn_submit.setOnClickListener(this);
		
		edttxt_startDate = (EditText)findViewById(R.id.editText_statrDate);
		edttxt_startTime = (EditText)findViewById(R.id.editText_startTime);
	
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 boolean isGPS = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (isGPS) {
			checkGPS();
		} else {
			if (!isLatLongcaptured()) {
				new AlertDialog.Builder(Customer_visit_metting_Activity.this)
				 .setTitle("Message")
				            .setMessage("Please turn On GPS/Location")
				            .setIcon(R.drawable.ipay)
				            .setPositiveButton("YES",
				                    new DialogInterface.OnClickListener() {
				                        @TargetApi(11)
				                        public void onClick(DialogInterface dialog, int id) {
				                         //   showToast("Thank you! You're awesome too!");
				                        	checkGPS();
				                            dialog.cancel();
				                           
				                        }
				                    }).show();
				          
				
			}
		}
		
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.startdate:
			
		
			gettingSystemTime();
			
			
			
			break;
			
		
		case R.id.submit:
			if (isValidate()) {
				
				 SaveAMTerettoryCustomersMeetingLog();
			}
       
		  
			break;

		default:
			break;
		}
	}
	

	private void gettingSystemTime() {
		// TODO Auto-generated method stub
		
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.gettingSystemTime(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							edttxt_startDate.setText(itemQuantityValueList.get(0).get(Constants.Date));
							edttxt_startTime.setText(itemQuantityValueList.get(0).get(Constants.TIME));
							combinedDateAndTime=itemQuantityValueList.get(0).get(Constants.combinedDateAndTime);
						}
						

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
	 
        	   // Function GetCurrentDateAndTime(ByVal UserID As String, ByVal Password As String) As String
	       
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}
         

           

             String ipAddress=getResources().getString(R.string.ipaddress);
             String webService =getResources().getString(R.string.Webservice_mis_android);
             String nameSpace=getResources().getString(R.string.nameSpace);
             String methodName="GetCurrentDateAndTime";
             String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
             dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
             dataFromNetwork.sendData(request);
             dataFromNetwork.execute();


	}

	private boolean isValidate() {
		// TODO Auto-generated method stub
		
		 
   	 boolean isGPS = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// TODO Auto-generated method stub
			if (isGPS) {
				
				if (mlatitude.equals("0") && mlongitude.equals("0") || mlatitude.equals("") && mlongitude.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please wait for few second",
							Toast.LENGTH_LONG).show();
					flag=false;
					checkGPS();
					
				}
				else {
					flag=true;
				}
			
				
			}
		if (!isGPS) {

			flag=false;
	
			if (mlatitude.equals("0") && mlongitude.equals("0") || mlatitude.equals("") && mlongitude.equals("")) {
				flag = false;
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				if (!isLatLongcaptured()) {
					new AlertDialog.Builder(Customer_visit_metting_Activity.this)
					 .setTitle("Alert !")
					            .setMessage("Turn on Location/GPS to allow submission")
					            .setIcon(R.drawable.ipay)
					            .setPositiveButton("YES",
					                    new DialogInterface.OnClickListener() {
					                        @TargetApi(11)
					                        public void onClick(DialogInterface dialog, int id) {
					                         //   showToast("Thank you! You're awesome too!");
					                        	checkGPS();
					                            dialog.cancel();
					                           
					                        }
					                    }).show();
					
				}
			}
		}
		if (edttxt_startDate.getText().toString().length()==0 && edttxt_startTime.getText().toString().length()==0 ) {
	          flag=false;
	          Toast.makeText(getApplicationContext(),
						"Please fill Start Date",
						Toast.LENGTH_LONG).show();
		}
		
		
		return flag;
	}

	/*
	 * // Check Gps StatusWritten By Jai
	 */
	private void checkGPS() {

		PackageManager packageManager = getCurrentContext().getPackageManager();
		boolean hasGPS = packageManager
				.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		 boolean isGPS = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (hasGPS) {
			try {
				
				if (isGPS) {

					getBestLocation();
				} else {
					if (gpssettingCounter == 1) {
						getBestLocation();
					} else {
						gpssettingCounter++;
						startActivityForResult(
								new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
								0);
						return ;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.getMessage();
			}

		} else {

			// get network location only
			getBestLocation();
		}

	}

	/**
	 * Get preciese location coordinated by using GPS or network provider.
	 * Written By Jai
	 */

	private void getBestLocation() {

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MAX_TIME, MAX_DISTANCE, locListener);
		Criteria critaria = new Criteria();
		critaria.setAccuracy(Criteria.ACCURACY_FINE);
		Location location = null;
		String provider = locationManager.getBestProvider(critaria, true);
		if (provider != null) {
			location = locationManager.getLastKnownLocation(provider);
		}

		if (location != null) {
			// set lat long into dataMap
			
			mlatitude = String.valueOf(location.getLatitude());
			mlongitude = String.valueOf(location.getLongitude());
		}

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, MAX_TIME, MAX_DISTANCE,
				locListener);
		provider = locationManager.getBestProvider(critaria, true);

		if (provider != null)
			location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			// Set lat long into data map.

			mlatitude = String.valueOf(location.getLatitude());
			mlongitude = String.valueOf(location.getLongitude());
		} else {
			mlatitude = "";
			mlongitude = "";
			//Toast.makeText(getCurrentContext(), "Please Turn on Gps", Toast.LENGTH_LONG).show();
		}

		gpssettingCounter = 0;

	}

	// Define LocationListener to get current location

	LocationListener locListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Log.i("On provider Enabled", provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Log.i("On provider Disabled", provider);

		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

			if (location != null) {
              
				mlatitude = String.valueOf(location.getLatitude());
				mlongitude = String.valueOf(location.getLongitude());

			}

		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (locationManager != null)
			locationManager.removeUpdates(locListener);
	}



	
	
	
	private void SaveAMTerettoryCustomersMeetingLog() {
		 GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Uploading data...", new GetDataCallBack() {
			@Override
		 	public void processResponse(Object response) {
				
				//{ 'Root': { 'AMVisitLog': [{'VisitLogID': '130'}] }}
				try{

			if (response==null) {
				Toast.makeText(getCurrentContext(),
						getResources().getString(R.string.error4),
						Toast.LENGTH_SHORT).show();
			} else {
                if (response!=null) {
                	ArrayList<Questions> questionList = FetchingdataParser.getAMLogResponce(response.toString());
                	AlertDialog.Builder errordialog = new AlertDialog.Builder(
							Customer_visit_metting_Activity.this); 		
                	if (response!=null&& questionList.size() == 0) {
                		errordialog.setMessage(response.toString());
						errordialog.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										
									}
								});
						AlertDialog dialog = errordialog.create();
						dialog.show();
					} 
                	
                	
                	else if (response!=null&& questionList.size() != 0) {
                		
                		
                		for (int i = 0; i < questionList.size(); i++) {
							Questions q = questionList.get(i);
							
                            Singleton.VisitLogID = q.getSetvisitLogID();
							
							
						}
                		errordialog.setMessage(getStringFromResource(R.string.message5)); 		
                		errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

        					@Override
        					public void onClick(DialogInterface dialog, int which) {
        						Intent intent = new Intent(Customer_visit_metting_Activity.this,PendingCalls.class);
        						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        					   startActivity(intent);
        					   finish();
        						
        					}
        				});
        				AlertDialog dialog = errordialog.create();
        				dialog.show();
                		
					} 
                	
				}
			}
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		
		
		
		try {
			
		    request.put(Constants.AM_ID, passworddetails.getString(Constants.USERID,""));
			request.put(Constants.CUSTID ,getIntent().getStringExtra(Constants.ID));
			//edttxt_startDate.setText(itemQuantityValueList.get(0).get(Constants.DATE));
			//edttxt_startTime.setText(itemQuantityValueList.get(0).get(Constants.DATE));
			request.put("ContactType" ,contactType); //@@@@@@@@@@
			if (edttxt_startDate.getText().toString().length()>0 && edttxt_startTime.getText().toString().length()>0 ) {
				request.put("StartDateTime" ,combinedDateAndTime);
			}
			
			request.put("EndDateTime" ,"");
			request.put("PurposeOfVisit", "");
			request.put("MeetingNotes","");
			request.put(Constants.latitude ,mlatitude);
			request.put(Constants.longitude ,mlongitude);
			
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SaveAMVisitLog_NEW";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		checkGPS();
	}
     @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    }
}
