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
import com.arcis.vgil.model.PendingOrderPartNoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaim on 1/27/2017.
 */

public class PendingOrderPartNoAdapter  extends ArrayAdapter<PendingOrderPartNoModel> implements Filterable {

    private int resourceID;
    private LayoutInflater minflatitor;
    private List<PendingOrderPartNoModel> dataList;
    private List<PendingOrderPartNoModel> originalList =  new ArrayList<PendingOrderPartNoModel>();

    public PendingOrderPartNoAdapter(Context context, int resource,
                                     ArrayList<PendingOrderPartNoModel> salelist) {
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
        PendingOrderPartNoModel details = getItem(position);

        // TODO Auto-generated method stub
        if(convertView==null){
            convertView = minflatitor.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView)convertView.findViewById(R.id.textView3);
            holder.ll_dd_pd= (LinearLayout) convertView.findViewById(R.id.ll_dd_pd);
            holder.ll_dd_pd.setVisibility(View.GONE);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textView1.setText(details.getItemCode());
        holder.textView2.setText(details.getQuantity());
        holder.textView3.setText(details.getGIT());
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
                    List<PendingOrderPartNoModel> tmpDataShown = new ArrayList<PendingOrderPartNoModel>();
                    for (PendingOrderPartNoModel data : originalList) {
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
                    dataList.addAll((List<PendingOrderPartNoModel>)results.values);
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
        private LinearLayout ll_dd_pd;


    }

}

