package edu.boisestate.cs597.spring2017.petrinet;

import java.lang.IllegalArgumentException;

public class NodeChange {
	private GraphNode beforeChange;
	private GraphNode afterChange;
	
	public NodeChange(GraphNode before, GraphNode after){
		if(before.getClass() != after.getClass()){
			throw new IllegalArgumentException();
		}
		this.beforeChange = before;
		this.afterChange = after;
	}

	public GraphNode getBeforeChange() {
		return beforeChange;
	}

	public GraphNode getAfterChange() {
		return afterChange;
	}
	

}
