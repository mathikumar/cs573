package edu.bsu.petriNet.model;


abstract class GraphElement
{
	private Integer ID;
	private String name;
	//Should never be instantiated
	public GraphElement(int id, String name)
	{
		this.ID = id;
		this.name = name;
	}
	
	public Integer getID()
	{
		return ID;
	}
	
	protected void setID(Integer iD)
	{
		ID = iD;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	
}
