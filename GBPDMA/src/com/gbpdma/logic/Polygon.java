package com.gbpdma.logic;

import java.util.LinkedList;

/**
 * @author yasuravithana
 *
 */
public class Polygon {
//This is the template for a polygon which can be used to indicate the boundary or landmark
    
    private String name;//name of the polygon
    public LinkedList<LocationPoint> points;//list of points that makes the polygon

    //constructor
    public Polygon(String name) {
	this.name = name;//set name
	points = new LinkedList<LocationPoint>();//initialize a points list
    }

    //getters
    public String getName() {
	return name;
    }

    @Override
    // This overrides how two Polygon objects are compared so that it is based on the points, not the object id.
    public boolean equals(Object other) {
	boolean result = false;
	//checks if the other object is a Polygon instance
	if (other instanceof Polygon) {
	    Polygon that = (Polygon) other;
	    //result will be true if the points are the same.
	    result = (this.points.equals(that.points));
	}
	return result;
    }
}
