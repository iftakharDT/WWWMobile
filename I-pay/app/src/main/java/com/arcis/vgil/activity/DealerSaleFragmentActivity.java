package com.arcis.vgil.activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;

import java.util.ArrayList;
import java.util.List;

public class DealerSaleFragmentActivity  extends BaseActivity{

	private TabLayout tabLayout;
	private ViewPager viewPager;

	@Override
	public void inti() {
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.visit_log);
		iniView();
	}
	/*@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		mtabHost = new FragmentTabHost(this);
		setContentView(mtabHost);
		mtabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
		
		//Dealer Manual Order will be allowed for AreaManager = 1,StateIncharege = 3,CNF = 11 ,
		 
		
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
		
		String id = pref.getString("contacttypeid", "");
		if(id.equals("14")){
			mtabHost.addTab(mtabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.salereturn))
					,DealerSaleFragmentNew.class,null);
			*//*
			mtabHost.addTab(mtabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.editsale))
					,EditSaleFragment.class,null);
			*//*
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
	*/


	private void iniView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		setupViewPager(viewPager);

		tabLayout = (TabLayout)findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);

	}
	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

		String id = pref.getString("contacttypeid", "");
		if(id.equals("14")){
			adapter.addFrag(new DealerSaleFragmentNew(), getResources().getString(R.string.salereturn));
			adapter.addFrag(new FinalSubmitFragment(), getResources().getString(R.string.finalsubmit));
		}else if(id.equals("1")||id.equals("3")|| id.equals("11")){
			adapter.addFrag(new DealerManualOrderFragment(),getResources().getString(R.string.dealersalemanualorder));

		}


		viewPager.setAdapter(adapter);

	}

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public ViewPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFrag(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}



}

