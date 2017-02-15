package edu.boisestate.cs573.spring2017.pretrinet.model;

import java.util.HashSet;

import edu.boisestate.cs573.spring2017.helpers.IdGenerator;

public class GraphNode
{
	private Integer ID;
	private Integer x;
	private Integer y;
	private String name;
	private HashSet<Arc> arcs;
	//Should never be instantiated
	public GraphNode()
	{
		this.ID= IdGenerator.getUniqueIdentifier();
		this.arcs =((new HashSet<Arc>()));
	}
	
	
	public Integer getID()
	{
		return ID;
	}
	public void setID(Integer iD)
	{
		ID = iD;
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
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
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
			if(this.ID== a.getTarget().getID()) result.add(a);
		}
		return result;
	}
	
	public HashSet<Arc> getOutGoingArcs()
	{
		HashSet<Arc> result = new HashSet<>();
		
		for(Arc a : this.getArcs())
		{
			if(this.ID== a.getSource().getID()) result.add(a);
		}
		return result;
	}
	
	
	
}
