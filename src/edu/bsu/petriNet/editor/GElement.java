package edu.bsu.petriNet.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import javax.swing.JFrame;

import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.model.GraphElement;


public interface GElement {
	
	public void draw(Graphics2D g, Map<Integer,GElement> elements, ElementSelection selection);
	public GraphElement getAbstractElement();
	public Boolean containsPoint(Point p);
	public Boolean withinRectangle(int startX, int startY, int endX, int endY);
	public GPoint getExitPoint(Vector vector);
	public void editDialog(JFrame frame, IController controller);

}
