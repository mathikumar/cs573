package edu.bsu.petriNet.analytics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;

/**
 * Created by Ion Madrazo on 4/5/2017.
 */

public class CoverabilityTree {

    private CoverabilityTreeNode root;

    public CoverabilityTree(PetriNet p)
    {
        root = new CoverabilityTreeNode(p , null, null);

        Queue<CoverabilityTreeNode> queue = new LinkedList<>();

        queue.add(root);
    int n = 0;
        while(!queue.isEmpty())
        {
            //System.out.print("Iteration"+(n++)+"queue:"+queue.size()+"\n");
           CoverabilityTreeNode current =  queue.poll();

            if(current.getLabel()== CoverabilityTreeNode.NODELABEL.ACTIVE)
            {
                for(CoverabilityTreeNode child : current.getChildren())
                {
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



    public CoverabilityTreeNode getRoot()
    {
        return root;
    }
    public static void main(String[] args)
    {

        PetriNet petrinet = XmlInputOutput.readModel("AsyncCommunication.xml");
        CoverabilityTree tree = new CoverabilityTree(petrinet);
        System.out.println("Finished");
    }

}
