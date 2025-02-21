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
import com.arcis.vgil.model.DealerDealerSale;

import java.util.ArrayList;
import java.util.List;

public class PartCustomAlertAdapter extends ArrayAdapter<DealerDealerSale> implements Filterable {

	private int resourceID;
	private LayoutInflater minflatitor;
	private List<DealerDealerSale> dataList;
	private List<DealerDealerSale> originalList =  new ArrayList<DealerDealerSale>();

	public PartCustomAlertAdapter(Context context, int resource,
                                  ArrayList<DealerDealerSale> salelist) {
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
	DealerDealerSale details = getItem(position);
	
	// TODO Auto-generated method stub
	if(convertView==null){
		convertView = minflatitor.inflate(resourceID, null);
		holder = new ViewHolder();
		holder.mtxt1 = (TextView)convertView.findViewById(R.id.partNo_shell);
	//	holder.mtxt2        = (TextView)convertView.findViewById(R.id.pending_order);
	//	holder.mtxt3    = (TextView)convertView.findViewById(R.id.git);
	//	holder.mtxt4   = (TextView)convertView.findViewById(R.id.inventory);
	//	holder.mtxt5 = (TextView)convertView.findViewById(R.id.quantity);


		convertView.setTag(holder);
	}
	holder = (ViewHolder)convertView.getTag();
	holder.mtxt1.setText(details.getCode());
	holder.mtxt1.setTag(details.getCode());
	//holder.mtxt2.setText(details.getPending_order());

	//holder.mtxt3.setText(details.getGitQuantity());
	//holder.mtxt4.setText(details.getInventory());
	//holder.mtxt5.setText(details.getOrderQty());


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
				List<DealerDealerSale> tmpDataShown = new ArrayList<DealerDealerSale>();
				for (DealerDealerSale data : originalList) {
					if (data.getCode().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
				dataList.addAll((List<DealerDealerSale>)results.values);
				notifyDataSetChanged();
			}else{
				notifyDataSetInvalidated();
			}

		}
	};
	return filter;

};


static class ViewHolder{

	private TextView mtxt1;
//	private TextView mtxt2;
	//private  TextView mtxt3;
	//private TextView mtxt4;
	//private TextView mtxt5;

}

}
