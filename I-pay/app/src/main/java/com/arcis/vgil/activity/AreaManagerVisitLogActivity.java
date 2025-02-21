package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Questions;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AreaManagerVisitLogActivity extends BaseActivity {


	Spinner /*countryspinner,*/statespinner,areaspinner,/*zonespinner,*/cityspinner/*,    districtspinner,pincodeSpinner*/;
	Spinner partyTypeSpinner, partyspinner;
	ArrayList<String> countryname,countryid, areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,pincode,pincodeid;
	ArrayList<HashMap<String,Object>> arealist;
	private LinearLayout questionLayout;
	EditText q1,q2,q3,q4,q5;
	EditText edttxt_startDate, edttxt_endDate,edttxt_starttime,edttxt_endtime,edttxt_pov,edttxt_mettingNotes;

	private Button btn_startDate,btn_timestart,btn_endDate,btn_timeend;
	private Button btn_save;

	private String areaId;
	private String partyTypeId;
	private String partyId = "";

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyTypeId() {
		return partyTypeId;
	}

	public void setPartyTypeId(String partyTypeId) {
		this.partyTypeId = partyTypeId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Override
 	 public void inti() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_area_manager_visit_log_layout);
		setCurrentContext(AreaManagerVisitLogActivity.this);
		initData();

		questionLayout = (LinearLayout)findViewById(R.id.qlayout);
		questionLayout.setVisibility(View.GONE);

		q1 = (EditText)findViewById(R.id.editText_Q1);
		q1.setVisibility(View.GONE);
		
		q2 = (EditText)findViewById(R.id.editText_Q2);
		q2.setVisibility(View.GONE);
		
		q3 = (EditText)findViewById(R.id.editText_Q3);
		q3.setVisibility(View.GONE);
		
		q4 = (EditText)findViewById(R.id.editText_Q4);
		q4.setVisibility(View.GONE);
		
		q5 = (EditText)findViewById(R.id.editText_Q5);
		q5.setVisibility(View.GONE);

		edttxt_startDate = (EditText)findViewById(R.id.editText_statrDate);
		edttxt_endDate = (EditText)findViewById(R.id.editText_endDate);

		edttxt_starttime = (EditText)findViewById(R.id.editText_starttime);
		edttxt_endtime = (EditText)findViewById(R.id.editText_endtime);
		
		edttxt_pov = (EditText)findViewById(R.id.editText_purposeofvisit);
		edttxt_mettingNotes = (EditText)findViewById(R.id.editText_meetingnotes);
		


		 partyTypeSpinner = (Spinner)findViewById(R.id.partytypespinner);

		 partyTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			  public void onItemSelected(AdapterView<?> arg0, View arg1,
                                         int position, long arg3) {
				if(position!=0){

					HashMap<String, String> datamap = (HashMap<String, String>) arg0.getItemAtPosition(position);
					String id = datamap.get(Constants.ID);
				
					if(getAreaId()!=null){
						GetPartyTypeQuestions(id);
						GetParty(getAreaId(), id);
						setPartyTypeId(id);
					}else{
						Util.showToast(getCurrentContext(), "Please select Area", Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		partyspinner     = (Spinner)findViewById(R.id.partyspinner);
 	 	partyspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			 public void onItemSelected(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2!=0){
					HashMap<String, Object> data = (HashMap<String, Object>) arg0.getSelectedItem();
					String id = data.get(Constants.ID).toString();
					setPartyId(id);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		btn_startDate = (Button)findViewById(R.id.startdate);
		btn_startDate.setOnClickListener(this);

		btn_endDate = (Button)findViewById(R.id.enddate);
		btn_endDate.setOnClickListener(this);

		btn_timestart = (Button)findViewById(R.id.starttime);
		btn_timestart.setOnClickListener(this);

		btn_timeend = (Button)findViewById(R.id.endtime);
		btn_timeend.setOnClickListener(this);

		btn_save = (Button)findViewById(R.id.save);
		btn_save.setOnClickListener(this);

	/*	countryspinner=(Spinner)findViewById(R.id.countryspinner);
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

		*/
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

		/*districtspinner=(Spinner)findViewById(R.id.districtspinner);
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
	
		*/
		
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
		areaspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2!=0){
					String areaId = areaidd.get(areaspinner.getSelectedItemPosition());
					setAreaId(areaId);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		getGeographyByCode(Constants.GEOCODE_COUNTRY, "1", "Country");
		GetPartyType();

	}

	@Override
	public void getScreenData() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean validation() {
		// TODO Auto-generated method stub
		
		boolean isValidated = true;
		
		String errMsg = getResources().getString(R.string.error3);
		if(areaspinner.getSelectedItemPosition()==0 ||areaspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			
			errMsg = errMsg.concat( " "+getResources().getString(R.string.area));
			isValidated =false;
		}
		// Party Type Id.
		
		if(partyTypeSpinner.getSelectedItemPosition()==0 ||partyTypeSpinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			
			errMsg = errMsg.concat(" "+ getStringFromResource(R.string.partytype));
			isValidated = false;
		}
		if(partyspinner.getSelectedItemPosition()==0 ||partyspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			errMsg = errMsg.concat(" "+ getStringFromResource(R.string.party));
			isValidated = false;
		}
		if(edttxt_pov.getText().toString().length()==0){
			edttxt_pov.setError(getStringFromResource(R.string.purposeofvisit));
			isValidated = false;
		}
		if(edttxt_mettingNotes.getText().toString().length()==0){
			edttxt_mettingNotes.setError(getStringFromResource(R.string.meetingnotes));
			isValidated = false;
		}
		if(q1.getText().toString().length()==0){
			q1.setError(getStringFromResource(R.string.question));
			isValidated = false;
		}
		if(edttxt_startDate.getText().toString().length()==0){
			edttxt_startDate.setError(getStringFromResource(R.string.startdate));
			isValidated = false;
		}
		if(edttxt_endDate.getText().toString().length()==0){
			edttxt_endDate.setError(getStringFromResource(R.string.enddate));
			isValidated = false;
		}
		if(edttxt_starttime.getText().toString().length()==0){
			edttxt_starttime.setError(getStringFromResource(R.string.time));
			isValidated = false;
		}
		if(edttxt_endtime.getText().toString().length()==0){
			edttxt_endtime.setError(getStringFromResource(R.string.time));
			isValidated = false;
		}
		
		if(!isValidated){
			Toast.makeText(getCurrentContext(), errMsg, Toast.LENGTH_LONG).show();
		}
			
		return isValidated;
	
	}


	private void GetPartyType() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading Party Type..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				ArrayList<HashMap<String, String>> partytypelist ;
				if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}else{
					if(response!=null){
						partytypelist = new FetchingdataParser().getPartTypeList(response.toString());
						if(partytypelist.size()==0){
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
						}else {

							SimpleAdapter partytypeadapter  = new SimpleAdapter(getCurrentContext(), partytypelist, android.R.layout.simple_spinner_item, new String[]{Constants.TYPE}, new int[]{android.R.id.text1});
							partytypeadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							partyTypeSpinner.setAdapter(partytypeadapter);

						}
					}
				}
			}});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetVisitType";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


	 private void GetParty(String areaId, String partyTypeId) {
		 GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading Party..", new GetDataCallBack() {
			@Override
		 	public void processResponse(Object response) {
				ArrayList<HashMap<String, String>> partytypelist ;
				if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}else{
					if(response!=null){
						partytypelist = new FetchingdataParser().getPartyList(response.toString());
						if(partytypelist.size()==0){
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
						}else {

							SimpleAdapter partytypeadapter  = new SimpleAdapter(getCurrentContext(), partytypelist, android.R.layout.simple_spinner_item, new String[]{Constants.PREF_NAME}, new int[]{android.R.id.text1});
							partytypeadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							partyspinner.setAdapter(partytypeadapter);

						}
					}
				}
			}});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {

			request.put(Constants.PARTYTYPE, partyTypeId);
			request.put(Constants.AREAID, areaId);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetPartyList";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	/**
	 * Get Party Type Questions.
	 * @param partyTypeId
	 */
	private void GetPartyTypeQuestions(String partyTypeId) {
		 GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading Party..", new GetDataCallBack() {
			@Override
		 	public void processResponse(Object response) {
				if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}else{
					if(response!=null){
						ArrayList<Questions> questionList = FetchingdataParser.getQuestionsList(response.toString());
						if(questionList.size()==0){
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
						}else{
							questionLayout.setVisibility(View.VISIBLE);
							for(int i=0;i<questionList.size();i++)
							{
								Questions q = questionList.get(i);
								switch (i) {
								case 0:
									q1.setVisibility(View.VISIBLE);
									q1.setHint(q.getQuestion());
									q1.setTag(q.getQuestion());
									break;
								case 1:
									q2.setVisibility(View.VISIBLE);
									q2.setTag(q.getQuestion());
									q2.setHint(q.getQuestion());
									break;
								case 2:
									q3.setVisibility(View.VISIBLE);
									q3.setTag(q.getQuestion());
									q3.setHint(q.getQuestion());
									break;
								case 3:
									q4.setVisibility(View.VISIBLE);
									q4.setTag(q.getQuestion());
									q4.setHint(q.getQuestion());
									break;
								case 4:
									q5.setVisibility(View.VISIBLE);
									q5.setTag(q.getQuestion());
									q5.setHint(q.getQuestion());
									break;
								default:
									break;
								}
							}

						}

					}
				}
			}});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {

			request.put(Constants.PARTYTYPE, partyTypeId);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetQuestionList";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	
	
	private void getGeographyByCode(final String geoName, final String geoId, final String areaType) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AreaManagerVisitLogActivity.this, ProgressDialog.STYLE_SPINNER,"Loading "+areaType+" Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist=new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(AreaManagerVisitLogActivity.this,getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
					}else {

						if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
							countryname.clear();
							countryname.add("Pleade Select");
							countryid.clear();
							countryid.add("0");
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
							zonename.clear();
							zonename.add("Pleade Select");
							zoneid.clear();
							zoneid.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
							stateidd.clear();
							statename.clear();
							statename.add("Pleade Select");
							stateidd.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_DISTRICT)){
							districtid.clear();
							districtname.clear();
							districtname.add("Pleade Select");
							districtid.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
							cityname.clear();
							cityidd.clear();
							cityname.add("Pleade Select");
							cityidd.add("0");
						}
						else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
							areaname.clear();
							areaidd.clear();
							areaname.add("Pleade Select");
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

						/*if(geoName.equalsIgnoreCase(Constants.GEOCODE_COUNTRY)){
							ArrayAdapter<String> countryAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,countryname);
							countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							countryspinner.setAdapter(countryAdapter);
						}*/
						/*if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
							ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,zonename);
							zoneAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							zonespinner.setAdapter(zoneAdapter);
						}*/
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
							ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,statename);
							stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							statespinner.setAdapter(stateAdapter);
						}
						/*if(geoName.equalsIgnoreCase(Constants.GEOCODE_DISTRICT)){
							ArrayAdapter<String> districtAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,districtname);
							districtAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							districtspinner.setAdapter(districtAdapter);
						}*/
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
					Toast.makeText(AreaManagerVisitLogActivity.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
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

	private void initData(){
		countryname=new ArrayList<String>();
		countryname.add("Please Select");
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

		areaname=new ArrayList<String>();
		areaname.add("Please Select");
		areaidd=new ArrayList<String>();
		areaidd.add("0");

		cityname=new ArrayList<String>();
		cityname.add("Please Select");
		cityidd=new ArrayList<String>();
		cityidd.add("0");

		arealist=new ArrayList<HashMap<String,Object>>();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		final
        Calendar cal = Calendar.getInstance();
		 DatePickerDialog datePickerDialog;
		 TimePickerDialog timepickerDialog ;
		switch (v.getId()) {
		case R.id.save:
			if(validation())
			 uploadData();
			break;
		case R.id.startdate:
			  datePickerDialog= new DatePickerDialog(getCurrentContext(), new DatePickerDialog.OnDateSetListener() {
				 
				 int month = cal.get(Calendar.MONTH);
				 int date = cal.get(Calendar.DATE);
				 int year = cal.get(Calendar.YEAR);
				@Override
				  public void onDateSet(DatePicker view, int year, int month, int date) {
					
					if( date<=this.date && month<=this.month && year<=this.year){
						edttxt_startDate.setText(String.valueOf(month+1)+"/"+ String.valueOf(date)+"/"+ String.valueOf(year));
						
					}else{
						Toast.makeText(view.getContext(), getResources().getString(R.string.datenotapplicable), Toast.LENGTH_SHORT).show();
					}

				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
			datePickerDialog.setMessage("Please Select Date");
			datePickerDialog.show();
			break;
		case R.id.enddate:
			
                  datePickerDialog= new DatePickerDialog(getCurrentContext(), new DatePickerDialog.OnDateSetListener() {
				 
                	  int month = cal.get(Calendar.MONTH);
     				 int date = cal.get(Calendar.DATE);
     				 int year = cal.get(Calendar.YEAR);
     				 
				@Override
				  public void onDateSet(DatePicker view, int year, int month, int date) {
					
					if(date<=this.date && month<=this.month && year<=this.year ){
						edttxt_endDate.setText(String.valueOf(month+1)+"/"+ String.valueOf(date)+"/"+ String.valueOf(year));
					}else{
						Toast.makeText(view.getContext(), getResources().getString(R.string.datenotapplicable), Toast.LENGTH_SHORT).show();
					}

				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
			datePickerDialog.setMessage("Please Select Date");
			datePickerDialog.show();
			break;
			
		case R.id.starttime:
			
			 timepickerDialog = new TimePickerDialog(getCurrentContext(), new TimePickerDialog.OnTimeSetListener() {
				
				@Override
			 	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					String ampm = "";
					// TODO Auto-generated method stub
					if(hourOfDay<12 ){
						ampm  = "AM";
					}else{
						ampm = "PM";
					}
					if(hourOfDay==0){
						hourOfDay = 12;
					}
					if(hourOfDay>12){
						hourOfDay = hourOfDay-12;
					}
					edttxt_starttime.setText(String.valueOf(hourOfDay)+":"+ String.valueOf(minute)+":"+ String.valueOf("00")+ String.valueOf(":"+ampm));
				}
			}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
			timepickerDialog.setMessage("Please select time");
			timepickerDialog.show();
			break;
			
		case R.id.endtime:


			timepickerDialog = new TimePickerDialog(getCurrentContext(), new TimePickerDialog.OnTimeSetListener() {

				String ampm = "";
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub

					if(hourOfDay<12 ){
						ampm = "AM";
					}else{
						ampm = "PM";
					}
					if(hourOfDay==0){
						hourOfDay = 12;
					}
					if(hourOfDay>12){
						hourOfDay = hourOfDay-12;
					}
					edttxt_endtime.setText(String.valueOf(hourOfDay)+":"+ String.valueOf(minute)+":"+ String.valueOf("00")+ String.valueOf(":"+ampm));
				}
			}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
			timepickerDialog.setMessage("Please select time");
			timepickerDialog.show();
			break;
		default:
			break;
		}
	}

	private void uploadData(){
		
		 GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AreaManagerVisitLogActivity.this, ProgressDialog.STYLE_SPINNER,"Uploading data Please wait..", new GetDataCallBack() {
			
			 @Override
			 public void processResponse(Object responseObject) {
				SoapObject response = null;
				try {
					response = (SoapObject) responseObject;
				}  catch (Exception e) {
					e.printStackTrace();
				} finally {
					AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext());
					if (response == null) {
						alert.setTitle(getStringFromResource(R.string.error6));
						if (responseObject != null) {
							alert.setMessage(responseObject.toString());
						} else {
							alert.setMessage(getStringFromResource(R.string.error4));
						}

					} else {
						alert.setMessage(getStringFromResource(R.string.message5));

					}

					alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {



							Intent i=new Intent(AreaManagerVisitLogActivity.this, ViewAll_AM.class);
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getRequestDataMap().clear();
							dialog.dismiss();
							startActivity(i);
							finish();
						}
					});
					alert.setCancelable(false);
					alert.show();
				}
			}
		});


		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences userDetails=Util.getSharedPreferences(AreaManagerVisitLogActivity.this, Constants.PREF_NAME);
		try {

			request.put(Constants.AREAID, getAreaId());
			request.put(Constants.PARTYTYPE, getPartyTypeId());
			request.put("PartyId", getPartyId());
			request.put("PurposeofVisit", edttxt_pov.getText().toString());
			request.put("MeetingNotes", edttxt_mettingNotes.getText().toString());
			request.put("Que1", q1.getTag().toString());
			request.put("Answer1", q1.getText().toString());
			request.put("Que2", q2.getTag().toString());
			request.put("Answer2", q2.getText().toString());
			request.put("Que3", q3.getTag().toString());
			request.put("Answer3", q3.getText().toString());
			request.put("Que4", q4.getTag().toString());
			request.put("Answer4", q4.getText().toString());
			request.put("Que5", q5.getTag().toString());
			request.put("Answer5", q5.getText().toString());
			request.put("AreaManagerId", userDetails.getString("Userid","0"));
			request.put("StartDate", edttxt_startDate.getText().toString()+" " +edttxt_starttime.getText().toString());
			request.put("EndDate", edttxt_endDate.getText().toString()+" " +edttxt_endtime.getText().toString());
			request.put(Constants.username, userDetails.getString("username",""));
			request.put(Constants.password,userDetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName = "SaveAreaMngrVisitLog";

		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	
	
	
	
		
}
