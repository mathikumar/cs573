package edu.bsu.petriNet.model;

import java.util.HashSet;

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
	
	public static void main(String[] args){
				
	}
}
