package com.example.main.beginningpainttest;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Stroke{
private Path _path;
private Paint _paint;

public Stroke(Paint paint){
        _paint = paint;
}

public Path getPath(){
        return _path;
}

public Paint getPaint(){
        return _paint;
}

public void addPoint(Point pt){
        if(_path == null){ //If there isn't a path for this stroke, make a new one.
                _path = new Path();
                _path.moveTo(pt.x, pt.y); //sets the path at the x and y coordinates you pressed down on
        } else{
                _path.lineTo(pt.x,pt.y); //the path "moves" with you each time x or y coords change of your ACTION_DOWN
        }
}
}
