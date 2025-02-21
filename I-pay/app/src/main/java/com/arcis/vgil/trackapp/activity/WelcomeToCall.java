package com.arcis.vgil.trackapp.activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;


public class WelcomeToCall extends BaseActivity {
//test
	//callplanner

	Dialog dialog;
	Button ok;
	String externalcontactview;
	LinearLayout mlayout_main;
	private boolean isLoginDealer = false;
	private CardView cv_daily_calls;
	private CardView cv_daily_visit_details;
	private CardView cv_call_planner;
	private CardView cv_daily_note;

	@Override
	 public void inti() {
		super.inti();
		setContentView(R.layout.viewall_amtwo);
		/*//getting the toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		//setting the title
		mTitle.setText("Action Menu");
		toolbar.setTitle("");
		setSupportActionBar(toolbar);*/


		setCurrentContext(this);

		cv_daily_calls =  findViewById(R.id.cv_daily_calls);
		cv_daily_calls.setOnClickListener(this);

		cv_daily_visit_details = findViewById(R.id.cv_daily_visit_details);
		cv_daily_visit_details.setOnClickListener(this);

		cv_call_planner = findViewById(R.id.cv_call_planner);
		cv_call_planner.setOnClickListener(this);

		cv_daily_note = findViewById(R.id.cv_daily_note);
		cv_daily_note.setOnClickListener(this);



	}


	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent ;

		Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
		v.startAnimation(animZoomOut);

		switch (v.getId()) {



			case R.id.cv_daily_calls:
				Toast.makeText(currentContext, "Coming Soon...", Toast.LENGTH_SHORT).show();

				break;

			case R.id.cv_daily_visit_details:
				intent = new Intent(WelcomeToCall.this, PendingCalls.class);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;

			case R.id.cv_call_planner:
				/*intent = new Intent(WelcomeToCall.this,AmCallPlanner.class);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
				intent = new Intent(WelcomeToCall.this, TerettoryCustomersList.class);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;


			case R.id.cv_daily_note:
				intent = new Intent(WelcomeToCall.this, AMDailyReport.class);
				startActivity(intent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;


		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_logout, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {


			case R.id.menu_logout:
				Intent i=new Intent(WelcomeToCall.this, LoginActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
				break;

		}
		return true;
	}*/




}
