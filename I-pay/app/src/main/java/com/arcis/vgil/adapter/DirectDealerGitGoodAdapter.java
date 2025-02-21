package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.DealerGoodGITReciept;

import java.util.ArrayList;
import java.util.List;

public class DirectDealerGitGoodAdapter extends
        ArrayAdapter<DealerGoodGITReciept> {

	private int resourceID;
	private LayoutInflater minflatitor;
	private List<DealerGoodGITReciept> dataList;
	private List<DealerGoodGITReciept> originalList = new ArrayList<DealerGoodGITReciept>();

	public DirectDealerGitGoodAdapter(Context context, int resource,
                                      ArrayList<DealerGoodGITReciept> salelist) {
		super(context, resource, salelist);
		// TODO Auto-generated constructor stub
		this.resourceID = resource;
		minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		ViewHolder holder = null;
		DealerGoodGITReciept details = getItem(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.mtxt1 = (TextView) convertView.findViewById(R.id.invoice);
			holder.mtxt2 = (TextView) convertView.findViewById(R.id.date);
			holder.mtxt3 = (TextView) convertView.findViewById(R.id.quantity);

			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.mtxt1.setText(details.getInVoiceNo());
		holder.mtxt2.setText(details.getDate());

		holder.mtxt3.setText(details.getQuantity());

		return convertView;
	}

	static class ViewHolder {

		private TextView mtxt1;
		private TextView mtxt2;
		private TextView mtxt3;

	}
}
