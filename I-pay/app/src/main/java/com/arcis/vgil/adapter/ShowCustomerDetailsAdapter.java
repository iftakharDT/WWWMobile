package com.arcis.vgil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaim on 3/22/2018.
 */

public class ShowCustomerDetailsAdapter extends RecyclerView.Adapter<ShowCustomerDetailsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String >> dataItems;





    public ShowCustomerDetailsAdapter(Context mContext, ArrayList<HashMap<String, String >> customerDetailsList) {
        this.context=mContext;
        this.dataItems=customerDetailsList;

    }
    @Override
    public ShowCustomerDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.shell_customer_list,parent,false);


        return new ShowCustomerDetailsAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ShowCustomerDetailsAdapter.MyViewHolder holder, int position) {

        holder.tv_mobile.setText(dataItems.get(position).get(Constants.MOBILE));
        holder.tv_contact_name.setText(dataItems.get(position).get(Constants.CONTACT_NAME));
        holder.tv_city_name.setText(dataItems.get(position).get(Constants.CITY));
        holder.tv_garage_name.setText(dataItems.get(position).get(Constants.GARAGE_NAME));
        holder.tv_Area.setText(dataItems.get(position).get(Constants.AREA));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mobile;
        private TextView tv_contact_name;
        private TextView tv_city_name;
        private TextView tv_garage_name;
        private TextView tv_Area;

        MyViewHolder(View view) {
            super(view);
            tv_mobile=  view.findViewById(R.id.tv_mobile);
            tv_contact_name=  view.findViewById(R.id.tv_contact_name);
            tv_city_name=  view.findViewById(R.id.tv_city_name);
            tv_garage_name=  view.findViewById(R.id.tv_garage_name);
            tv_Area=  view.findViewById(R.id.tv_Area);

        }

    }
}
