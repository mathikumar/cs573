package edu.bsu.petriNet.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import edu.bsu.petriNet.controller.BaseController;
import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;

public class BasicGraphEditorPanel extends JPanel
{

	/**
	 * The PetriNet currently being edited.
	 */
	private IController petriNetController;


	private JPanel designPanel;
	private JPanel simulatePanel;
	protected JTabbedPane displayPane;
    private CanvasPanel canvasPanel;
	protected String appTitle;
	protected JLabel statusBar;
	protected File currentFile;
	protected boolean modified = false;

	
	public BasicGraphEditorPanel(IController controller)
	{
		petriNetController = controller;
		// Creates the library pane that contains the tabs with the palettes
		setLayout(new BorderLayout());
		
		add(new JLabel("<html>Place: Left click to place.  Right click to modify. Right drag to move.<br>"
					+ "Transition: Left click to place.  Right click to modify. Right drag to move.<br>"
				    + "Arc: Left drag to create. Right click to modify.<br>"
				    + "Fire: Left click firable transition to fire.</html>"), BorderLayout.NORTH);
		
		displayPane = new JTabbedPane();
		add(displayPane, BorderLayout.CENTER);
		canvasPanel = new CanvasPanel(this, petriNetController);
		JScrollPane canvasScroll = new JScrollPane(canvasPanel);
		displayPane.addTab("PetriNet", null, canvasScroll,"The PetriNet");

		
		ButtonGroup modesGroup = new ButtonGroup();
		designPanel = new EditorPalette(modesGroup, canvasPanel);
		simulatePanel = new SimulationPalette(modesGroup, canvasPanel, controller);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,designPanel, simulatePanel);
		splitPane.setDividerLocation(120);
		splitPane.setResizeWeight(1);
		splitPane.setDividerSize(6);
		splitPane.setBorder(null);
		splitPane.setPreferredSize(new Dimension(100,480));
		this.add(splitPane, BorderLayout.WEST);
		
		
		

		statusBar = createStatusBar();
		add(statusBar, BorderLayout.SOUTH);
		
		// Create controller with an empty (new) Petri net
		petriNetController.registerStateListener(canvasPanel);
	}
	
	
	/**
	 * Save the Petri net being edited under the specified filename.
	 * 
	 * @param filename
	 * @throws java.io.IOException
	 */
	public void saveXml(String filename) throws java.io.IOException {
		petriNetController.save(filename);
	}




	/**
	 * 
	 */
	protected JLabel createStatusBar()
	{
		JLabel statusBar = new JLabel();
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

		return statusBar;
	}



	/**
	 * 
	 */
	protected void mouseWheelMoved(MouseWheelEvent e)
	{
		if (e.getWheelRotation() < 0)
		{
		}
		else
		{
		}

	}


	/**
	 * 
	protected void showGraphPopupMenu(MouseEvent e)
	{
		Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
				graphComponent);
		EditorPopupMenu menu = new EditorPopupMenu(BasicGraphEditorPanel.this);
		menu.show(graphComponent, pt.x, pt.y);

		e.consume();
	}
	 */



	/**
	 * 
	 */
	public void setCurrentFile(File file)
	{
		currentFile = file;
	}

	/**
	 * 
	 */
	public File getCurrentFile()
	{
		return currentFile;
	}

	/**
	 * 
	 * @param modified
	 */
	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	/**
	 * 
	 * @return whether or not the current graph has been modified
	 */
	public boolean isModified()
	{
		return modified;
	}

	










}
