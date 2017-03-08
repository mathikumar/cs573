package edu.bsu.petriNet.model;

public class Transition extends GraphNode
{

	protected Transition(int id, String name, int x, int y)
	{
		super(id,name, x, y);
	}

	public boolean isFirable()
	{

		for (Arc a : this.getIncomingArcs())
		{
			if (a.getWeight() > ((Place) a.getSource()).getNumberOfTokens())
			{
				return false;
			}
		}

		return true;

	}

	public boolean fire()
	{
		if (!this.isFirable())
		{
			return false;
		}

		for (Arc a : this.getIncomingArcs())
		{
			Place p = (Place) a.getSource();
			p.removeTokens(a.getWeight());
		}

		for (Arc a : this.getOutGoingArcs())
		{
			Place p = (Place) a.getTarget();
			p.addTokens(a.getWeight());
		}

		return true;

	}
	
	public AbstractTransition getAbstract(){
		AbstractTransition t = new AbstractTransition(this.getX(), this.getY(), this.getName());
		t.setID(this.getID());
		t.setFirable(this.isFirable());
		return t;
	}

}
