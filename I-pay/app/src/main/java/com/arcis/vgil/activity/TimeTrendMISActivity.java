/**
 * 
 */
package com.arcis.vgil.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.model.TimeTrendReport;
import com.arcis.vgil.parser.FetchingdataParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Muni Mishra
 *
 */
public class TimeTrendMISActivity extends BaseActivity{


	private Spinner countryspinner;
	private Spinner zonespinner;
	private Spinner statespinner;
	private Spinner cityspinner;
	private Spinner areaspinner;

	private Spinner spnr_segment,spnr_appication,spnr_partNo;
	private Spinner spnr_contactType,spnr_contactName;

	ArrayList<HashMap<String,Object>> arealist;
	private Button btn_fromDate,btn_toDate,btn_search;
	private EditText edttxt_fromDate,edttxt_toDate;

	private String fromdate;
	private String toDate;

	private String getFromdate() {
		return fromdate;
	}

	private void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}


	private String getToDate() {
		return toDate;
	}

	private void setToDate(String toDate) {
		this.toDate = toDate;
	}
	private int reportTrend;
	private LinearLayout mtrenddataLayout;



	private int getReportType() {
		return reportTrend;
	}

	private void setReportType(int reportTrend) {
		this.reportTrend = reportTrend;
	}
	
	ArrayList<String> countryname,countryid,areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,pincode,pincodeid;
	private String[] contacttypedata = {"Please select","State Incharge","Area Manager","Dealer","Mechanic"};

	private enum ContactName{

		STATE_INCHARGE(1),
		AREA_MANAGER(2),
		DEALER(3),
		MECHANIC(4);

		private final int value;
		private ContactName(int value){
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.time_trend_mis_layout);
		setCurrentContext(this);

		arealist=new ArrayList<HashMap<String,Object>>();

		btn_fromDate = (Button)findViewById(R.id.fromdate);
		btn_fromDate.setOnClickListener(this);

		btn_toDate = (Button)findViewById(R.id.todate);
		btn_toDate.setOnClickListener(this);

		btn_search  = (Button)findViewById(R.id.search);
		btn_search.setOnClickListener(this);

		edttxt_fromDate = (EditText)findViewById(R.id.fromdatetext);
		edttxt_toDate  = (EditText)findViewById(R.id.todatetext);
		
		mtrenddataLayout = (LinearLayout)findViewById(R.id.reportlayout);

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



		spnr_segment = (Spinner)findViewById(R.id.segmentspinner);
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

		spnr_appication = (Spinner)findViewById(R.id.applicationspinner);
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
		spnr_partNo = (Spinner)findViewById(R.id.partnospinner);

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
					getGeographyByCode(Constants.GEOCODE_CITY, stateidd.get(statespinner.getSelectedItemPosition()),"City");
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

		spnr_contactType = (Spinner)findViewById(R.id.contacttypespinner);
		ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,contacttypedata);
		countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spnr_contactType.setAdapter(countryAdapter);
		
		spnr_contactType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					// Get Contact Name
					switch (position) {
					case 1:
						// Get State Incharge Names
						getContactNames(ContactName.STATE_INCHARGE.getValue());
						setReportType(ContactName.STATE_INCHARGE.getValue());
						break;
					case 2:
						// Get Area MAnager Names
						getContactNames(ContactName.AREA_MANAGER.getValue());
						setReportType(ContactName.AREA_MANAGER.getValue());
						break;
					case 3:
						// Get Dealer Names
						getContactNames(ContactName.DEALER.getValue());
						setReportType(ContactName.DEALER.getValue());
						break;
					case 4:
						// Get Mechanic Name.
						getContactNames(ContactName.MECHANIC.getValue());
						setReportType(ContactName.MECHANIC.getValue());
						break;

					default:
						break;
					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spnr_contactName = (Spinner)findViewById(R.id.contactnamespinner);

		getGeographyByCode(Constants.GEOCODE_COUNTRY,"1","country");
		getSegment();
		getPartNo(null);
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
						//SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.PRODUCTCODE}, new int[]{android.R.id.text1});
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
	public boolean validation() {
		// TODO Auto-generated method stub

		boolean isvalid = true;
		String msg = getStringFromResource(R.string.error3);
		if(TextUtils.isEmpty(edttxt_fromDate.getText().toString())){
			isvalid = false;
			edttxt_fromDate.setError(getStringFromResource(R.string.startdate));
		}
		if(TextUtils.isEmpty(edttxt_toDate.getText().toString())){
			isvalid = false;
			edttxt_toDate.setError(getStringFromResource(R.string.enddate));
		}
		int index = countryspinner.getSelectedItemPosition();
		if(index== AdapterView.INVALID_POSITION || index== 0){

			msg = msg.concat(" "+getStringFromResource(R.string.country));
			isvalid = false;
		}
		index = spnr_contactType.getSelectedItemPosition();

		if(index== AdapterView.INVALID_POSITION || index== 0){
			msg = msg.concat(" "+getStringFromResource(R.string.contacttype));
			isvalid = false;
		}

		if(!isvalid){
			Toast.makeText(getCurrentContext(), msg, Toast.LENGTH_LONG).show();
		}

		return isvalid;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		final Calendar calendar= Calendar.getInstance();
		DatePickerDialog datePickerDialog = null;
		switch (v.getId()) {
		case R.id.fromdate:


			datePickerDialog= new DatePickerDialog(getCurrentContext(), new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int date) {

					Calendar cal  = Calendar.getInstance();
					int currmonth  = (cal.get(Calendar.MONTH)+1);
					int currDate = cal.get(Calendar.DAY_OF_MONTH);
					
						edttxt_fromDate.setText(String.valueOf(date)+"/"+ String.valueOf(month+1)+"/"+ String.valueOf(year));
						setFromdate(String.valueOf(month+1)+"-"+ String.valueOf(date)+"-"+ String.valueOf(year));
					
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
			datePickerDialog.show();
			break;

		case R.id.todate:


			datePickerDialog= new DatePickerDialog(getCurrentContext(), new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int date) {

					Calendar cal  = Calendar.getInstance();
					int currmonth  = (cal.get(Calendar.MONTH)+1);
					int currDate = cal.get(Calendar.DAY_OF_MONTH);
					
						edttxt_toDate.setText(String.valueOf(date)+"/"+ String.valueOf(month+1)+"/"+ String.valueOf(year));
						setToDate(String.valueOf(month+1)+"-"+ String.valueOf(date)+"-"+ String.valueOf(year));

				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
			datePickerDialog.show();
			break;

		case R.id.search:
			if(validation()){

				int month =  getDateDifferance(edttxt_fromDate.getText().toString(), edttxt_toDate.getText().toString());
				Log.i("Diff in Month", String.valueOf(month));
				getTrendReport(getReportType());

			}
			break;

		default:
			break;
		}



	}

	/**
	 * Get Contact Names based on selection of ContactType value.
	 * @param nameType
	 */
	private void getContactNames(int nameType){

		String MethodName = null;
		String countryID = "0";
		String zoneID = "0";
		String stateId = "0";
		String cityID = "0";
		String areaID = "0";

		if(countryspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && countryspinner.getSelectedItemPosition()!=0){
			countryID = countryid.get(countryspinner.getSelectedItemPosition());
		}
		int index= zonespinner.getSelectedItemPosition();
		if(zonespinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && zonespinner.getSelectedItemPosition()!=0){
			zoneID = zoneid.get(zonespinner.getSelectedItemPosition());
		}
		if(statespinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && statespinner.getSelectedItemPosition()!=0){
			stateId = stateidd.get(statespinner.getSelectedItemPosition());
		}
		if(cityspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && cityspinner.getSelectedItemPosition()!=0){
			cityID = cityidd.get(cityspinner.getSelectedItemPosition());
		}
		if(areaspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && areaspinner.getSelectedItemPosition()!=0){
			areaID = areaidd.get(areaspinner.getSelectedItemPosition());
		}



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> dataList = null;
					SimpleAdapter adapter = null;

					if(getReportType()== ContactName.STATE_INCHARGE.getValue()){

						dataList = FetchingdataParser.getStateInchargeList(response.toString());
						if(dataList.size()>0){

							adapter = new SimpleAdapter(getCurrentContext(), dataList, android.R.layout.simple_spinner_item, new String[]{Constants.STATE_INCHARGE}, new int[]{android.R.id.text1});
							adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_contactName.setAdapter(adapter);
						}
					}else if(getReportType()== ContactName.AREA_MANAGER.getValue()){

						dataList = FetchingdataParser.getAreaManagerList(response.toString());
						if(dataList.size()>0){

							adapter = new SimpleAdapter(getCurrentContext(), dataList, android.R.layout.simple_spinner_item, new String[]{Constants.AREAMANAGER}, new int[]{android.R.id.text1});
							adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_contactName.setAdapter(adapter);
						}
					}else if(getReportType()== ContactName.DEALER.getValue() || getReportType()== ContactName.MECHANIC.getValue()){

						dataList = FetchingdataParser.getExternalContactListList(response.toString());
						if(dataList.size()>0){
							adapter = new SimpleAdapter(getCurrentContext(), dataList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
							adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_contactName.setAdapter(adapter);
						}


					}

				}else{
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);


		request.put(Constants.countryID,countryID);
		request.put(Constants.zoneID,zoneID);
		request.put(Constants.stateID,stateId);

		try {

			if(nameType== ContactName.STATE_INCHARGE.getValue()){
				MethodName = "GetStateIncharge";


			}else if(nameType== ContactName.AREA_MANAGER.getValue()){
				MethodName = "GetAreaManager";
				request.put(Constants.cityID,cityID);
				request.put(Constants.areaID,areaID);
			}else if(nameType== ContactName.DEALER.getValue() || nameType== ContactName.MECHANIC.getValue()){

				MethodName = "GetExternalContact";
				request.put(Constants.cityID,cityID);
				request.put(Constants.areaID,areaID);
				if(nameType== ContactName.DEALER.getValue()){
					request.put(Constants.CONTACTTYPEID_1,Constants.CONTACT_TYPE_ID_DEALER);
				}else{
					request.put(Constants.CONTACTTYPEID_1,Constants.CONTACT_TYPE_ID_MECHANIC);
				}

			}

			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName=MethodName;
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


	private void getTrendReport(int nameType){

		String MethodName = null;
		String countryID = "0";
		String zoneID = "0";
		String stateId = "0";
		String cityID = "0";
		String areaID = "0";
		String segmentID = "0";
		String applicationID = "0";
		String partId = "0";
		String contactTypeId = "0";
		String contactNameID = "0";

		HashMap<String, String> dataMap = null;

		if(countryspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && countryspinner.getSelectedItemPosition()!=0){
			countryID = countryid.get(countryspinner.getSelectedItemPosition());
		}

		if(zonespinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && zonespinner.getSelectedItemPosition()!=0){
			zoneID = zoneid.get(zonespinner.getSelectedItemPosition());
		}
		if(statespinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && statespinner.getSelectedItemPosition()!=0){
			stateId = stateidd.get(statespinner.getSelectedItemPosition());
		}
		if(cityspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && cityspinner.getSelectedItemPosition()!=0){
			cityID = cityidd.get(cityspinner.getSelectedItemPosition());
		}
		if(areaspinner.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && areaspinner.getSelectedItemPosition()!=0){
			areaID = areaidd.get(areaspinner.getSelectedItemPosition());
		}
		if(spnr_appication.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && spnr_appication.getSelectedItemPosition()!=0){

			dataMap = (HashMap<String, String>) spnr_appication.getSelectedItem();
			applicationID = dataMap.get(Constants.ID);

		}
		if(spnr_segment.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && spnr_segment.getSelectedItemPosition()!=0){

			dataMap = (HashMap<String, String>) spnr_segment.getSelectedItem();
			segmentID = dataMap.get(Constants.ID);

		}
		if(spnr_partNo.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && spnr_partNo.getSelectedItemPosition()!=0){

			dataMap = (HashMap<String, String>) spnr_partNo.getSelectedItem();
			partId = dataMap.get(Constants.ID);

		}
		if(spnr_contactType.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && spnr_contactType.getSelectedItemPosition()!=0){


			contactTypeId = (String) spnr_contactType.getSelectedItem();

		}
		if(spnr_contactName.getSelectedItemPosition()!= AdapterView.INVALID_POSITION && spnr_contactName.getSelectedItemPosition()!=0){

			dataMap = (HashMap<String, String>) spnr_contactName.getSelectedItem();
			contactNameID = dataMap.get(Constants.ID);

		}




		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){


					ArrayList<TimeTrendReport> reportList = FetchingdataParser.getTrendReportStateInchargeList(response.toString());
					
					if(mtrenddataLayout.getChildCount()>1){
						mtrenddataLayout.removeViewAt(1);
					}
					
					if(reportList.size()>0){
					
						View view  = getLayoutInflater().inflate(R.layout.trend_mis_cell_layout, null);
						
						TextView txt_segment = (TextView)view.findViewById(R.id.textView1);
						TextView txt_application = (TextView)view.findViewById(R.id.textView2);
						TextView txt_partNo  = (TextView)view.findViewById(R.id.textView3);
						TextView txt_name  = (TextView)view.findViewById(R.id.textView4);
						TextView txt_country  = (TextView)view.findViewById(R.id.textView5);
						TextView txt_zone  = (TextView)view.findViewById(R.id.textView6);
						TextView txt_state  = (TextView)view.findViewById(R.id.textView7);
						TextView txt_city  = (TextView)view.findViewById(R.id.textView8);
						TextView txt_area  = (TextView)view.findViewById(R.id.textView9);
						TextView txt_month  = (TextView)view.findViewById(R.id.textView10);
						TextView txt_sale = (TextView)view.findViewById(R.id.textView11);
						TextView txt_fitment = (TextView)view.findViewById(R.id.textView12);
						
						for(int i=0;i<reportList.size();i++){
							
							TimeTrendReport report = reportList.get(i);
							txt_segment.setText("--");
							txt_segment.setTypeface(null, Typeface.NORMAL);
							
							txt_application.setText("--");
							txt_application.setTypeface(null, Typeface.NORMAL);
							
							txt_partNo.setText(report.getPartNo());
							txt_partNo.setTypeface(null, Typeface.NORMAL);
							
							txt_name.setText(report.getCategory());
							txt_name.setTypeface(null, Typeface.NORMAL);
							
							txt_country.setText(report.getCountry());
							txt_country.setTypeface(null, Typeface.NORMAL);
							
							txt_zone.setText(report.getZone());
							txt_zone.setTypeface(null, Typeface.NORMAL);
							
							txt_state.setText(report.getState());
							txt_state.setTypeface(null, Typeface.NORMAL);
							
							txt_city.setText(report.getCity());
							txt_city.setTypeface(null, Typeface.NORMAL);
							
							txt_area.setText(report.getArea());
							txt_area.setTypeface(null, Typeface.NORMAL);
							
							txt_month.setText(report.getMonth());
							txt_month.setTypeface(null, Typeface.NORMAL);
							
							txt_sale.setText(String.valueOf(report.getSale()));
							txt_sale.setTypeface(null, Typeface.NORMAL);
							
							txt_fitment.setText(String.valueOf(report.getFitment()));
							txt_fitment.setTypeface(null, Typeface.NORMAL);
							
							mtrenddataLayout.removeView(view);
							mtrenddataLayout.addView(view, i+1);
						}
					}

				}else{
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);


		request.put("InvoiceStartDate", getFromdate());
		request.put("InvoiceEndDate", getToDate());
		request.put(Constants.countryID,countryID);
		request.put(Constants.zoneID,zoneID);
		request.put(Constants.stateID,stateId);
		request.put(Constants.cityID,cityID);
		request.put(Constants.areaID,areaID);

		try {

			if(nameType== ContactName.STATE_INCHARGE.getValue()){
				MethodName = "StateInchargeTimeTrend";
				request.put("StateIncharge",contactNameID);

			}else if(nameType== ContactName.AREA_MANAGER.getValue()){
				MethodName = "AreaManagerTimeTrend";

				request.put("AreaManager",contactNameID);

			}else if(nameType== ContactName.DEALER.getValue() || nameType== ContactName.MECHANIC.getValue()){

				if(nameType== ContactName.DEALER.getValue()){
					MethodName = "DealerTimeTrend";
					request.put("DealerID",contactNameID);
				}else{

					MethodName = "MechanicTimeTrend";
					request.put("MechanicID",contactNameID);
				}
			}

			request.put(Constants.SEGMENTID,segmentID);
			request.put(Constants.APPLICATIONID,applicationID);
			request.put(Constants.PARTNOID,partId);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName=MethodName;
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private int getDateDifferance(String fromDate, String toDate){

		SimpleDateFormat format  = new SimpleDateFormat("dd/mm/yyyy");
		Date fdate  = null;
		Date tdate = null;

		try {
			fdate = (Date) format.parse(fromDate);
			tdate = (Date) format.parse(toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long diff = tdate.getTime()- fdate.getTime();
		int month = (int) (diff/(12*24*60*60*1000));
		return month;
	}


}
