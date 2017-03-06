package edu.bsu.petriNet.controller;

import java.util.ArrayList;
import java.util.Iterator;

import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.AbstractGraphNode;

public class StateSet {
	private ArrayList<AbstractArc> arcs;
	private ArrayList<AbstractPlace> places;
	private ArrayList<AbstractTransition> transitions;

	public StateSet(){
		this.arcs = new ArrayList<>();
		this.places = new ArrayList<>();
		this.transitions = new ArrayList<>();
	}
	/*
	public void add(AbstractGraphNode n){
		if(n instanceof AbstractArc){
			arcs.add((AbstractArc)n);
		}
		else if(n instanceof AbstractPlace){
			places.add((AbstractPlace)n);
		}
		else if(n instanceof AbstractTransition){
			transitions.add((AbstractTransition)n);
		}
	}*/
	
	public void addArc(AbstractArc a){
		arcs.add(a);
	}
	
	public void addTransition(AbstractTransition t){
		transitions.add(t);
	}
	
	public void addPlace(AbstractPlace p){
		places.add(p);
	}
	
	public void addArcs(Iterable<AbstractArc> arcs){
		for(AbstractArc a: arcs){
			this.arcs.add(a);
		}
	}
	
	public void addTransitions(Iterable<AbstractTransition> trans){
		for(AbstractTransition t: trans){
			transitions.add(t);
		}
	}
	
	public void addPlaces(Iterable<AbstractPlace> places){
		for(AbstractPlace p: places){
			this.places.add(p);
		}
	}	
	
	public ArrayList<AbstractArc> getArcs() {
		return arcs;
	}

	public ArrayList<AbstractPlace> getPlaces() {
		return places;
	}

	public ArrayList<AbstractTransition> getTransitions() {
		return transitions;
	}

	public Iterator<AbstractArc> arcIter(){
		return this.arcs.iterator();
	}
	
	public Iterator<AbstractPlace> placeIter(){
		return this.places.iterator();
	}
	
	public Iterator<AbstractTransition> transitionIter(){
		return this.transitions.iterator();
	}
	
}
