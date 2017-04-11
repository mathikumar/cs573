package edu.bsu.petriNet.analytics;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;

/**
 * Created by Ion Madrazo on 4/5/2017.
 */

public class CoverabilityTree {


    public static void main(String[] args)
    {
        PetriNet petrinet = XmlInputOutput.readModel("AsyncCommunication.xml");
        CoverabilityTreeNode node = new CoverabilityTreeNode(petrinet);
        System.out.println("Finished");
    }

}
