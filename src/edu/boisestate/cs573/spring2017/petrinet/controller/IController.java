package edu.boisestate.cs573.spring2017.petrinet.controller;




public interface IController {
	

	/**
	 * Begin a new petri net
	 * @return true on success, false on failure
	 */
	public Boolean newNet();
	
	/**
	 * Adds a transition to the petrinet.\n
	 * @return true on success, false on failure
	 */
	public Boolean addTransition();
	
	/**
	 * Adds a place to the petrinet
	 * @return
	 */
	public Boolean addPlace();
	
	/**
	 * Adds an arc to the petrinet
	 * @return true on success, false on failure
	 */
	public Boolean addArc();

	/**
	 * Connects an arc to another petrinet component.\n
	 * <span style="font-style: italic;">Precondition: ID and name are set on n  </span>
	 * @param a The Arc to connect
	 * @return true on success, false on failure
	 */
	public Boolean connectArc(AbstractArc a);
	
	/**
	 * Disconnects an arc from another petrinet component.
	 * @param a Arc to disconnect
	 * @return true on success, false on failure
	 */
	public Boolean disconnectArc(AbstractArc a);
	
	/**
	 * Delete a node from a petrinet.  Could be a place, transition or arc.
	 * @param p The place to delete
	 * @return true on success, false on failure
	 */
	public Boolean delete(GraphNode n);
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public Boolean setArcWeight(AbstractArc a);
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Boolean setPlaceTokenCount(AbstractPlace p);
	
	/**
	 * Changes the name of a node.
	 * 
	 * @param n
	 * @return
	 */
	public Boolean setName(GraphNode n);
	
	/**
	 * Adds the StateListener l to the list of objects that will be notified of changes in the net.
	 * @param l
	 * @return
	 */
	public Boolean registerStateListener(IStateListener l);
	
	/**
	 * Change the x,y coordinates of a node
	 * @param n
	 * @return
	 */
	public Boolean setLocation(GraphNode n);
	
	/**
	 * Saves the initial state of the net to the specified filename
	 * @param filename
	 * @return
	 */
	public Boolean save(String filename);
	
	/**
	 * Loads the initial state of the net at the specified filename.
	 * @param filename
	 * @return
	 */
	public Boolean load(String filename);
	
	/**
	 * Fires a transition in the net.
	 * @param t
	 * @return
	 */
	public Boolean fire(AbstractTransition t);
	
	/**
	 * Performs random simulation in the net for n_steps steps.
	 * @param n_steps
	 * @return
	 */
	public Boolean simulate(int n_steps);
	
	/**
	 * Undoes the last change in the net.
	 * @return
	 */
	public Boolean undo();



}
