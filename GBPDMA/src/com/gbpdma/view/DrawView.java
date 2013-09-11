package com.gbpdma.view;

import com.gbpdma.logic.LocationPoint;
import com.gbpdma.logic.Map;
import com.gbpdma.logic.MapProcessor;
import com.gbpdma.logic.Polygon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

public class DrawView extends View {
    Paint paint = new Paint();
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 10f;
    private float refx=0,refy=0,dx=0,dy=0;
    private float angle=0f;
//    private float currentX=-8884, currentY=-755;
    private float currentX=0, currentY=0;
    Boolean shouldScale=true;
    int screenHalfWidth,screenHalfHeight;
    Map map;
    LocationPoint previousPoint;
    

    public DrawView(Context context, Map map) {
        super(context);  
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screenHalfWidth = display.getWidth()/2;
        screenHalfHeight = display.getHeight()/2;
        currentX=screenHalfWidth;
        currentY=screenHalfHeight;
        MapProcessor.setCoordinates(map);
        this.map=map;
        
    }

    @Override
    public void onDraw(Canvas canvas) {
    	
    	canvas.rotate(angle,100,100);
        canvas.translate(currentX+=(dx)/mScaleFactor, currentY+=(dy)/mScaleFactor);
    	canvas.scale(mScaleFactor, mScaleFactor,screenHalfWidth-currentX,screenHalfHeight-currentY);

    	
    	//drawing boundary
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2/mScaleFactor);
        
        previousPoint=map.boundary.points.getLast();
        for(LocationPoint point : map.boundary.points)
        {
        	canvas.drawLine(previousPoint.getX(), previousPoint.getY(), point.getX(), point.getY(), paint);
        	previousPoint = point;
        }
        
        //drawing landmarks
        paint.setColor(Color.BLACK);
        for(Polygon landmark : map.landmarks)
        {
        	previousPoint=landmark.points.getLast();
        	for(LocationPoint point : landmark.points)
        	{
        		canvas.drawLine(previousPoint.getX(), previousPoint.getY(), point.getX(), point.getY(), paint);
        		previousPoint = point;
        	}
        }

    }
    
    private class ScaleListener 
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
@Override
public boolean onScale(ScaleGestureDetector detector) {
    mScaleFactor *= detector.getScaleFactor();
    shouldScale=true;

    // Don't let the object get too small or too large.
    //mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

    invalidate();
    return true;
}
}
    
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        
        if(ev.getAction()==MotionEvent.ACTION_DOWN)
        {
        	refx=ev.getRawX();
        	refy=ev.getRawY();
        }
        if(ev.getAction()==MotionEvent.ACTION_MOVE)
        {
        	float angleInRad=(float) (angle*Math.PI/180);
//        	dy=(float) ((ev.getRawX()-refx)*Math.sin(angleInRad)+(ev.getRawY()-refy)*Math.cos(angleInRad));
//        	dx=(float) ((ev.getRawY()-refy)*Math.cos(angleInRad)-(ev.getRawX()-refx)*Math.sin(angleInRad));


//        	double a = Math.atan((ev.getRawY()-refy)/(ev.getRawX()-refx))-angleInRad;
//        	double l =Math.sqrt((ev.getRawY()-refy)*(ev.getRawY()-refy)+(ev.getRawX()-refx)*(ev.getRawX()-refx));
//        	if(!Double.isNaN(a))
//        	{
//	        	dx=(float) (l*Math.cos(a));
//	        	dy=(float) (l*Math.sin(a));
//        	}
        	
        	dx=(ev.getRawX()-refx);
        	dy=(ev.getRawY()-refy);
        	refx=ev.getRawX();
        	refy=ev.getRawY();
        	invalidate();
        }
        return true;
    }
}