package com.gbpdma;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.gbpdma.R;
import com.gbpdma.location.GPS;
import com.gbpdma.logic.LocationPoint;
import com.gbpdma.logic.Map;
import com.gbpdma.logic.Polygon;
import com.gbpdma.io.FileHandler;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateMap extends Activity implements Observer{

	/* this class implements LocationListener, which listens for both
	 * changes in the location of the device and changes in the status
	 * of the GPS system.
	 * */
	
	static final String tag = "Main"; // for Log
	
	GPS gps;
	
	TextView txtInfo;
	EditText mapNameBox, landmarkNameBox;
	StringBuilder sb;
	Map map;
	Polygon currentPolygon;
	int noOfFixes = 0;
	long timeOffset;
	int indexToStartTime;
	
	Timer timer;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_map_name);
		
		
		
		mapNameBox = (EditText) findViewById(R.id.editText1);
		
		/* the location manager is the most vital part it allows access 
		 * to location and GPS status services */
		gps = new GPS((LocationManager)getSystemService(LOCATION_SERVICE),this);
		
	}
	
	@Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 * 
		 * add location listener and request updates every 1000ms or 1m
		 */
		gps.resume();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		gps.pause();
		super.onPause();
	}

	
	@Override
	protected void onStop() {
		/* may as well just finish since saving the state is not important for this toy app */
		//finish();
		super.onStop();
	}
	
	public void nextAfterMapNameClicked(View view) {
		
		map = new Map(mapNameBox.getText().toString());
		setContentView(R.layout.add_boundary_points);/* get TextView to display the GPS data */
		txtInfo = (TextView) findViewById(R.id.textInfo);
		gps.addObserver(this);
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        
		        runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if(sb!=null)
						{
						sb.delete(indexToStartTime, sb.length());
						sb.append(Long.toString((System.currentTimeMillis()-gps.getTime()-timeOffset)/1000)+" s");
						txtInfo.setText(sb.toString());
						}
					}
					
				});
		    }
		         
		},
		//Set how long before to start calling the TimerTask (in milliseconds)
		0,
		//Set the amount of time between each execution (in milliseconds)
		1000);
	}
	
	public void addBoundryPointClicked(View view) {
		map.boundary.points.add(new LocationPoint(gps.getLongitude(), gps.getLatitude()));
	}
	
	public void addLandmarksClicked(View view) {
		setContentView(R.layout.enter_landmark_name);
		landmarkNameBox = (EditText) findViewById(R.id.editTextLandmarkName);
	}
	
	public void nextAfterLandmarkNameClicked(View view) {
		map.landmarks.add(currentPolygon=new Polygon(landmarkNameBox.getText().toString()));
		setContentView(R.layout.add_landmark_points);/* get TextView to display the GPS data */
		txtInfo = (TextView) findViewById(R.id.textInfoInLandmarks);
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        
		        runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if(sb!=null)
						{
						sb.delete(indexToStartTime, sb.length());
						sb.append(Long.toString((System.currentTimeMillis()-gps.getTime()-timeOffset)/1000)+" s");
						txtInfo.setText(sb.toString());
						}
					}
					
				});
		    }
		         
		},
		//Set how long before to start calling the TimerTask (in milliseconds)
		0,
		//Set the amount of time between each execution (in milliseconds)
		1000);
	}
	
	public void addLandmarkPointClicked(View view) {
		currentPolygon.points.add(new LocationPoint(gps.getLongitude(), gps.getLatitude()));
	}
	
	public void newLandmarkClicked(View view) {
		setContentView(R.layout.enter_landmark_name);
		landmarkNameBox = (EditText) findViewById(R.id.editTextLandmarkName);
	}
	public void finishMapClicked(View view) {
		new FileHandler(this).writeFile(map, map.name);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		timeOffset = System.currentTimeMillis()-gps.getTime();
		//txtInfo.setText(Double.toString(gps.getLongitude()));
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
		indexToStartTime= sb.length();
	}
}
