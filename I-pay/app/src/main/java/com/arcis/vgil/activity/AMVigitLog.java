package com.arcis.vgil.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.arcis.vgil.R;
import com.arcis.vgil.fragment.CustomerVisitDiary;
import com.arcis.vgil.fragment.Dashboard;
import com.arcis.vgil.fragment.ExternalCustomerMasterSearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaim on 3/10/2017.
 */
public class AMVigitLog extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void inti() {
        super.inti();
        setCurrentContext(this);
        setContentView(R.layout.visit_log);
        iniView();
    }

    private void iniView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Dashboard(), "DashBoard");
        adapter.addFrag(new ExternalCustomerMasterSearchActivity(), "Customer Master");
        adapter.addFrag(new CustomerVisitDiary(), "Am Visit Diary");
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
