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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ViewExternalContact extends ListActivity {
	public ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
	public ArrayList<HashMap<String,Object>> array_sort =new ArrayList<HashMap<String,Object>>();
	String[] listview_array ;
	Dialog dialog;
	Intent intentdata;
	EditText search;
	SimpleAdapter simpleAdapter;
	HashMap<String, Object> row;
	public static final String AREAID = "areaid";
	private String areaID ;

	public String getAreaID() {
		return areaID;
	}


	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewexternalcontact);
		search=(EditText) findViewById(R.id.search);
		intentdata=getIntent();
		if(intentdata!=null && intentdata.getExtras()!=null){
			SharedPreferences pref = getPreferences(MODE_PRIVATE);
			String areaid = intentdata.getStringExtra("areanameid");
			if(areaid!=null){
				pref.edit().putString(AREAID,areaid ).commit();
				setAreaID(areaid);
			}
			
		}

		
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


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getAreaID()!=null){
			populateList(getAreaID());
		}else{
			String areaid = getPreferences(MODE_PRIVATE).getString(AREAID, "");
			setAreaID(areaid);
			populateList(getAreaID());
		}
		
		
	}

	
	private void populateList(String areanameid) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(ViewExternalContact.this, ProgressDialog.STYLE_SPINNER,"Loading Contact Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					list=new FetchingdataParser().getcontactdetails(response.toString());
					if(list.size()==0){
						Toast.makeText(ViewExternalContact.this,getResources().getString(R.string.message4) , Toast.LENGTH_SHORT).show();
					}else {

						simpleAdapter=new SimpleAdapter(ViewExternalContact.this,
								list, 
								R.layout.datarow,
								new String[]{Constants.CONTACTNAME_1,Constants.MOBILE_NUMBER ,Constants.emailid,Constants.dob},
								new int []{R.id.position1,R.id.position2,R.id.position3,R.id.position4});
						setListAdapter(simpleAdapter);
						registerForContextMenu(getListView());

					}

				}else if(response==null){
					Toast.makeText(ViewExternalContact.this,getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
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
		String methodName="GetAllExternalContact";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		HashMap<String, Object> roww =(HashMap<String, Object>)l.getItemAtPosition(position);

		//	final int position=position1;
		dialog = new Dialog(ViewExternalContact.this);
		dialog.setTitle(getResources().getString(R.string.viewcontact));
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(R.layout.contactview);
		((TextView)dialog.findViewById(R.id.position2)).setText(roww.get(Constants.CONTACTNAME_1).toString());
		((TextView)dialog.findViewById(R.id.position3)).setText(roww.get(Constants.streetaddress).toString());
		((TextView)dialog.findViewById(R.id.position4)).setText(roww.get(Constants.city).toString());
		((TextView)dialog.findViewById(R.id.position5)).setText(roww.get(Constants.state).toString());
		((TextView)dialog.findViewById(R.id.position6)).setText(roww.get(Constants.pincode).toString());
		((TextView)dialog.findViewById(R.id.position7)).setText(roww.get(Constants.area).toString());
		((TextView)dialog.findViewById(R.id.position8)).setText(roww.get(Constants.MOBILE_NUMBER).toString());
		((TextView)dialog.findViewById(R.id.position9)).setText(roww.get(Constants.alternatemobile).toString());
		((TextView)dialog.findViewById(R.id.position10)).setText(roww.get(Constants.dob).toString());
		((TextView)dialog.findViewById(R.id.position11)).setText(roww.get(Constants.emailid).toString());
		//((TextView)dialog.findViewById(R.id.position12)).setText(roww.get(Constants.district).toString());
		((TextView)dialog.findViewById(R.id.position13)).setText(roww.get(Constants.zone).toString());
		//	((TextView)dialog.findViewById(R.id.position2)).setText(list.get(position).get(Constants.contactname).toString());
		//	((TextView)dialog.findViewById(R.id.position3)).setText(list.get(position).get(Constants.streetaddress).toString());
		//	((TextView)dialog.findViewById(R.id.position4)).setText(list.get(position).get(Constants.city).toString());
		//	((TextView)dialog.findViewById(R.id.position5)).setText(list.get(position).get(Constants.state).toString());
		//	((TextView)dialog.findViewById(R.id.position6)).setText(list.get(position).get(Constants.pincode).toString());
		//	((TextView)dialog.findViewById(R.id.position7)).setText(list.get(position).get(Constants.area).toString());
		//	((TextView)dialog.findViewById(R.id.position8)).setText(list.get(position).get(Constants.mobilenumber).toString());
		//	((TextView)dialog.findViewById(R.id.position9)).setText(list.get(position).get(Constants.alternatemobile).toString());
		//	((TextView)dialog.findViewById(R.id.position10)).setText(list.get(position).get(Constants.dob).toString());
		//	((TextView)dialog.findViewById(R.id.position11)).setText(list.get(position).get(Constants.emailid).toString());
		dialog.show();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i=new Intent(ViewExternalContact.this, ViewAll_AM.class);
		startActivity(i);
		getPreferences(MODE_PRIVATE).edit().clear();
		finish();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = getListAdapter().getItemId(info.position);
		row =(HashMap<String, Object>)simpleAdapter.getItem(info.position);
		int i = (int) id;
		menu.setHeaderTitle("Select");
		menu.add(i,1,1,getResources().getString(R.string.edit));
		//menu.add(i,2,2,getResources().getString(R.string.delete)); // delete
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			Intent i=new Intent(ViewExternalContact.this, AddExternalContact.class);
			i.putExtra("action","edit");
			//	i.putExtra("areanameid", getintentdata.getStringExtra("areanameid"));
			//	i.putExtra("ID",list.get(item.getGroupId()).get(Constants.ID).toString());
			//	i.putExtra("contacttype",list.get(item.getGroupId()).get(Constants.contacttypeid).toString());
			//	i.putExtra("contactname",list.get(item.getGroupId()).get(Constants.contactname).toString() );
			//	if(!list.get(item.getGroupId()).get(Constants.dealerid).equals(""))
			//	i.putExtra("dealerid",list.get(item.getGroupId()).get(Constants.dealerid).toString()  );
			//	i.putExtra("mobilenumber",list.get(item.getGroupId()).get(Constants.mobilenumber).toString()  );
			//	i.putExtra("email", list.get(item.getGroupId()).get(Constants.emailid).toString() );
			//	i.putExtra("alternatemobile",list.get(item.getGroupId()).get(Constants.alternatemobile).toString()  );
			//
			//	try {
			//		SimpleDateFormat newdateFormat = new SimpleDateFormat("MM/dd/yyyy");
			//		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(list.get(item.getGroupId()).get(Constants.dob).toString());
			//		i.putExtra("dateofbirth",newdateFormat.format(date));
			//	} catch (ParseException e) {
			//		e.printStackTrace();
			//	}
			//	i.putExtra("streetaddress",list.get(item.getGroupId()).get(Constants.streetaddress).toString()  );
			//	i.putExtra("countryid",list.get(item.getGroupId()).get(Constants.countryID).toString()  );
			//	i.putExtra("zoneid", list.get(item.getGroupId()).get(Constants.zoneID).toString() );
			//	i.putExtra("stateid",list.get(item.getGroupId()).get(Constants.stateID).toString() );
			//	i.putExtra("cityid",list.get(item.getGroupId()).get(Constants.cityID).toString()  );
			//	i.putExtra("areaid", list.get(item.getGroupId()).get(Constants.areaID).toString() );
			//	i.putExtra("pincode",list.get(item.getGroupId()).get(Constants.pincode).toString()  );
			//	i.putExtra("primary",list.get(item.getGroupId()).get(Constants.IsPrimary).toString()  );	
			i.putExtra("ID",row.get(Constants.ID).toString());
			i.putExtra("contacttype",row.get(Constants.CONTACTTYPEID_1).toString());
			i.putExtra("contactname",row.get(Constants.CONTACTNAME_1).toString() );
			if(!row.get(Constants.dealerid).equals(""))
				i.putExtra("dealerid",row.get(Constants.dealerid).toString()  );
			i.putExtra("mobilenumber",row.get(Constants.MOBILE_NUMBER).toString()  );
			i.putExtra("email", row.get(Constants.emailid).toString() );
			i.putExtra("alternatemobile",row.get(Constants.alternatemobile).toString()  );

			try {
				SimpleDateFormat newdateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(row.get(Constants.dob).toString());
				i.putExtra("dateofbirth",newdateFormat.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			i.putExtra("streetaddress",row.get(Constants.streetaddress).toString()  );
			i.putExtra("countryid",row.get(Constants.countryID).toString()  );
			i.putExtra("zoneid", row.get(Constants.zoneID).toString() );
			i.putExtra("stateid",row.get(Constants.stateID).toString() );
			//	i.putExtra("districtid",row.get(Constants.districtID).toString() );
			i.putExtra("cityid",row.get(Constants.cityID).toString()  );
			i.putExtra("areaid", row.get(Constants.areaID).toString() );
			i.putExtra("pincode",row.get(Constants.pincode).toString()  );
			i.putExtra("primary",row.get(Constants.IsPrimary).toString()  );
			startActivity(i);
			break;
			//case 2:
			//	//delete function call here
			//	break;

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
			populateList(getAreaID());	
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
}
