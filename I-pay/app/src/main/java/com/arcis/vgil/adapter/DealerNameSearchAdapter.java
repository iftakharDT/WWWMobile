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
import com.arcis.vgil.model.DealerNameModel;

import java.util.ArrayList;
import java.util.List;


public class DealerNameSearchAdapter extends ArrayAdapter<DealerNameModel> implements Filterable {

	private int resourceID;
	private LayoutInflater minflatitor;
	private List<DealerNameModel> dataList;
	private List<DealerNameModel> originalList =  new ArrayList<DealerNameModel>();

	public DealerNameSearchAdapter(Context context, int resource,
                                   ArrayList<DealerNameModel> dealerNameModels) {
		super(context, resource, dealerNameModels);

		this.resourceID = resource;
		minflatitor = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = dealerNameModels;
		originalList.addAll(dealerNameModels);
	}
@Override
public int getCount() {
	// TODO Auto-generated method stub
	return dataList.size();
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	
	
	ViewHolder holder  = null;
	DealerNameModel details = getItem(position);
	
	// TODO Auto-generated method stub
	if(convertView==null){
		convertView = minflatitor.inflate(resourceID, null);
		holder = new ViewHolder();
		holder.mtxt1 = (TextView)convertView.findViewById(R.id.partNo_shell);
	

		convertView.setTag(holder);
	}
	else {
		holder = (ViewHolder)convertView.getTag();
	}
	
	holder.mtxt1.setText(details.getDealerName());



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
				List<DealerNameModel> tmpDataShown = new ArrayList<DealerNameModel>();
				for (DealerNameModel data : originalList) {
					if (data.getDealerName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
				dataList.addAll((List<DealerNameModel>)results.values);
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


}

}
