package com.arcis.vgil.coupon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.BaseActivity;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.activity.GetDataCallBackCoupon;
import com.arcis.vgil.activity.LoginActivity;
import com.arcis.vgil.activity.Util;
import com.arcis.vgil.adapter.CouponCodeAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.CouponCode;
import com.arcis.vgil.model.DialogCouponInfo;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class Coupon extends BaseActivity {
	private String redemptionMsg;
	private CouponCode coupon;
	EditText couponredeemedText;
	EditText mobilenumber;
	Button search, scan, redeemed, clearList,submit;
	RadioButton dealer, machenics,account;
	LinearLayout couponlayout, mobilenumberlayout;
	TextView posi1, posi2, posi3, posi4, posi5,txt_summary;


	ArrayList<HashMap<String, Object>> Couponlist = new ArrayList<HashMap<String, Object>>();
	HashMap<String, ArrayList<String>> couponssucesssMAP = new HashMap<String, ArrayList<String>>();

	ArrayList<String> couponssucesss = new ArrayList<String>();
	ArrayList<String> approvalId 	    = new ArrayList<String>();
	ArrayList<DialogCouponInfo> couponDialogList = new ArrayList<DialogCouponInfo>();

	ArrayList<CouponCode> couponList=new ArrayList<>();;

	//HashMap<String, ArrayList<String>> couponexceptionMAP = new HashMap<String, ArrayList<String>>();

	/** Coupon Redemption error Type**/

	public static final int COUPON_NO_CORRECT_REDEEMEDBY_OTHER = 1;
	public static final int COUPON_NO_CORRECT_REDEEMEDBY_SAME = 2;

	public static final int WRONG_COUPON_ATTEMPT1 = 3;
	public static final int WRONG_COUPON_ATTEMPT2 = 4;
	public static final int WRONG_COUPON_ATTEMPT3 = 5;
	public static final int WRONG_COUPON_ATTEMPT4 = 6;

	public static final int COUPON_NOTACTIVATED = 7;
	public static final int MOBILENO_NOT_EXIST = 8;

	/** Coupon Redemption Type **/
	public static final int DEALER_FOR_DEALER = 10;
	public static final int AM_FOR_MECHANIC = 11;
	public static final int DEALER_FOR_MECHANIC = 12;
	public static final int MECHANIC_FOR_MECHANIC = 13;

	public static final int EXTRA_REDEEM_FOR_DEALER = 0;
	public static final int EXTRA_REDEEM_FOR_MECHANIC = 1;
	public static final int EXTRA_REDEEM_FOR_ACCOUNT = 2;
	public static final String EXTRA_REDEEM_TYPE = "RedeemType";

	private int mCouponType;
	private boolean isaccountLocked,isMechanicAccountExist;
	private String City,Area,name;
	private String mdealerCity,mdealerArea;
	private GetDataFromNetwork dataFromNetwork;


	public boolean isMechanicAccountExist() {
		return isMechanicAccountExist;
	}

	public void setMechanicAccountExist(boolean isMechanicAccountExist) {
		this.isMechanicAccountExist = isMechanicAccountExist;
	}

	int earnedpoints,bonus,counter;
	TableLayout tableLayout1;
	CheckBox cashcollected;
	String LoginMobilenumber;
	String Mechanicid;
	ArrayList<String> coupons = new ArrayList<String>();
	StringBuilder couponmessage ;
	ProgressDialog progressDialog;
	private boolean isDealerUIShown;// If true then for dealer otherwise mechanic

	private ListView mcouponCodeListView;
	CouponCodeAdapter couponAdapter;
	private String contactTypeID, mApprovalId;
	public static final String COUPON_EXTRA ="action";

	private ArrayList<String> couponRedeemOtherList = new ArrayList<String>();
	private ArrayList<String> couponRedeemSameList = new ArrayList<String>();
	private ArrayList<String> couponRedeematempt1List = new ArrayList<String>();
	private ArrayList<String> couponRedeemAttempt2List = new ArrayList<String>();
	private ArrayList<String> couponRedeemAttempt3List = new ArrayList<String>();
	private ArrayList<String> couponRedeemAttempt4List = new ArrayList<String>();
	private ArrayList<String> couponnotactivatedList = new ArrayList<String>();
	private ArrayList<String> multipleCouponexist  = new ArrayList<String>();
	private ArrayList<String> couponredeemSucessfull = new ArrayList<String>();


	@Override
	public void inti() {
		super.inti();
		setContentView(R.layout.coupon);
		setCurrentContext(this);
		mobilenumber=(EditText) findViewById(R.id.mobilenumber);
		couponredeemedText=(EditText) findViewById(R.id.coupon);
		search=(Button) findViewById(R.id.search);
		search.setOnClickListener(Coupon.this);
		scan=(Button) findViewById(R.id.scan);
		scan.setOnClickListener(Coupon.this);

		redeemed=(Button) findViewById(R.id.couponreedemed);
		redeemed.setOnClickListener(Coupon.this);

		clearList  = (Button)findViewById(R.id.clear);
		clearList.setOnClickListener(this);

		dealer=(RadioButton) findViewById(R.id.dealer_coupon);
		dealer.setOnCheckedChangeListener(Coupon.this);
		machenics=(RadioButton) findViewById(R.id.machenics);
		machenics.setOnCheckedChangeListener(Coupon.this);
		
		account=(RadioButton) findViewById(R.id.account);
		account.setOnCheckedChangeListener(Coupon.this);
		
		posi1=(TextView) findViewById(R.id.position2);
		posi2=(TextView) findViewById(R.id.position3);
		posi3=(TextView) findViewById(R.id.position4);
		posi4=(TextView) findViewById(R.id.position5);
		posi5=(TextView) findViewById(R.id.position6);
		cashcollected=(CheckBox)findViewById(R.id.cashcollected);
		cashcollected.setOnCheckedChangeListener(Coupon.this);
		couponlayout=(LinearLayout)findViewById(R.id.couponlayout);
		mobilenumberlayout=(LinearLayout)findViewById(R.id.mobilenumberlayout);
		tableLayout1=(TableLayout) findViewById(R.id.tableLayout1);

		mcouponCodeListView = (ListView)findViewById(android.R.id.list);
		mcouponCodeListView.setOnTouchListener(listTouchListener);

		submit  = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);

		txt_summary = (TextView)findViewById(R.id.summary);

		getAreaManagerDetail();
		SharedPreferences passworddetails= Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		contactTypeID = passworddetails.getString(Constants.CONTACTTYPEID,"");

		int redeemtype  = getIntent().getIntExtra(EXTRA_REDEEM_TYPE, -1);
		if(redeemtype!=-1){
			if(redeemtype==0){
				// Redeeming for Dealer
				dealer.setChecked(true);
				account.setVisibility(View.GONE);
				machenics.setVisibility(View.GONE);
				ValidateDealerMobileNumber();
				isDealerUIShown = true;
			}
			if(redeemtype==1){
				// Redeeming for Mechanic
				machenics.setChecked(true);
				account.setVisibility(View.GONE);
				dealer.setVisibility(View.GONE);
				isDealerUIShown = false;
			}
			if(redeemtype==2){
				// Redeeming for Account
				account.setChecked(true);
				machenics.setVisibility(View.GONE);
				dealer.setVisibility(View.GONE);
				ValidateDealerMobileNumber();
				isDealerUIShown = true;
			}
		}
		// ContactTypeID =14 for Dealer.
		// ContactTypeID =1 for AM.
		// ContactTypeID =3 for State Incharge.
		// ContactTypeID =16 for Mechanic.

		if(contactTypeID.equalsIgnoreCase("1") ||
				contactTypeID.equalsIgnoreCase("3")||
				contactTypeID.equalsIgnoreCase("4")||
				contactTypeID.equalsIgnoreCase("5")||
				contactTypeID.equalsIgnoreCase("16")){

			if(contactTypeID.equalsIgnoreCase("16")){
				cashcollected.setVisibility(View.INVISIBLE);
			}
			dealer.setEnabled(false);
		}else{
			dealer.setEnabled(true);
			cashcollected.setChecked(true);
			cashcollected.setEnabled(false);
		}

	}

	public String getmApprovalId() {
		if(mApprovalId!=null){
			return mApprovalId;
		}
		return "";
	}

	public void setmApprovalId(String mApprovalId) {
		this.mApprovalId = mApprovalId;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.scan:
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(Coupon.this);
			dialog.setMessage("Please use SPAY application instead of Camera");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent("com.google.zxing.client.android.SCAN");
					intent.putExtra("SCAN_MODE", "DATA_MATRIX_MODE");
					startActivityForResult(intent, 0);
				}
			});
			dialog.create().show();
			break;
		case R.id.couponreedemed:

			if(couponredeemedText.getText().length()>0){
				String redeemtext = couponredeemedText.getText().toString().trim();
				isCouponValidBeforeAdd(redeemtext);
			}
			else 
				couponredeemedText.setError(getStringFromResource(R.string.couponredemtion));
			break;
		case R.id.search:
		   // cashcollected.setChecked(false);
			if (mobilenumber.length() == 0 || mobilenumber.length()<10) {
				mobilenumber.setError(getStringFromResource(R.string.mobilenumber));
			}else{
				if(contactTypeID.equalsIgnoreCase("1")){
					ValidateMechanicMobileNoForAM();
				}else{
					ValidateMechanicMobileNo();
				}
				
				
			}
			break;

		case R.id.submit:
			// Get approvalId.
			GetApprovalId();

			couponredeemedText.setHint("Clear coupon list to redeem more");
			couponredeemedText.setEnabled(false);
			break;

		case R.id.clear:

			clearCouponList();

			break;
		default:
			break;
		}
	}

	private void PopulateCouponCodeList(CouponCode code){

		if(couponAdapter==null){
			ArrayList<CouponCode > couponCodeList = new ArrayList<CouponCode>();
			couponCodeList.add(code);
			couponAdapter = new CouponCodeAdapter(getCurrentContext(), R.layout.coupon_redemption_cell, couponCodeList);
			mcouponCodeListView.setAdapter(couponAdapter);
		}else{
			couponAdapter.add(code);
			couponAdapter.notifyDataSetChanged();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		try {
			
		
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {

				String contents = intent.getStringExtra("SCAN_RESULT");

				if(contents!=null){
					couponlayout.setVisibility(View.VISIBLE);
					if(machenics.isChecked())
						tableLayout1.setVisibility(View.VISIBLE);
					String[] te= contents.split(",");
					String s1=te[3];
					couponredeemedText.setText(s1);
					isCouponValidBeforeAdd(te[3]);
				}else{
					Toast.makeText(getCurrentContext(), getStringFromResource(R.string.message4), Toast.LENGTH_SHORT).show();
				}

				
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		super.onCheckedChanged(buttonView, isChecked);
		switch (buttonView.getId()) {

		case R.id.machenics:

			if(isChecked){
				mCouponType = 1;
				if(couponAdapter !=null && couponAdapter.getCouponList().size()>0){
					clearCouponList();
				}

				scan.setVisibility(View.VISIBLE);
				couponlayout.setVisibility(View.VISIBLE);
				if(!contactTypeID.equalsIgnoreCase("16")){
					mobilenumberlayout.setVisibility(View.VISIBLE);
					cashcollected.setVisibility(View.VISIBLE);
					
				}

			}else{
				mobilenumberlayout.setVisibility(View.GONE);
				couponlayout.setVisibility(View.VISIBLE);
				cashcollected.setVisibility(View.GONE);
			}
			break;
		case R.id.dealer_coupon:

			if(isChecked){
				mCouponType = 0;
				if(couponAdapter!=null && couponAdapter.getCouponList().size()>0){
					clearCouponList();
				}
				mobilenumberlayout.setVisibility(View.GONE);
				tableLayout1.setVisibility(View.GONE);
				couponlayout.setVisibility(View.VISIBLE);
				scan.setVisibility(View.INVISIBLE);
			}else{
				mobilenumberlayout.setVisibility(View.VISIBLE);
				couponlayout.setVisibility(View.GONE);
				tableLayout1.setVisibility(View.GONE);
			}
			cashcollected.setVisibility(View.GONE);
			break;
			
		case R.id.account:

			if(isChecked){
				mCouponType = 1;
				if(couponAdapter !=null && couponAdapter.getCouponList().size()>0){
					clearCouponList();
				}

				scan.setVisibility(View.VISIBLE);
				couponlayout.setVisibility(View.VISIBLE);
				if(!contactTypeID.equalsIgnoreCase("16")){
					mobilenumberlayout.setVisibility(View.VISIBLE);
					cashcollected.setVisibility(View.VISIBLE);
					
				}

			}else{
				mobilenumberlayout.setVisibility(View.GONE);
				couponlayout.setVisibility(View.VISIBLE);
				cashcollected.setVisibility(View.GONE);
			}
			break;

		case R.id.cashcollected:

			if((contactTypeID.equals("1")||contactTypeID.equals("3")||contactTypeID.equals("4")||contactTypeID.equals("5")) && !isChecked){
				if(!isMechanicAccountExist()){
					// 
					AlertDialog.Builder dialog = new AlertDialog.Builder(Coupon.this);
					dialog.setMessage(getStringFromResource(R.string.mechanicaccount));
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							cashcollected.setChecked(true);
						}
					});
					dialog.create().show();
				}
			}
		default:
			break;
		}
	}


	private void InsertRedeemExceptionLog(String ErrorLog, String coupoontext) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Loading ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					//					Intent i=new Intent(Coupon.this, ViewAll.class);
					//					startActivity(i);
					//					finish();
				}else if(response==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		try {
			SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
			request.put(Constants.CouponCode,coupoontext);
			//request.put(Constants.CouponCode,couponredeemed.getText().toString());
			request.put(Constants.ErrorLog,ErrorLog);
			if(machenics.isChecked()){
				String jai=passworddetails.getString(Constants.MOBILE_NUMBER,"");
				request.put(Constants.MOBILE_NUMBER,passworddetails.getString(Constants.MOBILE_NUMBER,""));
			}
			else{
				request.put(Constants.MOBILE_NUMBER,LoginMobilenumber);
			}
			
			request.put(Constants.RedeemerID,passworddetails.getString("createdBy",""));    //// 1,2,3,4
			
			if(contactTypeID.equalsIgnoreCase("14"))
				request.put(Constants.UserTypeID,"1");
			
			else if (contactTypeID.equalsIgnoreCase("1"))
				request.put(Constants.UserTypeID,"3");
			
			else if (contactTypeID.equalsIgnoreCase("16"))
				request.put(Constants.UserTypeID,"2");
			
			else if (contactTypeID.equalsIgnoreCase("15")
					||contactTypeID.equalsIgnoreCase("17")
					||contactTypeID.equalsIgnoreCase("19")
					||contactTypeID.equalsIgnoreCase("20")){
				
				request.put(Constants.UserTypeID,"4");


			}
        	request.put(Constants.username, passworddetails.getString("createdBy",""));
			request.put(Constants.password,passworddetails.getString("password",""));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="InsertRedeemExceptionLog";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}



	private void ValidateDealerMobileNumber() {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Loading ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {

				if(response==null){
					LoginMobilenumber = "Not Available";
				}else{
					HashMap<String, String> dealerMAp = FetchingdataParser.getDealerInfo(response.toString());

					if(dealerMAp!=null){
						LoginMobilenumber = dealerMAp.get(Constants.MOBILE_NUMBER);
						Coupon.this.mdealerCity = dealerMAp.get(Constants.city);
						Coupon.this.mdealerArea = dealerMAp.get(Constants.area);
					}else{
						LoginMobilenumber = "Not Available";
					}
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		try {
			SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
			request.put(Constants.TYPE, passworddetails.getString("usertype",""));
			request.put(Constants.dealerid, passworddetails.getString("username", ""));
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerDetails";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void ValidateMechanicMobileNo() {

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Searching Mechanics ..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					Couponlist.clear();
					Couponlist=new FetchingdataParser().isMechanicExist(response.toString());
					if(Couponlist.size()==0){

						couponlayout.setVisibility(View.GONE);
						tableLayout1.setVisibility(View.GONE);
						cashcollected.setVisibility(View.GONE);
						AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext(),R.style.MyAlertDialogStyle);
						alert.setCancelable(false);
						alert.setTitle(getStringFromResource(R.string.phonenonotregister));
						alert.setMessage(getStringFromResource(R.string.message7));
						alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								couponlayout.setVisibility(View.VISIBLE);
							}
						});
						alert.setNegativeButton(android.R.string.no, new  DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();

							}
						});
						alert.show();
					}else {
						couponlayout.setVisibility(View.VISIBLE);
						tableLayout1.setVisibility(View.VISIBLE);
						String name = null ,area = null,address = null,city = null,state = null,accountNo = null;

						for (HashMap<String, Object> entry : Couponlist)
						{
							Mechanicid= (String) entry.get(Constants.ID);
							name = (String) entry.get(Constants.CONTACTNAME_1);
							area = (String) entry.get(Constants.area);
							address = (String) entry.get(Constants.streetaddress);
							city = (String) entry.get(Constants.city);
							state = (String) entry.get(Constants.state);
							accountNo = (String)entry.get(Constants.AccountNumber);

						}
						if(name!=null)
							posi1.setText(name);
						if(address!=null)
							posi2.setText(address);
						if(area!=null)
							posi3.setText(area);
						if(city!=null)
							posi4.setText(city);
						if(state!=null)
							posi5.setText(state);
						if(accountNo.length()>1){
							setMechanicAccountExist(true);
						}else{
							setMechanicAccountExist(false);
						}

						// If account info for mechanic is not available he is not able 
						// redeem coupon.
						if(contactTypeID.equals("16") && !isMechanicAccountExist()){
							AlertDialog.Builder dialog = new AlertDialog.Builder(Coupon.this);
							dialog.setMessage(getStringFromResource(R.string.mechanicaccount));
							dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									finish();
								}
							});
							dialog.create().show();
						}
					}

				}else if(response==null){
					couponlayout.setVisibility(View.GONE);
					tableLayout1.setVisibility(View.GONE);
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			request.put(Constants.MOBILE_NUMBER, mobilenumber.getText().toString());
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="IsMechanicExist";
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

	void showRedeemedCouponStatusDialog(StringBuilder data){

		AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext(),R.style.MyAlertDialogStyle);
		alert.setCancelable(false);
		alert.setPositiveButton(android.R.string.ok, new  DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				couponredeemedText.setText("");
				couponmessage = null;
				couponssucesss.clear();
				couponssucesssMAP.clear();
				approvalId.clear();
				earnedpoints = 0;
				bonus = 0;
				counter = 0;
				if(isaccountLocked){
					Intent intent  = new Intent(Coupon.this,LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}
		});
		alert.setMessage(data.toString());
		alert.show();

	}

	/**
	 * Shows redeemed coupon status message.
	 */
	void showRedeemedCouponStatusDialog(int coupontype, String redeemerId ){

		AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext(),R.style.MyAlertDialogStyle);
		alert.setCancelable(false);
		alert.setPositiveButton(android.R.string.ok, new  DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				couponredeemedText.setText("");
				couponmessage = null;
				couponssucesss.clear();
				couponssucesssMAP.clear();
				approvalId.clear();
				earnedpoints = 0;
				bonus = 0;
				counter = 0;
				couponRedeemOtherList.clear();
				couponRedeemSameList.clear();
				couponRedeematempt1List.clear();
				couponRedeemAttempt2List.clear();
				couponRedeemAttempt3List.clear();
				couponRedeemAttempt4List.clear();
				couponnotactivatedList.clear();
				couponredeemSucessfull.clear();
				if(isaccountLocked){
					Intent intent  = new Intent(Coupon.this,LoginActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}
		});

		StringBuilder msgBuilder = new StringBuilder();
		String MechanicMobNo = mobilenumber.getText().toString();
		SharedPreferences AMPref = Util.getAreaMangerSharedPreferences(getCurrentContext());
		String amName = AMPref.getString(Constants.AM_NAME, "");
		String amMobNo = AMPref.getString(Constants.AM_MobNo, "");

		String AMmsg = " Please contact "+amName+" Cell no."+ amMobNo;

		int couponredeemerType  = getCouponRedeemedBy(coupontype, redeemerId);

		switch (couponredeemerType) {

		// Dealer Redeeming dealer coupon
		case DEALER_FOR_DEALER:


			// iterate for each exeptiontype list and build the mesaage. 

			if(couponRedeemOtherList.size()>0){
				msgBuilder.append( "Redemption for coupon no ");
				for(String code: couponRedeemOtherList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed Reason � Coupon already redeemed. ");
				msgBuilder.append(AMmsg);
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemSameList.size()>0){
				msgBuilder.append( "Redemption for coupon no ");
				for(String code: couponRedeemSameList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed Reason � Coupon already redeemed. Approval ID ");
				msgBuilder.append(getmApprovalId());
				msgBuilder.append("\n");
			}
			else if(couponRedeematempt1List.size()>0){

				msgBuilder.append("Coupon no ");
				for(String code: couponRedeematempt1List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Please check and retry");
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemAttempt2List.size()>0){
				msgBuilder.append("Coupon no ");
				for(String code: couponRedeemAttempt2List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Please check and retry");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemAttempt3List.size()>0){

				msgBuilder.append("Coupon no ");
				for(String code: couponRedeemAttempt3List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Next wrong attempt will lead to your account being locked.");
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemAttempt4List.size()>0){
				msgBuilder.append("Your account has been locked due multiple wrong attempts. ");
				msgBuilder.append(AMmsg);
				msgBuilder.append("\n\n");
			}

			else if(couponnotactivatedList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponnotactivatedList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append("rejected");
				msgBuilder.append(AMmsg);
				msgBuilder.append("\n\n");
			}

			break;
			// AM redeeming Mechanic coupon
		case AM_FOR_MECHANIC:



			if(couponRedeemOtherList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemOtherList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed for mobile number ");
				msgBuilder.append(MechanicMobNo);
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemSameList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemSameList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed Reason � Coupon already redeemed for this mobile no. ");
				msgBuilder.append(MechanicMobNo);
				msgBuilder.append("Approval ID ");
				msgBuilder.append(getmApprovalId());
				msgBuilder.append("\n\n");
			}

			else if(couponRedeematempt1List.size()>0){

				msgBuilder.append("Coupon no ");
				for(String code: couponRedeematempt1List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Please check and retry");
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemAttempt2List.size()>0){

				msgBuilder.append("Coupon no ");
				for(String code: couponRedeemAttempt2List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Please check and retry");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemAttempt3List.size()>0){
				msgBuilder.append("Coupon no ");
				for(String code: couponRedeemAttempt3List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Next wrong attempt will lead to your account being locked.");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemAttempt4List.size()>0){
				msgBuilder.append("Your account has been locked due multiple wrong attempts.Please contact Helpdesk - 02533298603");
				msgBuilder.append("\n\n");
			}

			else if(couponnotactivatedList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");

				StringBuilder emailString  = new StringBuilder("Redemption for coupon ");
				for(String code: couponnotactivatedList){
					msgBuilder.append(code);
					msgBuilder.append(",");

					emailString.append(code);
					emailString.append(",");
				}
				msgBuilder.append(" failed for mobile number ");
				msgBuilder.append(MechanicMobNo);
				msgBuilder.append(" Reason � Coupon number not activated.Please investigate.");
				msgBuilder.append("\n\n");

				emailString.append(" rejected .<br> Please investigate source of purchase");
				// Send mail to AM
				SendEmaiToAM(emailString.toString(), Constants.COUPON_MAIL_SUBJECT);
			}


			break;
			// Dealer Redeeming mechanic coupon.

		case DEALER_FOR_MECHANIC:


			if(couponRedeemOtherList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemOtherList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed for mobile number ");
				msgBuilder.append(MechanicMobNo);
				msgBuilder.append(" Reason �  Coupon already redeemed. Please contact Area Manager.");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemSameList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemSameList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed Reason � Coupon already redeemed. Approval ID ");
				msgBuilder.append(getmApprovalId());
				msgBuilder.append("\n\n");
			}
			else if(couponRedeematempt1List.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeematempt1List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed due to wrong coupon number.Please Retry");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemAttempt2List.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemAttempt2List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed due to wrong coupon number.Please Retry");
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemAttempt3List.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemAttempt3List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed due to wrong coupon number.Please Retry");
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemAttempt4List.size()>0){
				msgBuilder.append("Your account has been locked due to multiple wrong attempts.Please contact Area Manager");
				msgBuilder.append("\n\n");
			}

			else if(couponnotactivatedList.size()>0){
				msgBuilder.append("Redemption for coupon no.");
				for(String code: couponnotactivatedList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed.Please contact Area Manager.");
				msgBuilder.append("\n\n");
			}

			break;
			// Mechanic redeeming mechanic Coupon.
		case MECHANIC_FOR_MECHANIC:

			if(couponRedeemOtherList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemOtherList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed.Reason �  Coupon already redeemed. ");
				msgBuilder.append(AMmsg);
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemSameList.size()>0){
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemSameList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" failed Reason � Coupon already redeemed. Approval ID ");
				msgBuilder.append(getmApprovalId());
				msgBuilder.append("\n\n");
			}
			else if(couponRedeematempt1List.size()>0){
				msgBuilder.append("Coupon no ");
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeematempt1List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Please check and retry");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemAttempt2List.size()>0){

				msgBuilder.append("Coupon no ");
				msgBuilder.append("Redemption for coupon no. ");
				for(String code: couponRedeemAttempt2List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Please check and retry");
				msgBuilder.append("\n\n");
			}
			else if(couponRedeemAttempt3List.size()>0){
				msgBuilder.append("Coupon no ");
				for(String code: couponRedeemAttempt3List){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" does not exist.Next wrong attempt will lead to your account being locked.");
				msgBuilder.append("\n\n");
			}

			else if(couponRedeemAttempt4List.size()>0){
				msgBuilder.append("Your account has been locked due multiple wrong attempts. ");
				msgBuilder.append(AMmsg);
				msgBuilder.append("\n\n");
			}
			else if(couponnotactivatedList.size()>0){
				msgBuilder.append("Redemption for coupon no.");
				for(String code: couponnotactivatedList){
					msgBuilder.append(code);
					msgBuilder.append(",");
				}
				msgBuilder.append(" rejected");
				msgBuilder.append(AMmsg);
				msgBuilder.append("\n\n");
			}
			break;
		}

		if(couponredeemSucessfull.size()>0){
			msgBuilder.append(couponredeemSucessfull.get(0));
		}

		txt_summary.setText(msgBuilder.toString());
		Log.i("Pop up dialog message", msgBuilder.toString());
		alert.setMessage(msgBuilder.toString());

		alert.show();

	}


//@@@@@@@@@@@@@@@@@@@@@@@@@
	public  void ValidateCoupon(final String coupontext,  int index) {

		/*** Individual class Used only for Coupon redemption ***/
		GetDataFromNetworkCoupon datafromnetwork = new GetDataFromNetworkCoupon(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Validating coupen...",coupontext,index, new GetDataCallBackCoupon() {

			@Override
			public void processResponse(Object result, String couponcode, int index) {
				// TODO Auto-generated method stub

				boolean addmsg=true;
				boolean checkvalidation=true;
				String InvoicingRefNo = null,RedeemDate = null,coupenId=null,couponTypeId=null, redeemedToId=null,approvalID = null;
				Object couponcheckresponse = result;
				String couponCode = couponcode;
				if(couponcheckresponse==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
					counter++;
					return ;
				}
				SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
				String couponRedeemerID = passworddetails.getString(Constants.CONTACTTYPEID,"");
				redemptionMsg ="";

				//#######################
					coupon = couponAdapter.getCouponList().get(index);

				/*if (couponAdapter.getCouponList().size()>0){
					coupon = couponAdapter.getCouponList().get(0);
					couponAdapter.getCouponList().remove(0);
				}
*/

				Couponlist.clear();
				Couponlist=new FetchingdataParser().couponvalidation(couponcheckresponse.toString());

				if(Couponlist.size()==0){

					int count = CheckInvalidCouponRedemtionAttempt(couponCode);
					if(count!=-1){
						if(count==1){
							couponRedeematempt1List.add(couponCode);
							redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, WRONG_COUPON_ATTEMPT1, couponCode, getmApprovalId());
						}else if(count==2){
							couponRedeemAttempt2List.add(couponCode);
							redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, WRONG_COUPON_ATTEMPT2, couponCode, getmApprovalId());
						}else if(count==3){
							couponRedeemAttempt3List.add(couponCode);
							redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, WRONG_COUPON_ATTEMPT3, couponCode, getmApprovalId());
						}else if(count>=4){
							couponRedeemAttempt4List.add(couponCode);
							redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, WRONG_COUPON_ATTEMPT4, couponCode, getmApprovalId());
							isaccountLocked = true;
						}
					}else{
						couponRedeematempt1List.add(couponCode);
						redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, WRONG_COUPON_ATTEMPT1, couponCode, getmApprovalId());
					}

					addmsg=false;
					checkvalidation=false;
					coupon.setCode(couponCode);
					coupon.setStatus(redemptionMsg);
					coupon.setIsredeemedSucessful(false);
					InsertRedeemExceptionLog(redemptionMsg,coupon.getCode());

				}else {

					for (HashMap<String, Object> entry : Couponlist)
					{
						coupenId = (String) entry.get(Constants.CouponID);
						couponTypeId = (String) entry.get(Constants.CouponTypeID);
						InvoicingRefNo = (String) entry.get(Constants.InvoicingRefNo);
						RedeemDate = (String) entry.get(Constants.RedeemDate);
						couponCode = (String)entry.get(Constants.CouponCode);
						redeemedToId = (String)entry.get(Constants.RedeemedToID);
						Coupon.this.City =(String)entry.get(Constants.city);
						Coupon.this.Area =  (String)entry.get(Constants.area);
						Coupon.this.name  = (String)entry.get(Constants.NAME);
						approvalID = (String)entry.get(Constants.APPROVALID);
					}

					coupon.setCode(couponCode);
					if(couponTypeId.equals("1") && couponRedeemerID.equalsIgnoreCase("16")){

						redemptionMsg = "You are not athorized to redeem this coupon";
						couponmessage.append(redemptionMsg+" \n");
						showRedeemedCouponStatusDialog(couponmessage);
						counter++;
						return;

					}

					if (!RedeemDate.equals("") ){

						if(Mechanicid!=null && redeemedToId.equalsIgnoreCase(Mechanicid)){

							couponRedeemOtherList.add(couponCode);
							redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, COUPON_NO_CORRECT_REDEEMEDBY_SAME, couponCode, approvalID);
						}else{
							couponRedeemSameList.add(couponCode);
							redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, COUPON_NO_CORRECT_REDEEMEDBY_OTHER, couponCode, approvalID);
						}
						if(addmsg){
							addmsg=false;
							//couponmessage.append(redemptionMsg+"\n");
						}
						checkvalidation=false;
						coupon.setStatus(redemptionMsg);
						coupon.setIsredeemedSucessful(false);
						InsertRedeemExceptionLog(redemptionMsg,coupon.getCode());

					}
					if (InvoicingRefNo.equals("")){

						couponnotactivatedList.add(couponCode);
						redemptionMsg =  getCouponRedemtionFailMessage(mCouponType, couponRedeemerID, COUPON_NOTACTIVATED, couponCode, approvalID);
						if(addmsg){
							addmsg=false;
							//couponmessage.append(redemptionMsg+"\n");
						}
						checkvalidation=false;
						coupon.setStatus(redemptionMsg);
						coupon.setIsredeemedSucessful(false);
						InsertRedeemExceptionLog(redemptionMsg,coupon.getCode());

					}

					if (Couponlist.size()>1)
					{	if(addmsg){
						addmsg=false;

						redemptionMsg = "Multiple coupon exist";
						multipleCouponexist.add(redemptionMsg);
						//couponmessage.append(redemptionMsg+"\n");
					}
					checkvalidation=false;
					coupon.setStatus(redemptionMsg);
					InsertRedeemExceptionLog(redemptionMsg,coupon.getCode());
					}

				}
				if(checkvalidation){
					RedeemCoupon(coupenId,couponTypeId,index,couponRedeemerID);

				}else {
					couponAdapter.getCouponList().set(index, coupon);
					couponAdapter.notifyDataSetChanged();
					if(counter==couponAdapter.getCount()-1){

						// Send  SMS only for Successfull redeemtion.
						TextView totalAmount = (TextView)findViewById(R.id.totalamount);
						totalAmount.setText(String.valueOf(earnedpoints+bonus));

						String smsText  = "";
						if(approvalId.size()>0){
							//couponssucesssMAP.put("9",couponssucesss);
							redemptionMsg = getCouponRedeemedSuccessfullyMsg(mCouponType, couponRedeemerID, getmApprovalId(), couponAdapter.getCouponList());
							couponredeemSucessfull.add(redemptionMsg);
							//couponmessage.append(redemptionMsg +"\n");
							smsText = getSMSText(mCouponType, couponRedeemerID, getmApprovalId(), couponAdapter.getCouponList());
							if(smsText.length()>0){
								SendSMS(smsText);
							}

						}
						//%%%%%%%%%%%%%%%%%%%
						showRedeemedCouponStatusDialog(mCouponType,couponRedeemerID);
					}
					counter++;
				}




			}
		});

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CheckCouponStatus";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.CouponCode, coupontext);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		}
		catch (Exception e){
			e.printStackTrace();
		}
		datafromnetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
		datafromnetwork.sendData(request);
		datafromnetwork.execute();

	}
//a@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#################################
	//################################################################
	public  void RedeemCoupon(String Couponid, String CouponTypeid, final int index, final String couponRedeemerID){

/*** Individual class Used only for Coupon redemption ***/
		CouponRedeemNetwork couponRedeemNetwork = new CouponRedeemNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Validating coup0n...",new CouponRedeemCallBack() {
			@Override
			public void processResponse(Object result) {
				if (result!=null){
					if(result!=null){
						CouponCode couponCodeT=couponAdapter.getCouponList().get(index);
						if(result.toString().contains("@@ERROR --->")){
							String err=result.toString().replace("@@ERROR --->", "");
							couponCodeT.setStatus(err);
							couponmessage.append(result.toString()+"\n");
						}else{
							String sp[]=result.toString().split(",");
							couponCodeT.setPartNo(sp[0]);
							couponCodeT.setEarnedpoint(Integer.valueOf(sp[2]));
							couponCodeT.setBonus(Integer.valueOf(sp[3]));
							couponCodeT.setStatus("Your Coupon Redeemed Sucessfully");
							couponCodeT.setEffort(Integer.valueOf(sp[4]));
							couponCodeT.setIsredeemedSucessful(true);
							approvalId.add(sp[1]);
							earnedpoints=earnedpoints+ Integer.valueOf(sp[2]);
							bonus=bonus+ Integer.valueOf(sp[3]);
						}

						//couponList.add(coupon);
						//for (CouponCode couponCodeT:couponList) {
						//	couponAdapter.getCouponList().add(index, coupon);
						//}
						couponAdapter.getCouponList().set(index, couponCodeT);
						couponAdapter.notifyDataSetChanged();
						if(counter==couponAdapter.getCount()-1){

							// Send  SMS only for Successfull redeemtion.
							TextView totalAmount = (TextView)findViewById(R.id.totalamount);
							totalAmount.setText(String.valueOf(earnedpoints+bonus));

							String smsText  = "";
							if(approvalId.size()>0){
								//couponssucesssMAP.put("9",couponssucesss);
								redemptionMsg = getCouponRedeemedSuccessfullyMsg(mCouponType, couponRedeemerID, getmApprovalId(), couponAdapter.getCouponList());
								couponredeemSucessfull.add(redemptionMsg);
								//couponmessage.append(redemptionMsg +"\n");
								smsText = getSMSText(mCouponType, couponRedeemerID, getmApprovalId(), couponAdapter.getCouponList());
								if(smsText.length()>0){
									SendSMS(smsText);
								}

							}
							//%%%%%%%%%%%%%%%%%%%
							showRedeemedCouponStatusDialog(mCouponType,couponRedeemerID);
						}
						counter++;

					}
				}


			}

			});
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {

			// for UnregisteredMechanic Cash should be 1.
			request.put(Constants.CouponID,Couponid);
			if(cashcollected.isChecked())
				request.put(Constants.Cash,"1");
			else
				request.put(Constants.Cash,"0");

			request.put(Constants.CouponTypeID,CouponTypeid);
			request.put(Constants.RedeemerByID,passworddetails.getString("username",""));

			if(contactTypeID.equalsIgnoreCase("14"))
				request.put(Constants.RedeemerByTypeID,"1");
			else if (contactTypeID.equalsIgnoreCase("1"))
				request.put(Constants.RedeemerByTypeID,"3");
			else if (contactTypeID.equalsIgnoreCase("3"))
				request.put(Constants.RedeemerByTypeID,"5");
			else if (contactTypeID.equalsIgnoreCase("4"))
				request.put(Constants.RedeemerByTypeID,"13");
			else if (contactTypeID.equalsIgnoreCase("5"))
				request.put(Constants.RedeemerByTypeID,"12");
			else if (contactTypeID.equalsIgnoreCase("16"))//@@@@@@@@@@@@@@@@@@@@@@@@@@@@ jai
				request.put(Constants.RedeemerByTypeID,"2");
			else if (contactTypeID.equalsIgnoreCase("15")
					||contactTypeID.equalsIgnoreCase("17")
					||contactTypeID.equalsIgnoreCase("19")
					||contactTypeID.equalsIgnoreCase("20")){
				request.put(Constants.RedeemerByTypeID,"4");


			}

			if(machenics.isChecked() && Mechanicid!=null){
				request.put(Constants.RedeemedToID,Mechanicid);
			}else{
				request.put(Constants.RedeemedToID,passworddetails.getString("username",""));
			}

			if(machenics.isChecked()){
				if(Mechanicid==null){
					if(contactTypeID.equals("14")){
						request.put(Constants.RedeemedToTypeID,"1");
					}else if(contactTypeID.equals("1")){
						request.put(Constants.RedeemedToTypeID,"3");
					}else if(contactTypeID.equalsIgnoreCase("15")||contactTypeID.equalsIgnoreCase("17")||contactTypeID.equalsIgnoreCase("19")||contactTypeID.equalsIgnoreCase("20")){
						request.put(Constants.RedeemedToTypeID,"4");
					}
					else if (contactTypeID.equals("16"))//@@@@@@@@@@@@@@@@@@@@@@@@@@@@ jai Made change
						request.put(Constants.RedeemedToTypeID,"2");
					else if (contactTypeID.equals("3"))
						request.put(Constants.RedeemedToTypeID,"5");
					else if (contactTypeID.equals("4"))
						request.put(Constants.RedeemedToTypeID,"13");
					else if (contactTypeID.equals("5"))
						request.put(Constants.RedeemedToTypeID,"12");
				}
				else{
					request.put(Constants.RedeemedToTypeID,"2");
				}

			}else{
				if(contactTypeID.equals("14")){
					request.put(Constants.RedeemedToTypeID,"1");
				}else if(contactTypeID.equals("1")){
					request.put(Constants.RedeemedToTypeID,"3");
				}else if(contactTypeID.equalsIgnoreCase("15")
						||contactTypeID.equalsIgnoreCase("17")
						||contactTypeID.equalsIgnoreCase("19")
						||contactTypeID.equalsIgnoreCase("20")){
					request.put(Constants.RedeemedToTypeID,"4");

				}
			}


			request.put("InputApprovalID", getmApprovalId());
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

			String ipAddress=getResources().getString(R.string.ipaddress);
			String webService =getResources().getString(R.string.webService);
			String nameSpace=getResources().getString(R.string.nameSpace);
			String methodName="RedeemCoupon";
			String soapcomponent=getResources().getString(R.string.soapcomponent);
			couponRedeemNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
			couponRedeemNetwork.sendData(request);
			Log.d(">>>","Redeem coupon request   "+request);
			couponRedeemNetwork.execute();

		}
		catch (Exception e){
			e.printStackTrace();
		}


	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


	/**
	 * Clear coupon list before switching to new redemption.
	 */
	private void clearCouponList(){

		AlertDialog.Builder dialog = new AlertDialog.Builder(getCurrentContext());
		dialog.setMessage("Would you like to clear coupons from list?");
		dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				couponAdapter.clear();
				couponAdapter.notifyDataSetChanged();
				txt_summary.setText("");
				couponredeemedText.setHint(getStringFromResource(R.string.entercoupon));
				couponredeemedText.setEnabled(true);
				dialog.dismiss();
			}
		});

		dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialog.create().show();
	}


	/**
	 *  Returns coupon fail  message.
	 *   
	 * @param couponType : type of coupon Either Dealer or mechanic. 0 for Dealer, 1 For Mechanic
	 * @param couponRedeemBy :ContactTypeID for Redeemer .
	 * @param errorType: coupon fail error type.
	 * @return
	 */
	private String getCouponRedemtionFailMessage(int couponType, String couponRedeemBy , int errorType, String couponCode, String approvalId){

		int couponRedemtionBy = getCouponRedeemedBy(couponType, couponRedeemBy);
		String couponmessage = "";
		String emailMesage = null;

		String MechanicMobNo = mobilenumber.getText().toString();
		SharedPreferences pref = Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);

		SharedPreferences AMPref = Util.getAreaMangerSharedPreferences(getCurrentContext());
		String amName = AMPref.getString(Constants.AM_NAME, "");
		String amMobNo = AMPref.getString(Constants.AM_MobNo, "");
		String amcity = AMPref.getString(Constants.AM_CITY, "");
		String amemail = AMPref.getString(Constants.AM_EMAIL, "");
		String amLocation = AMPref.getString(Constants.area, Constants.area);

		String AMmsg = " Please contact "+amName+" Cell no."+ amMobNo;
		String smsmsg = null;
		switch (couponRedemtionBy) {

		// Dealer Redeeming dealer coupon
		case DEALER_FOR_DEALER:

			switch (errorType) {

			case COUPON_NO_CORRECT_REDEEMEDBY_OTHER:
				couponmessage = "Redemption for coupon no " +couponCode +" failed Reason � Coupon already redeemed. "+AMmsg;

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+ LoginMobilenumber+
						"<br>"+
						" Reason �  Coupon already redeemed by "+	
						"<br>"+
						" Name - " + pref.getString(Constants.CONTACTNAME, "")+
						"<br>"+
						" City - "+ Coupon.this.City+
						"<br>"+
						" Location - " + Coupon.this.Area+
						"<br>"+" Please investigate.";

				// Send Mail To AM.
				SendEmaiToAM(emailMesage,Constants.COUPON_MAIL_SUBJECT);
				break;

			case COUPON_NO_CORRECT_REDEEMEDBY_SAME:
				couponmessage = "Redemption for coupon no "+couponCode + " failed Reason � Coupon already redeemed. Approval ID "+approvalId;

				break;

			case WRONG_COUPON_ATTEMPT1:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Please check and retry";

				break;

			case WRONG_COUPON_ATTEMPT2:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Please check and retry";

				break;

			case WRONG_COUPON_ATTEMPT3:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Next wrong attempt will lead to your account being locked.";

				break;

			case WRONG_COUPON_ATTEMPT4:
				couponmessage = "Your account has been locked due multiple wrong attempts. "+AMmsg;

				break;

			case COUPON_NOTACTIVATED:
				couponmessage = "Redemption for coupon no. "+couponCode+" rejected. "+AMmsg;

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+ LoginMobilenumber+
						"<br>"+
						" Reason �  Coupon number not activated. "+	
						"<br>"+
						" City - "+ Coupon.this.mdealerCity+
						"<br>"+
						" Location - " + Coupon.this.mdealerArea+ 
						"<br>"+
						"<br>"+
						" Please investigate!";

				// Send Mail To AM.
				SendEmaiToAM(emailMesage,Constants.COUPON_MAIL_SUBJECT);

				break;

			default:
				break;
			}
			break;

			// AM redeeming Mechanic coupon
		case AM_FOR_MECHANIC:

			switch (errorType) {

			case COUPON_NO_CORRECT_REDEEMEDBY_OTHER:
				couponmessage = "Redemption for coupon no. "+couponCode +" failed for mobile number "+MechanicMobNo;

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+MechanicMobNo+
						"<br>"+
						" Reason �  Coupon already redeemed by "+
						"<br>"+
						" Name - "+ Coupon.this.name+
						"<br>"+
						" City - "+Coupon.this.City+
						"<br>"+
						" Location - "+ Coupon.this.Area+
						"<br>"+
						" Please investigate.";
				SendEmaiToAM(emailMesage, Constants.COUPON_MAIL_SUBJECT);

				smsmsg = "Redemption for coupon. "+ couponCode + " failed . Coupon already redeemed. Contact. " +amName +" Cell no. " +amMobNo;
				SendSMS(smsmsg);


				break;

			case COUPON_NO_CORRECT_REDEEMEDBY_SAME:
				couponmessage = "Redemption for coupon no. "+ couponCode+" failed Reason � Coupon already redeemed for this mobile no. "+MechanicMobNo + "Approval ID "+approvalId;

				smsmsg = "Redemption for coupon "+ couponCode + " failed . Reason Coupon already redeemed. Approval ID. "+approvalId;
				SendSMS(smsmsg);

				break;

			case WRONG_COUPON_ATTEMPT1:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Please check and retry";

				break;

			case WRONG_COUPON_ATTEMPT2:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Please check and retry";

				break;

			case WRONG_COUPON_ATTEMPT3:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Next wrong attempt will lead to your account being locked.";

				break;

			case WRONG_COUPON_ATTEMPT4:
				couponmessage = "Your account has been locked due multiple wrong attempts.Please contact Helpdesk - 02533298603";

				break;

			case COUPON_NOTACTIVATED:
				couponmessage = "Redemption for coupon no. "+couponCode+ " failed for mobile number "+ MechanicMobNo+" Reason � Coupon number not activated.Please investigate.";

				break;

			case MOBILENO_NOT_EXIST:
				couponmessage = "Mechanic not registered.Please process the registration for mobile no "+MechanicMobNo;

				break;

			default:
				break;
			}
			break;
			// Dealer Redeeming mechanic coupon.

		case DEALER_FOR_MECHANIC:

			switch (errorType) {
			case COUPON_NO_CORRECT_REDEEMEDBY_OTHER:
				couponmessage = "Redemption for coupon no. "+ couponCode+"failed for mobile number "+ MechanicMobNo+" Reason �  Coupon already redeemed. Please contact Area Manager.";

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+MechanicMobNo+
						"<br>"+
						" Reason �  Coupon already redeemed by "+ 
						"<br>"+
						" Name - "+ Coupon.this.name+
						"<br>"+
						" City - "+Coupon.this.City+ 
						"<br>"+
						" Location - "+ Coupon.this.Area+ 
						"<br>"+
						" Please investigate.";
				SendEmaiToAM(emailMesage, Constants.COUPON_MAIL_SUBJECT);

				smsmsg = "Redemption for coupon. "+ couponCode + " failed . Coupon already redeemed. Contact. " +amName +" Cell no. " +amMobNo;
				SendSMS(smsmsg);

				break;

			case COUPON_NO_CORRECT_REDEEMEDBY_SAME:
				couponmessage = "Redemption for coupon no. "+ couponCode+" failed Reason � Coupon already redeemed. Approval ID "+approvalId;

				smsmsg = "Redemption for coupon. "+ couponCode + " failed . Reason Coupon already redeemed. Approval ID. "+approvalId;
				SendSMS(smsmsg);

				break;

			case WRONG_COUPON_ATTEMPT1:
				couponmessage = "Redemption for coupon no. "+couponCode +" failed due to wrong coupon number.Please Retry";

				break;

			case WRONG_COUPON_ATTEMPT2:
				couponmessage ="Redemption for coupon no. "+couponCode +" failed due to wrong coupon number.Please Retry";

				break;

			case WRONG_COUPON_ATTEMPT3:
				couponmessage ="Redemption for coupon no. "+couponCode +" failed due to wrong coupon number.Please Retry";

				break;

			case WRONG_COUPON_ATTEMPT4:
				couponmessage = "Your account has been locked due to multiple wrong attempts.Please contact Area Manager";

				break;

			case COUPON_NOTACTIVATED:
				couponmessage = "Redemption for coupon no." +couponCode+" failed.Please contact Area Manager.";

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+ LoginMobilenumber+
						"<br>"+
						" Reason �  Coupon number not activated. "+	
						"<br>"+
						" City - "+ posi4.getText().toString()+
						"<br>"+
						" Location - " + posi3.getText().toString()+ 
						"<br>"+
						"<br>"+
						" Please investigate!";
				SendEmaiToAM(emailMesage, Constants.COUPON_MAIL_SUBJECT);

				break;

			case MOBILENO_NOT_EXIST:
				couponmessage = "Redemption failed.Reason � Mobile number "+ MechanicMobNo+" Not registered.Please contact Area Manager.";

				break;

			default:
				break;
			}
			break;
			// Mechanic redeeming mechanic Coupon.
		case MECHANIC_FOR_MECHANIC:
			switch (errorType) {
			case COUPON_NO_CORRECT_REDEEMEDBY_OTHER:
				couponmessage = "Redemption for coupon no. "+ couponCode+"failed.Reason �  Coupon already redeemed. "+AMmsg;

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+MechanicMobNo+
						"<br>"+
						" Reason �  Coupon already redeemed by "+
						"<br>"+
						" Name - "+ Coupon.this.name+
						"<br>"+
						" City - "+Coupon.this.City+
						"<br>"+
						" Location - "+ Coupon.this.Area+
						"<br>"+
						" Please investigate.";
				SendEmaiToAM(emailMesage, Constants.COUPON_MAIL_SUBJECT);

				break;

			case COUPON_NO_CORRECT_REDEEMEDBY_SAME:
				couponmessage = "Redemption for coupon no. "+ couponCode+" failed Reason � Coupon already redeemed. Approval ID "+approvalId;

				break;

			case WRONG_COUPON_ATTEMPT1:
				couponmessage ="Coupon no "+ couponCode+" does not exist.Please check and retry";

				break;

			case WRONG_COUPON_ATTEMPT2:
				couponmessage ="Coupon no "+ couponCode+" does not exist.Please check and retry";

				break;

			case WRONG_COUPON_ATTEMPT3:
				couponmessage = "Coupon no "+ couponCode+" does not exist.Next wrong attempt will lead to your account being locked.";

				break;

			case WRONG_COUPON_ATTEMPT4:
				couponmessage = "Your account has been locked due multiple wrong attempts. "+AMmsg;

				break;

			case COUPON_NOTACTIVATED:
				couponmessage = "Redemption for coupon no."+couponCode+" rejected. "+AMmsg;

				emailMesage = "Redemption for coupon no. "+ couponCode+" failed for mobile number "+ LoginMobilenumber+
						"<br>"+
						" Reason �  Coupon number not activated. "+	
						"<br>"+
						" City - "+ posi4.getText().toString()+
						"<br>"+
						" Location - " + posi3.getText().toString()+ 
						"<br>"+
						"<br>"+
						" Please investigate!";
				SendEmaiToAM(emailMesage, Constants.COUPON_MAIL_SUBJECT);


				break;

			case MOBILENO_NOT_EXIST:
				couponmessage = "Redemption rejected. "+AMmsg;

				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
		return couponmessage;
	}

	/**
	 *  Used to find whose coupon is redeemed by whom.
	 * @param couponType
	 * @param couponRedeemBy
	 * @return
	 */
	private int getCouponRedeemedBy(int couponType , String couponRedeemBy){

		if(couponType==0){

			if(couponRedeemBy.equalsIgnoreCase("14") ||couponRedeemBy.equalsIgnoreCase("15") ||couponRedeemBy.equalsIgnoreCase("17")
					||couponRedeemBy.equalsIgnoreCase("19")||couponRedeemBy.equalsIgnoreCase("20")){
				return DEALER_FOR_DEALER;
			}

		}else{

			if(couponRedeemBy.equalsIgnoreCase("14") ||couponRedeemBy.equalsIgnoreCase("15") ||couponRedeemBy.equalsIgnoreCase("17")
					||couponRedeemBy.equalsIgnoreCase("19")||couponRedeemBy.equalsIgnoreCase("20")){

				return DEALER_FOR_MECHANIC;
			}else if(couponRedeemBy.equalsIgnoreCase("1")
					||couponRedeemBy.equalsIgnoreCase("3")
					||couponRedeemBy.equalsIgnoreCase("4")
					||couponRedeemBy.equalsIgnoreCase("5")){
				return AM_FOR_MECHANIC;
			}else if(couponRedeemBy.equalsIgnoreCase("16")) {
				return MECHANIC_FOR_MECHANIC;
			}
		}

		return -1;
	}

	/**
	 * Get reporting Area Manager Details. 
	 */
	private void getAreaManagerDetail(){

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Loading ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {

				if(response!=null){

					HashMap<String, String> amInfo = FetchingdataParser.getAreaManagerInfo(response.toString());
					if(amInfo!=null){
						SharedPreferences amPref = Util.getAreaMangerSharedPreferences(Coupon.this);
						Editor editor = amPref.edit();
						editor.putString(Constants.AM_ID, amInfo.get(Constants.AM_ID));
						editor.putString(Constants.AM_NAME, amInfo.get(Constants.AM_NAME));
						editor.putString(Constants.AM_MobNo, amInfo.get(Constants.AM_MobNo));
						editor.putString(Constants.AM_EMAIL, amInfo.get(Constants.AM_EMAIL));
						editor.putString(Constants.AM_CITY, amInfo.get(Constants.AM_CITY));
						editor.putString(Constants.area, amInfo.get(Constants.area));
						editor.commit();
					}else{
						Toast.makeText(Coupon.this,getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
					}


				}else if(response==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();

				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		try {
			SharedPreferences passworddetails=getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
			request.put("CustomerID", passworddetails.getString(Constants.USERID,""));
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetCustomerAreaManagerDetails";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private int CheckInvalidCouponRedemtionAttempt(String couponCode) {
		int count=-1;

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetInvalidCouponAttemptsAndLockAccount";
		String soapcomponent=getResources().getString(R.string.soapcomponent);

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {

			// If User is internal then IsInternal=1 else 0.
			int isInternal = 0;
			String usertype = passworddetails.getString(Constants.USERTYPE, "");
			if(usertype.equalsIgnoreCase("internal")){
				isInternal = 1;
			}
			request.put("IsInternal", isInternal);
			request.put("CouponCode", couponCode);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		}
		catch (Exception e){
			e.printStackTrace();
		}
		SoapObject Paramater = new SoapObject(nameSpace, methodName);
		Iterator iterator = request.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			Paramater.addProperty(entry.getKey().toString(), entry.getValue());
			Log.e(entry.getKey().toString(), entry.getValue().toString());
		}
		Object result = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		new MarshalBase64().register(envelope);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(Paramater);
		envelope.implicitTypes= false;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(ipAddress + webService);
		System.setProperty("http.keepAlive", "true");
		try {
			androidHttpTransport.call(nameSpace+soapcomponent+methodName, envelope);
			result = envelope.getResponse();
			if (result != null) {
				Log.d("","Invalid Coupon Count for "+ couponCode+" = "+ result.toString());
				count = Integer.parseInt(result.toString());
			}else{
				System.out.println("GetDataFromNetwork.doInBackground()"+result);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}


	/**
	 *  Get message for successful coupon redemption .
	 * @param couponType
	 * @param couponRedeemBy
	 * @param approvalID
	 * @param couponCodeList
	 * @return
	 */
	private String getCouponRedeemedSuccessfullyMsg(int couponType  , String couponRedeemBy, String approvalID, ArrayList<CouponCode> couponCodeList){

		StringBuilder msg = new StringBuilder();
		msg.append("Congrats! \n");
		int couponRedemtionBy = getCouponRedeemedBy(couponType, couponRedeemBy);
		int total = 0;

		switch (couponRedemtionBy) {
		case DEALER_FOR_DEALER:


			for(CouponCode code :couponCodeList){
				if(code.isIsredeemedSucessful()){
					msg.append(code.getCode());
					msg.append(",");
					msg.append(" Rs ");
					msg.append(code.getEarnedpoint());
					msg.append("(I)+");
					msg.append(code.getBonus());
					msg.append("(B )");
					total = total +code.getEarnedpoint()+code.getBonus();
					msg.append(",");

				}
			}
			msg.append("\n");
			msg.append("Total- ");
			msg.append("Rs ");
			msg.append(total);
			msg.append("\n");
			msg.append("ApprovalId: ");
			msg.append(approvalID);
			msg.append(".");

			break;

		case AM_FOR_MECHANIC:


			for(CouponCode code :couponCodeList){
				if(code.isIsredeemedSucessful()){
					msg.append(code.getCode());
					msg.append(",");
					msg.append(" Rs ");
					msg.append(code.getEarnedpoint());
					msg.append("(I)+");
					msg.append(code.getBonus());
					msg.append("(B )");
					total = total +code.getEarnedpoint()+code.getBonus();
					msg.append(",");

				}
			}
			msg.append("\n");
			msg.append("Total- ");
			msg.append("Rs ");
			msg.append(total);
			msg.append("\n");
			msg.append("ApprovalId: ");
			msg.append(approvalID);
			msg.append(".");
			break;
		case DEALER_FOR_MECHANIC:


			for(CouponCode code :couponCodeList){

				if(code.isIsredeemedSucessful()){
					msg.append(code.getCode());
					msg.append(",");
					msg.append(" Rs ");
					msg.append(code.getEarnedpoint());
					msg.append("(I)+ ");
					msg.append(code.getBonus());
					msg.append("(B)+ ");
					msg.append(code.getEffort());
					msg.append("(E)");
					total = total +code.getEarnedpoint()+code.getBonus()+code.getEffort();
					msg.append(",");

				}
			}
			msg.append("\n");
			msg.append("Total- ");
			msg.append("Rs ");
			msg.append(total);
			msg.append("\n");
			msg.append("ApprovalId: ");
			msg.append(approvalID);
			msg.append(".\n");
			if(!isMechanicAccountExist()){
				msg.append("Please link bank a/c or ATM card for transfer of points.");
			}
			break;

		case MECHANIC_FOR_MECHANIC:

			for(CouponCode code :couponCodeList){

				if(code.isIsredeemedSucessful()){
					msg.append(code.getCode());
					msg.append(",");
					msg.append(" Rs ");
					msg.append(code.getEarnedpoint());
					msg.append("(I)+ ");
					msg.append(code.getBonus());
					msg.append("(B)+ ");
					msg.append(code.getEffort());
					msg.append("(E)");
					total = total +code.getEarnedpoint()+code.getBonus()+code.getEffort();
					msg.append(",");

				}
			}
			msg.append("\n");
			msg.append("Total- ");
			msg.append("Rs ");
			msg.append(total);
			msg.append("\n");
			msg.append("ApprovalId: ");
			msg.append(approvalID);
			msg.append(".\n");
			if(!isMechanicAccountExist()){
				msg.append("Please link bank a/c or ATM card for transfer of points.");
			}
			break;

		default:
			break;
		}
		msg.append(" Redeemed Successfully!");
		Log.i("T","Coupon Redeemed Successfull Msg"+ msg.toString());
		return msg.toString();
	}

	private void GetApprovalId(){


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Loading ..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					Log.d("res>>",""+response.toString());
					setmApprovalId(response.toString());
					counter=0;
					couponmessage = new StringBuilder();
					if(isNetworkAvailable()){

						for(int i=0;i<couponAdapter.getCount();i++){ 
							CouponCode code = couponAdapter.getItem(i);
							ValidateCoupon(code.getCode(),i);
							if(Mechanicid==null){
								if(i==0)
								addUnregisteredMechanicPending(mobilenumber.getText().toString().trim(), code.getCode());
							}
						}
					}else{
						Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
					}

				}else if(response==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetApprovalID";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		Log.d("req>>",""+request);
		dataFromNetwork.execute();
	}

	private String getSMSText(int couponType  , String couponRedeemBy, String approvalID, ArrayList<CouponCode> couponCodeList){

		int couponRedemtionBy = getCouponRedeemedBy(couponType, couponRedeemBy);
		StringBuilder smsText = new StringBuilder();
		smsText.append("Congrats! ");
		int basic=0, bonus = 0,total = 0;
		switch (couponRedemtionBy) {
		case AM_FOR_MECHANIC:

			for(CouponCode code :couponCodeList){

				if(code.isIsredeemedSucessful()){
					smsText.append(code.getCode());
					smsText.append(",");
					smsText.append(" Rs ");
					smsText.append(code.getEarnedpoint());
					smsText.append("(I)%12B ");
					smsText.append(code.getBonus());
					smsText.append("(B)");
					total = total +code.getEarnedpoint()+code.getBonus();
					//smsText.append("\n");

				}
			}
			smsText.append("Total-");
			smsText.append("Rs ");
			smsText.append(total);
			//smsText.append("\n");
			smsText.append("Approval id-");
			smsText.append(approvalID);
			//smsText.append("\n");
			smsText.append("Collect Cash-Redeem directly %28 Earn more ");

			break;
		case DEALER_FOR_MECHANIC:

			for(CouponCode code :couponCodeList){

				if(code.isIsredeemedSucessful()){
					smsText.append(code.getCode());
					smsText.append(",");
					smsText.append(" Rs ");
					smsText.append(code.getEarnedpoint());
					smsText.append("(I)%12B ");
					smsText.append(code.getBonus());
					smsText.append("(B)");
					total = total +code.getEarnedpoint()+code.getBonus();

				}
			}
			//smsText.append("\n");
			smsText.append("Total-");
			smsText.append("Rs");
			smsText.append(total);
			//smsText.append("\n");
			smsText.append("Approval id-");
			smsText.append(approvalID);
			//smsText.append("\n");
			smsText.append("Collect Cash-Redeem directly %28 Earn more ");
			break;

		default:
			break;
		}

		Log.i("SMS Text", smsText.toString());
		return smsText.toString();
	}

	private void SendSMS(String messageBody) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Loading ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){



				}else if(response==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4)+"errrfrom msg" ,Toast.LENGTH_SHORT).show();

				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		/*SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);*/

		try {
			request.put("MessageBody",messageBody);
			request.put("PhoneNumberTo",mobilenumber.getText().toString());
			/*request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));*/
		}

		catch (Exception e){
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SendSMS";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


	/**
	 *  Email Coupon error to Reporting AM.
	 * @param messageBody
	 */
	private void SendEmaiToAM(String messageBody, String subject) {

		SharedPreferences AMPref = Util.getAreaMangerSharedPreferences(getCurrentContext());
		String amemail = AMPref.getString(Constants.AM_EMAIL, "");

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Sending mail ...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){



				}else if(response==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4)+"errrfrom msg" , Toast.LENGTH_SHORT).show();

				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);

		try {
			request.put("EmailAdd",amemail);
			request.put("Subject",subject);
			request.put("Message",messageBody);
			request.put("IsBodyHtml",1);
			/*request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));*/
		}

		catch (Exception e){
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SendMail";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	private void isCouponValidBeforeAdd( final String couponText){


		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "validating coupon", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				Object couponcheckresponse = result;
				String couponTypeId = null,couponCode = null;

				if(result==null){
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
					return ;
				}
				SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
				String couponRedeemerID = passworddetails.getString(Constants.CONTACTTYPEID,"");

				StringBuilder couponmessage = new StringBuilder();
				ArrayList<HashMap<String, Object>> Couponlist=new FetchingdataParser().couponvalidation(couponcheckresponse.toString());


				if(Couponlist.size()!=0){

					for (HashMap<String, Object> entry : Couponlist)
					{
						couponCode = (String)entry.get(Constants.CouponCode);
						couponTypeId = (String) entry.get(Constants.CouponTypeID);
					}
					if(isDealerUIShown && couponTypeId.equals("2")){


						String redemptionMsg = "This is not a Dealer coupon.Please check!";
						couponmessage.append(redemptionMsg+" \n");
						showRedeemedCouponStatusDialog(couponmessage);
						return;

					}else if(!isDealerUIShown && couponTypeId.equals("1")){
						String redemptionMsg = "This is not a Mechanic coupon.Please check!";
						couponmessage.append(redemptionMsg+" \n");
						showRedeemedCouponStatusDialog(couponmessage);
						return;
					}else{
						CouponCode ccode = new CouponCode();
						ccode.setCode(couponText);
						PopulateCouponCodeList(ccode);
						couponredeemedText.setText("");
					}
				}else{
					CouponCode ccode = new CouponCode();
					ccode.setCode(couponText);
					PopulateCouponCodeList(ccode);
					couponredeemedText.setText("");
				}
			}
		});

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CheckCouponStatus";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.CouponCode, couponText);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		}
		catch (Exception e){
			e.printStackTrace();
		}
		datafromnetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
		datafromnetwork.sendData(request);
		datafromnetwork.execute();

	}


	/**
	 * 
	 * @param mobileno : Mobile no of mechanic
	 * @param couponCode: coupon code
	 */
	private void addUnregisteredMechanicPending (String mobileno, String couponCode){


		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "updating...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				
			}
		});

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="AddUnregisteredCustomerInPernding";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.MOBILE_NUMBER, mobileno);
			request.put(Constants.CouponCode, couponCode);
			request.put("DoQuarantine", false);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		}
		catch (Exception e){
			e.printStackTrace();
		}
		datafromnetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
		datafromnetwork.sendData(request);
		datafromnetwork.execute();
	}

	
	private void ValidateMechanicMobileNoForAM() {

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(Coupon.this, ProgressDialog.STYLE_SPINNER,"Searching Mechanics ..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					Couponlist.clear();
					Couponlist=new FetchingdataParser().isMechanicExist(response.toString());
					if(Couponlist.size()==0){

						couponlayout.setVisibility(View.GONE);
						tableLayout1.setVisibility(View.GONE);
						cashcollected.setVisibility(View.GONE);
						AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext(),R.style.MyAlertDialogStyle);
						alert.setCancelable(false);
						alert.setTitle(getStringFromResource(R.string.phonenonotregister));
						alert.setMessage(getStringFromResource(R.string.message7));
						alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								dialog.dismiss();
								couponlayout.setVisibility(View.VISIBLE);
								cashcollected.setVisibility(View.VISIBLE);
								cashcollected.setChecked(true);
								cashcollected.setEnabled(false);
								/*if(contactTypeID.equalsIgnoreCase("1")){
									Intent  intent  = new Intent(getCurrentContext(),AddRetailerContact.class);
									intent.putExtra(COUPON_EXTRA, "add");
									startActivity(intent);
								}else{
									couponlayout.setVisibility(View.VISIBLE);
								}*/
								
							}
						});
						alert.setNegativeButton(android.R.string.no, new  DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();

							}
						});
						alert.show();
					}else {
						couponlayout.setVisibility(View.VISIBLE);
						tableLayout1.setVisibility(View.VISIBLE);
						String name = null ,area = null,address = null,city = null,state = null,accountNo = null;

						for (HashMap<String, Object> entry : Couponlist)
						{
							Mechanicid= (String) entry.get(Constants.ID);
							name = (String) entry.get(Constants.CONTACTNAME_1);
							area = (String) entry.get(Constants.area);
							address = (String) entry.get(Constants.streetaddress);
							city = (String) entry.get(Constants.city);
							state = (String) entry.get(Constants.state);
							accountNo = (String)entry.get(Constants.AccountNumber);

						}
						if(name!=null)
							posi1.setText(name);
						if(address!=null)
							posi2.setText(address);
						if(area!=null)
							posi3.setText(area);
						if(city!=null)
							posi4.setText(city);
						if(state!=null)
							posi5.setText(state);
						if(accountNo.length()>1){
							setMechanicAccountExist(true);
						}else{
							setMechanicAccountExist(false);
							AlertDialog.Builder dialog = new AlertDialog.Builder(Coupon.this,R.style.MyAlertDialogStyle);
							dialog.setMessage(getStringFromResource(R.string.mechaniregisterbutaccountnotavaible));
							dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									cashcollected.setChecked(true);
									dialog.dismiss();

								}
							});
							dialog.create().show();
						}

						// If account info for mechanic is not available he is not able 
						// redeem coupon.
						if(contactTypeID.equals("16") && !isMechanicAccountExist()){
							AlertDialog.Builder dialog = new AlertDialog.Builder(Coupon.this);
							dialog.setMessage(getStringFromResource(R.string.mechanicaccount));
							dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									finish();
								}
							});
							dialog.create().show();
						}
					}

				}else if(response==null){
					couponlayout.setVisibility(View.GONE);
					tableLayout1.setVisibility(View.GONE);
					Toast.makeText(Coupon.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			request.put(Constants.MOBILE_NUMBER, mobilenumber.getText().toString());
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="IsMechanicExistForAM";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}


}

