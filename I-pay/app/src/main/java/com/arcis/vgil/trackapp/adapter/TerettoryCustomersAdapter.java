package com.arcis.vgil.trackapp.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.model.TerettoryListModel;

import java.util.ArrayList;
import java.util.List;

public class TerettoryCustomersAdapter extends RecyclerView.Adapter<TerettoryCustomersAdapter.MyViewHolder> implements Filterable {

    private List<TerettoryListModel> dataList;
    private List<TerettoryListModel> originalList =  new ArrayList<TerettoryListModel>();
    private Context context;
    private CustomerAdapterListener listener;
    private double mlatitude;
    private double mlongitude;
    private boolean isClickable=true;


    public boolean setIsClickable(boolean isClickable){
       return this.isClickable = isClickable;
    }


    public TerettoryCustomersAdapter(Context context, double mlatitude, double mlongitude, List<TerettoryListModel> salelist, CustomerAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.mlatitude =mlatitude;
        this.mlongitude= mlongitude;
        dataList = salelist;
        originalList.addAll(salelist);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terettory_log_customer_cell, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final TerettoryListModel details = dataList.get(position);
try {
    holder.tv_firm_name.setText(details.getFirmName()==null || details.getFirmName().isEmpty() ? "N/A" : details.getFirmName());
    holder.tv_address.setText(details.getAddress()==null || details.getAddress().isEmpty() ? "N/A" : details.getAddress());
    holder.tv_city.setText(details.getCity()==null || details.getCity().isEmpty() ? "N/A" : details.getCity());
   // holder.tv_distance.setText(details.getDistance().isEmpty()?"Distance":details.getDistance()+"KM");
    holder.tv_mobile.setText(details.getMobileNo()==null ||details.getMobileNo().isEmpty() ? "" : details.getMobileNo());
    holder.tv_person.setText(details.getName()==null || details.getName().isEmpty() ? "" : details.getName());


    // change the row state to activated
    if(details.getIsCalled().equalsIgnoreCase("1")) {
        holder.iv_end_call.setVisibility(View.VISIBLE);
        holder.iv_end_call.setColorFilter(ContextCompat.getColor(context, R.color.primary_dark));
    } else {
        holder.iv_end_call.setVisibility(View.GONE);
    }


    if(details.getPlanned().equalsIgnoreCase("1")) {
        holder.iv_planned_call.setVisibility(View.VISIBLE);
        holder.iv_planned_call.setColorFilter(ContextCompat.getColor(context, R.color.primary_dark));
    } else {
        holder.iv_planned_call.setVisibility(View.GONE);
    }


    holder.tv_distance.setMovementMethod(LinkMovementMethod.getInstance());
    Spanned text = Html.fromHtml("<a href=''>Distance</a>");
    holder.tv_distance.setText(details.getDistance().isEmpty()?text:details.getDistance()+"KM");

    holder.tv_distance.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Animation animZoomOut = AnimationUtils.loadAnimation(context,R.anim.zoom_out);
            v.startAnimation(animZoomOut);
            String  currentDistance = getLocationFromAddress(details.getAddress());
            holder.tv_distance.setText(currentDistance.equalsIgnoreCase("0")?"0KM":currentDistance+"KM");
            details.setDistance(currentDistance);
        }
    });



}catch (Exception e){

    e.printStackTrace();
}

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
              private TextView tv_firm_name;
              private TextView tv_address;
              private TextView tv_city;
              private TextView tv_distance;
              private TextView tv_mobile;
              private TextView tv_person ;
              private ImageView iv_start_call;
              private  ImageView iv_end_call;
              private  ImageView iv_planned_call;
        public MyViewHolder(View view) {
            super(view);

            tv_firm_name = (TextView)view.findViewById(R.id.tv_firm_name);
            tv_address = (TextView)view.findViewById(R.id.tv_address);
            tv_city = (TextView)view.findViewById(R.id.tv_city);
            tv_distance = (TextView)view.findViewById(R.id.tv_distance);
            tv_mobile = (TextView)view.findViewById(R.id.tv_mobile);
            tv_person = (TextView)view.findViewById(R.id.tv_person);
            iv_start_call = (ImageView)view.findViewById(R.id.iv_start_call);
            iv_end_call = (ImageView)view.findViewById(R.id.iv_end_call);
            iv_planned_call = (ImageView)view.findViewById(R.id.iv_planned_call);





           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                tv_distance.setText(Html.fromHtml("<a href=''> <u> Distance </u></a>", Html.FROM_HTML_MODE_COMPACT));
             else
                tv_distance.setText(Html.fromHtml("<a href=''> <u> Distance </u></a>"));

*/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    if(isClickable)
                    listener.onCustomerSelected(dataList.get(getAdapterPosition()));
                }
            });

          iv_start_call.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(isClickable)
                  listener.onCallStart(dataList.get(getAdapterPosition()));
              }
          });

            iv_end_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isClickable)
                    listener.onCallEnd(dataList.get(getAdapterPosition()));
                }
            });


        }
    }


    public interface CustomerAdapterListener {
        void onCustomerSelected(TerettoryListModel customer);
        void onCallStart(TerettoryListModel customer);
        void onCallEnd(TerettoryListModel customer);
    }



   @Override

    public Filter getFilter() {

        Filter filter  = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length()>0) {
                    List<TerettoryListModel> tmpDataShown = new ArrayList<TerettoryListModel>();
                    for (TerettoryListModel data : originalList) {
                        if (data.getFirmName().toLowerCase().contains(constraint.toString().toLowerCase())||data.getName().toLowerCase().contains(constraint.toString().toLowerCase())|| data.getMobileNo().contains(constraint.toString())) {
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


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    dataList.clear();
                    dataList.addAll((List<TerettoryListModel>)results.values);
                    notifyDataSetChanged();
                }

            }
        };
        return filter;

    };



    public String getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,2);
            if (address!=null && address.size()>0) {
                Address location1=address.get(0);
                Log.d(">>>","lat "+mlatitude+"  "+mlongitude);
                return distance(mlatitude, mlongitude, location1.getLatitude(), location1.getLongitude());
            }else{
                return "0";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }



    private String distance(double lat1, double lon1, double lat2, double lon2) {

        if(lat1==0.0 || lon1==0.0 || lat2 == 0.0 || lon2 == 0.0)
          return "0";

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(lon1);

        Location endPoint=new Location("locationB");
        endPoint.setLatitude(lat2);
        endPoint.setLongitude(lon2);

        double distance = startPoint.distanceTo(endPoint);
        return String.format("%.0f",distance/1000);
    }



}


