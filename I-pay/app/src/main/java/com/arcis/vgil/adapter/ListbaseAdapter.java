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

/**
 * Created by jaim on 05/04/16.
 */
public class ListbaseAdapter extends BaseAdapter {




    Context context;
    ArrayList<Beanclass> bean;




    public ListbaseAdapter(Context context, ArrayList<Beanclass> bean) {


        this.context = context;
        this.bean = bean;

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
            convertView = layoutInflater.inflate(R.layout.listview_shell, null);
            viewHolder = new ViewHolder();
            viewHolder.ll_listshell = (LinearLayout) convertView.findViewById(R.id.ll_listshell);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }


        Beanclass bean = (Beanclass)getItem(position);

        viewHolder.ll_listshell.setBackgroundResource(bean.getImage());
        viewHolder.title.setText(bean.getTitle());


        return convertView;
    }
        class ViewHolder {
            LinearLayout ll_listshell;
            TextView title;

        }
    }




