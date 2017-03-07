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
	
	protected Boolean createPlace(int id, String name, int tokenN){
		
		Place p = new Place(id, name, tokenN);
		places.put(id,p);
		return true;
	}
	
	public Boolean addPlace(Place p){
		places.put(IdGenerator.getUniqueIdentifier(),p);
		return true;
	}
	
	public Boolean addTransition(Transition t){
		transitions.put(IdGenerator.getUniqueIdentifier(),t);
		return true;
	}
	
	public Boolean addArc(Arc a){
		arcs.put(IdGenerator.getUniqueIdentifier(),a);
		return true;
	}
		
	public Boolean createPlace(String name, int tokenN){
		
		return this.createPlace(IdGenerator.getUniqueIdentifier(), name, tokenN);
	}
	
	protected Boolean createTransition(int id, String name){
		
		Transition t = new Transition(id, name);
		transitions.put(id,t);
		return true;
	}
	
    public Boolean createTransition(String name){
		
		return this.createTransition(IdGenerator.getUniqueIdentifier(), name);
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
    	//TODO
    	return true;
    }
    
    public boolean setPlaceTokenNumber(int id, int tokenNumber)
    {
    	//TODO
    	return true;
    }
    public boolean setName(int id , String name){
    	//TODO
    	return true;
    }
    
    public boolean setPosition(int id, int x, int y)
    {
    	//TODO
    	return true;
    }
    
    public boolean fire(int transitionId)
    {
    	//TODO
    	return true;
    }
    
    
    public boolean delete(int id)
    {
    	//TODO
    	return true;
    }
    
	public PetriNet getDeepCopy()
	{
		PetriNet newNet = this;
		//TODO
		return newNet;
	}
	public HashMap<Integer,Arc> getArcs()
	{
		return this.arcs;
	}
	
	public HashMap<Integer,Place> getPlaces()
	{
		return this.places;
	}
	
	
	public HashMap<Integer,Transition> getTransitions()
	{
		return this.transitions;
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
