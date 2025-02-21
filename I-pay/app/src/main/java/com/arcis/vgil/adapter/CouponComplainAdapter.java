package com.arcis.vgil.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.arcis.vgil.R;
import com.arcis.vgil.model.CouponComplainModerl;

public class CouponComplainAdapter  extends ArrayAdapter<CouponComplainModerl> {

	private int resourceID;
	private LayoutInflater minflatitor;
	private List<CouponComplainModerl> dataList;
	private List<CouponComplainModerl> originalList =  new ArrayList<CouponComplainModerl>();

	public CouponComplainAdapter(Context context, int resource,
			ArrayList<CouponComplainModerl> salelist) {
		super(context, resource, salelist);

		this.resourceID = resource;
		minflatitor = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = salelist;
		originalList.addAll(salelist);
	}

@Override
public int getCount() {
	// TODO Auto-generated method stub
	return dataList.size();
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	
	
	ViewHolder holder  = null;
	CouponComplainModerl details = getItem(position);
	
	// TODO Auto-generated method stub
	if(convertView==null){
		convertView = minflatitor.inflate(resourceID, null);
		holder = new ViewHolder();
		holder.complaint_status = (TextView)convertView.findViewById(R.id.complaint_status);
		holder.complain_by = (TextView)convertView.findViewById(R.id.complain_by);
		holder.customer_city = (TextView)convertView.findViewById(R.id.customer_city);
		holder.customer_mobile = (TextView)convertView.findViewById(R.id.customer_mobile);
		holder.customer_name = (TextView)convertView.findViewById(R.id.customer_name);
		holder.complaint_no = (TextView)convertView.findViewById(R.id.complaint_no);
	

		convertView.setTag(holder);
	}
	else {
		holder = (ViewHolder)convertView.getTag();
	}
	holder.complaint_status.setText(details.getComplaintStatus());
	//holder.complaint_date.setText(details.getComplai());
	holder.complain_by.setText(details.getComplaintBy());
	holder.customer_city.setText(details.getCustomerCity());
	holder.customer_mobile.setText(details.getMobileNo());
	holder.customer_name.setText(details.getCustomerName());
	holder.complaint_no.setText(details.getComplaintNo());
	
	


	return convertView;
}
	
@Override
public boolean isEnabled(int position) {
	// TODO Auto-generated method stub
	return true;
}


static class ViewHolder{

	private TextView complaint_status, complaint_date ,complain_by, customer_city, customer_mobile ,customer_name, complaint_no;
	 


}

}
