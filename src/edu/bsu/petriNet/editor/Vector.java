package edu.bsu.petriNet.editor;

public class Vector {
	double x,y;
	
	public Vector(GPoint p){
		this(p.x, p.y);
	}
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector(double x0, double xt, double y0, double yt){
		this.x = xt-x0;
		this.y = yt-y0;
	}
	
	public double norm(){
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector unit(){
		return new Vector(x/norm(), y/norm());
	}
	
	public Vector inv(){
		return new Vector(-x, -y);
	}
	
	public Vector orth(){
		return new Vector(-y, x);
	}
	
	public Vector mul(double f){
		return new Vector(f*x, f*y);
	}
	
	public Vector add(Vector v){
		return new Vector(this.x+v.x, this.y+v.y);
	}
	

}
