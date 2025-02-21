package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.DealerOneLineSummaryModelDetails;

import java.util.ArrayList;

public class DealerOneLineSummaryDetailsAdapter extends
        ArrayAdapter<DealerOneLineSummaryModelDetails> {
	private int resourceID;
	private LayoutInflater minflatitor;
	private ArrayList<DealerOneLineSummaryModelDetails> dealerOneLineSummaryList = new ArrayList<DealerOneLineSummaryModelDetails>();
	private Context context;

	public DealerOneLineSummaryDetailsAdapter(Context context, int resource,
                                              ArrayList<DealerOneLineSummaryModelDetails> dealerOneLineSummaryList) {
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
	public DealerOneLineSummaryModelDetails getItem(int position) {
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
		DealerOneLineSummaryModelDetails details = dealerOneLineSummaryList
				.get(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.et_invoiceno = (TextView) convertView
					.findViewById(R.id.et_invoiceno);
			holder.et_date = (TextView) convertView.findViewById(R.id.et_date);
			holder.et_document_type = (TextView) convertView
					.findViewById(R.id.et_document_type);
			holder.et_balance_amount = (TextView) convertView
					.findViewById(R.id.et_balance_amount);

			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}
      if(details.getInvoiceNo()==null||details.getInvoiceNo().equalsIgnoreCase("TOTAL")){
    	    holder.et_invoiceno.setText("TOTAL");
    		holder.et_date.setText(details.getDate());
    		holder.et_document_type.setText(details.getDocumentType());
    		holder.et_balance_amount.setText(details.getBalanceAmount());
      }else {
    	holder.et_invoiceno.setText(details.getInvoiceNo());
  		holder.et_date.setText(details.getDate());
  		holder.et_document_type.setText(details.getDocumentType());
  		holder.et_balance_amount.setText(details.getBalanceAmount());
	}
		

		return convertView;
	}

	static class ViewHolder {
		private TextView et_invoiceno, et_date, et_document_type,
				et_balance_amount;

	}
}
