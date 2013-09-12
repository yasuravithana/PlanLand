package com.gbpdma.logic;

public class LocationPoint {

	private double longitude;
	private double latitude;
	private float x;
	private float y;
	
	public LocationPoint(double longitude, double latitude)
	{
		this.longitude=longitude;
		this.latitude=latitude;
	}
	
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
	
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof LocationPoint) {
	    	LocationPoint that = (LocationPoint) other;
	        result = (this.getLongitude() == that.getLongitude() && this.getLatitude() == that.getLatitude());
	    }
	    return result;
	}
}
