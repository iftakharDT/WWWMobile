package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.ExternalCutomerMasterAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.ExternalCustomerMaster;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RM_MM_ExternalContactMaster extends BaseActivity{

	String cVid="0",sVid="0",areaNameVid="0",dlVid="0",cityVid="0";
	private Spinner spnr_contacttype;
	private EditText edttxt_contactName,edttxt_mobileNO;
	private Spinner citySpinner,statespinner,zonespinner,countryspinner;
	private Spinner areaSpinner;
	private Button btn_search;
	String ContactType,ContactID;
	ArrayList<HashMap<String,Object>> arealist;
	ArrayList<String> countryname,countryid,statename,stateidd,zonename,zoneid,dealerName,dealerNameId,cityname,cityidd,areaName,areaNameId,customerName,customerNameId,contactType_Fit,contactTypeFit_Id;
	
	private String[] contacttypedata = {"Please select","D","M"};
	private ListView mlistView;
	@Override
	public void inti() {
		// TODO Auto-generated method stub

		setContentView(R.layout.rm_mm_external_contact_master);
		setCurrentContext(this);
		SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
		ContactID =spref.getString(Constants.USERID, "");
		spnr_contacttype = (Spinner)findViewById(R.id.spinner3);

		edttxt_contactName = (EditText)findViewById(R.id.editText_custname);
		edttxt_mobileNO    = (EditText)findViewById(R.id.editText_mobileno);

		btn_search = (Button)findViewById(R.id.search);
		btn_search.setOnClickListener(this);

		mlistView  = (ListView)findViewById(android.R.id.list);
		mlistView.setOnTouchListener(listTouchListener);
		mlistView.setOnItemClickListener(this);
		arealist=new ArrayList<HashMap<String,Object>>();
        countryname=new ArrayList<String>();
		
		countryid  =new ArrayList<String>();
		

		zonename=new ArrayList<String>();
		
		zoneid=new ArrayList<String>();
		

		statename=new ArrayList<String>();
		
		stateidd=new ArrayList<String>();
		



		cityname=new ArrayList<String>();
	
		cityidd=new ArrayList<String>();
		
		cityname=new ArrayList<String>();

		cityidd=new ArrayList<String>();


		areaName=new ArrayList<String>();

		areaNameId=new ArrayList<String>();
		

		
		countryspinner=(Spinner)findViewById(R.id.spinner_contry);

		countryspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		zonespinner=(Spinner)findViewById(R.id.spinner_zone);
		zonespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					stateidd.clear();
					statename.clear();
					cityidd.clear();
					cityname.clear();
					areaName.clear();
					areaNameId.clear();
					GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
					GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
					GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
				   
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		statespinner=(Spinner)findViewById(R.id.spinner_state);
		statespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					cityidd.clear();
					cityname.clear();
					areaNameId.clear();
					areaName.clear();
					GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
					GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
					
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
	         
			}
		});		
		
		citySpinner=(Spinner)findViewById(R.id.cityspinner);
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					areaName.clear();
					areaNameId.clear();
					cityVid=cityidd.get(citySpinner.getSelectedItemPosition());
					GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(citySpinner.getSelectedItemPosition()),"0","0");
				}
				

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		areaSpinner=(Spinner) findViewById(R.id.areaspinner);
		areaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				// TODO Auto-generated method stub

				if(position!=0){
					areaNameVid=areaNameId.get(areaSpinner.getSelectedItemPosition());
					
					
				}
				
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,contacttypedata);
		countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spnr_contacttype.setAdapter(countryAdapter);
	
		 if (ContactTypeAM.equalsIgnoreCase("4")) {
		      ContactType="RM";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
		    
			}
		 if (ContactTypeAM.equalsIgnoreCase("5")) {
		      ContactType="MM";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
		     
			}
		 
		 if (ContactTypeAM.equalsIgnoreCase("27")) {
		      ContactType="CO";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
		      
			}
		 if (ContactTypeAM.equalsIgnoreCase("28")) {
		      ContactType="HO";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
		     
			}
		 
		 if (ContactTypeAM.equalsIgnoreCase("3")) {
		      ContactType="SI";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0","0");
		     
			}
	
	}


	private void getAMCustomerMaster(String cityid, String areaId, String externalcontactType, String name, String mobileno){


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){


					ArrayList<ExternalCustomerMaster> dataList = FetchingdataParser.getExternalcustomerMAsterList(response.toString());
					if(dataList.size()>0){
						ExternalCutomerMasterAdapter masterAdapter = new ExternalCutomerMasterAdapter(getCurrentContext(), R.layout.external_customer_master_cell, dataList);
						mlistView.setAdapter(masterAdapter);
					}else{
						Toast.makeText(RM_MM_ExternalContactMaster.this, "NO DATA", Toast.LENGTH_LONG).show();
						mlistView.setAdapter(null);
					}


				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
	


		try {
			//Function HigherAuthorityCustomerMaster(LoginType As String, ContactID As String,
			//Country As String, Zone As String, State As String, City As String, Area As String, 
			//CustomerType As String, Name As String, MobileNo As String) As String
			 if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("4")) {
			      ContactType="RM";
			      request.put("LoginType",ContactType);
			   
				}
			 if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("5")) {
			      ContactType="MM";
			      request.put("LoginType",ContactType);
				}
			 
			 if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("27")) {
			      ContactType="CO";
			      request.put("LoginType",ContactType);
			    
				}
			 if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("28")) {
			      ContactType="HO";
			      request.put("LoginType",ContactType);
			   
				}
			 
			 if (passworddetails.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("3")) {
			      ContactType="SI";
			      request.put("LoginType",ContactType);
				}
			
		
			request.put("ContactID",passworddetails.getString(Constants.USERID, ""));
			request.put("CustomerType",externalcontactType);
			request.put("Country",countryid.get(countryspinner.getSelectedItemPosition()));
			request.put("Zone",zoneid.get(zonespinner.getSelectedItemPosition()));
			request.put("State", stateidd.get(statespinner.getSelectedItemPosition()));
			request.put(Constants.city,cityid);
			request.put(Constants.area,areaId);
			request.put("Name", name);
			request.put(Constants.MOBILE_NUMBER,mobileno);


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="HigherAuthorityCustomerMaster";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		super.onItemClick(arg0, arg1, arg2, arg3);
		
		
		
		ExternalCustomerMaster rowData = (ExternalCustomerMaster) arg0.getItemAtPosition(arg2);
		
		Bundle bundle = new Bundle();
		bundle.putString(Constants.NAME, rowData.getCustomer());
		bundle.putString(Constants.MOBILE_NUMBER, rowData.getMobileNo());
		bundle.putString(Constants.address, rowData.getAddress());
		bundle.putString("City", rowData.getArea());
		bundle.putString("Area", rowData.getArea());
		bundle.putString("EmailId", rowData.getEmail());
		bundle.putString("BDay",rowData.getBirthday() );
		bundle.putString("Category", rowData.getCategory());
		bundle.putString("AvgCallsperYear", rowData.getAvgcallsperyear());
		bundle.putString("LASTCALLDATE", rowData.getLastcalldate());
		bundle.putString("CUSTVALUEPMAVG", String.valueOf(rowData.getCustvaluepermaonthavg()));
		bundle.putString("CUSTVALUEPMAVG", String.valueOf(rowData.getCustvaluepermaonthavg()));
		bundle.putString("CUSTVALUETM", String.valueOf(rowData.getCustvalueTM()));
		
		bundle.putString("Status", rowData.getStatus());
		
		Intent intent  = new Intent(getCurrentContext(),ExternalCustomerDetailsActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.search:
			
			String cityId = "0";
			String areaid = "0";
			String contactType = "";
			String name = "";
			String mobileNO = "";
			
			if(citySpinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && citySpinner.getSelectedItemPosition()!=0){
				
			
				cityId = cityidd.get(citySpinner.getSelectedItemPosition());
			}
			
			if(areaSpinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && areaSpinner.getSelectedItemPosition()!=0){
				
				areaid = areaNameId.get(areaSpinner.getSelectedItemPosition());
			}

			if(spnr_contacttype.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && spnr_contacttype.getSelectedItemPosition()!=0){
				
				contactType = (String) spnr_contacttype.getSelectedItem();
				
			}
			if(edttxt_contactName.getText().length()>0){
				
				name = edttxt_contactName.getText().toString();
			}
			if(edttxt_mobileNO.getText().toString().length()>0){
				
				mobileNO = edttxt_mobileNO.getText().toString();
			}
			
			
			getAMCustomerMaster(cityId, areaid, contactType, name, mobileNO);
			
			break;

		default:
			break;
		}
	}
	private void GetGeographyByLogin(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId, final String areaID, final String amId) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					/**/
					arealist=new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : arealist)
						{
                            String geoids=(String)entry.get(Constants.GeoID);
							String geonames=(String)entry.get(Constants.GeoName);



							if(geoids!=null && geonames!=null){

								if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
									if(!countryid.contains(geoids))
										countryid.add(geoids);
									if(!countryname.contains(geonames))
										countryname.add(geonames);
								}

								if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
									if(!zoneid.contains(geoids))
										zoneid.add(geoids);
									if(!zonename.contains(geonames))
										zonename.add(geonames);
								}

								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){

									if(!stateidd.contains(geoids))
										stateidd.add(geoids);
									if(!statename.contains(geonames))
										statename.add(geonames);

								}
								
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){

									if(!cityidd.contains(geoids))
										cityidd.add(geoids);
									if(!cityname.contains(geonames))
										cityname.add(geonames);
								}
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
									if(!areaNameId.contains(geoids))
										areaNameId.add(geoids);
									if(!areaName.contains(geonames))
										areaName.add(geonames);
								}
							}
						
						}
						

						if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
							ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,countryname);
							countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							countryspinner.setAdapter(countryAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
							ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,zonename);
							zoneAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							zonespinner.setAdapter(zoneAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
							ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,statename);
							stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							statespinner.setAdapter(stateAdapter);
						}
						
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
							ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,cityname);
							cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							citySpinner.setAdapter(cityAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,areaName);
							AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							areaSpinner.setAdapter(AMAdapter);
						}
						
					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			/*
			 * Function GetGeographyByLogin
			 * (ByVal ContactType As String, ByVal GeoType As String, ByVal GeoID As String, 
			 * ByVal ContactID As String, ByVal StateID As String, ByVal CityID As String, 
			 * ByVal UserID As String, ByVal Password As String) As String
		*/ 
			request.put("ContactType", contactType);
			request.put(Constants.GeoName,geoName);
			request.put("GeoID",geoId);
			request.put("ContactID", ContactID);
			request.put(Constants.stateID, stateId);
			request.put(Constants.cityID, cityId);
			request.put(Constants.areaID, areaID);
			request.put(Constants.AM_ID, amId);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetGeographyByLogin_NEW";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	
	
	
	
	
	
	

}
