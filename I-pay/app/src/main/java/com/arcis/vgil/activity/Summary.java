package com.arcis.vgil.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.arcis.vgil.R;
import com.arcis.vgil.adapter.ShowCustomerDetailsAdapter;
import com.arcis.vgil.connectivity.GetDataFromNetwork;
import com.arcis.vgil.data.Constants;
import com.arcis.vgil.parser.FetchingdataParser;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.client.android.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class Summary extends BaseActivity {

    // Location Service new version code fusino
    private String mLastUpdateTime;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2*60*60*1000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 2*60*60*1000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui


    private Boolean mRequestingLocationUpdates = false;


    private static final String TAG = Customer_visit_metting_Activity.class.getSimpleName();

    private double mlatitude;
    private double mlongitude;
    public boolean isLatLongcaptured = false;


    public boolean getRequestingLocationUpdates() {
        return mRequestingLocationUpdates;

    }

    public void setRequestingLocationUpdates(boolean value) {
        this.mRequestingLocationUpdates = value;

    }

    public double getLatitude() {
        return mlatitude;
    }

    public double getLongitude() {
        return mlongitude;
    }

    public boolean isLatLongcaptured() {
        return isLatLongcaptured;
    }

    public void setLatLongcaptured(boolean isLatLongcaptured) {
        this.isLatLongcaptured = isLatLongcaptured;
    }




    private LinearLayout rl_root;
    private TextView tv_welcome;
    private TextView tv_primary_customers;
    private TextView tv_secondary_customers;
    private TextView tv_last_visited_date;
    private TextView tv_last_customer;
    private TextView tv_last_location;
    private RelativeLayout tv_;
    private String subAdminArea;
    private String locality;
    private String adminArea;
    private Button btn_next;


    @Override
    public void inti() {
        super.inti();
        setContentView(R.layout.activity_summary);

        rl_root =findViewById(R.id.rl_root);
        rl_root.setVisibility(View.GONE);
        tv_welcome =findViewById(R.id.tv_welcome);
        btn_next =findViewById(R.id.btn_next);
        tv_primary_customers =findViewById(R.id.tv_primary_customers);
        tv_secondary_customers =findViewById(R.id.tv_secondary_customers);
        tv_last_visited_date =findViewById(R.id.tv_last_visited_date);
        tv_last_customer =findViewById(R.id.tv_last_customer);
        tv_last_location =findViewById(R.id.tv_last_location);


        tv_primary_customers.setOnClickListener(this);
        tv_secondary_customers.setOnClickListener(this);
        btn_next.setOnClickListener(this);


        turnGPSOn();
        initLocation();

       // getWelcomeData("Gurgaon","Gurugram","Haryana");
      // getWelcomeData();
    }


    private void turnGPSOn() {
      /*  String provider = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            this.sendBroadcast(poke);
        }*/
    }

    private void initLocation() {


        // location services
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };
        mRequestingLocationUpdates = false;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        startLocationClick();
    }


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {

        if (mCurrentLocation != null) {
            mlatitude = mCurrentLocation.getLatitude();
            mlongitude = mCurrentLocation.getLongitude();
            setLatLongcaptured(true);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(mlatitude, mlongitude, 1);
                tv_welcome.setText("Welcome To "+addresses.get(0).getSubAdminArea());
                // Create the TypeFace from the TTF asset
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/krinkesregularpersonal.ttf");
// Assign the typeface to the view
                tv_welcome.setTypeface(font);

                subAdminArea= addresses.get(0).getSubAdminArea();
                locality=addresses.get(0).getLocality();
                adminArea= addresses.get(0).getAdminArea();

               // String cityName = addresses.get(0).getAddressLine(0);
               /* txtLocationResult.setText(
                           "Latitude:    " + mCurrentLocation.getLatitude() +
                                "\n\nLongitude:  " + mCurrentLocation.getLongitude() +
                                "\n\n addresses     " + addresses.toString()+
                                 "\n\n City Name     " + cityName +
                                "\n\n AdminArea     " + addresses.get(0).getAdminArea() +
                                "\n\n CountryName     " + addresses.get(0).getCountryName()+
                                "\n\n Locality     " + addresses.get(0).getLocality()+
                                "\n\n PostalCode     " + addresses.get(0).getPostalCode()+
                                "\n\n Premises     " + addresses.get(0).getPremises()+
                                "\n\n SubAdminArea     " + addresses.get(0).getSubAdminArea()+
                                "\n\n SubLocality     " + addresses.get(0).getSubLocality()


                );
*/

                  getWelcomeData();
                //getWelcomeData(addresses.get(0).getSubAdminArea(),addresses.get(0).getLocality(),addresses.get(0).getAdminArea());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void  getWelcomeData(){

            GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(Summary.this, ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

                @Override
                public void processResponse(Object result) {
                    // TODO Auto-generated method stub
                    if(result==null){
                        Toast.makeText(Summary.this, getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
                    }else{
                        ArrayList<HashMap<String, String >> welcomeDataList = FetchingdataParser.getWelcomeData(result.toString());
                        if(welcomeDataList==null){
                            AlertDialog.Builder errordialog = new AlertDialog.Builder(Summary.this);
                            errordialog.setTitle("Error!");
                            errordialog.setMessage(result.toString());
                            errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = errordialog.create();
                            dialog.show();
                        }else{
                            if(welcomeDataList.size()==0){
                                Toast.makeText(Summary.this, "No data found!", Toast.LENGTH_SHORT).show();
                            }else{

                                rl_root.setVisibility(View.VISIBLE);
                                tv_primary_customers.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", welcomeDataList.get(0).get(Constants.PRIMARY_CUSTOMER))));
                                tv_secondary_customers.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", welcomeDataList.get(0).get(Constants.SECONDRY_CUSTOMER))));
                                tv_last_visited_date.setText(welcomeDataList.get(0).get(Constants.LAST_VISITED_DATE));
                                tv_last_customer.setText(welcomeDataList.get(0).get(Constants.LAST_CUSTOMER));
                                tv_last_location.setText(welcomeDataList.get(0).get(Constants.LAST_LOCATION));

                            }
                        }

                    }

                }
            });

            LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
            SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
            try {
                //Constants.contactTypeIDForDealer
                requestdata.put("ContactID", passworddetails.getString(Constants.USERID, ""));
                requestdata.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID, ""));
                requestdata.put("Location1", "Allahabad");
               // requestdata.put("Location1", ""+subAdminArea);
                requestdata.put("Location2", ""+locality);
                requestdata.put("Location3", ""+adminArea);
                requestdata.put("Location4", "");
                requestdata.put(Constants.username, passworddetails.getString("username",""));
                requestdata.put(Constants.password,passworddetails.getString("password",""));
            } catch (Exception e) {
                e.printStackTrace();
            }

        String ipAddress=getResources().getString(R.string.ipaddress);
        String webService =getResources().getString(R.string.Webservice_mis_android);
        String nameSpace=getResources().getString(R.string.nameSpace);
        String methodName="GetWelcomeData";
        String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
        datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
        datafromnetwork.sendData(requestdata);
        datafromnetwork.execute();




    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_next:
                startActivity(new Intent(v.getContext(), MarketingTeamDasboard.class));
                break;
            case R.id.tv_primary_customers:
                showPSCustomerDetails("P");
                break;
            case R.id.tv_secondary_customers:
                showPSCustomerDetails("S");
                break;
        }


    }

private void showPSCustomerDetails(String customerType){


    GetDataFromNetwork datafromnetwork = new GetDataFromNetwork(Summary.this, ProgressDialog.STYLE_SPINNER, "Loading products...", new GetDataCallBack() {

        @Override
        public void processResponse(Object result) {
            // TODO Auto-generated method stub
            if(result==null){
                Toast.makeText(Summary.this, getResources().getString(R.string.error4), Toast.LENGTH_LONG).show();
            }else{
                ArrayList<HashMap<String, String >> customerDetailsList = FetchingdataParser.getCustomerDetails(result.toString());
                if(customerDetailsList==null){
                    AlertDialog.Builder errordialog = new AlertDialog.Builder(Summary.this);
                    errordialog.setTitle("Error!");
                    errordialog.setMessage(result.toString());
                    errordialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = errordialog.create();
                    dialog.show();
                }else{
                    if(customerDetailsList.size()==0){
                        Toast.makeText(Summary.this, "No data found!", Toast.LENGTH_SHORT).show();
                    }else{

                        showCustomerDetails(customerDetailsList);

                        /*rl_root.setVisibility(View.VISIBLE);
                        tv_primary_customers.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", welcomeDataList.get(0).get(Constants.PRIMARY_CUSTOMER))));
                        tv_secondary_customers.setText(Html.fromHtml(String.format("<a href=\"\">%s</a> ", welcomeDataList.get(0).get(Constants.SECONDRY_CUSTOMER))));
                        tv_last_visited_date.setText(welcomeDataList.get(0).get(Constants.LAST_VISITED_DATE));
                        tv_last_customer.setText(welcomeDataList.get(0).get(Constants.LAST_CUSTOMER));
                        tv_last_location.setText(welcomeDataList.get(0).get(Constants.LAST_LOCATION));*/

                    }
                }

            }

        }
    });

    LinkedHashMap<String,Object> requestdata= new LinkedHashMap<String, Object>();
    SharedPreferences passworddetails=getSharedPreferences("PASSWORD", MODE_PRIVATE);
    try {
        //Constants.contactTypeIDForDealer
        requestdata.put("CustomerType", customerType);
        requestdata.put("ContactID", passworddetails.getString(Constants.USERID, ""));
        requestdata.put("ContactTypeID", passworddetails.getString(Constants.CONTACTTYPEID, ""));
       // requestdata.put("Location1", "Allahabad");
        requestdata.put("Location1", ""+subAdminArea);
        requestdata.put("Location2", ""+locality);
        requestdata.put("Location3", ""+adminArea);
        requestdata.put("Location4", "");
        requestdata.put(Constants.username, passworddetails.getString("username",""));
        requestdata.put(Constants.password,passworddetails.getString("password",""));
    } catch (Exception e) {
        e.printStackTrace();
    }

    String ipAddress=getResources().getString(R.string.ipaddress);
    String webService =getResources().getString(R.string.Webservice_mis_android);
    String nameSpace=getResources().getString(R.string.nameSpace);
    String methodName="GetWelcomeData_CustomerDetail";
    String soapcomponent=getResources().getString(R.string.soapcomponent_androidmis);
    datafromnetwork.setConfig(ipAddress, webService, nameSpace,methodName,soapcomponent);
    datafromnetwork.sendData(requestdata);
    datafromnetwork.execute();


}


private void showCustomerDetails(ArrayList<HashMap<String, String >> customerDetailsList){

     LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     View view  = inflator.inflate(R.layout.dialog_ps_customer_details, null);
     RecyclerView rv_pending=view.findViewById(R.id.rv_pending);
     BottomSheetDialog  bottomSheetDialog = new BottomSheetDialog(Summary.this);
     bottomSheetDialog.setContentView(view);
     bottomSheetDialog.show();


    LinearLayoutManager rv_attribute_lm = new LinearLayoutManager(Summary.this);
    rv_attribute_lm.setOrientation(LinearLayoutManager.VERTICAL);
    rv_pending.setLayoutManager(rv_attribute_lm);

    ShowCustomerDetailsAdapter showCustomerDetailsAdapter = new ShowCustomerDetailsAdapter(getApplicationContext(),customerDetailsList);
    rv_pending.setAdapter(showCustomerDetailsAdapter);


}







    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        // Log.i(TAG, "All location settings are satisfied.");

                        // Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission


                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());


                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@android.support.annotation.NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(Summary.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(Summary.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    private void openSettings() {

        try {
            Intent intent = new Intent();
            intent.setAction(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",
                    BuildConfig.LIBRARY_PACKAGE_NAME, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startLocationClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }










}
