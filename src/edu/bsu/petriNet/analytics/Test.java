package edu.bsu.petriNet.analytics;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import edu.bsu.petriNet.analytics.GCoverabilityTree.Node;
import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;

public class Test {
	public static void main(String[] args){
		int topPadding = 100;
		int nodeSpacingH = 40;
		int nodeSpacingV = 80;
		int letterWidth = 15;
		GCoverabilityTree graph = new GCoverabilityTree("Coverability Tree");

    	//PetriNet petrinet = XmlInputOutput.readModel("AsyncCommunication.xml");
    	//PetriNet petrinet = XmlInputOutput.readModel("AsyncReadersWriter.xml");
    	PetriNet petrinet = XmlInputOutput.readModel("AsyncVendingMachine.xml");
    	//PetriNet petrinet = XmlInputOutput.readModel("AsyncDiningPhilosophers.xml");
		
    	
    	CoverabilityTree tree = new CoverabilityTree(petrinet);
		
		
    	List<CoverabilityTreeNode> currentLevel = new ArrayList<CoverabilityTreeNode>();
    	ArrayList<CoverabilityTreeNode> nextLevel ;
		Map<String,Node> nodes;
		Map<String,Node> prevNodes = new HashMap<String,Node>();
		
		int level = 0;
		int nodeWidth = tree.getRoot().getPetrinet().getPlaces().size()*letterWidth;
		
		Node nn = new Node(GCoverabilityTree.getNodeLabel(tree.getRoot()),graph.getViewWidth()/2-nodeWidth/2,topPadding);
		graph.addNode(nn);
		currentLevel.addAll(tree.getRoot().getChildren());
		prevNodes.put(tree.getRoot().toString(),nn);
		level++;
		boolean flag = true;
		while(!currentLevel.isEmpty()){
			nextLevel = new ArrayList<CoverabilityTreeNode>();
			Node n;
			nodes = new HashMap<String,Node>();
			int size = currentLevel.size();
			int totalWidth = size* nodeWidth + (size -1) * nodeSpacingH;
			int xLoc = graph.getViewWidth()/2-totalWidth/2;
			for(CoverabilityTreeNode node: currentLevel){
				n = new Node(GCoverabilityTree.getNodeLabel(node),xLoc,100+(level)*nodeSpacingV);
				graph.addNode(n);
				nodes.put(node.toString(), n);
				CoverabilityTreeNode parent = node.getParent();
				String parentLabel = GCoverabilityTree.getNodeLabel(parent);
				Node source = prevNodes.get(node.getParent().toString());
				graph.addEdge(source, n,node.getTransitionFromParent().getName());
				
				if(node.getLabel()!=CoverabilityTreeNode.NODELABEL.REPEATED)
					nextLevel.addAll(node.getChildren());
				xLoc += nodeWidth + nodeSpacingH;
			}
			
			level++;
			currentLevel = nextLevel;
			prevNodes = nodes;
		}
		
		JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(graph);

        frame.add(scroll);

        
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

	}
}
