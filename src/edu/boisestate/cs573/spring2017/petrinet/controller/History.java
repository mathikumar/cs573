package edu.boisestate.cs573.spring2017.petrinet.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class History implements IChangeListener {
	public Stack<ChangeSet> history;
	
	public History(){
		this.history = new Stack<>();
	}
	
	public List<NetOp> getUnwindOps(){
		return null;
	}
	
	public Boolean commitUndo(){
		this.history.pop();
		return true;
	}

	@Override
	public void stateChange(ChangeSet cs) {
		history.push(cs);	
	}
	
	
}
