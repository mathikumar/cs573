package edu.bsu.petriNet.util;

import java.awt.Point;

import edu.bsu.petriNet.editor.GPoint;
import edu.bsu.petriNet.model.AbstractGraphNode;

public class GeometryUtil {
	public static GPoint getMidPoint(AbstractGraphNode a,AbstractGraphNode b){
		return new GPoint((a.getX()+b.getX())/2,(a.getY()+b.getY())/2);
	}
	
	public static float getSlope(Point p1, Point p2){
		  return (float) (p2.y - p1.y) / (p2.x - p1.x);
	}
}
