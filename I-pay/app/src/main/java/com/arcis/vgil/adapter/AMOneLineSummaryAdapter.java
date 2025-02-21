package com.arcis.vgil.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.activity.GetDataCallBack;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.model.AMOneLineSummaryDetailsModel;
import com.arcis.vgil.model.AMOneLineSummaryModel;
import com.arcis.vgil.parser.FetchingdataParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AMOneLineSummaryAdapter extends
        ArrayAdapter<AMOneLineSummaryModel> {

	private int resourceID;
	private LayoutInflater minflatitor;
	private ArrayList<AMOneLineSummaryModel> dealerOneLineSummaryList = new ArrayList<AMOneLineSummaryModel>();
	private Context context;
	private int pos = 0;
	AlertDialog dialog;

	public AMOneLineSummaryAdapter(Context context, int resource,
                                   ArrayList<AMOneLineSummaryModel> dealerOneLineSummaryList) {
		super(context, resource, dealerOneLineSummaryList);
		this.context = context;
		this.resourceID = resource;
		minflatitor = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dealerOneLineSummaryList = dealerOneLineSummaryList;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dealerOneLineSummaryList.size();
	}

	@Override
	public AMOneLineSummaryModel getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		AMOneLineSummaryModel details = dealerOneLineSummaryList
				.get(position);

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = minflatitor.inflate(resourceID, null);
			holder = new ViewHolder();
			holder.et_delaer_oneline_am_name = (TextView) convertView
					.findViewById(R.id.et_delaer_oneline_am_name);
			holder.et_dealer_oneline_total_outstanding = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_total_outstanding);
			holder.et_dealer_oneline_Current = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_Current);
			holder.et_dealer_oneline_OD_UPTO_30 = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_OD_UPTO_30);
			holder.et_dealer_oneline_OD_less_30 = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_OD_less_30);
			holder.et_dealer_oneline_OD_less_60 = (TextView) convertView
					.findViewById(R.id.et_dealer_oneline_OD_less_60);

			convertView.setTag(holder);
		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.et_dealer_oneline_Current.setTag(position);
		holder.et_dealer_oneline_total_outstanding.setTag(position);
		holder.et_dealer_oneline_OD_UPTO_30.setTag(position);
		holder.et_dealer_oneline_OD_less_30.setTag(position);
		holder.et_dealer_oneline_OD_less_60.setTag(position);

		if (details.getAMName().equalsIgnoreCase("TOTAL")) {
			holder.et_delaer_oneline_am_name.setText("TOTAL");
			holder.et_dealer_oneline_Current.setText(details.getCurrent());
			holder.et_dealer_oneline_total_outstanding.setText(details
					.getTotalOutstanding());
			holder.et_dealer_oneline_OD_UPTO_30
					.setText(details.getOD_UPTO_30());
			holder.et_dealer_oneline_OD_less_30
					.setText(details.getOD_LESS_30());
			holder.et_dealer_oneline_OD_less_60.setText(details
					.getOD_LESSS_60());

			holder.et_dealer_oneline_Current.setClickable(false);
			holder.et_dealer_oneline_total_outstanding.setClickable(false);
			holder.et_dealer_oneline_OD_UPTO_30.setClickable(false);
			holder.et_dealer_oneline_OD_less_30.setClickable(false);
			holder.et_dealer_oneline_OD_less_60.setClickable(false);

		} else {
			holder.et_delaer_oneline_am_name.setText(details.getAMName());
			holder.et_dealer_oneline_total_outstanding.setText(details
					.getTotalOutstanding());
			holder.et_dealer_oneline_Current.setText(details.getCurrent());
			holder.et_dealer_oneline_OD_UPTO_30
					.setText(details.getOD_UPTO_30());
			holder.et_dealer_oneline_OD_less_30
					.setText(details.getOD_LESS_30());
			holder.et_dealer_oneline_OD_less_60.setText(details
					.getOD_LESSS_60());

			holder.et_dealer_oneline_Current.setClickable(true);
			holder.et_dealer_oneline_total_outstanding.setClickable(true);
			holder.et_dealer_oneline_OD_UPTO_30.setClickable(true);
			holder.et_dealer_oneline_OD_less_30.setClickable(true);
			holder.et_dealer_oneline_OD_less_60.setClickable(true);

			holder.et_dealer_oneline_total_outstanding
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// TODO Auto-generated method stub
							pos = (Integer) v.getTag();
							AMOneLineSummaryModel details = dealerOneLineSummaryList
									.get(pos);
							AmOneLineSummaryDetail(details.getAMID(),
									"TotalOutstanding");
						}
					});

			holder.et_dealer_oneline_Current
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pos = (Integer) v.getTag();
							AMOneLineSummaryModel details = dealerOneLineSummaryList
									.get(pos);
							AmOneLineSummaryDetail(details.getAMID(),
									"Current");

						}
					});
			holder.et_dealer_oneline_OD_UPTO_30
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pos = (Integer) v.getTag();
							AMOneLineSummaryModel details = dealerOneLineSummaryList
									.get(pos);
							AmOneLineSummaryDetail(details.getAMID(),
									"OD UPTO 30");
						}
					});

			holder.et_dealer_oneline_OD_less_30
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pos = (Integer) v.getTag();
							AMOneLineSummaryModel details = dealerOneLineSummaryList
									.get(pos);
							AmOneLineSummaryDetail(details.getAMID(),
									"OD>30");
						}
					});
			holder.et_dealer_oneline_OD_less_60
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pos = (Integer) v.getTag();
							AMOneLineSummaryModel details = dealerOneLineSummaryList
									.get(pos);
							AmOneLineSummaryDetail(details.getAMID(),
									"OD>60");

						}
					});
		}

		return convertView;
	}

	protected void AmOneLineSummaryDetail(String amID, String string) {
		// TODO Auto-generated method stub
		GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(context,
				ProgressDialog.STYLE_SPINNER, "Loading...",
				new GetDataCallBack() {
					@Override
					public void processResponse(Object response) {
						if (response != null) {

							ArrayList<AMOneLineSummaryDetailsModel> dealerOneLineSummaryList = FetchingdataParser
									.getAM_ONeLineSummayDetails(response
											.toString());
							if (dealerOneLineSummaryList != null) {
								if (dealerOneLineSummaryList.size() != 0) {

									// dealerOneLineSummaryAdapter=new
									// DealerOneLineSummaryAdapter(context,
									// R.layout.dealer_oneline_summary_shell,
									// dealerOneLineSummaryList);
									// listview_oneline_dealer.setAdapter(dealerOneLineSummaryAdapter);

									LayoutInflater inflator = (LayoutInflater) context
											.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
									View view = inflator
											.inflate(
													R.layout.am_oneline_summary_details,
													null);
									ListView listview_oneline_dealer_details = (ListView) view
											.findViewById(R.id.listview_oneline_dealer);
									TextView tittle_text = (TextView) view
											.findViewById(R.id.tittle_activity);
									tittle_text.setText("Details Screen");
									AlertDialog.Builder builder = new AlertDialog.Builder(
											context);
									builder.setView(view);

									AmSummaryDetailsAdapter dealerOneLineSummaryAdapter = new AmSummaryDetailsAdapter(
											context,
											R.layout.am_oneline_summary_details_shell,
											dealerOneLineSummaryList);
									listview_oneline_dealer_details
											.setAdapter(dealerOneLineSummaryAdapter);

									builder.setPositiveButton(
											"OK",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();
												}
											});

									dialog = builder.show();
								}

							} else {
								Toast.makeText(
										context,
										context.getResources().getString(
												R.string.message4)
												+ "4", Toast.LENGTH_SHORT)
										.show();

							}

						} else if (response == null) {
							Toast.makeText(
									context,
									context.getResources().getString(
											R.string.error4),
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		LinkedHashMap<String, Object> request = new LinkedHashMap<String, Object>();
		SharedPreferences passworddetails = context.getSharedPreferences(
				"PASSWORD", context.MODE_PRIVATE);

		try {

			//Function AMOneLineSummaryDetail(ByVal ContactTypeID As String,
			//ByVal ContactID As String, ByVal AMID As String, ByVal StatusType As String) As String

			request.put("ContactTypeID",
					passworddetails.getString(Constants.CONTACTTYPEID, ""));
			request.put("ContactID",
					passworddetails.getString(Constants.USERID, ""));
			request.put("AMID", amID);
			request.put("StatusType", string);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String ipAddress = context.getResources().getString(R.string.ipaddress);
		String webService = context.getResources().getString(
				R.string.Webservice_mis_android);
		String nameSpace = context.getResources().getString(R.string.nameSpace);
		String methodName = "AMOneLineSummaryDetail";
		String soapcomponent = context.getResources().getString(
				R.string.soapcomponent_androidmis);
		dataFromNetwork.setConfig(ipAddress, webService, nameSpace, methodName,
				soapcomponent);
		dataFromNetwork.sendData(request);
		dataFromNetwork.execute();
	}

	static class ViewHolder {
		private TextView et_delaer_oneline_am_name,
				et_dealer_oneline_total_outstanding, et_dealer_oneline_Current,
				et_dealer_oneline_OD_UPTO_30, et_dealer_oneline_OD_less_30,
				et_dealer_oneline_OD_less_60;

	}

}
