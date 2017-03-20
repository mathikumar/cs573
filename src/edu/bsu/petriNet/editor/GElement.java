package edu.bsu.petriNet.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import edu.bsu.petriNet.model.GraphElement;


public interface GElement {
	
	public void draw(Graphics2D g, Map<Integer,GElement> elements);
	public GraphElement getAbstractElement();
	public Boolean containsPoint(Point p);
	public GPoint getExitPoint(Vector vector);

}
