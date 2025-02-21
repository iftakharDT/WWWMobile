package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AM_DealerManualOrder;

import java.util.List;

public class AM_DealerManualAdapter extends ArrayAdapter<AM_DealerManualOrder> {

	private int resourceId;
	private LayoutInflater minflator;
	public AM_DealerManualAdapter(Context context, int resource, List<AM_DealerManualOrder> objects) {
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
		AM_DealerManualOrder details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.position1 = (TextView)convertView.findViewById(R.id.position1);
			holder.position2        = (TextView)convertView.findViewById(R.id.position2);
			
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		
		holder.position1.setText(details.getProductCode());
	
		holder.position2.setText(details.getQuantity());
		
		
		
		return convertView;
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
			return true;
	}
	private static class ViewHolder{
		private TextView position1;
		private TextView position2;
		
	}
  
}
