package edu.bsu.petriNet.controller;

import edu.bsu.petriNet.model.AbstractPlace;

public class ReadOnlyPlace implements ReadOnlyGraphElement {
	private AbstractPlace place;
	
	protected ReadOnlyPlace(AbstractPlace p) {
		place = p;
	}
	
	public Integer getTokens() {
		return place.getTokens();
	}

	@Override
	public String toString(){
		return place.toString();
	}
	
	public Integer getId() {
		return place.getID();
	}
	
	public Integer getX() {
		return place.getX();
	}
	
	public Integer getY() {
		return place.getY();
	}
}
