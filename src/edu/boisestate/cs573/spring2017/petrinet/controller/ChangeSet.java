package edu.boisestate.cs573.spring2017.petrinet.controller;

import java.util.ArrayList;
import java.util.Iterator;

public class ChangeSet {
	private ArrayList<NodeChange> changes;
	
	public ChangeSet(){
		changes = new ArrayList<>();
	}
	
	public void add(NodeChange n){
		changes.add(n);
	}
	
	public Iterator<NodeChange> iter(){
		return this.changes.iterator();
	}
	
}
