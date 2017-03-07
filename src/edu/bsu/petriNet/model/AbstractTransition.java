package edu.bsu.petriNet.model;

import edu.bsu.petriNet.model.AbstractGraphNode;

public class AbstractTransition extends AbstractGraphNode {
	private Boolean firable;

	public AbstractTransition(Integer id, Integer x, Integer y, String name) {
		super(id, x, y, name);
	}
	
	public AbstractTransition(Integer x, Integer y) {
		super(x, y);
	}
	
	public AbstractTransition(Integer x, Integer y, String name) {
		super(x, y, name);
	}

	public void setFirable(Boolean firable) {
		this.firable = firable;
	}

	public Boolean isFirable() {
		return firable;
	}
	
	@Override
	public String toString(){
		return "Transition \""+this.getName() + "\"("+this.getID()+")";
	}	

}
