package edu.bsu.petriNet.model;

import java.util.HashSet;


public abstract class GraphNode extends GraphElement
{

	private Integer x = 0;
	private Integer y = 0;

	private HashSet<Arc> arcs;
	
	//Should never be instantiated
	public GraphNode(int id, String name)
	{
		super(id, name);
		this.arcs =((new HashSet<Arc>()));
	}
		
	public Integer getX()
	{
		return x;
	}
	
	public void setX(Integer x)
	{
		this.x = x;
	}
	
	public Integer getY()
	{
		return y;
	}
	
	public void setY(Integer y)
	{
		this.y = y;
	}

	public void addArc(Arc a)
	{
		this.arcs.add(a);
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
