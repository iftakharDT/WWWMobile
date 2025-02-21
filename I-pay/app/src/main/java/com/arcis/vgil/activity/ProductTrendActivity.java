package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProductTrendActivity extends BaseActivity{

	
	
	private Spinner countryspinner;
	private Spinner zonespinner;
	private Spinner statespinner;
	private Spinner districtspinner;
	private Spinner cityspinner;
	private Spinner areaspinner;
	private Spinner spnr_segment,spnr_appication,spnr_partNo;
	ArrayList<HashMap<String,Object>> arealist;
	private RadioGroup mRGroup;
	private Button btn_search;
	private TextView txt_quantity;
	private TextView txt_value;
	private TextView txt_total;
	
	private LinearLayout mlayoutdealer,mlayoutmechanic;
	private Spinner spnr_dealer,spnr_mechanic;

	ArrayList<String> countryname,countryid,areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,pincode,pincodeid;
	private ListView mlistView;
	Bundle bundle  = new Bundle();

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();

		setContentView(R.layout.product_trend_layout);
		setCurrentContext(this);

		arealist=new ArrayList<HashMap<String,Object>>();


		mRGroup = (RadioGroup)findViewById(R.id.rg_trend);
		mRGroup.setOnCheckedChangeListener(this);
		
		countryname=new ArrayList<String>();
		countryname.add("Please select");
		countryid  =new ArrayList<String>();
		countryid.add("0");

		zonename=new ArrayList<String>();
		zonename.add("Please Select");
		zoneid=new ArrayList<String>();
		zoneid.add("0");

		statename=new ArrayList<String>();
		statename.add("Please Select");
		stateidd=new ArrayList<String>();
		stateidd.add("0");


		districtname=new ArrayList<String>();
		districtname.add("Please Select");
		districtid=new ArrayList<String>();
		districtid.add("0");


		cityname=new ArrayList<String>();
		cityname.add("Please Select");
		cityidd=new ArrayList<String>();
		cityidd.add("0");

		areaname=new ArrayList<String>();
		areaname.add("Please Select");
		areaidd=new ArrayList<String>();
		areaidd.add("0");


		btn_search = (Button)findViewById(R.id.search);
		btn_search.setOnClickListener(this);

		View trendFilterlayout = findViewById(R.id.trendfilter);
		
		spnr_dealer = (Spinner)findViewById(R.id.dealerspinner);
		spnr_mechanic = (Spinner)findViewById(R.id.mechanicspinner);

		spnr_segment = (Spinner)trendFilterlayout.findViewById(R.id.segmentspinner);
		spnr_segment.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					@SuppressWarnings("unchecked")
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String segmentid = data.get(Constants.ID);
					getApplications(segmentid);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spnr_appication = (Spinner)trendFilterlayout.findViewById(R.id.applicationspinner);

		spnr_appication.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					@SuppressWarnings("unchecked")
                    HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String applicationid = data.get(Constants.ID);
					getPartNo(applicationid);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spnr_partNo = (Spinner)trendFilterlayout.findViewById(R.id.partnospinner);

		countryspinner=(Spinner)findViewById(R.id.countryspinner);

		countryspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					getGeographyByCode(Constants.GEOCODE_ZONE,countryid.get(countryspinner.getSelectedItemPosition()),"Zone");

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		zonespinner=(Spinner)findViewById(R.id.zonespinner);
		zonespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					getGeographyByCode(Constants.GEOCODE_STATE,zoneid.get(zonespinner.getSelectedItemPosition()),"State");

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		statespinner=(Spinner)findViewById(R.id.statespinner);
		statespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					getGeographyByCode(Constants.GEOCODE_DISTRICT, stateidd.get(statespinner.getSelectedItemPosition()),"District");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		districtspinner=(Spinner)findViewById(R.id.districtspinner);
		districtspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					getGeographyByCode(Constants.GEOCODE_CITY,districtid.get(districtspinner.getSelectedItemPosition()),"City");
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
					getGeographyByCode(Constants.GEOCODE_AREA,cityidd.get(cityspinner.getSelectedItemPosition()),"Area");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		areaspinner=(Spinner)findViewById(R.id.areaspinner);


		mlistView = (ListView)findViewById(android.R.id.list);
		mlistView.setOnItemClickListener(this);
		mlistView.setOnTouchListener(listTouchListener);


		txt_total = (TextView)findViewById(R.id.totalamount);
		txt_total.setOnClickListener(this);
		
		txt_quantity = (TextView)findViewById(R.id.total_quantity);
		txt_value    = (TextView)findViewById(R.id.total_value);
		
		mlayoutdealer = (LinearLayout)findViewById(R.id.dealerlayout);
		mlayoutmechanic = (LinearLayout)findViewById(R.id.mechaniclayout);
		
		getGeographyByCode(Constants.GEOCODE_COUNTRY,"1","country");
		getSegment();
		getPartNo(null);

	}

	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		
		if(checkedId==R.id.rb_dealer){
			
			mlayoutdealer.setVisibility(View.VISIBLE);
			mlayoutmechanic.setVisibility(View.GONE);
			getDealer();
		}else if(checkedId==R.id.rb_mechanic){
			mlayoutdealer.setVisibility(View.GONE);
			mlayoutmechanic.setVisibility(View.VISIBLE);
			getMechanic();
		}
	};
	

	private void getTrendReport(){
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				if(result!=null){

					ArrayList<HashMap<String , String>> trendList = FetchingdataParser.getProductTrendList(result.toString());

					int quantity = 0;
					double value = 0;
					String[] from = {Constants.PARTNO,Constants.quantity,Constants.VALUE};
					int[] to = {R.id.position1,R.id.position2,R.id.position3};
					if(trendList!=null){

						SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(), trendList, R.layout.product_trend_list_cell, from, to);
						mlistView.setAdapter(adapter);

						try{
							for (HashMap<String, String> datamap :trendList){
								quantity = quantity+ Integer.parseInt(datamap.get(Constants.quantity));
								value = value+ Double.parseDouble(datamap.get(Constants.VALUE));
								txt_quantity.setText(String.valueOf(quantity));
								txt_value.setText(String.valueOf(value));
							}
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
		String methodName="ProductTrend";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}

	private void getGeographyByCode(final String geoName, final String geoId, final String areaType) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading "+areaType+" Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist=new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
					}else {

						if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
							countryname.clear();
							countryname.add("Please Select");
							countryid.clear();
							countryid.add("0");
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
							zonename.clear();
							zonename.add("Please Select");
							zoneid.clear();
							zoneid.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
							stateidd.clear();
							statename.clear();
							statename.add("Please Select");
							stateidd.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_DISTRICT)){
							districtid.clear();
							districtname.clear();
							districtname.add("Please Select");
							districtid.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
							cityname.clear();
							cityidd.clear();
							cityname.add("Please Select");
							cityidd.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
							areaname.clear();
							areaidd.clear();
							areaname.add("Please Select");
							areaidd.add("0");
						}
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
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_DISTRICT)){

									if(!districtid.contains(geoids))
										districtid.add(geoids);
									if(!districtname.contains(geonames))
										districtname.add(geonames);
								}
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){

									if(!cityidd.contains(geoids))
										cityidd.add(geoids);
									if(!cityname.contains(geonames))
										cityname.add(geonames);
								}
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
									if(!areaidd.contains(geoids))
										areaidd.add(geoids);
									if(!areaname.contains(geonames))
										areaname.add(geonames);
								}else if(geoName.equalsIgnoreCase(Constants.GEOCODE_PINCODE)){
									if(!pincode.contains(geonames))
										pincode.add(geonames);
								}
							}
						}
						Log.d("zonee", zonename.toString());

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
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_DISTRICT)){
							ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,districtname);
							districtAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							districtspinner.setAdapter(districtAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
							ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,cityname);
							cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							cityspinner.setAdapter(cityAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
							ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,areaname);
							AreaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							areaspinner.setAdapter(AreaAdapter);
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
			request.put(Constants.GeoName,geoName);
			request.put(Constants.GeoID,geoId);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetGeographyByID";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


	
	
	
	
	/**
	 *  Get Partno for selected applicationId
	 * @param appicationID
	 */
	private void getPartNo(String appicationID){


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<PartNoModel> partnoList = FetchingdataParser.getPartNo(response.toString());
					if(partnoList!=null){
					//	SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.PRODUCTCODE}, new int[]{android.R.id.text1});
					//	segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
					//	spnr_partNo.setAdapter(segmentAdapter);

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

		if(appicationID==null){
			appicationID = "0";
		}
		try {
			request.put("ApplicationID",appicationID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductOfApplication";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


	/**
	 * Get Segment.
	 */
	private void getSegment(){


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
			request.put("SegmentId","0");
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegments";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	/**
	 *  Get Application for selected segment
	 * @param segmentID  
	 */
	private void getApplications(String segmentID){


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> applicationList = FetchingdataParser.getApplications(response.toString());
					if(applicationList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), applicationList, android.R.layout.simple_spinner_item, new String[]{Constants.APPLICATIONNAME}, new int[]{android.R.id.text1});
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
			request.put("SegmentId",segmentID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetApplicationOfSegments";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.search:
			if(validate()){
				// getData and start next screen
				getData();
				getTrendReport();
				
			}
			break;

		case R.id.totalamount:
			
			String trendtype = (String) getRequestDataMap().get(Constants.TRENDTYPE);
			if(trendtype.equals("D")){
				bundle.putString(Constants.TRENDTYPE, "Dealer");
			}else{
				bundle.putString(Constants.TRENDTYPE, "Mechanic");
			}
			bundle.putString(Constants.ProductID, "0");
			Intent intent  = new Intent(getCurrentContext(),ProductTrendMISScreen2Activity.class);
			intent.putExtras(bundle);
			intent.putExtra(CustomerTrendMISScreen2.REPORT_TYPE, 1);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		super.onItemClick(arg0, arg1, arg2, arg3);
		
		@SuppressWarnings("unchecked")
        HashMap<String, String> datamap = (HashMap<String, String>) arg0.getItemAtPosition(arg2);
		String trendtype = (String) getRequestDataMap().get(Constants.TRENDTYPE);
		if(trendtype.equals("D")){
			bundle.putString(Constants.TRENDTYPE, "Dealer");
		}else{
			bundle.putString(Constants.TRENDTYPE, "Mechanic");
		}
		bundle.putString(Constants.ProductID, datamap.get(Constants.ProductID));
		bundle.putString(Constants.NAME, datamap.get(Constants.PARTNO));
		// Report Type ==0 for when user clicks on listview reportType == 1 for Total;
		
		Intent intent  = new Intent(getCurrentContext(),ProductTrendMISScreen2Activity.class);
		intent.putExtras(bundle);
		intent.putExtra(CustomerTrendMISScreen2.REPORT_TYPE, 0);
		startActivity(intent);
	}
	@Override
	public String getStringFromResource(int StringCode) {
		// TODO Auto-generated method stub
		return super.getStringFromResource(StringCode);
	}

	private boolean validate(){

		boolean isvalid = true;
		StringBuilder errorMsg = new StringBuilder();
		errorMsg.append(getStringFromResource(R.string.error3));
		int selectedradiobtnId = mRGroup.getCheckedRadioButtonId();
		if(selectedradiobtnId==-1){
			isvalid = false;
			errorMsg.append(" Contact Type,");
		}
		
		
		/*position = countryspinner.getSelectedItemPosition();

		if(position==0 || position == AdapterView.INVALID_POSITION){
			isvalid = false;
			errorMsg.append(" Country,");
		}

		position = zonespinner.getSelectedItemPosition();

		if(position==0 || position == AdapterView.INVALID_POSITION){
			isvalid = false;
			errorMsg.append(" Zone,");
		}

		position = statespinner.getSelectedItemPosition();

		if(position==0 || position == AdapterView.INVALID_POSITION){
			isvalid = false;
			errorMsg.append(" State,");
		}
		position = cityspinner.getSelectedItemPosition();

		if(position==0 || position == AdapterView.INVALID_POSITION){
			isvalid = false;
			errorMsg.append(" City,");
		}*/

		if(!isvalid){

			Util.showToast(getCurrentContext(), errorMsg.toString(), Toast.LENGTH_LONG).show();
		}
		return isvalid;
	}

	@SuppressWarnings("unchecked")
	private void getData(){

		HashMap< String, String > dataMap = null;
		int selectedradiobtnId = mRGroup.getCheckedRadioButtonId();
		String tempValue = "";
		String customerID = "0";
		if(selectedradiobtnId==R.id.rb_dealer){
			tempValue = "D";
			if(spnr_dealer.getSelectedItemPosition()!=0){
				dataMap = (HashMap<String, String>) spnr_dealer.getSelectedItem();
				customerID = dataMap.get(Constants.ID);
			}
		}else if(selectedradiobtnId==R.id.rb_mechanic){
			tempValue = "M";
			if(spnr_mechanic.getSelectedItemPosition()!=0){
				dataMap = (HashMap<String, String>) spnr_mechanic.getSelectedItem();
				customerID = dataMap.get(Constants.ID);
			}
		}

		BaseActivity.getRequestDataMap().clear();
		getRequestDataMap().put(Constants.TRENDTYPE, tempValue);
		
		getRequestDataMap().put(Constants.CUSTID, customerID);

		if(countryspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && countryspinner.getSelectedItemPosition()>0){
			getRequestDataMap().put(Constants.countryID, countryid.get(countryspinner.getSelectedItemPosition()));
		}else{
			getRequestDataMap().put(Constants.countryID, "0");
		}
		
		if(zonespinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && zonespinner.getSelectedItemPosition()>0){
			getRequestDataMap().put(Constants.zoneID, zoneid.get(zonespinner.getSelectedItemPosition()));
		}else{
			getRequestDataMap().put(Constants.zoneID, "0");
		}
		
		if(statespinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && statespinner.getSelectedItemPosition()>0){
			getRequestDataMap().put(Constants.stateID, stateidd.get(statespinner.getSelectedItemPosition()));
		}else{
			getRequestDataMap().put(Constants.stateID, "0");
		}

		if(cityspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && cityspinner.getSelectedItemPosition()>0){
			getRequestDataMap().put(Constants.cityID, cityidd.get(cityspinner.getSelectedItemPosition()));
		}else{
			getRequestDataMap().put(Constants.cityID, "0");
		}

		tempValue = (String) areaspinner.getSelectedItem();

		if(tempValue!=null){
			getRequestDataMap().put(Constants.areaID, areaidd.get(areaspinner.getSelectedItemPosition()));
		}else{
			getRequestDataMap().put(Constants.areaID, "0");
		}

		dataMap = (HashMap<String, String>) spnr_partNo.getSelectedItem();

		if(dataMap!=null){
			getRequestDataMap().put(Constants.ProductID, dataMap.get(Constants.ID));
		}else{
			getRequestDataMap().put(Constants.ProductID, "0");
		
		}
		
		getRequestDataMap().put("IsMonthly", "0");
		
		if(countryspinner.getSelectedItemPosition()>0){
			bundle.putString(Constants.country, countryname.get(countryspinner.getSelectedItemPosition()));
		}
		
		if(zonespinner.getSelectedItemPosition()>0){
			bundle.putString(Constants.zone, zonename.get(zonespinner.getSelectedItemPosition()));
		}
		if(statespinner.getSelectedItemPosition()>0){
			bundle.putString(Constants.state, statename.get(statespinner.getSelectedItemPosition()));
		}
		if(cityspinner.getSelectedItemPosition()>0){
			bundle.putString(Constants.city, cityname.get(cityspinner.getSelectedItemPosition()));
		}
		
		if(areaspinner.getSelectedItemPosition()>0){
			bundle.putString(Constants.GEOCODE_AREA, areaname.get(areaspinner.getSelectedItemPosition()));
		}
		if(spnr_partNo.getSelectedItemPosition()!=0){
			HashMap<String, String> partnodata = (HashMap<String, String>) spnr_partNo.getSelectedItem();
			bundle.putString(Constants.PRODUCTCODE, partnodata.get(Constants.PRODUCTCODE));
		}

	}
	
	/**
	 *  Get List of dealer
	 */
	private void getDealer(){


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> partnoList = FetchingdataParser.getDealerList(response.toString());
					if(partnoList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.DEALERNAME}, new int[]{android.R.id.text1});
						segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spnr_dealer.setAdapter(segmentAdapter);

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
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAllActiveDealer";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	/**
	 *  Get List of Mechanic
	 */
	private void getMechanic(){


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> partnoList = FetchingdataParser.getMechanicList(response.toString());
					if(partnoList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
						segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spnr_mechanic.setAdapter(segmentAdapter);

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
			request.put("ContactType", "16");
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAllActiveRetailer";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

}
