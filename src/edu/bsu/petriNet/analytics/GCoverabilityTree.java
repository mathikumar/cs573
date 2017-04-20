package edu.bsu.petriNet.analytics;
/* Simple graph drawing class
Bert Huang
COMS 3137 Data Structures and Algorithms, Spring 2009

This class is really elementary, but lets you draw 
reasonably nice graphs/trees/diagrams. Feel free to 
improve upon it!
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.Place;

public class GCoverabilityTree extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int transitionNameOffset = 35;
	private static final int transitionNameAlternateOffset = 15;
	
	private int width;
    private int height;
    private static int canvasWidth ;
    private static int canvasHeight;
    private ArrayList<Node> nodes;
    private ArrayList<edge> edges;

    public GCoverabilityTree() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        width = 30;// To be moved to config
        height = 30;
        setBorder(new LineBorder(Color.RED));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvasWidth=(int)screenSize.getWidth();
        canvasHeight=(int)screenSize.getHeight();
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        

    }

    public GCoverabilityTree(String name) { 
    	this();
    }

    
    public void addNode(String label, int x, int y) { 
		nodes.add(new Node(label,x,y));
    	this.repaint();
    }
    
    public void addNode(Node n){
    	nodes.add(n);
    	this.repaint();
    }
    
    public void addEdge(Node source, Node target,String name) {
		edges.add(new edge(source,target,name));
    	this.repaint();
    }
    public void paint(Graphics g) { 
    	FontMetrics f = g.getFontMetrics();
		int nodeHeight = Math.max(height, f.getHeight());
		g.setColor(Color.black);
		boolean flag = false;
		int yOffset;
		for (edge e : edges) {
				yOffset = transitionNameOffset;
			if(flag){
				yOffset += transitionNameAlternateOffset;
			}
			Point p1 = new Point(e.source.x,e.source.y);
			Point p2 = new Point(e.target.x, e.target.y);
			float m = getSlope(p1,p2);
			float x = (e.source.y+yOffset-e.source.y)/m+e.source.x;
			
			float x2 = (e.target.y-nodeHeight/2-e.source.y)/(m)+e.source.x;
			Font currentFont = g.getFont();
			Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.8F);
			g.setFont(newFont);
			if(e.source.x - e.target.x > 0){
				x-=(g.getFontMetrics().stringWidth(e.name)+6);
			}
			g.drawString(e.name, (int)x+3, e.source.y+yOffset);
			g.setFont(currentFont);
			
			g.drawLine(e.source.x, e.source.y, e.target.x, e.target.y);
		    drawArrowHead((Graphics2D)g,new Point((int)x2, e.target.y-nodeHeight/2),p1);
		    flag=!flag;
		}
	
		for (Node n : nodes) {
		    int nodeWidth = getNodeWidth(g, n.label);
		    g.setColor(Color.white);
		    g.fillRect(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);
		    g.setColor(Color.black);
		    g.drawRect(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);
		    g.drawString(n.label, n.x-f.stringWidth(n.label)/2, n.y+f.getHeight()/2);
		}
    }
    
    
    
   static class Node {
    	int x;
    	int y;
    	String label;
	
    	public Node(String label, int x, int y) {
    		this.x = x;
    		this.y = y;
    		this.label = label;
		}
    }
    
   static class edge {
    	Node source;
    	Node target;
    	String name;
    	public edge(Node source, Node target,String name) {
    		this.source = source;
    		this.target = target;
    		this.name = name;
    	}
    }
   
   public static String getNodeLabel(CoverabilityTreeNode node){
	   PetriNet net = node.getPetrinet();
	   String label = "";
	   for(Place p:net.getPlaces()){
		   label+=p.getNumberOfTokens();
	   }
	   return label;
   }
   
  private int getNodeWidth(Graphics g,String label){
	   FontMetrics f = g.getFontMetrics();
	   return Math.max(width, f.stringWidth(label)+width/2);
   }
  
  private void drawArrowHead(Graphics2D g2, Point tip, Point tail)
  {
      double dy = tip.y - tail.y;
      double dx = tip.x - tail.x;
      double theta = Math.atan2(dy, dx);
      double phi = Math.toRadians(30);
      double x, y, rho = theta + phi;
      double arrowLength=10;
      for(int j = 0; j < 2; j++){
          x = tip.x - arrowLength * Math.cos(rho);
          y = tip.y - arrowLength * Math.sin(rho);
          g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
          rho = theta - phi;
      }
  }
  
  private static float getSlope(Point p1, Point p2){
	  return (float) (p2.y - p1.y) / (p2.x - p1.x);
  }
  
  public int getViewWidth(){
	  return canvasWidth;
  }
  
  public int getViewHeight(){
	  return canvasHeight;
  }
}

