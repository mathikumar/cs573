package edu.bsu.petriNet.analytics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.Place;
import edu.bsu.petriNet.model.XmlInputOutput;

/**
 * Created by Ion Madrazo on 4/5/2017.
 */

public class CoverabilityTree {

	private CoverabilityTreeNode root;
	private ArrayList<CoverabilityTreeNode> nodeList;

	public CoverabilityTree(PetriNet p) {
		root = new CoverabilityTreeNode(p, null, null);
		nodeList = new ArrayList<>();

		Queue<CoverabilityTreeNode> queue = new LinkedList<>();

		queue.add(root);

		while (!queue.isEmpty()) {
			// System.out.print("Iteration"+(n++)+"queue:"+queue.size()+"\n");
			CoverabilityTreeNode current = queue.poll();

			nodeList.add(current);
			
			if (current.getLabel() == CoverabilityTreeNode.NODELABEL.ACTIVE) {
				for (CoverabilityTreeNode child : current.getChildren()) {
					try {
						//
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					queue.add(child);
				}
			}

		}
	}

	public CoverabilityTreeNode getRoot() {
		return root;
	}

	public static void main(String[] args) {

		PetriNet petrinet = XmlInputOutput.readModel("AsyncCommunication.xml");
		CoverabilityTree tree = new CoverabilityTree(petrinet);
		System.out.println("Finished");
	}

	public boolean isBounded() {

		for (CoverabilityTreeNode child : nodeList) {
			ArrayList<Place> places = child.getPetrinet().getPlaces();
			for(Place pl: places){
				if(pl.getNumberOfTokens() == Integer.MAX_VALUE)
					return false;
			}
		}
		
		return true;
	}

	public boolean hasDeadEnd() {

		for (CoverabilityTreeNode child : nodeList) {
			if(child.getLabel() == CoverabilityTreeNode.NODELABEL.DEADEND)
				return true;
		}
		return false;
	}
	
	public boolean isReachable(ArrayList<Integer> tokenList){
		
		for (CoverabilityTreeNode child : nodeList) {
			ArrayList<Place> places = child.getPetrinet().getPlaces();
			boolean found = true;
			for(int i = 0; i< places.size(); i++){
				if(places.get(i).getNumberOfTokens() != tokenList.get(i)){
					found = false;
					break;
				}
			}
			
			if(found == true)
				return true;
		}
		return false;
	}
	
}
