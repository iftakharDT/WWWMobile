package com.arcis.vgil.trackapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.model.SubSegmentType;

import java.util.List;

/**
 * Created by Ram on 2/19/2020.
 */
public class HorizontalReportAddapter extends RecyclerView.Adapter<HorizontalReportAddapter.ItemViewHolder>  {

    private Context context;
    private List<SubSegmentType> dataList;
    public HorizontalReportAddapter(Context context, List<SubSegmentType> list) {
        // TODO Auto-generated constructor stub
        dataList = list;
        this.context = context;
    }

    @Override
    public HorizontalReportAddapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_rv_item, parent, false);

        return new HorizontalReportAddapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HorizontalReportAddapter.ItemViewHolder holder, final int position) {

        try {
            holder.cb_report.setOnCheckedChangeListener(null);
            final SubSegmentType details = dataList.get(position);
            if (!details.getSubSegmentName().isEmpty()) {
                holder.tv_report.setText(details.getSubSegmentName());
            } else {
                holder.tv_report.setText("");
            }

            if(details.isSubSegmentSelected()){
                holder.cb_report.setChecked(true);
            }else{
                holder.cb_report.setChecked(false);
            }
            holder.cb_report.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        details.setSubSegmentSelected(isChecked);
                }
            });

        }catch(Exception e){

        }

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_report;
        private CheckBox cb_report;


        public ItemViewHolder(View view) {
            super(view);
            tv_report = (TextView)view.findViewById(R.id.tv_report);
            cb_report = (CheckBox) view.findViewById(R.id.cb_report);


        }
    }




}