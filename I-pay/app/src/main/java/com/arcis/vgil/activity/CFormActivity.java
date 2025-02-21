package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.InvoiceAdapter;
import com.arcis.vgil.adapter.InvoiceDetailAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.Invoice;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class CFormActivity  extends BaseActivity{


	ListView mlistInvoice,mlistInvoiceDetails;
	SharedPreferences pref;
	private LinearLayout mlayoutDealer, mlayoutAreaManager, mlayoutCFA,mlayoutbypost,mlayouthand;;
	private Button btn_save, btn_calculate,btn_cformdate,btn_handover,btn_dispatch,btn_recievedDate,btn_recieveddatecfa;
	InvoiceAdapter adapter ;
	private String[] modeOfDisp = {"Please select","Hand","Post"};

	private ArrayList<String> recievedList = new ArrayList<String>(Arrays.asList(new String[]{"Please select","Yes","No"}));


	private static final String INVOICEID = "InvoiceId";
	private static final String INVOICENO = "InvoiceNo";
	private static final String USERTYPEID = "UserTypeId";
	private Spinner spinner_mod,spinner_nop,spinner_amrecieved,spinner_amcformdelcfa,spinner_cfarecieved;
	private EditText edttxt_cformvalue,edttxt_cformno,edttxt_courRefno, edttxt_compName,edttxt_recievedDate,edttxt_recieveddatecfa;
	private boolean isDealer, isAreaManager,isCFA,isNameOfPersonRecieved ;
	String cforID;

	public String getCforID() {
		return cforID;
	}

	public void setCforID(String cforID) {
		this.cforID = cforID;
	}

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_cform_layout);
		setCurrentContext(this);



		mlistInvoice = (ListView)findViewById(android.R.id.list);
		mlistInvoice.setOnTouchListener(listTouchListener);
		mlistInvoiceDetails = (ListView)findViewById(R.id.invoice_dettail_list);
		mlistInvoiceDetails.setOnTouchListener(listTouchListener);

		btn_cformdate = (Button)findViewById(R.id.cformdate);
		btn_cformdate.setOnClickListener(this);

		btn_handover = (Button)findViewById(R.id.handoverdate);
		btn_handover.setOnClickListener(this);

		btn_dispatch = (Button)findViewById(R.id.dateofdisp);
		btn_dispatch.setOnClickListener(this);

		btn_recievedDate = (Button)findViewById(R.id.dateofrecieved);
		btn_recievedDate.setOnClickListener(this);

		btn_recieveddatecfa = (Button)findViewById(R.id.dateofrecieved_cfa);
		btn_recieveddatecfa.setOnClickListener(this);

		btn_save       = (Button)findViewById(R.id.save);
		btn_save.setOnClickListener(this);

		mlayoutDealer      = (LinearLayout)findViewById(R.id.bydealer);
		mlayoutAreaManager = (LinearLayout)findViewById(R.id.layoutbyfieldagent);
		mlayoutCFA         = (LinearLayout)findViewById(R.id.layoutbyCFA);
		mlayoutbypost      = (LinearLayout)findViewById(R.id.bycourier);
		mlayouthand  	   = (LinearLayout)findViewById(R.id.bydealersale);

		mlayouthand.setVisibility(View.GONE);
		mlayoutbypost.setVisibility(View.GONE);

		btn_calculate      = (Button)findViewById(R.id.calculate);
		btn_calculate.setOnClickListener(this);

		spinner_mod = (Spinner)findViewById(R.id.spinner_modeofdispatch);
		spinner_mod.setOnItemSelectedListener(spinnerListener);

		spinner_amrecieved = (Spinner)findViewById(R.id.spinner_recievedbyfa);
		spinner_amcformdelcfa = (Spinner)findViewById(R.id.spinner_deliveredtoCFA);
		spinner_cfarecieved  = (Spinner)findViewById(R.id.spinner_recievedbycfa);

		bindSpinnerAdapter(recievedList, spinner_amrecieved);
		bindSpinnerAdapter(recievedList, spinner_amcformdelcfa);
		bindSpinnerAdapter(recievedList, spinner_cfarecieved);



		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,modeOfDisp);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_mod.setAdapter(spinnerAdapter);

		spinner_nop  = (Spinner)findViewById(R.id.spinner_nameofperson);

		edttxt_cformno = (EditText)findViewById(R.id.editText_cformno);
		edttxt_cformvalue = (EditText)findViewById(R.id.editText_cformvalue);
		edttxt_courRefno  = (EditText)findViewById(R.id.editText_crn);
		edttxt_compName   = (EditText)findViewById(R.id.editText_nofcomp);
		edttxt_recievedDate = (EditText)findViewById(R.id.editText_recieveddate);
		edttxt_recieveddatecfa = (EditText)findViewById(R.id.editText_recieveddate_cfa);


		pref = Util.getSharedPreferences(this, Constants.PREF_NAME);
		String id = pref.getString(Constants.CONTACTTYPEID, "");

		if(id.equalsIgnoreCase("14")){
			isDealer = true;
			mlayoutAreaManager.setVisibility(View.GONE);
			mlayoutCFA.setVisibility(View.GONE);
		}else if(id.equalsIgnoreCase("1")){
			isAreaManager = true;
			mlayoutDealer.setVisibility(View.GONE);
			mlayoutCFA.setVisibility(View.GONE);
		}else if(id.equalsIgnoreCase("11")){
			isCFA = true;
			mlayoutDealer.setVisibility(View.GONE);
			mlayoutAreaManager.setVisibility(View.GONE);
		}

		Intent intent  = getIntent();
		if(intent!=null && intent.getExtras()!=null){
			// Activity started from CForm Pending activity 
			String cformID = intent.getStringExtra(CFormPendingActivity.EXTRA_CFORM_ID);
			setCforID(cformID);
			getPendingCformforAMCFAList(cformID);

		}else{
			//  Activity started from ViewAll activity 
			getDealerInvoiceList();
		}

	}

	@Override
	public boolean validation() {
		// TODO Auto-generated method stub
		boolean flag = true;

		String errMsg = getStringFromResource(R.string.error3);
		if(isDealer){
			// add validatio for dealer layout
			if(edttxt_cformno.getText().toString().length()==0){

				edttxt_cformno.setError(getStringFromResource(R.string.cformno));
				flag = false;
			}
			EditText edttxt1 = (EditText)findViewById(R.id.editText_cformdate);
			if(edttxt1.getText().toString().length()==0){
				edttxt1.setError(getStringFromResource(R.string.cformdate));
				flag = false;
			}
			if(edttxt_cformvalue.getText().toString().length()==0){
				edttxt_cformvalue.setError(getStringFromResource(R.string.cformvalue));
				flag = false;
			}
			if(spinner_mod.getSelectedItemPosition()==0){
				errMsg = errMsg.concat(" "+ getStringFromResource(R.string.modeofdispatch));
				flag = false;
			}
			if(spinner_mod.getSelectedItemPosition()==1){
				if(spinner_nop.getSelectedItemPosition()==0){
					errMsg = errMsg.concat(" " +getStringFromResource(R.string.nameofperson));
					flag = false;
				}
			}

			if(spinner_mod.getSelectedItemPosition()==2){
				if(edttxt_compName.getText().toString().length()==0){
					edttxt_compName.setError(getStringFromResource(R.string.nameofcompany));
					flag = false;
				}
				if(edttxt_courRefno.getText().toString().length()==0){
					edttxt_courRefno.setError(getStringFromResource(R.string.courierrefno));
					flag = false;
				}
			}

			EditText edttxt = 	(EditText)findViewById(R.id.editText_dateofdisp);

			if(edttxt.getText().toString().length()==0){
				edttxt.setError(getStringFromResource(R.string.dateofdisp));
				flag = false;

			}

		}else if(isAreaManager){
			// Add validation for area manager

			if(spinner_amrecieved.getSelectedItemPosition()==0){

				errMsg = errMsg.concat(" " +getStringFromResource(R.string.recieved));
				flag = false;
			}
			if(edttxt_recievedDate.getText().toString().length()==0){
				edttxt_recievedDate.setError(getStringFromResource(R.string.recieveddate));
				flag = false;
			}
			if(spinner_amcformdelcfa.getSelectedItemPosition()==0){
				errMsg = errMsg.concat(" " +getStringFromResource(R.string.cformdeliveredtocfa));
				flag = false;
			}
		}else if(isCFA){
			// add validation for cfa.

			if(spinner_cfarecieved.getSelectedItemPosition()==0){
				errMsg = errMsg.concat(" " +getStringFromResource(R.string.cformdeliveredtocfa));
				flag = false;
			}
			if(edttxt_recieveddatecfa.getText().toString().length()==0){
				edttxt_recieveddatecfa.setError(getStringFromResource(R.string.recieveddate));
				flag = false;
			}
		}
		if(!flag)
			Util.showToast(getCurrentContext(), errMsg, Toast.LENGTH_LONG).show();
		return flag;
	}

	private void getDealerInvoiceList(){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Dealer's Invoice...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<Invoice> invoiceList = FetchingdataParser.getInvoiceList(result.toString());
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
						adapter  = new InvoiceAdapter(getCurrentContext(), R.layout.invoicecell, invoiceList);
						mlistInvoice.setAdapter(adapter);
					}

				}
			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(CFormActivity.this, Constants.PREF_NAME);
		try {
			//
			requestdata.put(Constants.dealerUserid, passworddetails.getString(Constants.DEALERID,""));
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerInvoiceNotPending";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	@Override
	public void getScreenData() {
		// TODO Auto-generated method stub
		LinkedHashMap< String, Object> dataMap = new LinkedHashMap<String, Object>();

		if(isDealer){

			dataMap.put(Constants.cformno, edttxt_cformno.getText().toString());
			dataMap.put(Constants.cformdate, ((EditText)findViewById(R.id.editText_cformdate)).getText().toString());
			dataMap.put(Constants.cformvalue, edttxt_cformvalue.getText().toString());
			dataMap.put(Constants.modeofdispatch, spinner_mod.getSelectedItem().toString());
			if(spinner_mod.getSelectedItemPosition()==1){

				@SuppressWarnings("unchecked")
                HashMap<String, String> data = (HashMap<String, String>) spinner_nop.getSelectedItem();
				dataMap.put(Constants.personid ,data.get(Constants.ID));
			}else{
				dataMap.put(Constants.personid ,"0");
			}

			dataMap.put(Constants.handoverdate,((EditText)findViewById(R.id.editText_cformdate)).getText().toString());

			if(spinner_mod.getSelectedItemPosition()==2){

				dataMap.put(Constants.courierrefno, edttxt_courRefno.getText().toString());
				dataMap.put(Constants.couriercompanyname, edttxt_compName.getText().toString());


			}else{
				dataMap.put(Constants.courierrefno, "0");
				dataMap.put(Constants.couriercompanyname, "0");
			}

			dataMap.put(Constants.dateofdispatch, ((EditText)findViewById(R.id.editText_dateofdisp)).getText().toString());

			InvoiceDetailAdapter adapter  = (InvoiceDetailAdapter) mlistInvoiceDetails.getAdapter();
			dataMap.put(Constants.jsondata, getInvoiceIdJSON(adapter.getInvoiceList()));




		}
		if(isAreaManager){
			dataMap.put("Mode", "AM");
			dataMap.put("CFormID ", getCforID());
			if(spinner_amrecieved.getSelectedItemPosition()==1){
				dataMap.put("Received", "1");
			}else{
				dataMap.put("Received", "0");
			}
			dataMap.put("RecieptDate",edttxt_recievedDate.getText().toString());
			if(spinner_amcformdelcfa.getSelectedItemPosition()==1){
				dataMap.put("Delivered", "1");
			}else {
				dataMap.put("Delivered", "0");
			}

		}
		if(isCFA){
			dataMap.put("Mode", "CFA");
			dataMap.put("CFormID ", getCforID());
			if(spinner_cfarecieved.getSelectedItemPosition()==1){
				dataMap.put("Received", "1");
			}else{
				dataMap.put("Received", "0");
			}

			dataMap.put("RecieptDate",edttxt_recieveddatecfa.getText().toString());
			dataMap.put("Delivered", "0");
		}

		dataMap.put(Constants.username, pref.getString(Constants.USERID, ""));
		dataMap.put(Constants.password, pref.getString(Constants.PASSWORD, ""));
		setRequestDataMap(dataMap);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText edttxt = null;
		switch (v.getId()) {
		case R.id.calculate:
			getInvoiceDetails();
			break;

		case R.id.save:
			if(validation()){
				getScreenData();
				uploadData();
			}
			break;

		case R.id.cformdate:
			edttxt= (EditText)findViewById(R.id.editText_cformdate);
			setDate(edttxt);
			break;

		case R.id.handoverdate:

			edttxt= (EditText)findViewById(R.id.editText_handoverdate);
			setDate(edttxt);
			break;

		case R.id.dateofdisp:

			edttxt= (EditText)findViewById(R.id.editText_dateofdisp);
			setDate(edttxt);
			break;
		case R.id.dateofrecieved:
			setDate(edttxt_recievedDate);
			break;

		case R.id.dateofrecieved_cfa:
			setDate(edttxt_recieveddatecfa);
			break;

		default:
			break;
		}
	}


	private void getInvoiceDetails(){

		if(adapter!=null){
			String jsonString  = "";
			HashMap<Integer, Invoice> dataMap = adapter.getCheckedMap();
			if(!(dataMap.size()==0)){

				jsonString = getJSonStringForInvoices(dataMap);
				getDealerInvoiceDetailsList(jsonString);
			}else{
				Util.showToast(getCurrentContext(), "Please select invoice", Toast.LENGTH_SHORT).show();
			}
		}else{
			Util.showToast(getCurrentContext(), "Can't get details... Please try later", Toast.LENGTH_SHORT).show();

		}
	}

	private String getJSonStringForInvoices(HashMap<Integer, Invoice> datamap){


		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(Entry<Integer, Invoice> entry :datamap.entrySet()){

			Invoice invoice = entry.getValue();
			builder.append("{");
			builder.append("'");
			builder.append(INVOICENO);
			builder.append("'");
			builder.append(":");
			builder.append("'");
			builder.append(invoice.getInvoiceNo());
			builder.append("'");
			builder.append(",");

			builder.append("'");
			builder.append(USERTYPEID);
			builder.append("'");
			builder.append(":");
			builder.append("'");
			builder.append(pref.getString(Constants.USERID, "0"));
			builder.append("'");
			builder.append(",");
			builder.append("}");
			builder.append(",");
		}

		builder.append("]");

		Log.i("Json", builder.toString());
		return builder.toString();
	}

	private void getDealerInvoiceDetailsList(String json){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Invoice details...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					String[] array = result.toString().split("@");
					ArrayList<Invoice> invoiceList = FetchingdataParser.getInvoiceDetailList(array[0]);
					HashMap<String, Object> invoicetotalData = FetchingdataParser.getInvoiceTotalDetails(array[1]);
					if(invoiceList==null || invoicetotalData ==null){
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
						// Populate dealer invoice detail  List
						InvoiceDetailAdapter adapter = new InvoiceDetailAdapter(getCurrentContext(), R.layout.cforn_invoice_detail_cell, invoiceList);
						mlistInvoiceDetails.setAdapter(adapter);

						// Show Details
						showInvoiceTotalDetails(invoicetotalData);
					}

				}
			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(CFormActivity.this, Constants.PREF_NAME);
		try {
			//
			requestdata.put("InvoiceJSON", json);
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerInvoiceDetails";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}


	private void getPendingCformforAMCFAList(String cformid){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading Invoice details...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					String[] array = result.toString().split("@");
					ArrayList<Invoice> invoiceList = FetchingdataParser.getInvoiceDetailList(array[0].replace("dtCFormDetailsAtFA", "dtDealerInvoice"));
					HashMap<String, Object> invoicetotalData = FetchingdataParser.getInvoiceTotalDetails(array[1].replace("dtGrossInvoiceFA", "dtGrossInvoice"));
					if(invoiceList==null || invoicetotalData ==null){
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
						// Populate dealer invoice detail  List
						InvoiceDetailAdapter adapter = new InvoiceDetailAdapter(getCurrentContext(), R.layout.cforn_invoice_detail_cell, invoiceList);
						mlistInvoiceDetails.setAdapter(adapter);

						// Show Details
						showInvoiceTotalDetails(invoicetotalData);
					}

				}
			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(CFormActivity.this, Constants.PREF_NAME);
		try {
			//
			requestdata.put("CFormId ", cformid);
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetCFormDetailsAtFA";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	/**
	 *  Set Invoice details for selected invoices.
	 * @param data
	 */
	private void showInvoiceTotalDetails(HashMap<String, Object> data){

		TextView txt1 = (TextView)findViewById(R.id.txt1);
		TextView txt2 = (TextView)findViewById(R.id.txt2);
		TextView txt3 = (TextView)findViewById(R.id.txt3);
		TextView txt4 = (TextView)findViewById(R.id.txt4);
		TextView txt5 = (TextView)findViewById(R.id.txt5);
		txt1.setText(data.get(Constants.noofinvoice).toString());
		txt2.setText(data.get(Constants.noofproducts).toString());
		txt3.setText(data.get(Constants.quantity).toString());
		txt4.setText(data.get(Constants.totalvolume).toString());
		txt5.setText(data.get(Constants.pretaxamount).toString());

	}


	/**
	 *  Set Date .
	 * @param editview EditText View in which 
	 * selected date has to be set.
	 */
	private void setDate( final EditText editview){

		Calendar cal = Calendar.getInstance();
		DatePickerDialog datePicker = new DatePickerDialog(getCurrentContext(), new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
				// TODO Auto-generated method stub
				editview.setText(String.valueOf(monthOfYear+1)+"/"+ String.valueOf(dayOfMonth)+"/"+ String.valueOf(year));
			}
		}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		datePicker.setMessage("Please select Date");
		datePicker.show();
	}

	private OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
			// TODO Auto-generated method stub

			switch (arg0.getId()) {
			case R.id.spinner_modeofdispatch:

				if(arg2==1){
					mlayouthand.setVisibility(View.VISIBLE);
					mlayoutbypost.setVisibility(View.GONE);
					if(!isNameOfPersonRecieved)
						getNameOfPersonList();
				}
				if(arg2==2){
					mlayoutbypost.setVisibility(View.VISIBLE);
					mlayouthand.setVisibility(View.GONE);
				}

				break;

			case R.id.spinner_nameofperson:
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

	/**
	 *  Get List of name of person .
	 */
	private void getNameOfPersonList(){

		GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading name of persons...", new GetDataCallBack() {

			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					ArrayList<HashMap<String , String>> nopList = FetchingdataParser.getNameOfPerson(result.toString());
					if(nopList==null){
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
						isNameOfPersonRecieved = true;
						SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), nopList, android.R.layout.simple_spinner_item, new String[]{Constants.CONTACTNAME}, new int[]{android.R.id.text1});
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinner_nop.setAdapter(adapter);

					}
				}
			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=Util.getSharedPreferences(CFormActivity.this, Constants.PREF_NAME);
		try {
			//
			requestdata.put("ContactTypeID", 1);
			requestdata.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			requestdata.put(Constants.password,passworddetails.getString(Constants.PASSWORD,""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetInternalContactList";
		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	}

	private void bindSpinnerAdapter (ArrayList<String> datalist, Spinner spinner){

		ArrayAdapter<String> adpter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,datalist);
		adpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adpter);
	}

	private void uploadData(){


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
						getRequestDataMap().clear();
						finish();
					}
				});
				AlertDialog dialog = errordialog.create();
				dialog.show();
			}

		});

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_Sale);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="";
		if(isDealer){
			methodName = "SaveCFormEntryByDealer";
		}else if(isAreaManager || isCFA){
			methodName = "UpdateCFormEntryByAMORCFA";
		}

		String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(getRequestDataMap());
		datafromnetwork.execute();

	}

	/**
	 * Get InvoiceNo Json.
	 * @param invoiceList
	 * @return
	 */
	private String getInvoiceIdJSON(ArrayList<Invoice> invoiceList ){

		StringBuilder builder = new StringBuilder();
		if(invoiceList!=null){

			builder.append("[");

			for(Invoice invoice : invoiceList){
				builder.append("{");
				builder.append("'");
				builder.append(INVOICEID);
				builder.append("'");
				builder.append(":");
				builder.append("'");
				builder.append(invoice.getInvoiceId());
				builder.append("'");
				builder.append("}");
				builder.append(",");
			}
			builder.append("]");
			Log.i("Invoice Id String", builder.toString());
			return builder.toString();
		}

		return "0";
	}


}
