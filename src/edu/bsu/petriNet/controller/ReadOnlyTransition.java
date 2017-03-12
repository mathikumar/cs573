package edu.bsu.petriNet.controller;

import edu.bsu.petriNet.model.AbstractTransition;

public class ReadOnlyTransition implements ReadOnlyGraphElement {
	private AbstractTransition transition;
	
	protected ReadOnlyTransition(AbstractTransition t) {
		transition = t;
	}

	@Override
	public String toString(){
		return transition.toString();
	}
	
	public Integer getId() {
		return transition.getID();
	}
	
	public Integer getX() {
		return transition.getX();
	}
	
	public Integer getY() {
		return transition.getY();
	}
	
	public String getName() {
		return transition.getName();
	}
}
