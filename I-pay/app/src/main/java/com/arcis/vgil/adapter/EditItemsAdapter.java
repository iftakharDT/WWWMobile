package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.EditableProduct;

import java.util.HashMap;
import java.util.List;

public class EditItemsAdapter extends ArrayAdapter<EditableProduct> implements OnClickListener {

	private LayoutInflater minflator;
	private int resourceId;
	private HashMap<Integer, EditableProduct> checkedMap = new HashMap<Integer, EditableProduct>();
	
	public EditItemsAdapter(Context context, int resourceid,
                            List<EditableProduct> objects) {
		
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

	public HashMap<Integer, EditableProduct> getCheckedMap(){
		return checkedMap;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		EditableProduct product = getItem(position);
		EditableProductsViewHolder holder = null;

		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new EditableProductsViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.ep_textView1);
			holder.txt2 = (TextView)convertView.findViewById(R.id.ep_textView2);
			holder.txt3 = (TextView)convertView.findViewById(R.id.ep_textView3);
			holder.txt4 = (EditText)convertView.findViewById(R.id.ep_textView4);
			holder.checkbox = (CheckBox)convertView.findViewById(R.id.checkBox_delete);
			holder.checkbox.setOnClickListener(this);
			convertView.setTag(holder);

		}
		holder = (EditableProductsViewHolder)convertView.getTag();
		holder.txt1.setText(product.getProductcode());
		holder.txt2.setText(product.getSaleamount());
		holder.txt3.setText(product.getSaletype());
		holder.txt4.setText(product.getQuantity());
		if(product.isIschecked()){
			holder.checkbox.setChecked(true);
		}else{
			holder.checkbox.setChecked(false);
		}
		holder.checkbox.setTag(position);
		return convertView;
	}

	private static  class EditableProductsViewHolder{

		private TextView txt1;
		private TextView txt2;
		private TextView txt3;
		private EditText txt4;
		private CheckBox checkbox;

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{

			CheckBox checkbox = (CheckBox)v;
			int position = (Integer) v.getTag();
			boolean checked = checkbox.isChecked();
			EditableProduct product = getItem(position);
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
