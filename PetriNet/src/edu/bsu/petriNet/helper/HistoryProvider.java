package edu.bsu.petriNet.helper;

import java.util.ArrayList;

import edu.bsu.petriNet.model.PetriNet;

public class HistoryProvider
{

	private static ArrayList<PetriNet> checkpoints;
	private static int currentCheckPoint; 
	
	public static void init()
	{
		HistoryProvider.checkpoints = new ArrayList<PetriNet>();
		HistoryProvider.checkpoints.add(new PetriNet());
		HistoryProvider.currentCheckPoint = 0;
	}
	
	public static void reset()
	{
		HistoryProvider.init();
	}
	
	public static PetriNet getCurretPetriNet()
	{
		return HistoryProvider.checkpoints.get(HistoryProvider.currentCheckPoint);
	}
		
	public static void savePetriNetCheckPoint(PetriNet net)
	{
		HistoryProvider.checkpoints.add(net.getDeepCopy());
		HistoryProvider.currentCheckPoint++;
	}
		
	public static boolean isUndoPossible()
	{
		return HistoryProvider.currentCheckPoint>0;
	}
	
	public static boolean isRedoPossible()
	{
		return HistoryProvider.currentCheckPoint<HistoryProvider.checkpoints.size()-1;
	}
	
	public static boolean undo()
	{
		if(HistoryProvider.isUndoPossible())
		{
			HistoryProvider.currentCheckPoint--;
			return true;
		}
		return false;
	}
	
	public static boolean redo()
	{
		if(HistoryProvider.isRedoPossible())
		{
			HistoryProvider.currentCheckPoint++;
			return true;
		}
		return false;
	}
	
}
