package com.arcis.vgil.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;

public class ExternalCustomerDetailsActivity extends AppCompatActivity {

	
	TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11,txt12;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.external_customer_details_layout);
		
		txt1 = (TextView)findViewById(R.id.textView1);
		txt2 = (TextView)findViewById(R.id.textView2);
		txt3 = (TextView)findViewById(R.id.textView3);
		txt4 = (TextView)findViewById(R.id.textView4);
		txt5 = (TextView)findViewById(R.id.textView5);
		txt6 = (TextView)findViewById(R.id.textView6);
		
		txt7 = (TextView)findViewById(R.id.textView7);
		txt8 = (TextView)findViewById(R.id.textView8);
		txt9 = (TextView)findViewById(R.id.textView9);
		txt10 = (TextView)findViewById(R.id.textView10);
		
		txt11 = (TextView)findViewById(R.id.textView11);
		txt12 = (TextView)findViewById(R.id.textView12);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle!=null) {
			
			
			txt1.setText(bundle.getString(Constants.NAME));
			txt2.setText(bundle.getString(Constants.MOBILE_NUMBER));
			txt3.setText(bundle.getString(Constants.address));
			txt4.setText(bundle.getString("City"));
			txt5.setText(bundle.getString("Area"));
			txt6.setText(bundle.getString("EmailId"));
			txt7.setText(bundle.getString("BDay"));
			txt8.setText(bundle.getString("AvgCallsperYear"));
			txt9.setText(bundle.getString("LASTCALLDATE"));
			txt10.setText(bundle.getString("CUSTVALUEPMAVG"));
			txt11.setText(bundle.getString("CUSTVALUETM"));
			txt12.setText(bundle.getString("Status"));
		}
		
		
	}
}
