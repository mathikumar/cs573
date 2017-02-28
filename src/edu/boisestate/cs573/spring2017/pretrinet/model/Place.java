package edu.boisestate.cs573.spring2017.pretrinet.model;


public class Place extends GraphNode
{
	private int	numberOfTokens;

	public Place()
	{
		super();

	}

	public Place(int numberofTokens)
	{
		super();

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
	
	public AbstractPlace getAbstract(){
		AbstractPlace p =  new AbstractPlace(this.getX(), this.getY(), this.numberOfTokens, this.getName());
		p.setID(this.getID());
		return p;
	}
	
}
