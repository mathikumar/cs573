package edu.bsu.petriNet.controller;

import edu.bsu.petriNet.analytics.CoverabilityTree;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.AbstractGraphNode;

public interface IController {
	

	/**
	 * Begin a new petri net
	 * @return true on success, false on failure
	 */
	public Boolean newNet();
	
	/**
	 * Adds a transition to the petrinet.\n
	 * @return id on success, null on failure
	 */
	public Integer addTransition(AbstractTransition t);
	
	/**
	 * Adds a place to the petrinet
	 * @return id on success, null on failure
	 */
	public Integer addPlace(AbstractPlace p);
	
	/**
	 * Adds an arc to the petrinet
	 * @return id on success, null on failure
	 */
	public Integer addArc(AbstractArc a);


	
	/**
	 * Delete a node from a petrinet.  Could be a place, transition or arc.
	 * @param p The place to delete
	 * @return true on success, false on failure
	 */
	public Boolean delete(Integer ID);
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public Boolean setArcWeight(AbstractArc a, Boolean notify);
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public Boolean setPlaceTokenCount(AbstractPlace p, Boolean notify);
	
	/**
	 * Changes the name of a node.
	 * 
	 * @param n
	 * @return
	 */
	public Boolean setName(AbstractGraphNode n, Boolean notify);
	
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
	public Boolean setLocation(AbstractGraphNode n, Boolean notify);
	
	/**
	 * Translate the x,y coordinates of a node
	 * 
	 * @param id the id of the node to move
	 * @param dx delta x
	 * @param dy delta y
	 */
	public Boolean translate(Integer id, Integer dx, Integer dy, Boolean notify);
	
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
	public Boolean simulate(int n_steps, int delay_ms);
	
	/**
	 * Undoes the last change in the net.
	 * @return
	 */
	public Boolean undo();
	
	/**
	 * Redoes the last change in the net.
	 * @return
	 */
	public Boolean redo();
	
	/**
	 * Undoes all simulation changes, reverting the net to the M0 state.
	 * @return
	 */
	public Boolean undoSimulation();
	
	/**
	 * Start an undo block.
	 * 
	 * @throws IllegalStateException if already in an undo block.
	 */
	public void beginUndoBlock() throws IllegalStateException;
	
	/**
	 * End an undo block. All actions that happened within the undo block will
	 * be undone by a single undo() call.
	 * 
	 * @throws IllegalStateException if not in an undo block.
	 */
	public void endUndoBlock() throws IllegalStateException;
	
	public void pollState();
	
	public CoverabilityTree getCoverabilityTree();
}
