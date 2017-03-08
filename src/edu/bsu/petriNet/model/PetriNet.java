package edu.bsu.petriNet.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bsu.petriNet.helper.IdGenerator;

public class PetriNet
{

	
	private HashMap<Integer,Arc> arcs;
	private HashMap<Integer,Place> places;
	private HashMap<Integer,Transition> transitions;
	
	public PetriNet()
	{
		arcs = new HashMap<>();
		places = new HashMap<>();
		transitions = new HashMap<>();
	}
	
	protected Boolean createPlace(int id, String name, int tokenN, int x, int y){
		
		Place p = new Place(id, name, tokenN, x, y);
		places.put(id,p);
		return true;
	}
			
	public Boolean createPlace(String name, int tokenN, int x, int y){
		
		return this.createPlace(IdGenerator.getUniqueIdentifier(), name, tokenN, x, y);
	}
	
	protected Boolean createTransition(int id, String name, int x, int y){
		
		Transition t = new Transition(id, name, x, y);
		transitions.put(id,t);
		return true;
	}
	
    public Boolean createTransition(String name, int x, int y){
		
		return this.createTransition(IdGenerator.getUniqueIdentifier(), name, x, y);
	}
	
    protected Boolean createArc(int id, String name, int sourceId, int targeId, int weight){
		
    	GraphNode source = getGraphNodeById(sourceId);
    	GraphNode target = getGraphNodeById(targeId);
    	
    	if(source != null && target != null){
    		Arc a = new Arc(id, name, source, target);
    		
    		source.addArc(a);
    		target.addArc(a);
    		arcs.put(id,a);
    		
    		return true;
    	}
		return false;
	}
	
    public Boolean createArc(String name, int sourceId, int targeId, int weight){
		
		return this.createArc(IdGenerator.getUniqueIdentifier(), name, sourceId, targeId, weight);
	}
    
   /* private boolean deletePlace(int id)
    {
    	return true;
    }
    
    private boolean deletePlace(int id)
    {
    	return true;
    }
    
    private boolean deletePlace(int id)
    {
    	return true;
    }
    */
    
    public boolean setArcWeight(int id, int weight)
    {
    	if(arcs.containsKey(id)){
    		arcs.get(id).setWeight(weight);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean setPlaceTokenNumber(int id, int tokenNumber)
    {
    	if(places.containsKey(id)){
    		places.get(id).setNumberOfTokens(tokenNumber);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean setName(int id , String name){
    	
    	GraphElement gE = getGraphElementById(id);
    	if(gE != null){
    		gE.setName(name);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean setPosition(int id, int x, int y)
    {
    	GraphNode gN = (GraphNode)getGraphElementById(id);
    	if(gN != null){
    		gN.setX(x);
    		gN.setY(y);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean fire(int id)
    {
    	if(transitions.containsKey(id)){	
    		return transitions.get(id).fire();
    	} else {
    		return false;
    	}
    }
    
    
    public boolean delete(int id)
    {
    	GraphElement gE = getGraphElementById(id);
    	if(gE != null){
    		if(gE.getClass() == Arc.class){
    			return deleteArc((Arc)gE);
    		} else if(gE.getClass() == Transition.class){
    			return deleteTransition((Transition)gE);
    		} else if(gE.getClass() == Place.class){ 
    			return deletePlace((Place)gE);
    		}    	
    	}
    	
    	return false;
    }
    
    private boolean deleteArc(Arc a){
    	
    	boolean result = true;
    	result = result && a.getSource().removeArc(a.getID());
    	result = result && a.getTarget().removeArc(a.getID());
    	result = result && arcs.containsKey(a.getID());
    	
    	if(arcs.containsKey(a.getID())){
    		arcs.remove(a.getID());
    	}
    	
    	return result;
    }
    
    private boolean deleteTransition(Transition t){
    	boolean result = true;
    	
    	for(Arc a: t.getArcs()){
    		result = result && deleteArc(a);
    	}
    	
    	result = result && transitions.containsKey(t.getID());
    	
    	if(transitions.containsKey(t.getID())){
    		transitions.remove(t.getID());
    	}
    	
    	return result;
    }
    
    private boolean deletePlace(Place p){
    	boolean result = true;
    	
    	for(Arc a: p.getArcs()){
    		result = result && deleteArc(a);
    	}
    	
    	result = result && places.containsKey(p.getID());
    	
    	if(places.containsKey(p.getID())){
    		places.remove(p.getID());
    	}
    	
    	return result;
    }
    
    public GraphElement getGraphElementById(int id){
    	
    	GraphElement gE = null;
    	if(places.containsKey(id)){
    		gE = places.get(id);
    	} else if(transitions.containsKey(id)){
    		gE = transitions.get(id);
    	} else if(arcs.containsKey(id)){
    		gE = arcs.get(id);
    	}
    	return gE;
    }
    
	public PetriNet getDeepCopy()
	{
		String fileName = "tmp.xml.tmp";
		XmlInputOutput.printModel(this, fileName);
		PetriNet newNet = XmlInputOutput.readModel(fileName);
		
		return newNet;
	}
	public ArrayList<Arc> getArcs()
	{
		ArrayList<Arc> tmp = new ArrayList<>(arcs.values());
		return tmp;
	}
	
	public ArrayList<Place> getPlaces()
	{
		ArrayList<Place> tmp = new ArrayList<>(places.values());
		return tmp;
	}
	
	
	public ArrayList<Transition> getTransitions()
	{
		ArrayList<Transition> tmp = new ArrayList<>(transitions.values());
		return tmp;
	}
	
	public List<AbstractTransition> getAbstractTransitions()
	{
		List<AbstractTransition> abstractTransitions = new ArrayList<>();
		for(Transition t: this.transitions.values()){
			abstractTransitions.add(t.getAbstract());
		}
		return abstractTransitions;
	}
	
	
	public List<AbstractArc> getAbstractArcs()
	{
		List<AbstractArc> abstractArcs = new ArrayList<>();
		for(Arc a: this.arcs.values()){
			abstractArcs.add(a.getAbstract());
		}
		return abstractArcs;
	}
	
	public List<AbstractPlace> getAbstractPlaces()
	{
		List<AbstractPlace> abstractPlaces = new ArrayList<>();
		for(Place p: this.places.values()){
			abstractPlaces.add(p.getAbstract());
		}
		return abstractPlaces;
	}
	
	
	
	public Arc getArcById(int id){	
		return this.arcs.get(id);
	}
	
	public Transition getTransitionById(int id){	
		return this.transitions.get(id);
	}
	
	public Place getPlaceById(int id){	
		return this.places.get(id);
	}
	
	public GraphNode getGraphNodeById(int id){
		
		GraphNode g = null;
		
		g = getTransitionById(id);
		
		if(g != null)
			return g;
		else{
			return getPlaceById(id);
		}
	}
	
	public void updateIdGenerator(){	
		int maxID = 0;
		
		for(Integer id: this.arcs.keySet()){		
			maxID = Math.max(id, maxID);
		}
		
		for(Integer id: this.places.keySet()){		
			maxID = Math.max(id, maxID);
		}
		
		for(Integer id: this.transitions.keySet()){		
			maxID = Math.max(id, maxID);
		}
		
		IdGenerator.updateId(maxID);
	}
}
