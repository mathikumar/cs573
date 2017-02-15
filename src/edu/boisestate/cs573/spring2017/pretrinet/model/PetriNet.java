package edu.boisestate.cs573.spring2017.pretrinet.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PetriNet
{

	
	private HashSet<Arc> arcs;
	private HashSet<Place> places;
	private HashSet<Transition> transitions;
	
	public PetriNet()
	{
		arcs = new HashSet<Arc>();
		places = new HashSet<Place>();
		transitions = new HashSet<Transition>();
	}
	
	
	public PetriNet getDeepCopy()
	{
		PetriNet newNet = this;
		//TODO
		return newNet;
	}
	public HashSet<Arc> getArcs()
	{
		return this.arcs;
	}
	
	public HashSet<Place> getPlaces()
	{
		return this.places;
	}
	
	
	public HashSet<Transition> getTransitions()
	{
		return this.transitions;
	}
	
	
	
}
