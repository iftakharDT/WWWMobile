package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.GoodsReceiveAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.GoodsReceiveInfo;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GoodsReceivedDealerActivity  extends BaseActivity{

	private Spinner spinner_invoice;
	private ListView minvoiceProductListView, madd_productListView;
	private Spinner spinner_product;
	private EditText edttxt_quantity;
	private CheckBox chkbox_addtional;
	private Button btn_save,btn_cancle,btn_additem;

	private static final int EXTRA_REQUEST_CODE = 12345;
	public static final String EXTRA_DATA = "data";
	private int ItemGroupId;
	private LinearLayout madditionalProduct_Layout;
	GoodsReceiveAdapter adapter ;
	SimpleAdapter addProd_adapter;

	private static final String PRODUCTID = "ProductID";
	private static final String DISPQTY   = "DispatchQty";
	private static final String RECEIVEQTY = "RecQty";
	private static final String DAMAGEQTY  = "DamageQty";
	private static final String DAMAGEDESC = "DamageDes";
	private static final String SALERETURN = "SaleReturn";
	private static final String SHORYQTY = "ShortQty";
	private static final String NETQtY   = "NetQty";

	private static final String ADDTIONALQTY = "AdditionalQty";


	ArrayList<HashMap<String , String>> addProdList = new ArrayList<HashMap<String,String>>();

	public int getItemGroupId() {
		return ItemGroupId;
	}


	public void setItemGroupId(int setItemGroupId) {
		this.ItemGroupId = setItemGroupId;
	}


	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();

		setContentView(R.layout.activity_goods_recieved_dealer);

		spinner_invoice = (Spinner)findViewById(R.id.spinner_invoice);
		spinner_invoice.setOnItemSelectedListener(spinnerselectionListener);

		minvoiceProductListView = (ListView)findViewById(android.R.id.list);
		minvoiceProductListView.setOnTouchListener(listTouchListener);

		madd_productListView = (ListView)findViewById(R.id.listadditionalProduct);
		madd_productListView.setOnTouchListener(listTouchListener);
		registerForContextMenu(minvoiceProductListView);
		registerForContextMenu(madd_productListView);

		spinner_product    = (Spinner)findViewById(R.id.spinner_product);
		spinner_product.setOnItemSelectedListener(spinnerselectionListener);

		edttxt_quantity    = (EditText)findViewById(R.id.editText_recievedQty);
		chkbox_addtional  = (CheckBox)findViewById(R.id.checkBox_additprodreceived);
		chkbox_addtional.setOnCheckedChangeListener(this);

		btn_save          = (Button)findViewById(R.id.save);
		btn_save.setOnClickListener(this);

		btn_cancle       = (Button)findViewById(R.id.cancel);
		btn_cancle.setOnClickListener(this);

		btn_additem  = (Button)findViewById(R.id.additem);
		btn_additem.setOnClickListener(this);

		madditionalProduct_Layout = (LinearLayout)findViewById(R.id.additionalProduct_layout);
		setCurrentContext(this);
		if(isOnline()){
			getProductList();
			getDealerInvoiceList();
		}else{
			Util.showToast(getCurrentContext(),getStringFromResource(R.string.error7), Toast.LENGTH_LONG).show();
		}
	}


	private void getDealerInvoiceList(){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Dealer's Invoice...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<HashMap<String, String>> invoiceList = FetchingdataParser.getGoodsInvoices(result.toString());
					if(invoiceList==null){
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
						// Populate dealer invoice List
						SimpleAdapter adapter = new SimpleAdapter(getCurrentContext(), invoiceList, android.R.layout.simple_spinner_item, new String[]{Constants.InvoiceNo}, new int[]{android.R.id.text1});
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinner_invoice.setAdapter(adapter);
					}

				}
			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			//
			requestdata.put(Constants.dealerid, passworddetails.getString(Constants.DEALERID,""));
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetInvoicesFromGIT";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	/**
	 * Get Invoice Details
	 * @param invoiceId
	 */
	private void getDealerInvoiceProductDetails(String invoiceId){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Dealer's Invoice Details...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<GoodsReceiveInfo> goodsList = FetchingdataParser.getGoodsInvoicesDetailList(result.toString());
					if(goodsList==null){
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
						// Populate dealer invoice List
						adapter = new GoodsReceiveAdapter(getCurrentContext(), R.layout.goods_dealer_cell, goodsList);
						minvoiceProductListView.setAdapter(adapter);
					}

				}
			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			//
			requestdata.put(Constants.invoiceId, invoiceId);
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductDetailsAgainstInvoice";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	OnItemSelectedListener spinnerselectionListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.spinner_invoice:

				if(arg2!=0){
					// Get the list of products for that invoices.
					@SuppressWarnings("unchecked")
                    HashMap<String, String> dataMap = (HashMap<String, String>) arg0.getSelectedItem();
					String id = dataMap.get(Constants.ID);
					getDealerInvoiceProductDetails(id);
				}
				break;

			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.save:
			// Upload data to server
			if(validation()){

				try {
					
					String jsondata1 = getOutPutJson1(adapter.getInfoList());
					String jsondata2 = getOutPutJson2(addProdList);
					@SuppressWarnings("unchecked")
                    HashMap<String, String> invoicemap  = (HashMap<String, String>) spinner_invoice.getSelectedItem();
					String invoiceid = invoicemap.get(Constants.ID);
					
					uploadDealerGoods(jsondata1, jsondata2, invoiceid);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				
			}
			break;

		case R.id.cancel:
			finish();
			break;
		case R.id.additem:
			// add Item to listView;
			if(productvalidation()){

				HashMap<String, String> data = new HashMap<String, String>();
				@SuppressWarnings("unchecked")
                HashMap<String, String> product = (HashMap<String, String>) spinner_product.getSelectedItem();
				data.put(Constants.PRODUCTNAME, product.get(Constants.PRODUCTNAME));
				data.put(Constants.quantity, edttxt_quantity.getText().toString());
				data.put(Constants.ID, product.get(Constants.ID));
				addProdList.add(data);

				if(addProd_adapter==null){
					addProd_adapter = new SimpleAdapter(getCurrentContext(), addProdList, R.layout.additional_product_cell, new String[]{Constants.PRODUCTNAME
						,Constants.quantity}, new int[]{R.id.position1,R.id.position2});
					madd_productListView.setAdapter(addProd_adapter);
				}else{
					addProd_adapter.notifyDataSetChanged();
				}

				edttxt_quantity.setText("");

			}
		default:
			break;
		}
	};

	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {

		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Please select");

		switch (v.getId()) {
		case android.R.id.list:

			long id = minvoiceProductListView.getAdapter().getItemId(info.position);
			int i = (int) id; 
			menu.add(i, 0, 1, "Edit");
			break;

		case R.id.listadditionalProduct:

			long id1 = madd_productListView.getAdapter().getItemId(info.position);
			int j = (int) id1; 
			menu.add(j, 1, 1, "Delete");
			break;
		default:
			break;
		}


		super.onCreateContextMenu(menu, v, menuInfo);

	};

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			// start anothor activity to edit.
			GoodsReceiveInfo data = (GoodsReceiveInfo) minvoiceProductListView.getAdapter().getItem(item.getGroupId());


			if(data!=null){
				setItemGroupId(item.getGroupId());
				Bundle bundle = new Bundle();
				bundle.putString(Constants.damageQty, String.valueOf(data.getDamageQty()));
				bundle.putString(Constants.salereturn, String.valueOf(data.getSalereturn()));
				bundle.putString(Constants.descOfDamage, data.getDiscOfDamage());
				bundle.putString(Constants.shortQty, String.valueOf(data.getShortQty()));
				Intent intent  = new Intent(getCurrentContext(),EditGoodsReceivedActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, EXTRA_REQUEST_CODE);
			}

			break;

		case 1:

			addProdList.remove(item.getGroupId());
			addProd_adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return  true;
	};


	@Override
	public boolean validation() {
		// TODO Auto-generated method stub

		boolean flag = true;
		String errString  = getStringFromResource(R.string.selectinvoice);
		if(spinner_invoice.getSelectedItemPosition()==0){
			flag = false;	
		}
		if(!flag){
			Toast.makeText(getCurrentContext(), errString, Toast.LENGTH_SHORT).show();
		}
		return flag;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode==RESULT_OK && requestCode == EXTRA_REQUEST_CODE){
			if(data!=null){

				GoodsReceiveInfo info = adapter.getItem(getItemGroupId());

				Bundle bundle = data.getExtras();
				//info.setReceiveQty(Integer.parseInt(bundle.getString(Constants.receivedQty)));
				info.setDamageQty(Integer.parseInt(bundle.getString(Constants.damageQty)));
				info.setSalereturn(Integer.parseInt(bundle.getString(Constants.salereturn)));
				info.setDiscOfDamage(bundle.getString(Constants.descOfDamage));
				info.setShortQty(Integer.parseInt(bundle.getString(Constants.shortQty)));
				int netQty = info.getReceiveQty();
				netQty = (netQty-(info.getDamageQty()+info.getSalereturn()+info.getShortQty()));
				info.setNetqty(netQty);
				adapter.getInfoList().remove(getItemGroupId());
				adapter.notifyDataSetChanged();
				adapter.getInfoList().add(getItemGroupId(), info);
				adapter.notifyDataSetChanged();



			}
		}
	}

	/*
	 *  Get Products List.
	 */
	private void getProductList(){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<HashMap<String, String >> productList = FetchingdataParser.getDealerGoodsProductsList(result.toString());
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
						if(productList.size()==1){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							// set adapter for autocomplete textview.

							SimpleAdapter dataadapter  = new SimpleAdapter(getCurrentContext(), productList, android.R.layout.simple_spinner_item, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
							dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							spinner_product.setAdapter(dataadapter);
						}
					}

				}



			}


		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			//Constants.contactTypeIDForDealer
			requestdata.put(Constants.CONTACTID, passworddetails.getString("dealerid",""));
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


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		if(isChecked){
			// Make additional product layout visible
			madditionalProduct_Layout.setVisibility(View.VISIBLE);
		}else{
			madditionalProduct_Layout.setVisibility(View.GONE);
		}
	}

	/**
	 * Check item being added are correct or Not.
	 * @return
	 */
	private boolean productvalidation(){

		boolean flag = true;
		String errMsg  = getStringFromResource(R.string.error3);
		if(spinner_product.getSelectedItemPosition()==0){
			flag = false;
			errMsg.concat(" " +getStringFromResource(R.string.productname));
		}
		if(TextUtils.isEmpty(edttxt_quantity.getText().toString())){
			flag = false;
			edttxt_quantity.setError(getStringFromResource(R.string.quantity));
		}
		if(!flag){
			Util.showToast(getCurrentContext(), errMsg, Toast.LENGTH_SHORT).show();
		}
		return flag;
	}


	/**
	 * Get JSON for Invoices.
	 * @param goodsList
	 * @return
	 */
	private String getOutPutJson1(ArrayList<GoodsReceiveInfo> goodsList){

		
		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");


		for(int i=0;i<goodsList.size();i++){
			GoodsReceiveInfo order = goodsList.get(i);
			dataString.append("{");
			dataString.append("'");
			dataString.append(PRODUCTID);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getId());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(DISPQTY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getDiscpatchQty());
			dataString.append("'");
			dataString.append(",");

			dataString.append("'");
			dataString.append(RECEIVEQTY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getReceiveQty());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(DAMAGEQTY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getDamageQty());
			dataString.append("'");
			dataString.append(",");

			dataString.append("'");
			dataString.append(DAMAGEDESC);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			if(order.getDiscOfDamage()==null ){
				dataString.append("0");
			}else{
				dataString.append(order.getDiscOfDamage());
			}
			
			dataString.append("'");
			dataString.append(",");
			
			
			dataString.append("'");
			dataString.append(SALERETURN);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getSalereturn());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(SHORYQTY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getShortQty());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(NETQtY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(order.getNetqty());
			dataString.append("'");
			dataString.append(",");
			dataString.append("}");
			dataString.append(",");
			
			
		}
		dataString.append("]");
		Log.i("Goods Receive String", dataString.toString());

		return dataString.toString();
	}

	private String getOutPutJson2(ArrayList<HashMap<String, String>> goodsList){

		
		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");


		for(int i=0;i<goodsList.size();i++){
			HashMap<String, String> orderMap = goodsList.get(i);
			dataString.append("{");
			dataString.append("'");
			dataString.append(PRODUCTID);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(orderMap.get(Constants.ID));
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append(ADDTIONALQTY);
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(orderMap.get(Constants.quantity));
			dataString.append("'");
			dataString.append(",");
			dataString.append("}");
			dataString.append(",");
		}
		
		dataString.append("]");
		Log.i("Goods Receive String", dataString.toString());

		return dataString.toString();
	}

	/**
	 *  Upload dealer Goods.
	 * @param json1
	 * @param json2
	 * @param invoiceid
	 */
	
	private void uploadDealerGoods(String json1, String json2, String invoiceid){

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
		SharedPreferences passworddetails=Util.getSharedPreferences(getCurrentContext(), Constants.PREF_NAME);
		try {
			requestdata.put("JSONData", json1);
			requestdata.put("JSONData1", json2);
			requestdata.put("InvoiceID", invoiceid);
			requestdata.put(Constants.dealerid, passworddetails.getString(Constants.DEALERID, ""));
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
}
