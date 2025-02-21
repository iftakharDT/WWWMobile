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
import com.arcis.vgil.model.DealerSale;

import java.util.ArrayList;
import java.util.List;

public class DealerSaleAdapter extends ArrayAdapter<DealerSale> implements Filterable {


	private int resourceID;
	private LayoutInflater minflatitor;
	private List<DealerSale> dataList;
	private List<DealerSale> originalList =  new ArrayList<DealerSale>();

	public DealerSaleAdapter(Context context, int resource,
                             List<DealerSale> objects) {
		super(context, resource, objects);

		this.resourceID = resource;
		minflatitor = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dataList = objects;
		originalList.addAll(objects);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder  = null;
		DealerSale details = getItem(position); 
		if(convertView==null){
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.mtxt1 = (TextView)convertView.findViewById(R.id.position1);
			holder.mtxt2        = (TextView)convertView.findViewById(R.id.position2);
			holder.mtxt3    = (TextView)convertView.findViewById(R.id.position3);
			holder.mtxt4   = (TextView)convertView.findViewById(R.id.position4);
			holder.mtxt5 = (TextView)convertView.findViewById(R.id.position5);

			holder.mtxt6 = (TextView)convertView.findViewById(R.id.position6);
			holder.mtxt7 = (TextView)convertView.findViewById(R.id.position7);
			holder.mtxt8 = (TextView)convertView.findViewById(R.id.position8);
			holder.mtxt9 = (TextView)convertView.findViewById(R.id.position9);
			holder.mtxt10 = (TextView)convertView.findViewById(R.id.position10);
			holder.mtxt11 = (TextView)convertView.findViewById(R.id.position11);

			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.mtxt1.setText(details.getCode());
		holder.mtxt2.setText(details.getProductName());

		holder.mtxt3.setText(details.getClassification());
		holder.mtxt4.setText(String.valueOf(details.getUnitPrice()));
		holder.mtxt5.setText(details.getTargetStock());

		holder.mtxt6.setText(details.getCurrentStock());
		holder.mtxt7.setText(details.getRlevel());
		holder.mtxt8.setText(details.getYlevel());
		holder.mtxt9.setText(details.getGlevel());
		holder.mtxt10.setText(details.getSaleoftheday());
		holder.mtxt11.setText(String.valueOf(details.getSalequantity()));

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
					List<DealerSale> tmpDataShown = new ArrayList<DealerSale>();
					for (DealerSale data : originalList) {
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
					dataList.addAll((List<DealerSale>)results.values);
					notifyDataSetChanged();
				}else{
					notifyDataSetInvalidated();
				}

			}
		};
		return filter;

	};
	private static class ViewHolder{

		private TextView mtxt1;
		private TextView mtxt2;
		private TextView mtxt3;
		private TextView mtxt4;
		private TextView mtxt5;

		private TextView mtxt6;
		private TextView mtxt7;
		private TextView mtxt8;
		private TextView mtxt9;
		private TextView mtxt10;
		private TextView mtxt11;
	}

	public double getTotalAmount(){

		double totalamount = 0.0;
		for(DealerSale sale: dataList){

			totalamount = totalamount+sale.getAmount();
		}
		return totalamount;

	}


	public String getserverString(){

		StringBuilder detailsString  = new StringBuilder();

		for(DealerSale sale : this.dataList){

			if(sale.getSalequantity()>0){

				detailsString.append(sale.getProductName());
				detailsString.append("~");
				detailsString.append(sale.getCode());
				detailsString.append("~");
				detailsString.append(sale.getCurrentStock());
				detailsString.append("~");
				detailsString.append(sale.getProductID());
				detailsString.append("~");
				detailsString.append(String.valueOf(sale.getUnitPrice()));
				detailsString.append("~");
				detailsString.append(sale.getClassification());
				detailsString.append("~");
				detailsString.append(String.valueOf(sale.getSalequantity()));
				detailsString.append("~");
				detailsString.append(String.valueOf(Integer.parseInt(sale.getCurrentStock())-sale.getSalequantity()));
				detailsString.append(";");
			}

		}

		return detailsString.toString();
	}

}
