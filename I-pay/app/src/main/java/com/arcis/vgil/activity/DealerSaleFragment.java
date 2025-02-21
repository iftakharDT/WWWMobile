package com.arcis.vgil.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DealerProductAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.ProductDetails;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * Old Class for Dealer Sale implementation.
 * new class for Dealer sale DealerSaleFragment.
 */
public class DealerSaleFragment  extends Fragment implements OnClickListener {

	
	EditText partyname,datetext,qty,itemCode,itemName;
	AutoCompleteTextView searchItem;
	Button save,cancel ,additem,datebutton,btn_red,btn_green,btn_yellow,btn_submit;
	RadioGroup rg_saleType;
	TextView txt_stockQuantity,txt_btsbtoclassification, txt_rygstatus,txt_amount;
	private ProductDetails pr_details;
	private ListView mproductlist;
	ArrayList<ProductDetails> productDetailsList = new ArrayList<ProductDetails>();
	DealerProductAdapter productAdpter;
	private Context mcontext;
	private View contentView;
	
	public static final String DEBUG_TAG = "Dealer Sale";
	private String mserverdate;
	
	public String getserverdate() {
		return mserverdate;
	}

	public void setserverdate(String mserverdate) {
		this.mserverdate = mserverdate;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(contentView==null){
			setCurrentContext(getActivity());
			getProductList();
			contentView = inflater.inflate(R.layout.dealersale, null);
		}else{
			((ViewGroup)contentView.getParent()).removeView(contentView);
		}
		
		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		partyname=(EditText) getView().findViewById(R.id.partyname);
		
		datetext=(EditText)getView().findViewById(R.id.datetext);
		btn_submit = (Button)getView().findViewById(R.id.submitdate);
		btn_submit.setOnClickListener(this);
		
		qty=(EditText) getView().findViewById(R.id.quantity);
		mproductlist = (ListView)getView().findViewById(android.R.id.list);
		mproductlist.setOnTouchListener(listTouchListener);
		registerForContextMenu(mproductlist);
		searchItem=(AutoCompleteTextView) getView().findViewById(R.id.searchitem);
		
		searchItem.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionId== EditorInfo.IME_ACTION_SEARCH){
					// getproduct info.
					
					String productid = (String)searchItem.getTag();
					hideKeyboard(searchItem);
					getProductDetails(productid);
					return true;
				}
				return false;
			}
		});
		searchItem.addTextChangedListener(new TextWatcher() {
			
			@Override
		 	public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
		 	public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			
				Log.i("TextChange", s.toString());
				if(s.toString().contains("{")){
					String[] data = s.toString().split(",");
					String[] newdata = data[1].split("=");
					searchItem.setText(newdata[1]);
					String[] temparray = data[0].split("=");
					String id= temparray[1];
					searchItem.setTag(id);
				}
			}
		});
		itemCode = (EditText)getView().findViewById(R.id.edttxtitemcode);
		itemName = (EditText)getView().findViewById(R.id.edttxtitemname);
		
		
		
		txt_stockQuantity = (TextView)getView().findViewById(R.id.txtview_stockQuantity);
		txt_btsbtoclassification = (TextView)getView().findViewById(R.id.txtview_btobtsclasific);
		txt_rygstatus = (TextView)getView().findViewById(R.id.txtview_rygstatus);
		txt_amount = (TextView)getView().findViewById(R.id.txtview_totalAmount);
		
		btn_red 	= (Button)getView().findViewById(R.id.statusred);
		btn_yellow 	= (Button)getView().findViewById(R.id.statusyellow);
		btn_green =(Button)getView().findViewById(R.id.statusgreen);
		
		rg_saleType = (RadioGroup)getView().findViewById(R.id.dealertype);
	
		
		
		save=(Button) getView().findViewById(R.id.save);
		save.setOnClickListener(this);
		cancel=(Button) getView().findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		additem=(Button) getView().findViewById(R.id.additem);
		additem.setOnClickListener(this);
		datebutton=(Button) getView().findViewById(R.id.date);
		datebutton.setOnClickListener(this);
		
	}
	
	private void setCurrentContext(Activity context) {
		// TODO Auto-generated method stub
		mcontext = context;
	}

	public DealerSaleFragment (){
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.date:
			final Calendar calendar= Calendar.getInstance();
			DatePickerDialog datePickerDialog= new DatePickerDialog(getCurrentContext(), new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int month, int date) {
					
					Calendar cal  = Calendar.getInstance();
					int currmonth  = (cal.get(Calendar.MONTH)+1);
					int currDate = cal.get(Calendar.DAY_OF_MONTH);
					if(date>currDate || month>currmonth){
						Toast.makeText(view.getContext(), getResources().getString(R.string.dateerror), Toast.LENGTH_LONG).show();
					}else{
						datetext.setText(String.valueOf(date)+"/"+ String.valueOf(month+1)+"/"+ String.valueOf(year));
						setserverdate(String.valueOf(month+1)+"-"+ String.valueOf(date)+"-"+ String.valueOf(year));
						Log.i(DEBUG_TAG, getserverdate());
						
					}
					
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
			
			if(Build.VERSION.SDK_INT> Build.VERSION_CODES.GINGERBREAD_MR1)
			//datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
			datePickerDialog.show();
				break;
			case R.id.save:
				// Upload data to server.
				uploadData(productDetailsList);
				break;
			
	case R.id.additem:
		if(validation()){
			if(pr_details!=null){
				pr_details.setQuantity(Integer.parseInt(qty.getText().toString()));
				pr_details.setPartyName(partyname.getText().toString());
				if(!productDetailsList.contains(pr_details)){
					productDetailsList.add(pr_details);
					populateList(productDetailsList);
					setTotalAmount(productDetailsList);
					Toast.makeText(getCurrentContext(), "One Item added...", Toast.LENGTH_SHORT).show();
				}
				
			}
		}
		break;
	case R.id.cancel:
		startActivity(new Intent(getCurrentContext(),ViewAll_AM.class));
		getActivity().finish();

		break;
		
	case R.id.submitdate:
		if(getserverdate()!=null){
			getAlreadySaleProductList(getserverdate());
		}
		
		
		break;
		default:
			break;
		}
	}
	
	public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
		
		
		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = mproductlist.getAdapter().getItemId(info.position);
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
			productDetailsList.remove(selectedindex);
			DealerProductAdapter adapter =  (DealerProductAdapter) mproductlist.getAdapter();
			adapter.notifyDataSetChanged();
			setTotalAmount(productDetailsList);
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
		
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
	
	private void hideKeyboard(View anchorview){
		InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(anchorview.getWindowToken(), 0);
	}
	
	/** 
	 * Get product List
	 * @param productid
	 */
	private void getProductDetails( String productid){
			
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(mcontext, ProgressDialog.STYLE_SPINNER, "Loading product details...", new GetDataCallBack() {
			
			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdectDetails", result.toString());
					pr_details= FetchingdataParser.getProductDetails(result.toString());
						if(pr_details==null){
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
							
							// Bind UI Element.
							qty.setText("");
							
							itemCode.setText(pr_details.getProductCode());
							itemName.setText(pr_details.getProcuctName());
							txt_stockQuantity.setText(String.valueOf(pr_details.getStockquantity()));
							txt_btsbtoclassification.setText(pr_details.getClassification());
							txt_rygstatus.setText(pr_details.getRYGstatus());
							btn_red.setText(pr_details.getRed());
							btn_green.setText(pr_details.getGreen());
							btn_yellow.setText(pr_details.getYellow());
						}
				}
			}
		});
		
		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=mcontext.getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
		try {
			requestdata.put(Constants.PRODUCTID, productid) ;
			requestdata.put(Constants.DEALERID_1,passworddetails.getString("dealerid",""));
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			String ipAddress=getResources().getString(R.string.ipaddress);
			String webService =getResources().getString(R.string.Webservice_Sale);
			String nameSpace=getResources().getString(R.string.nameSpace);
			String methodName="GetProductDetails";
			String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
			datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
			datafromnetwork.sendData(requestdata);
			datafromnetwork.execute();
	}
	
	/**
	 * Get Products List.
	 */
	 private void getProductList(){
		
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {
			
			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getActivity().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
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
							searchItem.setAdapter(dataadapter);
						}
					}
					
				}
				
			
				
			}

			
		});
		
		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
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
	
	private Context getCurrentContext() {
		// TODO Auto-generated method stub
		return mcontext;
	}

	/** Upload data to server.
	 * @param savedDataList
	 */
	private void uploadData(ArrayList<ProductDetails> savedDataList){
		
		
		String saleType = null;
		RadioButton rb = (RadioButton)getView().findViewById(R.id.radioBtbsale);
		boolean ischeacked = rb.isChecked();
		if(ischeacked){
			saleType = "0";
		}else {
			saleType = "1";
		}
	 	GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, getStringFromResource(R.string.uploaddata), new GetDataCallBack() {
			
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
					errordialog.setTitle(getStringFromResource(R.string.error6));
					if (result != null) {
						errordialog.setMessage(result.toString());
					} else {
						errordialog.setMessage(getStringFromResource(R.string.error4));
					}
		
				} else {
					errordialog.setMessage(getStringFromResource(R.string.message5));
					
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
		
		LinkedHashMap<String , Object> requestMap = new LinkedHashMap<String, Object>();
		
		int totalAmountNewlyAdded = 0;
		String saleDate  = getserverdate().replace("-", "/");
		SharedPreferences pref = getActivity().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
		
		StringBuilder masterString  = new StringBuilder();
		masterString.append(pref.getString("dealerid", ""));
		masterString.append("~");
		masterString.append(partyname.getText().toString());
		masterString.append("~");
		
		StringBuilder detailsString  = new StringBuilder();
		
		for(ProductDetails product : savedDataList){
			
			if(product.getSaleid()==0){
				totalAmountNewlyAdded = totalAmountNewlyAdded + product.getNetsellingprice()*product.getQuantity();
				
				detailsString.append(product.getProcuctName());
				detailsString.append("~");
				detailsString.append(product.getProductCode());
				detailsString.append("~");
				detailsString.append(String.valueOf(product.getStockquantity()));
				detailsString.append("~");
				detailsString.append(product.getProductId());
				detailsString.append("~");
				detailsString.append(String.valueOf(product.getNetsellingprice()));
				detailsString.append("~");
				detailsString.append(product.getClassification());
				detailsString.append("~");
				detailsString.append(String.valueOf(product.getQuantity()));
				detailsString.append("~");
				detailsString.append(String.valueOf(product.getStockquantity()-product.getQuantity()));
				detailsString.append(";");
			}
			
		}
		
		masterString.append(String.valueOf(totalAmountNewlyAdded));
		masterString.append("~");
		masterString.append(saleDate);
		Log.i(DEBUG_TAG +" server String ",  masterString.toString() +"---" +detailsString.toString()+" SaleType=" +saleType);
		
		try{
			
			requestMap.put(Constants.SALEMASTERINFO, masterString.toString());
			requestMap.put(Constants.SALEDETAILINFO, detailsString.toString());
			requestMap.put(Constants.SALETYPE, saleType);
			requestMap.put(Constants.username, pref.getString("username",""));
			requestMap.put(Constants.password,pref.getString("password",""));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="SaveSale";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
		
		
	}
	
	public boolean validation(){
		Boolean flag=true;
		String errMgs = getStringFromResource(R.string.error3);
		if (partyname.length() == 0) {
			partyname.setError(getStringFromResource(R.string.partyname));
			flag = false;
		}
		
		if (datetext.length() == 0) {
			//datetext.setError(getStringFromResource(R.string.date));
			errMgs=errMgs.concat(getStringFromResource(R.string.date));
			flag = false;
		}
		if (qty.length() == 0 || Integer.parseInt(qty.getText().toString())> Integer.parseInt(txt_stockQuantity.getText().toString()) || qty.getText().equals("0")) {
			qty.setError(getStringFromResource(R.string.quantity));
			flag = false;
		}
		
		if (!flag && !errMgs.equals(getStringFromResource(R.string.error2))) {
			Toast.makeText(getCurrentContext(), errMgs, Toast.LENGTH_LONG)
					.show();
		}	
		return flag;
		
	}
	
	public String getStringFromResource(int StringCode) {
		return getResources().getString(StringCode);
		}
	
	 /** Populate List.
	 * @param productList
	 */
	private void populateList(ArrayList<ProductDetails> productList){
		if(productAdpter==null){
			productAdpter = new DealerProductAdapter(getCurrentContext(), R.layout.dealer_product_cell, productList);
			mproductlist.setAdapter(productAdpter);
		}else{
			productAdpter.notifyDataSetChanged();
		}
	}
	
	/**
	 *  Get Total amount of product Sold.
	 * @param datalist
	 * @return
	 */
	private void setTotalAmount(ArrayList<ProductDetails> datalist){
		int totalcount = 0;
		for(ProductDetails details :datalist){
			
			totalcount = totalcount +details.getNetsellingprice()*details.getQuantity();
		}
		txt_amount.setText(String.valueOf(totalcount));
	}
	
	 /** Get Already sold products based on the date provided.
	 * @param date Date for which already sold items to be needed.
	 */
	 private void getAlreadySaleProductList(String date){
		
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, getStringFromResource(R.string.loadsalelist), new GetDataCallBack() {

			
			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getStringFromResource(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<ProductDetails> details = FetchingdataParser.getAlreadySoldProductsList(result.toString());
					if(details==null){
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
						productDetailsList.addAll(details);
						populateList(productDetailsList);
						setTotalAmount(productDetailsList);
					}
				}
			}
		});
		
		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
		try {
			requestdata.put(Constants.SALEDATE, date);
			requestdata.put(Constants.SALEID, "0");
			requestdata.put(Constants.PRODUCTID, "0") ;
			requestdata.put(Constants.DEALERID_1,passworddetails.getString("dealerid",""));
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			String ipAddress=getResources().getString(R.string.ipaddress);
			String webService =getResources().getString(R.string.Webservice_Sale);
			String nameSpace=getResources().getString(R.string.nameSpace);
			String methodName="GetDealerSale_DateWise";
			String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
			datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
			datafromnetwork.sendData(requestdata);
			datafromnetwork.execute();
	}
}
