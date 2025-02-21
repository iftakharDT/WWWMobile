package com.arcis.vgil.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AvailableManualOrder;
import com.arcis.vgil.model.DealerDealerSale;

import java.util.ArrayList;
import java.util.List;

public class AvailableMenualOrderAlertAdapter extends RecyclerView.Adapter<AvailableMenualOrderAlertAdapter.ItemViewHolder>  {

    private Context context;
    private int resourceID;
    private List<AvailableManualOrder> dataList;
    public AvailableMenualOrderAlertAdapter(Context context, int resource, ArrayList<AvailableManualOrder> salelist) {

        // TODO Auto-generated constructor stub
        this.resourceID = resource;
        dataList = salelist;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(resourceID, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {


       try {
            final AvailableManualOrder details = dataList.get(position);
            if (!details.getQty().isEmpty()) {
                holder.quantity_position.setText(details.getQty());
            } else {
                holder.quantity_position.setText("");
            }

           if (!details.getAvailableQty().isEmpty()) {
               holder.available.setText(details.getAvailableQty());
           } else {
               holder.available.setText("");
           }

           holder.available.setText(details.getAvailability());
          /* if(details.getAvailability().equalsIgnoreCase("Depot")) {

               holder.available.setBackgroundColor(context.getResources().getColor( R.color.green));
           }else if(details.getAvailability().equalsIgnoreCase("p")){
               holder.available.setBackgroundColor(context.getResources().getColor( R.color.yellow));
           }else if(details.getAvailability().equalsIgnoreCase("N")){
               holder.available.setBackgroundColor(context.getResources().getColor( R.color.red));
           }*/

            holder.desc_position.setText(details.getDescription());
            holder.pending_position.setText(details.getPending_order());

        }catch(Exception e){

        }

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView pending_position;
        private TextView desc_position;
        private EditText quantity_position;
        private TextView available;

        public ItemViewHolder(View view) {
            super(view);
            pending_position = (TextView)view.findViewById(R.id.pending_position);
            desc_position = (TextView)view.findViewById(R.id.desc_position);
            quantity_position  = (EditText) view.findViewById(R.id.quantity_position);
            available  = (TextView) view.findViewById(R.id.available);
        }
    }




}

