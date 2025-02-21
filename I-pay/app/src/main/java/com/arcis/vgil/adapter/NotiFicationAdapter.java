package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.NotificationListModel;

import java.util.List;

/**
 * Created by jaim on 5/22/2017.
 */

public class NotiFicationAdapter   extends ArrayAdapter<NotificationListModel> {

    private int resourceId;
    private LayoutInflater minflator;
    private Context mContext;
    deleteToListenerNotification    deleteToListener;

    public interface deleteToListenerNotification{
        public void onDeleteToListenerNotification(int position);
    }
    public void setdeleteToListener(deleteToListenerNotification deleteToListener) {
        this.deleteToListener = deleteToListener;
    }


    public NotiFicationAdapter(Context context, int resource, List<NotificationListModel> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        resourceId = resource;
        minflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext=context;
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
        NotificationListModel details = getItem(position);
        if(convertView==null){
            convertView = minflator.inflate(resourceId, null);
            holder = new ViewHolder();
            holder.tv_message = (TextView)convertView.findViewById(R.id.tv_message);
            holder.tv_braodcast = (TextView)convertView.findViewById(R.id.tv_braodcast);
            holder.im_delete  = (ImageView) convertView.findViewById(R.id.im_delete);


            convertView.setTag(holder);
        }

    else{
            holder = (ViewHolder)convertView.getTag();
    }
        holder.im_delete.setTag(position);

        holder.tv_message.setText(details.getMessage());
        if (details.getNotificationType().equalsIgnoreCase("1")){
            holder.tv_braodcast.setText(". Braodcast");
            holder.tv_braodcast.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.im_delete.setVisibility(View.VISIBLE);
        }else {
            holder.tv_braodcast.setText(". Alert Message ");
            holder.tv_braodcast.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.im_delete.setVisibility(View.INVISIBLE);
        }

        holder.im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(Integer) v.getTag();
                if (deleteToListener != null) {
                    deleteToListener.onDeleteToListenerNotification(pos);
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
    private static class ViewHolder{
        private TextView tv_message,tv_braodcast;
        private ImageView im_delete;



    }

}


