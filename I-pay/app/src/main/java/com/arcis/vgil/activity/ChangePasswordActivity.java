package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;

import java.util.LinkedHashMap;

public class ChangePasswordActivity extends BaseActivity {

	
	private EditText edttxt_username,edttxt_oldpawword,edttxt_newpassword;
	private Button btn_submit;
	Bundle bundle;
	String _strBundleUserName;
	SharedPreferences pref;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.change_password_activity);
		bundle=getIntent().getExtras();
		
		edttxt_username = (EditText)findViewById(R.id.username);
       if (bundle!=null) {
			
			_strBundleUserName=bundle.getString("username");
			edttxt_username.setText(_strBundleUserName);	
		}
		
		edttxt_oldpawword = (EditText)findViewById(R.id.passwordold);
		edttxt_newpassword = (EditText)findViewById(R.id.passwordnew);
		setCurrentContext(this);
		btn_submit = (Button)findViewById(R.id.submit);
		btn_submit.setOnClickListener(this);
		//pref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
		//edttxt_username.setText(pref.getString("rememberusername", ""));
		
	}
	
	@Override
	public boolean validation() {
		// TODO Auto-generated method stub
		boolean isvalid = true;
		
		
		
		if(edttxt_oldpawword.getText().length()==0){
			
			edttxt_oldpawword.setError(getStringFromResource(R.string.oldpassword));
			isvalid = false;
		}
		if(edttxt_newpassword.getText().length()==0){
			
			edttxt_newpassword.setError(getStringFromResource(R.string.newpassword));
			isvalid = false;
		}
		
		if(_strBundleUserName.equalsIgnoreCase(edttxt_newpassword.getText().toString())){
			
			Toast.makeText(getCurrentContext(), "New password should not match the old password!", Toast.LENGTH_LONG).show();
			isvalid = false;
		}
		
		
		
		return isvalid;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(validation()){
			updateNewPassword();
		}
		
	}
	
	
	private void updateNewPassword(){
		
		
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				if(result!=null){

							AlertDialog.Builder dialog = new AlertDialog.Builder(getCurrentContext());
							dialog.setMessage("Password changed successfully! Please login again.");
							dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
									Intent intent = new Intent(ChangePasswordActivity.this,LoginActivity.class);
									startActivity(intent);
									dialog.dismiss();
								}
							});
							dialog.create().show();

				}else{
					Util.showToast(getCurrentContext(), getStringFromResource(R.string.error4), Toast.LENGTH_SHORT).show();
				}
			}
		});

		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
		LinkedHashMap<String, Object > requestMap = new LinkedHashMap<String, Object>();
		requestMap.put("UserCode", edttxt_username.getText().toString());
		try {
			requestMap.put("NewPassword",  BaseActivity.Encrypt(edttxt_newpassword.getText().toString(), "VGIL123456789"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		requestMap.put(Constants.username, pref.getString(Constants.ID, ""));
		requestMap.put(Constants.password, pref.getString(Constants.PASSWORD, ""));
		
		
		
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="ChangePassword";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}
}
