package com.gbpdma.test;


import org.xmlpull.v1.XmlPullParserException;

import com.gbpdma.io.FileHandler;
import com.gbpdma.logic.LocationPoint;
import com.gbpdma.logic.Plan;
import com.gbpdma.logic.Polygon;

import android.test.AndroidTestCase;


public class XmlTest extends AndroidTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testName() throws XmlPullParserException {
//		Activity welcome = new Welcome();
//		Intent intent = new Intent(welcome,CreateMap.class);
//		 welcome.startActivity(intent);
		Plan map = new Plan("test");
		LocationPoint p1= new LocationPoint(0,0);
		LocationPoint p2= new LocationPoint(100,0);
		LocationPoint p3= new LocationPoint(100,100);
		LocationPoint p4= new LocationPoint(0,100);
		
		LocationPoint p5= new LocationPoint(20,30);
		LocationPoint p6= new LocationPoint(80,50);
		LocationPoint p7= new LocationPoint(40,80);
		
		Polygon poly1 = new Polygon("boundary");
		poly1.points.add(p1);
		poly1.points.add(p2);
		poly1.points.add(p3);
		poly1.points.add(p4);
		map.boundary=poly1;
		
		Polygon poly2 = new Polygon("building1");
		poly2.points.add(p5);
		poly2.points.add(p6);
		poly2.points.add(p7);
		map.landmarks.add(poly2);
		
		new FileHandler(getContext()).writeFile(map, "abcd");
		
		Plan map1 = new FileHandler(getContext()).readFile("abcd");
		
		assertEquals(map.boundary.points, map1.boundary.points);
		
		assertEquals(map.landmarks, map1.landmarks);
	}

}
