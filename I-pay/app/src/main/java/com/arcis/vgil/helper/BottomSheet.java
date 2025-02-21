package com.arcis.vgil.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ListView;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.BottomSheetAdapter;
import com.arcis.vgil.model.DealerShipDetailsModel;

import java.util.List;

public class BottomSheet extends BottomSheetDialog {

    private Context context;
    public List<DealerShipDetailsModel> result;

    public BottomSheet(Context context, List<DealerShipDetailsModel> result){
        super(context);

        this.context = context;
        this.result=result;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        setContentView(view);
        //ListView for the items
        ListView listView = (ListView) view.findViewById(R.id.list_items);
        BottomSheetAdapter adapter = new BottomSheetAdapter(context, result);
        listView.setAdapter(adapter);

    }
}
