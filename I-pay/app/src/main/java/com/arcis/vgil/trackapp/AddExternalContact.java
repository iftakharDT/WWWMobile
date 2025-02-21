package com.arcis.vgil.trackapp;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.BaseActivity;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.activity.ViewAll_AM;
import com.arcis.vgil.activity.ViewExternalContact;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.data.DeptAndDesignationDetails;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddExternalContact extends BaseActivity{
	Spinner dealerspinner;

	private Spinner spinner_department,spinner_designation;

	EditText contactname,mobilenumber,emailid,alternatemob,dobtext;
	Button dateclick,addexternalcontact;
	RadioGroup isprimary;
	RadioButton primary,secondary;
	ArrayList<HashMap<String,Object>> arealist=new ArrayList<HashMap<String,Object>>();
	ArrayList<HashMap<String,Object>> dealerlist=new ArrayList<HashMap<String,Object>>();
	ArrayList<String> areaname,areaidd,cityname,cityidd,statename,stateidd,zonename,zoneid,countryname, department,designation;
	ArrayList<DeptAndDesignationDetails> desgandDeptDetailList = new ArrayList<DeptAndDesignationDetails>();
	LinearLayout dealerlayout;
	ArrayList<String> dealer,dealerid;
	ArrayAdapter<String> dealerAdapter;
	ArrayAdapter<String> areaAdapter;
	String checkaction;
	TextView header;
	Intent i;
	String areaidforbackpress;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
			"[a-zA-Z0-9+._%-+]{1,256}" +
					"@" +
					"[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
					"(" +
					"." +
					"[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
					")+"
			);
	SharedPreferences passworddetails;
	String whologin;
	public void inti() {
		setContentView(R.layout.activity_addexternalcontact);
		setCurrentContext(this);
		//contacttypespinner=(Spinner)findViewById(R.id.contacttypespinner);
		dealerspinner=(Spinner)findViewById(R.id.dealerspinner);
		contactname=(EditText) findViewById(R.id.contactname);
		mobilenumber=(EditText) findViewById(R.id.mobilenumber);
		emailid=(EditText) findViewById(R.id.emailid);
		alternatemob=(EditText) findViewById(R.id.alternatemob);
		//pincode=(EditText) findViewById(R.id.pincode);
		//streetaddress=(EditText) findViewById(R.id.streetaddress);
		dobtext=(EditText) findViewById(R.id.dobtext);
		header=(TextView) findViewById(R.id.header);
		dateclick=(Button) findViewById(R.id.dobbutton);
		dateclick.setOnClickListener(this);
		addexternalcontact=(Button) findViewById(R.id.addexternalcontact);
		addexternalcontact.setOnClickListener(this);

		isprimary=(RadioGroup) findViewById(R.id.isprimary);
		isprimary.setOnCheckedChangeListener(AddExternalContact.this);

		spinner_department = (Spinner)findViewById(R.id.departmentspinner);
		spinner_department.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub


				designation = new ArrayList<String>();
				String department = (String) arg0.getItemAtPosition(arg2);
				for(DeptAndDesignationDetails details : desgandDeptDetailList){
					String temp = details.getDeptName();
					if(department.equalsIgnoreCase(temp)){

						designation.add(details.getDesigName());
					}
				}
				// Set Adapter.
				ArrayAdapter< String> departmentAdapter  = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,designation);
				departmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spinner_designation.setAdapter(departmentAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinner_designation = (Spinner)findViewById(R.id.designationspinner);

		primary=(RadioButton) findViewById(R.id.primary);
		secondary=(RadioButton) findViewById(R.id.secondary);
		dealerlayout=(LinearLayout)findViewById(R.id.dealerlayout);
		passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		whologin=passworddetails.getString("contacttypeid", "");


		arealist=new ArrayList<HashMap<String,Object>>();
		areaname=new ArrayList<String>();
		areaname.add("Please Select");
		areaidd=new ArrayList<String>();
		areaidd.add("0");
		cityname=new ArrayList<String>();
		cityname.add("Please Select");
		cityidd=new ArrayList<String>();
		cityidd.add("0");
		statename=new ArrayList<String>();
		statename.add("Please Select");
		stateidd=new ArrayList<String>();
		stateidd.add("0");
		//		districtname=new ArrayList<String>();
		//		districtname.add("Please Select");
		//		districtid=new ArrayList<String>();
		//		districtid.add("0");
		zonename=new ArrayList<String>();
		zonename.add("Please Select");
		zoneid=new ArrayList<String>();
		zoneid.add("0");
		//		contacttype=new ArrayList<String>();
		//		contacttype.add("Please Select");
		//		contacttypeid=new ArrayList<String>();
		//		contacttypeid.add("0");
		dealer=new ArrayList<String>();
		dealer.add("Please Select");
		dealerid=new ArrayList<String>();
		dealerid.add("0");

		//		retailerid=new ArrayList<String>();
		//		retailerid.add("0");
		//
		//		retailer=new ArrayList<String>();
		//		retailer.add("Please Select");

		countryname=new ArrayList<String>();
		countryname.add("India");


		i=getIntent();
		checkaction=i.getStringExtra(ViewAll_AM.EXTRA_ACTION);
		areaidforbackpress=i.getStringExtra("areanameid");
		getdepartmentandDesignationList();
		if(checkaction.equalsIgnoreCase("add")){
			header.setText(getResources().getString(R.string.addexternalcontact));
			addexternalcontact.setText(getResources().getString(R.string.add));
		}else if(checkaction.equalsIgnoreCase("edit")){



			Toast.makeText(AddExternalContact.this,i.getStringExtra("zoneid")+"__"+i.getStringExtra("stateid"), Toast.LENGTH_SHORT).show();

		}

	};
	@Override
	public void setDataOnScreen() {
		super.setDataOnScreen();


		if(whologin.equalsIgnoreCase("14")){
			dealerlayout.setVisibility(View.GONE);
		}
		else{
			dealerlayout.setVisibility(View.VISIBLE);
			getdealernameList();
		}

		if(checkaction.equalsIgnoreCase("edit")){
			//contacttypespinner.setSelection(contacttypeid.indexOf(i.getStringExtra("contacttype")));
			dealerspinner.setSelection(dealerid.indexOf(i.getStringExtra("dealerid")));
			contactname.setText(i.getStringExtra("contactname" ));
			mobilenumber.setText(i.getStringExtra("mobilenumber" ));
			emailid.setText(i.getStringExtra("email"));
			if(!i.getStringExtra("alternatemobile" ).equals(""))
				alternatemob.setText(i.getStringExtra("alternatemobile" ));
			dobtext.setText(i.getStringExtra("dateofbirth"));
			/*streetaddress.setText(i.getStringExtra("streetaddress"));
		zonespinner.setSelection(zoneid.indexOf(i.getStringExtra("zoneid")));
		statespinner.setSelection(stateidd.indexOf(i.getStringExtra("stateid")));
		//districtspinner.setSelection(districtid.indexOf(i.getStringExtra("districtid")));
		cityspinner.setSelection(cityidd.indexOf(i.getStringExtra("cityid")));
		areaspinner.setSelection(areaidd.indexOf(i.getStringExtra("areaid")));
		pincode.setText(i.getStringExtra("pincode"));
			 */if(i.getStringExtra("primary").equals("0")){
				 primary.setChecked(true);
				 secondary.setChecked(false);
			 }else{
				 primary.setChecked(false);
				 secondary.setChecked(true);
			 }
		}
		else {


		}
	}
	@Override
	public boolean validation() {
		boolean flag=true;
		String errMgs = getStringFromResource(R.string.error3);
		if (contactname.length() == 0) {
			contactname.setError(getStringFromResource(R.string.contactname));
			flag = false;
		}
		if (mobilenumber.length() == 0 || mobilenumber.length()<10) {
			mobilenumber.setError(getStringFromResource(R.string.mobilenumber));
			flag = false;
		}

		if (emailid.length() == 0 ) {
			emailid.setError(getStringFromResource(R.string.emailid));
			flag = false;
		}
		if (!checkEmail(emailid.getText().toString())) {
			emailid.setError(getStringFromResource(R.string.emailid));
			flag = false;
		}
		if(!whologin.equalsIgnoreCase("14")){
			if(dealerspinner.getSelectedItemPosition()==0 ||dealerspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
				errMgs=errMgs.concat(getStringFromResource(R.string.dealer));
				flag=false;
			}

		}
		if(spinner_department.getSelectedItemPosition()==0 || spinner_department.getSelectedItemPosition()== AdapterView.INVALID_POSITION){

			errMgs = errMgs.concat(" "+getStringFromResource(R.string.department_error));
			flag = false;
		}

		if (!flag && !errMgs.equals(getStringFromResource(R.string.error2))) {
			Toast.makeText(getCurrentContext(), errMgs, Toast.LENGTH_LONG)
			.show();
		}
		return flag;
	}
	@Override
	public void getScreenData() {
		super.getScreenData();
		getRequestDataMap().clear();
		if(checkaction.equalsIgnoreCase("edit")){
			getRequestDataMap().put(Constants.ID, i.getStringExtra("ID"));

			getRequestDataMap().put(Constants.CONTACTNAME_1,contactname.getText().toString());

		}

		if(whologin.equalsIgnoreCase("14")){
			getRequestDataMap().put(Constants.dealerid,passworddetails.getString("createdBy",""));
		}
		else{
			getRequestDataMap().put(Constants.dealerid, dealerid.get(dealerspinner.getSelectedItemPosition()));
		}


		//	}else
		//	getRequestDataMap().put(Constants.retailerid, retailerid.get(dealerspinner.getSelectedItemPosition()));

		if(primary.isChecked()){
			getRequestDataMap().put(Constants.IsPrimary, "1");
		}
		else{
			getRequestDataMap().put(Constants.IsPrimary, "0");
		}
		getRequestDataMap().put(Constants.CONTACTTYPEID_1, whologin);
		getRequestDataMap().put(Constants.CONTACTNAME_1,contactname.getText().toString());

		getRequestDataMap().put(Constants.countryID, "1");
		getRequestDataMap().put(Constants.MOBILE_NUMBER,mobilenumber.getText().toString());
		if(alternatemob.getText().toString().length()>0)
			getRequestDataMap().put(Constants.alternatemobile,alternatemob.getText().toString());
		else
			getRequestDataMap().put(Constants.alternatemobile,"");
		if(dobtext.getText().toString().length()>0)
			getRequestDataMap().put(Constants.dob,dobtext.getText().toString());
		else
			getRequestDataMap().put(Constants.dob,"");

		getRequestDataMap().put(Constants.emailid,emailid.getText().toString());

		getRequestDataMap().put(Constants.Department, getDepartmentId(spinner_department.getSelectedItem().toString()));
		getRequestDataMap().put(Constants.Designation, getDesignationId(spinner_designation.getSelectedItem().toString()));

	}
	@Override
	public void startNextScreen() {
		super.startNextScreen();
		if(checkaction.equalsIgnoreCase("add")){
			postAfterMobileValidation();
		}else if(checkaction.equalsIgnoreCase("edit")){
			if(getRequestDataMap().get(Constants.MOBILE_NUMBER).equals(i.getStringExtra("mobilenumber"))){
				postalldata();
			}
			else{
				postAfterMobileValidation();
			}
		}
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.addexternalcontact:
			if(validation()){
				getScreenData();
				startNextScreen();
			}
			break;

		case R.id.dobbutton:
			final Calendar calendar= Calendar.getInstance();
			DatePickerDialog datePickerDialog= new DatePickerDialog(getCurrentContext(), new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int date) {
					getRequestDataMap().put(Constants.dob, String.valueOf(month+1)+"/"+ String.valueOf(date)+"/"+ String.valueOf(year));
					dobtext.setText(String.valueOf(month+1)+"/"+ String.valueOf(date)+"/"+ String.valueOf(year));
					//dobtext.setText(String.valueOf(date)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
			// datePickerDialog.set
			datePickerDialog.show();
			break;
		default:
			break;
		}
	}


	private void getdealernameList() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddExternalContact.this, ProgressDialog.STYLE_SPINNER,"Loading Dealer  Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					dealerlist.clear();
					dealerlist=new FetchingdataParser().getdealernameparser(response.toString());
					if(dealerlist.size()==0){
						Toast.makeText(AddExternalContact.this,getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
					}else {

						dealer.clear();
						dealerid.clear();
						dealer.add("Please Select");
						dealerid.add("0");
						for (HashMap<String, Object> entry : dealerlist)
						{
							String dealernames = (String) entry.get(Constants.dealer);
							String dealerids = (String) entry.get(Constants.dealerid);

							if(!dealer.contains(dealernames))
								dealer.add(dealernames);

							if(!dealerid.contains(dealerids))
								dealerid.add(dealerids);
						}
					}
					dealerAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,dealer);
					dealerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
					dealerspinner.setOnItemSelectedListener(AddExternalContact.this);
					dealerspinner.setAdapter(dealerAdapter);

					if(checkaction.equalsIgnoreCase("edit")){
						dealerspinner.setSelection(dealerid.indexOf(i.getStringExtra("dealerid")));
					}

				}else if(response==null){
					Toast.makeText(AddExternalContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});
		HashMap<String,Object> request=new HashMap<String, Object>();
		//	request.put(Constants.username,User.getUserInstance().getProperty(0));
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.username, passworddetails.getString("username",""));
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


	private void postalldata(){
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddExternalContact.this, ProgressDialog.STYLE_SPINNER,"Uploading data Please wait..", new GetDataCallBack() {
			@Override
			public void processResponse(Object responseObject) {
				SoapObject response = null;
				try {
					response = (SoapObject) responseObject;
				}  catch (Exception e) {
					e.printStackTrace();
				} finally {
					Builder alert = new Builder(getCurrentContext());
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
							Intent i=new Intent(AddExternalContact.this, ViewAll_AM.class);
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

		HashMap<String,Object> request=getRequestDataMap();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.USERID_1, passworddetails.getString("createdBy",""));
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName = "";
		if(checkaction.equalsIgnoreCase("add")){
			//		if(contacttypeid.get(contacttypespinner.getSelectedItemPosition()).equals("14")){
			//			 methodName="AddDealer";
			//			}else{
			//				methodName="AddRetailer";
			//			}
			methodName="AddDealer";
		}else if(checkaction.equalsIgnoreCase("edit")){
			methodName="UpdateExternalContact";
			request.put(Constants.ID, i.getStringExtra("ID"));
		}
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		for (Map.Entry<String, Object> entry : request.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			Log.d(key, value+"");
		}
		dataFromNetwork.execute();
	}
	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		if(!checkaction.equalsIgnoreCase("add")){
			Intent i=new Intent(AddExternalContact.this, ViewExternalContact.class);
			i.putExtra("areanameid",areaidforbackpress);
			startActivity(i);
		}
		finish();
	}
	void postAfterMobileValidation(){
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddExternalContact.this, ProgressDialog.STYLE_SPINNER,"Uploading data Please wait..", new GetDataCallBack() {
			@Override
			public void processResponse(Object responseObject) {

				if(responseObject.toString().equals("1")){
					Builder alert = new Builder(getCurrentContext());
					alert.setCancelable(false);
					alert.setTitle(getStringFromResource(R.string.error6));
					alert.setMessage(getStringFromResource(R.string.message6));
					alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent i=new Intent(AddExternalContact.this, ViewExternalContact.class);
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							getRequestDataMap().clear();
							dialog.dismiss();
							startActivity(i);
							finish();
						}
					});
					alert.show();
				}else{

					postalldata();
				}


			}
		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			requestdata.put(Constants.mobilenumberch, getRequestDataMap().get(Constants.MOBILE_NUMBER));
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="IsContactExist";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(requestdata);
		dataFromNetwork.execute();

	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private void getAreanameList(final String geoName, final String geoId) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddExternalContact.this, ProgressDialog.STYLE_SPINNER,"Loading Area Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist= new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(AddExternalContact.this,getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
						//					areaname.clear();
						//					areaidd.clear();
						//					cityname.clear();
						//					cityidd.clear();
						//					stateidd.clear();
						//					statename.clear();
						//					districtid.clear();
						//					districtname.clear();
						//					areaname.add("Please Select");
						//					areaidd.add("0");
						//					cityname.add("Please Select");
						//					cityidd.add("0");
						//					statename.add("Please Select");
						//					stateidd.add("0");
						//					districtname.add("Please Select");
						//					districtid.add("0");
						//					zonename=new ArrayList<String>();
						//					zonename.add("Please Select");
						//					zoneid=new ArrayList<String>();
						//					zoneid.add("0");
					}else {

						if(geoName.equalsIgnoreCase("ZONE")){
							zonename.clear();
							zonename.add("Please Select");
							zoneid.clear();
							zoneid.add("0");
						}
						else if(geoName.equalsIgnoreCase("STATE")){
							stateidd.clear();
							statename.clear();
							statename.add("Please Select");
							stateidd.add("0");
						}
						//					else if(geoName.equalsIgnoreCase("DISTRICT")){
						//			    		districtid.clear();
						//						districtname.clear();
						//						districtname.add("Please Select");
						//						districtid.add("0");
						//						}
						else if(geoName.equalsIgnoreCase("CITY")){
							cityname.clear();
							cityidd.clear();
							cityname.add("Please Select");
							cityidd.add("0");
						}
						else if(geoName.equalsIgnoreCase("AREA")){
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
								if(geoName.equalsIgnoreCase("ZONE")){
									if(!zoneid.contains(geoids))
										zoneid.add(geoids);
									if(!zonename.contains(geonames))
										zonename.add(geonames);

								}

								else if(geoName.equalsIgnoreCase("STATE")){

									if(!stateidd.contains(geoids))
										stateidd.add(geoids);
									if(!statename.contains(geonames))
										statename.add(geonames);

								}
								//					    	else if(geoName.equalsIgnoreCase("DISTRICT")){
								//					    		
								//						    	if(!districtid.contains(geoids))
								//						    		districtid.add(geoids);
								//						    	if(!districtname.contains(geonames))
								//						    		districtname.add(geonames);
								//						    	}
								else if(geoName.equalsIgnoreCase("CITY")){

									//if(!cityidd.contains(geoids))
									cityidd.add(geoids);
									//if(!cityname.contains(geonames))
									cityname.add(geonames);
								}
								else if(geoName.equalsIgnoreCase("AREA")){
									// 	if(!areaidd.contains(geoids))
									areaidd.add(geoids);
									//  	if(!areaname.contains(geonames))
									areaname.add(geonames);
								}

							}


						}
						Log.d("zonee", zonename.toString());
						/*if(geoName.equalsIgnoreCase("ZONE")){
					ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,zonename);
					zoneAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
					zonespinner.setAdapter(zoneAdapter);
					}
					if(geoName.equalsIgnoreCase("STATE")){
						ArrayAdapter<String> stateAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,statename);
						stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						statespinner.setAdapter(stateAdapter);
						}
		//					if(geoName.equalsIgnoreCase("DISTRICT")){
		//						ArrayAdapter<String> districtAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,districtname);
		//						districtAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		//						districtspinner.setAdapter(districtAdapter);
		//						}
					if(geoName.equalsIgnoreCase("CITY")){
						ArrayAdapter<String> cityAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,cityname);
						cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						cityspinner.setAdapter(cityAdapter);
						}
					if(geoName.equalsIgnoreCase("AREA")){
							ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,areaname);
							AreaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							areaspinner.setAdapter(AreaAdapter);
							}

					if(checkaction.equalsIgnoreCase("edit")){
						if(geoName.equalsIgnoreCase("ZONE")){
							zonespinner.setSelection(Integer.valueOf(i.getStringExtra("zoneid")));
							}
							if(geoName.equalsIgnoreCase("STATE")){
								statespinner.setSelection(stateidd.indexOf(i.getStringExtra("stateid")));

								}
		//							if(geoName.equalsIgnoreCase("DISTRICT")){
		//								ArrayAdapter<String> districtAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,districtname);
		//								districtAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		//								districtspinner.setAdapter(districtAdapter);
		//								}
							if(geoName.equalsIgnoreCase("CITY")){
								cityspinner.setSelection(cityidd.indexOf(i.getStringExtra("cityid")));

								}
							if(geoName.equalsIgnoreCase("AREA")){
								areaspinner.setSelection(areaidd.indexOf(i.getStringExtra("areaid")));

									}



					}*/


						//					alt = new AlertDialog.Builder(ViewAll.this);
						//					alt.setTitle((String) getResources().getText(R.string.select)).setItems(areaname.toArray(new CharSequence[areaname.size()]), new DialogInterface.OnClickListener() {
						//						
						//						@Override
						//						public void onClick(DialogInterface dialog, int which) {
						//							Intent send=new Intent(ViewAll.this, ViewExternalContact.class);
						//							send.putExtra("areanameid", areaidd.get(which));
						//							startActivity(send);
						//														
						//						}
						//					});
						//					alt.show();
					}

				}else if(response==null){
					Toast.makeText(AddExternalContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		//	request.put(Constants.username,User.getUserInstance().getProperty(0));
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
		String methodName="GetGeograpgy";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void getdepartmentandDesignationList(){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(AddExternalContact.this, ProgressDialog.STYLE_SPINNER, "Loading department...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				if(result!=null){
					desgandDeptDetailList.clear();
					desgandDeptDetailList = new FetchingdataParser().getDeptandDesgData(result.toString());

					if(desgandDeptDetailList!=null){
						department = new ArrayList<String>();
						department.add("Please select");

						Log.i("desgandDeptDetailList", String.valueOf(desgandDeptDetailList.size()));

						// Get Department Name here.

						for(DeptAndDesignationDetails details: desgandDeptDetailList){

							String department = details.getDeptName();
							if(AddExternalContact.this.department.contains(department)){
								continue;
							}else{
								AddExternalContact.this.department.add(department);
							}

						}

						// Set Adapter.
						ArrayAdapter< String> departmentAdapter  = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,department);
						departmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spinner_department.setAdapter(departmentAdapter);

					}else{
						Builder dialog = new Builder(getCurrentContext());
						dialog.setMessage(result.toString());
						dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						dialog.show();
					}

				}else{
					Toast.makeText(getCurrentContext(), getStringFromResource(R.string.error4), Toast.LENGTH_SHORT).show()
					;					}
			}
		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			requestdata.put(Constants.CONTACTTYPEID_1,Constants.contactTypeIDForDealer);
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDeptAndDesignation";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	/**
	 * Get Department Name.
	 * @param deptName
	 * @return
	 */
	private String getDepartmentId(String deptName){

		String deptId = "";
		if(deptName!=null){

			for(DeptAndDesignationDetails details : desgandDeptDetailList){

				if(deptName.equalsIgnoreCase(details.getDeptName())){

					deptId = String.valueOf(details.getDeptId());
					Log.i("DesignationId", deptId);
					break;
				}
			}
		}

		return  deptId;
	}

	/**
	 *  Get Designation Id.
	 * @param desgName
	 * @return
	 */
	private String getDesignationId(String desgName){

		String desgId = "";

		if(desgName!=null){

			for(DeptAndDesignationDetails details : desgandDeptDetailList){

				if(desgName.equalsIgnoreCase(details.getDesigName())){

					desgId = String.valueOf(details.getDesignationId());
					Log.i("DesignationId", desgId);
					break;
				}
			}
		}

		return  desgId;
	}
}
