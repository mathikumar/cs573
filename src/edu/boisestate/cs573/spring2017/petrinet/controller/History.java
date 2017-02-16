package edu.boisestate.cs573.spring2017.petrinet.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class History implements IStateListener {
	public Stack<StateSet> history;
	


	@Override
	public void newState(StateSet cs) {
		history.push(cs);	
	}
	
	
}
