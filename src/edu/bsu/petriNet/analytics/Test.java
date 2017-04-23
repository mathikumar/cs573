package edu.bsu.petriNet.analytics;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;

public class Test {
	public static void main(String[] args){
		GCoverabilityTree graph = new GCoverabilityTree("Coverability Tree");
    	PetriNet petrinet = XmlInputOutput.readModel("AsyncCommunication.xml");
    	//PetriNet petrinet = XmlInputOutput.readModel("AsyncReadersWriter.xml");
    	//PetriNet petrinet = XmlInputOutput.readModel("AsyncVendingMachine.xml");
    	//PetriNet petrinet = XmlInputOutput.readModel("AsyncDiningPhilosophers.xml");
	 	graph.makeTree(petrinet);
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
