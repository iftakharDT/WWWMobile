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

public class ShowPreviousCallDetailsAdapter extends RecyclerView.Adapter<ShowPreviousCallDetailsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String >> dataItems;





    public ShowPreviousCallDetailsAdapter(Context mContext, ArrayList<HashMap<String, String >> customerDetailsList) {
        this.context=mContext;
        this.dataItems=customerDetailsList;

    }
    @Override
    public ShowPreviousCallDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.previous_call_list,parent,false);


        return new ShowPreviousCallDetailsAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ShowPreviousCallDetailsAdapter.MyViewHolder holder, int position) {

        holder.tv_last_call_date.setText(dataItems.get(position).get(Constants.LAST_CALL_DATE));
        holder.tv_last_note.setText(dataItems.get(position).get(Constants.LAST_NOTE));

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_last_call_date;
        private TextView tv_last_note;


        MyViewHolder(View view) {
            super(view);
            tv_last_call_date=  view.findViewById(R.id.tv_last_call_date);
            tv_last_note=  view.findViewById(R.id.tv_last_note);


        }

    }
}
