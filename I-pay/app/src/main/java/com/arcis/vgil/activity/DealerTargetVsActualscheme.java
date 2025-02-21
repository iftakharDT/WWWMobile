package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DealerTargetVsActualscheme extends BaseActivity {
	
	private Spinner spinner_partno,spinner_shemename;
	String scheme_Name, part_Name;
	ListView mListView;
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealer_target_vs_actual_scheme);
		setCurrentContext(this);
		mListView=(ListView) findViewById(R.id.listView1);
		spinner_partno=(Spinner) findViewById(R.id.spinner_partno);
		spinner_shemename=(Spinner) findViewById(R.id.spinner_shemename);
		
		spinner_partno.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					//Function RetailersDealer_MIS(RetailerID As String, CityID As String) As String
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					part_Name = data.get(Constants.GroupName);
					DealerSchemes(part_Name);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		spinner_shemename.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
	
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
					scheme_Name = data.get(Constants.SaleTargetID);
					DealerTargetVsSales();
					
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		/* 
        Function DealerTargetVsSales(TargetID As String, ByRef dt As DataSet) As String */

	 
		DealerTargerProductGroups();
	//	DealerSchemes();
	}

	

	
	private void DealerTargerProductGroups() {
		// TODO Auto-generated method stub
	
		
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> Direct_dealer_SearchList = FetchingdataParser.getDealerTargerProductGroups(response.toString());
					if(Direct_dealer_SearchList!=null ){
						if (Direct_dealer_SearchList.size()!=0) {
							SimpleAdapter direct_dealer_SearchListAdapter = new SimpleAdapter(getCurrentContext(), Direct_dealer_SearchList, android.R.layout.simple_spinner_item, new String[]{Constants.GroupName}, new int[]{android.R.id.text1});
							direct_dealer_SearchListAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spinner_partno.setAdapter(direct_dealer_SearchListAdapter);
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
			
		//	Function DealerTargerProductGroups(DealerID As String) As String
			
			request.put(Constants.dealerid, passworddetails.getString(Constants.DEALERID, ""));
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerTargerProductGroups";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		
	}


	
	private void DealerSchemes(String part_Name) {
		// TODO Auto-generated method stub
	
		
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> Direct_dealer_SearchList = FetchingdataParser.getDealerSchemes(response.toString());
					if(Direct_dealer_SearchList!=null ){
						if (Direct_dealer_SearchList.size()!=0) {
							SimpleAdapter direct_dealer_SearchListAdapter = new SimpleAdapter(getCurrentContext(), Direct_dealer_SearchList, android.R.layout.simple_spinner_item, new String[]{Constants.SchemeName}, new int[]{android.R.id.text1});
							direct_dealer_SearchListAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							spinner_shemename.setAdapter(direct_dealer_SearchListAdapter);
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
		//	 Function DealerSchemes(TargetGroupName As String) As String

			request.put("TargetGroupName", part_Name);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerSchemes";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		
	}
	
	private void DealerTargetVsSales() {
		// TODO Auto-generated method stub
	
		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, String>> amTerretoryList;
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					amTerretoryList = new FetchingdataParser()
					.getDealerTargetVsSales(result
							.toString());
			if (amTerretoryList.size() == 0) {
				Toast.makeText(getCurrentContext(),
						result.toString(),
						Toast.LENGTH_SHORT).show();
			} else {
			
				TextView from_date= (TextView) findViewById(R.id.et_from);
				TextView to_date= (TextView) findViewById(R.id.et_to);
		        from_date.setText(amTerretoryList.get(0).get(Constants.StartDate));
		        to_date.setText(amTerretoryList.get(0).get(Constants.EndDate));
				
					
				
				
            
				SimpleAdapter adapter = new SimpleAdapter(
						getCurrentContext(),
						amTerretoryList,
						R.layout.dealer_target_vs_sales_cell,
						new String[] {
							
							Constants.SN, 
							Constants.Actual,
						    Constants.TargetValue,
						    Constants.Increment
								
								},
						new int[] { 
							     R.id.text1,
								 R.id.text2, 
				                 R.id.text3,
				                 R.id.text4,
								 });
				                 mListView.setAdapter(adapter);

			}
		}

			}

		});
		 
		
		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		
		try {
			
		
			requestdata.put("TargetID",scheme_Name);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="DealerTargetVsSales";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	
		
	
		
	
		
	}


	
}
