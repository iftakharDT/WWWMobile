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
import com.arcis.vgil.model.Product;

import java.util.HashMap;
import java.util.List;

public class FinalSubmitAdapter  extends ArrayAdapter<Product> implements OnClickListener {

	
	private int resourceId;
	private LayoutInflater minflator;
	private HashMap<Integer, Product> checkedMap = new HashMap<Integer, Product>();
	public FinalSubmitAdapter(Context context, int resource, List<Product> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
		minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}
	
	public HashMap<Integer, Product> getCheckedMap(){
		return checkedMap;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder  = null;
		Product details = getItem(position); 
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.ep_textView1);
			holder.txt2        = (TextView)convertView.findViewById(R.id.ep_textView2);
			holder.txt3    = (TextView)convertView.findViewById(R.id.ep_textView3);
			holder.txt4   = (TextView)convertView.findViewById(R.id.ep_textView4);
			holder.txt5 = (TextView)convertView.findViewById(R.id.ep_textView5);
			holder.chkbox = (CheckBox)convertView.findViewById(R.id.checkBox_delete);
			holder.chkbox.setOnClickListener(this);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();
		holder.txt1.setText(String.valueOf(details.getSaleNo()));
		holder.txt2.setText(details.getPartyName());
		holder.txt3.setText(details.getSaleDate());
		holder.txt4.setText(String.valueOf(details.getNoOfItem()));
		holder.txt5.setText(String.valueOf(details.getSaleAmount()));
		if(details.isIschecked()){
			holder.chkbox.setChecked(true);
		}else{
			holder.chkbox.setChecked(false);
		}
		holder.chkbox.setTag(position);
		return convertView;
	}
	
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
			return true;
	}
	private static class ViewHolder{
		private TextView txt1;
		private TextView txt2;
		private TextView txt3;
		private TextView txt4;
		private TextView txt5;
		private CheckBox chkbox;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{

			CheckBox checkbox = (CheckBox)v;
			int position = (Integer) v.getTag();
			boolean checked = checkbox.isChecked();
			Product product = getItem(position);
			if(checked){
				product.setIschecked(true);
				checkedMap.put(position, product);
			}else{
				if(checkedMap.containsKey(position)){
					checkedMap.remove(position);
					product.setIschecked(false);
				}
			}

			notifyDataSetChanged();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
