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
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.TransferHandler;

import edu.bsu.petriNet.controller.IController;


public class SimulationPalette extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 7771113885935187066L;
	protected JLabel selectedEntry = null;
	protected Color gradientColor = new Color(117, 195, 173);
	CanvasPanel canvasPanel;
	IController controller;
	JToggleButton fireButton;
	JButton fireRandomButton,fireNButton;
	JTextField nField, delayField;

	
	@SuppressWarnings("serial")
	public SimulationPalette(ButtonGroup designChoicesGroup, CanvasPanel canvasPanel, IController controller)
	{
		setBackground(new Color(230, 180, 190));
		setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		this.canvasPanel = canvasPanel;
		this.controller = controller;
		
		add(new JLabel("Simulate"));
		
		
		fireButton = new JToggleButton("Fire"); 
		designChoicesGroup.add(fireButton);
		fireButton.addActionListener(this);
		add(fireButton);
		
		fireRandomButton = new JButton("Fire@Random");
		//designChoicesGroup.add(fireRandomButton);
		fireRandomButton.addActionListener(this);
		add(fireRandomButton);
		
		fireNButton = new JButton("Fire@N");
		//designChoicesGroup.add(fireNButton);
		fireNButton.addActionListener(this);
		add(fireNButton);
		
		nField = new JTextField();
		nField.setEditable(true);
		nField.setText("5");
		add(nField);
		
		delayField = new JTextField();
		delayField.setEditable(true);
		delayField.setText("1");
		add(delayField);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == fireButton){
			canvasPanel.setFireBehavior();
		}
		if(arg0.getSource() == fireRandomButton){
			controller.simulate(1, 0);
		}
		if(arg0.getSource() == fireNButton){
			try{
				Integer n = Integer.valueOf(nField.getText());
				controller.simulate(n, 1000*Integer.valueOf(delayField.getText()));
			}catch(NumberFormatException e){
				nField.setText("1");
			}
		}
		
	}


}
