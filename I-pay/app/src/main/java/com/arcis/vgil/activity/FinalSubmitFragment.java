package com.arcis.vgil.activity;

/**
 * @author Muni Mishra
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.FinalSubmitAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Product;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class FinalSubmitFragment extends Fragment implements OnClickListener,OnCheckedChangeListener,OnItemLongClickListener {


	private View contentview;
	private FragmentActivity currentContext;
	private Button btn_search;
	private RadioGroup rg_saletype;
	private ListView mlistView;
	private Button btn_submit;

	public static final int SALETYPE_ALL = 0;
	public static final int SALETYPE_SALE = 1;
	public static final int SALETYPE_RETURN = 2;
	

	public static final String SERVICEINVOICENO = "serviceinvoiceno";
	public static final String PARTYNAME = "partyname";
	public static final String DATE = "date";
	public static final String SALEID = "saleId";
	private static final String DEBUG_TAG = "FinalSubmitFragment";

	private int checkedSaleType;
	private FinalSubmitAdapter adapter;


	public int getCheckedSaleType() {
		return checkedSaleType;
	}


	public void setCheckedSaleType(int checkedSaleType) {
		this.checkedSaleType = checkedSaleType;
	}
	
	public FragmentActivity getCurrentContext() {
		return currentContext;
	}


	public void setCurrentContext(FragmentActivity currentContext) {
		this.currentContext = currentContext;
	}


	public FinalSubmitFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			setCurrentContext(getActivity());
			contentview =inflater.inflate(R.layout.final_submit_layout, null);

		return  contentview;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		btn_search = (Button)getView().findViewById(R.id.search);
		btn_search.setOnClickListener(this);
		rg_saletype = (RadioGroup)getView().findViewById(R.id.rg_dealeredit);
		rg_saletype.setOnCheckedChangeListener(this);
		mlistView = (ListView)getView().findViewById(android.R.id.list);
		mlistView.setOnTouchListener(listTouchListener);
		btn_submit = (Button)getView().findViewById(R.id.submititems);
		btn_submit.setOnClickListener(this);
		registerForContextMenu(mlistView);
	}

	private String getStringFromResource(int stringid){
		return getResources().getString(stringid);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id==R.id.search){
			getEditableItemsList(getCheckedSaleType());
		}
		if(id==R.id.submititems){
			// submit final Sibmittion
			
			HashMap<Integer,Product> checkedMap = adapter.getCheckedMap();
			StringBuilder saleDetailString = new StringBuilder();
			for(Entry<Integer, Product> entry:checkedMap.entrySet()){
				Product value = entry.getValue();
				saleDetailString.append(value.getSaleid());
				saleDetailString.append(";");
			}
			checkedMap.clear();
			updateFinalSubmitionItems(saleDetailString.toString());
		}

	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
		
		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		long id = adapter.getItemId(info.position);
		int i = (int) id; 
		menu.add(i, 0, 1, "Show Details");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getGroupId();
		Product product = adapter.getItem(id);
		AlertDialog.Builder dialog = new AlertDialog.Builder(getCurrentContext());
		dialog.setTitle("Product Details!");
		dialog.setMessage("SaleId :"+ product.getSaleid()+"\n"
				+"SaleNo : " +product.getSaleNo()+"\n"
				+"Party Name : " +product.getPartyName()+"\n"
				+"Sale Date : " +product.getSaleDate()+"\n"
				+"Sale Quantity : "+product.getSaleQty()+"\n"
				+"Sale Amount : " +product.getSaleAmount()+"\n"
				+"Return Quantity : "+product.getReturnqQty()+"\n"
				+"Total Amount : "+ product.getTotalAmount()+"\n"
				+"Total Quantity : "+ product.getTotalQty()+"\n"
				+"No Of Items : "+ product.getNoOfItem()+"\n"
				+"Entry Date : " +product.getEntryDate());
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			dialog.dismiss();	
			}
		});
		AlertDialog d = dialog.create();
		d.show();
		return super.onContextItemSelected(item);
	}
	/**
	 * Get list of product for edit;
	 * @param saletype
	 */
	private void getEditableItemsList(int saletype){


		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Editable items",datacallback );

		LinkedHashMap<String, Object> requestdata = new LinkedHashMap<String, Object>();
		SharedPreferences credpref = getCurrentContext().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);

		try {
			requestdata.put(Constants.DEALERID_1, credpref.getString("dealerid",""));
			requestdata.put(Constants.SALETYPE, saletype);
			requestdata.put(Constants.username, credpref.getString("username",""));
			requestdata.put(Constants.password,credpref.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetSaleMasterYetTobeFinal";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	GetDataCallBack datacallback = new GetDataCallBack() {

		@Override
		public void processResponse(Object result) {
			// TODO Auto-generated method stub
			
			if(result!=null){
				
			ArrayList<Product> finalProductList = FetchingdataParser.getEditableProductsList(result.toString());
				if(finalProductList==null){
					AlertDialog.Builder errorDialog = new AlertDialog.Builder(getCurrentContext());
					errorDialog.setTitle("Error!");
					errorDialog.setMessage(result.toString());
					errorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							dialog.dismiss();
						}
					});
					AlertDialog dialog = errorDialog.create();
					dialog.show();
				}else{
					if(finalProductList.size()==0){
						Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_LONG).show();
					}else{
						
							adapter  = new FinalSubmitAdapter(getCurrentContext(), R.layout.final_submit_cell, finalProductList);
							mlistView.setAdapter(adapter);
						
					}

				}
			}
			


		}
	};


	@Override
	 public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		switch (checkedId) {
		case R.id.radioBtall:
			setCheckedSaleType(SALETYPE_ALL);
			break;
		case R.id.radioBtbsale:
			setCheckedSaleType(SALETYPE_SALE);
			break;
		case R.id.radioBtbreturn:
			setCheckedSaleType(SALETYPE_RETURN);
			break;


		default:
			break;
		}

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


	/**
	 *  Update server for the final submition items.
	 * @param saleid
	 * @param saledetails
	 */
	 private void updateFinalSubmitionItems(String saleid){


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
		SharedPreferences pref = getCurrentContext().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);

		Log.i(DEBUG_TAG +" Final Submitted Items server String ", "SaleId="+saleid);

		try{
			requestMap.put(Constants.DEALERID_1, pref.getString("dealerid", ""));
			requestMap.put(Constants.SALEID, saleid);
			requestMap.put(Constants.username, pref.getString(Constants.ID,""));
			requestMap.put(Constants.password,pref.getString("password",""));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerSaleFinalSubmission";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(arg0.getContext(), "View Long pressed", Toast.LENGTH_SHORT).show();
		return true;
	}

	}
