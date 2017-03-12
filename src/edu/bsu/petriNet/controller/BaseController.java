package edu.bsu.petriNet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import edu.bsu.petriNet.editor.BasicGraphEditor;
import edu.bsu.petriNet.helper.HistoryProvider;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractGraphNode;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.GraphNode;
import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;
import edu.bsu.petriNet.util.PetriNetUtil;

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
		petrinet.setPosition(n.getID(), n.getX(), n.getY());
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
		notifyStateListeners();
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
	
	public Boolean relocateNode(int id, int newX, int newY) {
		GraphNode element = petrinet.getGraphNodeById(id);
		if (element == null) {
			throw new NullPointerException("no node exists with id "+id);
		} else {
			element.setX(newX);
			element.setY(newY);
			notifyStateListeners();
			return true;
		}
	}
	
	/**
	 * Get a read-only view of the node in the Petri net with the specified
	 * id (if it exists).
	 * 
	 * @param id The id of the element to view.
	 * @return If an node with this id exists, a ReadOnlyGraphNode view of
	 * 	that node is returned. If no node with this id exists, null is
	 * 	returned.
	 */
	public ReadOnlyGraphNode viewNodeWithId(Integer id) {
		GraphNode element = petrinet.getGraphNodeById(id);
		if (element == null) {
			return null;
		} else {
			return new ReadOnlyGraphNode(element);
		}
	}
	
	/**
	 * Get a list of all nodes in the Petri net in read-only form.
	 * 
	 * @return A List of ReadOnlyGraphNodes in the Petri net.
	 */
	public List<ReadOnlyGraphElement> viewNodes() {
		List<ReadOnlyGraphElement> rval = new ArrayList<ReadOnlyGraphElement>();
		rval.addAll(viewArcs());
		rval.addAll(viewPlaces());
		rval.addAll(viewTransitions());
		return rval;
	}

	/**
	 * Get a list of all arcs in the Petri net in read-only form.
	 * 
	 * @return A List of ReadOnlyArcs in the Petri net.
	 */
	public List<ReadOnlyArc> viewArcs() {
		List<AbstractArc> arcs = petrinet.getAbstractArcs();
		List<ReadOnlyArc> rval = new ArrayList<ReadOnlyArc>(arcs.size());
		for (AbstractArc a : arcs) {
			rval.add(new ReadOnlyArc(a));
		}
		return rval;
	}
	
	/**
	 * Get a list of all places in the Petri net in read-only form.
	 * 
	 * @return A List of ReadOnlyPlaces in the Petri net.
	 */
	public List<ReadOnlyPlace> viewPlaces() {
		List<AbstractPlace> places = petrinet.getAbstractPlaces();
		List<ReadOnlyPlace> rval = new ArrayList<ReadOnlyPlace>(places.size());
		for (AbstractPlace p : places) {
			rval.add(new ReadOnlyPlace(p));
		}
		return rval;
	}
	
	/**
	 * Get a list of all transitions in the Petri net in read-only form.
	 * 
	 * @return A List of ReadOnlyTransitions in the Petri net.
	 */
	public List<ReadOnlyTransition> viewTransitions() {
		List<AbstractTransition> transitions = petrinet.getAbstractTransitions();
		List<ReadOnlyTransition> rval = new ArrayList<ReadOnlyTransition>(transitions.size());
		for (AbstractTransition t : transitions) {
			rval.add(new ReadOnlyTransition(t));
		}
		return rval;
	}
	
	/**
	 * Convert the Petri net to an mxGraph representation.
	 * 
	 * @return An mxGraph representing this controller's Petri net.
	 */
	public mxGraph convertToGraph() {
		return PetriNetUtil.convertPetriNetToMxGraph(petrinet);
	}
	
	/**
	 * 
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void saveXml(String filename) throws IOException
	{
		XmlInputOutput.printModel(petrinet, filename);
	}
}
