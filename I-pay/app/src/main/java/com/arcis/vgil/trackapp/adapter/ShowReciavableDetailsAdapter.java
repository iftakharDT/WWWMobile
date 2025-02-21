package com.arcis.vgil.trackapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.data.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jaim on 3/22/2018.
 */

public class ShowReciavableDetailsAdapter extends RecyclerView.Adapter<ShowReciavableDetailsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String >> dataItems;





    public ShowReciavableDetailsAdapter(Context mContext, ArrayList<HashMap<String, String >> customerDetailsList) {
        this.context=mContext;
        this.dataItems=customerDetailsList;

    }
    @Override
    public ShowReciavableDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recievable_call_list,parent,false);
        return new ShowReciavableDetailsAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ShowReciavableDetailsAdapter.MyViewHolder holder, int position) {

        holder.tv_bill_number.setText(dataItems.get(position).get(Constants.BILL_NUMBER));
        holder.tv_bill_date.setText(dataItems.get(position).get(Constants.BILL_DATE));
        holder.tv_amount.setText(dataItems.get(position).get(Constants.AMOUNT));

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_bill_number;
        private TextView tv_bill_date;
        private TextView tv_amount;


        MyViewHolder(View view) {
            super(view);
            tv_bill_number=  view.findViewById(R.id.tv_bill_number);
            tv_bill_date=  view.findViewById(R.id.tv_bill_date);
            tv_amount=  view.findViewById(R.id.tv_amount);


        }

    }
}
