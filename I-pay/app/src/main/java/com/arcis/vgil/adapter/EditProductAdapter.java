package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.Product;

import java.util.List;

public class EditProductAdapter  extends ArrayAdapter<Product> {

	private int resourceId;
	private LayoutInflater minflator;
	public EditProductAdapter(Context context, int resource, List<Product> objects) {
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
		Product details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.position1);
			holder.txt2        = (TextView)convertView.findViewById(R.id.position2);
			holder.txt3    = (TextView)convertView.findViewById(R.id.position3);
			holder.txt4   = (TextView)convertView.findViewById(R.id.position4);
			holder.txt5 = (TextView)convertView.findViewById(R.id.position5);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText(String.valueOf(details.getSaleNo()));
		holder.txt2.setText(details.getPartyName());
		holder.txt3.setText(details.getSaleDate());
		holder.txt4.setText(String.valueOf(details.getNoOfItem()));
		holder.txt5.setText(String.valueOf(details.getSaleAmount()));
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
	}
}
