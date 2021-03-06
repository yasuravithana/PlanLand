package com.gbpdma.logic;

/**
 * @author yasuravithana
 * 
 */
public class LocationPoint {// this is the template for a location point

    private double longitude;// holds the longitude of the point
    private double latitude;// holds the latitude of the point
    private float x;// holds the x-coordinates of the point that is used when drawing the map. Not valid until the point is processed
    private float y;// holds the y-coordinates of the point that is used when drawing the map. Not valid until the point is processed

    // constructor sets the longitude and latitude
    public LocationPoint(double longitude, double latitude) {
	this.longitude = longitude;
	this.latitude = latitude;
    }

    // getters and setters for the private variables
    public double getLongitude() {
	return longitude;
    }

    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

    public double getLatitude() {
	return latitude;
    }

    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    public float getX() {
	return x;
    }

    public void setX(float x) {
	this.x = x;
    }

    public float getY() {
	return y;
    }

    public void setY(float y) {
	this.y = y;
    }

    @Override
    // This overrides how two LocationPoint objects are compared so that it is based on the location, not the object id.
    public boolean equals(Object other) {
	boolean result = false;
	//checks if the other object is a LocationPoint instance
	if (other instanceof LocationPoint) {
	    LocationPoint that = (LocationPoint) other;
	    //result will be true if the longitudes and latitudes are the same.
	    result = (this.getLongitude() == that.getLongitude() && this.getLatitude() == that.getLatitude());
	}
	return result;
    }
}
