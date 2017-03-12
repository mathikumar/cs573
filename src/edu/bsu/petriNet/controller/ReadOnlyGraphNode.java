package edu.bsu.petriNet.controller;

import edu.bsu.petriNet.model.GraphNode;

public class ReadOnlyGraphNode implements ReadOnlyGraphElement {
	private GraphNode node;
	
	protected ReadOnlyGraphNode(GraphNode n) {
		node = n;
	}

	@Override
	public String toString(){
		return node.toString();
	}
	
	public Integer getId() {
		return node.getID();
	}
	
	public Integer getX() {
		return node.getX();
	}
	
	public Integer getY() {
		return node.getY();
	}
}