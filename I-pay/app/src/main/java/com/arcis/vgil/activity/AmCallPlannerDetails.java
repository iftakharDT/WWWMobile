package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.AmcallPlannerAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AmCallPlanerModel;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AmCallPlannerDetails extends BaseActivity {
	
	ListView mlistView;
	String contType;
	String StateId;
	String CityId;
	String AreaID;
	String CustomerType;
	String CurtomerClass;
	int LastVisited;
	private Button call_planner_save;
	// Function CallPlanner_MIS(AMID As String,
	//StateID As String,
	//CityID As String,
	private HashMap<String , String> dataMap;
	AmcallPlannerAdapter adapter;
	ArrayList<AmCallPlanerModel> amCallPannerList;
	private ArrayList<AmCallPlanerModel> orderList = new ArrayList<AmCallPlanerModel>();
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.am_call_planner_details);
		setCurrentContext(this);
		call_planner_save=(Button)findViewById(R.id.call_planner_save);
		call_planner_save.setOnClickListener(this);
			
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		if (b != null) {
			
			contType = b.getString("CONTACTTYPE");
			StateId = b.getString("stateID");
			CityId = b.getString("cityID");
			AreaID = b.getString("areaID");
			CustomerType = b.getString(Constants.customerType);
			CurtomerClass = b.getString("Class_type");
			LastVisited=b.getInt("Last_visited");

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
						
						if (response == null) {
							Toast.makeText(getCurrentContext(),
									getResources().getString(R.string.error4),
									Toast.LENGTH_SHORT).show();
						} 
						
						
						else {
							if (response != null) {
							
							 amCallPannerList = FetchingdataParser.getAmCallPlannerCustomersList(response.toString());	
								
								if (amCallPannerList!=null) {
									
									adapter  =new AmcallPlannerAdapter(getCurrentContext(), R.layout.am_call_planner_custom_shell, amCallPannerList);
									mlistView.setAdapter(adapter);
								} 
								
								
								else {
									 if (response.toString().equalsIgnoreCase("{ 'Root': { 'data': ] }}")) {
										Toast.makeText(getCurrentContext(),
												"NO DATA",
												Toast.LENGTH_SHORT).show();
									}
									 
									 else {
										 Toast.makeText(getCurrentContext(),
													response.toString(),
													Toast.LENGTH_SHORT).show();
									}
									

									

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
			
		//	 Function CallPlanner_MIS(AMID As String, StateID As String,
			//CityID As String, AreaID As String, CustomerType As String,
			//CurtomerClass As String, LastVisited As Integer) As String

			 

			
			request.put(Constants.stateID, StateId);
			request.put(Constants.cityID, CityId);
			request.put(Constants.areaID, AreaID);
			request.put(Constants.customerType, CustomerType);
			request.put("CurtomerClass", CurtomerClass);
			request.put("LastVisited", LastVisited);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CallPlanner_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
		
	}

	/*@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
 
         
		Intent intent = new Intent(getCurrentContext(),
				Customer_visit_metting_Activity.class);
		intent.putExtra("CONTACTTYPE", amCallPannerList.get(arg2).getContactType() );
		intent.putExtra(Constants.ID, amCallPannerList.get(arg2).getID());
		intent.putExtra(Constants.NAME, amCallPannerList.get(arg2).getName());
		startActivity(intent);
	}*/
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		
		for(int i=0;i<amCallPannerList.size();i++)
        {
			if (amCallPannerList.get(i).getPlanned().equals("0")) {
            if(amCallPannerList.get(i).isSelected())
            {
            	
            		String dealerId=amCallPannerList.get(i).getID();
                	AmCallPlanerModel order = new AmCallPlanerModel();
                	order.setID(dealerId);
    				orderList.add(order);
				}
            	
          
        }
            
         
        }
		
		   if (orderList.size()>0) {
	        	  String jsondata=null;
	  			try{
	  			jsondata = getJsonStringForManualOrder(orderList);
	  			}catch(Exception ex){
	  				ex.printStackTrace();
	  				Toast.makeText(getCurrentContext(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
	  			}
              saveCallPLanner(jsondata);
			}
				else {
					Toast.makeText(getCurrentContext(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
				}
	}



	
private void saveCallPLanner(String jsondata) {
		// TODO Auto-generated method stub
	

	  GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, getResources().getString(R.string.uploaddata), new GetDataCallBack() {

		@Override
	  	public void processResponse(Object result) {
			// TODO Auto-generated method stub
			SoapObject responce  = null;
			try{

				responce = (SoapObject)result;
			}catch(Exception ex){
				ex.printStackTrace();
			}
			AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
			if (responce == null) {
				errordialog.setTitle(getResources().getString(R.string.error6));
				if (result != null) {
					errordialog.setMessage(result.toString());
				} else {
					errordialog.setMessage(getResources().getString(R.string.error4));
				}

			} else {
				errordialog.setMessage(getResources().getString(R.string.message5));
		
				
				

			}
		 	 errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				  public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					dialog.dismiss();	
				
				}
			});
			AlertDialog dialog = errordialog.create();
			dialog.show();

		}

	});
	  LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
try{
	
	
	requestdata.put("JSONData", jsondata);

	} catch (Exception e) {
		e.printStackTrace();
	}
	
	String ipAddress=getResources().getString(R.string.ipaddress);
	String webService =getResources().getString(R.string.webService);
	String nameSpace=getResources().getString(R.string.nameSpace);
	String methodName="SaveAMVisitPlan";
	String soapcomponent=getResources().getString(R.string.soapcomponent);
	datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
	datafromnetwork.sendData(requestdata);
	datafromnetwork.execute();
		
	}



/*
	private AmCallPlanerModel CaptureData(HashMap<String, String> productMap) {
		// TODO Auto-generated method stub
		AmCallPlanerModel delaerID = new AmCallPlanerModel();
		
		delaerID.setID(amCallPannerList.get);
		return delaerID;
	}*/
	
	private String getJsonStringForManualOrder(
		ArrayList<AmCallPlanerModel> dealrID) {
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);



		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");


		for(int i=0;i<dealrID.size();i++){
			AmCallPlanerModel am_dealerDealerSale = dealrID.get(i);
			dataString.append("{");
			dataString.append("'");
			dataString.append("AMID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(passworddetails.getString("username",""));
			dataString.append("'");
			dataString.append(",");
			
			
			dataString.append("'");
			dataString.append("CUstomerID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(am_dealerDealerSale.getID());
			dataString.append("'");
			dataString.append("}");
			dataString.append(",");
			
			
			
		}
		dataString.append("]");
		Log.i("Server data String ", dataString.toString());

		return dataString.toString();}



	
	
}
