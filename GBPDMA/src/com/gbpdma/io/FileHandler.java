package com.gbpdma.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.gbpdma.logic.LocationPoint;
import com.gbpdma.logic.Plan;
import com.gbpdma.logic.Polygon;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

/**
 * @author yasuravithana
 * 
 */
public class FileHandler {// This class take care of storing a Plan object in to the memory as an XML file and loading a Plan object from a stored XML file.

    Context context;// this will hold the current context

    public FileHandler(Context activity)// constructor
    {
	context = activity;
    }

    // takes a Plan object and write an XML file with the specified name.
    public void writeFile(Plan plan, String name) {
	FileOutputStream fileos = null;// stream used to write the file
	try {
	    fileos = context.openFileOutput(name + ".xml",
		    android.content.Context.MODE_PRIVATE);// opens the XML file
	} catch (FileNotFoundException e) {

	}
	// create a XmlSerializer in order to write xml data
	XmlSerializer serializer = Xml.newSerializer();
	try {
	    // set the FileOutputStream as output for the serializer, using UTF-8 encoding
	    serializer.setOutput(fileos, "UTF-8");
	    // Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
	    serializer.startDocument(null, Boolean.valueOf(true));
	    // set indentation option
	    serializer.setFeature(
		    "http://xmlpull.org/v1/doc/features.html#indent-output",
		    true);
	    // start a tag called "map"
	    serializer.startTag(null, "map");
	    // add name attribute
	    serializer.attribute(null, "name", plan.name);

	    // start a tag called "boundary"
	    serializer.startTag(null, "boundary");
	    // add name attribute
	    serializer.attribute(null, "name", plan.boundary.getName());

	    // Adding points to the map boundary
	    for (LocationPoint point : plan.boundary.points)// for each point in boundary
	    {
		// start a tag called "point"
		serializer.startTag(null, "point");
		// add longitude attribute
		serializer.attribute(null, "longitude", Double.toString(point
			.getLongitude()));
		// add latitude attribute
		serializer.attribute(null, "latitude", Double.toString(point
			.getLatitude()));
		// end tag "point"
		serializer.endTag(null, "point");
	    }

	    // end tag "boundary"
	    serializer.endTag(null, "boundary");

	    // adding landmarks
	    for (Polygon landmark : plan.landmarks)// for each landmark
	    {
		// start a tag called "landmark"
		serializer.startTag(null, "landmark");
		// add name attribute
		serializer.attribute(null, "name", landmark.getName());

		for (LocationPoint point : landmark.points)// for each point of a landmark
		{
		    // start a tag called "point"
		    serializer.startTag(null, "point");
		    // add longitude attribute
		    serializer.attribute(null, "longitude", Double
			    .toString(point.getLongitude()));
		    // add latitude attribute
		    serializer.attribute(null, "latitude", Double
			    .toString(point.getLatitude()));
		    // end tag "point"
		    serializer.endTag(null, "point");
		}

		// end tag "landmark"
		serializer.endTag(null, "landmark");
	    }

	    // end tag "map"
	    serializer.endTag(null, "map");

	    serializer.endDocument();// ending the XML doc
	    serializer.flush();// write xml data into the FileOutputStream
	    fileos.close();// finally close the file stream
	} catch (Exception e) {

	}

    }

    // This method reads the specified XML file, parse it, create a Plan object and returns it.
    public Plan readFile(String name) throws XmlPullParserException {

	Plan plan = null;// reference to the Plan object that is being created
	FileInputStream fileis = null;// stream used to read file
	try {
	    fileis = context.openFileInput(name + ".xml");// open XML file
	} catch (FileNotFoundException e) {

	}
	// variables to hold object references used for parsing the XML
	XmlPullParserFactory factory;
	XmlPullParser xpp = null;

	try {
	    // instantiate XML parser
	    factory = XmlPullParserFactory.newInstance();
	    factory.setNamespaceAware(true);
	    xpp = factory.newPullParser();
	    xpp.setInput(fileis, "UTF-8");

	} catch (XmlPullParserException e) {

	}

	Polygon currentPolygon = null;// this is the polygon that is currently being created
	int eventType = xpp.getEventType();// parser event type

	while (eventType != XmlPullParser.END_DOCUMENT)// process tag while not reaching the end of document
	{
	    switch (eventType) {
	    // at start of document: START_DOCUMENT
	    case XmlPullParser.START_DOCUMENT: {
		break;
	    }

	    // at start of a tag: START_TAG
	    case XmlPullParser.START_TAG: {
		// get tag name
		String tagName = xpp.getName();

		if (tagName.equalsIgnoreCase("map"))// if "map", create a new plan using the name attribute
		{
		    plan = new Plan(xpp.getAttributeValue(null, "name"));
		} else if (tagName.equalsIgnoreCase("boundary"))// if "boundary", create a new polygon add it as the plan's boundary and make it the current polygon
		{
		    currentPolygon = plan.boundary = new Polygon(xpp
			    .getAttributeValue(null, "name"));
		} else if (tagName.equalsIgnoreCase("landmark"))// if "landmark", create a new polygon, make it the current polygon and add it to the plan's lanmarks list
		{
		    currentPolygon = new Polygon(xpp.getAttributeValue(null,
			    "name"));
		    plan.landmarks.add(currentPolygon);
		} else if (tagName.equalsIgnoreCase("point"))// if "point", create a new point with longitude and latitude attributes and add it to the current polygon's points list
		{
		    currentPolygon.points.add(new LocationPoint(Double
			    .parseDouble(xpp.getAttributeValue(null,
				    "longitude")), Double.parseDouble(xpp
			    .getAttributeValue(null, "latitude"))));
		}
		break;
	    }
	    }
	    // jump to next event
	    try {
		eventType = xpp.next();
	    } catch (XmlPullParserException e) {

	    } catch (IOException e) {

	    }
	}
	return plan;// returns the created Plan object
    }

}
