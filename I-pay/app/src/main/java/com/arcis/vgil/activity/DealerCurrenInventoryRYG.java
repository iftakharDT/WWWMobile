package com.arcis.vgil.activity;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.PartNoAllCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.PartNoModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DealerCurrenInventoryRYG extends BaseActivity  {
	/*
	 * Stock report
	*/
	private Spinner spnr_segment,spnr_oe,spnr_appication/*,spnr_partNo*/;
	private ListView mListView;
	
	AlertDialog dialog;
	private EditText part_no_edit;
	private Button btn_get;
	
	private int SegmentID=0,OEID=0,ApplicationID=0;
	int ParntNo;
	PartNoAllCustomAlertAdapter partNoAdapter;
	ArrayList<PartNoModel> partnoList;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealercurren_inventory_ryg);
		setCurrentContext(this);
		
		mListView = (ListView)findViewById(R.id.lv_delaer_ryg);
		btn_get=(Button) findViewById(R.id.get_button);
		btn_get.setOnClickListener(this);
		
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
		
		/*spnr_partNo = (Spinner)findViewById(R.id.spinner_partno);
		spnr_partNo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position!=0){
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					ParntNo=Integer.parseInt(data.get(Constants.ProductID));
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		*/
		
		
		getSegment();
		getPartNo(0,0,0);
		part_no_edit=(EditText) findViewById(R.id.part_no);
		part_no_edit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.get_button:
			getItemQuantityValue(SegmentID,OEID,ApplicationID);
			break;
			
		case R.id.part_no:
			
			getSerachPartDetails();
			
			break;

		default:
			break;
		}
	}
	
	
	private void getItemQuantityValue(int segmentID, int oEID,
			int applicationID) {
		// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){

					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getDealerItemQuantityRYG(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							SimpleAdapter adapter  = new SimpleAdapter(getCurrentContext(), itemQuantityValueList, R.layout.dealer_stockreportryg_signle_view,
									new String[]{Constants.Product_Code,Constants.CurrentStock,Constants.RGap,Constants.YGap,Constants.GGap},
									new int[]{R.id.product_code,R.id.current_stock,R.id.redgap,R.id.yellow,R.id.greengap});
									mListView.setAdapter(adapter);	
						
							
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
	 
	        
	        request.put("DealerID", passworddetails.getString(Constants.DEALERID,""));
			request.put("SegmentID",SegmentID);
			request.put("OEID",OEID);
			request.put("ApplicationID",ApplicationID);
			request.put("ProductID",ParntNo);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerCurrentInventoryWithRYG_MIS";
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
						////segmentAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
					//	spnr_partNo.setAdapter(segmentAdapter);

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

    				if(DealerCurrenInventoryRYG.this.partNoAdapter!=null)
    					DealerCurrenInventoryRYG.this.partNoAdapter.getFilter().filter(arg0);
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
