package edu.bsu.petriNet.helper;

import java.util.ArrayList;

import edu.bsu.petriNet.model.PetriNet;

public class HistoryProvider
{

	private ArrayList<PetriNet> checkpoints;
	private int currentCheckPoint; 
	
	public HistoryProvider(){
		this.reset();
	}
	
	public void reset()
	{
		this.checkpoints = new ArrayList<PetriNet>();
		//this.checkpoints.add(new PetriNet());
		this.currentCheckPoint = -1;
	}
	
	public PetriNet getCurretPetriNet()
	{
		return this.checkpoints.get(this.currentCheckPoint);
	}
		
	public void savePetriNetCheckPoint(PetriNet net)
	{
		if(this.currentCheckPoint < this.checkpoints.size()-1){
			this.checkpoints = new ArrayList<>(this.checkpoints.subList(0, this.currentCheckPoint+1));
		}
		this.checkpoints.add(net.getDeepCopy());
		this.currentCheckPoint++;
		System.out.println("Put: "+this+" checkpoints: "+this.checkpoints.size()+" i: "+this.currentCheckPoint);
	}
		
	public boolean isUndoPossible()
	{
		System.out.println("Check: "+this+" checkpoints: "+this.checkpoints.size()+" i: "+this.currentCheckPoint);
		return this.currentCheckPoint>0;
	}
	
	public boolean isRedoPossible()
	{
		return this.currentCheckPoint<this.checkpoints.size()-1;
	}
	
	public boolean hasState(){
		return this.currentCheckPoint >=0 && this.currentCheckPoint < this.checkpoints.size();
	}
	
	public boolean undo()
	{
		if(this.isUndoPossible())
		{
			this.currentCheckPoint--;
			return true;
		}
		return false;
	}
	
	public boolean redo()
	{
		if(this.isRedoPossible())
		{
			this.currentCheckPoint++;
			return true;
		}
		return false;
	}
	
}
