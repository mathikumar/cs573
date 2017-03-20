package edu.bsu.petriNet.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;


import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.GraphElement;

public class GPlace implements GElement{
	
	AbstractPlace abstractPlace;
	private final Integer RADIUS = 25;
	
	public GPlace(AbstractPlace p){
		this.abstractPlace = p;
	}
	
	public void draw(Graphics2D g, Map<Integer,GElement> elements){
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(CanvasPanel.LINE_THICKNESS));
		g.drawOval(abstractPlace.getX()-RADIUS, abstractPlace.getY()-RADIUS, 2*RADIUS, 2*RADIUS);
		g.drawString(String.valueOf(abstractPlace.getTokens()), abstractPlace.getX(), abstractPlace.getY());
	}

	@Override
	public GraphElement getAbstractElement() {
		return abstractPlace;
	}

	@Override
	public Boolean containsPoint(Point p) {
		return p.distance(new Point(abstractPlace.getX(), abstractPlace.getY())) < RADIUS;
	}

	@Override
	public GPoint getExitPoint(Vector vector) {
		Vector v = new Vector(abstractPlace.getX(), abstractPlace.getY());
		return new GPoint(v.add(vector.unit().mul(RADIUS)));
	}

}
