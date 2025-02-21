package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.TabHost.OnTabChangeListener;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;

public class MISFragmentActivity  extends FragmentActivity {

	
	private FragmentTabHost mtabHost;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		mtabHost = new FragmentTabHost(this);
		setContentView(mtabHost);
		mtabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
		
		//Dealer Manual Order will be allowed for AreaManager = 1,StateIncharege = 3,CNF = 11 ,
		 
		
		SharedPreferences pref = Util.getSharedPreferences(getApplicationContext(), Constants.PREF_NAME);
		
		String id = pref.getString("contacttypeid", "");
		if(id.equals("14")){
			mtabHost.addTab(mtabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.salereturn))
					,DealerSaleFragment.class,null);
			
			mtabHost.addTab(mtabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.editsale))
					,EditSaleFragment.class,null);
			
			mtabHost.addTab(mtabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.finalsubmit))
					,FinalSubmitFragment.class,null);
		}else if(id.equals("1")||id.equals("3")|| id.equals("11")){
			
			mtabHost.addTab(mtabHost.newTabSpec("tab4").setIndicator(getResources().getString(R.string.dealersalemanualorder))
					,DealerManualOrderFragment.class,null);
		}
		mtabHost.setCurrentTab(0);
		mtabHost.setOnTabChangedListener(ontabchangeListener);

	}
	
	
	OnTabChangeListener ontabchangeListener  = new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			mtabHost.setCurrentTabByTag(tabId);
			Log.i("Current Tab", tabId);
		}
	};
	
	@Override
	public void onBackPressed() {
	super.onBackPressed();
	startActivity(new Intent(MISFragmentActivity.this,ViewAll_AM.class));
	finish();
}
}
