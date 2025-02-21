package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.DealerManualOrder;

import java.util.List;

public class DealerManualAdapter extends ArrayAdapter<DealerManualOrder> {

	
	private int resourceId;
	private LayoutInflater minflator;
	public DealerManualAdapter(Context context, int resource, List<DealerManualOrder> objects) {
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
		DealerManualOrder details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.mtxt1 = (TextView)convertView.findViewById(R.id.position1);
			holder.mtxt2        = (TextView)convertView.findViewById(R.id.position2);
			holder.mtxt3    = (TextView)convertView.findViewById(R.id.position3);
			holder.mtxt4   = (TextView)convertView.findViewById(R.id.position4);
			holder.mtxt5 = (TextView)convertView.findViewById(R.id.position5);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.mtxt2.setText(details.getDealerName());
		holder.mtxt1.setText(details.getProductCode());
	
		holder.mtxt3.setText(details.getQuantity());
		holder.mtxt4.setText(String.valueOf(details.getMobileno()));
		holder.mtxt5.setText(String.valueOf(details.getCity()));
		
		
		return convertView;
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
			return true;
	}
	private static class ViewHolder{
		private TextView mtxt1;
		private TextView mtxt2;
		private TextView mtxt3;
		private TextView mtxt4;
		private TextView mtxt5;
	}
}
