package com.arcis.vgil.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.arcis.vgil.R;
import com.arcis.vgil.model.AMVisitDiaryModel;

import java.util.ArrayList;

/**
 * Created by jaim on 2/7/2017.
 */

public class AmVisitDiaryAdapter  extends
        ArrayAdapter<AMVisitDiaryModel> {
    private int resourceID;
    private LayoutInflater minflatitor;
    private ArrayList<AMVisitDiaryModel> dealerOneLineSummaryList = new ArrayList<AMVisitDiaryModel>();
    private Context context;
     AMVisitDiaryModel details;
    ViewHolder holder = null;
    int pos;


    public AmVisitDiaryAdapter(Context context, int resource,
                               ArrayList<AMVisitDiaryModel> dealerOneLineSummaryList) {
        // TODO Auto-generated constructor stub
        super(context, resource, dealerOneLineSummaryList);
        this.context = context;
        this.resourceID = resource;
        minflatitor = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dealerOneLineSummaryList = dealerOneLineSummaryList;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return dealerOneLineSummaryList.size();
    }

    @Override
    public AMVisitDiaryModel getItem(int position) {
        // TODO Auto-generated method stub
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        details = dealerOneLineSummaryList
                .get(position);

        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = minflatitor.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.e_meetingnotes = (TextView) convertView
                    .findViewById(R.id.e_meetingnotes);
            holder.et_date = (TextView) convertView.findViewById(R.id.et_date);
            holder.et_call = (TextView) convertView
                    .findViewById(R.id.et_call);
            holder.et_activ_notes = (EditText) convertView
                    .findViewById(R.id.et_activ_notes);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.et_activ_notes.setTag(position);
        holder.e_meetingnotes.setText(details.getMeetingNotes());
        holder.et_date.setText(details.getDate());
        holder.et_call.setText(details.getCallTime());

       /* holder.et_activ_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos= (Integer) v.getTag();
                Toast.makeText(context,String.valueOf(pos), Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.et_activ_notes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pos= (Integer) v.getTag();
                if(MotionEvent.ACTION_UP == event.getAction()) {
                }
                return false; // return is important...
            }
        });

        holder.et_activ_notes.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                dealerOneLineSummaryList.get(pos).setActionNotes(s.toString());
            }
        });

        return convertView;
    }

    static class ViewHolder {
        private TextView e_meetingnotes, et_call ,et_date;
        private EditText et_activ_notes;

    }
}
