/**
 * 
 */
package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.GoodsReceiveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author munim
 *
 */
public class GoodsReceiveAdapter  extends ArrayAdapter<GoodsReceiveInfo> {

	private int resourceId;
	private LayoutInflater minflator;
	private ArrayList<GoodsReceiveInfo> infoList;
	
	public ArrayList<GoodsReceiveInfo> getInfoList() {
		return infoList;
	}


	public GoodsReceiveAdapter(Context context, int ResourceId,
                               List<GoodsReceiveInfo> objects) {
		super(context, ResourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId = ResourceId;
		minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		infoList = (ArrayList<GoodsReceiveInfo>) objects;
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
		GoodsReceiveInfo details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.position1);
			holder.txt2        = (TextView)convertView.findViewById(R.id.position2);
			holder.txt3    = (TextView)convertView.findViewById(R.id.position3);
			holder.txt4   = (TextView)convertView.findViewById(R.id.position4);
			holder.txt5 = (TextView)convertView.findViewById(R.id.position5);
			holder.txt6 = (TextView)convertView.findViewById(R.id.position6);
			holder.txt7 = (TextView)convertView.findViewById(R.id.position7);
			holder.txt8 = (TextView)convertView.findViewById(R.id.position8);
			holder.txt9 = (TextView)convertView.findViewById(R.id.position9);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText((details.getId()));
		holder.txt2.setText(details.getProductName());
		holder.txt3.setText(String.valueOf(details.getDiscpatchQty()));
		holder.txt4.setText(String.valueOf(details.getReceiveQty()));
		holder.txt5.setText(String.valueOf(details.getDamageQty()));
		
		if(details.getDiscOfDamage()!=null){
			holder.txt6.setText(String.valueOf(details.getDiscOfDamage()));
		}else{
			holder.txt6.setText("");
		}
		
		holder.txt7.setText(String.valueOf(details.getSalereturn()));
		holder.txt8.setText(String.valueOf(details.getShortQty()));
		holder.txt9.setText(String.valueOf(details.getNetqty()));
		
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
		private TextView txt5;
		private TextView txt6;
		private TextView txt7;
		private TextView txt8;
		private TextView txt9;
	}
}
