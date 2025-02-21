/**
 * 
 */
package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Muni Mishra
 *
 */
public class CFormPendingActivity extends BaseActivity {

	ListView mlistview;
	public static final String EXTRA_CFORM_ID = "cformID";
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_cform_pending_layout);
		setCurrentContext(this);
		
		mlistview = (ListView)findViewById(android.R.id.list);
		Intent intent  = getIntent();
		if(intent!=null){
			String methodName = "";
			String contacttypeid =intent.getStringExtra(ViewAll_AM.EXTRA_USER_CONTACT_TYPE_ID);
			if(contacttypeid.equals(Constants.CONTACT_TYPE_ID_AREAMANAGER)){
				// Get C Form pending  for area Manager
				methodName = "GetCFormPendingAtAreaManager";
			}else if(contacttypeid.equals(Constants.CONTACT_TYPE_ID_CFA)){
				// Get C Form for CFA.
				methodName = "GetCFormPendingAtCFA";
			}
			getCformPendingList(methodName);
		}
	}
	
	
	private void getCformPendingList(String methodname){

		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading pending cforms...", new GetDataCallBack() {

			@Override
		 	 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<HashMap<String, String>> invoiceList = FetchingdataParser.getCformPendingList(result.toString());
					if(invoiceList==null){
						AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
						errordialog.setTitle("Error!");
						errordialog.setMessage(result.toString());
						errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();	
							}
						});
						AlertDialog dialog = errordialog.create();
						dialog.show();
					}else{
						// Populate dealer invoice List
					   
						SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(), invoiceList, R.layout.dealer_product_cell, new String[]{Constants.cfaName
							,Constants.DEALERNAME,Constants.cformno,Constants.cformvalue,Constants.modeofdispatch}, new int[]{R.id.position1,R.id.position2,R.id.position3,R.id.position4,R.id.position5});
						mlistview.setAdapter(adapter);
						registerForContextMenu(mlistview);
			}
			}
			}
		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName=methodname;
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}
	
	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {


		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Please select");
		long id = mlistview.getAdapter().getItemId(info.position);
		int i = (int) id; 
		menu.add(i, 0, 1, "Update");
		super.onCreateContextMenu(menu, v, menuInfo);

	};
	
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			// start anothor activity to edit.
		HashMap<String, String> data = (HashMap<String, String>) mlistview.getAdapter().getItem(item.getGroupId());

			if(data!=null){
				Bundle bundle = new Bundle();
				bundle.putString(EXTRA_CFORM_ID, data.get(Constants.cformId));
				Intent intent  = new Intent(getCurrentContext(),CFormActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			break;
		default:
			break;
		}
		return  true;
	};
}
