package com.arcis.vgil.trackapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork;
import com.arcis.vgil.trackapp.data.Constants;
import com.arcis.vgil.trackapp.model.Singleton;

import org.ksoap2.serialization.SoapObject;

import java.util.LinkedHashMap;

public class AMQuestionarePartNew extends BaseActivity {
	private Button  submit;
	private EditText shortNote;
	String visitLog_ID = null;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.amquestionpartnew);
		Intent intent = getIntent();
		visitLog_ID = intent.getStringExtra("VISITOG");
		
		shortNote=(EditText) findViewById(R.id.shortNote);
		submit=(Button) findViewById(R.id.submit);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (shortNote.getText().length()!=0) {
					
				//	Function SaveAllVisitLogDetailsWithoutQuestions(ByVal VisitLogID As String,
					//ByVal MeetingNotes As String, ByVal UserID As String, ByVal Password As String) As String
					
				SaveAllVisitLogDetailsWithoutQuestions();

					
				}else {
					Toast.makeText(getCurrentContext(), "Feedback Notes should not empaty", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	    
		
	}
	protected void SaveAllVisitLogDetailsWithoutQuestions() {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
				getCurrentContext(), ProgressDialog.STYLE_SPINNER,
				"Uploading data...", new GetDataCallBack() {
					@Override
					public void processResponse(Object result) {

						SoapObject responce = null;
						try {

							responce = (SoapObject) result;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						AlertDialog.Builder errordialog = new AlertDialog.Builder(
								getCurrentContext());
						if (responce == null) {
							errordialog
									.setTitle(getStringFromResource(R.string.error6));
							if (result != null) {
								errordialog.setMessage(result.toString());
							} else {
								errordialog
										.setMessage(getStringFromResource(R.string.error4));
							}

						} else {
							errordialog
									.setMessage(getStringFromResource(R.string.message5));

						}
						errordialog.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if (visitLog_ID == null
												|| "".equals(visitLog_ID)) {
											/*Intent intent = new Intent(
													AMQuestionarePartNew.this,
													DailyCallsActivity.class);
											startActivity(intent);*/
										} else {
											Intent intent = new Intent(
													AMQuestionarePartNew.this,
													PendingCalls.class);
											   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        					   startActivity(intent);
				        					   finish();
										}
									}
								});
						AlertDialog dialog = errordialog.create();
						dialog.show();
					}
				});

		LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails = getSharedPreferences("PASSWORD",
				MODE_PRIVATE);

		try {
			
			
			if (visitLog_ID == null || "".equals(visitLog_ID)) {
				request.put(Constants.visitLogID, Singleton.VisitLogID);
			} else {
				request.put(Constants.visitLogID, visitLog_ID);
			}
			
			if (shortNote.getText().toString().length()>0) {
				request.put("MeetingNotes", shortNote.getText().toString());
			}
			else {
				request.put("MeetingNotes", "");
			}
			request.put(Constants.username,
					passworddetails.getString(Constants.ID, ""));
			request.put(Constants.password,
					passworddetails.getString("password", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress = getResources().getString(R.string.ipaddress);
		String webService = getResources().getString(R.string.webService);
		String nameSpace = getResources().getString(R.string.nameSpace);
		String methodName = "SaveAllVisitLogDetailsWithoutQuestions";
		String soapcomponent = getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName,
				soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();

	
		
	}

}
