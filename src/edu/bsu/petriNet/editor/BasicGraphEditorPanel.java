package edu.bsu.petriNet.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import java.util.ArrayList;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.bsu.petriNet.analytics.CoverabilityTree;
import edu.bsu.petriNet.analytics.CoverabilityTreeNode;
import edu.bsu.petriNet.analytics.GCoverabilityTree;
import edu.bsu.petriNet.controller.BaseController;
import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;
import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;

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
    private CanvasPanel canvasPanel1;
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
		displayPane.addTab("Coverability Tree", null, null, "The Coverability Tree");
		
		
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				//System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
				if(index == 1){
					//displayPane.remove(1);
//					File xmlFileName = getCurrentFile();
//					if(xmlFileName != null){
//						showCTree(xmlFileName);
//					}
					showCTree();
				}
			}
		};
		
		displayPane.addChangeListener(changeListener);

		
		ButtonGroup modesGroup = new ButtonGroup();
		designPanel = new EditorPalette(modesGroup, canvasPanel, controller);
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

	public void cut() {
		canvasPanel.cut();
	}
	public void copy() {
		canvasPanel.copy();
	}
	public void paste() {
		canvasPanel.paste();
	}
	
	public void showCTree(){
		
		JFrame frame = new JFrame();
		GCoverabilityTree graph = new GCoverabilityTree("Coverability Tree");
    	//PetriNet petrinet = getPetriNet(xmlFile.getAbsolutePath());
		CoverabilityTree tree = petriNetController.getCoverabilityTree();
		
		graph.makeTree(tree);
		//frame.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(graph);
       //frame.add(scroll);
		//displayPane.addTab("Coverability Tree", null, scroll, "The Coverability Tree");
        displayPane.setComponentAt(1, scroll);
		displayPane.setSelectedIndex(1);
	}
	
	public PetriNet getPetriNet(String sourcePath){
		
		return XmlInputOutput.readModel(sourcePath);
		
	}
	
	public void checkDeadLock(){
		//PetriNet petrinet = petriNetController.;
		//CoverabilityTree tree = new CoverabilityTree(petrinet);
		CoverabilityTree tree = petriNetController.getCoverabilityTree();
		
		String message = "The petrinet has ";
    	if(tree.hasDeadEnd()){
    		message += "deadend";
    	} else{
    		message += "no dead transitions";
    	}
       JOptionPane.showMessageDialog(null,message);
	}
	
	public void checkBoundedness(){
		//PetriNet petrinet = petriNetController.;
		//CoverabilityTree tree = new CoverabilityTree(petrinet);
		CoverabilityTree tree = petriNetController.getCoverabilityTree();
				
		
		String message = "The petrinet is ";
    	if(tree.isBounded()){
    		message += "bounded";
    	} else{
    		message += "not bounded";
    	}
       JOptionPane.showMessageDialog(null,message);
	}
	
	public void checkReachability(){
		//PetriNet petrinet = petriNetController.;
		//CoverabilityTree tree = new CoverabilityTree(petrinet);
		CoverabilityTree tree = petriNetController.getCoverabilityTree();
				
		ArrayList<Integer> tokenList = new ArrayList<Integer>();
		GCoverabilityTree graph = new GCoverabilityTree("Coverability Tree");
		String marking = graph.getNodeLabel(tree.getRoot());
		//System.out.println(marking);
		//GCoverabilityTree.getNodeLabel(tree.getRoot()
		//tokenList.add(tree.getRoot());
	
 	   for(int i = 0;i<marking.length();i++){
 		   tokenList.add(Integer.parseInt(String.valueOf(marking.charAt(i))));
 	   }
		
 	  String message = "The petrinet is ";
 	 
 	  if(tree.isReachable(tokenList)){
 		  message += "reachable";
      }else{
    	  message += "not reachable";
      }
      JOptionPane.showMessageDialog(null,message);
	}


}
