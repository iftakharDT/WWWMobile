package com.arcis.vgil.activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.fragment.DealerNameHistoryForSale;
import com.arcis.vgil.fragment.DealerNameHistoryForSaleThisMonth;
import com.arcis.vgil.fragment.RM_MM_Sales;
import com.arcis.vgil.fragment.RM_MM_SalesThisMonth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaim on 3/10/2017.
 */

public class RM_MM_Sale extends BaseActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences spref;


    @Override
    public void inti() {
        super.inti();
        setCurrentContext(this);
        setContentView(R.layout.visit_log);
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
        if (spref.getString(Constants.CONTACTTYPEID,"").equalsIgnoreCase("14")) {
            adapter.addFrag(new DealerNameHistoryForSale(), "Purchase History till LM");
            adapter.addFrag(new DealerNameHistoryForSaleThisMonth(), "Purchase History Form TM");
        }else {
            adapter.addFrag(new RM_MM_Sales(), "Sale History till LM");
            adapter.addFrag(new RM_MM_SalesThisMonth(), "Sales History Form TM");
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
