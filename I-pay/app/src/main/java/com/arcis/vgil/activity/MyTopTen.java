package com.arcis.vgil.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.ListbaseAdapter;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Beanclass;

import java.util.ArrayList;

/**
 * Created by jaim on 3/21/2017.
 */

public class MyTopTen extends BaseActivity {
    SharedPreferences pref;
    public static final String EXTRA_USER_CONTACT_TYPE_ID = "usertype";
    private ListView list;
    private ArrayList<Beanclass> Bean;
    private ListbaseAdapter baseAdapter;

    private int[] IMAGE = {
            R.drawable.selector1,
            R.drawable.selector5,

    };

    @Override
    public void inti() {
        super.inti();
        setCurrentContext(this);
        setContentView(R.layout.view_all_am_new);
        pref=Util.getSharedPreferences(this, Constants.PREF_NAME);

        String[] TITLE = {
                getResources().getString(R.string.dealer_mm_rm),
                getResources().getString(R.string.part_mm_rm),


        };
        list = (ListView)findViewById(R.id.list);
        Bean = new ArrayList<Beanclass>();
        for (int i= 0; i< TITLE.length; i++){
            Beanclass bean = new Beanclass(IMAGE[i], TITLE[i]);
            Bean.add(bean);
        }
        baseAdapter = new ListbaseAdapter(MyTopTen.this, Bean);
        list.setAdapter(baseAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent ;
                if (position==0){
                    intent = new Intent(MyTopTen.this,DealerRmMm.class);
                    startActivity(intent);
                }else if (position==1){
                    intent = new Intent(MyTopTen.this,PartsRmMm.class);
                    startActivity(intent);
                }

            }
        });

    }

}



