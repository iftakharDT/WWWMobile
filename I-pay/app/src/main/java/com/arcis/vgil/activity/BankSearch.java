package com.arcis.vgil.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class BankSearch  extends ListActivity implements CompoundButton.OnCheckedChangeListener,OnClickListener {
	
	RadioButton ifsc,bankcityname;
	Spinner banknamespinner;
	LinearLayout namecitylayout,datadisplalayout;
	EditText ifsccode;
	Spinner cityspinner;
	Spinner statespinner;
	Spinner zonespinner;
	ArrayList<HashMap<String,Object>> arealist=new ArrayList<HashMap<String,Object>>();
	ArrayList<HashMap<String,Object>> bankArrayList=new ArrayList<HashMap<String,Object>>();

	ArrayList<String> cityname,cityidd,statename,stateidd,zonename,zoneid;
	ArrayList<String> bankname,bankid;
	ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
	Button banksearch;
	SharedPreferences bankdetails;

	// Geography Data Category codes.
	public static final String ZONE = "Z";
	public static final String STATE = "ST";
	public static final String DISTRICT = "D";
	public static final String CITY = "CT";
	public static final String AREA = "A";
	public static final String PINCODE = "P";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.banksearch);

		ifsc=(RadioButton) findViewById(R.id.ifsc);


		ifsc.setOnCheckedChangeListener(BankSearch.this);
		bankcityname=(RadioButton) findViewById(R.id.banknamecity);
		bankcityname.setOnCheckedChangeListener(BankSearch.this);
		ifsccode=(EditText) findViewById(R.id.ifsccode);
		banknamespinner=(Spinner) findViewById(R.id.banknamespinner);
		namecitylayout=(LinearLayout) findViewById(R.id.banlcitylayout);
		datadisplalayout=(LinearLayout) findViewById(R.id.bankdisplaylayout);
		datadisplalayout.setVisibility(View.GONE);
		arealist=new ArrayList<HashMap<String,Object>>();
		banksearch=(Button) findViewById(R.id.banksearch);
		banksearch.setOnClickListener(this);

		ifsc.setChecked(true);
		ifsccode.setVisibility(View.VISIBLE);

		cityname=new ArrayList<String>();
		cityname.add("Please Select");
		cityidd=new ArrayList<String>();
		cityidd.add("0");
		statename=new ArrayList<String>();
		statename.add("Please Select");
		stateidd=new ArrayList<String>();
		stateidd.add("0");
		zonename=new ArrayList<String>();
		zonename.add("Please Select");
		zoneid=new ArrayList<String>();
		zoneid.add("0");

		bankname=new ArrayList<String>();
		bankname.add("Please Select");
		bankid=new ArrayList<String>();
		bankid.add("0");
		bankdetails=getSharedPreferences("BANK", MODE_PRIVATE);
		zonespinner=(Spinner)findViewById(R.id.zonespinner);

		zonespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){

					getGeographyByCode(STATE, zoneid.get(zonespinner.getSelectedItemPosition()), "State");

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
					getGeographyByCode(CITY, stateidd.get(statespinner.getSelectedItemPosition()), "City");

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

					GetBankDetails(cityidd.get(cityspinner.getSelectedItemPosition()));
				}
			}	

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		bankdetails.edit().clear().commit();
		bankdetails.edit().putString("banknameid", list.get(position).get(Constants.BankNameID).toString()).commit();
		bankdetails.edit().putString("branchnameid", list.get(position).get(Constants.BankBranchID).toString()).commit();
		bankdetails.edit().putString("ifsc", list.get(position).get(Constants.IFSCCode).toString()).commit();
		finish();
	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	public boolean validation() {
		boolean flag=true;
		String errMgs = getStringFromResource(R.string.error3);

		if(bankcityname.isChecked()){
			if(statespinner.getSelectedItemPosition()==0 ||statespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
				errMgs=errMgs.concat(getStringFromResource(R.string.state));
				flag=false;
			}
			//if(districtspinner.getSelectedItemPosition()==0 ||districtspinner.getSelectedItemPosition()==AdapterView.INVALID_POSITION){
			//			errMgs=errMgs.concat(getStringFromResource(R.string.district));
			//			flag=false;
			//}
			if(zonespinner.getSelectedItemPosition()==0 ||zonespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
				errMgs=errMgs.concat(getStringFromResource(R.string.zone));
				flag=false;
			}
			if(cityspinner.getSelectedItemPosition()==0 ||cityspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
				errMgs=errMgs.concat(getStringFromResource(R.string.city));
				flag=false;
			}
			if(banknamespinner.getSelectedItemPosition()==0 ||banknamespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
				errMgs=errMgs.concat(getStringFromResource(R.string.bankname));
				flag=false;
			}
		}
		else if  (ifsc.isChecked()){
			if (ifsccode.length() == 0) {
				ifsccode.setError(getStringFromResource(R.string.ifsc));
				flag = false;
			}
		}

		if (!flag && !errMgs.equals(getStringFromResource(R.string.error2))) {
			Toast.makeText(BankSearch.this, errMgs, Toast.LENGTH_LONG)
			.show();
		}
		return flag;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.ifsc:
			if(isChecked){
				ifsccode.setVisibility(View.VISIBLE);
			}else{
				ifsccode.setVisibility(View.GONE);
			}
			break;
		case R.id.banknamecity:
			if(isChecked){

				getGeographyByCode(ZONE, "1", "Zone");
				namecitylayout.setVisibility(View.VISIBLE);
			}else{
				namecitylayout.setVisibility(View.GONE);
			}
		default:
			break;
		}

	}

	public String getStringFromResource(int StringCode) {
		return getResources().getString(StringCode);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.banksearch:
			if(validation()){
				if(ifsc.isChecked())
					populateList("0", ifsccode.getText().toString(), "", "");
				else
					populateList("1", "", bankid.get(banknamespinner.getSelectedItemPosition()), cityidd.get(cityspinner.getSelectedItemPosition()));
			}


			break;

		default:
			break;
		}
	}

	/**
	 * Search bank Details.
	 * @param SearchType
	 * @param IFSC
	 * @param BankID
	 * @param CityID
	 */
	private void populateList(String SearchType , String IFSC , String BankID , String CityID ) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(BankSearch.this, ProgressDialog.STYLE_SPINNER,"Loading bank Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					list=new FetchingdataParser().getbankdata(response.toString());
					if(list.size()==0){
						datadisplalayout.setVisibility(View.GONE);

						Toast.makeText(BankSearch.this,getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
					}else {
						datadisplalayout.setVisibility(View.VISIBLE);

						SimpleAdapter simpleAdapter=new SimpleAdapter(BankSearch.this,
								list, 
								R.layout.datarow1,
								new String[]{Constants.BankName,Constants.BranchName ,Constants.IFSCCode},
								new int []{R.id.position1,R.id.position2,R.id.position3});
						setListAdapter(simpleAdapter);
						registerForContextMenu(getListView());

					}

				}else if(response==null){
					datadisplalayout.setVisibility(View.GONE);
					Toast.makeText(BankSearch.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.mode, SearchType);
			request.put(Constants.IFSC,IFSC);
			request.put(Constants.BankNameID, BankID);
			request.put(Constants.cityID,CityID );
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SearchBankDetail";
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

	/**
	 * Get All bank Names.
	 */
	private void GetBankDetails(String cityid) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(BankSearch.this, ProgressDialog.STYLE_SPINNER,"Loading Account Type..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					bankArrayList.clear();
					bankArrayList=new FetchingdataParser().getbankname(response.toString());
					if(bankArrayList.size()==0){
						Toast.makeText(BankSearch.this,getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : bankArrayList)
						{
							String bname = (String) entry.get(Constants.BankName);
							String bid = (String) entry.get(Constants.BankNameID);
							if(!bankname.contains(bname))
								bankname.add(bname);
							if(!bankid.contains(bid))
								bankid.add(bid);

						}
						ArrayAdapter<String> bankadapter = new ArrayAdapter<String>(BankSearch.this, android.R.layout.simple_spinner_item,bankname);
						bankadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						banknamespinner.setAdapter(bankadapter);


					}

				}else if(response==null){
					Toast.makeText(BankSearch.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.cityID, cityid);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetBankDetail";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void getGeographyByCode(final String geoName, final String geoId, final String areaType) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(BankSearch.this, ProgressDialog.STYLE_SPINNER,"Loading "+areaType+" Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist=new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(BankSearch.this,getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
					}else {

						if(geoName.equalsIgnoreCase(ZONE)){
							zonename.clear();
							zonename.add("Pleade Select");
							zoneid.clear();
							zoneid.add("0");
						}
						else if(geoName.equalsIgnoreCase(STATE)){
							stateidd.clear();
							statename.clear();
							statename.add("Pleade Select");
							stateidd.add("0");
						}
						/*else if(geoName.equalsIgnoreCase(DISTRICT)){
							districtid.clear();
							districtname.clear();
							districtname.add("Pleade Select");
							districtid.add("0");
						}*/
						else if(geoName.equalsIgnoreCase(CITY)){
							cityname.clear();
							cityidd.clear();
							cityname.add("Pleade Select");
							cityidd.add("0");
						}
						/*else if(geoName.equalsIgnoreCase(AREA)){
							areaname.clear();
							areaidd.clear();
							areaname.add("Pleade Select");
							areaidd.add("0");
						}else if(geoName.equalsIgnoreCase(PINCODE)){

							pincode.clear();
							pincode.add("Please Select");
						}*/


						for (HashMap<String, Object> entry : arealist)
						{

							String geoids=(String)entry.get(Constants.GeoID);
							String geonames=(String)entry.get(Constants.GeoName);

							if(geoids!=null && geonames!=null){
								if(geoName.equalsIgnoreCase(ZONE)){
									if(!zoneid.contains(geoids))
										zoneid.add(geoids);
									if(!zonename.contains(geonames))
										zonename.add(geonames);
								}

								else if(geoName.equalsIgnoreCase(STATE)){

									if(!stateidd.contains(geoids))
										stateidd.add(geoids);
									if(!statename.contains(geonames))
										statename.add(geonames);

								}
								/*else if(geoName.equalsIgnoreCase(DISTRICT)){

									if(!districtid.contains(geoids))
										districtid.add(geoids);
									if(!districtname.contains(geonames))
										districtname.add(geonames);
								}*/
								else if(geoName.equalsIgnoreCase(CITY)){

									if(!cityidd.contains(geoids))
										cityidd.add(geoids);
									if(!cityname.contains(geonames))
										cityname.add(geonames);
								}
								/*else if(geoName.equalsIgnoreCase(AREA)){
									if(!areaidd.contains(geoids))
										areaidd.add(geoids);
									if(!areaname.contains(geonames))
										areaname.add(geonames);
								}else if(geoName.equalsIgnoreCase(PINCODE)){
									if(!pincode.contains(geonames))
										pincode.add(geonames);
								}*/
							}
						}
						Log.d("zonee", zonename.toString());
						if(geoName.equalsIgnoreCase(ZONE)){
							ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String>(BankSearch.this, android.R.layout.simple_spinner_item,zonename);
							zoneAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							zonespinner.setAdapter(zoneAdapter);
						}
						if(geoName.equalsIgnoreCase(STATE)){
							ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(BankSearch.this, android.R.layout.simple_spinner_item,statename);
							stateAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							statespinner.setAdapter(stateAdapter);
						}
						/*if(geoName.equalsIgnoreCase(DISTRICT)){
							ArrayAdapter<String> districtAdapter = new ArrayAdapter<String> (BankSearch.this, android.R.layout.simple_spinner_item,districtname);
							districtAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							districtspinner.setAdapter(districtAdapter);
						}*/
						if(geoName.equalsIgnoreCase(CITY)){
							ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(BankSearch.this, android.R.layout.simple_spinner_item,cityname);
							cityAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							cityspinner.setAdapter(cityAdapter);
						}
						/*if(geoName.equalsIgnoreCase(AREA)){
							ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String> (BankSearch.this, android.R.layout.simple_spinner_item,areaname);
							AreaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							areaspinner.setAdapter(AreaAdapter);
						}

						if(geoName.equalsIgnoreCase(PINCODE)){
							ArrayAdapter<String> AreaAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,pincode);
							AreaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							pincodeSpinner.setAdapter(AreaAdapter);
						}*/

						/*if(checkaction.equalsIgnoreCase("edit")){
							if(geoName.equalsIgnoreCase(ZONE)){
								zonespinner.setSelection(zoneid.indexOf(i.getStringExtra("zoneid")));
							}
							if(geoName.equalsIgnoreCase(STATE)){
								statespinner.setSelection(stateidd.indexOf(i.getStringExtra("stateid")));

							}
							if(geoName.equalsIgnoreCase(DISTRICT)){
								districtspinner.setSelection(districtid.indexOf(i.getStringExtra("districtid")));
							}
							if(geoName.equalsIgnoreCase(CITY)){
								cityspinner.setSelection(cityidd.indexOf(i.getStringExtra("cityid")));

							}
							if(geoName.equalsIgnoreCase(AREA)){
								areaspinner.setSelection(areaidd.indexOf(i.getStringExtra("areaid")));

							}

							if(geoName.equalsIgnoreCase(PINCODE)){
								//pincodeSpinner.setSelection(pincodeid.indexOf(i.getStringExtra("areaid")));

							}
						}*/
					}

				}else if(response==null){
					Toast.makeText(BankSearch.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
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
}
