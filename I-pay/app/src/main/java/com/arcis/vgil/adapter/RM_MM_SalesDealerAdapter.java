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
import com.arcis.vgil.model.RM_MM_DealerSalesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaim on 1/30/2017.
 */

public class RM_MM_SalesDealerAdapter  extends ArrayAdapter<RM_MM_DealerSalesModel> implements Filterable {

    private int resourceID;
    private LayoutInflater minflatitor;
    private List<RM_MM_DealerSalesModel> dataList;
    private List<RM_MM_DealerSalesModel> originalList =  new ArrayList<RM_MM_DealerSalesModel>();
    partDetailsToListenerPart partDetailsToListener;
    int pos;



    public interface partDetailsToListenerPart{
        public void onpartDetailsToListenerPart(int position);
    }
    public void setpartDetailsToListener(partDetailsToListenerPart partDetailsToListener) {
        this.partDetailsToListener = partDetailsToListener;
    }
    public RM_MM_SalesDealerAdapter(Context context, int resource,
                                    ArrayList<RM_MM_DealerSalesModel> salelist) {
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
        RM_MM_DealerSalesModel details = getItem(position);

        // TODO Auto-generated method stub
        if(convertView==null){
            convertView = minflatitor.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
            holder.textView3 = (TextView)convertView.findViewById(R.id.textView3);
            holder.dealer_details = (TextView)convertView.findViewById(R.id.dealer_details);
            holder.view_line = (View) convertView.findViewById(R.id.view_line);
            holder.dealer_details.setVisibility(View.GONE);
            holder.view_line.setVisibility(View.GONE);
            holder.part_details = (TextView)convertView.findViewById(R.id.part_details);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.dealer_details.setTag(position);
        holder.part_details.setTag(position);
        holder.textView1.setText(details.getDealerShipName());
        holder.textView2.setText(details.getLYValue());
        holder.textView3.setText(details.getTYValue());

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
                    List<RM_MM_DealerSalesModel> tmpDataShown = new ArrayList<RM_MM_DealerSalesModel>();
                    for (RM_MM_DealerSalesModel data : originalList) {
                        if (data.getDealerShipName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    dataList.addAll((List<RM_MM_DealerSalesModel>)results.values);
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
        private View view_line;


    }

}

