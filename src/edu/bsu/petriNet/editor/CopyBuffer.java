package edu.bsu.petriNet.editor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import edu.bsu.petriNet.controller.IController;

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
		for (GElement element : elements) {
			if (element.isPlace()) {
				controller.addPlace((edu.bsu.petriNet.model.AbstractPlace)element.getAbstractCopy(dx,dy));
			} else if (element.isTransition()) {
				controller.addTransition((edu.bsu.petriNet.model.AbstractTransition)element.getAbstractCopy(dx,dy));
			}
		}
	}
}
