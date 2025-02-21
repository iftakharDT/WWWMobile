package com.arcis.vgil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.DealerDealerSale;


import java.util.ArrayList;
import java.util.List;

public class PartCustomAlertAdapterNew extends RecyclerView.Adapter<PartCustomAlertAdapterNew.ItemViewHolder> implements Filterable {

    private Context context;
    private int resourceID;
    private List<DealerDealerSale> dataList;
    private List<DealerDealerSale> originalList =  new ArrayList<DealerDealerSale>();
    public PartCustomAlertAdapterNew(Context context, int resource, ArrayList<DealerDealerSale> salelist) {

        // TODO Auto-generated constructor stub
        this.resourceID = resource;
        dataList = salelist;
        originalList.addAll(salelist);
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
            final DealerDealerSale details = dataList.get(position);
            if (!details.getOrderQty().isEmpty()) {
                holder.partQuantityET.setText(details.getOrderQty());
            } else {
                holder.partQuantityET.setText("");
            }

            holder.mtxt1.setText(details.getCode());
            holder.descriptionTV.setText(details.getDescription());

           holder.partQuantityET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View v, boolean hasFocus) {


                   if(!hasFocus) {
                       ((EditText) v).setText(Constants.getOrderQtyBySKU(((EditText) v).getText().toString(), details.getDealerSKU()));
                   }

                   ((EditText)v).addTextChangedListener(new TextWatcher() {

                       @Override
                       public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                           // TODO Auto-generated method stub

                       }

                       @Override
                       public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                     int arg3) {
                           // TODO Auto-generated method stub

                       }

                       @Override
                       public void afterTextChanged(Editable arg0) {
                           // TODO Auto-generated method stub
                           try {


                               dataList.get(position).setOrderQty(Constants.getOrderQtyBySKU(arg0.toString(),details.getDealerSKU()));
                           }catch (Exception e){
                               e.printStackTrace();
                           }

                       }
                   });

               }
           });



        }catch(Exception e){

        }
     /*   //we need to update adapter once we finish with editing
        holder.partQuantityET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Log.d(">>>","not focus");
                    dataList.get(position).setOrderQty(((EditText)v).getText().toString());

                }
            }
        });
*/
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mtxt1;
        private EditText partQuantityET;
        private TextView descriptionTV;

        public ItemViewHolder(View view) {
            super(view);
            mtxt1 = (TextView)view.findViewById(R.id.partNo_shell);
            descriptionTV = (TextView)view.findViewById(R.id.descriptionTV);
            partQuantityET  = (EditText) view.findViewById(R.id.partQuantityET);
        }
    }


    @Override
    public Filter getFilter() {

        Filter filter  = new Filter() {



            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length()>0) {
                    List<DealerDealerSale> tmpDataShown = new ArrayList<DealerDealerSale>();
                    for (DealerDealerSale data : originalList) {
                        if (data.getCode().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
                    dataList.addAll((List<DealerDealerSale>)results.values);
                    notifyDataSetChanged();
                }else{

                    //notifyDataSetInvalidated();
                    notifyAll();
                }

            }
        };
        return filter;

    };

}

