package com.gbpdma.logic;

public class MapProcessor
{
	static final int earthRadius =6371;
	static double centerLongitude=0, centerLatitude=0;
	public static void setCoordinates(Map map)
	{
		getCenter(map);
		setCoordinates(map.boundary);
		for (Polygon landmark : map.landmarks)
		{
			setCoordinates(landmark);
		}
	}
	
	public static void setCoordinates(Polygon polygon)
	{
		for (LocationPoint point : polygon.points)
		{
			setCoordinates(point);
		}
	}
	
	public static void setCoordinates(LocationPoint point)
	{
//		point.setX((float)(Math.toRadians(point.getLongitude())*earthRadius));
//		point.setY((float)(Math.toRadians(point.getLatitude())*earthRadius));
		point.setX((float)(Math.toRadians(point.getLongitude()-centerLongitude)*earthRadius)*1000);
		point.setY(-(float)(Math.toRadians(point.getLatitude()-centerLatitude)*earthRadius)*1000);
	}
	
	public static void getCenter(Map map)
	{
		double sumLongitudes=0,sumLatitudes=0;
		for (LocationPoint point : map.boundary.points)
		{
			sumLongitudes+=point.getLongitude();
			sumLatitudes+=point.getLatitude();
		}
		centerLongitude=sumLongitudes/map.boundary.points.size();
		centerLatitude=sumLatitudes/map.boundary.points.size();
	}
}
