package edu.bsu.petriNet.analytics;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.Place;
import edu.bsu.petriNet.util.PropertiesLoader;

public class GCoverabilityTree extends JPanel {
    private static final long serialVersionUID = 1L;
	private static int transitionNameOffset;
	private static int transitionNameAlternateOffset;
	private static int width;
	private static int height;
	private static int topPadding;
	private static int nodeSpacingH;
	private static int nodeSpacingV;
	private static int letterWidth;
    private static int canvasWidth ;
    private static int canvasHeight;
    private ArrayList<Node> nodes;
    private ArrayList<edge> edges;
    private CoverabilityTree tree; 

    public GCoverabilityTree() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        loadStyleConfigs();
    }
    
    private void loadStyleConfigs(){
    	transitionNameOffset = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("transitionNameOffset"));
    	transitionNameAlternateOffset = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("transitionNameAlternateOffset"));
    	width = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("width"));
    	height = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("height"));
    	topPadding = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("topPadding"));
    	nodeSpacingH = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("nodeSpacingH"));
    	nodeSpacingV = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("nodeSpacingV"));
    	letterWidth = Integer.parseInt(PropertiesLoader.getProperties("config").getProperty("letterWidth"));
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvasWidth=(int)screenSize.getWidth();
        canvasHeight=(int)screenSize.getHeight();
    }
    
    public GCoverabilityTree(String name) { 
    	this();
		JButton rechability = new JButton();
        rechability.setText("Check rechability");
        this.add(rechability);
        rechability.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               String marking = JOptionPane.showInputDialog("Enter the marking","marking");
               String message = "The given marking is ";
               
               ArrayList<Integer> tokenList = new ArrayList<Integer>();
               if(isMarkingValid(marking)){
            	   for(int i = 0;i<marking.length();i++){
            		   tokenList.add(Integer.parseInt(String.valueOf(marking.charAt(i))));
            	   }
            	   if(tree.isReachable(tokenList)){
                	   message += "reachable";
                   }else{
                	   message += "not reachable";
                   }
                   JOptionPane.showMessageDialog(null,message);
               } else{
                   JOptionPane.showMessageDialog(null,"Invalid marking");
               }
            }
        });

        
        JButton bounded = new JButton();
        bounded.setText("Is bounded?");
        this.add(bounded);
        bounded.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String message = "The petrinet is " + (tree.isBounded()?"":"not ") + "bounded";
            	JOptionPane.showMessageDialog(null,message);
            }
        });
        
        JButton dead = new JButton();
        dead.setText("has dead transitions?");
        this.add(dead);
        dead.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String message = "The petrinet has ";
            	if(tree.hasDeadEnd()){
            		message += "deadend";
            	} else{
            		message += "no dead transitions";
            	}
               JOptionPane.showMessageDialog(null,message);
            }
        });

        
    }

    public void addNode(String label, int x, int y,boolean dead) { 
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
        super.paint(g);

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
			if(x2 < (e.target.x - this.getNodeWidth(g, nodes.get(0).label)/2)){
				x2 = (e.target.x - this.getNodeWidth(g, nodes.get(0).label)/2);
				int y2 = (int)(e.source.y + m * (x2 - e.source.x));
				drawArrowHead((Graphics2D)g,new Point((int)x2, y2),p1);
			}else if(x2 > (e.target.x + this.getNodeWidth(g, nodes.get(0).label)/2)){
				x2 = (e.target.x + this.getNodeWidth(g, nodes.get(0).label)/2);
				int y2 = (int)(e.source.y + m * (x2 - e.source.x));
				drawArrowHead((Graphics2D)g,new Point((int)x2, y2),p1);	
			}else{
				drawArrowHead((Graphics2D)g,new Point((int)x2, e.target.y-nodeHeight/2),p1);
			}
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
  
  private void drawArrowHead(Graphics2D g2, Point tip, Point tail){
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
  
  public void makeTree(PetriNet petrinet){
	    this.tree = new CoverabilityTree(petrinet);
		List<CoverabilityTreeNode> currentLevel = new ArrayList<CoverabilityTreeNode>();
		ArrayList<CoverabilityTreeNode> nextLevel ;
		Map<String,Node> nodes;
		Map<String,Node> prevNodes = new HashMap<String,Node>();
		int level = 0;
		int nodeWidth = tree.getRoot().getPetrinet().getPlaces().size()*letterWidth;
		canvasWidth = getRequiredCanvasWidth();
		setHScale(canvasWidth);
		Node rootNode = new Node(GCoverabilityTree.getNodeLabel(tree.getRoot()),this.getViewWidth()/2,topPadding);
		this.addNode(rootNode);
		currentLevel.addAll(tree.getRoot().getChildren());
		prevNodes.put(tree.getRoot().toString(),rootNode);
		level++;
		while(!currentLevel.isEmpty()){
			nextLevel = new ArrayList<CoverabilityTreeNode>();
			Node n;
			nodes = new HashMap<String,Node>();
			int size = currentLevel.size();
			int totalWidth = (size-1)* nodeWidth + (size -1) * nodeSpacingH;
			int xLoc = this.getViewWidth()/2-totalWidth/2;
			
			for(CoverabilityTreeNode node: currentLevel){
				n = new Node(GCoverabilityTree.getNodeLabel(node),xLoc,topPadding+(level)*nodeSpacingV);
				if(n.y+30 > canvasHeight){
					setVScale(n.y+30);
				}
				this.addNode(n);
				nodes.put(node.toString(), n);
				Node source = prevNodes.get(node.getParent().toString());
				
				this.addEdge(source, n,node.getTransitionFromParent().getName());
				if(node.getLabel()!=CoverabilityTreeNode.NODELABEL.REPEATED)
					nextLevel.addAll(node.getChildren());
				xLoc += nodeWidth + nodeSpacingH;
			}
			level++;
			currentLevel = nextLevel;
			prevNodes = nodes;
		}
		
  }
  
  private boolean isMarkingValid(String marking){
	  if(tree.getRoot().getPetrinet().getPlaces().size() == marking.length()){
		  try{
			  Integer.parseInt(marking);
		  }catch(Exception e){
			  return false;
		  }
		  return true;
	  } else{
		  return false;
	  }
	  
  }
  
  public int getRequiredCanvasWidth(){
	  List<CoverabilityTreeNode> currentLevel = new ArrayList<CoverabilityTreeNode>();
	  ArrayList<CoverabilityTreeNode> nextLevel ;
	  currentLevel.add(tree.getRoot());
	  int maxWidth = canvasWidth;
	  int nodeWidth = tree.getRoot().getPetrinet().getPlaces().size()*letterWidth;
		
	  while(!currentLevel.isEmpty()){
		  nextLevel = new ArrayList<CoverabilityTreeNode>();
		  int size = currentLevel.size();
			
			int totalWidth = (size-1)* nodeWidth + (size -1) * nodeSpacingH;
			if(totalWidth + 150 > maxWidth){
				maxWidth = totalWidth + 150;
			}
			for(CoverabilityTreeNode node: currentLevel){
				if(node.getLabel()!=CoverabilityTreeNode.NODELABEL.REPEATED)
					nextLevel.addAll(node.getChildren());
			}
			currentLevel = nextLevel;
		}
	  if(maxWidth>canvasWidth){
		  nodeSpacingH-=15;
		  nodeSpacingV+=40;
	  }
	  return maxWidth;
  }
  
  public void setHScale(int value) {
	     this.setPreferredSize(new Dimension(value, canvasHeight));
	     canvasWidth=value;
	     this.revalidate();
 }
  
  public void setVScale(int value) {
	     this.setPreferredSize(new Dimension(canvasWidth,value));   
	     canvasHeight=value;
	     this.revalidate();
  }
}

