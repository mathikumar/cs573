package edu.bsu.petriNet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.bsu.petriNet.helper.HistoryProvider;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.Arc;
import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.Place;
import edu.bsu.petriNet.model.Transition;
import edu.bsu.petriNet.model.AbstractGraphNode;

public class BaseController implements IController {
	private ArrayList<IStateListener> stateListeners;
	private PetriNet petrinet;
	private Random random;
	

	public BaseController(){
		this.stateListeners = new ArrayList<>();
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
		petrinet.addPlace(p.getName(), p.getTokens());
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
	public Boolean delete(Integer ID) {
		petrinet.delete(ID);
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean setArcWeight(AbstractArc a) {
		Boolean r = petrinet.setArcWeight(a.getID(), a.getWeight());
		notifyStateListeners();
		return r;
	}

	@Override
	public Boolean setPlaceTokenCount(AbstractPlace p) {
		Boolean r = petrinet.setPlaceTokenNumber(p.getID(), p.getTokens());
		notifyStateListeners();
		return r;
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
		petrinet.setPosition(id, x, y);
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean save(String filename) {
		return XmlInputOutput.printModel(petrinet, filename);
	}

	@Override
	public Boolean load(String filename) {
		petrinet = XmlInputOutput.readModel(filename);
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean fire(AbstractTransition t) {
		Boolean r = petrinet.fire(t.getID())
		notifyStateListeners();
		return r;
	}

	@Override
	public Boolean simulate(int n_steps) {
		for(int i = 0; i < n_steps; i++){
			//Get available firable transitions.
			HashMap<Integer,AbstractTransition> firables = new HashMap<>();
			int k = 0;
			for(Transition t :this.petrinet.getAbstractTransitions()){
				if(t.isFirable()){
					firables.put(k++, t);
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
			for(Arc arc: this.petrinet.getAbstractArcs()){
				newstate.addArc(arc);
			}
			for(Transition trans: this.petrinet.getAbstractTransitions()){
				newstate.addTransition(trans);
			}
			for(Place place: this.petrinet.getAbstractPlaces()){
				newstate.addPlace(place);
			}
			l.newState(newstate);
		}
	}


}
