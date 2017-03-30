package edu.bsu.petriNet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import edu.bsu.petriNet.editor.BasicGraphEditorPanel;
import edu.bsu.petriNet.helper.HistoryProvider;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractGraphNode;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.GraphNode;
import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;

public class BaseController implements IController {
	private ArrayList<IStateListener> stateListeners;
	private PetriNet petrinet;
	private Random random;
	private HistoryProvider m0History, simHistory;
	

	public BaseController(){
		this.stateListeners = new ArrayList<>();
		this.petrinet = new PetriNet();
		this.m0History = new HistoryProvider();
		this.simHistory = new HistoryProvider();
		this.random = new Random();
	}

	@Override
	public Boolean newNet() {
		this.petrinet = new PetriNet();
		this.m0History.reset();
		this.simHistory.reset();
		notifyStateListeners();
		return true;
	}

	@Override
	public Boolean addTransition(AbstractTransition t) {
		petrinet.createTransition(t.getName(),t.getX(),t.getY());
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean addPlace(AbstractPlace p) {
		petrinet.createPlace(p.getName(), p.getTokens(),p.getX(),p.getY());
		notifyStateListeners();
		return null;
	}

	@Override
	public Boolean addArc(AbstractArc a) {
		petrinet.createArc(a.getName(), a.getOrigin(), a.getTarget(), a.getWeight());
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
		petrinet.setName(n.getID(), n.getName());
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
		petrinet.setPosition(n.getID(), n.getX(), n.getY());
		notifyStateListeners();
		return null;
	}
	
	public Boolean translate(Integer id, Integer dx, Integer dy) {
		GraphNode node = petrinet.getGraphNodeById(id);
		petrinet.setPosition(id, node.getX()+dx, node.getY()+dy);
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
		Boolean r = petrinet.fire(t.getID());
		notifyStateListeners(true, false);
		return r;
	}

	@Override
	public Boolean simulate(int n_steps) {
		for(int i = 0; i < n_steps; i++){
			//Get available firable transitions.
			HashMap<Integer,AbstractTransition> firables = new HashMap<>();
			int k = 0;
			for(AbstractTransition t :this.petrinet.getAbstractTransitions()){
				if(t.isFirable()){
					firables.put(k++, t);
				}
			}
			//Fire one.
			if(firables.keySet().size() >0){
				int target = this.random.nextInt(firables.keySet().size());
				this.fire(firables.get(target));
			}
		}
		return true;
	}

	@Override
	public Boolean undo() {
		if(this.simHistory.isUndoPossible()){
			this.simHistory.undo();
			this.petrinet = this.simHistory.getCurretPetriNet();
			notifyStateListeners(true, true);
			return true;
		}else if(this.m0History.isUndoPossible()){
			this.m0History.undo();
			this.petrinet = this.m0History.getCurretPetriNet();
			notifyStateListeners(false, true);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Boolean undoSimulation() {
		this.petrinet = this.m0History.getCurretPetriNet();
		this.simHistory.reset();
		notifyStateListeners(false, true);
		return true;
	}
	
	private void notifyStateListeners(){
		notifyStateListeners(false, false);
	}
	
	private void notifyStateListeners(Boolean isSimulation, Boolean isUndoChange){
		// We have two HistoryProviders.  One for M0 changes, One for Simulation changes.
		// For every M0 change we can clear the Simulation change history
		if(!isUndoChange){
			if(isSimulation){
				this.simHistory.savePetriNetCheckPoint(this.petrinet);
			}else{
				this.m0History.savePetriNetCheckPoint(this.petrinet);
				this.simHistory.reset();
			}
		}
		for(IStateListener l : this.stateListeners){
			StateSet newstate = new StateSet();
			for(AbstractArc arc: this.petrinet.getAbstractArcs()){
				newstate.addArc(arc);
			}
			for(AbstractTransition trans: this.petrinet.getAbstractTransitions()){
				newstate.addTransition(trans);
			}
			for(AbstractPlace place: this.petrinet.getAbstractPlaces()){
				newstate.addPlace(place);
			}
			l.newState(newstate);
		}
	}

	
	
	
}
