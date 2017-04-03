package edu.bsu.petriNet.editor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.GraphElement;

public class CopyBuffer {
	Collection<GElement> elements;
	Point origin;
	
	public CopyBuffer(ElementSelection selection, Map<Integer,GElement> candidateElements) {
		elements = new ArrayList<GElement>();
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for (Integer id : selection) {
			GElement element = candidateElements.get(id);
			Point elementOrigin = element.getUpperLeftVisualCorner();
			minX = Math.min(minX, elementOrigin.x);
			minY = Math.min(minY, elementOrigin.y);
			elements.add(candidateElements.get(id));
		}
		origin = new Point(minX,minY);
	}
	
	public void paste(IController controller, Point newOrigin) {
		if (newOrigin == null) newOrigin = origin;
		int dx = newOrigin.x-origin.x;
		int dy = newOrigin.y-origin.y;
				
		// everything below this point sucks
		// Start by pasting all the places and transitions, completely
		// failing to use polymorphism properly in the process.
		// While doing this, a map of the old IDs to the new IDs is built so
		// that the arcs can be created.
		Map<Integer,Integer> newIds = new HashMap<Integer,Integer>();
		
		for (GElement element : elements) {
			if (element.isPlace()) {
				GraphElement newElement = element.getAbstractCopy(dx,dy);
				newIds.put(element.getAbstractElement().getID(),
						controller.addPlace((edu.bsu.petriNet.model.AbstractPlace)newElement));
			} else if (element.isTransition()) {
				GraphElement newElement = element.getAbstractCopy(dx,dy);
				newIds.put(element.getAbstractElement().getID(),
						controller.addTransition((edu.bsu.petriNet.model.AbstractTransition)newElement));
			}
		}
		// Only after an arc's place and transition have been added can the arc
		// itself actually be added, hence the double iteration.
		for (GElement element : elements) {
			if (element.isArc()) {
				AbstractArc arc = (AbstractArc)element.getAbstractElement();
				controller.addArc(new AbstractArc(arc.getWeight(),
						newIds.get(arc.getOrigin()),newIds.get(arc.getTarget())));
			}
		}
	}
}
