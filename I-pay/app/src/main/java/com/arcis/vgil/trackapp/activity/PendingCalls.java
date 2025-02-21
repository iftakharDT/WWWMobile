package com.arcis.vgil.trackapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.connectivity.GetDataFromNetwork;
import com.arcis.vgil.trackapp.data.Constants;
import com.arcis.vgil.trackapp.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class PendingCalls extends BaseActivity  {
	private ListView mListView;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.pending_calls);
		setCurrentContext(this);

		//getting the toolbar
		/*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		//setting the title
		mTitle.setText("Pending Calls");
		toolbar.setTitle("");

		setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


		mListView = (ListView)findViewById(R.id.pendinglV);
		mListView.setOnTouchListener(listTouchListener);
		mListView.setOnItemClickListener(this);
		
		GetAMTerettoryCustomers();
	}
	private void GetAMTerettoryCustomers() {
		// TODO Auto-generated method stub
		

		 GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
		 	public void processResponse(Object response) {
				ArrayList<HashMap<String, String>> amTerretoryList ;
				if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
				}else{
					if(response!=null){
						amTerretoryList = new FetchingdataParser().getAmPendingCallList(response.toString());
						if(amTerretoryList.size()==0){
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4) ,Toast.LENGTH_SHORT).show();
						}else {
							
							SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), amTerretoryList, R.layout.questionnare_customer_cell,
							new String[]{Constants.customerName,Constants.contactType,Constants.endDateTime,Constants.startDateTime,Constants.purposeOfVisit,Constants.meetingNotes,Constants.visitLogIDPending},
							new int[]{R.id.custumer_name,R.id.contact_type,R.id.end_of_date,R.id.start_of_date,R.id.purpose_of_meeting,R.id.meeting_note,R.id.visit_log});
							mListView.setAdapter(adapter);
							
						}
					}
				}
			}});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		
		/*Function GetAMVisitLog(ByVal AMID As String, ByVal UserID As String, ByVal Password As String) As String

		 */

		
		try {
			//Constants.AM_ID, passworddetails.getString(Constants.USERID,"") 
		    request.put(Constants.AM_ID, passworddetails.getString(Constants.USERID,""));
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAMVisitLog";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();*/


		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAMVisitLog";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	
		 TextView amVisitLog= (TextView) arg1.findViewById(R.id.visit_log);
		 TextView contactTpe= (TextView) arg1.findViewById(R.id.contact_type);
		 String stringVisitlog=String.valueOf(amVisitLog.getText().toString());
		 String stringContactTpe=String.valueOf(contactTpe.getText().toString());
		 Intent intent  = new Intent(PendingCalls.this,AMQuestionarePartNew.class);
		 intent.putExtra("CONTACT_TYPE", stringContactTpe);
		 intent.putExtra("VISITOG", stringVisitlog);
		 startActivity(intent);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	 @Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
	    	super.onBackPressed();
	    	finish();
	    }
}
