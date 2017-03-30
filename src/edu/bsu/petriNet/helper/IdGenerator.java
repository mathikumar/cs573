package edu.bsu.petriNet.helper;

public class IdGenerator
{
	private int lastId;
	
	public IdGenerator()
	{
		lastId = 0;
	}
	
	
	public int getUniqueIdentifier()
	{
		this.lastId++;
		return this.lastId;
		
	}
	
	public void updateId(int id)
	{
		this.lastId=Math.max(this.lastId,id);
	}
	
}
