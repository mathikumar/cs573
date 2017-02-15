package edu.boisestate.cs573.spring2017.petrinet.controller;

public class BaseController implements IController {
	private IChangeListener changeListener;
	private IStateListener stateListener;

	public BaseController(IChangeListener clistener, IStateListener slistener){
		this.changeListener = clistener;
		this.stateListener = slistener;
	}
	
	@Override
	public Boolean newNet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addTransition(AbstractTransition t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addPlace(AbstractPlace p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addArc(AbstractArc a) {
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
	public Boolean deletePlace(AbstractPlace p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteTransition(AbstractTransition t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteArc(AbstractArc a) {
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
	public Boolean registerChangeListener(IChangeListener l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setLocation(GraphNode n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean save() {
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
