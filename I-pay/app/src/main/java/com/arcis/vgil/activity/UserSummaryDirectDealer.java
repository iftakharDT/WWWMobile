package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.MessageAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.Utils;
import com.arcis.vgil.model.AmNameModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.arcis.vgil.helper.Utils.CountNotification;

public class UserSummaryDirectDealer  extends BaseActivity{



	private Button btn_next,get;
	String popmethod1="CDDueInNextThreeDays";
	String popmethod2="PaymentDueInNextThreeDays";
	String popmethod3="DealerAccountLockReason";
	MessageAlertAdapter messageAlertAdapter;
	private ArrayList<AmNameModel> messageList;
	private TextView st_tv_tgt,st_tv_act,sa_tv_actly,sftm_tv_tgt,sftm_tv_act,
			sftm_tv_actly,order_ftm,order_po,order_pd,out_os_tv,out_od_tv,out_lock_tv,
			stock_tv_tstk,stock_tv_rgap,stock_tv_ygap,reg_dlr,reg_mech,reg_city,atctm_dealer,
			atctm_machanic,atctm_city;
	String ContactID,ContactTypeID,RMID="0",SIID="0",AMID="0",DealerID="0";
    private AutoCompleteTextView et_rm_auto ,et_si_auto, et_am_auto, et_dealer_auto;
    private LinearLayout ll_regd, ll_acttm;
	private SharedPreferences pref;

	AlertDialog dialog;
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.rm_am_summary_layout);
		setCurrentContext(this);
		CountNotification(getCurrentContext());

		//Utils.GCMRegistration(getCurrentContext());

		pref=Util.getSharedPreferences(this, Constants.PREF_NAME);
		TextView tv=(TextView) findViewById(R.id.welcometext);
		tv.append(" "+ pref.getString("contactname", ""));
		ContactTypeID=pref.getString(Constants.CONTACTTYPEID, "");
		ContactID =pref.getString(Constants.DEALERID, "");


		btn_next  = (Button)findViewById(R.id.next);
		btn_next.setOnClickListener(this);

		get = (Button)findViewById(R.id.get_value);
		get.setOnClickListener(this);



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



        et_rm_auto = (AutoCompleteTextView) findViewById(R.id.et_rm_auto);
        et_si_auto =(AutoCompleteTextView)findViewById(R.id.et_si_auto);
        et_am_auto= (AutoCompleteTextView) findViewById(R.id.et_am_auto);
        et_dealer_auto= (AutoCompleteTextView)findViewById(R.id.et_dealer_auto);

        et_rm_auto.setVisibility(View.GONE);
        et_si_auto.setVisibility(View.GONE);
        et_am_auto.setVisibility(View.GONE);
        et_dealer_auto.setVisibility(View.GONE);

        ll_regd= (LinearLayout) findViewById(R.id.ll_regd);
        ll_acttm= (LinearLayout) findViewById(R.id.ll_acttm);
        ll_regd.setVisibility(View.GONE);
        ll_acttm .setVisibility(View.GONE);


		cdDueInNextThreeDays();
		paymentDueInNextThreeDays();
		dealerAccountLockReason();



	}

	private void cdDueInNextThreeDays() {
		dealerAlertPopUpMessage(popmethod1,1);
	}
	private void paymentDueInNextThreeDays() {
		dealerAlertPopUpMessage(popmethod2,2);
	}

	private void dealerAccountLockReason() {
		dealerAlertPopUpMessage(popmethod3,3);
	}




	private void dealerAlertPopUpMessage(String method, final int type) {

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					messageList = FetchingdataParser.getAlertMessage(response.toString());
					if(messageList!=null ){
						if (messageList.size()!=0) {
							if (type==1){
								alertDialogueMessage(messageList);

							}else if (type==2){
								alertDialogueMessage(messageList);

							}else {
								alertDialogueMessage(messageList);
							}


						}


					}

				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			//CDDueInNextThreeDays(ByVal CustomerID As String, ByVal UserID As String,
			// ByVal Password As String) As String


			request.put("CustomerID", passworddetails.getString(Constants.USERID,""));
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName=method;
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();


	}

	private void alertDialogueMessage(ArrayList<AmNameModel> messageList) {

		LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view  = inflator.inflate(R.layout.rm_mm_stock_result_search, null);
		EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
		searchpartNo.setVisibility(View.GONE);
		LinearLayout ll_pending_order_dialogue= (LinearLayout) view.findViewById(R.id.ll_pending_order_dialogue);
		ll_pending_order_dialogue.setVisibility(View.GONE);
		ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
		AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
		builder.setView(view);
		messageAlertAdapter=new MessageAlertAdapter(getCurrentContext(), R.layout.rm_mm_outsatanding_shell, messageList);
		list_part_no.setAdapter(messageAlertAdapter);
		builder.setView(view);
		builder.setNegativeButton(
				"cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		dialog = builder.show();

	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
			case R.id.next:
				startActivity(new Intent(v.getContext(),ViewAll_directDealerActivity.class));
				overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
				break;

			case R.id.get_value:
				//DealerSummary_MIS();
				AM_Summary_MIS( ContactTypeID,ContactID,RMID,SIID,AMID,ContactID);

				break;

		}



	}
	
	OnItemSelectedListener onitemselectedlistener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};


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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//menu.add(1, 2, 2, "Logout").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		//return super.onCreateOptionsMenu(menu);
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

				Intent intent= new Intent(UserSummaryDirectDealer.this, UserNotification.class);
				startActivity(intent);
			}
		});

		return super.onCreateOptionsMenu(menu);
	}
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, R.string.refresh).setIcon(R.drawable.refresh);
		return super.onCreateOptionsMenu(menu);
	}*/

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, R.string.refresh).setIcon(R.drawable.refresh);
		return super.onCreateOptionsMenu(menu);
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
	case 1:
		Intent intent  = new Intent(UserSummaryDirectDealer.this,UserSummaryDirectDealer.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		break;
			case 2:
				Intent intent1=new Intent(UserSummaryDirectDealer.this, LoginActivity.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent1);
				finish();
				break;
		
		}
		return true;
	}
}
