package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DealerMannualSaleAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DealerDealerSale;
import com.arcis.vgil.parser.FetchingdataParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DirectDealerMannualOrderThree extends BaseActivity{
	
	private ListView mlistView;
   // private	Button placeOrder,cancel;
    private DealerMannualSaleAdapter adapter;
    DealerDealerSale saledata;
    private EditText edt_textPArtNo;
    private Button btn_cancel;
    private TextView txt_totalAmont,txt_discount,txt_netAmont;
	private Button btn_save;
	String dealerId;
    String contacttypeId ;
    
    private HashMap<String , String> dataMap;
    private static double temtotalAmount; 
    private static double temdicAmount;
    ArrayList<DealerDealerSale> requestBean=new ArrayList<DealerDealerSale>();
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.direct_dealermannual_order_three);
		mlistView = (ListView)findViewById(R.id.listView1);
		mlistView.setOnTouchListener(listTouchListener);
		registerForContextMenu(mlistView);
		edt_textPArtNo = (EditText)findViewById(R.id.partno);
		edt_textPArtNo.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

				if(DirectDealerMannualOrderThree.this.adapter!=null)
					DirectDealerMannualOrderThree.this.adapter.getFilter().filter(arg0);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		btn_cancel = (Button)findViewById(R.id.cancel);
		btn_cancel.setOnClickListener(this);
        btn_save = (Button)findViewById(R.id.save);
        btn_save.setOnClickListener(this);
		
		try {
			SharedPreferences pref = getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
			contacttypeId = pref.getString("contacttypeid", "");
			 dealerId=pref.getString(Constants.DEALERID,"");
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		
		
		if(contacttypeId.equals("14")){
			//mshowdealer.setVisibility(View.GONE);
			getProductList(dealerId);
		}else{
			//mshowdealer.setVisibility(View.VISIBLE);
			//getDealerNamesList();
		}
		txt_totalAmont = (TextView)findViewById(R.id.txtview_totalAmount);
		txt_discount   = (TextView)findViewById(R.id.txtview_discount);
		txt_netAmont   = (TextView)findViewById(R.id.txtview_netamount);

	}
	private void getProductList(String dealerId) {
		// TODO Auto-generated method stub
		


		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
					ArrayList<DealerDealerSale> salelist = FetchingdataParser.getDealeMannualrSale(result.toString());
					if(salelist==null){
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
						if(salelist.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							
							adapter  =new DealerMannualSaleAdapter(getCurrentContext(), R.layout.dealermannual_order_single_two, salelist);
							mlistView.setAdapter(adapter);
						
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = mlistView.getAdapter().getItemId(info.position);
		int i = (int) id; 
		menu.setHeaderTitle("Please select");
		menu.add(0, i, 1, "Sale item");
		super.onCreateContextMenu(menu, v, menuInfo);


	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getGroupId()) {
		case 0:

			final int id = item.getItemId();
			LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view  = inflator.inflate(R.layout.order_sale_quantity_layout, null);
          

			final DealerMannualSaleAdapter adapter =  (DealerMannualSaleAdapter) mlistView.getAdapter();
			
			  saledata = adapter.getItem(id);

			EditText text1 = (EditText)view.findViewById(R.id.edttxt_currentstock);
			text1.setText(saledata.getInventory());

			final int currentStock = Integer.parseInt(saledata.getInventory());

			final EditText text_sale = (EditText)view.findViewById(R.id.editText_saleorder);
			AlertDialog.Builder saleDailog = new AlertDialog.Builder(getCurrentContext());

			saleDailog.setView(view);
			saleDailog.setPositiveButton("Save", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					String sale =  text_sale.getText().toString();
					if(sale.length()>0){

						int  saleQty  = Integer.parseInt(sale);
						if(saleQty>currentStock){
							Util.showToast(getCurrentContext(), "Sale quantity can't be greate than stock quantity!", Toast.LENGTH_LONG).show();
						}else{
							saledata.setOrderQty(String.valueOf(saleQty));;
							dialog.dismiss();
							adapter.notifyDataSetChanged();
							updateItemAmount(id);
							requestBean.add(saledata);
						}
					}else{
						Util.showToast(getCurrentContext(), "Please enter correct quantity", Toast.LENGTH_LONG).show();
					}
				}

			});

			saleDailog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub

					arg0.dismiss();
				}
			});

			saleDailog.create().show();
			
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);

	}
	
	protected void updateItemAmount(final int id) {
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
					
					ArrayList<HashMap<String, String>> saleDiscountlist = FetchingdataParser.getDealeMannualrSaleDiscount(result.toString());
					if(saleDiscountlist==null){
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
						if(saleDiscountlist.size()==0){
							Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
						}else{
							final DealerMannualSaleAdapter adapter =  (DealerMannualSaleAdapter) mlistView.getAdapter();
							final DealerDealerSale saledata = adapter.getItem(id);	
						/// write here
							
							String jai=saledata.getUnitPrice();
							double unitprice = Double.parseDouble(saledata.getUnitPrice());
							int   quantity= Integer.parseInt(saledata.getOrderQty());
							double totalAmount = quantity* unitprice;
							temtotalAmount =temtotalAmount+totalAmount; 
					        temtotalAmount = Math.round(temtotalAmount * 100.0) / 100.0;
						    txt_totalAmont.setText(String.valueOf(temtotalAmount));
						    double discount = Double.parseDouble(saleDiscountlist.get(0).get(Constants.MORDERDISCOUNTPER));
						    double dicAmount = (totalAmount*discount)/100;
						    temdicAmount=temdicAmount+dicAmount;
						    temdicAmount = Math.round(temdicAmount * 100.0) / 100.0;
					        txt_discount.setText(String.valueOf(temdicAmount));
							txt_netAmont.setText(String.valueOf(temtotalAmount-temdicAmount));
						}
					}

				}

			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			//Constants.contactTypeIDForDealer
			requestdata.put("DealerId", passworddetails.getString(Constants.DEALERID,""));
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerDiscountPercentage";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}
	
	
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.save:
     
			String jsondata=null;
		try{
		jsondata = getJsonStringForManualOrder(requestBean);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		uploadManualOrder(dealerId, jsondata);
		getProductList(dealerId);
		clearScreen();
		break;
        case R.id.cancel:
			finish();
			break;
		
		}
	}
	
	
	private void clearScreen(){

		txt_totalAmont.setText("");
		txt_discount.setText("");
		txt_netAmont.setText("");
		
	}
	private void uploadManualOrder(String dealerId, String detailsData) {
		// TODO Auto-generated method stub


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
				requestdata.put(Constants.jsonstring, detailsData);
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
	
	
	
	
	/*
	 *  Creating JSONObject 
	*/
	private JSONObject createWriteReview_JsonObject(ArrayList<DealerDealerSale> requestBean) {
	    	JSONObject jsonObject=null;
	        try {
	        jsonObject = new JSONObject();
	        JSONArray jsonArray = new JSONArray();
	        for (int i = 0; i < requestBean.size(); i++) {
	    	DealerDealerSale dealerDealerSale=requestBean.get(i);
	            jsonObject.put(Constants.CODE,dealerDealerSale.getCode() );
	            jsonObject.put(Constants.PENDING_ORDER, dealerDealerSale.getPending_order());
	            jsonObject.put(Constants.GITQAUNTITY,dealerDealerSale.getGitQuantity() );
	            jsonObject.put(Constants.INVENTORY, dealerDealerSale.getInventory());
	            jsonObject.put(Constants.ORDERQUANTITY,dealerDealerSale.getOrderQty() );
	            jsonObject.put(Constants.UNITPRICE_MANNUAL,dealerDealerSale.getUnitPrice() );
	            jsonArray.put(jsonObject);
			}
	        JSONObject jsonObjectBigObj = new JSONObject();
	        jsonObjectBigObj.put("Responce", jsonArray);
            return jsonObjectBigObj;
	        } catch (JSONException e) {
            e.getMessage();
	        }
	       return null;
	   }
	
	/**
	 * Json String containing coupon information. 
	 * @param eventid
	 * @param coupontextList
	 * @return json SAtructured String .
	 */
 	private String getJsonStringForManualOrder(ArrayList<DealerDealerSale> requestBean){



		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");


		for(int i=0;i<requestBean.size();i++){
			DealerDealerSale dealerDealerSale = requestBean.get(i);
			dataString.append("{");
			dataString.append("'");
			dataString.append("ProductID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(dealerDealerSale.getProductID());
			dataString.append("'");
			dataString.append(",");
			
			
			dataString.append("'");
			dataString.append("Qty");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(dealerDealerSale.getOrderQty());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append("UnitPrice");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(dealerDealerSale.getUnitPrice());
			dataString.append("'");
			dataString.append("}");
			dataString.append(",");
			
			
			
		}
		dataString.append("]");
		Log.i("Server data String ", dataString.toString());

		return dataString.toString();
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
}
