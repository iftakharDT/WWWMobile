package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DirectDealerGitGoodAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class AmCallPlanner extends BaseActivity {
    private Spinner statespinner;
	private Spinner cityspinner;
	private Spinner areaspinner;
	

	private RadioGroup rg_contactType,rg_class_type;
	int lastVisited=0;
	String contType;
	String class_Type;
	String StateId ,CityId,AreaID,MobileNo, contactType = "";
	ArrayList<HashMap<String,Object>> arealist;
	private ListView listView;
	private Button get;
	private DirectDealerGitGoodAdapter adapter;
	private EditText et_visited_days;
	ArrayList<String> areaNameId,areaName,areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,pincode,pincodeid;
   
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.am_call_planner);
		setCurrentContext(this);
		listView=(ListView) findViewById(R.id.listView1);
		et_visited_days=(EditText) findViewById(R.id.et_visited_days);
		get=(Button) findViewById(R.id.search);
		get.setOnClickListener(this);
		rg_contactType = (RadioGroup)findViewById(R.id.rg_contacttype);
		
		rg_contactType.setOnCheckedChangeListener(this);
		
		rg_class_type = (RadioGroup)findViewById(R.id.rg_class_type);
		rg_class_type.setOnCheckedChangeListener(this);
		
		statespinner = (Spinner) findViewById(R.id.statespinner);
		cityspinner  = (Spinner) findViewById(R.id.cityspinner);
		areaspinner = (Spinner) findViewById(R.id.areaspinner);
        
		statename=new ArrayList<String>();
		stateidd=new ArrayList<String>();
		cityname=new ArrayList<String>();
        cityidd=new ArrayList<String>();
		areaName=new ArrayList<String>();
        areaNameId=new ArrayList<String>();
        arealist=new ArrayList<HashMap<String,Object>>();
		
		
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
			
          case R.id.rg_class_type:
			
			switch (checkedId) {
			case R.id.rb_a:
				class_Type="A";
			
				break;
				
			case R.id.rb_b:
				class_Type="B";
				
				break;
				
            case R.id.rb_c:
            	class_Type="C";
				break;
			default:
				break;
			}
			break;
		

		default:
			break;
		}
	}
	
	 private void GetGeographyForAMVisitLog(final String geoName, String stateId, String cityID, String areaId, String ContactType) {
			GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
				@Override
				public void processResponse(Object response) {
					if(response!=null){
						arealist.clear();
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
		public boolean validation() {
			
			boolean isvalid = true;
			
			String errorMsg = "Invalid Search!";
			int checkedid = rg_contactType.getCheckedRadioButtonId();
			int checkClass_Id=rg_class_type.getCheckedRadioButtonId();
			
			if(checkedid==-1){
				isvalid = false; 
				errorMsg = errorMsg+" Please select contactType ";
			}

			
			if(statespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION ||statespinner.getSelectedItemPosition()==0){
				isvalid = false;
				errorMsg = errorMsg+" Please select state ";
			}
			if(checkClass_Id==-1){
				isvalid = false; 
				errorMsg = errorMsg+" Please select Class Type ";
			}
		
			
			if(!isvalid)
			Util.showToast(getCurrentContext(), errorMsg, Toast.LENGTH_LONG).show();
			
			
			return isvalid;
			
		};
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(validation()){
				GetAMTerettoryCustomers();
				Intent intent= new Intent(AmCallPlanner.this, AmCallPlannerDetails.class);
				intent.putExtra("CONTACTTYPE", contType);
		        intent.putExtra("stateID", StateId);
				intent.putExtra("cityID", CityId);
				intent.putExtra("areaID", AreaID);
				intent.putExtra("Class_type", class_Type);
				intent.putExtra("Last_visited", lastVisited);
				intent.putExtra(Constants.customerType, contactType);
				startActivity(intent);
			}
			
		}
		
		private void GetAMTerettoryCustomers() {
			
			     MobileNo = "";
				 StateId=stateidd.get(statespinner.getSelectedItemPosition()); 
				 CityId=cityidd.get(cityspinner.getSelectedItemPosition());;
				 AreaID=areaNameId.get(areaspinner.getSelectedItemPosition());
			
			if (et_visited_days.getText().length()>0) {
				lastVisited= Integer.parseInt(et_visited_days.getText().toString()) ;
			}
			
		}

		
	
}
