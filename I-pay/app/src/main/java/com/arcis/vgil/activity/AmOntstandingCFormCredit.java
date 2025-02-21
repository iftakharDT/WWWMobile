package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DealerNameSearchAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DealerNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AmOntstandingCFormCredit extends BaseActivity{
	/*
	 * Geographical Search
	*/
	private Button get;
	private Spinner countryspinner;
	private Spinner zonespinner;
	private Spinner statespinner;
	private Spinner cityspinner;
	private Spinner spnr_segment,spnr_oe,spnr_appication,spnr_partNo,spnr_Am,spnr_dealer;
	ArrayList<HashMap<String,String>> arealist;
	private int SegmentID=0,OEID=0,ApplicationID=0;
	String selectedDealerName;
	String selectedDealerID;
	String ContactType,ContactID;
	private EditText dealer_search;
	ArrayList<String> countryname,countryid,amname,amidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,dealerName,dealerNameId;
	private ArrayList<DealerNameModel> dealerNameModelsList;
	private DealerNameSearchAdapter dealerNameAdapter;
	private AlertDialog dialog;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.am_ontstanding_cform_credit);
		setCurrentContext(this);
		setCurrentContext(this);
      
		arealist=new ArrayList<HashMap<String,String>>();
		get=(Button) findViewById(R.id.get);
		get.setOnClickListener(this);
		SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		String ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
		ContactID =spref.getString(Constants.USERID, "");
		dealer_search=(EditText) findViewById(R.id.dealer_search);
		dealer_search.setOnClickListener(this);
		
		dealerNameModelsList= new ArrayList<DealerNameModel>();
		countryname=new ArrayList<String>();
		//countryname.add("Please select");
		countryid  =new ArrayList<String>();
		//countryid.add("0");

		zonename=new ArrayList<String>();
		//zonename.add("Please Select");
		zoneid=new ArrayList<String>();
		//zoneid.add("0");

		statename=new ArrayList<String>();
		//statename.add("Please Select");
		stateidd=new ArrayList<String>();
		//stateidd.add("0");


		districtname=new ArrayList<String>();
		//districtname.add("Please Select");
		districtid=new ArrayList<String>();
		//districtid.add("0");


		cityname=new ArrayList<String>();
		//cityname.add("Please Select");
		cityidd=new ArrayList<String>();
		//cityidd.add("0");

		amname=new ArrayList<String>();
		//areaname.add("Please Select");
		amidd=new ArrayList<String>();
		//areaidd.add("0");
		dealerName=new ArrayList<String>();
	
		dealerNameId=new ArrayList<String>();
	
		countryspinner=(Spinner)findViewById(R.id.spinner_contry);

		/*countryspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		});*/

		zonespinner=(Spinner)findViewById(R.id.spinner_zone);
		/*zonespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position!=0){
					getGeographyByCode(ContactType,Constants.GEOCODE_STATE,zoneid.get(zonespinner.getSelectedItemPosition()),ContactID,"0","0");

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});*/

		statespinner=(Spinner)findViewById(R.id.spinner_state);
		statespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
                    dealer_search.setText("");
					dealerNameModelsList.clear();
					getGeographyByCode(ContactType,Constants.GEOCODE_CITY,stateidd.get(statespinner.getSelectedItemPosition()),ContactID,"0","0");
					getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0");
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
					 dealer_search.setText("");
					 dealerNameModelsList.clear();
					 getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(cityspinner.getSelectedItemPosition()));
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		spnr_Am=(Spinner) findViewById(R.id.spinner_am);
		/*spnr_Am.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if(position!=0){
					//HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					//String segmentid = data.get(Constants.ID);
					
				}
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		/*spnr_dealer=(Spinner) findViewById(R.id.spinner_dealer);
		spnr_dealer.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if(position!=0){
				
					   selectedDealerID=	dealerNameId.get(spnr_dealer.getSelectedItemPosition());
					   selectedDealerName=	dealerName.get(spnr_dealer.getSelectedItemPosition());
					 	
				}
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
		 if (ContactTypeAM.equalsIgnoreCase("1")) {
		      ContactType="AM";
		      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0");
			}
		 if (ContactTypeAM.equalsIgnoreCase("4")) {
		      ContactType="RM";
		      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0");
			}
		 if (ContactTypeAM.equalsIgnoreCase("5")) {
		      ContactType="MM";
		      getGeographyByCode(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_AREA,"0",ContactID,"0","0");
		      getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0");
			}
	}

	private void getGeographyByCode(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist=new FetchingdataParser().getarealistparserCform(response.toString());
					if(arealist.size()==0){
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, String> entry : arealist)
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
									
									DealerNameModel dealerNameModel= new DealerNameModel();
									
									if(!dealerNameId.contains(geoids)){
										//dealerNameId.add(geoids);
										dealerNameModel.setDealerNameId(geoId);
									}
										
									if(!dealerName.contains(geonames)||dealerName.contains(geonames)){
										dealerNameModel.setDealerName(geonames);
										//dealerName.add(geonames);
									}
									dealerNameModelsList.add(dealerNameModel);
										
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
						/*if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String> (getCurrentContext(), android.R.layout.simple_spinner_item,dealerName);
							AMAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_dealer.setAdapter(AMAdapter);
						}*/
						
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.get:
			
			if (isValidate()) {
				Intent intent= new Intent(AmOntstandingCFormCredit.this,AmOutstandingCFormCreditResult.class);
				intent.putExtra("DealerID", selectedDealerID);
				intent.putExtra("selectedDealerName", selectedDealerName);
				startActivity(intent);
			}
			
			break;
		case R.id.dealer_search:
			if (arealist.size()!=0) {
                getDealerNameDetails();
				}
				else {
					Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
				}
			break;

		default:
			break;
		}
	}
	private boolean isValidate() {
		// TODO Auto-generated method stub
		String errMgs = getStringFromResource(R.string.error3);
		boolean flag = true;
		if (dealer_search.getText().length()==0) {
			
			errMgs=errMgs.concat(getStringFromResource(R.string.dealer));
			flag = false;
		}
		else {
			flag = true;
		}
		if (!flag) {
			Toast.makeText(getCurrentContext(), errMgs, Toast.LENGTH_LONG).show();
		}
		
		return flag;
	}
	
	
	private void getDealerNameDetails() {
		// TODO Auto-generated method stub
		
		
		LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
		
		EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
		ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
        AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
        builder.setView(view);
        dealerNameAdapter=new DealerNameSearchAdapter(getCurrentContext(), R.layout.am_manual_part_no_shell, dealerNameModelsList);
            list_part_no.setAdapter(dealerNameAdapter);
            list_part_no.setOnItemClickListener(new OnItemClickListener() {

		

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long arg3) {
					// TODO Auto-generated method stub
					if (position!=0) {
						 Toast.makeText(getCurrentContext(),dealerNameModelsList.get(position).getDealerName(), Toast.LENGTH_LONG).show();
						 dealer_search.setText(dealerNameModelsList.get(position).getDealerName());
						 selectedDealerID=dealerNameModelsList.get(position).getDealerNameId();
					     selectedDealerName=dealer_search.getText().toString();	
					}else {
						 Toast.makeText(getCurrentContext(),"Plaese Select Valid Name", Toast.LENGTH_LONG).show();
						 dealer_search.setText("");
					}
					
					 dialog.dismiss();
					
				}
				
			});
            
            
            searchpartNo.addTextChangedListener(new TextWatcher() {

    			@Override
    			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    				// TODO Auto-generated method stub

    				if(AmOntstandingCFormCredit.this.dealerNameAdapter!=null)
    					AmOntstandingCFormCredit.this.dealerNameAdapter.getFilter().filter(arg0);
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
