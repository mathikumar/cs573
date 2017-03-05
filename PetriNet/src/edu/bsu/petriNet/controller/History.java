package edu.bsu.petriNet.controller;

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
