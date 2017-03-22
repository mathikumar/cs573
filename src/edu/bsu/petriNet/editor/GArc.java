package edu.bsu.petriNet.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractGraphNode;
import edu.bsu.petriNet.model.AbstractTransition;
import edu.bsu.petriNet.model.GraphElement;

public class GArc implements GElement {

	AbstractArc abstractArc;
	Integer ARROW_SIZE=16;
	private GPoint originExit, targetExit;
	
	public GArc(AbstractArc p){
		this.abstractArc = p;
	}
	
	public void draw(Graphics2D g, Map<Integer,GElement> elements){
		AbstractGraphNode origin = (AbstractGraphNode)elements.get(abstractArc.getOrigin()).getAbstractElement();
		AbstractGraphNode target = (AbstractGraphNode)elements.get(abstractArc.getTarget()).getAbstractElement();
		Vector vec = new Vector( origin.getX(), target.getX(), origin.getY(), target.getY());
		originExit = elements.get(abstractArc.getOrigin()).getExitPoint(vec);
		targetExit = elements.get(abstractArc.getTarget()).getExitPoint(vec.inv());	
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(CanvasPanel.LINE_THICKNESS));
		g.drawLine(originExit.x, originExit.y, targetExit.x, targetExit.y);
		
		Vector arrow_unit = vec.unit().mul(ARROW_SIZE);
		GPoint a = new GPoint(new Vector(targetExit).add(arrow_unit.inv()).add(arrow_unit.orth()));
		GPoint b = new GPoint(new Vector(targetExit).add(arrow_unit.inv()).add(arrow_unit.orth().inv()));
		g.fillPolygon(new int[]{targetExit.x,a.x,b.x}, new int[]{targetExit.y,a.y,b.y}, 3);
		
		GPoint textLoc = originExit.add(new Vector(targetExit).add(new Vector(originExit).inv()).mul(0.1));
		g.drawString(String.valueOf(abstractArc.getWeight()), textLoc.x, textLoc.y+4);
		
	}

	
	@Override
	public GraphElement getAbstractElement() {
		return abstractArc;
	}

	@Override
	public Boolean containsPoint(Point p) {
		if(Geometry.distance(p, new Vector(originExit), new Vector(targetExit)) < 7 && Geometry.withinRect(p, originExit, targetExit)){
			return true;
		}
		return false;
	}

	@Override
	public GPoint getExitPoint(Vector vector) {
		return null;
	}

	@Override
	public void editDialog(JFrame frame, IController controller) {
		JDialog dialog = new JDialog(frame,"Click a button", true);
		//JTextField nameField = new JTextField(this.abstractArc.getName());
		//nameField.setPreferredSize(new Dimension(100,35));
		JTextField weightField = new JTextField(this.abstractArc.getWeight());
		weightField.setPreferredSize(new Dimension(100,35));
		JPanel contentPane = new JPanel();
		contentPane.add(new JLabel("weight:"));
		contentPane.add(weightField);
		dialog.setContentPane(contentPane);
		dialog.pack();
		dialog.setVisible(true);
		try{
			int t = Integer.valueOf(weightField.getText());
			abstractArc.setWeight(t);
		}catch(NumberFormatException e){
			
		}
		controller.setArcWeight(abstractArc);
		
	}
	
	

}
