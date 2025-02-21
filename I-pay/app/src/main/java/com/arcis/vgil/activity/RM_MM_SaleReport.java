package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.PartNoAllCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.date.CalenderAndDateComprison;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RM_MM_SaleReport extends BaseActivity{
	
	/*
	 * Geographical Search
	*/
	private Spinner countryspinner;
	private Spinner zonespinner;
	private Spinner statespinner;
	private Spinner cityspinner;
	private Spinner spnr_segment,spnr_oe,spnr_appication,spnr_partNo,spnr_Am,spnr_dealer;
	ArrayList<HashMap<String,Object>> arealist;
	CalenderAndDateComprison calenderAndDateComprison;
	/* private Calendar cal;
	 private int day;
	 private int month;
	 private int year;*/
	AlertDialog dialog;
	String currentDateString;
	private int SegmentID=0,OEID=0,ApplicationID=0;
	String cVid="0",zVid="0",sVid="0",amVid="0",dlVid="0",cityVid="0";
	int ParntNo;
	private EditText from_date_edit,to_date_edit;
	String ContactType,ContactID;
	Button get,from_date,to_date;
	LinearLayout ll_pending_order_click;
	Date now;
	EditText part_no;
	private TextView tv_application,tv_oe,tv_segment;
	PartNoAllCustomAlertAdapter partNoAdapter;
	ArrayList<PartNoModel> partnoList;
	ArrayList<PartNoModel> partnoListfiler;
	
	
	ArrayList<String> countryname,countryid,amname,amidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,dealerName,dealerNameId;
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
	super.inti();
	setContentView(R.layout.activity_am_sale_layout);
	setCurrentContext(this);
	spnr_segment=(Spinner) findViewById(R.id.spinner_segment);
	spnr_oe=(Spinner) findViewById(R.id. spinner_oe );
	spnr_appication=(Spinner) findViewById(R.id.spinner_application );
	spnr_appication.setVisibility(View.GONE);
	spnr_oe.setVisibility(View.GONE);
	spnr_segment.setVisibility(View.GONE);
	tv_segment=(TextView) findViewById(R.id.tv_segment);
	tv_oe=(TextView) findViewById(R.id.tv_oe);
	tv_application=(TextView) findViewById(R.id.tv_application);
	tv_segment.setVisibility(View.GONE);
	tv_oe.setVisibility(View.GONE);
	tv_application.setVisibility(View.GONE);
	 
	
	get=(Button) findViewById(R.id.get_value);
	get.setOnClickListener(this);
	from_date=(Button) findViewById(R.id.from_date);
	from_date.setOnClickListener(this);
	to_date=(Button) findViewById(R.id.to_date);
	to_date.setOnClickListener(this);
	from_date_edit=(EditText) findViewById(R.id.from_date_edit);
	to_date_edit=(EditText) findViewById(R.id.to_date_edit);
	ll_pending_order_click=(LinearLayout) findViewById(R.id.ll_pending_order_click);
	ll_pending_order_click.setOnClickListener(this);
    now = new Date();
	Date alsoNow = Calendar.getInstance().getTime();
	currentDateString = new SimpleDateFormat("yyyy/MM/dd").format(now);

	arealist=new ArrayList<HashMap<String,Object>>();

	SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
	 String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
	 ContactID =spref.getString(Constants.USERID, "");
	
	countryname=new ArrayList<String>();
	
	countryid  =new ArrayList<String>();


	zonename=new ArrayList<String>();

	zoneid=new ArrayList<String>();


	statename=new ArrayList<String>();
	
	stateidd=new ArrayList<String>();
	

	districtname=new ArrayList<String>();

	districtid=new ArrayList<String>();



	cityname=new ArrayList<String>();

	cityidd=new ArrayList<String>();


	amname=new ArrayList<String>();

	amidd=new ArrayList<String>();
	
	dealerName=new ArrayList<String>();

	dealerNameId=new ArrayList<String>();
	
	countryspinner=(Spinner)findViewById(R.id.spinner_contry);
	part_no=(EditText) findViewById(R.id.part_no);

	countryspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
			if(position!=0){
			 cVid=countryid.get(countryspinner.getSelectedItemPosition());
			 getGeographyByCode(ContactType,Constants.GEOCODE_STATE,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");

			}
			else {
				 cVid=countryid.get(countryspinner.getSelectedItemPosition());
				
			}
			
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
			 zVid=zoneid.get(zonespinner.getSelectedItemPosition());
				stateidd.clear();
				statename.clear();
				getGeographyByCode(ContactType,Constants.GEOCODE_STATE,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
				getGeographyByCode(ContactType,Constants.GEOCODE_CITY,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
			    getGeographyByCode(ContactType,Constants.GEOCODE_AREA,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
			    getGeographyByCode(ContactType,Constants.GEOCODE_DLR,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0","0","0");
			
			}
			else {
				zVid=zoneid.get(zonespinner.getSelectedItemPosition());
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
		       sVid=stateidd.get(statespinner.getSelectedItemPosition());
		        cityidd.clear();
				cityname.clear();
				dealerName.clear();
				dealerNameId.clear();
				amidd.clear();
				amname.clear();
			    getGeographyByCode(ContactType,Constants.GEOCODE_CITY,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
			    getGeographyByCode(ContactType,Constants.GEOCODE_AREA,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
			    getGeographyByCode(ContactType,Constants.GEOCODE_DLR,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0","0","0");
			}	

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
         
		}
	});

	
	cityspinner=(Spinner)findViewById(R.id.spinner_city);
	cityspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
			if(position!=0){
				 cityVid=cityidd.get(cityspinner.getSelectedItemPosition());
				 dealerName.clear();
				 dealerNameId.clear();
				 amidd.clear();
				 amname.clear();
			     getGeographyByCode(ContactType,Constants.GEOCODE_AREA,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(cityspinner.getSelectedItemPosition()),"0","0");
				 getGeographyByCode(ContactType,Constants.GEOCODE_DLR,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(cityspinner.getSelectedItemPosition()),"0","0");
		}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	});

	
	spnr_Am=(Spinner) findViewById(R.id.spinner_am);
	spnr_Am.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
			// TODO Auto-generated method stub

			if(position!=0){
				 amVid=amidd.get(spnr_Am.getSelectedItemPosition());
				 dealerName.clear();
				 dealerNameId.clear();
			     getGeographyByCode(ContactType,Constants.GEOCODE_DLR,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(cityspinner.getSelectedItemPosition()),"0",amidd.get(spnr_Am.getSelectedItemPosition()));
						
			}
			else {
				 amVid=amidd.get(spnr_Am.getSelectedItemPosition());	
			}
			
		
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	
	
	spnr_dealer=(Spinner) findViewById(R.id.spinner_dealer);
	spnr_dealer.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
			// TODO Auto-generated method stub

			if(position!=0){
				
				dlVid=dealerNameId.get(spnr_dealer.getSelectedItemPosition());
				
			}
			
		
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});
	
	
	

	
	
	
	 if (ContactTypeAM.equalsIgnoreCase("4")) {
	      ContactType="RM";
	      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");
		}
	 if (ContactTypeAM.equalsIgnoreCase("5")) {
	      ContactType="MM";
	      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");
		}
	 
	 if (ContactTypeAM.equalsIgnoreCase("27")) {
	      ContactType="CO";
	      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");
		}
	 if (ContactTypeAM.equalsIgnoreCase("28")) {
	      ContactType="HO";
	      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");
		}
	 
	 if (ContactTypeAM.equalsIgnoreCase("3")) {
	      ContactType="SI";
	      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0","0","0");
	      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0","0","0");
		}
	
	

	getPartNo(0,0,0);
	part_no.setOnClickListener(this);
	}
	
	private void getGeographyByCode(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId, final String areaID, final String amId) {
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
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
									if(!amidd.contains(geoids))
										amidd.add(geoids);
									if(!amname.contains(geonames))
										amname.add(geonames);
								}else if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
									
									if(!dealerNameId.contains(geoids))
										dealerNameId.add(geoids);
									if(!dealerName.contains(geonames))
										dealerName.add(geonames);
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
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,amname);
							AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_Am.setAdapter(AMAdapter);
						}
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,dealerName);
							AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_dealer.setAdapter(AMAdapter);
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
						
						if (partnoList.size()!=0) {
							partnoListfiler= FetchingdataParser.getPartNo(response.toString());
						}
						else {
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.no_data) , Toast.LENGTH_SHORT).show();
						}
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

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.get_value:
		
           if (isValidateGet()) {
        	   SalesAMReport_Common(cVid,zVid,sVid,amVid,dlVid,cityVid);
		}
			


			break;
			case R.id.to_date:
				
			
				
				if (from_date_edit.getText().toString() != null && !from_date_edit.getText().toString().isEmpty()) {
					calenderAndDateComprison= new CalenderAndDateComprison();
					 final Calendar c = Calendar.getInstance();
			         int   mYear = c.get(Calendar.YEAR);
			         int  mMonth = c.get(Calendar.MONTH);
			         int   mDay = c.get(Calendar.DAY_OF_MONTH);

			            DatePickerDialog dpd = new DatePickerDialog(getCurrentContext(),
			                    new DatePickerDialog.OnDateSetListener() {

			                        @Override
			                        public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
			           
			                        	String user_choseDate= (year+ "/"
			                                    + (monthOfYear + 1) + "/" +dayOfMonth );
			                        	String resultWithFuture=calenderAndDateComprison.dateComprison(user_choseDate, currentDateString);
			                        	if (resultWithFuture.equals("1")) {
			                        		String resultwithFrom=calenderAndDateComprison.dateComprison(from_date_edit.getText().toString(),user_choseDate);
				                        	
				                        	if (resultwithFrom.equals("1")) {
												to_date_edit.setText(user_choseDate);
											}
				                        	else {
												Toast.makeText(getCurrentContext(), "Select date should not lesser than from date", Toast.LENGTH_LONG).show();
											}
										}
			                        	
			                            
			                        }
			                    }, mYear, mMonth, mDay);
			            dpd.show();
					
				}
				else {
					Toast.makeText(getCurrentContext(), "Please select from date First", Toast.LENGTH_LONG).show();
				}
				
				
				break;
			case R.id.from_date:
				
				calenderAndDateComprison= new CalenderAndDateComprison();
				
				 final Calendar c_from = Calendar.getInstance();
		         int   mYear_from = c_from.get(Calendar.YEAR);
		         int  mMonth_from = c_from.get(Calendar.MONTH);
		         int   mDay_from = c_from.get(Calendar.DAY_OF_MONTH);

		            DatePickerDialog dpd_from = new DatePickerDialog(getCurrentContext(),
		                    new DatePickerDialog.OnDateSetListener() {

		                        @Override
		                        public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
		           
		                        	String user_choseDate= (year+ "/"
		                                    + (monthOfYear + 1) + "/" +dayOfMonth );
		                        	String result=calenderAndDateComprison.dateComprison(user_choseDate, currentDateString);
		                        	if (result.equals("1")) {
										from_date_edit.setText(user_choseDate);
									}
		                        	else {
										Toast.makeText(getCurrentContext(), "Select date can not be current date and future date", Toast.LENGTH_LONG).show();
									}
		                            
		                        }
		                    }, mYear_from, mMonth_from, mDay_from);
		            dpd_from.show();
				
				break;
				
			case R.id.ll_pending_order_click:
				
			
				SalesAMPendingOrderDrillDown( cVid, zVid, sVid, cityVid, amVid,  dlVid,SegmentID, OEID,ApplicationID, ParntNo);
				 

				break;
				
			case R.id.part_no:
				part_no.setText("");
				if (partnoListfiler!=null) {
					if (partnoListfiler.size()!=0) {
						getPartNo(0,0,0);
						 getSerachPartDetails();
					}
                  
				}
				else {
					Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
				}
					break;

		default:
			break;
		}
	}

	private boolean isValidateGet() {
		// TODO Auto-generated method stub
		String errMgs = getStringFromResource(R.string.error3);
		boolean flag = true;
		
		if (from_date_edit.getText().toString().length()==0||to_date_edit.getText().length()==0) {
			errMgs=errMgs.concat(getStringFromResource(R.string.date));
			flag = false;
			
         	}
		
		
		if (!flag) {
			Toast.makeText(getCurrentContext(), errMgs, Toast.LENGTH_LONG).show();
		}
		
		return flag;
	}


	private void SalesAMPendingOrderDrillDown(String cVid, String zVid,
                                              String sVid, String cityVid, String amVid, String dlVid,
                                              int segmentID, int oEID, int applicationID, int parntNo) {
		

		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getAmSalePendingRportDetails(response.toString());
					if(segmentList!=null){
						
						

						if(segmentList.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							
							LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
							EditText et_search=(EditText)view. findViewById(R.id.et_search);
							et_search.setVisibility(View.GONE);
							ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
							LinearLayout ll_pending_order_dialogue=(LinearLayout) view.findViewById(R.id.ll_pending_order_dialogue);
							ll_pending_order_dialogue.setVisibility(View.VISIBLE);
							
				            AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
				            builder.setView(view);
				        
					            SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), segmentList, R.layout.am_sale_pending_order_detail_dialog_shell,
										new String[]{Constants.ItemCode,Constants.CYQuantity,Constants.CYPendingOrderValue,Constants.PYQuantity,Constants.PYPendingOrderValue},
										new int[]{R.id.item_value,R.id.item_previous_quantity,R.id.item_previous_value,R.id.item_current_quantity,R.id.item_current_value});
					                     list_part_no.setAdapter(adapter);	
										
										
										
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


		
		    request.put("CountryID",countryid.get(countryspinner.getSelectedItemPosition()));
	        request.put("ZoneID",zoneid.get(zonespinner.getSelectedItemPosition()));
	        request.put("StateID",sVid);
	        request.put("CityID",cityVid);
	        request.put("AMID",amidd.get(spnr_Am.getSelectedItemPosition()));
	        request.put("DealerID",dealerNameId.get(spnr_dealer.getSelectedItemPosition()));
			request.put("SegmentID", String.valueOf(SegmentID));
			request.put("OEID", String.valueOf(OEID));
			request.put("ApplicationID", String.valueOf(ApplicationID));
			request.put("ProductID", String.valueOf(ParntNo));
			request.put("FromDate",from_date_edit.getText().toString());
		    request.put("ToDate",to_date_edit.getText().toString());
		    request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SalesAMPendingOrderDrillDown";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	
		
	}

	private void SalesAMReport_Common(String cVid, String zVid, String sVid,
                                      String amVid, String dlVid, String cityVid) {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getAmSaleRportDetails(response.toString());
					if(segmentList!=null){
						
						TextView pYSaleQuantity=(TextView) findViewById(R.id.psq1);
						TextView pYSaleValue=(TextView) findViewById(R.id.psv1);
						TextView cYSaleQuantity=(TextView) findViewById(R.id.csq1);
						TextView cYSaleValue=(TextView) findViewById(R.id.csv1);
						
						TextView pYSecondarySaleQuantity=(TextView) findViewById(R.id.pssq2);
						TextView pYSecondarySaleValue=(TextView) findViewById(R.id.pssv2);
						TextView cYSecondarySaleQuantity=(TextView) findViewById(R.id.cssq2);
						TextView cYSecondarySaleValue=(TextView) findViewById(R.id.cssv2);
						
						TextView pYFitmentQuantity=(TextView) findViewById(R.id.pfsq3);
						TextView pYFitmentValue=(TextView) findViewById(R.id.pfsv3);
						TextView cYFitmentQuantity=(TextView) findViewById(R.id.cfsq3);
						TextView cYFitmentValue=(TextView) findViewById(R.id.cfsv3);
						
						TextView pYPendingQuantity=(TextView) findViewById(R.id.ppoq4);
						TextView pYPendingValue=(TextView) findViewById(R.id.ppov4);
						TextView cYPendingQuantity=(TextView) findViewById(R.id.cpoq4);
						TextView cYPendingValue=(TextView) findViewById(R.id.cpov4);
						
						
					
						
						pYSaleQuantity.setText(segmentList.get(0).get(Constants.PYSaleQuantity ));
						pYSaleValue.setText(segmentList.get(0).get(Constants.PYSaleValue ));
					    cYSaleQuantity.setText(segmentList.get(0).get(Constants.CYSaleQuantity));
						cYSaleValue.setText(segmentList.get(0).get(Constants.CYSaleValue));
				    	 
						pYSecondarySaleQuantity.setText(segmentList.get(0).get(Constants.PYSecondarySaleQuantity));
						pYSecondarySaleValue.setText(segmentList.get(0).get(Constants.PYSecondarySaleValue));
						cYSecondarySaleQuantity.setText(segmentList.get(0).get(Constants.CYSecondarySaleQuantity));
						cYSecondarySaleValue.setText(segmentList.get(0).get(Constants.CYSecondarySaleValue));
				    	 
						pYPendingQuantity.setText(segmentList.get(0).get(Constants.PYPendingQuantity));
						pYPendingValue.setText(segmentList.get(0).get(Constants.PYPendingValue));
						cYPendingQuantity.setText(segmentList.get(0).get(Constants.CYPendingQuantity));
				    	cYPendingValue.setText(segmentList.get(0).get(Constants.CYPendingValue));
				    	 
				    	 pYFitmentQuantity.setText(segmentList.get(0).get(Constants.PYFitmentQuantity));
				    	 pYFitmentValue.setText(segmentList.get(0).get(Constants.PYFitmentValue));
				    	 cYFitmentQuantity.setText(segmentList.get(0).get(Constants.CYFitmentQuantity));
				    	 cYFitmentValue.setText(segmentList.get(0).get(Constants.CYFitmentValue));
				    	
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

		    request.put("CountryID",countryid.get(countryspinner.getSelectedItemPosition()));
	        request.put("ZoneID",zoneid.get(zonespinner.getSelectedItemPosition()));
	        request.put("StateID",sVid);
	        request.put("CityID",cityVid);
	        request.put("AMID",amidd.get(spnr_Am.getSelectedItemPosition()));
	        request.put("DealerID",dealerNameId.get(spnr_dealer.getSelectedItemPosition()));
			request.put("SegmentID", String.valueOf(SegmentID));
			request.put("OEID", String.valueOf(OEID));
			request.put("ApplicationID", String.valueOf(ApplicationID));
			request.put("ProductID", String.valueOf(ParntNo));
			if (from_date_edit.getText().toString().length()!=0) {
				request.put("FromDate",from_date_edit.getText().toString());
			}
			else {
				request.put("FromDate",null);
			}
			if (from_date_edit.getText().length()!=0) {
				request.put("ToDate",to_date_edit.getText().toString());
			}
			else {
				request.put("ToDate",null);
			}
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SalesAMReport_Common";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}

	

	private void getSerachPartDetails() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
		
		EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
		ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
        AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
        builder.setView(view);
        partNoAdapter=new PartNoAllCustomAlertAdapter(getCurrentContext(), R.layout.am_manual_part_no_shell, partnoListfiler);
            list_part_no.setAdapter(partNoAdapter);
            list_part_no.setOnItemClickListener(new OnItemClickListener() {

		

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long arg3) {
					// TODO Auto-generated method stub
			
					  ParntNo = Integer.parseInt(partNoAdapter.getItem(position).getProductID());
			
					 Toast.makeText(getCurrentContext(),partNoAdapter.getItem(position).getCode(), Toast.LENGTH_LONG).show();
					 part_no.setText(partNoAdapter.getItem(position).getCode());
					
					 dialog.dismiss();
					
				}
				
			});
            
            
            searchpartNo.addTextChangedListener(new TextWatcher() {

    			@Override
    			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    				// TODO Auto-generated method stub

    				if(RM_MM_SaleReport.this.partNoAdapter!=null)
    					RM_MM_SaleReport.this.partNoAdapter.getFilter().filter(arg0);
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
