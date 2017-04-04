package edu.bsu.petriNet.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A set of selected GElements.
 */
public class ElementSelection implements Iterable<Integer> {
	private Set<Integer> contents;
	private Point rectangleStart;
	private Map<Integer,GElement> candidateElements;
	
	public static final Stroke SELECTION_STROKE = new BasicStroke(1);
	
	public ElementSelection(Map<Integer,GElement> candidateElements) {
		contents = new TreeSet<Integer>();
		this.candidateElements = candidateElements;
	}
	
	public void clear() {
		contents.clear();
	}
	
	public boolean contains(GElement element) {
		return contents.contains(element.getAbstractElement().getID());
	}
	
	public boolean add(GElement element) {
		return contents.add(element.getAbstractElement().getID());
	}
	
	public boolean remove(GElement element) {
		return contents.remove(element.getAbstractElement().getID());
	}
	
	public void beginRectangleSelect(Point start) {
		System.out.println("started rect at "+start.toString());
		rectangleStart = start;
	}
	
	public void endRectangleSelect(Point end) {
		if (rectangleStart != null) {
			clear();
			
			System.out.println("ended rect at "+end.toString());

			int startX = Math.min(rectangleStart.x, end.x);
			int startY = Math.min(rectangleStart.y, end.y);
			int endX = Math.max(rectangleStart.x, end.x);
			int endY = Math.max(rectangleStart.y, end.y);
			
			for(Map.Entry<Integer, GElement> el : candidateElements.entrySet()){
				if (el.getValue().withinRectangle(startX, startY, endX, endY)){
					add(el.getValue());
				}
			}
			
			System.out.println("selected "+contents.size());
			
			rectangleStart = null;
		}
	}
	
	public void draw(Graphics2D g, Point mousePos) {
		if (rectangleStart != null) {
			int startx = Math.min(rectangleStart.x, mousePos.x);
			int starty = Math.min(rectangleStart.y, mousePos.y);
			int endx = Math.max(rectangleStart.x, mousePos.x);
			int endy = Math.max(rectangleStart.y, mousePos.y);
			g.setStroke(SELECTION_STROKE);
			g.setColor(Color.BLACK);
			g.drawRect(startx, starty, endx-startx, endy-starty);
		}
	}

	public boolean pendingRectangleSelect() {
		return rectangleStart != null;
	}

	@Override
	public Iterator<Integer> iterator() {
		return contents.iterator();
	}
}
