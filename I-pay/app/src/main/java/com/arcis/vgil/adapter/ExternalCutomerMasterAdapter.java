package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.ExternalCustomerMaster;

import java.util.List;

public class ExternalCutomerMasterAdapter  extends ArrayAdapter<ExternalCustomerMaster> {

	private int resourceId ;
	private LayoutInflater minflator;
	
	public ExternalCutomerMasterAdapter(Context context, int resource,
                                        List<ExternalCustomerMaster> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		this.resourceId = resource;
		this.minflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder holder  = null;
		ExternalCustomerMaster details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.textView1);
			holder.txt2        = (TextView)convertView.findViewById(R.id.textView2);
			holder.txt3    = (TextView)convertView.findViewById(R.id.textView3);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText(details.getCustomer());
		holder.txt2.setText(details.getMobileNo());
		holder.txt3.setText(details.getAddress());
		
		return convertView;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	
	private static class ViewHolder{
		private TextView txt1;
		private TextView txt2;
		private TextView txt3;
		
	}
}
