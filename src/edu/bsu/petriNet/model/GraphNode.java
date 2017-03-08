package edu.bsu.petriNet.model;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class GraphNode extends GraphElement
{

	private Integer x = 0;
	private Integer y = 0;

	private HashMap<Integer,Arc> arcs;
	
	//Should never be instantiated
	public GraphNode(int id, String name, int x, int y)
	{
		super(id, name);
		setX(x);
		setY(y);
		this.arcs =((new HashMap<>()));
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
		this.arcs.put(a.getID(), a);
	}
	
	
	public ArrayList<Arc> getArcs()
	{
		ArrayList<Arc> tmp = new ArrayList<>(arcs.values());
		return tmp;
	}

	public boolean removeArc(int id)
	{
		if(arcs.containsKey(id)){
			arcs.remove(id);
			return true;
		}
		return false;
	}

	public ArrayList<Arc> getIncomingArcs()
	{
		ArrayList<Arc> result = new ArrayList<>();
		
		for(Arc a : this.getArcs())
		{
			if(this.getID()== a.getTarget().getID()) result.add(a);
		}
		return result;
	}
	
	public ArrayList<Arc> getOutGoingArcs()
	{
		ArrayList<Arc> result = new ArrayList<>();
		
		for(Arc a : this.getArcs())
		{
			if(this.getID()== a.getSource().getID()) result.add(a);
		}
		return result;
	}
	
	
	
}
