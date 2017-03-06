package edu.bsu.petriNet.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;

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
				//TODO tokens
				Place place = new Place(Integer.parseInt(v.getId()),name,0);
				mxGeometry geo = v.getGeometry();
				place.setX((int)geo.getX());
				place.setY((int)geo.getY());
				places.put(v.getId(),place);
			} else{
				Transition transition = new Transition(Integer.parseInt(v.getId()),name);
				mxGeometry geo = v.getGeometry();
				transition.setX((int)geo.getX());
				transition.setY((int)geo.getY());
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
}
