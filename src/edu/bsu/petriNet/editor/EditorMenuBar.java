package edu.bsu.petriNet.editor;

import java.awt.Dimension;
import java.io.File;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;


import edu.bsu.petriNet.controller.IController;



public class EditorMenuBar extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060203894740766714L;
	File currentFile;

	public enum AnalyzeType
	{
		IS_CONNECTED, IS_SIMPLE, IS_CYCLIC_DIRECTED, IS_CYCLIC_UNDIRECTED, COMPLEMENTARY, REGULARITY, COMPONENTS, MAKE_CONNECTED, MAKE_SIMPLE, IS_TREE, ONE_SPANNING_TREE, IS_DIRECTED, GET_CUT_VERTEXES, GET_CUT_EDGES, GET_SOURCES, GET_SINKS, PLANARITY, IS_BICONNECTED, GET_BICONNECTED, SPANNING_TREE, FLOYD_ROY_WARSHALL
	}

	public EditorMenuBar(final BasicGraphEditorPanel editor, final IController controller)
	{

		JMenu menu = null;
		JMenu submenu = null;

		// Creates the file menu
		menu = add(new JMenu("File"));
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	controller.newNet();}
		});
		menu.add(newMenuItem);
		
		JMenuItem loadItem = new JMenuItem("Open");
		loadItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            java.io.File file = fc.getSelectedFile();
		            try {
						controller.load(file.getCanonicalPath());
						editor.setCurrentFile(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		        
		        } 
			}
		});
		menu.add(loadItem);
		
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				saveAction(editor, controller); 
			}
		});
		
		menu.add(saveItem);
		
		JMenuItem saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				saveAction(editor, controller);
			}
		});
		menu.add(saveAsItem);
		menu.addSeparator();
		submenu = new JMenu("Examples"); 
		
		JMenuItem cpItem = new JMenuItem("Communication Protocol");
		cpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				
		            String file = "Examples/AsyncCommunication-Finished.xml";
		            controller.load(file);		        
		        
			}
		});
		
		submenu.add(cpItem);
		
		JMenuItem dpItem = new JMenuItem("Dining Philosophors");
		dpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				
		            String file = "Examples/AsyncDiningPhilosophers-Finished.xml";
		            controller.load(file);		        
		        
			}
		});
		submenu.add(dpItem);
		
		JMenuItem vmItem = new JMenuItem("Vending Machine");
		vmItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	

		            String file = "Examples/AsyncVendingMachine-Finished.xml";
		            controller.load(file);		        
		        
			}
		});
		submenu.add(vmItem);
		
		JMenuItem rwItem = new JMenuItem("Readers & Writers");
		rwItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				
		            String file = "Examples/AsyncReadersWriter-Finished.xml";
		            controller.load(file);		        
		        
			}
		});
		submenu.add(rwItem);
		
		menu.add(submenu);
		menu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				
				/*if(editor.isModified()){
					int rtnChoice = showConfirmDialog("Save changes to file?", "Confirm Save");
					
					if (rtnChoice == JOptionPane.CANCEL_OPTION)
		    			return;
		    		else
		    			if (rtnChoice == JOptionPane.YES_OPTION)
		    				
		    				saveAction(editor, controller);
				}*/
				System.exit(0);
			}
		});
		menu.add(exitItem);
		
		// Creates the edit menu
		menu = add(new JMenu("Edit"));
		JMenuItem undoItem = new JMenuItem("Undo");
		undoItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				controller.undo();	        
		        
			}
		});
		menu.add(undoItem);
		
		JMenuItem redoItem = new JMenuItem("Redo");
		redoItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				
		            controller.redo();	        
		        
			}
		});
		menu.add(redoItem);
		
		menu.addSeparator();
		
		JMenuItem cutItem = new JMenuItem("Cut");
		cutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	editor.cut();}
		});
		menu.add(cutItem);
		
		JMenuItem copyItem = new JMenuItem("Copy");
		copyItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	editor.copy();}
		});
		menu.add(copyItem);
		
		JMenuItem pasteItem = new JMenuItem("Paste");
		pasteItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	editor.paste();}
		});
		menu.add(pasteItem);
		
		menu.addSeparator();
		
		JMenuItem deleteItem = new JMenuItem("Delete (NYI)");
		menu.add(deleteItem);
		
		// Creates the analysis menu
		menu = add(new JMenu("Analysis"));
		JMenuItem boundedItem = new JMenuItem("Check Boundedness");
		boundedItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
//				currentFile = editor.getCurrentFile();
//				if(currentFile != null){
//					editor.checkBoundedness(currentFile);
//				}
				editor.checkBoundedness();
			}
		});
		menu.add(boundedItem);
		
		JMenuItem deadLockItem = new JMenuItem("Check DeadLock");
		deadLockItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
//				currentFile = editor.getCurrentFile();
//				if(currentFile != null){
//					editor.checkDeadLock(currentFile);
//				}
				editor.checkDeadLock();
			}
		});
		menu.add(deadLockItem);
		
		JMenuItem reachabilityItem = new JMenuItem("Check Reachability");
		reachabilityItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
//				currentFile = editor.getCurrentFile();
//				if(currentFile != null){
//					editor.checkReachability(currentFile);
//				}
				editor.checkReachability();
			}
		});
		menu.add(reachabilityItem);
		
		JMenuItem coverabilityItem = new JMenuItem("Show Coverability Tree");
		coverabilityItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
//				currentFile = editor.getCurrentFile();
				
				System.out.println(currentFile);
//				if(currentFile != null){
//					editor.showCTree(currentFile);
//				}
				editor.showCTree();
			}
		});
		menu.add(coverabilityItem);
		
	}
	
	public int showConfirmDialog(String message, String title){
		return JOptionPane.showConfirmDialog(null,
				message, title,
				JOptionPane.YES_NO_OPTION);
	}
	
	public void saveAction(final BasicGraphEditorPanel editor, final IController controller){
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fc.getSelectedFile();
            try {
				controller.save(file.getCanonicalPath());
				editor.setCurrentFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		        
        } 
	}
};
