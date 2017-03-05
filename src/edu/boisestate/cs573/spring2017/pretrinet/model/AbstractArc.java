package edu.boisestate.cs573.spring2017.pretrinet.model;

import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractGraphNode;

public class AbstractArc{
	private Integer weight;
	private Integer origin;
	private Integer target;

	
	public AbstractArc(Integer weight, Integer originID, Integer targetID) {
		this.weight = weight;
		this.origin = origin;
		this.target = target;
	}
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getOrigin() {
		return origin;
	}

	public Integer getTarget() {
		return target;
	}

	
	
}
