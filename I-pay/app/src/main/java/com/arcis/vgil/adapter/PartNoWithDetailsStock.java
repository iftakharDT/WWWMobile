package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.RM_MM_Stock_Model;

import java.util.ArrayList;
import java.util.List;

public class PartNoWithDetailsStock extends ArrayAdapter<RM_MM_Stock_Model> implements Filterable {

	private int resourceID;
	private LayoutInflater minflatitor;
	private List<RM_MM_Stock_Model> dataList;
	private List<RM_MM_Stock_Model> originalList =  new ArrayList<RM_MM_Stock_Model>();

	public PartNoWithDetailsStock(Context context, int resource,
                                  ArrayList<RM_MM_Stock_Model> salelist) {
		super(context, resource, salelist);

		this.resourceID = resource;
		minflatitor = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = salelist;
		originalList.addAll(salelist);
	}
@Override
public int getCount() {
	// TODO Auto-generated method stub
	return super.getCount();
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	
	
	ViewHolder holder  = null;
	RM_MM_Stock_Model details = getItem(position);
	
	// TODO Auto-generated method stub
	if(convertView==null){
		convertView = minflatitor.inflate(resourceID, null);
		holder = new ViewHolder();
		holder.partNo = (TextView)convertView.findViewById(R.id.position1);
		holder.stock = (TextView)convertView.findViewById(R.id.position2);
		holder.git = (TextView)convertView.findViewById(R.id.position3);
		holder.peding_order = (TextView)convertView.findViewById(R.id.position4);
	

		convertView.setTag(holder);
	}
	holder = (ViewHolder)convertView.getTag();
	holder.partNo.setText(details.getProductCode());
	holder.stock.setText(details.getCurrentStock());
	holder.git.setText(details.getGIT());
	holder.peding_order.setText(details.getPendingOrder());
	


	return convertView;
}
	
@Override
public boolean isEnabled(int position) {
	// TODO Auto-generated method stub
	return true;
}


@Override

public Filter getFilter() {

	Filter filter  = new Filter() {



		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			if (constraint != null && constraint.length()>0) {
				List<RM_MM_Stock_Model> tmpDataShown = new ArrayList<RM_MM_Stock_Model>();
				for (RM_MM_Stock_Model data : originalList) {
					if (data.getProductCode().toLowerCase().contains(constraint.toString().toLowerCase())) {
						tmpDataShown.add(data);
					}
				}
				filterResults.values = tmpDataShown;
				filterResults.count = tmpDataShown.size();
			}else{
				filterResults.values = originalList;
				filterResults.count = originalList.size();
			}

			return filterResults;
		}


		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			if (results != null) {
				dataList.clear();
				dataList.addAll((List<RM_MM_Stock_Model>)results.values);
				notifyDataSetChanged();
			}else{
				notifyDataSetInvalidated();
			}

		}
	};
	return filter;

};


static class ViewHolder{

	private TextView partNo;
	private TextView stock;
	private TextView git;
	private TextView peding_order;


}

}
