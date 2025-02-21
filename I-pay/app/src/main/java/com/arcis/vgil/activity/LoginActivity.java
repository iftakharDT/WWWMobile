
package com.arcis.vgil.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.arcis.vgil.R;
import com.arcis.vgil.R.string;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.NotificationUtils;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LoginActivity extends BaseActivity{
	// For svn testing
	Button login,btn_register;
	EditText username,password;
	CheckBox remember;
	public String USERIDAMSTOCK;
	SharedPreferences passworddetails;
	HashMap<String,Object> loginMap=new HashMap<String,Object>();
	private BroadcastReceiver mRegistrationBroadcastReceiver;
	String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
			Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION
	};
	/*
        Permission Activity
        */
	private static final int PERMISSION_CALLBACK_CONSTANT = 100;
	private static final int REQUEST_PERMISSION_SETTING = 101;

	private SharedPreferences permissionStatus;
	private boolean sentToSettings = false;

	@Override
	public void inti() {
		super.inti();
		setContentView(R.layout.activity_main);
		setCurrentContext(this);
		permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
		
		TextView et_version_Name=(TextView) findViewById(R.id.textView2);
		try {
			int versionName = getCurrentContext().getPackageManager().getPackageInfo(getCurrentContext().getPackageName(), 0).versionCode;
			et_version_Name.setText("Version :"+ String.valueOf(versionName));
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getVersion();

		login=(Button) findViewById(R.id.login);
		login.setOnClickListener(this);
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		remember=(CheckBox) findViewById(R.id.remember);
		btn_register = (Button)findViewById(R.id.btn_register);
		btn_register.setOnClickListener(this);
		
		passworddetails=getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
		checkForGCMRegisteration();

		
		remember.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					try{
						passworddetails.edit().putString("remember", "yes").commit();
						passworddetails.edit().putString("rememberusername", username.getText().toString()).commit();
						passworddetails.edit().putString("rememberpassword",password.getText().toString()).commit();

					}
					catch(Exception e){

					}
				}else{
					passworddetails.edit().putString("remember", "no").commit();
					passworddetails.edit().putString("rememberusername","").commit();
					passworddetails.edit().putString("rememberpassword","").commit();
				}

			}
		});
		
		if(passworddetails.getString("remember","").equalsIgnoreCase("yes")){
			username.setText(passworddetails.getString("rememberusername", ""));
			password.setText(passworddetails.getString("rememberpassword", ""));
			remember.setChecked(true);
			username.setSelection(username.getText().length());
		}

	}
	@Override
	public boolean validation() {
		boolean flag = true;
		if (!notNullCheck(R.id.username))
			flag = false;

		if (!notNullCheck(R.id.password))
			flag = false;

		return flag;
	}
	@Override
	public void getScreenData() {
		super.getScreenData();
		getRequestDataMap().put(Constants.username,username.getText().toString());
		getRequestDataMap().put(Constants.password,password.getText().toString());

	}
	@Override
	public void startNextScreen() {
		getlogindetails();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			if (validation()) {

				checkRunTimePermissions();

				/*getScreenData();
				startNextScreen();*/
			}
			break;
		case R.id.btn_register:
			Intent intent = new Intent(LoginActivity.this, AddRetailerContact.class);
			intent.putExtra("action","add");
			startActivity(intent);
			break;
		default:
			break;
		}	
	}

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
	private void getlogindetails() {
	 	GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(LoginActivity.this, ProgressDialog.STYLE_SPINNER,"Login Please Wait ..", new GetDataCallBack() {
			@Override
		 	public void processResponse(Object response) {

				if(response!=null){
					loginMap.clear();
					loginMap=new FetchingdataParser().isUserExist(response.toString());
					AlertDialog.Builder alert = new AlertDialog.Builder(getCurrentContext());
					alert.setCancelable(false);
					alert.setTitle(getStringFromResource(string.loginerroe));
					
					Object isLocked = loginMap.get(Constants.isLocked);
					String passwordStatus = (String)loginMap.get(Constants.PasswordStatus); // 0 for new user 1 for password already changed.
					// 
					if(loginMap.isEmpty()){
						
						alert.setMessage(getStringFromResource(string.usernotregister));
						
						alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						alert.show();

					}else if(isLocked!=null && ((String) isLocked).equalsIgnoreCase("true") || ((String) isLocked).equalsIgnoreCase("1")){
						
							alert.setMessage(getStringFromResource(string.accountlocked));
							alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						alert.show();
					}else if(passwordStatus.equalsIgnoreCase("false")){
						
						alert.setMessage(getStringFromResource(string.changepassowrd));
						alert.setPositiveButton("Change password", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							Intent intent =  new Intent(LoginActivity.this,ChangePasswordActivity.class);
							String userName= String.valueOf(username.getText().toString());
							intent.putExtra("username", userName);
							startActivity(intent);
							dialog.dismiss();
						}
					});
						alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								finish();
							}
						});
					alert.show();
						
					}else  {
						//if(isPhoneNumberRegistered(loginMap.get(Constants.mobilenumber).toString()))
						try {

							SharedPreferences passworddetails=Util.getSharedPreferences(LoginActivity.this, Constants.PREF_NAME);
							passworddetails.edit().putString(Constants.ID, loginMap.get(Constants.ID).toString()).commit();
							passworddetails.edit().putString(Constants.USERNAME, loginMap.get(Constants.USERID_1).toString()).commit();
							passworddetails.edit().putString(Constants.PASSWORD, BaseActivity.Encrypt(password.getText().toString(), "VGIL123456789")).commit();
							passworddetails.edit().putString(Constants.CREATEDBY, loginMap.get(Constants.USERID_1).toString()).commit();
							passworddetails.edit().putString(Constants.CONTACTTYPEID, loginMap.get(Constants.CONTACTTYPEID_1).toString()).commit();
							passworddetails.edit().putString(Constants.CONTACTNAME, loginMap.get(Constants.CONTACTNAME_1).toString()).commit();
							passworddetails.edit().putString(Constants.USERTYPE, loginMap.get(Constants.TYPE).toString()).commit();
							passworddetails.edit().putString(Constants.CONTACTTYPENAME, loginMap.get(Constants.CONTACTTYPENAME_1).toString()).commit();
						    passworddetails.edit().putString(Constants.MOBILE_NUMBER, loginMap.get(Constants.MOBILE_NUMBER).toString()).commit();
							passworddetails.edit().putString(Constants.USERID, loginMap.get(Constants.USERID_1).toString()).commit();
							passworddetails.edit().putString(Constants.DEALERID, loginMap.get(Constants.DEALERID_1).toString()).commit();
							
							if(remember.isChecked()){
								passworddetails.edit().putString("rememberusername", loginMap.get(Constants.USERCODE).toString()).commit();
								passworddetails.edit().putString("rememberpassword", password.getText().toString()).commit();
							}
							
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						// Retailer 15,17, 19,20
						// DDealer 14
						// Mechanic 16
						// AM 1;
						
						Intent intent  = null;
						if(loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("1")){
							// Show AM User Summary 
							//intent = new Intent(LoginActivity.this, MarketingTeamDasboard.class);
							intent = new Intent(LoginActivity.this, Summary.class);
							//intent = new Intent(LoginActivity.this, UserSummaryAM.class);
						}else if(loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("14")){
							
							// Show Dealer User Summary 
	                 	intent = new Intent(LoginActivity.this, UserSummaryDirectDealer.class);
						}else if(loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("15")
								||loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("17")
								||loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("19")
								||loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("20")){
							
							// Show Retailer User Summary 
							intent = new Intent(LoginActivity.this, UserSummaryRetailer.class);
							//Util.showToast(LoginActivity.this, "You are not athorized to use this application", Toast.LENGTH_SHORT).show();
							
						}else if(loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("16")){
							
							// Show Mechanic User Summary 
							intent = new Intent(LoginActivity.this, UserSummaryMechanicActivity.class);
							//Util.showToast(LoginActivity.this, "You are not athorized to use this application", Toast.LENGTH_SHORT).show();
							
						}
						
                       else if(loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("4")||loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("27")||loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("28")||loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("3")){

							// Show RM User Summary 
							intent = new Intent(LoginActivity.this, Summary.class);
							//intent = new Intent(LoginActivity.this, UserSummaryRM.class);
						}
                       else if(loginMap.get(Constants.CONTACTTYPEID_1).toString().equals("5")){
							
							// Show MM User Summary 
							intent = new Intent(LoginActivity.this, Summary.class);
						}
						if(intent!=null){
							startActivity(intent);
							finish();
						}else{
							Util.showToast(LoginActivity.this, "You are not athorized to use this application", Toast.LENGTH_SHORT).show();
						
						}
						
					}/*else{
						alert.setMessage(getStringFromResource(R.string.phonenonotregister));
						alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						alert.show();
					}*/

				}else if(response==null){
					Toast.makeText(LoginActivity.this,getResources().getString(string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		try {
			request.put(Constants.username,username.getText().toString());
			request.put(Constants.password, BaseActivity.Encrypt(password.getText().toString(), "VGIL123456789"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*TelephonyManager teleManager  = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNO = teleManager.getLine1Number();
		if(phoneNO!=null && phoneNO.length()>0){
			request.put(Constants.phoneNo, phoneNO);
		}else{
			phoneNO = teleManager.getSimSerialNumber();
			request.put(Constants.phoneNo, phoneNO);
		}*/
		String ipAddress=getResources().getString(string.ipaddress);
		String webService =getResources().getString(string.webService);
		String nameSpace=getResources().getString(string.nameSpace);
		String methodName="Login";
		String soapcomponent=getResources().getString(string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	private String getPhoneNumber(String number){
		
		if(number.length()>=10){
			if(number.contains("+91")){
				number.replace("+91", "");
				
			}
		}
		
		return number;
	}
	
	/*private boolean isPhoneNumberRegistered(String phoneNumber){
		
		TelephonyManager teleManager  = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String devicephoneNO = teleManager.getLine1Number();
		
		if(devicephoneNO!=null){
			String replaceddevicePno = getPhoneNumber(devicephoneNO);
			String replaceduserPNo   = getPhoneNumber(phoneNumber);
			if(replaceduserPNo.equals(replaceddevicePno)){
				return true;
			}
		}
		
		return false;
		
	}*/
	
	private void getVersion(){
		
		
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				if(result!=null){

						String applicationVersion = getStringFromResource(string.application_version);
						if(!applicationVersion.equals(result.toString())){
							
							AlertDialog.Builder dialog = new AlertDialog.Builder(getCurrentContext());
							dialog.setTitle("Application Version");
							dialog.setMessage("Application version changed,Please download latest version from : https://play.google.com/store/apps/details?id=com.arcis.vgil");
							dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									finish();
								}
							});
							dialog.create().show();
						}

				}else{
					Util.showToast(getCurrentContext(), getStringFromResource(string.error4), Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String, Object > requestMap = new LinkedHashMap<String, Object>();
		
		String ipAddress=getResources().getString(string.ipaddress);
		String webService =getResources().getString(string.webService);
		String nameSpace=getResources().getString(string.nameSpace);
		String methodName="Version";
		String soapcomponent=getResources().getString(string.soapcomponent);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}

	private void checkForGCMRegisteration() {



		// register if saved registration id not found
		if (passworddetails.getString(Constants.GCMID,"").isEmpty()||passworddetails.getString(Constants.GCMID,"")==null) {
		//if (passworddetails==null){
				registerInBackground();
		}



	}

	private void registerInBackground() {

		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				// checking for type intent filter
				if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);

				//	displayFirebaseRegId();

				} else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
					// new push notification is received

					String message = intent.getStringExtra("message");

					Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
					Log.d("@@@@@@@@@@@@@@@@@@",message);

				}
			}
		};

		//displayFirebaseRegId();
	}

	// Fetches reg id from shared preferences
	// and displays on the screen
	private void displayFirebaseRegId() {

		String regId = passworddetails.getString(Constants.GCMID,"");

		Log.e("Spicer", "Firebase reg id: " + regId);

		if (!TextUtils.isEmpty(regId))  {
			Log.d("@@@@ not resister @@",regId);
		}
		else{
			// Toast.makeText(getApplicationContext(),"Firebase Reg Id is not received yet!", Toast.LENGTH_LONG).show();

		}


	}

	@Override
	protected void onResume() {
		super.onResume();

		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Constants.REGISTRATION_COMPLETE));

		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Constants.PUSH_NOTIFICATION));

		// clear the notification area when the app is opened
		NotificationUtils.clearNotifications(getApplicationContext());

	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
		super.onPause();
	}



	private void checkRunTimePermissions() {


		if(ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
		         ){
			if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[0])
					|| ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[1])
					|| ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[2])
					|| ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[3])
					){
				//Show Information about why you need the permission
				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("Need Multiple Permissions");
				builder.setMessage("This app needs Camera,Storage  permissions.");
				builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
			} else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
				//Previously Permission Request was cancelled with 'Dont Ask Again',
				// Redirect to Settings after showing Information about why you need the permission
				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("Need Multiple Permissions");
				builder.setMessage("This app needs Camera ,Storage  permissions.");
				builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						sentToSettings = true;
						Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
						Uri uri = Uri.fromParts("package", getPackageName(), null);
						intent.setData(uri);
						startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
						Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera ,Storage and Call", Toast.LENGTH_LONG).show();
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
			}
			else {
				//just request the permission
				ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
			}

			//txtPermissions.setText("Permissions Required");

			SharedPreferences.Editor editor = permissionStatus.edit();
			editor.putBoolean(permissionsRequired[0],true);
			editor.commit();
		} else {
			//You already have the permission, just go ahead.
			proceedAfterPermission();
		}
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (sentToSettings) {
			if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
				//Got Permission
				proceedAfterPermission();
			}
		}
	}


	private void proceedAfterPermission() {
		getScreenData();
		startNextScreen();
	}

}
