package com.arcis.vgil.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * @author Muni Mishra
 *
 */
public class DailyCallsActivity  extends BaseActivity{

	
	private RadioGroup rg_contactType;
	private RadioGroup rg_searchType;
	private Spinner statespinner;
	
	private Spinner cityspinner;
	private Spinner areaspinner;
	private LinearLayout mGeoLayout;
	private EditText edittext_mobileNo;
	
	private Spinner spnr_partyType;
	
	private EditText edttxt_mobileSearch;
	
	private Spinner mspinnerState;
	private Spinner mspinnerCity;
	private Spinner mspinnerArea;

	private Button btn_search;
	private ListView mlistView;
	String contType;
	String StateId ,CityId,AreaID,MobileNo, contactType = "";
	HashMap< String, String> datamap = null;
	ArrayList<String> areaNameId,areaName,areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,pincode,pincodeid;
	ArrayList<HashMap<String,Object>> arealist;
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.activity_daily_calls);
		setCurrentContext(this);
		
		statename=new ArrayList<String>();
		
		stateidd=new ArrayList<String>();
		cityname=new ArrayList<String>();

		cityidd=new ArrayList<String>();
		
		areaName=new ArrayList<String>();

		areaNameId=new ArrayList<String>();
        arealist=new ArrayList<HashMap<String,Object>>();
        
        
		rg_contactType = (RadioGroup)findViewById(R.id.rg_contacttype);
		rg_contactType.setOnCheckedChangeListener(this);
		
		rg_searchType = (RadioGroup)findViewById(R.id.rg_searchtype);
		rg_searchType.setOnCheckedChangeListener(this);
		
		mGeoLayout = (LinearLayout)findViewById(R.id.geolayout);
		mGeoLayout.setVisibility(View.GONE);
		
		mspinnerState = (Spinner) findViewById(R.id.statespinner);
		mspinnerCity  = (Spinner) findViewById(R.id.cityspinner);
		mspinnerArea = (Spinner) findViewById(R.id.areaspinner);
		
		edttxt_mobileSearch = (EditText)findViewById(R.id.edttxt_mobileno);
		edttxt_mobileSearch.setVisibility(View.GONE);
		
		statespinner=(Spinner)findViewById(R.id.statespinner);
		statespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					// StateId=stateidd.get(statespinner.getSelectedItemPosition()); 
					cityname.clear();
					cityidd.clear();
					areaName.clear();
					areaNameId.clear();
					GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,stateidd.get(statespinner.getSelectedItemPosition()),"0","0",contactType);
					GetGeographyForAMVisitLog(Constants.GEOCODE_AREA_CUSTOMER,stateidd.get(statespinner.getSelectedItemPosition()),"0","0",contactType);
				}
				

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		cityspinner=(Spinner)findViewById(R.id.cityspinner);
		cityspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					//CityId=cityidd.get(cityspinner.getSelectedItemPosition());
					
					areaName.clear();
					areaNameId.clear();
					GetGeographyForAMVisitLog(Constants.GEOCODE_AREA_CUSTOMER,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(cityspinner.getSelectedItemPosition()),"0",contactType);
				}
				else {
					
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		areaspinner=(Spinner)findViewById(R.id.areaspinner);
		areaspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					
				
					AreaID=areaNameId.get(areaspinner.getSelectedItemPosition());
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	
		
		
		
		
		
		btn_search = (Button)findViewById(R.id.search);
		btn_search.setOnClickListener(this);
		
		mlistView = (ListView)findViewById(android.R.id.list);
		mlistView.setOnTouchListener(listTouchListener);
		mlistView.setOnItemClickListener(this);
		
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		super.onCheckedChanged(group, checkedId);
		
		switch (group.getId()) {
		
		case R.id.rg_contacttype:
			
			switch (checkedId) {
			
			case R.id.rb_dealer:
				cityname.clear();
				cityidd.clear();
				areaName.clear();
				areaNameId.clear();
				contactType = "D";//14
				contType= "14";
				
				GetGeographyForAMVisitLog(Constants.GEOCODE_STATE,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_AREA_CUSTOMER,"0","0","0",contactType);
				
				break;
				
			case R.id.rb_mechanic:
				cityname.clear();
				cityidd.clear();
				areaName.clear();
				areaNameId.clear();
				contactType = "M";//16
				contType= "16";
				GetGeographyForAMVisitLog(Constants.GEOCODE_STATE,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_AREA_CUSTOMER,"0","0","0",contactType);
				break;
				
			case R.id.rb_retailer:
				cityname.clear();
				cityidd.clear();
				areaName.clear();
				areaNameId.clear();
				contactType = "R";//15
				contType= "15";
				
				GetGeographyForAMVisitLog(Constants.GEOCODE_STATE,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_AREA_CUSTOMER,"0","0","0",contactType);
				break;

			default:
				break;
			}
			break;
			
		case R.id.rg_searchtype:
			
			switch (checkedId) {
			case R.id.rb_geo:
			//	GetAMTerettory();
			//	Function GetGeographyForAMVisitLog(ByVal LoginType As String, ByVal GeoType As String, ByVal ContactID As String, ByVal StateID As String, ByVal CityID As String, ByVal ContactType As String, ByVal UserID As String, ByVal Password As String) As String

				 
	
				GetGeographyForAMVisitLog(Constants.GEOCODE_STATE,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_CITY,"0","0","0",contactType);
				GetGeographyForAMVisitLog(Constants.GEOCODE_AREA_CUSTOMER,"0","0","0",contactType);
				
				mGeoLayout.setVisibility(View.VISIBLE);
				edttxt_mobileSearch.setVisibility(View.GONE);
				break;
				
			case R.id.rb_mobileNo:
				mGeoLayout.setVisibility(View.GONE);
				edttxt_mobileSearch.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(validation()){
			GetAMTerettoryCustomers();
			Intent intent= new Intent(DailyCallsActivity.this, AmTerettoryCustomersList.class);
			intent.putExtra("CONTACTTYPE", contType);
			intent.putExtra("stateID", StateId);
			intent.putExtra("cityID", CityId);
			intent.putExtra("areaID", AreaID);
			intent.putExtra("MobileNo", MobileNo);
			intent.putExtra(Constants.customerType, contactType);
			startActivity(intent);
		}
		
	}
	
	
	
	
	@Override
	public boolean validation() {
		
		boolean isvalid = true;
		
		String errorMsg = "Invalid Search!";
		int checkedid = rg_contactType.getCheckedRadioButtonId();
		
		if(checkedid==-1){
			isvalid = false; 
			errorMsg = errorMsg+" Please select contactType ";
		}
		checkedid = rg_searchType.getCheckedRadioButtonId();
		
		if(checkedid==-1){
			isvalid = false; 
			errorMsg = errorMsg+" Please select search Type ";
		}else{
			
			if(checkedid==R.id.rb_mobileNo){
				
				if(edttxt_mobileSearch.getText().toString().length()==0){
					isvalid = false; 
					edttxt_mobileSearch.setError(getResources().getString(R.string.mobilenumber));
				}
			}else{
				
				if(mspinnerState.getSelectedItemPosition()== AdapterView.INVALID_POSITION || mspinnerState.getSelectedItemPosition()==0){
					isvalid = false;
					errorMsg = errorMsg+" Please select state ";
				}
			}
		}
		
		if(!isvalid)
		Util.showToast(getCurrentContext(), errorMsg, Toast.LENGTH_LONG).show();
		
		
		return isvalid;
		
	};
	
	private void GetAMTerettoryCustomers() {
		
		
		int checkedId = rg_searchType.getCheckedRadioButtonId();
		
		
		if(checkedId==R.id.rb_geo){
			MobileNo = "";
			
			//datamap = (HashMap<String, String>) mspinnerState.getSelectedItem();
			//StateId = datamap.get(Constants.stateID);
			 StateId=stateidd.get(statespinner.getSelectedItemPosition()); 
			 CityId=cityidd.get(cityspinner.getSelectedItemPosition());;
			 AreaID=areaNameId.get(areaspinner.getSelectedItemPosition());
			
		}else{
			StateId = "0";
			CityId = "0";
			AreaID = "0";
			MobileNo = edttxt_mobileSearch.getText().toString();
		}
		
	/*	checkedId = rg_contactType.getCheckedRadioButtonId();
		if(checkedId==R.id.rb_dealer){
			contactType = "D";//14
			contType= "14";
		}else if(checkedId ==R.id.rb_mechanic){
			contactType = "M";//16
			contType= "16";
		}else if(checkedId ==R.id.rb_retailer){
			contactType = "R";//15
			contType= "15";
		}
		*/
		
		
	}

	
	 private void GetGeographyForAMVisitLog(final String geoName, String stateId, String cityID, String areaId, String ContactType) {
			GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
				@Override
				public void processResponse(Object response) {
					if(response!=null){
						arealist.clear();
						
						/*cityname.clear();
						cityidd.clear();
						areaName.clear();
						areaNameId.clear();*/
						
						arealist=new FetchingdataParser().getarealistparser(response.toString());
						if(arealist.size()==0){
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
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
							
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
								ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,statename);
								stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
								statespinner.setAdapter(stateAdapter);
							}
							
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
								ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,cityname);
								cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
								cityspinner.setAdapter(cityAdapter);
							}
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
								ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,areaName);
								AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
								areaspinner.setAdapter(AMAdapter);
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

			
				request.put("LoginType", "AM");
				request.put(Constants.GeoName,geoName);
				//request.put("GeoID","0");
				request.put("ContactID",passworddetails.getString(Constants.USERID,""));
				request.put(Constants.stateID,stateId );
				request.put(Constants.cityID,cityID );
				request.put(Constants.areaID, areaId);
				request.put("ContactType", contactType);
		
				
				request.put(Constants.username, passworddetails.getString("username",""));
				request.put(Constants.password,passworddetails.getString("password",""));


			} catch (Exception e) {
				e.printStackTrace();
			}

			String ipAddress=getResources().getString(R.string.ipaddress);
			String webService =getResources().getString(R.string.Webservice_mis_android);
			String nameSpace=getResources().getString(R.string.nameSpace);
			String methodName="GetGeographyForAMVisitLog";
			String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
			dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
			dataFromNetwork.sendData(request);
			dataFromNetwork.execute();
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
			// TODO Auto-generated method stub
			super.onItemClick(parent, view, postion, id);
			Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
			
		}
		
		
		
		
		
}
