package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.PartNoAllCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DealerPurchageHistory extends BaseActivity {
	
	private Spinner spnr_segment,spnr_oe,spnr_appication,spnr_partNo;
	
	private Button btn_get,btn_startDate,btn_startTime;
	private int SegmentID=0,OEID=0,ApplicationID=0;
	EditText et_date_from,et_date_to;
	 PartNoAllCustomAlertAdapter partNoAdapter;
	 ArrayList<PartNoModel> partnoList;
	 private EditText part_no_edit;
	 AlertDialog dialog;
	int ParntNo;
	TextView quantiy;
	TextView value;
	TextView pendingOrder;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealer_purchage_history);
		setCurrentContext(this);
		
		
		et_date_from=(EditText) findViewById(R.id.et_date_from);
		et_date_to=(EditText) findViewById(R.id.et_date_to);
		
		btn_get=(Button) findViewById(R.id.get_button);
		btn_get.setOnClickListener(this);
		
		btn_startDate = (Button)findViewById(R.id.date_from);

		btn_startDate.setOnClickListener(this);
		
		btn_startTime = (Button)findViewById(R.id.date_to);
		btn_startTime.setOnClickListener(this);
		
		part_no_edit=(EditText) findViewById(R.id.part_no);
		
		spnr_segment = (Spinner)findViewById(R.id.spinner_segment);
		spnr_segment.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String segmentid = data.get(Constants.ID);
					getOETroughSegment(segmentid );
					
					SegmentID= Integer.parseInt(segmentid);
					getPartNo(SegmentID,0,0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		spnr_oe = (Spinner)findViewById(R.id.spinner_oe);
		spnr_oe.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String segmentid = data.get(Constants.ID);
					//Function GetProductSegmentationDetail_MIS(RequestType As String, ParentID As String, ByVal UserID As String, ByVal Password As String) 
					getApplications(segmentid);
					OEID= Integer.parseInt(segmentid);
					getPartNo(SegmentID,OEID,0);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		spnr_appication = (Spinner)findViewById(R.id.spinner_application);

		spnr_appication.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					String applicationid = data.get(Constants.ID);
					ApplicationID= Integer.parseInt(applicationid);
					getPartNo(SegmentID,OEID,ApplicationID);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		spnr_partNo = (Spinner)findViewById(R.id.spinner_partno);
		spnr_partNo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					ParntNo= Integer.parseInt(data.get(Constants.ProductID));
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		getSegment();
		getPartNo(0,0,0);
		part_no_edit.setFocusable(false);
		part_no_edit.setOnClickListener(this);
	}
	

	private void getItemQuantityValue(int segmentID, int oEID,
			int applicationID) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getpuchageItemQuantityValueList(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
						 quantiy=(TextView) findViewById(R.id.tvQuanity);
					     value=(TextView) findViewById(R.id.tvvalue);
					     pendingOrder=(TextView) findViewById(R.id.tv_peding_order);
							
							
							quantiy.setText(itemQuantityValueList.get(0).get(Constants.DIRECTDEALERQUANTITY ));
							value.setText(itemQuantityValueList.get(0).get(Constants.VALUE ));
							pendingOrder.setText(itemQuantityValueList.get(0).get(Constants.PendingOrder));
							
						}
						

					}else {
						quantiy.setText("");
						value.setText("");
						pendingOrder.setText("");
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
try {       
	//Function DealerPurchaseHistory(ByVal SegmentID As String, ByVal OEID As String,
	//ByVal ApplicationId As String, ByVal ProductID As String, 
	//ByVal StartDate As DateTime, ByVal ENDDate As DateTime,
	//ByVal UserID As String, ByVal Password As String) As String

	      
	        request.put("DealerID",passworddetails.getString(Constants.DEALERID,""));
			request.put("SegmentID", String.valueOf(SegmentID));
			request.put("OEID", String.valueOf(OEID));
			request.put("ApplicationId", String.valueOf(ApplicationID));
			request.put("ProductID", String.valueOf(ParntNo));
			
			request.put("StartDate",et_date_from.getText().toString());
			request.put("ENDDate",et_date_to.getText().toString());
			
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerPurchaseHistory";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	}

	protected void getOETroughSegment(String segmentid) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> oeList = FetchingdataParser.getOE(response.toString());
					if(oeList!=null ){
						if (oeList.size()!=0) {
							SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), oeList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
							segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spnr_oe.setAdapter(segmentAdapter);
						}
						

					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);



		try {
			request.put("RequestType","OE");
			request.put("ParentID",segmentid);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegmentationDetail_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	}

	protected void getApplications(String segmentID) {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> applicationList = FetchingdataParser.getApplications(response.toString());
					if(applicationList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), applicationList, android.R.layout.simple_spinner_item, new String[]{Constants.NAME}, new int[]{android.R.id.text1});
						segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spnr_appication.setAdapter(segmentAdapter);

					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
     try {
			request.put("RequestType","APPLICATION");
			request.put("ParentID",segmentID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegmentationDetail_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	}
// Public Function GetAllProduct_MIS(SegmentID As Integer, OEID As Integer, ApplicationID As Integer, ByVal UserID As String, ByVal Password As String)

	private void getPartNo(int SegmentID,int OEID,int ApplicationID) {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

				    partnoList = FetchingdataParser.getPartNo(response.toString());
					if(partnoList!=null){
						//SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), partnoList, android.R.layout.simple_spinner_item, new String[]{Constants.CODE}, new int[]{android.R.id.text1});
					//	segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
	
			request.put("SegmentID",SegmentID);
			request.put("OEID",OEID);
			request.put("ApplicationID",ApplicationID);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetAllProduct_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}

	private void getSegment() {
		// TODO Auto-generated method stub



		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> segmentList = FetchingdataParser.getSegment(response.toString());
					if(segmentList!=null){
						SimpleAdapter segmentAdapter = new SimpleAdapter(getCurrentContext(), segmentList, android.R.layout.simple_spinner_item, new String[]{Constants.SEGMENTNAME}, new int[]{android.R.id.text1});
						segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
						spnr_segment.setAdapter(segmentAdapter);

					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();

					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
	try {
			request.put("RequestType","SEGMENT");
			request.put("ParentID","0");
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetProductSegmentationDetail_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		Calendar cal = Calendar.getInstance();
		
		
		switch (v.getId()) {
		case R.id.get_button:
			
			if(et_date_from.getText().toString().length()>0 && et_date_to.getText().toString().length()>0){
				getItemQuantityValue(SegmentID,OEID,ApplicationID);
			}
			else {
				et_date_from.setError(getStringFromResource(R.string.date_from));
				et_date_to.setError(getStringFromResource(R.string.date_to));
			}
			
			break;
			
		case R.id.date_from:
	DatePickerDialog datePicker = new DatePickerDialog(getCurrentContext(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
					// TODO Auto-generated method stub
					
					et_date_from.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);
					
				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			datePicker.setTitle("Please select Date");
			datePicker.show();
			break;
			
		case R.id.date_to:
			

			DatePickerDialog datePicker_to = new DatePickerDialog(getCurrentContext(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
					// TODO Auto-generated method stub
					
					et_date_to.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);
					
				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			datePicker_to.setTitle("Please select Date");
			datePicker_to.show();
			
			break;
			
       case R.id.part_no:
			
    	   getSerachPartDetails();
			
			
			break;
		default:
			break;
		}
	}

	
	private void getSerachPartDetails() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
		
		EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
		ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
        AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
        builder.setView(view);
        partNoAdapter=new PartNoAllCustomAlertAdapter(getCurrentContext(), R.layout.am_manual_part_no_shell, partnoList);
            list_part_no.setAdapter(partNoAdapter);
            list_part_no.setOnItemClickListener(new OnItemClickListener() {

		

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long arg3) {
					// TODO Auto-generated method stub
			
					ParntNo = Integer.parseInt(partnoList.get(position).getProductID());
			
					 Toast.makeText(getCurrentContext(),partnoList.get(position).getCode(), Toast.LENGTH_LONG).show();
					 part_no_edit.setText(partnoList.get(position).getCode());
					
					 dialog.dismiss();
					
				}
				
			});
            
            
            searchpartNo.addTextChangedListener(new TextWatcher() {

    			@Override
    			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    				// TODO Auto-generated method stub

    				if(DealerPurchageHistory.this.partNoAdapter!=null)
    					DealerPurchageHistory.this.partNoAdapter.getFilter().filter(arg0);
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
            
            
            builder.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            
            dialog = builder.show();
	
	
		
		
	}

	
	
	
}
