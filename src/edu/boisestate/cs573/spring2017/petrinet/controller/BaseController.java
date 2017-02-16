package edu.boisestate.cs573.spring2017.petrinet.controller;

import java.util.ArrayList;

public class BaseController implements IController {
	private ArrayList<IStateListener> stateListener;

	public BaseController(){
		
	}

	@Override
	public Boolean newNet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addTransition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addPlace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addArc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean connectArc(AbstractArc a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean disconnectArc(AbstractArc a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(GraphNode n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setArcWeight(AbstractArc a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setPlaceTokenCount(AbstractPlace p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setName(GraphNode n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean registerStateListener(IStateListener l) {
		this.stateListener.add(l);
		return true;
	}

	@Override
	public Boolean setLocation(GraphNode n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean save(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean fire(AbstractTransition t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean simulate(int n_steps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean undo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
