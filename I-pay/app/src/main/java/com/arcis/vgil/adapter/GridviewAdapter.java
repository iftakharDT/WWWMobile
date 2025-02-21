package com.arcis.vgil.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.Beanclass;

import java.util.ArrayList;


public class GridviewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Beanclass> bean;
    public GridviewAdapter(Context context, ArrayList<Beanclass> bean) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.gridview_shell, null);
            viewHolder = new ViewHolder();
            viewHolder.title1 = (TextView) convertView.findViewById(R.id.title1);
            viewHolder.ll_gridview = (LinearLayout) convertView.findViewById(R.id.ll_gridview);
            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        Beanclass bean = (Beanclass) getItem(position);
        viewHolder.title1.setText(bean.getTitle());
        viewHolder.ll_gridview.setBackgroundResource(bean.getImage());



        return convertView;
    }

    private class ViewHolder {

        TextView title1;
        LinearLayout ll_gridview;


    }
}

