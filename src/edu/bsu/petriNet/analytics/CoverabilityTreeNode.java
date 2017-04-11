package edu.bsu.petriNet.analytics;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import java.util.ArrayList;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.Transition;

/**
 * Created by Ion Madrazo on 4/5/2017.
 */

public class CoverabilityTreeNode {

    public static  enum NODELABEL { ACTIVE, DEADEND, REPEATED  };
    private Transition transitionFromParent = null;
    private CoverabilityTreeNode parent = null;
    private ArrayList<CoverabilityTreeNode> children;
    private PetriNet petrinet;
    private NODELABEL label = NODELABEL.ACTIVE;





    public CoverabilityTreeNode(PetriNet petrinet,CoverabilityTreeNode parent,Transition transitionFromParent)
    {

        this.petrinet = petrinet.getDeepCopy();

        this.transitionFromParent = transitionFromParent;
        this.parent = parent;
        label = this.calculateNodeLabel();
        if(label == NODELABEL.ACTIVE)
        {
            changeTokensToNIfNeeded();
            //generateChildren();
        }
    }



    private  NODELABEL calculateNodeLabel()
    {

        for(CoverabilityTreeNode ancestor: this.getAllAncestors())
        {
            if(areSameState(this,ancestor))
            {
                return NODELABEL.REPEATED;
            }
        }


        if(petrinet.getFirableTransitions().size()==0) {
            return NODELABEL.DEADEND;
        }



        return NODELABEL.ACTIVE;
    }

    public  ArrayList<CoverabilityTreeNode> getAllAncestors()
    {
        ArrayList<CoverabilityTreeNode> ancestors = new ArrayList<>();
        CoverabilityTreeNode ancestor = this.getParent();
        while(ancestor!=null)
        {
            ancestors.add(ancestor);
            ancestor = ancestor.getParent();
        }

        return ancestors;
    }


    private void  generateChildren()
    {
        this.children = new ArrayList<>();
        for(Transition t: this.petrinet.getFirableTransitions())
        {
            PetriNet tmpPetrinet = petrinet.getDeepCopy();
            tmpPetrinet.fire(t.getID());
            this.children.add(new CoverabilityTreeNode(tmpPetrinet,this,t));
        }

    }



    private boolean areSameState(CoverabilityTreeNode currentNode, CoverabilityTreeNode ancestorNode)
    {
        for(int i =0;i<currentNode.getPetrinet().getPlaces().size();i++)
        {
            if(currentNode.getPetrinet().getPlaces().get(i).getNumberOfTokens()!=ancestorNode.getPetrinet().getPlaces().get(i).getNumberOfTokens())
            {
                return false;
            }
        }
        return true;
    }


    private boolean isStrictlyBiggerState(CoverabilityTreeNode currentNode, CoverabilityTreeNode ancestorNode)
    {
        if(areSameState(currentNode,ancestorNode)) return false;
        for(int i =0;i<currentNode.getPetrinet().getPlaces().size();i++)
        {
            if(currentNode.getPetrinet().getPlaces().get(i).getNumberOfTokens()<ancestorNode.getPetrinet().getPlaces().get(i).getNumberOfTokens())
            {
                return false;
            }
        }
        return true;
    }

    private void changeTokensToNIfNeeded()
    {


        for(CoverabilityTreeNode ancestorNode: this.getAllAncestors())
        {
            if(isStrictlyBiggerState(this,ancestorNode))
            {
                for(int i =0;i<this.getPetrinet().getPlaces().size();i++)
                {
                    if(this.getPetrinet().getPlaces().get(i).getNumberOfTokens()>ancestorNode.getPetrinet().getPlaces().get(i).getNumberOfTokens())
                    {
                        this.getPetrinet().getPlaces().get(i).setNumberOfTokens(Integer.MAX_VALUE);
                    }
                }
            }
        }

    }



    public PetriNet getPetrinet()
    {
        return petrinet;
    }

    public CoverabilityTreeNode getParent()
    {
        return parent;
    }

    public Transition getTransitionFromParent()
    {
        return transitionFromParent;
    }

    public NODELABEL getLabel()
    {
        return this.label;
    }
    public ArrayList<CoverabilityTreeNode> getChildren()
    {
        if(this.children==null)this.generateChildren();
        return this.children;
    }
}
