package edu.boisestate.cs573.spring2017.pretrinet.model;


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
	
	public GraphElement(String name){
		this.name = name;
	}
	
	public GraphElement(int id){
		this.ID = id;
	}
	
	public GraphElement(){
		
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
	
	@Override
	public boolean equals(Object o){
		if(o instanceof GraphElement){
			return this.ID == ((GraphElement)o).ID;
		}
		return false;
	}
	
}
