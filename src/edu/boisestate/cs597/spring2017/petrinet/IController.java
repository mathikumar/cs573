package edu.boisestate.cs597.spring2017.petrinet;




public interface IController {
	

	/*
	 * Modify net
	 */
	/*
	 * 	Begin a new petri net
	 */
	public Boolean newNet();
	
	/*
	 * Add a place to a petri net
	 */
	public Boolean addTransition(AbstractTransition t);
	public Boolean addPlace(AbstractPlace p);
	public Boolean addArc(AbstractArc a);
	public Boolean connectArc(AbstractArc a);
	public Boolean disconnectArc(AbstractArc a);
	public Boolean deletePlace(AbstractPlace p);
	public Boolean deleteTransition(AbstractTransition t);
	public Boolean deleteArc(AbstractArc a);
	public Boolean setArcWeight(AbstractArc a);
	public Boolean setPlaceTokenCount(AbstractPlace p);
	public Boolean setName(GraphNode n);
	public Boolean registerChangeListener(IChangeListener l);
	public Boolean setLocation(GraphNode n);
	public Boolean save();
	public Boolean load(String filename);
	public Boolean fire(AbstractTransition t);
	public Boolean simulate(int n_steps);
	public Boolean undo();



}
