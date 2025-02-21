package com.arcis.vgil.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.arcis.vgil.R;
import com.arcis.vgil.adapter.CouponComplainAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetworkList;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.CouponComplainModerl;
import com.arcis.vgil.parser.FetchingdataParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ComplainHistory  extends BaseActivity{
	PullToRefreshListView coupon_complain_list;
	ImageView no_data_Image;
	TextView user_name;
	CouponComplainAdapter  couponComplainAdapter;
	ArrayList<CouponComplainModerl> couponComplainlist;
	
	ListView actualListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_complain_history);
		setCurrentContext(this);
		SharedPreferences pref=Util.getSharedPreferences(this, Constants.PREF_NAME);
		TextView	tv=(TextView) findViewById(R.id.user_name);
		tv.append(" "+ pref.getString("contactname", ""));
		
		coupon_complain_list=(PullToRefreshListView) findViewById(R.id.coupon_complain_list);
		no_data_Image=(ImageView) findViewById(R.id.no_data_Image);
		getCouponComplainListData();
		
		// Set a listener to be invoked when the list should be refreshed.
		coupon_complain_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(PullToRefreshBase<ListView> refreshView) {
						
						getCouponComplainListData();
						
					}
				});
		
		


	}
	private void getCouponComplainListData() {
		// TODO Auto-generated method stub
		GetDataFromNetworkList dataFromNetwork= new GetDataFromNetworkList(getCurrentContext(),coupon_complain_list, ProgressDialog.STYLE_SPINNER, "Loading.....", new GetDataCallBack() {
			
			@Override
			public void processResponse(Object result) {
				// TODO Auto-generated method stub

				if(result!=null){
                    couponComplainlist = FetchingdataParser.getCouponComplainList(result.toString());
					if(couponComplainlist!=null){
						if (couponComplainlist.size()!=0) {
							no_data_Image.setVisibility(View.GONE);
							actualListView = coupon_complain_list.getRefreshableView();
                            // Need to use the Actual ListView when registering for Context Menu
							registerForContextMenu(actualListView);
							couponComplainAdapter= new CouponComplainAdapter(getCurrentContext(), R.layout.coupon_complain_shell,couponComplainlist);
							actualListView.setAdapter(couponComplainAdapter);
							couponComplainAdapter.notifyDataSetChanged();
								
						}
						else {
							Toast.makeText(getCurrentContext(),result.toString() ,Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(getCurrentContext(),getResources().getString(R.string.message4)+"4" ,Toast.LENGTH_SHORT).show();
						
					}

				}else if(result==null){
					Toast.makeText(getCurrentContext(),getResources().getString(R.string.error4) ,Toast.LENGTH_SHORT).show();
					no_data_Image.setVisibility(View.VISIBLE);
				}
			}
		});
		LinkedHashMap<String,Object> request=new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
		try {
			
			request.put(Constants.username, passworddetails.getString(Constants.USERID,""));
			request.put("UserTypeID",passworddetails.getString(Constants.CONTACTTYPEID,""));


		} catch (Exception e) {
			e.printStackTrace();
		}
		String ipAddress=getResources().getString(R.string.ipaddress);
		String webService =getResources().getString(R.string.webService);
		String nameSpace=getResources().getString(R.string.nameSpace);
		String methodName = "GetCouponComplaints";  //GetCouponComplaints
		String soapcomponent=getResources().getString(R.string.soapcomponent);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}
	
	
}
