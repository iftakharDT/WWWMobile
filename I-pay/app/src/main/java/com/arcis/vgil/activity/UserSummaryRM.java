package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.CustomerAdapterAutoComplete;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.Utils;
import com.arcis.vgil.model.AmNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.arcis.vgil.helper.Utils.CountNotification;

public class UserSummaryRM  extends BaseActivity {
	private Button btn_next,get_value;
	String ContactID,ContactTypeID,RMID="0",SIID="0",AMID="0",DealerID="0";
	private ArrayList<AmNameModel> RmModelsList,SiModelList,AmModelList,DealerModelList;
	private AutoCompleteTextView et_rm_auto ,et_si_auto, et_am_auto, et_dealer_auto;
	private CustomerAdapterAutoComplete auto_adapter;
	private TextView st_tv_tgt,st_tv_act,sa_tv_actly,sftm_tv_tgt,sftm_tv_act,
			sftm_tv_actly,order_ftm,order_po,order_pd,out_os_tv,out_od_tv,out_lock_tv,
			stock_tv_tstk,stock_tv_rgap,stock_tv_ygap,reg_dlr,reg_mech,reg_city,atctm_dealer,
			atctm_machanic,atctm_city;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rm_am_summary_layout);
		setCurrentContext(this);
		CountNotification(getCurrentContext());

		//Utils.GCMRegistration(getCurrentContext());

		pref=Util.getSharedPreferences(this, Constants.PREF_NAME);
		TextView tv=(TextView) findViewById(R.id.welcometext);
		tv.append(" "+ pref.getString("contactname", ""));
		ContactTypeID=pref.getString(Constants.CONTACTTYPEID, "");
		ContactID =pref.getString(Constants.USERID, "");

		st_tv_tgt=(TextView) findViewById(R.id.st_tv_tgt);
		st_tv_act=(TextView) findViewById(R.id.st_tv_act);
		sa_tv_actly=(TextView) findViewById(R.id.sa_tv_actly);

		sftm_tv_tgt=(TextView) findViewById(R.id.sftm_tv_tgt);
		sftm_tv_act=(TextView) findViewById(R.id.sftm_tv_act);
		sftm_tv_actly=(TextView) findViewById(R.id.sftm_tv_actly);

		order_ftm=(TextView) findViewById(R.id.order_ftm);
		order_po=(TextView) findViewById(R.id.order_po);
		order_pd=(TextView) findViewById(R.id.order_pd);
		out_os_tv=(TextView) findViewById(R.id.out_os_tv);
		out_od_tv=(TextView) findViewById(R.id.out_od_tv);
		out_lock_tv=(TextView) findViewById(R.id.out_lock_tv);


		stock_tv_tstk=(TextView) findViewById(R.id.stock_tv_tstk);
		stock_tv_rgap=(TextView) findViewById(R.id.stock_tv_rgap);
		stock_tv_ygap=(TextView) findViewById(R.id.stock_tv_ygap);


		reg_dlr=(TextView) findViewById(R.id.reg_dlr);
		reg_mech=(TextView) findViewById(R.id.reg_mech);
		reg_city=(TextView) findViewById(R.id.reg_city);


		atctm_dealer=(TextView) findViewById(R.id.atctm_dealer);
		atctm_machanic=(TextView) findViewById(R.id.atctm_machanic);
		atctm_city=(TextView) findViewById(R.id.atctm_city);



		btn_next  = (Button)findViewById(R.id.next);
		btn_next.setOnClickListener(this);
		get_value  = (Button)findViewById(R.id.get_value);
		get_value.setOnClickListener(this);

		et_rm_auto = (AutoCompleteTextView) findViewById(R.id.et_rm_auto);
		et_si_auto =(AutoCompleteTextView)findViewById(R.id.et_si_auto);
		et_am_auto= (AutoCompleteTextView) findViewById(R.id.et_am_auto);
		et_dealer_auto= (AutoCompleteTextView)findViewById(R.id.et_dealer_auto);

		et_rm_auto.setVisibility(View.GONE);
		et_si_auto.setVisibility(View.GONE);

		et_rm_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				AmNameModel selected = (AmNameModel) parent.getAdapter().getItem(position);
				RMID=selected.getAmidd();
				removedData();
				LoginWiseContactHierarchy(ContactTypeID,ContactID,RMID,"0","0","0","SI");
				LoginWiseContactHierarchy(ContactTypeID,ContactID,RMID,"0","0","0","AM");
				if (DealerModelList!=null){
					DealerModelList.clear();
					et_dealer_auto.setText("");
				}
				et_si_auto.setText("");
				et_am_auto.setText("");

			}
		});

		et_si_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AmNameModel selected = (AmNameModel) parent.getAdapter().getItem(position);
				SIID=selected.getAmidd();
				removedData();
				LoginWiseContactHierarchy(ContactTypeID,ContactID,RMID,SIID,"0","0","AM");
				if (DealerModelList!=null){
					DealerModelList.clear();
					et_dealer_auto.setText("");
				}
				et_am_auto.setText("");


			}
		});


		et_am_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AmNameModel selected = (AmNameModel) parent.getAdapter().getItem(position);
				AMID=selected.getAmidd();
				removedData();
				LoginWiseContactHierarchy(ContactTypeID,ContactID,RMID,SIID,AMID,"0","DLR");
				et_dealer_auto.setText("");


			}
		});

		et_dealer_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AmNameModel selected = (AmNameModel) parent.getAdapter().getItem(position);
				DealerID=selected.getAmidd();
				removedData();
			}
		});

		if (ContactTypeID.equalsIgnoreCase("4")) {
			//ContactType="RM";
			LoginWiseContactHierarchy(ContactTypeID,ContactID,"0","0","0","0","AM");



		}

		if (ContactTypeID.equalsIgnoreCase("27")) {
			//ContactType="CO";
			LoginWiseContactHierarchy(ContactTypeID,ContactID,"0","0","0","0","AM");



		}

		if (ContactTypeID.equalsIgnoreCase("28")) {
			//ContactType="HO";
			LoginWiseContactHierarchy(ContactTypeID,ContactID,"0","0","0","0","AM");



		}
		if (ContactTypeID.equalsIgnoreCase("3")) {
			//ContactType="SI";
			LoginWiseContactHierarchy(ContactTypeID,ContactID,"0","0","0","0","AM");
		}


	}

	private void removedData() {

		st_tv_tgt.setText("");
		st_tv_act.setText("");
		sa_tv_actly.setText("");
		sftm_tv_tgt.setText("");
		sftm_tv_act.setText("");
		sftm_tv_actly.setText("");
		order_ftm.setText("");
		order_po.setText("");
		order_pd.setText("");
		out_os_tv.setText("");
		out_od_tv.setText("");
		out_lock_tv.setText("");
		stock_tv_tstk.setText("");
		stock_tv_rgap.setText("");
		stock_tv_ygap.setText("");
		reg_dlr.setText("");
		reg_mech.setText("");
		reg_city.setText("");
		atctm_dealer.setText("");
		atctm_machanic.setText("");
		atctm_city.setText("");

	}

	private void LoginWiseContactHierarchy(String contactTypeID, String contactID, String RMID, String SIID, String AMID, String DealerID, final String RequiredContactType) {


		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					if (RequiredContactType.equalsIgnoreCase("RM")){
						RmModelsList = FetchingdataParser.getFilterValueID(response.toString());
						if(RmModelsList!=null ){
							if (RmModelsList.size()!=0) {
								et_rm_auto.setThreshold(1);
								auto_adapter = new CustomerAdapterAutoComplete(getCurrentContext(), R.layout.row_people, RmModelsList);
								et_rm_auto.setAdapter(auto_adapter);

							}
						}
					}
					if (RequiredContactType.equalsIgnoreCase("SI")){
						SiModelList = FetchingdataParser.getFilterValueID(response.toString());
						if(SiModelList!=null ){
							if (SiModelList.size()!=0) {
								et_si_auto.setThreshold(1);
								auto_adapter = new CustomerAdapterAutoComplete(getCurrentContext(), R.layout.row_people, SiModelList);
								et_si_auto.setAdapter(auto_adapter);

							}
						}

					}if (RequiredContactType.equalsIgnoreCase("AM")){
						AmModelList = FetchingdataParser.getFilterValueID(response.toString());
						if(AmModelList!=null ){
							if (AmModelList.size()!=0) {
								et_am_auto.setThreshold(1);
								auto_adapter = new CustomerAdapterAutoComplete(getCurrentContext(), R.layout.row_people, AmModelList);
								et_am_auto.setAdapter(auto_adapter);

							}
						}

					}if (RequiredContactType.equalsIgnoreCase("DLR")){
						DealerModelList = FetchingdataParser.getFilterValueID(response.toString());
						if(DealerModelList!=null ){
							if (DealerModelList.size()!=0) {
								et_dealer_auto.setThreshold(1);
								auto_adapter = new CustomerAdapterAutoComplete(getCurrentContext(), R.layout.row_people, DealerModelList);
								et_dealer_auto.setAdapter(auto_adapter);

							}
						}

					}
				}
				else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		try {
			request.put("ContactTypeID",contactTypeID);
			request.put("ContactID", contactID);
			request.put("RMID", RMID);
			request.put("SIID",SIID);
			request.put("AMID", AMID);
			request.put("DealerID", DealerID);
			request.put("RequiredContactType",RequiredContactType);


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="LoginWiseContactHierarchy";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();


	}

	//Function LandingScreenFromSavedData(ByVal ContactTypeID As String,
	// ByVal ContactID As String, ByVal RMID As String, ByVal SIID As String,
	// ByVal AMID As String, ByVal DealerID As String) As String

	private void AM_Summary_MIS(String contactTypeID, String contactID, String RMID, String SIID, String AMID, String DealerID) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {



				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getAMSummary_MIS(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {


							st_tv_tgt.setText(itemQuantityValueList.get(0).get(Constants.TGTFTY));
							st_tv_act.setText(itemQuantityValueList.get(0).get(Constants.ACTFTY));
							sa_tv_actly.setText(itemQuantityValueList.get(0).get(Constants.ACTLYFTY));

							sftm_tv_tgt.setText(itemQuantityValueList.get(0).get(Constants.TGTFTM));
							sftm_tv_act.setText(itemQuantityValueList.get(0).get(Constants.ACTFTM));
							sftm_tv_actly.setText(itemQuantityValueList.get(0).get(Constants.ACTLYFTM));

							order_ftm.setText(itemQuantityValueList.get(0).get(Constants.OrderFTM));
							order_po.setText(itemQuantityValueList.get(0).get(Constants.PO));
							order_pd.setText(itemQuantityValueList.get(0).get(Constants.PD));


							out_os_tv.setText(itemQuantityValueList.get(0).get(Constants.Days));
							out_od_tv.setText(itemQuantityValueList.get(0).get(Constants.OD));
							out_lock_tv.setText(itemQuantityValueList.get(0).get(Constants.LockPercent));


							stock_tv_tstk.setText(itemQuantityValueList.get(0).get(Constants.TStk));
							stock_tv_rgap.setText(itemQuantityValueList.get(0).get(Constants.RGap));
							stock_tv_ygap.setText(itemQuantityValueList.get(0).get(Constants.Setup));


							reg_dlr.setText(itemQuantityValueList.get(0).get(Constants.DLR));
							reg_mech.setText(itemQuantityValueList.get(0).get(Constants.Mech));
							reg_city.setText(itemQuantityValueList.get(0).get(Constants.City));


							atctm_dealer.setText(itemQuantityValueList.get(0).get(Constants.ACTDLRTM));
							atctm_machanic.setText(itemQuantityValueList.get(0).get(Constants.ACTMECHTM));
							atctm_city.setText(itemQuantityValueList.get(0).get(Constants.ACTCITYTM));



						}


					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();

		try {

			request.put("ContactTypeID",contactTypeID);
			request.put("ContactID", contactID);
			request.put("RMID", RMID);
			request.put("SIID",SIID);
			request.put("AMID", AMID);
			request.put("DealerID", DealerID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="LandingScreenFromSavedData";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();



	}


	OnItemSelectedListener onitemselectedlistener = new OnItemSelectedListener() {



		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
			// TODO Auto-generated method stub


		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, R.string.refresh).setIcon(R.drawable.refresh);
		getMenuInflater().inflate(R.menu.menu_main, menu);
		MenuItem item = menu.findItem(R.id.saved_badge);
		MenuItemCompat.setActionView(item, R.layout.badge_layout);
		RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
		ImageView notificationImage = (ImageView) notifCount.findViewById(R.id.notification);
		TextView tv = (TextView) notifCount.findViewById(R.id.counter);
		if (pref.getString(Constants.NotificationCount, "").equalsIgnoreCase("0")){
			tv.setVisibility(View.INVISIBLE);

		}else {
			tv.setVisibility(View.VISIBLE);
			tv.setText(pref.getString(Constants.NotificationCount, ""));
		}
		notificationImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent= new Intent(UserSummaryRM.this, UserNotification.class);
				startActivity(intent);
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case 1:
				Intent intent  = new Intent(UserSummaryRM.this,UserSummaryRM.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;

			case 2:
				Intent intent1=new Intent(UserSummaryRM.this, LoginActivity.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent1);
				finish();
				break;

		}
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.next:
				startActivity(new Intent(v.getContext(),ViewAll_MM_RM.class));
				break;

			case R.id.get_value:
				if (et_rm_auto.getText().length()==0){
					RMID="0";
				}if (et_si_auto.getText().length()==0){
				SIID="0";

			    }if (et_am_auto.getText().length()==0){
				 AMID="0";

			    }if (et_dealer_auto.getText().length()==0){
				 DealerID="0";

			    }
				AM_Summary_MIS( ContactTypeID,ContactID,RMID,SIID,AMID,DealerID);
				//Toast.makeText(getCurrentContext(), "This feature is not available ", Toast.LENGTH_LONG).show();
				break;

			default:
				break;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}





}