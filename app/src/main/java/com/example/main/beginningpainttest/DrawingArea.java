package com.example.main.beginningpainttest;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Main on 10/23/2017.
 */

public class DrawingArea extends View {
	protected static int paintColor = Color.BLACK; //variable which holds paint color
	protected static float strokeWidth = 20; //variable which holds brush size
	static List<Stroke> _allStrokes = new ArrayList<Stroke>(); //holds each instance of 'stroke' that's made
	private static SparseArray<Stroke> _activeStrokes; //use to retrieve the currently drawn strokes



	//Constructor for DrawingArea
	public DrawingArea(Context context, AttributeSet attrs){
		super(context, attrs);
		_allStrokes = new ArrayList<>(); //This array holds all strokes
		_activeStrokes = new SparseArray<>(); //This holds active strokes
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	//Override for onDraw, this is what code is executed when you draw
	@Override
	protected void onDraw(Canvas canvas) {
		if (_allStrokes != null) {
			for (Stroke stroke: _allStrokes) {
				if (stroke != null) {
					Path path = stroke.getPath();
					Paint painter = stroke.getPaint();
					if ((path != null) && (painter != null)) { //If there is a path and paint for each stroke iterated through, it will draw it on the canvas
						canvas.drawPath(path, painter);
					}
				}
			}
		}
	}


	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getActionMasked(); //returns action being performed, such as DOWN or MOVE
		final int pointerCount = event.getPointerCount();

		switch (action) {
			case MotionEvent.ACTION_DOWN: {
				pointDown((int)event.getX(), (int)event.getY(), event.getPointerId(0));
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				for (int pc = 0; pc < pointerCount; pc++) {
					pointMove((int) event.getX(pc), (int) event.getY(pc), event.getPointerId(pc));
				}
				break;
			}
			case MotionEvent.ACTION_POINTER_DOWN: {
				for (int pc = 0; pc < pointerCount; pc++) {
					pointDown((int)event.getX(pc), (int)event.getY(pc), event.getPointerId(pc));
				}
				break;
			}
			case MotionEvent.ACTION_UP: {
				break;
			}
			case MotionEvent.ACTION_POINTER_UP: {
				break;
			}
		}
		postInvalidate();
		return true;
	}

	private void pointDown(int x, int y, int id) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(paintColor);
		paint.setStrokeWidth(strokeWidth);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND); //all these are the variables we can edit to change brushes

		Point pt = new Point(x, y);
		Stroke stroke = new Stroke(paint);
		stroke.addPoint(pt);
		_activeStrokes.put(id, stroke);
		_allStrokes.add(stroke);
	}

	private void pointMove(int x, int y, int id) {
		//retrieve the stroke and add new point to its path
		Stroke stroke = _activeStrokes.get(id);
		if (stroke != null) {
			Point pt = new Point(x, y);
			stroke.addPoint(pt);
		}
	}


	//onClick functions for changing brush color
	protected static void paintChangeBlue(){
		paintColor = Color.BLUE;
	}
	protected static void paintChangeGreen() {paintColor = Color.GREEN; }
	protected static void paintChangeRed(){
		paintColor = Color.RED;
	}
	protected static void paintChangePurple() { paintColor = Color.rgb(158, 17, 158);}
	protected static void paintChangeWhite() {paintColor = Color.rgb(255,255,255);}
	protected static void paintChangeBlack() {paintColor = Color.BLACK;}
	protected static void paintChangeOrange() {paintColor = Color.rgb(255,165,0);}
	protected static void paintChangeBrown() {paintColor = Color.rgb(165,42,42);}
	protected static void paintChangePink() {paintColor = Color.rgb(255,62,150);}
	protected static void paintChangeYellow() {paintColor = Color.rgb(255,255,0);}
	protected static void paintChangeGrey() {paintColor = Color.rgb(102,102,102);}
	protected static void paintChangeDarkGreen() {paintColor = Color.rgb(0,100,0);}
	protected static void paintChangeErase() {paintColor = Color.rgb(255,255,255);}
	protected static void paintChangeCustom(int r, int g, int b) {paintColor = Color.rgb(r, g, b);}



	//clears array of strokes
	// TO-DO: won't update until next time you draw
	protected static void clearCanvas() {
		_activeStrokes.clear();
		_allStrokes.clear();
	}



}

