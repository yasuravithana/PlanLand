package com.gbpdma.logic;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.gbpdma.R;
import com.gbpdma.Welcome;
import com.gbpdma.location.GPS;
import com.gbpdma.R.id;
import com.gbpdma.R.layout;
import com.gbpdma.io.FileHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * CreateMap Class
 * 
 * This class represents the activity which handles the creation of a plan.
 * 
 * @author yasuravithana
 * 
 */

public class CreatePlan extends Activity implements Observer {

    /*
     * this class implements Observer, and gets notifications for changes in the location of the device and changes in the status of the GPS system.
     */

    GPS gps; // This is the instance of the GPS class which
	     // handles the location service.

    TextView txtInfo; // Placeholder for the TextView that currently
		      // displays the location information.
    EditText mapNameBox, landmarkNameBox; // variables to hold
					  // references to the text
					  // boxes where user inputs.
    StringBuilder sb; // This is used to build the
		      // string displayed in the
		      // textInfo.
    Plan plan; // Holds the reference to
	       // the currently working
	       // plan
    Polygon currentPolygon; // Holds the reference to
			    // the polygon that the user
    long timeOffset;// This will hold the difference between System time and Satellite time.
    int indexToStartTime;// This is used to keep the position in the string where time value starts to rewrite it.
    Timer timer;// Used to update the "time since this lock"

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.enter_plan_name);

	mapNameBox = (EditText) findViewById(R.id.editText1);

	// Creation of the location manager to access location and GPS status services
	gps = new GPS(this);

    }

    @Override
    protected void onResume() {
	gps.resume();// Resume GPS listening
	super.onResume();
    }

    @Override
    protected void onPause() {
	gps.pause();// Pauses GPS listening since it is power consuming
	super.onPause();
    }

    @Override
    protected void onStop() {
	super.onStop();
    }

    // This is the method which handles the event of the user clicking next after entering plan name
    public void nextAfterPlanNameClicked(View view) {
	plan = new Plan(mapNameBox.getText().toString());// creates a new plan
	setContentView(R.layout.add_boundary_points);

	txtInfo = (TextView) findViewById(R.id.textInfo);// get TextView to display the GPS data
	gps.addObserver(this);// Add as an observer of location changes
	timer = new Timer();
	// setting the timer task to execute each second to calculate and show the time since last GPS fix.
	timer.scheduleAtFixedRate(new TimerTask() {

	    @Override
	    public void run() {

		runOnUiThread(new Runnable() {

		    @Override
		    public void run() {
			if (sb != null) {
			    sb.delete(indexToStartTime, sb.length());// delete the currently displayed time.
			    sb.append(Long.toString((System.currentTimeMillis()
				    - gps.getTime() - timeOffset) / 1000)
				    + " s");// calculate the "time since" and add it to the string.
			    txtInfo.setText(sb.toString());// Display GPS data.
			}
		    }

		});
	    }

	},
	// Set how long before to start calling the TimerTask (in milliseconds)
		0,
		// Set the amount of time between each execution (in milliseconds)
		1000);
    }

    // This is the method which handles the event of the user clicking Add Boundary Point
    public void addBoundryPointClicked(View view) {
	plan.boundary.points.add(new LocationPoint(gps.getLongitude(), gps
		.getLatitude()));// add the current location to the list of points of boundary
	Toast toast = Toast.makeText(this, "Point added to the boundary.",
		Toast.LENGTH_SHORT);// user notification
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }

    // This is the method which handles the event of the user clicking Remove Boundary Point
    public void removeBoundryPointClicked(View view) {
	Toast toast;
	if (plan.boundary.points.size() > 0) {
	    plan.boundary.points.removeLast();// remove the last point from the list of points of boundary
	    toast = Toast.makeText(this,
		    "Point removed from the boundary.", Toast.LENGTH_SHORT);// user notification
	} else {
	    toast = Toast.makeText(this,
		    "No points to remove from the boundary.", Toast.LENGTH_SHORT);// user notification
	}
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }

    // This is the method which handles the event of the user clicking Add Landmarks after entering border points
    public void addLandmarksClicked(View view) {
	setContentView(R.layout.enter_landmark_name);
	landmarkNameBox = (EditText) findViewById(R.id.editTextLandmarkName);
    }

    // This is the method which handles the event of the user clicking next after entering landmark name
    public void nextAfterLandmarkNameClicked(View view) {
	plan.landmarks.add(currentPolygon = new Polygon(landmarkNameBox
		.getText().toString()));// creating a polygon, making it the current working polygon and adding it to the list of landmarks
	setContentView(R.layout.add_landmark_points);

	txtInfo = (TextView) findViewById(R.id.textInfoInLandmarks);// get TextView to display the GPS data

	timer = new Timer();
	// setting the timer task to execute each second to calculate and show the time since last GPS fix.
	timer.scheduleAtFixedRate(new TimerTask() {

	    @Override
	    public void run() {

		runOnUiThread(new Runnable() {

		    @Override
		    public void run() {
			if (sb != null) {
			    sb.delete(indexToStartTime, sb.length());// delete the currently displayed time.
			    sb.append(Long.toString((System.currentTimeMillis()
				    - gps.getTime() - timeOffset) / 1000)
				    + " s");// calculate the "time since" and add it to the string.
			    txtInfo.setText(sb.toString());// Display GPS data.
			}
		    }

		});
	    }

	},
	// Set how long before to start calling the TimerTask (in milliseconds)
		0,
		// Set the amount of time between each execution (in milliseconds)
		1000);
    }

    // This is the method which handles the event of the user clicking Add Landmark Point
    public void addLandmarkPointClicked(View view) {
	currentPolygon.points.add(new LocationPoint(gps.getLongitude(), gps
		.getLatitude()));// add current location point to the points list of the polygon
	Toast toast = Toast.makeText(this, "Point added to the landmark.",
		Toast.LENGTH_SHORT);// user notification
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }

    // This is the method which handles the event of the user clicking Remove Landmark Point
    public void removeLandmarkPointClicked(View view) {
	Toast toast;
	if (currentPolygon.points.size() > 0) {
	    currentPolygon.points.removeLast();// remove the last point from the list of points of current landmark
	    toast = Toast.makeText(this, "Point removed from the landmark.",
		    Toast.LENGTH_SHORT);// user notification
	} else {
	    toast = Toast.makeText(this, "No points to remove from the landmark.",
		    Toast.LENGTH_SHORT);// user notification
	}
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
    }

    // This is the method which handles the event of the user clicking New Landmark after done with one landmark. Simply loads the layout to enter landmark name.
    public void newLandmarkClicked(View view) {
	setContentView(R.layout.enter_landmark_name);
	landmarkNameBox = (EditText) findViewById(R.id.editTextLandmarkName);
    }

    // This is the method which handles the event of the user clicking finish plan
    public void finishPlanClicked(View view) {
	new FileHandler(this).writeFile(plan, plan.name);// creates a new file handler to write the plan into file.
	Toast toast = Toast.makeText(this, "Map saved succesfully.",
		Toast.LENGTH_SHORT);// user notification
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
	// Go back to the welcome screen
	finish();
    }

    @Override
    // this method is called when GPS object has any changes to notify of.
    public void update(Observable arg0, Object arg1) {
	timeOffset = System.currentTimeMillis() - gps.getTime();
	// txtInfo.setText(Double.toString(gps.getLongitude()));
	sb = new StringBuilder(512);

	/* display some of the data in the TextView */

	sb.append("Longitude: ");
	sb.append(gps.getLongitude());
	sb.append('\n');

	sb.append("Latitude: ");
	sb.append(gps.getLatitude());
	sb.append('\n');

	sb.append("Accuracy: ");
	sb.append(gps.getAccuracy() + " m");
	sb.append('\n');

	sb.append("Time since this fix: ");

	txtInfo.setText(sb.toString());
	indexToStartTime = sb.length();// this to stored to rewrite time.
    }
}
