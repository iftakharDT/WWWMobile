package com.arcis.vgil.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.adapter.CustomMasterlistAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.CustomerMAsterListModel;
import com.arcis.vgil.parser.FetchingdataParser;


public class AmCustomerMaster_Fitment extends BaseActivity {
	String cVid="0",sVid="0",areaNameVid="0",dlVid="0",cityVid="0";
	ArrayList<HashMap<String,Object>> arealist;
    private Spinner spnr_segment,spnr_oe,spnr_appication/*,spnr_partNo*/;
    private Spinner statespinner,citySpinner,areaSpinner,Typespinner;
	private Button btn_get,btn_startDate,btn_startTime;
	private int SegmentID=0,OEID=0,ApplicationID=0;
	private EditText et_date_from,et_date_to,mannual_part_no;
	int ParntNo;
	String ContactType,ContactID,CouponTypeID;
	String ContactIDTwo="";
	ArrayList<String>  statename,stateidd,cityname,cityidd,areaName,areaNameId,customerName,customerNameId,contactType_Fit,contactTypeFit_Id;
	CustomMasterlistAlertAdapter partNoAdapter;
	ArrayList<CustomerMAsterListModel> TypespinnerList;
	private RadioGroup rg_searchType;
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
		
		statename=new ArrayList<String>();
        stateidd=new ArrayList<String>();
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
		
		rg_searchType = (RadioGroup)findViewById(R.id.rg_searchtype);
		rg_searchType.setOnCheckedChangeListener(this);
		
		statespinner=(Spinner)findViewById(R.id.statespinner);
		statespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			
					 cityidd.clear();
					 cityname.clear();
					 areaName.clear();
					 areaNameId.clear();
					 GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0");
						
					 GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityVid,"0");
		
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
					 GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,sVid,cityVid,"0");
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
		
		
		Typespinner=(Spinner) findViewById(R.id.typespinner);
		Typespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					dlVid=data.get(Constants.ID);

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
					
					SegmentID=Integer.parseInt(segmentid);
					//getPartNo(SegmentID,0,0);
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
				OEID=Integer.parseInt(segmentid);
			//	getPartNo(SegmentID,OEID,0);
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
					ApplicationID=Integer.parseInt(applicationid);
				//	getPartNo(SegmentID,OEID,ApplicationID);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		

		
		 if (ContactTypeAM.equalsIgnoreCase("1")) {
		      ContactType="AM";
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0");
		      GetGeographyByLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0");
			  GetGeographyByLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0","0");
			}
		
	      GetExternalContactTypeList();
      
 
		
		
	}
	
	

	private void GetAreaManagerContacts(final String cityId,final String areaId,final String ContactID) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
                   TypespinnerList = FetchingdataParser.getCustomerMAsterList(response.toString());
					if(TypespinnerList!=null ){
						if (TypespinnerList.size()!=0) {
							LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
							
							EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
							ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
					        AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
					        builder.setView(view);
					        partNoAdapter=new CustomMasterlistAlertAdapter(getCurrentContext(), R.layout.am_manual_part_no_shell, TypespinnerList);
					            list_part_no.setAdapter(partNoAdapter);
					            list_part_no.setOnItemClickListener(new OnItemClickListener() {

							

									@Override
									public void onItemClick(AdapterView<?> parent, View view,
											int position, long arg3) {
										// TODO Auto-generated method stub
										
										
										/*//Function ExternalContactMAsterCouponValue_MIS(ByVal CountryID As String,
										ByVal ZoneID As String, ByVal StateID As String, 
										ByVal CityID As String, ByVal AreaID As String, 
										ByVal DateFrom As String, ByVal DateTo As String,
										ByVal CouponTypeID As String;
								*/
										
									    String  CustomerID = TypespinnerList.get(position).getID();


								        String  CustomerName=TypespinnerList.get(position).getNAME();
										Intent intent=new Intent(AmCustomerMaster_Fitment.this,AmCustomerMaster_FitmentDetails.class);
										intent.putExtra("CountryID", "0");
										intent.putExtra("ZoneID", "0");
										intent.putExtra("StateID", stateidd.get(statespinner.getSelectedItemPosition()));
										intent.putExtra("CityID",cityVid );
										intent.putExtra("From_Date",et_date_from.getText().toString());
										intent.putExtra("To_Date", et_date_to.getText().toString());
										intent.putExtra("CouponTypeID",CouponTypeID);
										intent.putExtra("ContactType", dlVid);//@@
										intent.putExtra("CustomerID", CustomerID);//@@
										intent.putExtra("CustomerName", CustomerName);//@@
										startActivity(intent);
										
										 dialog.dismiss();
										
									}
									
								});
					            
					            
					            searchpartNo.addTextChangedListener(new TextWatcher() {

					    			@Override
					    			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					    				// TODO Auto-generated method stub

					    				if(AmCustomerMaster_Fitment.this.partNoAdapter!=null)
					    					AmCustomerMaster_Fitment.this.partNoAdapter.getFilter().filter(arg0);
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
						

					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);



		try {
			
			 /* Function GetContactCustomerForCouponValue(ByVal CountryID As String, 
			ByVal ZoneID As String, ByVal StateID As String,
			ByVal CityID As String, ByVal AreaID As String, 
			ByVal ContactTypeID As String, ByVal CustomerTypeID As String, 
			ByVal ContactID As String, DateFrom As String,
			DateTo As String, ByVal UserID As String,
			ByVal Password As String) As String    */
			
			request.put("CountryID", "0");
			request.put("ZoneID", "0"); 
			request.put("StateID", stateidd.get(statespinner.getSelectedItemPosition()));
			request.put("CityID", cityidd.get(citySpinner.getSelectedItemPosition()));
			request.put("AreaID", "0");
			request.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID,""));
			request.put("CustomerTypeID", dlVid);
			request.put("ContactID", passworddetails.getString(Constants.USERID,""));
			request.put("DateFrom",et_date_from.getText().toString());
		    request.put("DateTo",et_date_to.getText().toString());
		    request.put("CouponTypeID",CouponTypeID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetContactCustomerForCouponValue";
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
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
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


	private void GetGeographyByLogin(final String contactType ,final String geoName,final String geoId,final String ContactID,final String stateId,final String cityId,final String areaId) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					/**/
					arealist=new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : arealist) 
						{

							String geoids=(String)entry.get(Constants.GeoID);
							String geonames=(String)entry.get(Constants.GeoName);



							if(geoids!=null && geonames!=null){
								
								if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){

									if(!stateidd.contains(geoids))
										stateidd.add(geoids);
									if(!statename.contains(geonames))
										statename.add(geonames);

								}

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
						
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
						ArrayAdapter<String> stateAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,statename);
						stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						statespinner.setAdapter(stateAdapter);
					}
					
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
							ArrayAdapter<String> cityAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,cityname);
							cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							citySpinner.setAdapter(cityAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,areaName);
							AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							areaSpinner.setAdapter(AMAdapter);
						}
						
					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
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
			request.put(Constants.areaID, areaId);
			request.put(Constants.AM_ID, passworddetails.getString("username",""));
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		//String methodName="GetGeographyByLogin";
		String methodName="GetGeographyByLogin_NEW";
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
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
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
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
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
/*
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
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
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
*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		Calendar cal = Calendar.getInstance();
		
		
		switch (v.getId()) {
		case R.id.get_button:

		/*	Intent intent=new Intent(AmCustomerMaster_Fitment.this,AmCustomerMaster_FitmentDetails.class);
			intent.putExtra("CityID",cityVid );
			intent.putExtra("AreaID", areaNameVid);
			intent.putExtra("ContactType", dlVid);
			intent.putExtra("ContactID", ContactIDTwo);
			startActivity(intent);*/
			if (validation()) {
				GetAreaManagerContacts(cityVid,areaNameVid,dlVid);
			}
			
			
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
			
		default:
			break;
		}
	}


	@Override
	public boolean validation() {
		// TODO Auto-generated method stub
		boolean flag = true;
		String errorMsg = "Invalid";
		if (!notNullCheck(R.id.et_date_from))
			flag = false;

		if (!notNullCheck(R.id.et_date_to))
			flag = false;
		int checkedid = rg_searchType.getCheckedRadioButtonId();
		if(checkedid==-1){
			flag = false; 
			errorMsg = errorMsg+" Please select search Type ";
		}
		
		if(Typespinner.getSelectedItemPosition()==AdapterView.INVALID_POSITION || Typespinner.getSelectedItemPosition()==0){
			flag = false;
			errorMsg = errorMsg+" Please select Type ";
		}
		if(!flag)
			Util.showToast(getCurrentContext(), errorMsg, Toast.LENGTH_LONG).show();
			
		return flag;
	}
	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		super.onCheckedChanged(group, checkedId);
		
		switch (group.getId()) {
			
		case R.id.rg_searchtype:
			
			switch (checkedId) {
			case R.id.rb_geo:
				CouponTypeID  = "2";
			
				break;
				
			case R.id.rb_mobileNo:
				CouponTypeID  = "1";
				
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
	}
	
}
