package com.arcis.vgil.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.AM_DealerManualAdapter;
import com.arcis.vgil.adapter.PartCustomAlertAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AM_DealerManualOrder;
import com.arcis.vgil.model.DealerDealerSale;
import com.arcis.vgil.parser.FetchingdataParser;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DelaerMannualOrderActivityTwo extends BaseActivity  {
	
private EditText partNo;
private Button addItem,Submit;
private ListView mListView;
private Spinner cust_code_spinner;
private EditText cust_name, cust_city;
PartCustomAlertAdapter arrayAdapter;
String dealerId,DEALERCODE;
String contacttypeId ;
ArrayList<DealerDealerSale> salelist;
ArrayList<HashMap<String,Object>> arealist;
String ContactType,ContactID;
ArrayList<String> countryname,countryid;
AlertDialog dialog;
TextView pending_position,git_position,inventry_position,quantity_position;
private HashMap<String , String> dataMap;
private ArrayList<AM_DealerManualOrder> orderList = new ArrayList<AM_DealerManualOrder>();
private AM_DealerManualAdapter mManualAdapter;
ArrayList<AM_DealerManualOrder> requestBean=new ArrayList<AM_DealerManualOrder>();
String productID;

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setContentView(R.layout.dealer_manual_order_two);
		setCurrentContext(this);
		SharedPreferences spref=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		ContactType="DLR";
		ContactID =spref.getString(Constants.DEALERID, "");
		initiView();
		uiListner();
		arealist=new ArrayList<HashMap<String,Object>>();
		countryname=new ArrayList<String>();
    	countryid  =new ArrayList<String>();
 
	
		 
		GetMappedGeographyForLogin(ContactType,Constants.GEOCODE_DLR,"0",ContactID,"0","0");
	}

	
	private void initiView() {
		// TODO Auto-generated method stub
		partNo=(EditText) findViewById(R.id.mannual_part_no);
		cust_city=(EditText) findViewById(R.id.mannual_city);
		cust_name=(EditText) findViewById(R.id.mannual_customer_name);
		mListView=(ListView) findViewById(R.id.mannual_oder_lV);
		addItem=(Button) findViewById(R.id.additem);
		Submit=(Button) findViewById(R.id.submit);
		cust_code_spinner=(Spinner) findViewById(R.id.cust_code_spinner);
		pending_position=(TextView) findViewById(R.id.pending_position);
		git_position=(TextView) findViewById(R.id.git_position);
		inventry_position=(TextView) findViewById(R.id.inventry_position);
		quantity_position=(EditText) findViewById(R.id.quantity_position);
	
	}
	
	private void uiListner() {
		// TODO Auto-generated method stub
		partNo.setOnClickListener(this);
		addItem.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		Submit.setOnClickListener(this);
		cust_code_spinner.setOnItemSelectedListener( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
				// TODO Auto-generated method stub
              if(position!=0){
              DEALERCODE=countryid.get(cust_code_spinner.getSelectedItemPosition());
		      GetDealerDetails(countryid.get(cust_code_spinner.getSelectedItemPosition()));
		     
             }
              else {
            	  
				Toast.makeText(getCurrentContext(), "Please select customer code ", Toast.LENGTH_SHORT);
			}
              
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				DEALERCODE="0";
			}
		});
		
	}
	
	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			super.onClick(v);
			switch (v.getId()) {
			case R.id.mannual_part_no:
				
				try {
					if (!DEALERCODE.equals(null)||!DEALERCODE.isEmpty()) {
						 getProductList(DEALERCODE);
					}
					else {
						Toast.makeText(getCurrentContext(), "Please Select Dealer Code", Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					
					Toast.makeText(getCurrentContext(), "Please Select Dealer Code", Toast.LENGTH_LONG).show();
				}
				
				break;
				
			case R.id.additem:
				

				if(validation()){
					// Capture Data

					AM_DealerManualOrder order = CaptureData(dataMap);
					orderList.add(order);
					//updateItemAmount(order);
					clearScreen();

					if(mManualAdapter==null){
						mManualAdapter = new AM_DealerManualAdapter(getCurrentContext(), R.layout.additional_product_cell, orderList);
						mListView.setAdapter(mManualAdapter);
					}else{
						mManualAdapter.notifyDataSetChanged();
					}
					Toast.makeText(getCurrentContext(), "Item added successfully!", Toast.LENGTH_LONG).show();
					
				
				}

			
				
				break;
				
			case R.id.submit:
          if (orderList.size()>0) {
        	  String jsondata=null;
  			try{
  			jsondata = getJsonStringForManualOrder(orderList);
  			}catch(Exception ex){
  				ex.printStackTrace();
  				Toast.makeText(getCurrentContext(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
  			}
  			uploadManualOrder(DEALERCODE, jsondata);
		}
			else {
				Toast.makeText(getCurrentContext(), "Please Fill all required details!", Toast.LENGTH_LONG).show();
			}
			
			
			break;

			default:
				break;
			}
		}
	

	private void getProductList(String dealerId) {
		// TODO Auto-generated method stub
		


		 GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

			@Override
			 public void processResponse(Object result) {
				// TODO Auto-generated method stub
				if(result==null){
					Toast.makeText(getCurrentContext(), getCurrentContext().getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
				}else{
					Log.i(" DealerProdect Data", result.toString());
				    salelist = FetchingdataParser.getDealeMannualrSale(result.toString());
					if(salelist==null){
						AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
						errordialog.setTitle("Error!");
						errordialog.setMessage(result.toString());
						errordialog.setPositiveButton("Ok", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();	
							}
						});
						AlertDialog dialog = errordialog.create();
						dialog.show();
					}else{
						  if(salelist==null){
								AlertDialog.Builder errordialog = new AlertDialog.Builder(getCurrentContext());
								errordialog.setTitle("Error!");
								errordialog.setMessage("Data is Not available");
								errordialog.setPositiveButton("Ok", new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();	
									}
								});
								AlertDialog dialog = errordialog.create();
								dialog.show();
							}else{
								if(salelist.size()==0){
									Toast.makeText(getCurrentContext(), "No data found!", Toast.LENGTH_SHORT).show();
								}else{
									
									LayoutInflater inflator = (LayoutInflater) getCurrentContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
									View view  = inflator.inflate(R.layout.mannual_part_no_search_dialog, null);
									
									EditText searchpartNo = (EditText)view.findViewById(R.id.et_search);
									ListView list_part_no = (ListView)view.findViewById(R.id.list_part_no);
						            AlertDialog.Builder  builder= new AlertDialog.Builder(getCurrentContext());
						            builder.setView(view);
									 arrayAdapter=new PartCustomAlertAdapter(getCurrentContext(), R.layout.am_manual_part_no_shell, salelist);
							            list_part_no.setAdapter(arrayAdapter);
							            list_part_no.setOnItemClickListener(new OnItemClickListener() {

									

											@Override
											public void onItemClick(AdapterView<?> parent, View view,
                                                                    int position, long arg3) {
												// TODO Auto-generated method stub
												String product = salelist.get(position).getCode();
												
												 Toast.makeText(getCurrentContext(),product, Toast.LENGTH_LONG).show();
												 partNo.setText(product);
												 addingVlaueForList();
												 dialog.dismiss();
												
											}
											
										});
							            
							            
							            searchpartNo.addTextChangedListener(new TextWatcher() {

							    			@Override
							    			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							    				// TODO Auto-generated method stub

							    				if(DelaerMannualOrderActivityTwo.this.arrayAdapter!=null)
							    					DelaerMannualOrderActivityTwo.this.arrayAdapter.getFilter().filter(arg0);
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
							                    new OnClickListener() {
							                        @Override
							                        public void onClick(DialogInterface dialog, int which) {
							                            dialog.dismiss();
							                        }
							                    });
							            
							            dialog = builder.show();
								
								}
							}
				        
				           
					}

				}

			}

		});

		LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		try {
			//Constants.contactTypeIDForDealer
			requestdata.put(Constants.CONTACTID, dealerId);
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
	

	private void GetMappedGeographyForLogin(final String contactType , final String geoName, final String geoId, final String ContactID, final String stateId, final String cityId) {
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading  Details..", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
					arealist.clear();
					arealist=new FetchingdataParser().getarealistparserDealerCode(response.toString());
					if(arealist.size()==0){
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" , Toast.LENGTH_SHORT).show();
					}else {

						for (HashMap<String, Object> entry : arealist)
						{

							String geoids=(String)entry.get(Constants.GeoID);
							String geonames=(String)entry.get(Constants.GeoName);



							if(geoids!=null && geonames!=null){

								if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
									if(!countryid.contains(geoids))
										countryid.add(geoids);
									if(!countryname.contains(geonames))
										countryname.add(geonames);
								}

								
							}
						}
						

						if(geoName.equalsIgnoreCase(Constants.GEOCODE_DLR)){
							ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getCurrentContext(), android.R.layout.simple_spinner_item,countryname);
							countryAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
							cust_code_spinner.setAdapter(countryAdapter);
						}
						
						
					}

				}else if(response==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) , Toast.LENGTH_SHORT).show();
				}
			}
		});

		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			/*
			 * Function GetGeographyByLogin
			 * (ByVal ContactType As String, ByVal GeoType As String, ByVal GeoID As String, 
			 * ByVal ContactID As String, ByVal StateID As String, ByVal CityID As String, 
			 * ByVal UserID As String, ByVal Password As String) As String
		*/ 
			request.put("ContactType", contactType);
			request.put(Constants.GeoName,geoName);
			request.put("GeoID",geoId);
			request.put("ContactID", ContactID);
			request.put(Constants.stateID, stateId);
			request.put(Constants.cityID, cityId);
			request.put(Constants.username, passworddetails.getString("username",""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.Webservice_mis_android);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetGeographyByLogin";
		String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	
	protected void GetDealerDetails(String dealerId) {
			// TODO Auto-generated method stub

		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(getCurrentContext(), ProgressDialog.STYLE_SPINNER,"Loading...", new GetDataCallBack() {
			@Override
			public void processResponse(Object response) {
				if(response!=null){
                      
					ArrayList<HashMap<String, String>> itemQuantityValueList = FetchingdataParser.getAm_Cus_City_Name(response.toString());
					if(itemQuantityValueList!=null ){
						if (itemQuantityValueList.size()!=0) {
							
							cust_city=(EditText) findViewById(R.id.mannual_city);
							cust_name=(EditText) findViewById(R.id.mannual_customer_name);
							cust_city.setText(itemQuantityValueList.get(0).get(Constants.city));
							cust_name.setText(itemQuantityValueList.get(0).get(Constants.DEALERNAME));
							
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
	
			//GetDealerDetails(ByVal Type As String, ByVal DealerID As String, ByVal UserID As String, ByVal Password As String)
			request.put("External", "External");
			request.put("DealerID", dealerId);
			request.put(Constants.username, passworddetails.getString(Constants.ID,""));
			request.put(Constants.password,passworddetails.getString("password",""));


		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName="GetDealerDetails";
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	

		
	}


	public  void addingVlaueForList(){
		
		for (int i = 0; i < salelist.size(); i++) {
			
			if (salelist.get(i).getCode().equalsIgnoreCase(partNo.getText().toString())) {
				
				pending_position.setText(salelist.get(i).getPending_order());
				git_position.setText(salelist.get(i).getGitQuantity());
				inventry_position.setText(salelist.get(i).getInventory());
				productID=(salelist.get(i).getProductID());
				
			}
			
		}
		 
	 }
	
	 
	 private AM_DealerManualOrder CaptureData(HashMap<String, String> productMap){

		AM_DealerManualOrder order = new AM_DealerManualOrder();
		//order.setDealerName(partNo.getText().toString());
		order.setProductCode(partNo.getText().toString());
		order.setPending(pending_position.getText().toString());
		order.setGit(git_position.getText().toString());
		order.setInventory(inventry_position.getText().toString());
		order.setQuantity(quantity_position.getText().toString());
		order.setProductId(productID);
		//order.setUnitPrice(Double.parseDouble(productMap.get(Constants.UNITPRICE)));
		//order.setDiscountpercentage(Double.parseDouble(productMap.get(Constants.DISCOUNTPERCENTAGE)));
		//order.setProductId(productMap.get(Constants.ID));
		//order.setDealerID(getDealerId());
		return order;
	}
	 private void clearScreen(){

		    partNo.setText("");
		    pending_position.setText("");
		    git_position.setText("");
		    inventry_position.setText("");
		    quantity_position.setText("");
		}
	 

    @Override
	public boolean validation() {
		boolean isValid = true;
		String errMsg = getResources().getString(R.string.error3);
		
		if(quantity_position.getText().toString().length()==0){
			quantity_position.setError(getResources().getString(R.string.quantity));
			//errMsg = errMsg.concat(" " +);
			isValid = false;
		}

		if(partNo.getText().toString().length()==0||partNo.getText().toString().length()<10){
			partNo.setError(getResources().getString(R.string.partno));

			isValid = false;
		}
		
		if(!isValid){
			Toast.makeText(getCurrentContext(), errMsg, Toast.LENGTH_LONG).show();
		}
		return isValid;
	}
	@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			// TODO Auto-generated method stub
			super.onItemClick(parent, view, position, id);
			

            AlertDialog.Builder alert = new AlertDialog.Builder(
            		DelaerMannualOrderActivityTwo.this);
            alert.setTitle("Alert!!");
            alert.setMessage("Are you sure to delete record");
            alert.setPositiveButton("YES", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                                       
                	//do your work here
                	orderList.remove(position);
                	mManualAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    

                }
            });
            alert.setNegativeButton("NO", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();

 
		}
	
	
	private void uploadManualOrder(String dealerId, String detailsData) {
		// TODO Auto-generated method stub


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
						errordialog.setTitle(getResources().getString(R.string.error6));
						if (result != null) {
							errordialog.setMessage(result.toString());
						} else {
							errordialog.setMessage(getResources().getString(R.string.error4));
						}

					} else {
						errordialog.setMessage(getResources().getString(R.string.message5));
				
						
						

					}
				 	 errordialog.setPositiveButton("Ok", new OnClickListener() {

						@Override
						  public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							dialog.dismiss();	
							
							clearScreen();
							orderList.clear();
							mManualAdapter.notifyDataSetChanged();
						}
					});
					AlertDialog dialog = errordialog.create();
					dialog.show();

				}

			});

			LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
			SharedPreferences passworddetails=getCurrentContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
			try {
				requestdata.put(Constants.DEALERID_1, dealerId);
				requestdata.put(Constants.totalAmount, "0");
				requestdata.put(Constants.discount, "0");
				requestdata.put(Constants.orderamount,"0");
				requestdata.put(Constants.jsonstring, detailsData);
				requestdata.put(Constants.username, passworddetails.getString("username",""));
				requestdata.put(Constants.password,passworddetails.getString("password",""));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String ipAddress=getResources().getString(R.string.ipaddress);
			String webService =getResources().getString(R.string.Webservice_Sale);
			String nameSpace=getResources().getString(R.string.nameSpace);
			String methodName="SaveDealerManualOrder";
			String soapcomponent=getResources().getString(R.string.soapcomponent_sale);
			datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
			datafromnetwork.sendData(requestdata);
			datafromnetwork.execute();
		
	}
	
	/**
	 * Json String containing coupon information. 
	 * @param eventid
	 * @param coupontextList
	 * @return json SAtructured String .
	 */
 	private String getJsonStringForManualOrder(ArrayList<AM_DealerManualOrder> requestBean){



		StringBuilder dataString  = new StringBuilder();
		dataString.append("[");


		for(int i=0;i<requestBean.size();i++){
			AM_DealerManualOrder am_dealerDealerSale = requestBean.get(i);
			dataString.append("{");
			dataString.append("'");
			dataString.append("ProductID");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(am_dealerDealerSale.getProductId());
			dataString.append("'");
			dataString.append(",");
			
			
			dataString.append("'");
			dataString.append("Qty");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append(am_dealerDealerSale.getQuantity());
			dataString.append("'");
			dataString.append(",");
			
			dataString.append("'");
			dataString.append("UnitPrice");
			dataString.append("'");
			dataString.append(":");
			dataString.append("'");
			dataString.append("0");
			dataString.append("'");
			dataString.append("}");
			dataString.append(",");
			
			
			
		}
		dataString.append("]");
		Log.i("Server data String ", dataString.toString());

		return dataString.toString();
	}





}
