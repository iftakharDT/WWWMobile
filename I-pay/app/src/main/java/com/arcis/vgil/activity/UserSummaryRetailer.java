package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.helper.Utils;

import static com.arcis.vgil.helper.Utils.CountNotification;

public class UserSummaryRetailer  extends BaseActivity{

	
	private Button btn_next;
	private AutoCompleteTextView et_rm_auto ,et_si_auto, et_am_auto, et_dealer_auto;
	private SharedPreferences pref;
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
		btn_next = (Button)findViewById(R.id.next);
		btn_next.setOnClickListener(this);


		et_rm_auto = (AutoCompleteTextView) findViewById(R.id.et_rm_auto);
		et_si_auto =(AutoCompleteTextView)findViewById(R.id.et_si_auto);
		et_am_auto= (AutoCompleteTextView) findViewById(R.id.et_am_auto);
		et_dealer_auto= (AutoCompleteTextView)findViewById(R.id.et_dealer_auto);

		et_rm_auto.setVisibility(View.GONE);
		et_si_auto.setVisibility(View.GONE);
		et_am_auto.setVisibility(View.GONE);
		et_dealer_auto.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		startActivity(new Intent(UserSummaryRetailer.this, ViewAll_RetailerActivity.class));
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

}
