package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DirectDealerOutstandingCFormCreditResult extends BaseActivity{
     ListView mlistView;
     String Type_C_Form;
	    
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealer_outstanding_cform_credit_result);
		setCurrentContext(this);
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences("PASSWORD", getCurrentContext().MODE_PRIVATE);
	    String DealerID =passworddetails.getString("dealerid","");
	    mlistView=(ListView) findViewById(R.id.listView1);
		Intent intent=getIntent();
		Bundle b=intent.getExtras();
		if (b!=null) {
		 Type_C_Form=b.getString("Type_C_Form");
			
			if (Type_C_Form.equalsIgnoreCase("OS")) {
				DealerOutstandingDetail(DealerID,"1","0","0","0");
			}
			else if (Type_C_Form.equalsIgnoreCase("OD")) {
				DealerOutstandingDetail(DealerID,"0","1","0","0");
			}
			else if (Type_C_Form.equalsIgnoreCase("ODD")) {
				  DealerOutstandingDetail(DealerID,"0","0","1","0");
				    
			}
			else if (Type_C_Form.equalsIgnoreCase("PCF")) {
				   DealerOutstandingDetail(DealerID,"0","0","0","1");
			}
		        
			
		}
		
	}



	private void DealerOutstandingDetail(String DealerID, String IsOutStanding,
                                         String IsOverDue, String IsOver_30Days_Due, String IsCForm) {
		// TODO Auto-generated method stub
		
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getDealerOutstandingDetail(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
						//	 etOutStanding,et_OverDue,et_od30Due,et_pendiCForm
						
							SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), itemQuantityValueList, R.layout.am_oustd_cform_credit_signleview,
									new String[]{Constants.BILLNO,Constants.DATE,Constants.AMMOUNT},
									new int[]{R.id.billno,R.id.date,R.id.ammount});
									mlistView.setAdapter(adapter);	
					    	
							
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
	

	  request.put("DealerID", DealerID);
	  request.put("IsOutStanding", IsOutStanding);
	  request.put("IsOverDue", IsOverDue);
	  request.put("IsOver_30Days_Due", IsOver_30Days_Due);
	  request.put("IsCForm", IsCForm);
	  request.put(Constants.username, passworddetails.getString(Constants.ID,""));
	  request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerOutstandingDetail";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	

		
		
	}

}
