package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.ListbaseAdapter;
import com.arcis.vgil.coupon.DirectDealerCoupon;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Beanclass;

import java.util.ArrayList;

import static com.arcis.vgil.helper.Utils.openApplication;

public class ViewAll_directDealerActivity extends BaseActivity {
	SharedPreferences pref;
	public static final String EXTRA_USER_CONTACT_TYPE_ID = "usertype";
	private ListView list;
	private ArrayList<Beanclass> Bean;
	private ListbaseAdapter baseAdapter;

	private int[] IMAGE = {
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,
			R.drawable.selector1,
			R.drawable.selector5,

	};
	@Override
	 public void inti() {
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.view_all_am_new);
		pref=Util.getSharedPreferences(this, Constants.PREF_NAME);

		String[] TITLE = {
				getResources().getString(R.string.visit_log),
				getResources().getString(R.string.sale_mm_rm),
				getResources().getString(R.string.outstanding_mm_rm),
				getResources().getString(R.string.stock_mm_rm),
				getResources().getString(R.string.order_mm_rm),
				getResources().getString(R.string.mytopten_mm_rm),
				getResources().getString(R.string.spicer_catalogue_mm_rm),
				getResources().getString(R.string.spicer_catalogue_sale_activity),
				getResources().getString(R.string.coupon_mm_rm),
				getResources().getString(R.string.daily_action_mm_rm),
		};
		list = (ListView)findViewById(R.id.list);
		Bean = new ArrayList<Beanclass>();
		for (int i= 0; i< TITLE.length; i++){
			Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
			Bean.add(bean);
		}
		baseAdapter = new ListbaseAdapter(ViewAll_directDealerActivity.this, Bean);
		list.setAdapter(baseAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent ;
				if (position==0){
					intent = new Intent(ViewAll_directDealerActivity.this,CustomerVisitDiary.class);
					startActivity(intent);
				}else if (position==1){
					intent = new Intent(ViewAll_directDealerActivity.this,RM_MM_Sale.class);
					startActivity(intent);
				}else if (position==2){
					intent = new Intent(ViewAll_directDealerActivity.this,OutstandingRmMmAm.class);
					startActivity(intent);
				}else if (position==3){
					intent = new Intent(ViewAll_directDealerActivity.this,DealerStock_Report.class);
					startActivity(intent);
				}
				else if (position==4){

					Toast.makeText(getCurrentContext(),"Not available Now",Toast.LENGTH_LONG).show();
					/*intent = new Intent(ViewAll_directDealerActivity.this,AMOrder.class);
					startActivity(intent);*/
				}else if (position==5){
						/*Toast.makeText(getCurrentContext(),"Not available Now",Toast.LENGTH_LONG).show();*/
					intent = new Intent(ViewAll_directDealerActivity.this,PartsRmMm.class);
					startActivity(intent);
				}
				else if (position==6){
					open();
				}else if (position==7){
					intent = new Intent(ViewAll_directDealerActivity.this,DealerSaleFragmentActivity.class);
					startActivity(intent);
				}else if (position==8){
					intent = new Intent(ViewAll_directDealerActivity.this,DirectDealerCoupon.class);
					startActivity(intent);
				}else if (position==9){
					Toast.makeText(getCurrentContext(),"Not available Now", Toast.LENGTH_LONG).show();
						/*intent = new Intent(ViewAll_MM_RM.this,Coupon.class);
						startActivity(intent);*/
				}
			}
		});


	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	private void open() {
		openApplication(getCurrentContext(), "com.way2webworld.spicer");
	}
}
