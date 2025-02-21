package com.arcis.vgil.adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.DashboardModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends ArrayAdapter<DashboardModel> {

	private int resourceID;
	private LayoutInflater minflatitor;
	private List<DashboardModel> dataList;
	private List<DashboardModel> originalList = new ArrayList<DashboardModel>();

	public DashboardAdapter(Context context, int resource,
                            List<DashboardModel> salelist) {
		super(context, resource, salelist);
		// TODO Auto-generated constructor stub
		this.resourceID = resource;
		this.minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dataList = salelist;
		this.originalList.addAll(salelist);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@Override
	public DashboardModel getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

    @Override
    public long getItemId(int position) {
        return position;
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		final DashboardModel details = getItem(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.FirmName = (TextView) convertView.findViewById(R.id.name);
			holder.login = (TextView) convertView.findViewById(R.id.login);
			holder.calls = (TextView) convertView.findViewById(R.id.calls);
		    convertView.setTag(holder);
			
			
		}
		
		 else {
	            holder = (ViewHolder) convertView.getTag();
	        }
		
	 
		holder.FirmName.setText(details.getAMName());
		holder.login.setText(details.getLoginTime());
		holder.calls.setText(details.getCalls());
        
      if (details.getColor().equalsIgnoreCase("G")) {
    	  holder.login.setBackgroundColor(Color.GREEN);
	} 
      else if (details.getColor().equalsIgnoreCase("R")) {
    	  holder.login.setBackgroundColor(Color.RED);
		
	}
      else {
    	  holder.login.setBackgroundColor(Color.YELLOW);
	}
      if (Integer.parseInt(details.getCalls())<=6) {
    	  holder.calls.setBackgroundColor(Color.RED);
	} 
      else if (Integer.parseInt(details.getCalls())>6&& Integer.parseInt(details.getCalls())<=9) {
    	  holder.calls.setBackgroundColor(Color.YELLOW);
		
	}
      else {
    	  holder.calls.setBackgroundColor(Color.GREEN);
	}
		return convertView;
	}
	
	 
	
	static class ViewHolder {
		private TextView FirmName;
		private TextView login;
		private TextView calls;
		
	}
	


}
