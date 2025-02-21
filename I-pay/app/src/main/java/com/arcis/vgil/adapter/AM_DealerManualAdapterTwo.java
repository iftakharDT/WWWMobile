package com.arcis.vgil.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AM_DealerManualOrder;

import java.util.ArrayList;

public class AM_DealerManualAdapterTwo extends RecyclerView.Adapter<AM_DealerManualAdapterTwo.ItemViewHolder> {

    private Context context;
    private ArrayList<AM_DealerManualOrder> itemList;
    public AM_DealerManualAdapterTwo(Context context, ArrayList<AM_DealerManualOrder> objects) {

        // TODO Auto-generated constructor stub
        this.context = context;
        this.itemList = objects;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.additional_product_cell_new, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        final AM_DealerManualOrder details = itemList.get(position);

        holder.pending_position.setText(details.getProductCode());
        holder.sku_position.setText(details.getPending());
        holder.desc_position.setText(details.getDescription());
        holder.quantity_position.setText(details.getQuantity());



        holder.quantity_position.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus) {
                    ((EditText) v).setText(Constants.getOrderQtyBySKU(((EditText) v).getText().toString(), details.getSku()));
                }

                ((EditText)v).addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        try {
                           // itemList.get(position).setQuantity(arg0.toString());
                            itemList.get(position).setQuantity(Constants.getOrderQtyBySKU(arg0.toString(),details.getSku()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView pending_position;
        private TextView sku_position;
        private TextView desc_position;
        private EditText quantity_position;
        private ImageView remove;

        public ItemViewHolder(View view) {
            super(view);

            pending_position = (TextView)view.findViewById(R.id.pending_position);
            sku_position        = (TextView)view.findViewById(R.id.sku_position);
            desc_position        = (TextView)view.findViewById(R.id.desc_position);
            quantity_position        = (EditText) view.findViewById(R.id.quantity_position);
            remove = (ImageView)view.findViewById(R.id.remove);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context,android.R.style.Theme_Material_Light_Dialog_Alert);
                    dialog.setTitle("Are you sure to delete this item?");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                            removeAt(getAdapterPosition());
                        }
                    });

                    dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.create().show();

                }
            });
        }
    }


    public void removeAt(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }



	/*@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder  = null;
		AM_DealerManualOrder details = getItem(position);
		if(convertView==null){
			convertView = minflator.inflate(resourceId, null);
			holder = new ViewHolder();
			holder.pending_position = (TextView)convertView.findViewById(R.id.pending_position);
			holder.sku_position        = (TextView)convertView.findViewById(R.id.sku_position);
			holder.desc_position        = (TextView)convertView.findViewById(R.id.desc_position);
			holder.quantity_position        = (EditText) convertView.findViewById(R.id.quantity_position);
			holder.remove = (ImageView)convertView.findViewById(R.id.remove);
			convertView.setTag(holder);
		}
		holder = (ViewHolder)convertView.getTag();

		holder.pending_position.setText(details.getPending());
		holder.sku_position.setText(details.getProductId());
		holder.desc_position.setText(details.getInventory());
		holder.quantity_position.setText(details.getQuantity());



		return convertView;
	}


	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
			return true;
	}
	private static class ViewHolder{
		private TextView pending_position;
		private TextView sku_position;
		private TextView desc_position;
		private EditText quantity_position;
		private ImageView remove;

	}
  */
}

