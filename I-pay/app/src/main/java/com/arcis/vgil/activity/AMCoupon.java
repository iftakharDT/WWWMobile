package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.ListbaseAdapter;
import com.arcis.vgil.coupon.Coupon;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Beanclass;

import java.util.ArrayList;

/**
 * Created by jaim on 9/20/2017.
 */

public class  AMCoupon extends BaseActivity {

    // Coupon Redemption
    // Coupon Redemption Acoount
    // Coupon Complain
    // Coupon Complain Histroy

    SharedPreferences pref;
    public static final String EXTRA_USER_CONTACT_TYPE_ID = "usertype";
    private ListView list;
    private ArrayList<Beanclass> Bean;
    private ListbaseAdapter baseAdapter;

    private int[] IMAGE = {
            R.drawable.selector1,
            R.drawable.selector5,
            R.drawable.selector1,
            R.drawable.selector5,
            R.drawable.selector1

    };

    @Override
    public void inti() {
        super.inti();
        setCurrentContext(this);
        setContentView(R.layout.view_all_am_new);
        pref=Util.getSharedPreferences(this, Constants.PREF_NAME);

        String[] TITLE = {
                getResources().getString(R.string.couponredemtion),
                getResources().getString(R.string.coupon_redemption_account),
                getResources().getString(R.string.coupon_complain),
                getResources().getString(R.string.coupon_complain_history),
                getResources().getString(R.string.coupon_redemption_history),


        };
        list = (ListView)findViewById(R.id.list);
        Bean = new ArrayList<Beanclass>();
        for (int i= 0; i< TITLE.length; i++){
            Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
            Bean.add(bean);
        }
        baseAdapter = new ListbaseAdapter(AMCoupon.this, Bean);
        list.setAdapter(baseAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent ;
                if (position==0){
                    intent = new Intent(AMCoupon.this, Coupon.class);
                    intent.putExtra(Coupon.EXTRA_REDEEM_TYPE, Coupon.EXTRA_REDEEM_FOR_MECHANIC);
                    startActivity(intent);
                }else if (position==1){
                    intent = new Intent(AMCoupon.this,CouponRedemptionAccount.class);
                    startActivity(intent);
                }else if (position==2){
                    intent = new Intent(AMCoupon.this,CoupanComplain.class);
                    startActivity(intent);
                }else if (position==3){
                    intent = new Intent(AMCoupon.this,ComplainHistory.class);
                    startActivity(intent);
                }else if (position==4){
                    intent = new Intent(AMCoupon.this,AmCustomerMaster_Fitment.class);
                    startActivity(intent);
                }

            }
        });

    }

}
