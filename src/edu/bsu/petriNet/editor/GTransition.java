package edu.bsu.petriNet.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.GraphElement;

public class GTransition implements GElement {

	private AbstractTransition abstractTransition;
	private int W = 25;
	private int H = 50;
	
	public GTransition(AbstractTransition p){
		this.abstractTransition = p;
	}
	
	public void draw(Graphics2D g, Map<Integer,GElement> elements){
		if(this.abstractTransition.isFirable()){
			g.setColor(Color.RED);
			g.fillRect(abstractTransition.getX()-W/2, abstractTransition.getY()-H/2, W, H);
		}
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(CanvasPanel.LINE_THICKNESS));
		g.drawRect(abstractTransition.getX()-W/2, abstractTransition.getY()-H/2, W, H);
	}

	@Override
	public GraphElement getAbstractElement() {
		return abstractTransition;
	}

	@Override
	public Boolean containsPoint(Point p) {
		return p.distance(new Point(abstractTransition.getX(), abstractTransition.getY())) < 10;
	}

	@Override
	public GPoint getExitPoint(Vector vector) {
		Vector v = new Vector(abstractTransition.getX(), abstractTransition.getY());
		return new GPoint(v.add(vector.unit().mul(W)));	
	}
	
}
