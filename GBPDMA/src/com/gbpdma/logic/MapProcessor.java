package com.gbpdma.logic;

/**
 * @author yasuravithana
 * 
 */
public class MapProcessor {// this class provides the necessary facilities
    static final int earthRadius = 6371;// earth radius in km
    static double centerLongitude = 0, centerLatitude = 0;// variables holding the location of the center of the plan

    //this is used to completely process all the points included in a plan
    public static void setCoordinates(Plan plan) {
	getCenter(plan);//this is called to set valid values for centerLongitude and centerLatitude variables
	setCoordinates(plan.boundary);//calls to process points of the boundary
	for (Polygon landmark : plan.landmarks) {//for each landmark in the plan
	    setCoordinates(landmark);//calls to process points of the landmark
	}
    }

  //this is used to completely process all the points included in a polygon
    public static void setCoordinates(Polygon polygon) {
	for (LocationPoint point : polygon.points) {//for each point in the points list
	    setCoordinates(point);//process the point
	}
    }

    //used to process a single point
    public static void setCoordinates(LocationPoint point) {
	//the x and y coordinates are found considering the earth as a sphere. ie. arc length = angle in rad * radius
	point.setX((float) (Math.toRadians(point.getLongitude()
		- centerLongitude) * earthRadius) * 1000);//calculate and set x assuming the point described by centerLongitude and centerLatitude variables is origin
	point.setY(-(float) (Math.toRadians(point.getLatitude()
		- centerLatitude) * earthRadius) * 1000);//calculate and set y assuming the point described by centerLongitude and centerLatitude variables is origin. Negative is used as the y-axis of canvas, etc. is inverted 
    }

    //this sets valid values for centerLongitude and centerLatitude variables
    public static void getCenter(Plan map) {
	double sumLongitudes = 0, sumLatitudes = 0;
	//calculate the sum of longitudes and latitudes of the points of the boundary
	for (LocationPoint point : map.boundary.points) {
	    sumLongitudes += point.getLongitude();
	    sumLatitudes += point.getLatitude();
	}
	//calculate the average of longitudes and latitudes of the points of the boundary
	centerLongitude = sumLongitudes / map.boundary.points.size();
	centerLatitude = sumLatitudes / map.boundary.points.size();
    }
}
