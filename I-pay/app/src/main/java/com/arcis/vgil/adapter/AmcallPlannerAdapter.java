package com.arcis.vgil.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AmCallPlanerModel;

import java.util.ArrayList;
import java.util.List;

public class AmcallPlannerAdapter extends ArrayAdapter<AmCallPlanerModel> {
	
	public SparseBooleanArray mCheckStates;
	private int resourceID;
	private LayoutInflater minflatitor;
	private List<AmCallPlanerModel> dataList;
	private List<AmCallPlanerModel> originalList = new ArrayList<AmCallPlanerModel>();

	public AmcallPlannerAdapter(Context context, int resource,
                                List<AmCallPlanerModel> salelist) {
		super(context, resource, salelist);
		// TODO Auto-generated constructor stub
		this.resourceID = resource;
		this.minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dataList = salelist;
		this.originalList.addAll(salelist);
		this.mCheckStates = new SparseBooleanArray(salelist.size());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@Override
	public AmCallPlanerModel getItem(int position) {
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
		final AmCallPlanerModel details = getItem(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.FirmName = (TextView) convertView.findViewById(R.id.firm_name);
			holder.Name = (TextView) convertView.findViewById(R.id.text1);
			holder.State = (TextView) convertView.findViewById(R.id.text2);
			holder.City = (TextView) convertView.findViewById(R.id.text3);
			
			holder.Area = (TextView) convertView.findViewById(R.id.text4);
			holder.MobileNo = (TextView) convertView.findViewById(R.id.text5);
			holder.LastVisited = (TextView) convertView.findViewById(R.id.text6);
			
			holder.NoOfCalls_Last30Days = (TextView) convertView.findViewById(R.id.text7);
			holder.BusinessTrend=(TextView) convertView.findViewById(R.id.text8);
			holder.cb_callplan=(CheckBox) convertView.findViewById(R.id.cb_callplan);
			

			convertView.setTag(holder);
			
			
		}
		
		 else {
	            holder = (ViewHolder) convertView.getTag();
	        }
		
		holder.cb_callplan.setTag(position);
       // holder.cb_callplan.setChecked( false);
	//	holder.cb_callplan.setOnCheckedChangeListener(this);
		holder.cb_callplan.setChecked(details.isSelected());
		holder.cb_callplan.setOnClickListener( new View.OnClickListener() {
		     public void onClick(View v) {
		      CheckBox cb = (CheckBox) v ;
		   
		      details.setSelected(cb.isChecked());
		     
		      
		     }  
		    });  
		holder.FirmName.setText(details.getFirmName());
		holder.Name.setText(details.getName());
		
		holder.State.setText(details.getCity());
        holder.City.setText(details.getState());

        if (details.getPlanned().equals("1")) {
			holder.cb_callplan.setChecked(true);
			holder.cb_callplan.setClickable(false);
			
		}
        else {
        	holder.cb_callplan.setClickable(true);
		
		}
        holder.Area.setText(details.getArea());
		holder.MobileNo.setText(details.getMobileNo());
		holder.LastVisited.setText(details.getLastVisited());
        holder.NoOfCalls_Last30Days.setText(details.getNoOfCalls_Last30Days()); 
        
        
       
        
      if (details.getBusinessTrend().equalsIgnoreCase("G")) {
    	  holder.BusinessTrend.setBackgroundColor(Color.GREEN);
	} 
      else if (details.getBusinessTrend().equalsIgnoreCase("R")) {
    	  holder.BusinessTrend.setBackgroundColor(Color.RED);
		
	}
      else {
    	  holder.BusinessTrend.setBackgroundColor(Color.YELLOW);
	}
		return convertView;
	}
	
	
	
	    
	
	static class ViewHolder {
		private TextView FirmName;
		private TextView Name;
		private TextView State;
		private TextView City;
		private TextView Area;
		private TextView MobileNo;
		private TextView LastVisited;
		private TextView NoOfCalls_Last30Days;
		private TextView BusinessTrend;
		private CheckBox cb_callplan;
		
		

	}
	
/*	@Override
	public void onCheckedChanged(CompoundButton buttonView,
	        boolean isChecked) {
		
		

	     mCheckStates.put((Integer) buttonView.getTag(), true);    

	}*/

}
