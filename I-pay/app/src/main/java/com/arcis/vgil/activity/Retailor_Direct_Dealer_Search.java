package com.arcis.vgil.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Retailor_Direct_Dealer_Search extends BaseActivity{
	private Button search;
	private ListView mListView;
	ArrayList<String> cityname,cityidd;
	private Spinner cityspinner;
	String cityID ="0";
	
	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.retailor_direct_dealer_search);
		setCurrentContext(this);
		cityspinner=(Spinner) findViewById(R.id.cityspinner);
		mListView=(ListView) findViewById(R.id.listView1);
		search=(Button) findViewById(R.id.search);
		search.setOnClickListener(this);
		directDealerCityList();

		cityname=new ArrayList<String>();
		//cityname.add("Pleade Select");
		cityidd=new ArrayList<String>();
		//cityidd.add("0");
		
		cityspinner=(Spinner)findViewById(R.id.cityspinner);
		cityspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				if(position!=0){
					//Function RetailersDealer_MIS(RetailerID As String, CityID As String) As String
					HashMap<String, String> data = (HashMap<String, String>) arg0.getSelectedItem();
				      cityID = data.get(Constants.cityID);
					direct_Direct_Dealer_searchList();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
  
	
	
	private void directDealerCityList() {
		// TODO Auto-generated method stub
	
		
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> Direct_dealer_SearchList = FetchingdataParser.getDirect_dealer_Search(response.toString());
					if(Direct_dealer_SearchList!=null ){
						if (Direct_dealer_SearchList.size()!=0) {
							SimpleAdapter direct_dealer_SearchListAdapter = new SimpleAdapter(getCurrentContext(), Direct_dealer_SearchList, android.R.layout.simple_spinner_item, new String[]{Constants.city}, new int[]{android.R.id.text1});
							direct_dealer_SearchListAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							cityspinner.setAdapter(direct_dealer_SearchListAdapter);
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
			//	// RetailersDealerCity_MIS(RetailerID As String)
		
			
			request.put("RetailerID", passworddetails.getString(Constants.USERID, ""));
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="RetailersDealerCity_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	
	
		
	
		
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		direct_Direct_Dealer_searchList();
	}

	private void direct_Direct_Dealer_searchList() {
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
					.getdirect_Direct_Dealer_searchList(result
							.toString());
			if (amTerretoryList.size() == 0) {
				Toast.makeText(getCurrentContext(),
						result.toString(),
						Toast.LENGTH_SHORT).show();
			} else {
            
				SimpleAdapter adapter = new SimpleAdapter(
						getCurrentContext(),
						amTerretoryList,
						R.layout.ret_direct_dealer_search_cell,
						new String[] {Constants.DEALERNAME,
							Constants.CONTACTTYPENAME_2,
								Constants.Phone,
								Constants.streetaddress,
								
								Constants.state,
								Constants.city,
								Constants.area ,
								Constants.PinCode},
						new int[] { R.id.firm_name,
							     R.id.text1,
								 R.id.text2, 
								 R.id.text6,
                                 R.id.text3,
								 R.id.text4, 
								 R.id.text5, 
								 R.id.text7});
				       mListView.setAdapter(adapter);

			}
		}

			}

		});
		 
		// Function DealerGoodsReceive_Master_Android(DealerID As String, ByVal UserID As String, ByVal Password As String) As String

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			//	//Function RetailersDealer_MIS(RetailerID As String, CityID As String) As String
			String jai=passworddetails.getString(Constants.RETAILERID, "");
			requestdata.put("RetailerID",passworddetails.getString(Constants.USERID, ""));
			requestdata.put(Constants.cityID, cityID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="RetailersDealer_MIS";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		datafromnetwork.sendData(requestdata);
		datafromnetwork.execute();
	
		
	
		
	
		
	}


}
