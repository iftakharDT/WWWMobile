package com.arcis.vgil.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AmNameModel;

import java.util.ArrayList;

public class AmNameListAdapter extends ArrayAdapter<AmNameModel> {

	private int resourceID;
	private LayoutInflater minflatitor;
	private ArrayList<AmNameModel> dealerOneLineSummaryList = new ArrayList<AmNameModel>();
	private Context context;
	private int pos = 0;
	AlertDialog dialog;

	public AmNameListAdapter(Context context, int resource,
                             ArrayList<AmNameModel> dealerOneLineSummaryList) {
		super(context, resource, dealerOneLineSummaryList);
		this.context = context;
		this.resourceID = resource;
		minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dealerOneLineSummaryList = dealerOneLineSummaryList;
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

			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.et_dlr_am_name.setTag(position);
		holder.et_dlr_am_name.setText(details.getAmname());

		return convertView;
	}

	static class ViewHolder {
		private TextView et_dlr_am_name;
				

	}

}
