package com.gbpdma.test;

import com.gbpdma.logic.LocationPoint;
import com.gbpdma.logic.Polygon;

import android.test.AndroidTestCase;

public class polygonTest extends AndroidTestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testPolygon(){
		
		LocationPoint point1 = new LocationPoint(79.024587,6.02578);
		LocationPoint point2 = new LocationPoint(79.025487,6.02578);
		LocationPoint point3 = new LocationPoint(78.024587,6.02608);
		Polygon polygon1 = new Polygon("poly1");
		polygon1.points.add(point1);
		polygon1.points.add(point2);
		polygon1.points.add(point3);
		
		LocationPoint point4 = new LocationPoint(79.024587,6.02578);
		LocationPoint point5 = new LocationPoint(79.025487,6.02578);
		LocationPoint point6 = new LocationPoint(78.024587,6.02608);
		Polygon polygon2 = new Polygon("poly3");
		polygon2.points.add(point4);
		polygon2.points.add(point5);
		polygon2.points.add(point6);
		
		assertEquals(true, polygon1.equals(polygon2));
	}
}
