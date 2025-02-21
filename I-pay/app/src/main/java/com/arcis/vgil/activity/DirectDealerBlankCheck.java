package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.TabHost.OnTabChangeListener;

import com.arcis.vgil.R;

public class DirectDealerBlankCheck extends FragmentActivity {
	
	private FragmentTabHost mtabHost;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		mtabHost = new FragmentTabHost(this);
		setContentView(mtabHost);
		mtabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
		
		 try {
			 mtabHost.addTab(mtabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.unused_check))
						,DirectDealer_Un_UsedBank_Check.class,null);
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
			try {
				mtabHost.addTab(mtabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.used))
						,DirectDealer_UsedBank_Check.class,null);

			} catch (Exception e) {
				// TODO: handle exception
				e.getMessage();
			}	

				try {
					mtabHost.addTab(mtabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.blocked))
							,DirectDealer_Blocked_Bank_Check.class,null);
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}
				
				mtabHost.setCurrentTab(0);
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
	startActivity(new Intent(DirectDealerBlankCheck.this,ViewAll_directDealerActivity.class));
	finish();
}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
