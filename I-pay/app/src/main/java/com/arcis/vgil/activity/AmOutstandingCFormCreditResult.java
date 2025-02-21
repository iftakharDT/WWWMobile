package com.arcis.vgil.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AmOutstandingCFormCreditResult extends BaseActivity{
     private ListView mlistView;
	 private Button btn_OutStanding,btn_current,btn_OD_UPTO_30,btn_OD_Less_30,btn_OD_less_60;
	 String DealerID;
	 String selectedDealerName;
	 private EditText etOutStanding,et_current,et_OD_UPTO_30,et_OD_Less_30,et_OD_less_60;
	 private TextView header;
	 Double etOutStandingValue,et_OverDueValue,et_od30DueValue,et_pendiCFormValue;
	    
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.am_outstanding_cform_credit_result);
		setCurrentContext(this);
		header=(TextView) findViewById(R.id.header);
		Intent intent=getIntent();
		Bundle b=intent.getExtras();
		if (b!=null) {
		 DealerID=b.getString("DealerID");
		 selectedDealerName=b.getString("selectedDealerName");
		 header.setText(selectedDealerName);
		}
		
		etOutStanding=(EditText) findViewById(R.id.editText1);
		et_current=(EditText) findViewById(R.id.editText2);
		et_OD_UPTO_30=(EditText) findViewById(R.id.editText3);
		et_OD_Less_30=(EditText) findViewById(R.id.editText4);
		et_OD_less_60=(EditText) findViewById(R.id.editText5);
	
		btn_OutStanding=(Button) findViewById(R.id.button1);
		btn_OutStanding.setOnClickListener(this);
		
		btn_current=(Button) findViewById(R.id.button2);
		btn_current.setOnClickListener(this);
		
		
		btn_OD_UPTO_30=(Button) findViewById(R.id.button3);
		btn_OD_UPTO_30.setOnClickListener(this);
		
		btn_OD_Less_30=(Button) findViewById(R.id.button4);
		btn_OD_Less_30.setOnClickListener(this);
		
		btn_OD_less_60=(Button) findViewById(R.id.button5);
		btn_OD_less_60.setOnClickListener(this);
		
		mlistView=(ListView) findViewById(R.id.listView1);
		DealerOutstandingSummary(DealerID);
		
		
	}

	private void DealerOutstandingSummary(final String dealerId) {
		// TODO Auto-generated method stub
		
				GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
					@Override
					public void processResponse(Object response) {
						if(response!=null){

							ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getDealerOutstandingSummary(response.toString());
							if(itemQuantityValueList!=null ){
								if (itemQuantityValueList.size()!=0) {
								    etOutStanding.setText(itemQuantityValueList.get(0).get(Constants.TotalOutstanding));
									et_current.setText(itemQuantityValueList.get(0).get(Constants.Current ));
									et_OD_UPTO_30.setText(itemQuantityValueList.get(0).get(Constants.OD_UPTO_30));
									et_OD_Less_30.setText(itemQuantityValueList.get(0).get(Constants.OD_30));
									et_OD_less_60.setText(itemQuantityValueList.get(0).get(Constants.OD_60));
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button1:
			DealerOutstandingDetail(DealerID,"TOTAL OUTSTANIDNG");
			break;
        case R.id.button2:
        	DealerOutstandingDetail(DealerID,"CURRENT");
			break;
			case R.id.button3:
          	  DealerOutstandingDetail(DealerID,"OD UPTO 30");
     		 break;
       case R.id.button4:
    	    DealerOutstandingDetail(DealerID,"OD>30");
	        break;
	        
       case R.id.button5:
   	    DealerOutstandingDetail(DealerID,"OD>60");
	        break;
		default:
			break;
		}
	}

	private void DealerOutstandingDetail(String DealerID, String Type) {
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
	
//	Function DealerOutstandingDetail(ByVal DealerID As String, ByVal IsOutStanding As String, 
		//ByVal IsOverDue As String, ByVal IsOver_30Days_Due As String, ByVal UserID As String, ByVal Password As String) As String
     
	  request.put("DealerID", DealerID);
	  request.put("Type", Type);
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
	



		// TODO Auto-generated method stub
		
	}

}
