package edu.bsu.petriNet.editor;

import java.awt.Point;

public class GPoint extends Point {
	//int x,y;
	
	public GPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public GPoint(double x, double y){
		this.x = (int)Math.floor(x);
		this.y = (int)Math.floor(y);
	}
	
	public GPoint(Point p){
		this(p.x, p.y);
	}
	
	public GPoint(Vector v){
		this(v.x, v.y);
	}
	
	public GPoint add(GPoint p){
		return this.add(new Vector(p));
	}
	
	public GPoint add(Vector v){
		return new GPoint(this.x+v.x, this.y+v.y);
	}
	
	@Override
	public String toString(){
		return "GPoint("+x+","+y+")";
	}

}
