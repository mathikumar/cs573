package edu.bsu.petriNet.model;


public class Place extends GraphNode
{
	private int	numberOfTokens;
	
	protected Place(int id, String name, int numberofTokens, int x, int y)
	{
		super(id,name, x, y);
		
		this.setNumberOfTokens(numberofTokens);
	}

	public int getNumberOfTokens()
	{
		return numberOfTokens;
	}

	public void setNumberOfTokens(int numberOfTokens)
	{
		assert numberOfTokens>=0;
		if(numberOfTokens>=0) this.numberOfTokens = numberOfTokens;
	}

	
	public void addTokens(int n)
	{
		if(!(this.getNumberOfTokens()== Integer.MAX_VALUE))
		this.setNumberOfTokens(this.getNumberOfTokens()+n);
	}
	
	public void removeTokens(int n){
		if(!(this.getNumberOfTokens()== Integer.MAX_VALUE))
		this.setNumberOfTokens(this.getNumberOfTokens()-n);
	}
	
	public AbstractPlace getAbstract(){
		AbstractPlace p =  new AbstractPlace(this.getX(), this.getY(), this.numberOfTokens, this.getName());
		p.setID(this.getID());
		return p;
	}
	
}
