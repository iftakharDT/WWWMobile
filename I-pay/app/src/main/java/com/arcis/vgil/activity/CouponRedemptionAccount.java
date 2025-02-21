package com.arcis.vgil.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class CouponRedemptionAccount extends BaseActivity{
	private ListView mListView;
	private Button bt_credit;
	ArrayList<HashMap<String, String>> itemQuantityValueListamount;
	ArrayList<HashMap<String, String>> itemQuantityValueList;
	private TextView tv_cd,cd_debit;
	int debit=0;
	int credit=0;
	AlertDialog dialog;
	ArrayList<HashMap<String, String>> Debit_Credit_List;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.coupn_redemption_acount);
		setCurrentContext(this);
		mListView = (ListView)findViewById(R.id.lv_account);
		bt_credit=(Button) findViewById(R.id.bt_credit);
		tv_cd=(TextView) findViewById(R.id.tv_cd);
		cd_debit=(TextView) findViewById(R.id.cd_debit);
		bt_credit.setOnClickListener(this);
		getCouponRedemptionAccount();
		CouponPendingAmount_MIS();
	}

	private void getCouponRedemptionAccount() {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

			       itemQuantityValueList = FetchingdataParser.getCouponRedeemptionAccount(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							for (int i = 0; i < itemQuantityValueList.size(); i++) {
								
								if (Integer.parseInt(itemQuantityValueList.get(i).get(Constants.Days).toString())<90) {

									SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), itemQuantityValueList, R.layout.coupn_redemption_account_shell,
											new String[]{Constants.TransDate,Constants.RefNo,Constants.Credit,Constants.Debit},
											new int[]{R.id.tv_date,R.id.tv_ref,R.id.tv_credit,R.id.tv_debit});
											mListView.setAdapter(adapter);	
											mListView.setOnItemClickListener(new OnItemClickListener() {

												@Override
												public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
												{
													// TODO Auto-generated method stub
													//Function CouponWiseAccountDetails(AccountHolderID As String, 
													//TransDate As String, BankRefNo As String) As 
													String TransDate =itemQuantityValueList.get(arg2).get(Constants.TransDate);
													String BankRefNo =itemQuantityValueList.get(arg2).get(Constants.RefNo);
												
													
													CouponWiseAccountDetails(TransDate,BankRefNo);
												}

												
											});
								}
								else {
							       LinearLayout ll_greaterninty=(LinearLayout) findViewById(R.id.ll_greaterninty);
							       ll_greaterninty.setVisibility(View.GONE);
									if (itemQuantityValueList.get(i).get(Constants.Debit)!=null&&itemQuantityValueList.get(i).get(Constants.Debit)!="") {
										updateDebitAmount(itemQuantityValueList.get(i).get(Constants.Debit));
									}
									if (itemQuantityValueList.get(i).get(Constants.Credit)!=null&&itemQuantityValueList.get(i).get(Constants.Credit)!="") {
										updateCreditAmount(itemQuantityValueList.get(i).get(Constants.Credit));
										
									}
									
								
									
								}
								
							}
						
						
							
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
        	
	     request.put("ExternalContactID", passworddetails.getString(Constants.USERID,""));
	     
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CouponRedeemptionAccount_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
		
	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		try {
			if (itemQuantityValueListamount.get(0).get(Constants.Amount).equalsIgnoreCase("0")||itemQuantityValueListamount.get(0).get(Constants.Amount).equalsIgnoreCase("0")) {
				Toast.makeText(getCurrentContext(), getResources().getText(R.string.coupon_account), Toast.LENGTH_SHORT).show();
			}
			else {
				CouponPendingPaymentDetails_MIS();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getCurrentContext(), getResources().getText(R.string.coupon_account), Toast.LENGTH_SHORT).show();
		}
		
		
	}
	

	private void CouponPendingPaymentDetails_MIS() {
		// TODO Auto-generated method stub
		

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getCouponPendingPaymentDetails(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							
							   AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
							    // Get the layout inflater
							    LayoutInflater inflater =  ((Activity) getCurrentContext()).getLayoutInflater();
							    View dialogView = inflater.inflate(R.layout.dialouge_coupon_list_details, null);
							    builder.setTitle("Coupon Amount Details");
							    
							    builder.setCancelable(false);
							    builder.setView(dialogView);
							    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								});
							    ListView listView=(ListView) dialogView.findViewById(R.id.listView1);
							    TextView tv_amount=(TextView) dialogView.findViewById(R.id.tv_amount);
					
							    
							    SimpleAdapter dataadapter  = new SimpleAdapter(getCurrentContext(), itemQuantityValueList, R.layout.dialouge_coupon_list_details_shell,
								        		new String[]{Constants.CouponCode,Constants.Amount},
								        		new int[]{R.id.tV_coupon_no,R.id.tV_amount});
									            listView.setAdapter(dataadapter);
									
									            tv_amount.setText(itemQuantityValueListamount.get(0).get(Constants.Amount));
						          
						            
						            AlertDialog alertDialog = builder.create();
						            alertDialog.show();
						
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
        	
	     request.put("ExternalContactID", passworddetails.getString(Constants.USERID,""));
	     
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CouponPendingPaymentDetails_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
		
	
	
		
	}

	private void CouponPendingAmount_MIS() {
		// TODO Auto-generated method stub
		

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					 itemQuantityValueListamount = FetchingdataParser.getCouponPendingAmount(response.toString());
					if(itemQuantityValueListamount!=null ){
						if (itemQuantityValueListamount.size()!=0) {
							
							TextView amount=(TextView) findViewById(R.id.tv_amount);
							amount.setText(itemQuantityValueListamount.get(0).get(Constants.Amount));
							
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
        	
	     request.put("ExternalContactID", passworddetails.getString(Constants.USERID,""));
	     
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="CouponPendingAmount_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
		
	
	
		
	}
	
	private void updateCreditAmount(String credit){

		try{
			int creditBal = Integer.parseInt(credit);
			
			this.credit =this.credit +creditBal; 
			
			//cd_debit
			
			tv_cd.setText(String.valueOf(this.credit));
			

		}catch(Exception ex){
			ex.printStackTrace();

		}
	}
		
		private void updateDebitAmount(String debit){

			try{
				int creditBal = Integer.parseInt(debit);
				
				this.debit =this.debit +creditBal; 
			    cd_debit.setText(String.valueOf(this.debit));
				

			}catch(Exception ex){
				ex.printStackTrace();

			}
	
	
	}
	
		private void CouponWiseAccountDetails(String TransDate, String BankRefNo) {
			// TODO Auto-generated method stub
			


			 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

				@Override
				 public void processResponse(Object result) {
					// TODO Auto-generated method stub
					if(result==null){
						Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
					}else{
						Log.i(" DealerProdect Data", result.toString());
					    Debit_Credit_List = FetchingdataParser.getDebit_Credit_List(result.toString());
						if(Debit_Credit_List==null){
							AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
							errordialog.setTitle("Error!");
							errordialog.setMessage(result.toString());
							errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();	
								}
							});
							AlertDialog dialog = errordialog.create();
							dialog.show();
						}else{
							  if(Debit_Credit_List==null){
									AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
									errordialog.setTitle("Error!");
									errordialog.setMessage("Data is Not available");
									errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();	
										}
									});
									AlertDialog dialog = errordialog.create();
									dialog.show();
								}else{
									if(Debit_Credit_List.size()==0){
										Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
									}else{
										
										LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
										View view  = inflator.inflate(R.layout.debit_credit_detail__dialog, null);
										ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
							            AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
							            builder.setView(view);
                                     
							            SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), Debit_Credit_List, R.layout.debit_credit_detail_dialog_shell,
												new String[]{Constants.CouponCode,Constants.Amount,Constants.Trans},
												new int[]{R.id.tv_coupon_code,R.id.tv_amount,R.id.tv_trans_type});
							                     list_part_no.setAdapter(adapter);	
								            builder.setNegativeButton(
								                    "cancel",
								                    new DialogInterface.OnClickListener() {
								                        @Override
								                        public void onClick(DialogInterface dialog, int which) {
								                            dialog.dismiss();
								                        }
								                    });
								            
								            dialog = builder.show();
									
									}
								}
					        
					           
						}

					}

				}

			});

			LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
			SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
			try {
				//Constants.contactTypeIDForDealer
			//	Function CouponWiseAccountDetails(AccountHolderID As String, TransDate As String, BankRefNo As String) As String
  
				requestdata.put("AccountHolderID", passworddetails.getString(Constants.USERID,""));
		        requestdata.put("TransDate", TransDate);
				requestdata.put("BankRefNo",BankRefNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String ipAddress=getResources().getString(R.string.ipaddress);
			String webService =getResources().getString(R.string.Webservice_mis_android);
			String nameSpace=getResources().getString(R.string.nameSpace);
			String methodName="CouponWiseAccountDetails";
			String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
			datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
			datafromnetwork.sendData(requestdata);
			datafromnetwork.execute();
		
			
		}
}
