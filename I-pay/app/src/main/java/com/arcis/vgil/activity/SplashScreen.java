package com.arcis.vgil.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.arcis.vgil.R;

public class SplashScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		TextView tv = (TextView) findViewById(R.id.textView1 );

		Animation a = AnimationUtils.loadAnimation(this, R.anim.push_up_in);
		a.setRepeatCount(Animation.INFINITE);
		a.setRepeatMode(Animation.REVERSE);
		a.reset();
		tv.clearAnimation();
		tv.startAnimation(a);

		Handler handler = new Handler();

        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
               finish();
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                SplashScreen.this.startActivity(intent);

            }

        }, 2000); 

    }
    
   

}
