package edu.boisestate.cs573.spring2017.petrinet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.boisestate.cs573.spring2017.helpers.HistoryProvider;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractArc;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractPlace;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractTransition;
import edu.boisestate.cs573.spring2017.pretrinet.model.Arc;
import edu.boisestate.cs573.spring2017.pretrinet.model.PetriNet;
import edu.boisestate.cs573.spring2017.pretrinet.model.Place;
import edu.boisestate.cs573.spring2017.pretrinet.model.Transition;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractGraphNode;

public class BaseController implements IController {
	private ArrayList<IStateListener> stateListeners;
	private PetriNet petrinet;
	private Random random;
	

	public BaseController(){
		this.petrinet = new PetriNet();
		HistoryProvider.init();
		this.random = new Random();
	}

	@Override
	public Boolean newNet() {
		this.petrinet = new PetriNet();
		HistoryProvider.reset();
		return true;
	}

	@Override
	public Boolean addTransition(AbstractTransition t) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean addPlace(AbstractPlace p) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean addArc(AbstractArc a) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean delete(AbstractGraphNode n) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean setArcWeight(AbstractArc a) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean setPlaceTokenCount(AbstractPlace p) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean setName(AbstractGraphNode n) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean registerStateListener(IStateListener l) {
		this.stateListeners.add(l);
		return true;
	}

	@Override
	public Boolean setLocation(AbstractGraphNode n) {
		// TODO Auto-generated method stub
		notifyStateListeners();
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
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean fire(AbstractTransition t) {
		// TODO Auto-generated method stub
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean simulate(int n_steps) {
		for(int i = 0; i < n_steps; i++){
			//Get available firable transitions.
			HashMap<Integer,AbstractTransition> firables = new HashMap<>();
			int k = 0;
			for(Transition t :this.petrinet.getTransitions()){
				if(t.isFirable()){
					firables.put(k++, t.getAbstract());
				}
			}
			//Fire one.
			int target = this.random.nextInt(firables.keySet().size());
			this.fire(firables.get(target));
			notifyStateListeners();
		}
		return true;
	}

	@Override
	public Boolean undo() {
		if(HistoryProvider.isUndoPossible()){
			HistoryProvider.undo();
			this.petrinet = HistoryProvider.getCurretPetriNet();
			notifyStateListeners();
			return true;
		}else{
			return false;
		}
	}
	
	private void notifyStateListeners(){
		HistoryProvider.savePetriNetCheckPoint(this.petrinet);
		for(IStateListener l : this.stateListeners){
			StateSet newstate = new StateSet();
			for(Arc arc: this.petrinet.getArcs()){
				newstate.addArc(arc.getAbstract());
			}
			for(Transition trans: this.petrinet.getTransitions()){
				newstate.addTransition(trans.getAbstract());
			}
			for(Place place: this.petrinet.getPlaces()){
				newstate.addPlace(place.getAbstract());
			}
			l.newState(newstate);
		}
	}


}
