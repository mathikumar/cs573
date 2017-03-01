package edu.boisestate.cs573.spring2017.pretrinet.model;

public class Arc extends GraphElement
{
	private GraphNode source;
	private GraphNode target;
	private int weight=1;
	
	protected Arc(int id, String name, GraphNode source, GraphNode target)
	{
		super(id,name);
		this.source = source;
		this.target = target;
	}

	public GraphNode getSource()
	{
		return source;
	}

	public void setSource(GraphNode source)
	{
		this.source = source;
	}

	public GraphNode getTarget()
	{
		return target;
	}

	public void setTarget(GraphNode target)
	{
		this.target = target;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	
	

}
