package edu.bsu.petriNet.controller;

import edu.bsu.petriNet.model.AbstractArc;

/**
 * A container for an AbstractArc that allows inspection, but not modification.
 * 
 * @author andrew
 *
 */
public class ReadOnlyArc implements ReadOnlyGraphElement {
	private AbstractArc arc;
	protected ReadOnlyArc(AbstractArc a) {
		arc = a;
	}
	public Integer getWeight() {
		return arc.getWeight();
	}

	public Integer getOrigin() {
		return arc.getOrigin();
	}

	public Integer getTarget() {
		return arc.getTarget();
	}

	@Override
	public String toString(){
		return arc.toString();
	}
	
	public Integer getId() {
		return arc.getID();
	}
	
	public String getName() {
		return arc.getName();
	}
}