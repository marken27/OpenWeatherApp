package com.example.galileo.openweatherapp.utility;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GLocBase implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener {

	private GoogleApiClient mGoogleApiClient;
	private LocationRequest requestLoc;
	public Location currLoc;
	public static double latitude = 0.0; // latitude
	public static double longitude = 0.0; // longitude

	private static GLocBase gLocBase;

	public synchronized static GLocBase getInstance(Context context) {
		if(gLocBase == null) {
			gLocBase = new GLocBase(context);
		} return (gLocBase);
	}

	public GLocBase(Context context){
		initSetup(context);
	}

	public void initSetup(Context context) {
		// TODO Auto-generated method stub

		Log.e("=-=-=-Glocbase", "init setup");

		if(mGoogleApiClient == null) {
			mGoogleApiClient = new GoogleApiClient.Builder(context)
					.addApi(LocationServices.API)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.build();

			requestLoc = LocationRequest.create();
			requestLoc.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			requestLoc.setInterval((1000 * 300000) * 1);
			requestLoc.setFastestInterval((1000 * 300000) * 1);

			if (!mGoogleApiClient.isConnected())
				mGoogleApiClient.connect();
		} else {
			Log.e("=-=-=-Glocbase", "googleApiClient != null");
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		try {
			currLoc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
			if(mGoogleApiClient != null) {
				if (location != null) {
					latitude = currLoc.getLatitude();
					longitude = currLoc.getLongitude();
				}
			}
		} catch(SecurityException e) { }
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		try {
			if(mGoogleApiClient != null)
				LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, requestLoc, this);

			if (mGoogleApiClient == null) {
				currLoc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
			}
		} catch(SecurityException e) { }
	}

	@Override
	public void onConnectionSuspended(int i) { }

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

	public String getLocation() {
		String Location = latitude + "/" + longitude;
		return Location;
	}

	public static double getLatitude() {
		return latitude;
	}

	public static double getLongitude(){
		return longitude;
	}

	public void disconnectFuse() {
		if(mGoogleApiClient != null) {
			if (mGoogleApiClient.isConnected()) {
				LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
				mGoogleApiClient.disconnect();
				Log.e("=====disconnecting", "disconnecting from google api");
			} else Log.e("=====disconnecting", "already disconnected");
		} else Log.e("=====disconnecting", "mGoogleApiClient null");
	}

	public void connectFuse() {
		if(mGoogleApiClient != null) {
			if (!mGoogleApiClient.isConnected()) {
				Log.e("=-=-GlocBase", "connecting");
				mGoogleApiClient.connect();
			}else Log.e("=-=-GlocBase", "already connected");
		} else Log.e("=-=-GlocBase", "null googleClientApi");
	}
}
