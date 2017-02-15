package edu.boisestate.cs573.spring2017.petrinet.controller;

import java.util.ArrayList;
import java.util.Iterator;

public class StateSet {
	private ArrayList<GraphNode> states;

	public StateSet(){
		this.states = new ArrayList<>();
	}
	
	public void add(GraphNode n){
		this.states.add(n);
	}
	
	public Iterator<GraphNode> iter(){
		return this.states.iterator();
	}
	
	
}
