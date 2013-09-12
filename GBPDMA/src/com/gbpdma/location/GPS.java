package com.gbpdma.location;

import java.util.Observable;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author yasuravithana
 * 
 */
public class GPS extends Observable implements LocationListener {

    /*
     * this class implements LocationListener, which listens for both changes in the location of the device and changes in the status of the GPS system. And this class is responsible to provide the GPS data when rquested
     */

    LocationManager locationManager; // reference to the system's location manager
    Context context;// reference to the current context
    private double longitude, latitude;// variables to hold longitude and latitude
    private float accuracy;// variable to hold accuracy
    private long time;// variables to hold time at the current GPS lock

    // constructor
    public GPS(Context cntxt) {
	context = cntxt;
	locationManager = (LocationManager) context
		.getSystemService(Context.LOCATION_SERVICE);
    }

    // getters for the private variables
    public double getLongitude() {
	return longitude;
    }

    public double getLatitude() {
	return latitude;
    }

    public float getAccuracy() {
	return accuracy;
    }

    public long getTime() {
	return time;
    }

    // this will resume receiving GPS updates
    public void resume() {
	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		100, 0.01f, this);
    }

    // this will pause receiving GPS updates to save battery
    public void pause() {
	locationManager.removeUpdates(this);
    }

    @Override
    // this method is called when the GPS location is changed
    public void onLocationChanged(Location location) {

	longitude = location.getLongitude();// store the longitude
	latitude = location.getLatitude();// store the latitude
	accuracy = location.getAccuracy();// store the accuracy
	time = location.getTime();// store the time of the lock
	setChanged();// mark as the object has changed
	notifyObservers();// inform all observers about the change
    }

    @Override
    // this is called if/when the GPS is disabled in settings to bring up the GPS settings
    public void onProviderDisabled(String provider) {
	Intent intent = new Intent(
		android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	context.startActivity(intent);
    }

    @Override
    // this is called if/when the GPS is enabled
    public void onProviderEnabled(String provider) {
	Toast.makeText(context, "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    // this is called when the GPS status is changed
    public void onStatusChanged(String provider, int status, Bundle extras) {
	switch (status) {
	case LocationProvider.OUT_OF_SERVICE:
	    Toast.makeText(context, "Status Changed: Out of Service",
		    Toast.LENGTH_SHORT).show();
	    break;
	case LocationProvider.TEMPORARILY_UNAVAILABLE:
	    Toast.makeText(context, "Status Changed: Temporarily Unavailable",
		    Toast.LENGTH_SHORT).show();
	    break;
	case LocationProvider.AVAILABLE:
	    Toast.makeText(context, "Status Changed: Available",
		    Toast.LENGTH_SHORT).show();
	    break;
	}
    }

}
