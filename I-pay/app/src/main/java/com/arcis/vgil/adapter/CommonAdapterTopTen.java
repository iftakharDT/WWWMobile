package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.CommonTopTenModel;

import java.util.List;

/**
 * Created by jaim on 3/21/2017.
 */
public class CommonAdapterTopTen  extends ArrayAdapter<CommonTopTenModel> {

private int resourceId;
private LayoutInflater minflator;
public CommonAdapterTopTen(Context context, int resource, List<CommonTopTenModel> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        resourceId = resource;
        minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
@Override
public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder  = null;
        CommonTopTenModel details = getItem(position);
        if(convertView==null){
        convertView = minflator.inflate(resourceId, null);
        holder = new ViewHolder();
        holder.name = (TextView)convertView.findViewById(R.id.textView1);
        holder.city = (TextView)convertView.findViewById(R.id.textView2);
        holder.value  = (TextView)convertView.findViewById(R.id.textView3);
        holder.tv_level  = (TextView)convertView.findViewById(R.id.tv_level);

        convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        holder.name.setText(details.getName());
        holder.city.setText(details.getCity());
        holder.value.setText(details.getValue());
        holder.tv_level.setText(String.valueOf(position+1));



        return convertView;
        }


@Override
public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return true;
        }
private static class ViewHolder{
    private TextView name,city,value,tv_level;

}

}

