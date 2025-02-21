package com.arcis.vgil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.DealerShipDetailsModel;

import java.util.List;

/**
 * Created by jaim on 12/30/2016.
 */

public class BottomSheetAdapter extends BaseAdapter {
    private LayoutInflater layoutinflater;
    List<DealerShipDetailsModel> listStorage;
    private Context context;


    public BottomSheetAdapter(Context context, List<DealerShipDetailsModel> result) {
        this.context = context;
        this.layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listStorage = result;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {

            return listStorage.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.bottom_sheet_shell, parent, false);
            listViewHolder.ContactName= (TextView)convertView.findViewById(R.id.et_contactname);
            listViewHolder.Mobile = (TextView)convertView.findViewById(R.id.et_mobile);
            listViewHolder.GarageName = (TextView)convertView.findViewById(R.id.et_garagename);
            listViewHolder.Address = (TextView)convertView.findViewById(R.id.et_Address);
            listViewHolder.City = (TextView)convertView.findViewById(R.id.et_City);
            listViewHolder.Email = (TextView)convertView.findViewById(R.id.et_Email);


            // listViewHolder.btninfo = (Button)convertView.findViewById(R.id.btnhelp);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.ContactName.setText(listStorage.get(position).getContactName());
        listViewHolder.Mobile.setText(String.valueOf(listStorage.get(position).getMobile()));
        listViewHolder.GarageName.setText(String.valueOf(listStorage.get(position).getGarageName()));
        listViewHolder.Address.setText(String.valueOf(listStorage.get(position).getAddress()));
        listViewHolder.City.setText(String.valueOf(listStorage.get(position).getCity()));
        listViewHolder.Email.setText(String.valueOf(listStorage.get(position).getEmail()));


        return convertView;
    }

    static class ViewHolder{

        TextView ContactName, Mobile, GarageName, Address, City, Email;



    }
}
