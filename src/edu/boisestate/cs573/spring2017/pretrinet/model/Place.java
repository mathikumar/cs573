package edu.boisestate.cs573.spring2017.pretrinet.model;


public class Place extends GraphNode
{
	private int	numberOfTokens;
	
	protected Place(int id, String name, int numberofTokens)
	{
		super(id,name);
		
		this.setNumberOfTokens(numberofTokens);
	}

	public int getNumberOfTokens()
	{
		return numberOfTokens;
	}

	public void setNumberOfTokens(int numberOfTokens)
	{
		this.numberOfTokens = numberOfTokens;
	}

	
	public void addTokens(int n)
	{
		this.setNumberOfTokens(this.getNumberOfTokens()+n);
	}
	
	public void removeTokens(int n){
		this.setNumberOfTokens(this.getNumberOfTokens()-n);
	}
	
	
}
