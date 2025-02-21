package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DealerManualAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DealerManualOrder;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DealerMannualOrderActivity extends BaseActivity {
    
	private Spinner mdealerspinner;
	private EditText edttxt_qntity,edttxt_mobNo,edttxt_city;
	private AutoCompleteTextView edttxt_itemcode,edttxt_itemName;
	private ListView mlistView;
	private Button btn_additem,btn_placeOrder,btn_cancle;
	private TextView txt_totalAmont,txt_discount,txt_netAmont;
	private LinearLayout mshowdealer;
	private double totalAmount = 0;
	String dealerId;
	private static final String PRODUCTCODE ="Product Code";
	private static final String PRODUCTNAME ="Product Name";
	private static final String QUANTITY    = "Qty";
	private static final String UNITPRICE = "unit Price";
	private static final String PRODUCTID = "Product ID";
	
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	String contacttypeId ;
	private DealerManualAdapter mManualAdapter;
	private HashMap<String , String> dataMap;
	private ArrayList<DealerManualOrder> orderList = new ArrayList<DealerManualOrder>();
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealer_manual_order);
		setCurrentContext(this);
		
		mdealerspinner = (Spinner)findViewById(R.id.spinner_dealername);
		mdealerspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2!=0){
					@SuppressWarnings("unchecked")
                    HashMap<String , String > data = (HashMap<String, String>) arg0.getItemAtPosition(arg2);
					if(getDealerId()==null){
						setDealerId(data.get(Constants.ID));
						getProductList(getDealerId());
					}else{
						String id = data.get(Constants.ID);
						if(!id.equals(getDealerId()) && orderList.size()>0){
							showDealerChangedDialog(id);
						}else{
							setDealerId(id);
							getProductList(id);
						}
					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		edttxt_itemcode = (AutoCompleteTextView)findViewById(R.id.searchcode);

		edttxt_itemcode.setOnItemClickListener(new OnItemClickListener() {


			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
				// TODO Auto-generated method stub
				dataMap = (HashMap<String, String>) arg0.getItemAtPosition(arg2);
				String itemName = dataMap.get(Constants.PRODUCTNAME);
				String itemcode = dataMap.get(Constants.CODE);
				edttxt_itemName.setText(itemName);
				edttxt_itemcode.setText(itemcode);
			}
		});

		edttxt_itemName = (AutoCompleteTextView)findViewById(R.id.searchname);
		edttxt_qntity   = (EditText)findViewById(R.id.quantity);
		edttxt_mobNo    = (EditText)findViewById(R.id.edttxt_mobileno);
		edttxt_city     = (EditText)findViewById(R.id.edttxt_city);
		btn_additem    = (Button)findViewById(R.id.additem);
		btn_additem.setOnClickListener(this);
		btn_placeOrder  = (Button)findViewById(R.id.save);
		btn_placeOrder.setOnClickListener(this);
		btn_cancle = (Button)findViewById(R.id.cancel);
		btn_cancle.setOnClickListener(this);
		mlistView = (ListView)findViewById(android.R.id.list);
		mlistView.setOnTouchListener(listTouchListener);
		registerForContextMenu(mlistView);
		mshowdealer = (LinearLayout)findViewById(R.id.showdealer);

		SharedPreferences pref = getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		contacttypeId = pref.getString("contacttypeid", "");
		String dealerId=pref.getString(Constants.DEALERID,"");
		if(contacttypeId.equals("14")){
			mshowdealer.setVisibility(View.GONE);
			getProductList(dealerId);
		}else{
			mshowdealer.setVisibility(View.VISIBLE);
			getDealerNamesList();
		}
		txt_totalAmont = (TextView)findViewById(R.id.txtview_totalAmount);
		txt_discount   = (TextView)findViewById(R.id.txtview_discount);
		txt_netAmont   = (TextView)findViewById(R.id.txtview_netamount);

	}
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		if(id==R.id.additem){
			if(validation()){
				// Capture Data

				DealerManualOrder order = CaptureData(dataMap);
				orderList.add(order);
				updateItemAmount(order);
				clearScreen();

				if(mManualAdapter==null){
					mManualAdapter = new DealerManualAdapter(getCurrentContext(), R.layout.dealer_product_cell, orderList);
					mlistView.setAdapter(mManualAdapter);
				}else{
					mManualAdapter.notifyDataSetChanged();
				}
				Toast.makeText(getCurrentContext(), "Item added successfully!", Toast.LENGTH_LONG).show();
			}

		}

		if(id==R.id.save){

			String jsondata  = "";
		try{
			jsondata = getJsonStringForManualOrder(orderList);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		uploadManualOrder(getDealerId(), jsondata);
		}
		if(id==R.id.cancel){
			finish();
		}
	}

	public boolean validation(){

		boolean isValid = true;
		String errMsg = getResources().getString(R.string.error3);
		if(!contacttypeId.equals("14")){
			if(mdealerspinner.getSelectedItemPosition()==0|| mdealerspinner.getSelectedItemPosition()== AdapterView.INVALID_POSITION){

				errMsg = errMsg.concat(" " +"Please select Dealer Name");
				isValid = false;
			}
		}

		if(edttxt_itemcode.getText().toString().length()==0){
			edttxt_itemcode.setError(getResources().getString(R.string.itemcode));
			//errMsg = errMsg.concat(" " +);
			isValid = false;
		}
		if(edttxt_itemName.getText().toString().length()==0){
			edttxt_itemName.setError(getResources().getString(R.string.itemname));
			//errMsg = errMsg.concat(" " +);
			isValid = false;
		}
		if(edttxt_qntity.getText().toString().length()==0){
			edttxt_qntity.setError(getResources().getString(R.string.quantity));
			//errMsg = errMsg.concat(" " +);
			isValid = false;
		}

		if(edttxt_mobNo.getText().toString().length()==0||edttxt_mobNo.getText().toString().length()<10){
			edttxt_mobNo.setError(getResources().getString(R.string.mobilenumber));

			isValid = false;
		}
		if(edttxt_city.getText().toString().length()==0){
			edttxt_city.setError(getResources().getString(R.string.city));
			//errMsg = errMsg.concat(" " +);
			isValid = false;
		}
		if(!isValid){
			Toast.makeText(getCurrentContext(), errMsg, Toast.LENGTH_LONG).show();
		}
		return isValid;
	}

	/**
	 * Get Dealer's Name List.
	 */
	private void getDealerNamesList(){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Dealer's Name...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<HashMap<String, String >> productList = FetchingdataParser.getDealerNamesList(result.toString());
					if(productList==null){
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
						if(productList.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							// set adapter for autocomplete textview.

							SimpleAdapter dataadapter  = new SimpleAdapter(getCurrentContext(), productList, android.R.layout.simple_spinner_item, new String[]{Constants.DEALERNAME}, new int[]{android.R.id.text1});
							dataadapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							mdealerspinner.setAdapter(dataadapter);
						}
					}

				}

			}


		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			//Constants.contactTypeIDForDealer
			//requestdata.put(Constants.CONTACTID, passworddetails.getString("dealerid",""));
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerList";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}


	/**
	 * Get Products List for selected Dealer .
	 */
	 private void getProductList(String dealerId){

		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<HashMap<String, String >> productList = FetchingdataParser.getDealerProductList(result.toString());
					if(productList==null){
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
						if(productList.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							// set adapter for autocomplete textview.

							SimpleAdapter dataadapter  = new SimpleAdapter(getCurrentContext(), productList, android.R.layout.simple_list_item_1, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
							edttxt_itemcode.setAdapter(dataadapter);
						}
					}

				}

			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			//Constants.contactTypeIDForDealer
			requestdata.put(Constants.CONTACTID, dealerId);
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductList";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	
	 
	 
	 private DealerManualOrder CaptureData(HashMap<String, String> productMap){

		DealerManualOrder order = new DealerManualOrder();
		order.setDealerName(edttxt_itemcode.getText().toString());
		order.setProductCode(edttxt_itemcode.getText().toString());
		order.setProductNAme(edttxt_itemName.getText().toString());
		order.setMobileno(edttxt_mobNo.getText().toString());
		order.setCity(edttxt_city.getText().toString());
		order.setQuantity(edttxt_qntity.getText().toString());
		order.setUnitPrice(Double.parseDouble(productMap.get(Constants.UNITPRICE)));
		order.setDiscountpercentage(Double.parseDouble(productMap.get(Constants.DISCOUNTPERCENTAGE)));
		order.setProductId(productMap.get(Constants.ID));
		order.setDealerID(getDealerId());
		return order;
	}

	private OnTouchListener listTouchListener = new OnTouchListener() {


		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				// Disallow ScrollView to intercept touch events.
				v.getParent().requestDisallowInterceptTouchEvent(true);
				break;

			case MotionEvent.ACTION_UP:
				// Allow ScrollView to intercept touch events.
				v.getParent().requestDisallowInterceptTouchEvent(false);
				break;
			}

			// Handle ListView touch events.
			v.onTouchEvent(event);
			return true;
		}
	};

	public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {

		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = mlistView.getAdapter().getItemId(info.position);
		int i = (int) id; 
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(i, 0, 1, "Delete");
	};

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case 0:

			int selectedindex  = item.getGroupId();
			orderList.remove(selectedindex);
			DealerManualAdapter adapter =  (DealerManualAdapter) mlistView.getAdapter();
			adapter.notifyDataSetChanged();
			//setTotalAmount(productDetailsList);
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);

	}


	private void updateItemAmount(DealerManualOrder order){

		try{
			double quantity = Double.parseDouble(order.getQuantity());
			
			double unitprice = order.getUnitPrice();
			double totalAmount = quantity* unitprice;
			this.totalAmount =this.totalAmount +totalAmount; 
			txt_totalAmont.setText(String.valueOf(this.totalAmount));
			double discount = order.getDiscountpercentage();
			double dicAmount = (this.totalAmount*discount)/100;
			txt_discount.setText(String.valueOf(dicAmount));
			txt_netAmont.setText(String.valueOf(this.totalAmount-dicAmount));

		}catch(Exception ex){
			ex.printStackTrace();

		}

	}

	 private void clearScreen(){

		edttxt_itemcode.setText("");
		edttxt_itemName.setText("");
		edttxt_city.setText("");
		edttxt_mobNo.setText("");
		edttxt_qntity.setText("");
	}

	/**
	 *  Dialog used for notifying user that 
	 *  Dealer is going to change.
	 */
	private void showDealerChangedDialog(final String id){

		AlertDialog.Builder dialog = new AlertDialog.Builder(getCurrentContext());
		dialog.setTitle("Dealer Changed!");
		dialog.setMessage("By Changing dealer without placing the order \npreviously added items will be lost.");
		dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				orderList.clear();
				((DealerManualAdapter) mlistView.getAdapter()).notifyDataSetChanged();
				getProductList(id);
				setDealerId(id);
			}

		});
		dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				dialog.dismiss();
			}
		});
		dialog.show();
	}


	/**
	 * Json String containing coupon information. 
	 * @param eventid
	 * @param coupontextList
	 * @return json SAtructured String .
	 */
 	private String getJsonStringForManualOrder(ArrayList<DealerManualOrder> list){



		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");


		for(int i=0;i<list.size();i++){
			DealerManualOrder order = list.get(i);
			dataString.append("{");
			dataString.append("'");
			dataString.append(PRODUCTCODE);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getProductCode());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(PRODUCTNAME);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getProductCode());
			dataString.append("'");
			dataString.append(",");

			dataString.append("'");
			dataString.append(QUANTITY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getQuantity());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(UNITPRICE);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getUnitPrice());
			dataString.append("'");
			dataString.append(",");

			dataString.append("'");
			dataString.append(PRODUCTID);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getProductId());
			dataString.append("'");
			dataString.append(",");
			dataString.append("}");
			dataString.append(",");
		}
		dataString.append("]");
		Log.i("Server data String ", dataString.toString());

		return dataString.toString();
	}

	private void uploadManualOrder(String dealerId, String jsonString){

		  GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, getResources().getString(R.string.uploaddata), new GetDataCallBack() {

			@Override
		  	public void processResponse(Object result) {
				// TODO Auto-generated method stub
				SoapObject responce  = null;
				try{

					responce = (SoapObject)result;
				}catch(Exception ex){
					ex.printStackTrace();
				}
				AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
				if (responce == null) {
					errordialog.setTitle(getResources().getString(R.string.error6));
					if (result != null) {
						errordialog.setMessage(result.toString());
					} else {
						errordialog.setMessage(getResources().getString(R.string.error4));
					}

				} else {
					errordialog.setMessage(getResources().getString(R.string.message5));

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

			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			requestdata.put(Constants.DEALERID_1, dealerId);
			requestdata.put(Constants.totalAmount, txt_totalAmont.getText().toString());
			requestdata.put(Constants.discount, txt_discount.getText().toString());
			requestdata.put(Constants.orderamount, txt_netAmont.getText().toString());
			requestdata.put(Constants.jsonstring, jsonString);
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SaveDealerManualOrder";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

}
