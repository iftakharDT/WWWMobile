package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.RM_MM_AMSalesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaim on 1/28/2017.
 */

public class RM_MM_SalesAmAdapter  extends ArrayAdapter<RM_MM_AMSalesModel> implements Filterable {

    private int resourceID;
    private LayoutInflater minflatitor;
    private List<RM_MM_AMSalesModel> dataList;
    private List<RM_MM_AMSalesModel> originalList =  new ArrayList<RM_MM_AMSalesModel>();
    partDetailsToListenerPart    partDetailsToListener;
    dealerDetailsToListenerName     dealerDetailsToListener;
    int pos;


    public interface dealerDetailsToListenerName{
        public void ondealerDetailsToListenerName(int position);
    }
    public void setpartDetailsToListenerName(dealerDetailsToListenerName dealerDetailsToListenerName) {
        this.dealerDetailsToListener = dealerDetailsToListenerName;
    }


    public interface partDetailsToListenerPart{
        public void onpartDetailsToListenerPart(int position);
    }
    public void setpartDetailsToListener(partDetailsToListenerPart partDetailsToListener) {
        this.partDetailsToListener = partDetailsToListener;
    }
    public RM_MM_SalesAmAdapter(Context context, int resource,
                                ArrayList<RM_MM_AMSalesModel> salelist) {
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
        RM_MM_AMSalesModel details = getItem(position);

        // TODO Auto-generated method stub
        if(convertView==null){
            convertView = minflatitor.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView)convertView.findViewById(R.id.textView3);
            holder.dealer_details = (TextView)convertView.findViewById(R.id.dealer_details);
            holder.part_details = (TextView)convertView.findViewById(R.id.part_details);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

       holder.dealer_details.setTag(position);
       holder.part_details.setTag(position);
       holder.textView1.setText(details.getAMName());
       holder.textView2.setText(details.getLYValue());
       holder.textView3.setText(details.getTYValue());

        holder.dealer_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pos = (Integer) v.getTag();
                if (dealerDetailsToListener != null) {
                    dealerDetailsToListener.ondealerDetailsToListenerName(pos);
                }

            }
        });

        holder.part_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pos = (Integer) v.getTag();
                if (partDetailsToListener != null) {
                    partDetailsToListener.onpartDetailsToListenerPart(pos);
                }

            }
        });


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
                    List<RM_MM_AMSalesModel> tmpDataShown = new ArrayList<RM_MM_AMSalesModel>();
                    for (RM_MM_AMSalesModel data : originalList) {
                        if (data.getAMName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    dataList.addAll((List<RM_MM_AMSalesModel>)results.values);
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }

            }
        };
        return filter;

    };


    static class ViewHolder{

        private TextView textView1,textView2,textView3,dealer_details ,part_details;


    }

}

