package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.PartNoAllCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AmCustomerMaster_Fitment_deprecated extends BaseActivity {
	String cVid="0",sVid="0",areaNameVid="0",dlVid="0",cityVid="0";
	ArrayList<HashMap<String,Object>> arealist;
    private Spinner spnr_segment,spnr_oe,spnr_appication/*,spnr_partNo*/;
    private Spinner citySpinner;
    private Spinner areaSpinner;
    private Spinner customerSpinner;
    private Spinner Typespinner;
	private Button btn_get,btn_startDate,btn_startTime;
	private int SegmentID=0,OEID=0,ApplicationID=0;
	EditText et_date_from,et_date_to,mannual_part_no;
	int ParntNo;
	String ContactType,ContactID;
	String ContactIDTwo="";
	ArrayList<String> cityname,cityidd,areaName,areaNameId,customerName,customerNameId,contactType_Fit,contactTypeFit_Id;
	PartNoAllCustomAlertAdapter partNoAdapter;
	ArrayList<PartNoModel> partnoList;
	AlertDialog dialog;
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.am_customermaster_fitment);
		setCurrentContext(this);
		SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		 String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
		 ContactID =spref.getString(Constants.USERID, "");
		arealist=new ArrayList<HashMap<String,Object>>();
		
		cityname=new ArrayList<String>();

		cityidd=new ArrayList<String>();


		areaName=new ArrayList<String>();

		areaNameId=new ArrayList<String>();
		
		customerName=new ArrayList<String>();

		customerNameId=new ArrayList<String>();
		
		
		
		
		et_date_from=(EditText) findViewById(R.id.et_date_from);
		et_date_to=(EditText) findViewById(R.id.et_date_to);
		
		btn_get=(Button) findViewById(R.id.get_button);
		btn_get.setOnClickListener(this);
		
		btn_startDate = (Button)findViewById(R.id.date_from);

		btn_startDate.setOnClickListener(this);
		
		btn_startTime = (Button)findViewById(R.id.date_to);
		btn_startTime.setOnClickListener(this);
		
		
		citySpinner=(Spinner)findViewById(R.id.cityspinner);
		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					 areaName.clear();
					 areaNameId.clear();
					 cityVid=cityidd.get(citySpinner.getSelectedItemPosition());
					 GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,cityVid,"0");
					// final String cityId,final String areaId,final String ContactID
					 GetAreaManagerContacts(cityVid,"0","0");
				}
				else {
					 GetAreaManagerContacts("0","0","0");
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
					GetAreaManagerContacts(cityVid,areaNameVid,dlVid);
					
				}
				else {
					 GetAreaManagerContacts(cityVid,"0",dlVid);
				}
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Typespinner=(Spinner) findViewById(R.id.typespinner);
		Typespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				// TODO Auto-generated method stub

				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					dlVid=data.get(Constants.ID);
					
					 GetAreaManagerContacts(cityVid,areaNameVid,dlVid);
				}
				else {
					 GetAreaManagerContacts(cityVid,areaNameVid,"0");
				}
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		customerSpinner = (Spinner)findViewById(R.id.customer_spinner);
		customerSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					ContactIDTwo = data.get(Constants.ID);
				//	Function ExternalContactMAster_MIS(ByVal CityID As String,
					//ByVal AreaID As String, ByVal ContactType As String,
					//ByVal ContactID As String, ByVal UserID As String, ByVal Password As String) As String
					ExternalContactMAster_MIS(cityVid,areaNameVid,dlVid,ContactIDTwo);
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		
		
		spnr_segment = (Spinner)findViewById(R.id.spinner_segment);
		spnr_segment.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String segmentid = data.get(Constants.ID);
					getOETroughSegment(segmentid );
					
					SegmentID= Integer.parseInt(segmentid);
					getPartNo(SegmentID,0,0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		spnr_oe = (Spinner)findViewById(R.id.spinner_oe);
		spnr_oe.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
				HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
				String segmentid = data.get(Constants.ID);
			    getApplications(segmentid);
				OEID= Integer.parseInt(segmentid);
				getPartNo(SegmentID,OEID,0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		spnr_appication = (Spinner)findViewById(R.id.spinner_application);

		spnr_appication.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String applicationid = data.get(Constants.ID);
					ApplicationID= Integer.parseInt(applicationid);
					getPartNo(SegmentID,OEID,ApplicationID);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
	/*	spnr_partNo = (Spinner)findViewById(R.id.spinner_partno);
		spnr_partNo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					ParntNo=Integer.parseInt(data.get(Constants.ProductID));
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		*/
		getSegment();
		getPartNo(0,0,0);
		mannual_part_no=(EditText) findViewById(R.id.mannual_part_no);
		mannual_part_no.setOnClickListener(this);
		
		 if (ContactTypeAM.equalsIgnoreCase("1")) {
		      ContactType="AM";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
			  GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0");
			}
		
	      GetExternalContactTypeList();

			GetAreaManagerContacts("0","0","0");

		
		
	}
	
	
	protected void ExternalContactMAster_MIS(String cityVid,
                                             String areaNameVid, String ContactType, String contactID) {
		// TODO Auto-generated method stub
		
		
		

		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getExternalContactMAster_MIS(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							EditText et_mobile=(EditText) findViewById(R.id.et_mobile);
							EditText et_address=(EditText) findViewById(R.id.et_address);
							EditText et_email=(EditText) findViewById(R.id.et_email);
							EditText et_bday=(EditText) findViewById(R.id.et_bday);
							EditText et_abc=(EditText) findViewById(R.id.et_abc);
					        EditText et_avg_call_peryear=(EditText) findViewById(R.id.et_avg_call_peryear);
					        EditText et_last_call_date=(EditText) findViewById(R.id.et_last_call_date);
					     	EditText et_cus_value_pm=(EditText) findViewById(R.id.et_cus_value_pm);
					        EditText et_cus_value_tm=(EditText) findViewById(R.id.et_cus_value_tm);
				
							
							
					        et_mobile.setText(itemQuantityValueList.get(0).get(Constants.MOBILE_NUMBER));
					        et_address.setText(itemQuantityValueList.get(0).get(Constants.streetaddress));
					        et_email.setText(itemQuantityValueList.get(0).get(Constants.emailid));
					        et_bday.setText(itemQuantityValueList.get(0).get(Constants.dob));
					        et_abc.setText(itemQuantityValueList.get(0).get(Constants.TYPE));
					        et_avg_call_peryear.setText(itemQuantityValueList.get(0).get(Constants.AVG_Calls_Year));
					        et_last_call_date.setText(itemQuantityValueList.get(0).get(Constants.LastCallDate));
					        et_cus_value_pm.setText(itemQuantityValueList.get(0).get(Constants.CustomerValueAvgPM));
					        et_cus_value_tm.setText(itemQuantityValueList.get(0).get(Constants.CustomerValueTM));
							
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
		//	Function ExternalContactMaster_MIS(ByVal CityID As String, 
			//ByVal AreaID As String, ByVal ContactType As String, 
			//ByVal ContactID As String, ByVal UserID As String, ByVal Password As String) As String

			request.put(Constants.cityID, cityVid);
			request.put(Constants.areaID, areaNameVid);
			request.put("ContactType", ContactType);
			request.put("ContactID", contactID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="ExternalContactMaster_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		// TODO Auto-generated method stub
		
	
		// TODO Auto-generated method stub
		
	
		
		
	}


	private void GetAreaManagerContacts(final String cityId, final String areaId, final String ContactID) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> TypespinnerList = FetchingdataParser.getAreaManagerContacts(response.toString());
					if(TypespinnerList!=null ){
						if (TypespinnerList.size()!=0) {
							SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), TypespinnerList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
							segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							customerSpinner	.setAdapter(segmentAdapter);
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
			//
//Function GetAreaManagerContacts(ByVal AMID As String, ByVal CityID As String, ByVal AreaID As String,
			//ByVal ContactType As String, ByVal UserID As String, ByVal Password As String) As String
			request.put("AMID", passworddetails.getString(Constants.USERID,""));
			request.put(Constants.cityID, cityId);
			request.put(Constants.areaID, areaId);
			request.put("ContactType", ContactID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAreaManagerContacts";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		// TODO Auto-generated method stub
		
	
		// TODO Auto-generated method stub
		
	}


	private void GetExternalContactTypeList() {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> customerList = FetchingdataParser.getExternalContactTypeList(response.toString());
					if(customerList!=null ){
						if (customerList.size()!=0) {
							SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), customerList, android.R.layout.simple_spinner_item, new String[]{Constants.TYPE}, new int[]{android.R.id.text1});
							segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							Typespinner.setAdapter(segmentAdapter);
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
			//Function GetExternalContactTypeList(ByVal UserID As String, ByVal Password As String) As String
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetExternalContactTypeList";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		// TODO Auto-generated method stub
		
	}


	private void GetGeographyByLogin(final String contactType , final String geoName, final String geoId, final String ContactID, final String cityId, final String areaId) {
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

								 if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){

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
						/*if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,customerName);
							AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							customerSpinner.setAdapter(AMAdapter);
						}
						*/
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
			request.put(Constants.stateID, "0");
			request.put(Constants.cityID, cityId);
			request.put(Constants.areaID, areaId);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetGeographyByLogin";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	protected void getOETroughSegment(String segmentid) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> oeList = FetchingdataParser.getOE(response.toString());
					if(oeList!=null ){
						if (oeList.size()!=0) {
							SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), oeList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
							segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_oe.setAdapter(segmentAdapter);
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
			request.put("RequestType","OE");
			request.put("ParentID",segmentid);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegmentationDetail_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	}

	protected void getApplications(String segmentID) {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> applicationList = FetchingdataParser.getApplications(response.toString());
					if(applicationList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), applicationList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
						segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spnr_appication.setAdapter(segmentAdapter);

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
			request.put("RequestType","APPLICATION");
			request.put("ParentID",segmentID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegmentationDetail_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	}
// Public Function GetAllProduct_MIS(SegmentID As Integer, OEID As Integer, ApplicationID As Integer, ByVal UserID As String, ByVal Password As String)

	private void getPartNo(int SegmentID,int OEID,int ApplicationID) {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					partnoList = FetchingdataParser.getPartNo(response.toString());
					if(partnoList!=null){
						//SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
						//segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						//spnr_partNo.setAdapter(segmentAdapter);

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
			request.put("SegmentID",SegmentID);
			request.put("OEID",OEID);
			request.put("ApplicationID",ApplicationID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAllProduct_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}

	private void getSegment() {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getSegment(response.toString());
					if(segmentList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), segmentList, android.R.layout.simple_spinner_item, new String[]{Constants.SEGMENTNAME}, new int[]{android.R.id.text1});
						segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spnr_segment.setAdapter(segmentAdapter);

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
			request.put("RequestType","SEGMENT");
			request.put("ParentID","0");
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegmentationDetail_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		Calendar cal = Calendar.getInstance();
		
		
		switch (v.getId()) {
		case R.id.get_button:
//			Function ExternalContactMAsterCouponValue_MIS(ByVal CityID As String, 
			//ByVal AreaID As String, ByVal ContactType As String, ByVal ContactID As String,
		//	ByVal UserID As String, ByVal Password As String) As String
			
			Intent intent=new Intent(AmCustomerMaster_Fitment_deprecated.this,AmCustomerMaster_FitmentDetails.class);
			intent.putExtra("CityID",cityVid );
			intent.putExtra("AreaID", areaNameVid);
			intent.putExtra("ContactType", dlVid);
			intent.putExtra("ContactID", ContactIDTwo);
			startActivity(intent);
			
			break;
			
		case R.id.date_from:
	    DatePickerDialog datePicker = new DatePickerDialog(getCurrentContext(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
					// TODO Auto-generated method stub
					
					et_date_from.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);
					
				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			datePicker.setTitle("Please select Date");
			datePicker.show();
			break;
			
		case R.id.date_to:
			

			DatePickerDialog datePicker_to = new DatePickerDialog(getCurrentContext(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
					// TODO Auto-generated method stub
					
					et_date_to.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);
					
				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			datePicker_to.setTitle("Please select Date");
			datePicker_to.show();
			
			break; 
			
			
           case R.id.mannual_part_no:
			

        		getSerachPartDetails();
			
			break;
		default:
			break;
		}
	}


	private void getSerachPartDetails() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
		
		EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
		ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
        AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
        builder.setView(view);
        partNoAdapter=new PartNoAllCustomAlertAdapter(getCurrentContext(), R.layout.am_manual_part_no_shell, partnoList);
            list_part_no.setAdapter(partNoAdapter);
            list_part_no.setOnItemClickListener(new OnItemClickListener() {

		

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long arg3) {
					// TODO Auto-generated method stub
			
					ParntNo = Integer.parseInt(partnoList.get(position).getProductID());
			
					 Toast.makeText(getCurrentContext(),partnoList.get(position).getCode(), Toast.LENGTH_LONG).show();
					 mannual_part_no.setText(partnoList.get(position).getCode());
					
					 dialog.dismiss();
					
				}
				
			});
            
            
            searchpartNo.addTextChangedListener(new TextWatcher() {

    			@Override
    			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    				// TODO Auto-generated method stub

    				if(AmCustomerMaster_Fitment_deprecated.this.partNoAdapter!=null)
    					AmCustomerMaster_Fitment_deprecated.this.partNoAdapter.getFilter().filter(arg0);
    			}

    			@Override
    			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
    				// TODO Auto-generated method stub

    			}

    			@Override
    			public void afterTextChanged(Editable arg0) {
    				// TODO Auto-generated method stub

    			}
    		});
            
            
            builder.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            
            dialog = builder.show();
	
	
		
		
	}

	
	
	
}
