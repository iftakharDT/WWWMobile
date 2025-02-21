package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DirectDealerGitGoodAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DealerGoodGITReciept;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DirectDealerGitGoodReciept extends BaseActivity{
	String ProductID;
	    int id;
		private ListView mlistView;
	    private DirectDealerGitGoodAdapter adapter;
	    private DealerGoodGITReciept saledata;
	    DealerGoodGITReciept requestBean=new DealerGoodGITReciept();
	    private TextView dataFor_InvNo,Date,Amount,code,ReceivedQty,DispatchedQty ;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.direct_dealer_gitgood_reciept);
		mlistView = (ListView)findViewById(R.id.listView1);
		mlistView.setOnTouchListener(listTouchListener);
		registerForContextMenu(mlistView);
	
		dealerGoodsReceive_Master_Android();
		
	}
	
	
	private void dealerGoodsReceive_Master_Android() {
		// TODO Auto-generated method stub
		
		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<DealerGoodGITReciept> salelist = FetchingdataParser.getDealeGood_GIt_Recieved(result.toString());
					if(salelist==null){
						if (result.toString().equalsIgnoreCase("{ 'Root': { 'Invoice': ] }}")) {
							AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
							errordialog.setTitle("Error!");
							errordialog.setMessage("No Data Found");
							errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();	
								}
							});
							AlertDialog dialog = errordialog.create();
							dialog.show();
						}
						else {
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
						}
						
					}else{
						if(salelist.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							
							adapter  =new DirectDealerGitGoodAdapter(getCurrentContext(), R.layout.direct_dealer_gitgood_reciept_single, salelist);
							mlistView.setAdapter(adapter);
						
						}
					}

				}

			}

		});
		 
		// Function DealerGoodsReceive_Master_Android(DealerID As String, ByVal UserID As String, ByVal Password As String) As String

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			//Constants.contactTypeIDForDealer
			requestdata.put(Constants.DEALERID_1, passworddetails.getString(Constants.DEALERID, ""));
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerGoodsReceive_Master_Android";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	
		
	
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = mlistView.getAdapter().getItemId(info.position);
		int i = (int) id; 
		menu.setHeaderTitle("Please select");
		menu.add(i, 0, 1, "Details and Received");
	
		super.onCreateContextMenu(menu, v, menuInfo);


	}

	
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case 0:

			id = item.getItemId();
			LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view  = inflator.inflate(R.layout.direct_dealer_gitgood_reciept_details, null);
          

		    adapter =  (DirectDealerGitGoodAdapter) mlistView.getAdapter();
			saledata = adapter.getItem(id);
			
			int InvoiceID= Integer.parseInt(saledata.getInvoiceID());
			int DispatchID= Integer.parseInt(saledata.getDispatchID());
		
			code = (TextView)view.findViewById(R.id.textView10);
			ReceivedQty = (TextView)view.findViewById(R.id.textView11);
			
			DealerGoodsReceive_Details_Android( InvoiceID ,DispatchID);
			
			
			AlertDialog.Builder saleDailog = new AlertDialog.Builder(getCurrentContext());

			saleDailog.setView(view);
			saleDailog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String jsondata=null;
					try{
					jsondata = getJsonStringForManualOrder(saledata);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					 
				   SaveGoodsReceived(jsondata);
				}
	
			});
           saleDailog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
			saleDailog.create().show();
			
			break;
			
	

		default:
			break;
		}
		return super.onContextItemSelected(item);

	}
	
	



	private void SaveGoodsReceived(String jsondata) {
		// TODO Auto-generated method stub
		
		

		// TODO Auto-generated method stub
		
		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getDealeGood_GIt_Recieved_Details(result.toString());
					if(itemQuantityValueList==null){
						AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
						errordialog.setTitle("Error!");
						errordialog.setMessage(result.toString());
						errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								dealerGoodsReceive_Master_Android();
							}
						});
						AlertDialog dialog = errordialog.create();
						dialog.show();
					}else{
						if(itemQuantityValueList.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
							
						}else{
							
					    	
						}
					}

				}

			}

		});
		 
		// 
/*
	Public Function SaveGoodsReceived(ByVal JSONData As String, ByVal UserID As String, ByVal Password As String)
*/
		 

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			requestdata.put("JSONData", jsondata);
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SaveGoodsReceived";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	
		
	
		
	
		
	}


	private void DealerGoodsReceive_Details_Android(int invoiceID,
			int dispatchID) {
		// TODO Auto-generated method stub
		
		

		// TODO Auto-generated method stub
		
		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getDealeGood_GIt_Recieved_Details(result.toString());
					if(itemQuantityValueList==null){
						AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
						if (result.toString().equalsIgnoreCase("anyType{}")) {
							errordialog.setTitle("Success!");
							errordialog.setMessage("Data Uploaded");	
						}
						else {
							errordialog.setTitle("Error!");
							errordialog.setMessage(result.toString());
						}
						
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
						if(itemQuantityValueList.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
							
						}else{
						     ProductID=itemQuantityValueList.get(0).get(Constants.ProductID);
						//	dataFor_InvNo.setText(itemQuantityValueList.get(0).get(Constants.InvoiceNo));
							code.setText(itemQuantityValueList.get(0).get(Constants.code));
							ReceivedQty.setText(itemQuantityValueList.get(0).get(Constants.ReceivedQty));
							//DispatchedQty.setText(itemQuantityValueList.get(0).get(Constants.DispatchedQty));
						
					    	
						}
					}

				}

			}

		});
		 
		// 
/*
		 (ByVal InvoiceID As Integer, DispatchID As Integer, ByRef ds As DataSet, ByVal UserID As String, ByVal Password As String) As String
*/
		 

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
		
			requestdata.put("InvoiceID", invoiceID);
			requestdata.put("DispatchID", dispatchID);
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerGoodsReceive_Details_Android";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	
		
	
		
	
		
	}

	private String getJsonStringForManualOrder(DealerGoodGITReciept requestBean) {
		
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		String DealerID=passworddetails.getString(Constants.DEALERID,"");
		/*DealerID
		ProductID --- InvoiceNo
		Quantity ---InvQuantity
		DispatchID ---
		InvoiceID --*/

		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");

			dataString.append("{");
			dataString.append("'");
			dataString.append("DealerID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(DealerID);
			dataString.append("'");
			dataString.append(",");
			
			
			
			dataString.append("'");
			dataString.append("ProductID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(ProductID);
			dataString.append("'");
			dataString.append(",");
			
			
			dataString.append("'");
			dataString.append("Quantity");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(requestBean.getQuantity());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append("DispatchID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(requestBean.getDispatchID());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append("InvoiceID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(requestBean.getInvoiceID());
			dataString.append("'");
			dataString.append("}");
			
			
		
		
		dataString.append("]");
		Log.i("Server data String ", dataString.toString());

		return dataString.toString();}

	@Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
	super.onClick(v);
	switch (v.getId()) {
	case R.id.recieved:
		
		
		break;

	default:
		break;
	}
	}

}
