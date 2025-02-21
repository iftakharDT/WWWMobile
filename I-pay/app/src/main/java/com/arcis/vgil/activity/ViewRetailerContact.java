package com.arcis.vgil.activity;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ViewRetailerContact extends ListActivity {

	public ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
	String[] listview_array ;
	Dialog dialog;
	Intent getintentdata;
	EditText search;
	SimpleAdapter simpleAdapter;
	HashMap<String, Object> row;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewretailercontact);
		search=(EditText) findViewById(R.id.search);
		getintentdata=getIntent();
		populateList(getintentdata.getStringExtra("areanameid"));			

		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//((SimpleAdapter)getListAdapter()).getFilter().filter(s); 
				if(simpleAdapter!=null){
					simpleAdapter.getFilter().filter(s);
					simpleAdapter.notifyDataSetChanged();
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void populateList(String areanameid) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(ViewRetailerContact.this, ProgressDialog.STYLE_SPINNER,"Loading Contact Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					list=new FetchingdataParser().getretailercontactdetails(response.toString());
					if(list.size()==0){
						Toast.makeText(ViewRetailerContact.this,getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
					}else {

						simpleAdapter=new SimpleAdapter(ViewRetailerContact.this,
								list, 
								R.layout.datarow,
								new String[]{Constants.CONTACTNAME_1,Constants.PhoneNo ,Constants.streetaddress,Constants.city},
								new int []{R.id.position1,R.id.position2,R.id.position3,R.id.position4});
						setListAdapter(simpleAdapter);
						registerForContextMenu(getListView());
					}
				}else if(response==null){
					Toast.makeText(ViewRetailerContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			request.put(Constants.areaID,areanameid);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="ShowRetailer";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		HashMap<String, Object> roww =(HashMap<String, Object>)l.getItemAtPosition(position);

		dialog = new Dialog(ViewRetailerContact.this);
		dialog.setTitle(getResources().getString(R.string.retailercontact));
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(R.layout.retailercontactview);
		HashMap<String, Object> map = list.get(position);
		((TextView)dialog.findViewById(R.id.position2)).setText(roww.get(Constants.CONTACTNAME_1).toString());
		((TextView)dialog.findViewById(R.id.position3)).setText(roww.get(Constants.contacttype).toString());
		((TextView)dialog.findViewById(R.id.position4)).setText(roww.get(Constants.PhoneNo).toString());
		((TextView)dialog.findViewById(R.id.position5)).setText(roww.get(Constants.streetaddress).toString());
		((TextView)dialog.findViewById(R.id.position6)).setText(roww.get(Constants.area).toString());
		((TextView)dialog.findViewById(R.id.position7)).setText(roww.get(Constants.city).toString());
		((TextView)dialog.findViewById(R.id.position8)).setText(roww.get(Constants.state).toString());
		((TextView)dialog.findViewById(R.id.position9)).setText(roww.get(Constants.zone).toString());
		((TextView)dialog.findViewById(R.id.position10)).setText(roww.get(Constants.country).toString());
		((TextView)dialog.findViewById(R.id.position11)).setText(roww.get(Constants.pincode).toString());
		
		((TextView)dialog.findViewById(R.id.position18)).setText(roww.get(Constants.AccountHolderName).toString());
		((TextView)dialog.findViewById(R.id.position19)).setText(roww.get(Constants.BankName).toString());
		((TextView)dialog.findViewById(R.id.position20)).setText(roww.get(Constants.BranchName).toString());
		((TextView)dialog.findViewById(R.id.position21)).setText(roww.get(Constants.IFSCCode).toString());
		((TextView)dialog.findViewById(R.id.position22)).setText(roww.get(Constants.AccountType).toString());
		((TextView)dialog.findViewById(R.id.position23)).setText(roww.get(Constants.AccountNumber).toString());
		dialog.show();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent(ViewRetailerContact.this, ViewAll_AM.class);
		startActivity(i);
		finish();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = simpleAdapter.getItemId(info.position);
		row =(HashMap<String, Object>)simpleAdapter.getItem(info.position);
		int i = (int) id;
		menu.setHeaderTitle("Select");
		menu.add(i,1,1,getResources().getString(R.string.edit));
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			Intent i=new Intent(ViewRetailerContact.this, AddRetailerContact.class);
			i.putExtra("retaileraction","edit");
		
			i.putExtra("areanameid", getintentdata.getStringExtra("areanameid"));
			i.putExtra("ID",row.get(Constants.ID).toString());
			i.putExtra("contactname",row.get(Constants.CONTACTNAME_1).toString() );

			i.putExtra("garagename",row.get(Constants.GarageName).toString() );
			i.putExtra("verificationdoctype",row.get(Constants.VerificationID).toString() );
			i.putExtra("verificationdoc",row.get(Constants.VerificationName).toString() );
			i.putExtra("ISIndividula",row.get(Constants.ISIndividula).toString() );
			i.putExtra("districtid",row.get(Constants.districtID).toString());

			i.putExtra("contacttype",row.get(Constants.CONTACTTYPEID_1).toString());
			i.putExtra("mobilenumber",row.get(Constants.PhoneNo).toString()  );
			i.putExtra("streetaddress",row.get(Constants.streetaddress).toString()  );
			i.putExtra("countryid",row.get(Constants.countryID).toString()  );
			i.putExtra("zoneid", row.get(Constants.zoneID).toString() );
			i.putExtra("stateid",row.get(Constants.stateID).toString() );
			i.putExtra("cityid",row.get(Constants.cityID).toString()  );
			i.putExtra("areaid", row.get(Constants.areaID).toString() );
			i.putExtra("pincode",row.get(Constants.pincode).toString()  );
			i.putExtra("AccountHolderName",row.get(Constants.AccountHolderName).toString()  );	
			i.putExtra("BankName",row.get(Constants.BankNameID).toString()  );	
			i.putExtra("BranchName",row.get(Constants.BankBranchID).toString()  );	
			i.putExtra("IFSCCode",row.get(Constants.IFSCCode).toString()  );	
			i.putExtra("AccountType",row.get(Constants.AccountTypeID).toString()  );	
			i.putExtra("AccountNumber",row.get(Constants.AccountNumber).toString()  );	
			i.putExtra("Authorized",row.get(Constants.authorized).toString()  );	
			startActivity(i);
			finish();
			break;
			//case 2:
			//		//delete function call here
			//		break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Refresh").setIcon(android.R.drawable.ic_menu_preferences);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("Refresh")) {
			populateList(getintentdata.getStringExtra("areanameid"));	
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
}