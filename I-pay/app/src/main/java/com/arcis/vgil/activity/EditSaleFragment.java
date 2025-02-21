package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.EditProductAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Product;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EditSaleFragment  extends Fragment implements OnClickListener,OnCheckedChangeListener,OnItemClickListener {


	private View contentview;
	private FragmentActivity currentContext;
	private Button btn_search;
	private RadioGroup rg_saletype;
	private ListView mlistView;

	public static final int SALETYPE_ALL = 0;
	public static final int SALETYPE_SALE = 1;
	public static final int SALETYPE_RETURN = 2;
	

	public static final String SERVICEINVOICENO = "serviceinvoiceno";
	public static final String PARTYNAME = "partyname";
	public static final String DATE = "date";
	public static final String SALEID = "saleId";

	private int checkedSaleType;
	private EditProductAdapter adapter;


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


	public EditSaleFragment(){

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(contentview==null){
			setCurrentContext(getActivity());

			contentview =inflater.inflate(R.layout.dealer_edit_layout, null);
		}else{
			((ViewGroup)contentview.getParent()).removeView(contentview);
		}
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
		mlistView.setOnItemClickListener(this);
		mlistView.setOnTouchListener(listTouchListener);
		registerForContextMenu(mlistView);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		getEditableItemsList(getCheckedSaleType());
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


	@Override
	public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {


		AdapterView.AdapterContextMenuInfo info;
		info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Please select");
		long id = mlistView.getAdapter().getItemId(info.position);
		int i = (int) id; 
		menu.add(i, 0, 1, "Edit");
		menu.add(i, 1, 2, "Delete");
		super.onCreateContextMenu(menu, v, menuInfo);

	};

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			// start anothor activity to edit.
			Product product = adapter.getItem(item.getGroupId());

			if(product!=null){
				Bundle bundle = new Bundle();
				bundle.putString(SERVICEINVOICENO, product.getSaleNo());
				bundle.putString(PARTYNAME, product.getPartyName());
				bundle.putString(DATE, product.getSaleDate());
				bundle.putString(SALEID, product.getSaleid());
				Intent intent  = new Intent(getCurrentContext(),EditProductActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			break;

		case 1:
			// delete the item.
			adapter.remove(adapter.getItem(item.getGroupId()));
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return  true;
	};


	  GetDataCallBack datacallback = new GetDataCallBack() {

		@Override
		 public void processResponse(Object result) {
			// TODO Auto-generated method stub
			AlertDialog.Builder errorDialog = new AlertDialog.Builder(getCurrentContext());
			
			ArrayList<Product> editProductList = FetchingdataParser.getEditableProductsList(result.toString());
			if(editProductList==null){
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
				if(editProductList.size()==0){
					Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_LONG).show();
				}else{
					adapter  = new EditProductAdapter(getCurrentContext(), R.layout.dealer_product_cell, editProductList);
					mlistView.setAdapter(adapter);

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	
}
