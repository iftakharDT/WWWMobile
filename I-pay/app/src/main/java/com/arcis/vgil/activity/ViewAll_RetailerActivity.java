package com.arcis.vgil.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.arcis.vgil.R;
import com.arcis.vgil.coupon.Coupon;


public class ViewAll_RetailerActivity extends BaseActivity {

	private Button btn_couponRedSelf,btn_couponRedMech,btn_retailer,btn_retailer6;
	
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.viewall_retailer_layout);
		
		btn_couponRedSelf = (Button)findViewById(R.id.btn_retailer1);
		btn_couponRedSelf.setOnClickListener(this);
		
		btn_couponRedMech = (Button)findViewById(R.id.btn_retailer2);
		btn_couponRedMech.setOnClickListener(this);
		
		btn_retailer= (Button) findViewById(R.id.btn_retailer3);
		btn_retailer.setOnClickListener(this);
		btn_retailer= (Button) findViewById(R.id.btn_retailer5);
		btn_retailer.setOnClickListener(this);
		
		btn_retailer6=(Button) findViewById(R.id.btn_retailer6);
		btn_retailer6.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent ;
		
		switch (v.getId()) {
	
		
		case R.id.btn_retailer1:
			intent = new Intent(ViewAll_RetailerActivity.this, Coupon.class);
			intent.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_DEALER);
			startActivity(intent);
			break;
			
		case R.id.btn_retailer2:
			intent = new Intent(ViewAll_RetailerActivity.this, Coupon.class);
			intent.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_MECHANIC);
			startActivity(intent);
			break;
		case R.id.btn_retailer3:
			intent= new Intent(ViewAll_RetailerActivity.this,CouponRedemptionAccount.class);
			startActivity(intent);
			break;
        
		case R.id.btn_retailer5:
			intent= new Intent(ViewAll_RetailerActivity.this,Retailor_Direct_Dealer_Search.class);
			startActivity(intent);
			break;
			
		case R.id.btn_retailer6:
			/*intent= new Intent(ViewAll_RetailerActivity.this,ContactUs.class);
			startActivity(intent);*/
			break;
			

		}
	}
}
