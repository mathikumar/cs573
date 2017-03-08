package edu.bsu.petriNet.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;

import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.Arc;
import edu.bsu.petriNet.model.GraphNode;
import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.Place;
import edu.bsu.petriNet.model.Transition;

public class PetriNetUtil {
	public static PetriNet convertMxGraphToPetriNet(mxGraph graph){
		Object parent = graph.getDefaultParent();
		Object[] vertices = graph.getChildVertices(parent);
		PetriNet petriNet = new PetriNet();
		Map<String,Place> places = new HashMap<String,Place>();
		Map<String,Transition> transitions = new HashMap<String,Transition>();
		
		for(Object vertex:vertices){
			mxCell v = (mxCell)vertex;
			String name = (String)v.getValue();
			if(v.getStyle()!=null && v.getStyle().equals("ellipse")){
				mxGeometry geo = v.getGeometry();
				Place place = new Place(Integer.parseInt(v.getId()),name,0,(int)geo.getX(),(int)geo.getY());
				places.put(v.getId(),place);
			} else{
				mxGeometry geo = v.getGeometry();
				
				Transition transition = new Transition(Integer.parseInt(v.getId()),name,(int)geo.getX(),(int)geo.getY());
				transitions.put(v.getId(),transition);
			}
		}
		for(Entry<String,Place> e: places.entrySet()){
			petriNet.addPlace(e.getValue());
		}
		for(Entry<String,Transition> e: transitions.entrySet()){
			petriNet.addTransition(e.getValue());
		}
		
		Object[] edges = graph.getChildEdges(parent);
		for(Object edge:edges){
			mxCell v = (mxCell)edge;
			String name = (String)v.getValue();
			GraphNode source;
			GraphNode target;
			if(places.containsKey(v.getSource().getId())){
				source = places.get(v.getSource().getId());
			}else{
				source = transitions.get(v.getSource().getId());
			}
			if(places.containsKey(v.getTarget().getId())){
				target = places.get(v.getTarget().getId());
			}else{
				target = transitions.get(v.getTarget().getId());
			}
			Arc arc = new Arc(Integer.parseInt(v.getId()),name,source,target);
			petriNet.addArc(arc);
		}
		
		return petriNet;
	}
	
	public static mxGraph convertPetriNetToMxGraph(PetriNet petriNet){
		mxGraph graph = new mxGraph();
		graph.getModel().beginUpdate();
		Object parent = graph.getDefaultParent();
		try
		{
			Map<Integer,Object> nodes = new HashMap<Integer,Object>();
			for(AbstractPlace place:petriNet.getAbstractPlaces()){
				//TODO size and location
				Object v = graph.insertVertex(parent, null, place.getName(), 100, 100, 70, 70,"shape=ellipse;perimeter=ellipsePerimeter");	
				nodes.put(place.getID(),v);
			}
			
			for(AbstractTransition transition:petriNet.getAbstractTransitions()){
				//TODO size and location
				Object t = graph.insertVertex(parent, null,transition.getName(),300,300, 40,70);
				nodes.put(transition.getID(), t);
			}
					
			for(AbstractArc arc:petriNet.getAbstractArcs()){
				graph.insertEdge(parent, null, arc.getName(), nodes.get(arc.getOrigin()), nodes.get(arc.getTarget()));
			}
		}
		finally
		{
			graph.getModel().endUpdate();
			
		}
		return graph;
	}
}
