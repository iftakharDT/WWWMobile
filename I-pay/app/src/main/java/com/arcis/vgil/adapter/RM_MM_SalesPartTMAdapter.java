package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.RM_MM_SalesPartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaim on 1/31/2017.
 */

public class RM_MM_SalesPartTMAdapter extends ArrayAdapter<RM_MM_SalesPartModel> implements Filterable {

    private int resourceID;
    private LayoutInflater minflatitor;
    private List<RM_MM_SalesPartModel> dataList;
    private List<RM_MM_SalesPartModel> originalList =  new ArrayList<RM_MM_SalesPartModel>();
    public RM_MM_SalesPartTMAdapter(Context context, int resource,
                                    ArrayList<RM_MM_SalesPartModel> salelist) {
        super(context, resource, salelist);

        this.resourceID = resource;
        minflatitor = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataList = salelist;
        originalList.addAll(salelist);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder  = null;
        RM_MM_SalesPartModel details = getItem(position);

        // TODO Auto-generated method stub
        if(convertView==null){
            convertView = minflatitor.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.ll_partshell=(LinearLayout) convertView.findViewById(R.id.ll_partshell);
            holder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView)convertView.findViewById(R.id.textView3);
            holder.textView3.setVisibility(View.GONE);
            holder.ll_partshell.setWeightSum(2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textView1.setText(details.getItemCode());
        holder.textView2.setText(details.getLYQuantity());
       // holder.textView3.setText(details.getTYQuantity());


        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return true;
    }


    @Override

    public Filter getFilter() {

        Filter filter  = new Filter() {



            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length()>0) {
                    List<RM_MM_SalesPartModel> tmpDataShown = new ArrayList<RM_MM_SalesPartModel>();
                    for (RM_MM_SalesPartModel data : originalList) {
                        if (data.getItemCode().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tmpDataShown.add(data);
                        }
                    }
                    filterResults.values = tmpDataShown;
                    filterResults.count = tmpDataShown.size();
                }else{
                    filterResults.values = originalList;
                    filterResults.count = originalList.size();
                }

                return filterResults;
            }


            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    dataList.clear();
                    dataList.addAll((List<RM_MM_SalesPartModel>)results.values);
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }

            }
        };
        return filter;

    };


    static class ViewHolder{

        private TextView textView1,textView2,textView3;
        private LinearLayout ll_partshell;


    }

}

