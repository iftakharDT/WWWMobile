package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.Invoice;

import java.util.HashMap;
import java.util.List;


public class InvoiceAdapter extends ArrayAdapter<Invoice> implements OnClickListener {

	private LayoutInflater minflator;
	private int resourceId;
	private HashMap<Integer, Invoice> checkedMap = new HashMap<Integer, Invoice>();
	public InvoiceAdapter(Context context, int resourceid,
                          List<Invoice> objects) {
		super(context, resourceid, objects);
		// TODO Auto-generated constructor stub

		minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		resourceId = resourceid;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	public HashMap<Integer, Invoice> getCheckedMap(){
		return checkedMap;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Invoice product = getItem(position);
		ViewHolder holder = null;

		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.ep_textView1);
			holder.checkbox = (CheckBox)convertView.findViewById(R.id.checkBox_1);
			holder.checkbox.setOnClickListener(this);
			convertView.setTag(holder);

		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText(product.getInvoiceNo());
		
		if(product.isIschecked()){
			holder.checkbox.setChecked(true);
		}else{
			holder.checkbox.setChecked(false);
		}
		holder.checkbox.setTag(position);
		return convertView;
	}

	private static  class ViewHolder{

		private TextView txt1;
		private CheckBox checkbox;

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{

			CheckBox checkbox = (CheckBox)v;
			int position = (Integer) v.getTag();
			boolean checked = checkbox.isChecked();
			Invoice invoice = getItem(position);
			if(checked){
				invoice.setIschecked(true);
				checkedMap.put(position, invoice);
			}else{
				if(checkedMap.containsKey(position)){
					checkedMap.remove(position);
					invoice.setIschecked(false);
				}
			}
			notifyDataSetChanged();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}