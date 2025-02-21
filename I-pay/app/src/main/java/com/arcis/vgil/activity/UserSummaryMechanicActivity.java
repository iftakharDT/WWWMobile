package com.arcis.vgil.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.coupon.Coupon;
import com.arcis.vgil.data.Constants;


public class UserSummaryMechanicActivity  extends BaseActivity{

	
	private Button btn_next,btn_coupon_redemption,coupon_account,btn_contact_us;
	

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.user_summary_mechanic_layout);

		//Utils.GCMRegistration(getCurrentContext());
		
		SharedPreferences pref=Util.getSharedPreferences(this, Constants.PREF_NAME);
		TextView tv=(TextView) findViewById(R.id.welcometext);
		tv.append(" "+ pref.getString("contactname", ""));
		
		btn_coupon_redemption=(Button) findViewById(R.id.btn_coupon_redemption);
		coupon_account=(Button) findViewById(R.id.coupon_account);
		btn_contact_us=(Button) findViewById(R.id.btn_contact_us);
		
		btn_coupon_redemption.setOnClickListener(this);
		coupon_account.setOnClickListener(this);
		btn_contact_us.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.btn_coupon_redemption:
			Intent intent  = new Intent(UserSummaryMechanicActivity.this, Coupon.class);
			intent.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_MECHANIC);
			startActivity(intent);
			break;
			
			
		case R.id.coupon_account:
			Intent intent_Account  = new Intent(UserSummaryMechanicActivity.this,CouponRedemptionAccount.class);
			intent_Account.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_ACCOUNT);
			startActivity(intent_Account);
			break;
		case R.id.btn_contact_us:
			/*Intent intent_contact  = new Intent(UserSummaryMechanicActivity.this,ContactUs.class);
			startActivity(intent_contact);*/
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
