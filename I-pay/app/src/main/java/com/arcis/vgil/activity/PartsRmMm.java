package com.arcis.vgil.activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.fragment.TopTenAcessInventory;
import com.arcis.vgil.fragment.TopTenInventory;
import com.arcis.vgil.fragment.TopTenLoosingParts;
import com.arcis.vgil.fragment.TopTenLossMaking;
import com.arcis.vgil.fragment.TopTenPending;
import com.arcis.vgil.fragment.TopTenProfitable;
import com.arcis.vgil.fragment.TopTenRedPart;
import com.arcis.vgil.fragment.TopTenSalesParts;
import com.arcis.vgil.fragment.TopTenWinningParts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaim on 3/21/2017.
 */
public class PartsRmMm extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences spref;

    @Override
    public void inti() {
        super.inti();
        setCurrentContext(this);
        setContentView(R.layout.scrollable_tabs);
        spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);

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
        if (spref.getString(Constants.CONTACTTYPEID, "").equalsIgnoreCase("14")) {
            adapter.addFrag(new TopTenSalesParts(), "Sales");
            adapter.addFrag(new TopTenWinningParts(), "Won");
            adapter.addFrag(new TopTenLoosingParts(), "Loss");
            adapter.addFrag(new TopTenInventory(), "Inventory");
            adapter.addFrag(new TopTenAcessInventory(), "Excess Inventory");
            adapter.addFrag(new TopTenRedPart(), "Red Parts");
            adapter.addFrag(new TopTenPending(), "Pending");
        }else {
            adapter.addFrag(new TopTenSalesParts(), "Sales");
            adapter.addFrag(new TopTenWinningParts(), "Won");
            adapter.addFrag(new TopTenLoosingParts(), "Loss");
            adapter.addFrag(new TopTenInventory(), "Inventory");
            adapter.addFrag(new TopTenAcessInventory(), "Excess Inventory");
            adapter.addFrag(new TopTenRedPart(), "Red Parts");
            adapter.addFrag(new TopTenPending(), "Pending");
            adapter.addFrag(new TopTenProfitable(), "Profitable");
            adapter.addFrag(new TopTenLossMaking(), "LossMaking");
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
