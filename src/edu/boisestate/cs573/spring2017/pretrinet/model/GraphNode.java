package edu.boisestate.cs573.spring2017.pretrinet.model;

import java.util.HashSet;

import edu.boisestate.cs573.spring2017.helpers.IdGenerator;

public class GraphNode extends AbstractGraphNode
{
	private HashSet<Arc> arcs;
	//Should never be instantiated
	public GraphNode()
	{
		super();
		this.setID(IdGenerator.getUniqueIdentifier());
		this.arcs =((new HashSet<Arc>()));
	}
	
	public GraphNode(AbstractGraphNode n){
		super(n);
		this.setID(IdGenerator.getUniqueIdentifier());
		this.arcs =((new HashSet<Arc>()));
	}
	

	public HashSet<Arc> getArcs()
	{
		return arcs;
	}


	public HashSet<Arc> getIncomingArcs()
	{
		HashSet<Arc> result = new HashSet<>();
		
		for(Arc a : this.getArcs())
		{
			if(this.getID()== a.getTarget().getID()) result.add(a);
		}
		return result;
	}
	
	public HashSet<Arc> getOutGoingArcs()
	{
		HashSet<Arc> result = new HashSet<>();
		
		for(Arc a : this.getArcs())
		{
			if(this.getID()== a.getSource().getID()) result.add(a);
		}
		return result;
	}
	
	
	
}
