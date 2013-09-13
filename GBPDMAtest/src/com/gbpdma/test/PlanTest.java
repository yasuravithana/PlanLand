package com.gbpdma.test;

import com.gbpdma.logic.LocationPoint;
import com.gbpdma.logic.Plan;
import com.gbpdma.logic.Polygon;

import android.hardware.Camera.Size;
import android.test.AndroidTestCase;

public class PlanTest extends AndroidTestCase {
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testPlan(){
		Plan plan = new Plan("test");
		assertNotNull(plan.boundary);
		assertNotNull(plan.landmarks);
		assertEquals(0, plan.landmarks.size());
		plan.landmarks.add(new Polygon("testPoly"));
		assertEquals(1, plan.landmarks.size());
	}		
}
