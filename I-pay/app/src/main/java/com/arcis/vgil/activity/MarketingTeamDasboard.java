package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.trackapp.activity.WelcomeToCall;

import java.util.ArrayList;

import static com.arcis.vgil.helper.Utils.openApplication;


public class MarketingTeamDasboard extends BaseActivity{
	private SharedPreferences pref;
	public static final String EXTRA_ACTION = "action";
	public static final String EXTRA_USER_CONTACT_TYPE_ID = "usertype";
	private ListView list;

	private CardView cv_visit_log;
	private CardView cv_sales;
	private CardView cv_order;
	private CardView cv_my_top_10;
	private CardView cv_outstanding;
	private CardView cv_stock;
	private CardView cv_catalogue;
	private CardView cv_coupon;
	private CardView cv_customer_registration;
	private CardView cv_sale_activity;
	private CardView cv_daily_action;
	private CardView cv_customer_visit;
	private String contactType;

	/*private int[] IMAGE = {
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1
	};*/

	@Override
	public void inti() {
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.marketing_team_dasboard);

		SharedPreferences spref = getSharedPreferences("PASSWORD", MODE_PRIVATE);
		contactType = spref.getString(Constants.CONTACTTYPEID, "");

		cv_visit_log=findViewById(R.id.cv_visit_log);
		cv_sales=findViewById(R.id.cv_sales);
		cv_order=findViewById(R.id.cv_order);
		cv_my_top_10=findViewById(R.id.cv_my_top_10);
		cv_outstanding=findViewById(R.id.cv_outstanding);
		cv_stock=findViewById(R.id.cv_stock);
		cv_catalogue=findViewById(R.id.cv_catalogue);
		cv_coupon=findViewById(R.id.cv_coupon);
		cv_customer_registration=findViewById(R.id.cv_customer_registration);
		cv_sale_activity=findViewById(R.id.cv_sale_activity);
		cv_daily_action=findViewById(R.id.cv_daily_action);
		cv_customer_visit=findViewById(R.id.cv_customer_visit);
		cv_sale_activity.setVisibility(View.GONE);
		cv_daily_action.setVisibility(View.GONE);


		cv_visit_log.setOnClickListener(this);
		cv_sales.setOnClickListener(this);
		cv_outstanding.setOnClickListener(this);
		cv_order.setOnClickListener(this);
		cv_my_top_10.setOnClickListener(this);
		cv_stock.setOnClickListener(this);
		cv_catalogue.setOnClickListener(this);
		cv_coupon.setOnClickListener(this);
		cv_customer_registration.setOnClickListener(this);
		cv_customer_visit.setOnClickListener(this);



	/*	pref=Util.getSharedPreferences(this, Constants.PREF_NAME);
		String[] TITLE = {
				getResources().getString(R.string.visit_log),
				getResources().getString(R.string.sale_mm_rm),
				getResources().getString(R.string.outstanding_mm_rm),
				getResources().getString(R.string.stock_mm_rm),
				getResources().getString(R.string.order_mm_rm),
				getResources().getString(R.string.mytopten_mm_rm),
				getResources().getString(R.string.spicer_catalogue_mm_rm),
				getResources().getString(R.string.coupon_mm_rm),
				getResources().getString(R.string.customerregistration),
		};
		list = (ListView)findViewById(R.id.list);
		Bean = new ArrayList<Beanclass>();
		for (int i= 0; i< TITLE.length; i++){
			Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
			Bean.add(bean);
		}
		baseAdapter = new ListbaseAdapter(ViewAll_AM.this, Bean);
		list.setAdapter(baseAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent ;
				if (position==0){
					intent = new Intent(ViewAll_AM.this, AMVigitLog.class);
					startActivity(intent);
				}else if (position==1){
					intent = new Intent(ViewAll_AM.this, RM_MM_Sale.class);
					startActivity(intent);
				}else if (position==2){
					intent = new Intent(ViewAll_AM.this, OutstandingRmMmAm.class);
					startActivity(intent);
				}else if (position==3){
					intent = new Intent(ViewAll_AM.this, AmStockReport.class);
					startActivity(intent);
				}else if (position==4){
					intent = new Intent(ViewAll_AM.this, AMOrder.class);
					startActivity(intent);
				}else if (position==5){
					//Toast.makeText(getCurrentContext(),"Not available Now",Toast.LENGTH_LONG).show();
					intent = new Intent(ViewAll_AM.this,MyTopTen.class);
					startActivity(intent);
				}
				else if (position==6){
					open();
				}else if (position==7){

					Toast.makeText(getCurrentContext(),"Not available Now", Toast.LENGTH_LONG).show();
					*//*intent = new Intent(ViewAll_AM.this, AMCoupon.class);
					startActivity(intent);*//*
				}else if (position==8){
					intent = new Intent(ViewAll_AM.this, AddRetailerContact.class);
					intent.putExtra("action","add");
					startActivity(intent);
				}

			}
		});*/
	}


	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent ;
		switch(v.getId()){

			case R.id.cv_visit_log:

				if(contactType.equals("1")){
					intent = new Intent(MarketingTeamDasboard.this, AMVigitLog.class);
				}else{
					intent = new Intent(MarketingTeamDasboard.this,VisitLog.class);
				}
					startActivity(intent);
					break;

			case R.id.cv_sales:
				intent = new Intent(MarketingTeamDasboard.this, RM_MM_Sale.class);
				startActivity(intent);
				break;
			case R.id.cv_outstanding:
				intent = new Intent(MarketingTeamDasboard.this, OutstandingRmMmAm.class);
				startActivity(intent);
				break;
			case R.id.cv_stock:
				if(contactType.equals("1")){
					intent = new Intent(MarketingTeamDasboard.this, AmStockReport.class);
				}else{
					intent = new Intent(MarketingTeamDasboard.this,RM_MM_Stock_Report.class);
				}
				startActivity(intent);
				break;
			case R.id.cv_order:
				if(contactType.equals("1")){
					intent = new Intent(MarketingTeamDasboard.this, AMOrder.class);
				}else{
					intent = new Intent(MarketingTeamDasboard.this,RM_MM_PedingOrder.class);
				}
				startActivity(intent);
				break;
			case R.id.cv_my_top_10:
				intent = new Intent(MarketingTeamDasboard.this,MyTopTen.class);
				startActivity(intent);
				break;
			case R.id.cv_catalogue:
				open();
				break;
			case R.id.cv_coupon:
				//Toast.makeText(getCurrentContext(),"Not available Now", Toast.LENGTH_LONG).show();
				intent = new Intent(MarketingTeamDasboard.this, AMCoupon.class);
				startActivity(intent);
				break;
			case R.id.cv_customer_registration:
				intent = new Intent(MarketingTeamDasboard.this, AddRetailerContact.class);
				intent.putExtra("action","add");
				startActivity(intent);
               break;
			case R.id.cv_customer_visit:
				intent = new Intent(MarketingTeamDasboard.this, WelcomeToCall.class);
				startActivity(intent);
				break;


		}
	}

	private void open() {
		openApplication(getCurrentContext(), "com.silverskills.anandAutoExpo");
	}
}
