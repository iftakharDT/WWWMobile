package com.arcis.vgil.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AMOneLineSummaryDetailsModel;

import java.util.ArrayList;

public class AmSummaryDetailsAdapter extends
        ArrayAdapter<AMOneLineSummaryDetailsModel> {
	private int resourceID;
	private LayoutInflater minflatitor;
	private ArrayList<AMOneLineSummaryDetailsModel> dealerOneLineSummaryList = new ArrayList<AMOneLineSummaryDetailsModel>();
	private Context context;

	public AmSummaryDetailsAdapter(Context context, int resource,
                                   ArrayList<AMOneLineSummaryDetailsModel> dealerOneLineSummaryList) {
		// TODO Auto-generated constructor stub
		super(context, resource, dealerOneLineSummaryList);
		this.context = context;
		this.resourceID = resource;
		minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dealerOneLineSummaryList = dealerOneLineSummaryList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return dealerOneLineSummaryList.size();
	}

	@Override
	public AMOneLineSummaryDetailsModel getItem(int position) {
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
		AMOneLineSummaryDetailsModel details = dealerOneLineSummaryList
				.get(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.et_dealer_oneline_dealer_code = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_dealer_code);
			holder.et_dealer_oneline_dealership_name = (TextView) convertView.findViewById(R.id.et_dealer_oneline_dealership_name);
			holder.et_city = (TextView) convertView
					.findViewById(R.id.et_city);
			holder.et_dealer_oneline_total_outstanding = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_total_outstanding);
			
			holder.et_delaer_oneline_Current = (TextView) convertView
					.findViewById(R.id.et_delaer_oneline_Current);
			holder.et_dealer_oneline_OD_UPTO_30 = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_OD_UPTO_30);
			holder.et_dealer_oneline_OD_less_30 = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_OD_less_30);
			holder.et_dealer_oneline_OD_less_60 = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_OD_less_60);

			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (details.getBusinessPartner() == null
				|| details.getBusinessPartner().equalsIgnoreCase("TOTAL")) {
			holder.et_dealer_oneline_dealer_code.setText("TOTAL");
			holder.et_dealer_oneline_dealership_name.setText(details.getDealerShipName());
			holder.et_city.setText(details.getDealerCity());
			holder.et_dealer_oneline_total_outstanding.setText(details.getTotalOutstanding());
			
			
			holder.et_delaer_oneline_Current.setText(details.getCurrent());
			holder.et_dealer_oneline_OD_UPTO_30.setText(details.getOD_UPTO_30());
			holder.et_dealer_oneline_OD_less_30.setText(details.getOD_LESS_30());
			holder.et_dealer_oneline_OD_less_60.setText(details.getOD_LESSS_60());
			
		} else {
			holder.et_dealer_oneline_dealer_code.setText(details.getBusinessPartner());
			holder.et_dealer_oneline_dealership_name.setText(details.getDealerShipName());
			holder.et_city.setText(details.getDealerCity());
			holder.et_dealer_oneline_total_outstanding.setText(details.getTotalOutstanding());
			holder.et_delaer_oneline_Current.setText(details.getCurrent());
			holder.et_dealer_oneline_OD_UPTO_30.setText(details.getOD_UPTO_30());
			holder.et_dealer_oneline_OD_less_30.setText(details.getOD_LESS_30());
			holder.et_dealer_oneline_OD_less_60.setText(details.getOD_LESSS_60());
		}

		return convertView;
	}

	static class ViewHolder {
		private TextView et_dealer_oneline_dealer_code ,
		et_dealer_oneline_dealership_name, et_city, et_dealer_oneline_total_outstanding,
		et_delaer_oneline_Current , et_dealer_oneline_OD_UPTO_30, 
		et_dealer_oneline_OD_less_30, et_dealer_oneline_OD_less_60;

	}
}
