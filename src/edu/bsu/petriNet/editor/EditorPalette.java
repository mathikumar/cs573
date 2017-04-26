/**
 * Copyright (c) 2007-2012, JGraph Ltd
 */
package edu.bsu.petriNet.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.TransferHandler;

import edu.bsu.petriNet.controller.IController;


public class EditorPalette extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 7771113885935187066L;
	protected JLabel selectedEntry = null;
	protected Color gradientColor = new Color(117, 195, 173);
	JToggleButton newPlaceButton;
	JToggleButton newArcButton;
	JToggleButton newTransitionButton;
	JToggleButton selectButton;
	CanvasPanel canvasPanel;
	IController controller;
	
	@SuppressWarnings("serial")
	public EditorPalette(ButtonGroup designChoicesGroup, CanvasPanel canvasPanel, IController controller)
	{
		setBackground(new Color(149, 230, 190));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.canvasPanel = canvasPanel;
		this.controller = controller;
		
		add(new JLabel("Design"));
		
			
		newPlaceButton = new JToggleButton();
		try {
		    Image img = ImageIO.read(getClass().getResource("../resources/place.gif"));
		    newPlaceButton.setIcon(new ImageIcon(img));
	    } catch (Exception ex) {
	    	newPlaceButton.setText("Place");
	    }
		designChoicesGroup.add(newPlaceButton);
		newPlaceButton.addActionListener(this);
		add(newPlaceButton);
		
		newArcButton = new JToggleButton();
		try {
		    Image img = ImageIO.read(getClass().getResource("../resources/arc.gif"));
		    newArcButton.setIcon(new ImageIcon(img));
	    } catch (Exception ex) {
	    	newArcButton.setText("Arc");
	    }
		designChoicesGroup.add(newArcButton);
		newArcButton.addActionListener(this);
		add(newArcButton);
		
		newTransitionButton = new JToggleButton();
		try {
		    Image img = ImageIO.read(getClass().getResource("../resources/transition.gif"));
		    newTransitionButton.setIcon(new ImageIcon(img));
	    } catch (Exception ex) {
	    	newTransitionButton.setText("Transition");
	    }
		designChoicesGroup.add(newTransitionButton);
		newTransitionButton.addActionListener(this);
		add(newTransitionButton);
		
		selectButton = new JToggleButton("Select");
		designChoicesGroup.add(selectButton);
		selectButton.addActionListener(this);
		add(selectButton);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.undoSimulation();
		if(arg0.getSource() == newPlaceButton){
			this.canvasPanel.setNewPlaceState();
		} else if(arg0.getSource() == newArcButton){
			this.canvasPanel.setNewArcState();
		} else if(arg0.getSource() == newTransitionButton){
			this.canvasPanel.setNewTransitionState();
		} else if(arg0.getSource() == selectButton){
			this.canvasPanel.setSelectState();
		}
		
	}


}
