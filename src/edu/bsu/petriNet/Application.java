/**
 * Copyright (c) 2006-2012, JGraph Ltd */
package edu.bsu.petriNet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import edu.bsu.petriNet.controller.BaseController;
import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.editor.BasicGraphEditorPanel;
import edu.bsu.petriNet.editor.EditorMenuBar;
import edu.bsu.petriNet.editor.EditorPalette;

public class Application
{

	/**
	 * Holds the shared number formatter.
	 * 
	 * @see NumberFormat#getInstance()
	 */
	public static final NumberFormat numberFormat = NumberFormat.getInstance();

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		final IController controller = new BaseController();

		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				BasicGraphEditorPanel mainPanel = new BasicGraphEditorPanel(controller);
				frame.setJMenuBar(new EditorMenuBar(mainPanel,controller));
				frame.getContentPane().add(mainPanel);
				//UIManager.setLookAndFeel(clazz);
				SwingUtilities.updateComponentTreeUI(frame);
				frame.setTitle("PetriNetz");
				frame.setPreferredSize(new Dimension(800,480));
				frame.pack();
				frame.setVisible(true);
			}
		});
		
	}
}
