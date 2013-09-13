package com.gbpdma.test;


import com.gbpdma.logic.LocationPoint;

import android.test.AndroidTestCase;


public class MapProcessorTest extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testSetCoordinates(){
		LocationPoint point = new LocationPoint(79.024587,6.02578);
		com.gbpdma.logic.MapProcessor.setCoordinates(point);
		assertEquals("X coordinates wrong!",8787133, point.getX(),0.5);
		assertEquals("Y coordinates wrong!",-670036, point.getY(),0.5);
	}

}
