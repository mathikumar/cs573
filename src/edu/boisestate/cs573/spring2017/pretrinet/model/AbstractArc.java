package edu.boisestate.cs573.spring2017.pretrinet.model;

import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractGraphNode;

public class AbstractArc extends GraphElement{
	private Integer weight;
	private Integer origin;
	private Integer target;

	public AbstractArc(Integer id, Integer weight, Integer originID, Integer targetID, String name) {
		super(id,name);
		this.weight = weight;
		this.origin = originID;
		this.target = targetID;
	}

	public AbstractArc(Integer id, Integer weight, Integer originID, Integer targetID) {
		super(id);
		this.weight = weight;
		this.origin = originID;
		this.target = targetID;
	}
	
	public AbstractArc(Integer weight, Integer originID, Integer targetID, String name) {
		super(name);
		this.weight = weight;
		this.origin = originID;
		this.target = targetID;
	}
	
	public AbstractArc(Integer weight, Integer originID, Integer targetID) {
		super();
		this.weight = weight;
		this.origin = originID;
		this.target = targetID;
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

	@Override
	public String toString(){
		return "Arc \""+this.getName() + "\"("+this.getID()+")";
	}	
	
}
