package edu.bsu.petriNet.model;

public class Transition extends GraphNode
{

	protected Transition(int id, String name)
	{
		super(id,name);
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

}
