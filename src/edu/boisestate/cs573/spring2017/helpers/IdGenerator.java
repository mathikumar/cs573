package edu.boisestate.cs573.spring2017.helpers;

public class IdGenerator
{

	
	private static int lastId =0;
	
	public static int getUniqueIdentifier()
	{
		IdGenerator.lastId++;
		return IdGenerator.lastId;
		
	}
	
	public static void updateId(int id)
	{
		 IdGenerator.lastId=Math.max(IdGenerator.lastId,id);
	}
	
}
