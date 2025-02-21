package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.coupon.Coupon;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRetailerContact extends BaseActivity {

	Spinner contacttypespinner,authorized,verificationspinner;
	String cVid="0",zVid="0",sVid="0",amVid="0",dlVid="0",cityVid="0";
	String ContactTypeAM;
	String ContactType,ContactID;
	Spinner countryspinner,statespinner,areaspinner,zonespinner,cityspinner;
	Spinner bankaccounttype ;
	SharedPreferences bankdetails;
	String IFsccode;
	EditText contactname,garagename,mobilenumber,streetaddress,pincodeSpinner,et_pan_card;
	EditText bankaccountholder,bankaccountnumber,ifsccode,verificationdoc;
	Button addretailerContact,search,btn_takephoto,btn_takephotoGallary;
	CheckBox bankdatafill;
	ArrayList<HashMap<String,Object>> arealist;
	ArrayList<HashMap<String,Object>> contacttypelist=new ArrayList<HashMap<String,Object>>();
	ArrayList<HashMap<String,Object>> VerificationDoctypeList=new ArrayList<HashMap<String,Object>>();
	ArrayList<HashMap<String,Object>> aaccounttypelist=new ArrayList<HashMap<String,Object>>();
	ArrayList<String> contacttype,contacttypeid;
	ArrayList<String> verificationname,verificationid;
	ArrayList<String> countryname,countryid,areaname,areaidd,cityname,cityidd,districtname,districtid,statename,stateidd,zonename,zoneid,pincode,pincodeid;
	ArrayAdapter<String> areaAdapter;
	ArrayList<String> authorizedlist;
	ArrayList<String> accounttypelist,accounttypeidlist;
	String checkaction = "";
	TextView header;
	Intent i;
	LinearLayout banklayout;
	String areaidforbackpress;
	RadioButton garage,individual;
	private static final int BANK_SEARCH_REQUEZST_CODE= 23465;
	private static final int CAPTURE_IMAGE_CAMERA = 1;
	private static final int CAPTURE_IMAGE_GALLARY = 2;
	private Bitmap mbitmap;
	private ImageView mDocImage;
	File file  = new File(Environment.getExternalStorageDirectory()+"/"+"doc.jpeg");
	private RadioGroup rgroup;
	private LinearLayout atmLayout;

	public void inti() {
		setContentView(R.layout.activity_addretailercontact);
		setCurrentContext(this);
		
		 SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		 ContactTypeAM=spref.getString(Constants.CONTACTTYPEID, "");
		 ContactID =spref.getString(Constants.USERID, "");
		 
		 
		contacttypespinner=(Spinner)findViewById(R.id.retailertypespinner);
		verificationspinner=(Spinner)findViewById(R.id.verificationspinner);
		//verificationspinner.setEnabled(false);
		garage=(RadioButton) findViewById(R.id.garage);
		individual=(RadioButton) findViewById(R.id.individual);
		bankdetails=getSharedPreferences("BANK", MODE_PRIVATE);
		bankdetails.edit().clear().commit();
		authorized=(Spinner)findViewById(R.id.authorized);

		authorizedlist=new ArrayList<String>();
		authorizedlist.add("Please Select");
		authorizedlist.add("Yes");
		authorizedlist.add("No");

		ArrayAdapter<String> auAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,authorizedlist);
		auAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		authorized.setAdapter(auAdapter);

		bankaccounttype=(Spinner)findViewById(R.id.bankaccounttype);
		verificationdoc=(EditText) findViewById(R.id.verificationdoc);
		garagename=(EditText) findViewById(R.id.garagename);
		contactname=(EditText) findViewById(R.id.contactname);
		mobilenumber=(EditText) findViewById(R.id.mobilenumber);
		pincodeSpinner=(EditText) findViewById(R.id.pincodespinner);
		et_pan_card=(EditText) findViewById(R.id.et_pan_card);
		streetaddress=(EditText) findViewById(R.id.streetaddress);
		bankaccountholder=(EditText) findViewById(R.id.bankaccountholder);
		ifsccode=(EditText) findViewById(R.id.ifsccode);
		bankaccountnumber=(EditText) findViewById(R.id.bankaccountnumber);
		header=(TextView) findViewById(R.id.header);
		addretailerContact=(Button) findViewById(R.id.addretailercontact);
		addretailerContact.setOnClickListener(this);
		search=(Button) findViewById(R.id.search);
		search.setOnClickListener(this);
		
		btn_takephoto = (Button)findViewById(R.id.takephoto);
		btn_takephoto.setOnClickListener(this);
		
		btn_takephotoGallary = (Button)findViewById(R.id.takephotofromgallary);
		btn_takephotoGallary.setOnClickListener(this);

		banklayout=(LinearLayout)findViewById(R.id.bankLayout);

		rgroup = (RadioGroup)findViewById(R.id.rg_bankdetails);
		rgroup.setOnCheckedChangeListener(this);
		
		mDocImage  = (ImageView)findViewById(R.id.img_attachment);

		atmLayout = (LinearLayout) findViewById(R.id.atmlayout);
		atmLayout.setVisibility(View.GONE);

		arealist=new ArrayList<HashMap<String,Object>>();

	//	Function GetMappedGeographyForLogin(ByVal ContactType As String, 
		//ByVal GeoType As String, ByVal GeoID As String, 
		//ByVal ContactID As String, 
		//ByVal StateID As String, ByVal CityID As String, ByVal UserID As String, ByVal Password As String) As String

		countryname=new ArrayList<String>();
	
		countryid  =new ArrayList<String>();
	

		areaname=new ArrayList<String>();
		
		areaidd=new ArrayList<String>();
	

		cityname=new ArrayList<String>();
	
		cityidd=new ArrayList<String>();
	
		statename=new ArrayList<String>();
	
		stateidd=new ArrayList<String>();
	

		districtname=new ArrayList<String>();
	
		districtid=new ArrayList<String>();
	

		zonename=new ArrayList<String>();
	
		zoneid=new ArrayList<String>();
	

		verificationname=new ArrayList<String>();
		verificationid=new ArrayList<String>();



		pincode = new ArrayList<String>();
		pincode.add("Please Select");
		pincodeid = new ArrayList<String>();
		pincodeid.add("0");



		contacttype=new ArrayList<String>();
		contacttype.add("Please Select");
		contacttypeid=new ArrayList<String>();
		contacttypeid.add("0");

		accounttypelist=new ArrayList<String>();
		accounttypelist.add("Please Select");
		accounttypeidlist=new ArrayList<String>();
		accounttypeidlist.add("0");


		countryspinner=(Spinner)findViewById(R.id.countryspinner);

		countryspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					
					// cVid=countryid.get(countryspinner.getSelectedItemPosition());
					

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
					 zVid=zoneid.get(zonespinner.getSelectedItemPosition());

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
					try {
					//	getGeographyByCode(ContactType,Constants.GEOCODE_CITY,stateidd.get(statespinner.getSelectedItemPosition()),ContactID,"0","0");
					//	getGeographyByCode(ContactType,Constants.GEOCODE_DLR,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0");
						cityidd.clear();
						cityname.clear();
						areaidd.clear();
						areaname.clear();
					//	String jai=stateidd.get(statespinner.getSelectedItemPosition());
						//{ContactType=AM, GeoType=CT, GeoID=33, ContactID=20, StateID=0, CityID=0, UserID=20, Password=BHn+UQ9w+RMTlWytgkoqaA==
					//}

						GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_CITY,stateidd.get(statespinner.getSelectedItemPosition()),ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0");
						GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),"0");
					} catch (Exception e) {
						// TODO: handle exception
						e.getMessage();
					}
				
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
					areaidd.clear();
					areaname.clear();
					//{ContactType=AM, GeoType=CT, GeoID=33, ContactID=20, StateID=0, CityID=0, UserID=20, Password=BHn+UQ9w+RMTlWytgkoqaA==
					//}
					GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,stateidd.get(statespinner.getSelectedItemPosition()),cityidd.get(cityspinner.getSelectedItemPosition()));
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		areaspinner=(Spinner)findViewById(R.id.areaspinner);
	/*	areaspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					if(arg2!=0){
						
						//GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_PINCODE,areaidd.get(areaspinner.getSelectedItemPosition()),"Pincode");
						
					}	
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}
				

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
	*/	
		
		
		i=getIntent();
		checkaction=i.getStringExtra(Coupon.COUPON_EXTRA);
		areaidforbackpress=i.getStringExtra("areanameid");
		
		if(checkaction.equalsIgnoreCase("add")){
			header.setText(getResources().getString(R.string.addretailercontact));
			addretailerContact.setText(getResources().getString(R.string.add));
			verificationspinner.setVisibility(View.VISIBLE);
			verificationdoc.setVisibility(View.VISIBLE);
		}else if(checkaction.equalsIgnoreCase("edit")){
			//GetBankDetail(i.getStringExtra("BankCity"));
			header.setText(getResources().getString(R.string.editretailercontact));
			addretailerContact.setText(getResources().getString(R.string.save));
			verificationspinner.setVisibility(View.GONE);
			verificationdoc.setVisibility(View.GONE);
		}
		

	}

	@Override
	public void setDataOnScreen() {
		super.setDataOnScreen();

		getContactTypeList();
		GetAccountType();
		 if (ContactTypeAM.equalsIgnoreCase("1")) {
		      ContactType="AM";
		      GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0");
		      GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0");
		      GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0");
		      GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
		      GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0");
		    
			}

		if (ContactTypeAM.equalsIgnoreCase("3")) {
			ContactType="SI";
			GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_COUNTRY,"0",ContactID,"0","0");
			GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_ZONE,"0",ContactID,"0","0");
			GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_STATE,"0",ContactID,"0","0");
			GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_CITY,"0",ContactID,"0","0");
			GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_AREA_CUSTOMER,"0",ContactID,"0","0");

		}

		if(checkaction.equalsIgnoreCase("edit")){
			getGeographyByCode(Constants.GEOCODE_STATE,i.getStringExtra("zoneid"),"State");
			getGeographyByCode(Constants.GEOCODE_DISTRICT,i.getStringExtra("stateid"),"District");
			getGeographyByCode(Constants.GEOCODE_CITY,i.getStringExtra("districtid"),"City");
			getGeographyByCode(Constants.GEOCODE_AREA,i.getStringExtra("cityid"),"Area");
            getGeographyByCode(Constants.GEOCODE_PINCODE, i.getStringExtra("pincodeid"), "pincode");
             String s=i.getStringExtra("pincodeid");

			//contacttypespinner.setSelection(contacttypeid.indexOf(i.getStringExtra("contacttype")));
			contactname.setText(i.getStringExtra("contactname" ));
			garagename.setText(i.getStringExtra("garagename"));
			verificationdoc.setText(i.getStringExtra("verificationdoc"));
			if(i.getStringExtra("ISIndividula").equals("1"))
				individual.setChecked(true);
			else
		    garage.setChecked(true);
			mobilenumber.setText(i.getStringExtra("mobilenumber" ));
			streetaddress.setText(i.getStringExtra("streetaddress"));
			countryspinner.setSelection(countryname.indexOf(i.getStringExtra("countryid")));
			if(i.getStringExtra("Authorized").equalsIgnoreCase("True"))
				authorized.setSelection(1);
			else{
				authorized.setSelection(2);
			}

			//cityspinner.setSelection(cityidd.indexOf(i.getStringExtra("cityid")));
			//	bankcity.setSelection(cityidd.indexOf(i.getStringExtra("BankCity")));

			//pincode.setText(i.getStringExtra("pincode"));

			RadioButton button = (RadioButton) rgroup.getChildAt(0);
			if(!(i.getStringExtra("AccountNumber").equalsIgnoreCase(""))){

				button.setChecked(true);
			}else{
				button.setChecked(false);
			}


		}
		else{
			GetVerificationDoctypeList();
		}
	}
	
	@Override
	public boolean validation() {
		boolean flag=true;
		String errMgs = getStringFromResource(R.string.error3);


		/*if(pincodeSpinner.length()==0||pincodeSpinner.length()<6){

			errMgs = errMgs.concat(" " +getStringFromResource(R.string.pincode));
			flag = false;
		}*/
		
		if(contacttypespinner.getSelectedItemPosition()==0 ||contacttypespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			Toast.makeText(getCurrentContext(), "Please Select"+getStringFromResource(R.string.contacttype), Toast.LENGTH_LONG).show();
			return false;
		}
		if (contactname.length() == 0) {
			Toast.makeText(getCurrentContext(), "Contact Name can't be blank", Toast.LENGTH_LONG).show();
			return false;
		}
		if (mobilenumber.length() == 0 || mobilenumber.length()<10) {
			Toast.makeText(getCurrentContext(), "Make sure Mobile Number is correct.", Toast.LENGTH_LONG).show();
			return false;
		}


		if(Integer.parseInt(countryid.get(countryspinner.getSelectedItemPosition()))==0 ||countryspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			Toast.makeText(getCurrentContext(), "Please Select"+getStringFromResource(R.string.country), Toast.LENGTH_LONG).show();
			return false;
		}
		
		if(Integer.parseInt(zoneid.get(zonespinner.getSelectedItemPosition()))==0 ||zonespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			Toast.makeText(getCurrentContext(), "Please Select"+getStringFromResource(R.string.zone), Toast.LENGTH_LONG).show();
			return false;
		}
		if(statespinner.getSelectedItemPosition()==0 ||statespinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			Toast.makeText(getCurrentContext(), "Please Select"+getStringFromResource(R.string.state), Toast.LENGTH_LONG).show();
			return false;

		}
		if(cityspinner.getSelectedItemPosition()==0 ||cityspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			Toast.makeText(getCurrentContext(), "Please Select"+getStringFromResource(R.string.city), Toast.LENGTH_LONG).show();
			return false;
		}
		if(areaspinner.getSelectedItemPosition()==0 ||areaspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			Toast.makeText(getCurrentContext(), "Please Select"+getStringFromResource(R.string.area), Toast.LENGTH_LONG).show();
			return false;
		}
		/*String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
		Pattern p = Pattern.compile(regex);

		if (et_pan_card.getText().toString().trim().length() == 0 ) {
			Toast.makeText(getCurrentContext(), "Pan Card can't be blank.", Toast.LENGTH_LONG).show();
			return false;
		}

		Matcher m = p.matcher(et_pan_card.getText().toString().trim());
		if(!m.matches()){
			Toast.makeText(getCurrentContext(), "Make sure PAN Details is Correct.", Toast.LENGTH_LONG).show();
			return false;
		}*/

		return flag;
	}

	@Override
	public void getScreenData() {
		super.getScreenData();
		getRequestDataMap().clear();


        SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		getRequestDataMap().put(Constants.retailerid, "0");
		getRequestDataMap().put(Constants.code, "0");
		getRequestDataMap().put(Constants.CONTACTNAME_1,contactname.getText().toString());
		getRequestDataMap().put(Constants.contacttype, contacttypeid.get(contacttypespinner.getSelectedItemPosition()));
		getRequestDataMap().put(Constants.streetaddress,streetaddress.getText().toString());
		getRequestDataMap().put(Constants.country, countryid.get(countryspinner.getSelectedItemPosition()));
		getRequestDataMap().put(Constants.zone, zoneid.get(zonespinner.getSelectedItemPosition()));
		getRequestDataMap().put(Constants.state, stateidd.get(statespinner.getSelectedItemPosition()));
		getRequestDataMap().put(Constants.district,"0");	
		getRequestDataMap().put(Constants.city, cityidd.get(cityspinner.getSelectedItemPosition()));
		getRequestDataMap().put(Constants.area, areaidd.get(areaspinner.getSelectedItemPosition()));
		getRequestDataMap().put(Constants.pincode, pincodeSpinner.getText().toString());
		getRequestDataMap().put(Constants.PhoneNo,mobilenumber.getText().toString());
		
		
		if(authorized.getSelectedItemPosition()==1)
			getRequestDataMap().put(Constants.authorized,"1");
		else
			getRequestDataMap().put(Constants.authorized,"0");


		if(bankaccountholder.length()>0)
			getRequestDataMap().put(Constants.AccountHolderName,bankaccountholder.getText().toString());
		else
			getRequestDataMap().put(Constants.AccountHolderName," ");

		if(ifsccode.length()>0){
			if(checkaction.equalsIgnoreCase("add")){
				getRequestDataMap().put(Constants.BankBranchID,bankdetails.getString("branchnameid",""));
			}else{
				getRequestDataMap().put(Constants.BankBranchID,i.getStringExtra("BranchName"));

			}
		}
		else
			getRequestDataMap().put(Constants.BankBranchID,0);

		if(bankaccounttype.getSelectedItemPosition()==0 ||bankaccounttype.getSelectedItemPosition()== AdapterView.INVALID_POSITION){
			getRequestDataMap().put(Constants.AccountType,0);
		}
		else
			getRequestDataMap().put(Constants.AccountType,accounttypeidlist.get(bankaccounttype.getSelectedItemPosition()));

		if(bankaccountnumber.length()>0)
			getRequestDataMap().put(Constants.AccountNumber,bankaccountnumber.getText().toString());
		else
			getRequestDataMap().put(Constants.AccountNumber,0);

		getRequestDataMap().put(Constants.createdby, passworddetails.getString("username",""));

		if(checkaction.equalsIgnoreCase("add")){
			getRequestDataMap().put(Constants.VerificationID,verificationid.get(verificationspinner.getSelectedItemPosition()));
			getRequestDataMap().put(Constants.VerificationName,verificationdoc.getText().toString());
		}

		if(garagename.length()>0)
			getRequestDataMap().put(Constants.GarageName,garagename.getText().toString());
		else
			getRequestDataMap().put(Constants.GarageName," ");

		if(garage.isChecked())
			getRequestDataMap().put(Constants.ISIndividula,"0");
		else if(individual.isChecked())
			getRequestDataMap().put(Constants.ISIndividula,"1");

		if(ifsccode.length()>0){
			if(checkaction.equalsIgnoreCase("add")){
				getRequestDataMap().put(Constants.BankNameID,bankdetails.getString("banknameid"," "));
			}else{
				getRequestDataMap().put(Constants.BankNameID,i.getStringExtra("BankName"));

			}
		}
		else{
			getRequestDataMap().put(Constants.BankNameID,0);
		}

		getRequestDataMap().put(Constants.DOB, "04/12/1986");
		getRequestDataMap().put(Constants.isactive, "0");
		getRequestDataMap().put(Constants.contactid, "0");
		getRequestDataMap().put(Constants.areamanagerid, "0");

		try {
			getRequestDataMap().put(Constants.username, passworddetails.getString("username",""));
			getRequestDataMap().put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		EditText atmCardNo = (EditText)findViewById(R.id.edttxt_atmCardNo);

		getRequestDataMap().put(Constants.ATMNO, atmCardNo.getText().toString());

		RadioButton radioButton = (RadioButton) rgroup.getChildAt(1);
		if(radioButton.isChecked()){
			getRequestDataMap().put(Constants.ISATM,true);
		}else{
			getRequestDataMap().put(Constants.ISATM,false);
		}
		getRequestDataMap().put(Constants.panNumber, et_pan_card.getText().toString());

	}
	
	@Override
	protected void onRestart() {
		super.onRestart();

		if(!bankdetails.getString("ifsc","").equalsIgnoreCase(""))
			ifsccode.setText(bankdetails.getString("ifsc",""));
		else
			ifsccode.setText(IFsccode);

	}
	
	@Override
	public void startNextScreen() {
		super.startNextScreen();
		if(checkaction.equalsIgnoreCase("add")){
			postAfterMobileValidation();
		}else if(checkaction.equalsIgnoreCase("edit")){
			if(getRequestDataMap().get(Constants.PhoneNo).equals(i.getStringExtra("mobilenumber"))){
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
		Intent intent= null;
		switch (v.getId()) {
		case R.id.addretailercontact:
			if(validation()){
				getScreenData();
				startNextScreen();
			}
			break;
		case R.id.search:
			startActivity(new Intent(AddRetailerContact.this, BankSearch.class));
			break;
		case R.id.takephoto:
			// capture image from camera.
			intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, CAPTURE_IMAGE_CAMERA);
			break;
		case R.id.takephotofromgallary:
			// Take photo from gallary
			
			 try
			    {
			        intent = new Intent( Intent.ACTION_GET_CONTENT );
			        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			        intent.setType( "image/*" );
			        startActivityForResult( intent, CAPTURE_IMAGE_GALLARY);
			    }
			    catch ( ActivityNotFoundException e )
			    {
			        e.printStackTrace();
			    }
			break;
		default:
			break;
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		if(resultCode == RESULT_OK){
			
			if(requestCode == CAPTURE_IMAGE_CAMERA){
				mbitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
				mDocImage.setImageBitmap(mbitmap);
			}else if(requestCode== CAPTURE_IMAGE_GALLARY){
				
				 try {
		                // We need to recyle unused bitmaps
		                if (mbitmap != null) {
		                	mbitmap.recycle();
		                }
		                InputStream stream = getContentResolver().openInputStream(
		                        data.getData());
		                mbitmap = BitmapFactory.decodeStream(stream);
		                stream.close();
		                mDocImage.setImageBitmap(mbitmap);
		            } catch (FileNotFoundException e) {
		                e.printStackTrace();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
			}
			
			
			
		}
	}
	private void postalldata(){
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Uploading data Please wait..", new GetDataCallBack() {
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
						StringBuilder message = new StringBuilder();
						message.append("Welcome !!! Your mobile no ");
						message.append(mobilenumber.getText().toString());
						message.append(" is registered. Verification pending.");
						alert.setMessage(message.toString());

					}

					alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							if(checkaction.equalsIgnoreCase("edit")){
								if(! i.getStringExtra("mobilenumber" ).equalsIgnoreCase(mobilenumber.getText().toString())){
									//String smsmsg="Your Mobile No is Changed New No is "+mobilenumber.getText().toString();
									//SendMessage(smsmsg,mobilenumber.getText().toString());
									//SendMessageViaWebService("26", mobilienumbertag, mobilenumber.getText().toString(), false,i.getStringExtra("mobilenumber" ));
								}


								if(!i.getStringExtra("AccountNumber").equalsIgnoreCase(bankaccountnumber.getText().toString())){
									//SendMessageViaWebService("27", accountnumbertag,bankaccountnumber.getText().toString(), false,i.getStringExtra("mobilenumber" ));

								}

							}

							finish();
						}
					});
					alert.setCancelable(false);
					alert.show();
				}
			}
		});


		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName = "";
		if(checkaction.equalsIgnoreCase("add")){
			//methodName="SaveRetailerNew";
			methodName="SaveRetailerNew_PAN";
		}
		else if(checkaction.equalsIgnoreCase("edit")){
			methodName="UpdateRetailerFromAndroid";

		}
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(getRequestDataMap());
		dataFromNetwork.execute();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i;
		if(!checkaction.equalsIgnoreCase("add")){
			i=new Intent(AddRetailerContact.this, ViewRetailerContact.class);
			i.putExtra("areanameid",areaidforbackpress);
			startActivity(i);
		}
		finish();
	}

	void postAfterMobileValidation(){

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Validating Mobile Number Please wait..", new GetDataCallBack() {
			@Override
			public void processResponse(Object responseObject) {

				if(responseObject.toString().equals("1")){
					AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext());
					alert.setTitle(getStringFromResource(R.string.error6));
					alert.setCancelable(false);
					alert.setMessage(getStringFromResource(R.string.message6));
					alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent i=new Intent(AddRetailerContact.this, ViewAll_AM.class);
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

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.mobilenumberch, getRequestDataMap().get(Constants.PhoneNo));
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="IsContactExist";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void getContactTypeList() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Loading Contact Type..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					contacttypelist.clear();
					contacttypelist=new FetchingdataParser().getdealerparser(response.toString());
					if(contacttypelist.size()==0){
						Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.message4)+"2" , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : contacttypelist)
						{
							String contacttypes = (String) entry.get(Constants.contacttype);
							String contacttypeids = (String) entry.get(Constants.CONTACTTYPEID_1);
							if(!contacttype.contains(contacttypes))
								contacttype.add(contacttypes);
							if(!contacttypeid.contains(contacttypeids))
								contacttypeid.add(contacttypeids);
						}
						contacttype.remove("Dealer");
						contacttypeid.remove("14");
						ArrayAdapter<String> contacttypeadapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,contacttype);
						contacttypeadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						contacttypespinner.setAdapter(contacttypeadapter);

						if(checkaction.equalsIgnoreCase("edit")){
							contacttypespinner.setSelection(contacttypeid.indexOf(i.getStringExtra("contacttype")));
						}
					}

				}else if(response==null){
					Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
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
		String methodName="GetContactType";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void GetAccountType() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Loading Account Type..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					aaccounttypelist.clear();
					aaccounttypelist=new FetchingdataParser().getaccounttype(response.toString());
					if(aaccounttypelist.size()==0){
						Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.message4)+"2" , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : aaccounttypelist)
						{
							String accounttypes = (String) entry.get(Constants.AccountType);
							String accounttypesid = (String) entry.get(Constants.AccountTypeID);
							//  if(!accounttypelist.contains(accounttypes))
							accounttypelist.add(accounttypes);
							//   if(!accounttypeidlist.contains(accounttypesid))
							accounttypeidlist.add(accounttypesid);

						}
						ArrayAdapter<String> accounttypeadapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,accounttypelist);
						accounttypeadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						bankaccounttype.setAdapter(accounttypeadapter);
						if(checkaction.equalsIgnoreCase("edit")){
							bankaccountholder.setText(i.getStringExtra("AccountHolderName"));
							bankaccountnumber.setText(i.getStringExtra("AccountNumber"));
							ifsccode.setText(i.getStringExtra("IFSCCode"));
							IFsccode=i.getStringExtra("IFSCCode");
							bankaccounttype.setSelection(accounttypeidlist.indexOf(i.getStringExtra("AccountType")));
						}

					}

				}else if(response==null){
					Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
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
		String methodName="GetAccountType";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private void getGeographyByCode(final String geoName, final String geoId, final String areaType) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Loading "+areaType+" Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist=new FetchingdataParser().getarealistparser(response.toString());
					if(arealist.size()==0){
						Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
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

						if(checkaction.equalsIgnoreCase("edit")){
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_ZONE)){
								zonespinner.setSelection(zoneid.indexOf(i.getStringExtra("zoneid")));
							}
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_STATE)){
								statespinner.setSelection(stateidd.indexOf(i.getStringExtra("stateid")));

							}
							/*if(geoName.equalsIgnoreCase(Constants.GEOCODE_DISTRICT)){
								districtspinner.setSelection(districtid.indexOf(i.getStringExtra("districtid")));
							}*/
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_CITY)){
								cityspinner.setSelection(cityidd.indexOf(i.getStringExtra("cityid")));

							}
							if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA)){
								areaspinner.setSelection(areaidd.indexOf(i.getStringExtra("areaid")));

							}

						}
					}

				}else if(response==null){
					Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
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

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {

		case R.id.rb_bankdetails:
			banklayout.setVisibility(View.VISIBLE);
			atmLayout.setVisibility(View.GONE);

			break;
		case R.id.rb_atmdetails:
			atmLayout.setVisibility(View.VISIBLE);
			banklayout.setVisibility(View.GONE);
			break;

		default:
			break;
		}

	}

	private void GetVerificationDoctypeList() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Loading Account Type..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					VerificationDoctypeList.clear();
					VerificationDoctypeList=new FetchingdataParser().getverificationtype(response.toString());
					if(VerificationDoctypeList.size()==0){
						Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.message4)+"2" , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : VerificationDoctypeList)
						{
							String veriname = (String) entry.get(Constants.VerificationName);
							String veriid = (String) entry.get(Constants.VerificationID);
							if(!verificationname.contains(veriname))
								verificationname.add(veriname);
							if(!verificationid.contains(veriid))
								verificationid.add(veriid);

						}
						ArrayAdapter<String> verificationadapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,verificationname);
						verificationadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						verificationspinner.setAdapter(verificationadapter);
						//verificationspinner.setSelection(1);


					}

				}else if(response==null){
					Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
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
		String methodName="GetVerificationDoctypeList";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void SendMessageViaWebService(String EventID, String Tags, String Replacements, boolean IsMultiSMS, String mob) {
		
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(AddRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Loading ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){


				}else if(response==null){
					Toast.makeText(AddRetailerContact.this,getResources().getString(R.string.error4)+"errrfrom msg" , Toast.LENGTH_SHORT).show();

				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		try {
			request.put(Constants.EventID,EventID); 
			request.put(Constants.UserNameforsms,"arcis");
			request.put(Constants.passwordforsms,"archtp01");
			request.put(Constants.PhoneNumberforsms,mob);
			request.put(Constants.PhoneNumberFrom,"9212352102"); 
			request.put(Constants.AdditionalText,""); 
			request.put(Constants.Tags,Tags);
			request.put(Constants.Replacements,Replacements);
			request.put(Constants.IsMultiSMS,IsMultiSMS);
			for (Map.Entry<String, Object> entry : request.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				Log.d(key, value+"");
			}
		}

		catch (Exception e){
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SendMessageViaWebService";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	
	//{ContactType=AM, GeoType=CT, GeoID=33, ContactID=20, StateID=0, CityID=0, UserID=20, Password=BHn+UQ9w+RMTlWytgkoqaA==
	//}

	private void GetMappedGeographyForLogin(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId) {
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
									if(!cityname.contains(geonames)||cityname.contains(geonames))
										cityname.add(geonames);
								}
								else if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
									if(!areaidd.contains(geoids))
										areaidd.add(geoids);
									if(!areaname.contains(geonames))
										areaname.add(geonames);
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
						if(geoName.equalsIgnoreCase(Constants.GEOCODE_AREA_CUSTOMER)){
							ArrayAdapter<String> AMAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,areaname);
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
		String methodName="GetMappedGeographyForLogin";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		Log.d("request",">>>>> "+request);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	
	
	
	
}