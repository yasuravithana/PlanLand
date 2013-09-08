package com.gbpdma;

import java.util.LinkedList;

public class Polygon {

	private String name;
	public LinkedList<LocationPoint> points;
	
	public Polygon(String name)
	{
		this.name = name;
		points = new LinkedList<LocationPoint>();
	}
	
	public String getName() {
		return name;
	}
	
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof Polygon) {
	    	Polygon that = (Polygon) other;
	        result = (this.points.equals(that.points));
	    }
	    return result;
	}
}
