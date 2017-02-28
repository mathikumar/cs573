package edu.boisestate.cs573.spring2017.pretrinet.model;

public class Arc 
{
	private GraphNode source;
	private GraphNode target;
	private int weight;
	
	public Arc()
	{
		this.setWeight(1);
	}

	
	public Arc(GraphNode source,GraphNode target)
	{
		this.setSource(source);
		this.setTarget(target);
		this.setWeight(1);
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

	public AbstractArc getAbstract(){
		return new AbstractArc(source.getID(), target.getID(),weight);
	}
	

}
