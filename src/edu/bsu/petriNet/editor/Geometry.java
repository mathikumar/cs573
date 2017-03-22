package edu.bsu.petriNet.editor;

import java.awt.Point;

public class Geometry {
	
	public static GPoint intersection(Vector a, Vector da, Vector c, Vector dc){
		Vector b = a.add(da);
		Vector d = c.add(dc);
		Double denom = (a.x-b.x)*(c.y-d.y) - (a.y-b.y)*(c.x-d.x);
		Double rx = ((a.x*b.y-a.y*b.x)*(c.x-d.x) - (a.x-b.x)*(c.x*d.y-c.y*d.x))/
					(denom);
		
		Double ry = ((a.x*b.y-a.y*b.x)*(c.y-d.y) - (a.y-b.y)*(c.x*d.y-c.y*d.x))/
					(denom);
		
		return new GPoint(rx,ry);
	}
	
	public static Double distance(Point pt, Vector a, Vector b){
		return Math.abs((b.y-a.y)*pt.x - (b.x-a.x)*pt.y + b.x*a.y - b.y*a.x)/(b.add(a.inv()).norm());
	}
	
	public static Boolean withinRect(Point pt, Point a, Point b){
		return pt.x > Math.min(a.x,b.x) &&
				pt.x < Math.max(a.x, b.x)&&
				pt.y > Math.min(a.y, b.y)&&
				pt.y < Math.max(a.y, b.y);
	}
	
	

}
