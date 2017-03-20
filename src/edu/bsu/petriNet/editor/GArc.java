package edu.bsu.petriNet.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractGraphNode;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.GraphElement;

public class GArc implements GElement {

	AbstractArc abstractArc;
	Integer ARROW_SIZE=16;
	
	public GArc(AbstractArc p){
		this.abstractArc = p;
	}
	
	public void draw(Graphics2D g, Map<Integer,GElement> elements){
		AbstractGraphNode origin = (AbstractGraphNode)elements.get(abstractArc.getOrigin()).getAbstractElement();
		AbstractGraphNode target = (AbstractGraphNode)elements.get(abstractArc.getTarget()).getAbstractElement();
		Vector vec = new Vector( origin.getX(), target.getX(), origin.getY(), target.getY());
		GPoint originExit = elements.get(abstractArc.getOrigin()).getExitPoint(vec);
		GPoint targetExit = elements.get(abstractArc.getTarget()).getExitPoint(vec.inv());	
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(CanvasPanel.LINE_THICKNESS));
		g.drawLine(originExit.x, originExit.y, targetExit.x, targetExit.y);
		
		Vector arrow_unit = vec.unit().mul(ARROW_SIZE);
		GPoint a = new GPoint(new Vector(targetExit).add(arrow_unit.inv()).add(arrow_unit.orth()));
		GPoint b = new GPoint(new Vector(targetExit).add(arrow_unit.inv()).add(arrow_unit.orth().inv()));
		g.fillPolygon(new int[]{targetExit.x,a.x,b.x}, new int[]{targetExit.y,a.y,b.y}, 3);
		
		GPoint textLoc = originExit.add(vec.mul(0.5));
		g.drawString(String.valueOf(abstractArc.getWeight()), textLoc.x, textLoc.y);
	}

	@Override
	public GraphElement getAbstractElement() {
		return abstractArc;
	}

	@Override
	public Boolean containsPoint(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GPoint getExitPoint(Vector vector) {
		return null;
	}
	
	

}
