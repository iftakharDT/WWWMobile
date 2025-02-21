package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.CouponCode;

import java.util.ArrayList;
import java.util.List;

public class CouponCodeAdapter  extends ArrayAdapter<CouponCode> {

	
	private int resourceId;
	private LayoutInflater minflator;
	
	private ArrayList<CouponCode> couponList ;
	
	public ArrayList<CouponCode> getCouponList() {
		return couponList;
	}

	public CouponCodeAdapter(Context context, int textViewResourceId,
                             List<CouponCode> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		
		resourceId = textViewResourceId;
		minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		couponList = (ArrayList<CouponCode>) objects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder  = null;
		CouponCode details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.position1);
			holder.txt2        = (TextView)convertView.findViewById(R.id.position2);
			holder.txt3    = (TextView)convertView.findViewById(R.id.position3);
			holder.txt4    = (TextView)convertView.findViewById(R.id.position4);
			
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText(details.getCode());
		
		if(details.getPartNo()!=null){
			holder.txt2.setText(details.getPartNo());
		}else{
			holder.txt2.setText("");
		}
		
		holder.txt3.setText(String.valueOf(details.gettotal()));
		
		if(details.getStatus()!=null){
			holder.txt4.setText(details.getStatus());
		}else{
			holder.txt4.setText("");
		}
		return convertView;
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
			return true;
	}
	private static class ViewHolder{
		private TextView txt1;
		private TextView txt2;
		private TextView txt3;
		private TextView txt4;
		
	}
}
