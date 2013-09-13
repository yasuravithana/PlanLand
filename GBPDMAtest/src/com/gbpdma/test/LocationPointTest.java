package com.gbpdma.test;

import com.gbpdma.logic.LocationPoint;

import android.test.AndroidTestCase;

public class LocationPointTest extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testLocationPoints(){
		LocationPoint point1 = new LocationPoint(79.024587,6.02578);
		LocationPoint point2 = new LocationPoint(79.024587,6.02578);
		LocationPoint point3 = new LocationPoint(78.024587,6.02578);
		assertEquals(true, point1.equals(point2));
		assertEquals(false, point1.equals(point3));
	}
}
