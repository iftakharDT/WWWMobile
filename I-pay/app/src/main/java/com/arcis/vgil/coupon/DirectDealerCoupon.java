package com.arcis.vgil.coupon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.BaseActivity;
import com.arcis.vgil.activity.CouponRedemptionAccount;
import com.arcis.vgil.activity.Util;
import com.arcis.vgil.adapter.ListbaseAdapter;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Beanclass;

import java.util.ArrayList;

public class DirectDealerCoupon extends BaseActivity {

    private SharedPreferences pref;
    public static final String EXTRA_ACTION = "action";
    public static final String EXTRA_USER_CONTACT_TYPE_ID = "usertype";
    private ListView list;
    private ArrayList<Beanclass> Bean;
    private ListbaseAdapter baseAdapter;
    private int[] IMAGE = {
            R.drawable.selector1,
            R.drawable.selector5,
            R.drawable.selector1,

    };

    @Override
    public void inti() {
        super.inti();
        setContentView(R.layout.activity_coupon);
        pref= Util.getSharedPreferences(this, Constants.PREF_NAME);
        String[] TITLE = {
                getResources().getString(R.string.couponredemptionself),
                getResources().getString(R.string.couponredemptionmech),
                getResources().getString(R.string.couponredemptionaccount),

        };
        list = (ListView)findViewById(R.id.list);
        Bean = new ArrayList<Beanclass>();
        for (int i= 0; i< TITLE.length; i++){
            Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
            Bean.add(bean);
        }
        baseAdapter = new ListbaseAdapter(DirectDealerCoupon.this, Bean);
        list.setAdapter(baseAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent ;
                if (position==0){
                    intent = new Intent(DirectDealerCoupon.this, Coupon.class);
                    intent.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_DEALER);
                    startActivity(intent);
                }else if (position==1){
                    intent = new Intent(DirectDealerCoupon.this, Coupon.class);
                    intent.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_MECHANIC);
                    startActivity(intent);
                }
                else if (position==2){
                    intent = new Intent(DirectDealerCoupon.this, CouponRedemptionAccount.class);
                    startActivity(intent);
                }
            }
        });
    }
}
