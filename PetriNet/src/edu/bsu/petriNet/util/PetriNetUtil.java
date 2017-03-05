package edu.bsu.petriNet.util;

import com.mxgraph.view.mxGraph;

import edu.bsu.petriNet.model.PetriNet;

public class PetriNetUtil {
	public static PetriNet convertMxGraphToPetriNet(mxGraph graph){
		Object parent = graph.getDefaultParent();
		
		Object[] vertices = graph.getChildVertices(parent);
		for(Object vertex:vertices){
			
			
		}
		Object[] edges = graph.getChildEdges(parent);
		
		return null;
	}
}
