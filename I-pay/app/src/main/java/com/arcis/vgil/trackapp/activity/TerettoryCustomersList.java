package com.arcis.vgil.trackapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.arcis.vgil.BuildConfig;
import com.arcis.vgil.R;
import com.arcis.vgil.trackapp.fragment.NavigationHomeFragment;
import com.arcis.vgil.trackapp.fragment.NavigationOtherFragment;
import com.arcis.vgil.trackapp.helper.BottomNavigationBehavior;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.text.DateFormat;
import java.util.Date;

public class TerettoryCustomersList extends BaseActivity {


	// Location Service new version code fusino
	private String mLastUpdateTime;
	// location updates interval - 10sec
	private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

	// fastest updates interval - 5 sec
	// location updates will be received if another app is requesting the locations
	// than your app can handle
	private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
	private static final int REQUEST_CHECK_SETTINGS = 100;
	// bunch of location related apis
	private FusedLocationProviderClient mFusedLocationClient;
	private SettingsClient mSettingsClient;
	private LocationRequest mLocationRequest;
	private LocationSettingsRequest mLocationSettingsRequest;
	private LocationCallback mLocationCallback;
	private Location mCurrentLocation;
	// boolean flag to toggle the ui


	private Boolean mRequestingLocationUpdates;

	private static final String TAG = "TerettoryCustomersList";

	private double mlatitude ;
	private double mlongitude ;
	public boolean isLatLongcaptured = false;


	public boolean getRequestingLocationUpdates(){
	return 	mRequestingLocationUpdates;

	}

	public void setRequestingLocationUpdates(boolean value){
		 	this.mRequestingLocationUpdates=value;

	}

	public double getLatitude(){
		return mlatitude;
	}
	public double getLongitude(){
		return mlongitude;
	}

	public boolean isLatLongcaptured() {
		return isLatLongcaptured;
	}

	public void setLatLongcaptured(boolean isLatLongcaptured) {
		this.isLatLongcaptured = isLatLongcaptured;
	}

	@Override
	public void inti() {
		// TODO Auto-generated method stub
		super.inti();
		setCurrentContext(this);
		setContentView(R.layout.teretorry_customer_list);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		// attaching bottom sheet behaviour - hide / show on scroll
		CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
		layoutParams.setBehavior(new BottomNavigationBehavior());

		loadFragment(new NavigationHomeFragment());


		initLocation();

	}


	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			Fragment fragment;
			switch (item.getItemId()) {
				case R.id.navigation_home:
					fragment = new NavigationHomeFragment();
					loadFragment(fragment);
					return true;
				case R.id.navigation_other:
					fragment = new NavigationOtherFragment();
					loadFragment(fragment);
					return true;

			}

			return false;
		}
	};

	/**
	 * loading fragment into FrameLayout
	 *
	 * @param fragment
	 */
	private void loadFragment(Fragment fragment) {
		// load fragment
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_container, fragment);
		//transaction.addToBackStack(null);
		transaction.commit();
	}


	private void initLocation(){

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
		}
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
					public void onFailure(@NonNull Exception e) {
						int statusCode = ((ApiException) e).getStatusCode();
						switch (statusCode) {
							case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
								Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
										"location settings ");
								try {
									// Show the dialog by calling startResolutionForResult(), and check the
									// result in onActivityResult().
									ResolvableApiException rae = (ResolvableApiException) e;
									rae.startResolutionForResult(TerettoryCustomersList.this, REQUEST_CHECK_SETTINGS);
								} catch (IntentSender.SendIntentException sie) {
									Log.i(TAG, "PendingIntent unable to execute request.");
								}
								break;
							case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
								String errorMessage = "Location settings are inadequate, and cannot be " +
										"fixed here. Fix in Settings.";
								Log.e(TAG, errorMessage);

								Toast.makeText(TerettoryCustomersList.this, errorMessage, Toast.LENGTH_LONG).show();
						}

						updateLocationUI();
					}
				});
	}


	private void openSettings() {
		Intent intent = new Intent();
		intent.setAction(
				Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package",
				BuildConfig.APPLICATION_ID, null);
		intent.setData(uri);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
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
