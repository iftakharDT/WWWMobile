package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.ListbaseAdapter;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Beanclass;

import java.util.ArrayList;

import static com.arcis.vgil.helper.Utils.openApplication;

public class ViewAll_MM_RM extends BaseActivity {
	SharedPreferences pref;
	public static final String EXTRA_USER_CONTACT_TYPE_ID = "usertype";
	private ListView list;
	private ArrayList<Beanclass> Bean;
	private ListbaseAdapter baseAdapter;
	private String contactTypeID;

	private int[] IMAGE = {
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1
	};

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.view_all_am_new);
		pref=Util.getSharedPreferences(this, Constants.PREF_NAME);
		contactTypeID=pref.getString(Constants.CONTACTTYPEID, "");

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

			try {

				if(i != TITLE.length - 1){
					Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
					Bean.add(bean);

				}else{
					if (contactTypeID.equalsIgnoreCase("3")){
						Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
						Bean.add(bean);
					}
				}

			}catch (Exception e){
				e.printStackTrace();
			}
		}
		baseAdapter = new ListbaseAdapter(ViewAll_MM_RM.this, Bean);
		list.setAdapter(baseAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					Intent intent ;
					if (position==0){
						intent = new Intent(ViewAll_MM_RM.this,VisitLog.class);
						startActivity(intent);
					}else if (position==1){
						intent = new Intent(ViewAll_MM_RM.this,RM_MM_Sale.class);
						startActivity(intent);
					}else if (position==2){
						intent = new Intent(ViewAll_MM_RM.this,OutstandingRmMmAm.class);
						startActivity(intent);
					}else if (position==3){
						intent = new Intent(ViewAll_MM_RM.this,RM_MM_Stock_Report.class);
						startActivity(intent);
					}
					else if (position==4){
						intent = new Intent(ViewAll_MM_RM.this,RM_MM_PedingOrder.class);
						startActivity(intent);
					}else if (position==5){
						/*Toast.makeText(getCurrentContext(),"Not available Now",Toast.LENGTH_LONG).show();*/
						intent = new Intent(ViewAll_MM_RM.this,MyTopTen.class);
						startActivity(intent);
					}
					else if (position==6){
						open();
					}else if (position==7){
						//Toast.makeText(getCurrentContext(),"Not available Now", Toast.LENGTH_LONG).show();
						intent = new Intent(ViewAll_MM_RM.this,AMCoupon.class);
						startActivity(intent);
					}else if (position==8){
						// Customer registration
						intent = new Intent(ViewAll_MM_RM.this, AddRetailerContact.class);
						intent.putExtra("action","add");
						startActivity(intent);
					}

			}
		});


		
	}

	private void open() {
		openApplication(getCurrentContext(), "com.way2webworld.spicer");
	}



}
