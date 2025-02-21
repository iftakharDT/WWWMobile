package com.arcis.vgil.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AmNameModel;

import java.util.ArrayList;
import java.util.List;

public class DealerNameListAdapter extends ArrayAdapter<AmNameModel> implements Filterable {

	private int resourceID;
	private LayoutInflater minflatitor;
	private ArrayList<AmNameModel> dealerOneLineSummaryList = new ArrayList<AmNameModel>();
	private List<AmNameModel> originalList =  new ArrayList<AmNameModel>();
	private Context context;
	private int pos = 0;
	AlertDialog dialog;

	public DealerNameListAdapter(Context context, int resource,
                                 ArrayList<AmNameModel> dealerOneLineSummaryList) {
		super(context, resource, dealerOneLineSummaryList);
		this.context = context;
		this.resourceID = resource;
		minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dealerOneLineSummaryList = dealerOneLineSummaryList;
		originalList.addAll(dealerOneLineSummaryList);

		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dealerOneLineSummaryList.size();
	}

	@Override
	public AmNameModel getItem(int position) {
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
		AmNameModel details = dealerOneLineSummaryList.get(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.et_dlr_am_name = (TextView) convertView
					.findViewById(R.id.et_dlr_am_name);
			
			holder.tv_level = (TextView) convertView
					.findViewById(R.id.tv_level);
			
			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.et_dlr_am_name.setTag(position);
		holder.et_dlr_am_name.setText(details.getAmname());
		holder.tv_level.setText("DLR");
		return convertView;
	}

	@Override

	public Filter getFilter() {

		Filter filter  = new Filter() {



			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null && constraint.length()>0) {
					List<AmNameModel> tmpDataShown = new ArrayList<AmNameModel>();
					for (AmNameModel data : originalList) {
						if (data.getAmname().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
					dealerOneLineSummaryList.clear();
					dealerOneLineSummaryList.addAll((List<AmNameModel>)results.values);
					notifyDataSetChanged();
				}else{
					notifyDataSetInvalidated();
				}

			}
		};
		return filter;

	};


	static class ViewHolder {
		private TextView et_dlr_am_name,tv_level;
				

	}

}
