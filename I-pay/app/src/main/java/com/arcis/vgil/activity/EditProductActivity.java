package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.EditItemsAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.EditableProduct;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class EditProductActivity  extends AppCompatActivity implements OnClickListener {


	private static final String DEBUG_TAG = "EditProductActivity";
	private EditText edttxt_saleno,edttxt_partyName,edttxt_date;
	private Button btn_deleteitem,btn_save;
	private EditItemsAdapter adapter;
	private ListView mlistview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_product_layout);

		edttxt_saleno = (EditText)findViewById(R.id.edittxt_serviceinvoice);
		edttxt_partyName = (EditText)findViewById(R.id.edittxt_partyname);
		edttxt_date     = (EditText)findViewById(R.id.edittxt_date);

		btn_deleteitem = (Button)findViewById(R.id.deleteitems);
		btn_deleteitem.setOnClickListener(this);
		btn_save       = (Button)findViewById(R.id.save);
		btn_save.setOnClickListener(this);
		mlistview = (ListView)findViewById(android.R.id.list);
		mlistview.setOnTouchListener(listTouchListener);

		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){

			String saleno = bundle.getString(EditSaleFragment.SERVICEINVOICENO);
			String partyname = bundle.getString(EditSaleFragment.PARTYNAME);
			String date      = bundle.getString(EditSaleFragment.DATE);
			String saleid = bundle.getString(EditSaleFragment.SALEID);
			if(saleno!=null){
				edttxt_saleno.setText(saleno);
			}
			if(partyname!=null){
				edttxt_partyName.setText(partyname);
			}
			if(date!=null){
				edttxt_date.setText(date);
			}

			getEditableItemsList(saleid);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();

		if(id==R.id.deleteitems){
			// delete items
			int deleteditems = 0;
			HashMap<Integer, EditableProduct> checkedMap = adapter.getCheckedMap();
			if(checkedMap.size()>0){
				String saleId = null;
				StringBuilder saleDetailString = new StringBuilder();
				for(Entry<Integer, EditableProduct> entry:checkedMap.entrySet()){
					EditableProduct value = entry.getValue();
					adapter.remove(value);
					deleteditems++;
					saleId= value.getSaleid();
					saleDetailString.append(value.getSalesDetailId());
					saleDetailString.append(";");
				}
				checkedMap.clear();
				adapter.notifyDataSetChanged();
				Toast.makeText(getCurrentContext(), String.valueOf(deleteditems)+" items deleted", Toast.LENGTH_SHORT).show();
				updateDeletedItems(saleId, saleDetailString.toString());
			}
			
		}
		if(id==R.id.save){
			// upload data to server.##########################################
			
		}
	}
 
	private CharSequence getStringFromResource(int error6) {
		// TODO Auto-generated method stub
		return getResources().getString(error6);
	}

	/**
	 *  Update server for the deleted items.
	 * @param saleid
	 * @param saledetails
	 */
	private void updateDeletedItems(String saleid, String saledetails){


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
		SharedPreferences pref = getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);

		//Log.i(DEBUG_TAG +" Deleted Items server String ",  "SaleId="+saleid +"---"+"SaledetailIDs= "+ saledetails);

		try{

			requestMap.put(Constants.DEALERID_1, pref.getString("dealerid", ""));
			requestMap.put(Constants.SALEID, saleid);
			requestMap.put(Constants.SALESDETAILSIDS, saledetails);
			requestMap.put(Constants.username, pref.getString("username",""));
			requestMap.put(Constants.password,pref.getString("password",""));
		}catch(Exception ex){
			ex.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DeleteSaleDetails";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestMap);
		datafromnetwork.execute();
	}

	/**
	 * Get list of product for edit;
	 * @param
	 */

	private void getEditableItemsList(String saletid){


		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Please wait...",datacallback );

		LinkedHashMap<String, Object> requestdata = new LinkedHashMap<String, Object>();
		SharedPreferences credpref = getCurrentContext().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);

		try {
			requestdata.put(Constants.DEALERID_1, credpref.getString("dealerid",""));
			requestdata.put(Constants.SALEID, saletid);
			requestdata.put(Constants.username, credpref.getString("username",""));
			requestdata.put(Constants.password,credpref.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetSaleDetailsYetTobeFinalForEdit";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}
	private Context getCurrentContext() {
		// TODO Auto-generated method stub
		return EditProductActivity.this;
	}

	GetDataCallBack datacallback = new GetDataCallBack() {

		@Override
		public void processResponse(Object result) {
			// TODO Auto-generated method stub
			if(result!=null){
				ArrayList<EditableProduct> productsList = FetchingdataParser.getListOfEditableItems(result.toString());
				if(productsList!=null){
					adapter = new EditItemsAdapter(getCurrentContext(), R.layout.edit_product_cell, productsList);
					mlistview.setAdapter(adapter);

				}else{
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
			}

		}
	};

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
