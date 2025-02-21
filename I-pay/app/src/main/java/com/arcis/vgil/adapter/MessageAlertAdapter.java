package com.arcis.vgil.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AmNameModel;

import java.util.ArrayList;

/**
 * Created by jaim on 2/2/2017.
 */

public class MessageAlertAdapter  extends ArrayAdapter<AmNameModel> {

    private int resourceID;
    private LayoutInflater minflatitor;
    private ArrayList<AmNameModel> dealerOneLineSummaryList = new ArrayList<AmNameModel>();
    private Context context;
    private int pos = 0;
    AlertDialog dialog;

    public MessageAlertAdapter(Context context, int resource,
                               ArrayList<AmNameModel> dealerOneLineSummaryList) {
        super(context, resource, dealerOneLineSummaryList);
        this.context = context;
        this.resourceID = resource;
        minflatitor = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dealerOneLineSummaryList = dealerOneLineSummaryList;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dealerOneLineSummaryList.size();
    }

    @Override
    public AmNameModel getItem(int position) {
        // TODO Auto-generated method stub
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        AmNameModel details = dealerOneLineSummaryList.get(position);

        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = minflatitor.inflate(resourceID, null);
            holder = new ViewHolder();

            holder.et_dlr_am_name = (TextView) convertView
                    .findViewById(R.id.et_dlr_am_name);
            /*holder.et_dlr_am_name.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.et_dlr_am_name.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);*/
            holder.et_dlr_am_name.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_level= (TextView) convertView
                    .findViewById(R.id.tv_level);
            holder.tv_level.setGravity(Gravity.LEFT);
            holder.tv_level.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_level.setVisibility(View.GONE);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.et_dlr_am_name.setTag(position);
        holder.et_dlr_am_name.setText(details.getMessage());
        holder.tv_level.setText(String.valueOf(position));
        return convertView;
    }

    static class ViewHolder {
        private TextView et_dlr_am_name,tv_level;

    }

}
