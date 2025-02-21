package com.arcis.vgil.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.ProductDetails;

import java.util.List;

public class DealerProductAdapter  extends ArrayAdapter<ProductDetails> {

	private int resourceId;
	private LayoutInflater minflator;
	public DealerProductAdapter(Context context, int resource, List<ProductDetails> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
		minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		ProductDetails details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.mproductCode = (TextView)convertView.findViewById(R.id.position1);
			holder.mName        = (TextView)convertView.findViewById(R.id.position2);
			holder.mclassification    = (TextView)convertView.findViewById(R.id.position3);
			holder.mUnitprice   = (TextView)convertView.findViewById(R.id.position4);
			holder.mQuantity = (TextView)convertView.findViewById(R.id.position5);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.mproductCode.setText(details.getProductCode());
		holder.mName.setText(details.getProcuctName());
		holder.mclassification.setText(details.getClassification());
		holder.mUnitprice.setText(String.valueOf(details.getNetsellingprice()));
		holder.mQuantity.setText(String.valueOf(details.getQuantity()));
		if(details.getSaleid()>0){
			convertView.setBackgroundColor(Color.GRAY);
		}else{
			//convertView.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if(getItem(position).getSaleid()>0){
			return false;
		}else{
			return true;
		}
	}
	private static class ViewHolder{
		private TextView mproductCode;
		private TextView mName;
		private TextView mclassification;
		private TextView mUnitprice;
		private TextView mQuantity;
	}
}
