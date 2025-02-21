package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.Invoice;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author munim
 *
 */
public class InvoiceDetailAdapter extends ArrayAdapter<Invoice> {

	private int resourceId;
	private LayoutInflater minflator;
	private ArrayList<Invoice> invoiceList;

	public InvoiceDetailAdapter(Context context, int textViewResourceId,
                                List<Invoice> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		
		resourceId = textViewResourceId;
		minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		invoiceList = (ArrayList<Invoice>) objects;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	
	public ArrayList<Invoice> getInvoiceList(){
		
		return invoiceList;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder  = null;
		Invoice details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.position1);
			holder.txt2        = (TextView)convertView.findViewById(R.id.position2);
			holder.txt3    = (TextView)convertView.findViewById(R.id.position3);
			holder.txt4   = (TextView)convertView.findViewById(R.id.position4);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText(String.valueOf(details.getInvoiceNo()));
		holder.txt2.setText(details.getErpOrderNo());
		holder.txt3.setText(details.getProductCode());
		holder.txt4.setText(String.valueOf(details.getQuantity()));
		return convertView;
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
			return false;
	}
	private static class ViewHolder{
		private TextView txt1;
		private TextView txt2;
		private TextView txt3;
		private TextView txt4;
	}
}
