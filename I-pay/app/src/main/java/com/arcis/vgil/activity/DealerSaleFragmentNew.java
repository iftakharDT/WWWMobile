/**
 * 
 */
package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.DealerSaleAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DealerSale;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author munim
 *
 */
public class DealerSaleFragmentNew  extends Fragment implements OnClickListener {



	private Context mcontext;
	private ListView mlistView;
	private EditText edt_textPArtNo;
	private Button btn_date;
	private EditText edttxt_date;
	private Button btn_submit;
	private DealerSaleAdapter adapter;
	private TextView txt_totalAmount;
	private Button btn_save;
	private View contentview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			mcontext = getActivity();
			contentview =inflater.inflate(R.layout.dealer_sale_layout, null);

		return  contentview;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mlistView = (ListView) getView().findViewById(android.R.id.list);
		mlistView.setOnTouchListener(listTouchListener);
		registerForContextMenu(mlistView);
		edt_textPArtNo = (EditText)getView().findViewById(R.id.partno);
		edt_textPArtNo.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

				if(DealerSaleFragmentNew.this.adapter!=null)
				DealerSaleFragmentNew.this.adapter.getFilter().filter(arg0);
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

		btn_date = (Button)getView().findViewById(R.id.date);
		btn_date.setOnClickListener(this);

		edttxt_date = (EditText)getView().findViewById(R.id.datetext);

		btn_submit = (Button)getView().findViewById(R.id.submitdate);
		btn_submit.setOnClickListener(this);

		txt_totalAmount = (TextView)getView().findViewById(R.id.totalamount);

		btn_save = (Button)getView().findViewById(R.id.save);
		btn_save.setOnClickListener(this);

	}


	private void getDealerSaleDashboard(String date){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(mcontext, ProgressDialog.STYLE_SPINNER, "Loading...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(mcontext, getActivity().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{

					ArrayList<com.arcis.vgil.model.DealerSale> salelist = FetchingdataParser.getDealerSale(result.toString());
					if(salelist!=null){

						adapter  =new DealerSaleAdapter(mcontext, R.layout.dealersell_listcell, salelist);
						mlistView.setAdapter(adapter);

					}

				}

			}
		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getActivity().getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);
		try {
			requestdata.put("DealerID", passworddetails.getString("dealerid",""));
			requestdata.put("SaleDate", date);
			requestdata.put(Constants.username, passworddetails.getString("username",""));
			requestdata.put(Constants.password,passworddetails.getString("password",""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerDailySaleDashboard";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {

		case R.id.date:

			final Calendar cal = Calendar.getInstance();
			DatePickerDialog datepkrDialog = new DatePickerDialog(mcontext, new OnDateSetListener() {

				public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

					String currentDate = cal.get(Calendar.MONTH)+1+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR);

					String inputdate = (monthOfYear+1)+"/"+dayOfMonth+"/"+year;

					int datediff = getDateDifferance(currentDate, inputdate);
					datediff = Math.abs(datediff);
					if(datediff>3){
						Util.showToast(mcontext, getResources().getString(R.string.error8), Toast.LENGTH_LONG).show();
					}else{
						edttxt_date.setText(inputdate);
					}
				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			datepkrDialog.setMessage("Please select date");
			datepkrDialog.show();
			break;

		case R.id.submitdate:

			if(edttxt_date.getText().toString().length()>0){

				getDealerSaleDashboard(edttxt_date.getText().toString().trim());
			}else{
				Util.showToast(mcontext, getResources().getString(R.string.error8), Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.save:
			uploadData();
		default:
			break;
		}


	}


	private int getDateDifferance(String currentdate, String inputDate){

		String dateStart = currentdate;
		String dateStop = inputDate;

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Date d1 = null;
		Date d2 = null;
		int  diffDays = 0;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();
			diffDays = (int)diff / (24 * 60 * 60 * 1000);

			Log.i("Date Differance", String.valueOf(diffDays));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  diffDays;
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

			int id = item.getItemId();
			LayoutInflater inflator = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view  = inflator.inflate(R.layout.order_sale_quantity_layout, null);


			final DealerSaleAdapter adapter =  (DealerSaleAdapter) mlistView.getAdapter();
			final com.arcis.vgil.model.DealerSale saledata = adapter.getItem(id);
			EditText text1 = (EditText)view.findViewById(R.id.edttxt_currentstock);
			text1.setText(saledata.getCurrentStock());

			final int currentStock = Integer.parseInt(saledata.getCurrentStock());

			final EditText text_sale = (EditText)view.findViewById(R.id.editText_saleorder);
			AlertDialog.Builder saleDailog = new AlertDialog.Builder(mcontext);

			saleDailog.setView(view);
			saleDailog.setPositiveButton("Save", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					String sale =  text_sale.getText().toString();
					if(sale.length()>0){

						int  saleQty  = Integer.parseInt(sale);
						if(saleQty>currentStock){
							Util.showToast(mcontext, "Sale quantity can't be greate than stock quantity!", Toast.LENGTH_LONG).show();
						}else{
							saledata.setSalequantity(saleQty);
							dialog.dismiss();
							adapter.notifyDataSetChanged();
							double totalamount = 	adapter.getTotalAmount();
							txt_totalAmount.setText(String.valueOf(totalamount));

						}
					}else{
						Util.showToast(mcontext, "Please enter correct quantity", Toast.LENGTH_LONG).show();
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



	
	
	public OnTouchListener listTouchListener = new OnTouchListener() {


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
	 * Upload data to server.
	 * @param savedDataList
	 */
	private void uploadData(){


		String saleType = null;
		RadioButton rb = (RadioButton)getView().findViewById(R.id.radioBtbsale);
		boolean ischeacked = rb.isChecked();
		if(ischeacked){
			saleType = "0";
		}else {
			saleType = "1";
		}
		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(mcontext, ProgressDialog.STYLE_SPINNER, getResources().getString(R.string.uploaddata), new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				SoapObject responce  = null;
				try{

					responce = (SoapObject)result;
				}catch(Exception ex){
					ex.printStackTrace();
				}
				AlertDialog.Builder errordialog = new AlertDialog.Builder(mcontext);
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

		LinkedHashMap<String , Object> requestMap = new LinkedHashMap<String, Object>();

		SharedPreferences pref = mcontext.getSharedPreferences("PASSWORD", Context.MODE_PRIVATE);

		StringBuilder masterString  = new StringBuilder();
		masterString.append(pref.getString("dealerid", ""));
		masterString.append("~");
		masterString.append("Sold From Android");
		masterString.append("~");
		masterString.append(String.valueOf(adapter.getTotalAmount()));
		masterString.append("~");
		masterString.append(edttxt_date.getText().toString());

		String detailString  = adapter.getserverString();

		Log.i(" server String ",  masterString.toString() +"---" +detailString+" SaleType=" +saleType);

		try{

			requestMap.put(Constants.SALEMASTERINFO, masterString.toString());
			requestMap.put(Constants.SALEDETAILINFO, detailString);
			requestMap.put(Constants.SALETYPE, saleType);
			requestMap.put(Constants.username, pref.getString(Constants.ID,""));
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
}
