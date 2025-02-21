package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.fragment.BroadcastFragment;
import com.arcis.vgil.fragment.NotificationFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by jaim on 2/17/2017.
 */
public class UserNotification extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences spref;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);

        MarkAllNotificationRead();

        iniView();
    }

    private void MarkAllNotificationRead() {
        GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(
                this, ProgressDialog.STYLE_SPINNER,
                "Loading...", new GetDataCallBack() {
            @Override
            public void processResponse(Object response) {

                if (response == null) {
                    Toast.makeText( getApplicationContext(),
                            getApplicationContext().getString(R.string.error4),
                            Toast.LENGTH_SHORT).show();

                } else {

                            SharedPreferences passworddetails = Util.getSharedPreferences( getApplicationContext(), Constants.PREF_NAME);
                            passworddetails.edit().putString(Constants.NotificationCount, "0").commit();





                }


            }
        });
        // Function MarkAllNotificationRead(ByVal ContactID As String, ByVal ContactTypeID As String,
        // ByVal ReadBy As String, ByVal UserID As String, ByVal Password As String) As String


        LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
        SharedPreferences passworddetails =  getApplicationContext().getSharedPreferences("PASSWORD", MODE_PRIVATE);

        try {
            request.put("ContactID", passworddetails.getString(Constants.USERID, ""));
            request.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID, ""));
            request.put("ReadBy", passworddetails.getString(Constants.USERID, ""));
            request.put(Constants.username, passworddetails.getString(Constants.ID, ""));
            request.put(Constants.password, passworddetails.getString("password", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String ipAddress =  getApplicationContext().getString(R.string.ipaddress);
        String webService =  getApplicationContext().getString(R.string.Webservice_mis_android);
        String nameSpace =  getApplicationContext().getString(R.string.nameSpace);
        String methodName = "MarkAllNotificationRead";
        String soapcomponent =  getApplicationContext().getString(R.string.soapcomponent_androidmis);
        dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName, soapcomponent);
        dataFromNetwork.sendData(request);
        dataFromNetwork.execute();

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
            adapter.addFrag(new NotificationFragment(), "Notification");
        }else {
            adapter.addFrag(new NotificationFragment(), "Notification");
            adapter.addFrag(new BroadcastFragment(), "Broadcast");
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
