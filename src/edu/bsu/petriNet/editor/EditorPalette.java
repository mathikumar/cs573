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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.TransferHandler;


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
	
	@SuppressWarnings("serial")
	public EditorPalette(ButtonGroup designChoicesGroup, CanvasPanel canvasPanel)
	{
		setBackground(new Color(149, 230, 190));
		setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		this.canvasPanel = canvasPanel;
		
		add(new JLabel("Design"));
		
			
		newPlaceButton = new JToggleButton("Place"); 
		designChoicesGroup.add(newPlaceButton);
		newPlaceButton.addActionListener(this);
		add(newPlaceButton);
		
		newArcButton = new JToggleButton("Arc");
		designChoicesGroup.add(newArcButton);
		newArcButton.addActionListener(this);
		add(newArcButton);
		
		newTransitionButton = new JToggleButton("Transition");
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
