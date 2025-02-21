package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DealerOutstandingCFormCredit extends BaseActivity {
	
	
	private EditText etOutStanding,et_OverDue,et_od30Due,et_pendiCForm,creditLimit,lockReason;
	 Button btn_OutStanding,btn_OverDue,btn_od30Due,btn_pendiCForm;
	 Double etOutStandingValue,et_OverDueValue,et_od30DueValue,et_pendiCFormValue;

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealer_outstanding_c_form);
		setCurrentContext(this);
		creditLimit=(EditText) findViewById(R.id.et_credit_limit);
		lockReason=(EditText) findViewById(R.id.et_lock_reason);
		etOutStanding=(EditText) findViewById(R.id.editText1);
		et_OverDue=(EditText) findViewById(R.id.editText2);
		et_od30Due=(EditText) findViewById(R.id.editText3);
		et_pendiCForm=(EditText) findViewById(R.id.editText4);

		
		
		
		btn_OutStanding=(Button) findViewById(R.id.button1);
		btn_OutStanding.setOnClickListener(this);
		btn_OverDue=(Button) findViewById(R.id.button2);
		btn_OverDue.setOnClickListener(this);
		btn_od30Due=(Button) findViewById(R.id.button3);
		btn_od30Due.setOnClickListener(this);
		btn_pendiCForm=(Button) findViewById(R.id.button4);
		btn_pendiCForm.setOnClickListener(this);
		
		btn_OutStanding.setClickable(false);
		btn_OverDue.setClickable(false);
	    btn_od30Due.setClickable(false);
		btn_pendiCForm.setClickable(false);
		
		Button show=(Button) findViewById(R.id.sent);
		show.setOnClickListener(this);
	}
  @Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	super.onClick(v);
	Intent intent=null;
	switch (v.getId()) {
	case R.id.sent:
		
		btn_OutStanding.setClickable(true);
		btn_OverDue.setClickable(true);
	    btn_od30Due.setClickable(true);
		btn_pendiCForm.setClickable(true);
		
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences("PASSWORD", getCurrentContext().MODE_PRIVATE);
	    String DealerID =passworddetails.getString("dealerid","");
		DealerOutstandingSummary(DealerID);
		
		break;
		
	case R.id.button1:
			intent= new Intent(DealerOutstandingCFormCredit.this,DirectDealerOutstandingCFormCreditResult.class);
			intent.putExtra("Type_C_Form", "OS");
			startActivity(intent);
	
		
		

		break;

    case R.id.button2:
    	
    	    intent= new Intent(DealerOutstandingCFormCredit.this,DirectDealerOutstandingCFormCreditResult.class);
     		intent.putExtra("Type_C_Form", "OD");
     		startActivity(intent);
		
    
		break;
   case R.id.button3:
	   
	  
    	intent= new Intent(DealerOutstandingCFormCredit.this,DirectDealerOutstandingCFormCreditResult.class);
   		intent.putExtra("Type_C_Form", "PCF");
   		startActivity(intent);
	
	   
	    
        break;
   case R.id.button4:
	   
	
    	intent= new Intent(DealerOutstandingCFormCredit.this,DirectDealerOutstandingCFormCreditResult.class);
   		intent.putExtra("Type_C_Form", "ODD");
   		startActivity(intent);
		
        break;

	default:
		break;
	}
}
	private void DealerOutstandingSummary(final String dealerId) {
		// TODO Auto-generated method stub
		
				GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
					@Override
					public void processResponse(Object response) {
						if(response!=null){

							ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getDealerOutstanding_Dealer_Summary(response.toString());
							if(itemQuantityValueList!=null ){
								if (itemQuantityValueList.size()!=0) {
							
							    creditLimit.setText(itemQuantityValueList.get(0).get(Constants.CreditLimit ));
							    lockReason.setText(itemQuantityValueList.get(0).get(Constants.LockReason ));
								etOutStanding.setText(itemQuantityValueList.get(0).get(Constants.OutStanding ));
							    et_OverDue.setText(itemQuantityValueList.get(0).get(Constants.OverDue ));
								et_od30Due.setText(itemQuantityValueList.get(0).get(Constants.OD_30_Days));
								et_pendiCForm.setText(itemQuantityValueList.get(0).get(Constants.Pending_CForms));
							    	
									
								}
								

							}else {
								Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

							}

						}else if(response==null){
							Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
						}
					}
				});
				
				LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
				SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {       
			  request.put("DealerID", dealerId);
			  request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			  request.put(Constants.password,passworddetails.getString("password",""));


				} catch (Exception e) {
					e.printStackTrace();
				}

				String ipAddress=getResources().getString(R.string.ipaddress);
				String webService =getResources().getString(R.string.Webservice_mis_android);
				String nameSpace=getResources().getString(R.string.nameSpace);
				String methodName="DealerOutstandingSummary";
				String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
				dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
				dataFromNetwork.sendData(request);
				dataFromNetwork.execute();
			
		
		
	}

}
