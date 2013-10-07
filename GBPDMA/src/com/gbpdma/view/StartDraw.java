package com.gbpdma.view;

import org.xmlpull.v1.XmlPullParserException;

import com.gbpdma.R;
import com.gbpdma.io.FileHandler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * @author yasuravithana
 *
 */
public class StartDraw extends Activity {
//this class is related to the activity which draws the plan
    
    DrawView drawView;//this is that view that draws the plan

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Bundle b = getIntent().getExtras();//get the additional parameters passed at the creation of the intent

	try {
	    drawView = new DrawView(this, new FileHandler(this).readFile(b
		    .getString("file")));//instantiate the view that draws the plan
	} catch (XmlPullParserException e) {
	    e.printStackTrace();
	    return;
	}
	drawView.setBackgroundColor(Color.LTGRAY);//set background color of the view
	setContentView(drawView);//make it the current view

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	
	super.onCreateContextMenu(menu, v, menuInfo);
    }    

    @Override
    protected void onPause() {
	drawView.pauseCompass();
	drawView.gps.pause();
	super.onPause();
    }

    @Override
    protected void onResume() {
	if(drawView.isTracking)
	{
	    drawView.resumeCompass();
	    drawView.gps.resume();
	}
	super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.view_map, menu);
	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	switch(item.getItemId())
	{
	case R.id.mode:
	    if(drawView.isTracking)
	    {
		drawView.isTracking=false;
		Toast.makeText(this, "Tracking mode disabled.", Toast.LENGTH_LONG).show();
		drawView.pauseCompass();
		drawView.gps.pause();
	    }
	    else
	    {
		drawView.isTracking=true;
		Toast.makeText(this, "Tracking mode enabled.", Toast.LENGTH_LONG).show();
		drawView.resumeCompass();
		drawView.gps.resume();
	    }
	    break;
	}
	return super.onOptionsItemSelected(item);
    }
    
}