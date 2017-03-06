package edu.bsu.petriNet.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngTextDecoder;
import com.mxgraph.view.mxGraph;

import edu.bsu.petriNet.editor.BasicGraphEditor;
import edu.bsu.petriNet.editor.DefaultFileFilter;
import edu.bsu.petriNet.model.PetriNet;
import edu.bsu.petriNet.model.XmlInputOutput;
import edu.bsu.petriNet.util.PetriNetUtil;

public class FileMenuController {
	
	public static final BasicGraphEditor getEditor(ActionEvent e)
	{
		if (e.getSource() instanceof Component)
		{
			Component component = (Component) e.getSource();

			while (component != null
					&& !(component instanceof BasicGraphEditor))
			{
				component = component.getParent();
			}

			return (BasicGraphEditor) component;
		}

		return null;
	}

	@SuppressWarnings("serial")
	public static class SaveAction extends AbstractAction
	{
		protected boolean showDialog;
		protected String lastDir = null;
		public SaveAction(boolean showDialog)
		{
			this.showDialog = showDialog;
		}

		protected void saveXml(BasicGraphEditor editor, String filename) throws IOException
		{
			mxGraphComponent graphComponent = editor.getGraphComponent();
			mxGraph graph = graphComponent.getGraph();
			PetriNet petriNet = PetriNetUtil.convertMxGraphToPetriNet(graph);
			XmlInputOutput.printModel(petriNet, filename);
		}

		public void actionPerformed(ActionEvent e)
		{
			BasicGraphEditor editor = getEditor(e);
			if (editor != null)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();
				FileFilter selectedFilter = null;
				DefaultFileFilter xmlFilter = new DefaultFileFilter(".xml", "XML " + mxResources.get("file") + " (.xml)");
				String filename = null;
				boolean dialogShown = false;

				if (showDialog || editor.getCurrentFile() == null)
				{
					String wd;

					if (lastDir != null)
					{
						wd = lastDir;
					}
					else if (editor.getCurrentFile() != null)
					{
						wd = editor.getCurrentFile().getParent();
					}
					else
					{
						wd = System.getProperty("user.dir");
					}

					JFileChooser fc = new JFileChooser(wd);

					FileFilter defaultFilter = xmlFilter;
					fc.addChoosableFileFilter(defaultFilter);
					fc.setFileFilter(defaultFilter);
					int rc = fc.showDialog(null, mxResources.get("save"));
					dialogShown = true;

					if (rc != JFileChooser.APPROVE_OPTION)
					{
						return;
					}
					else
					{
						lastDir = fc.getSelectedFile().getParent();
					}

					filename = fc.getSelectedFile().getAbsolutePath();
					selectedFilter = fc.getFileFilter();

					if (selectedFilter instanceof DefaultFileFilter)
					{
						String ext = ((DefaultFileFilter) selectedFilter).getExtension();

						if (!filename.toLowerCase().endsWith(ext))
						{
							filename += ext;
						}
					}

					if (new File(filename).exists()	&& JOptionPane.showConfirmDialog(graphComponent, mxResources.get("overwriteExistingFile")) != JOptionPane.YES_OPTION)
					{
						return;
					}
				}
				else
				{
					filename = editor.getCurrentFile().getAbsolutePath();
				}

				try
				{
					String ext = filename.substring(filename.lastIndexOf('.') + 1);

						if (selectedFilter == xmlFilter || (editor.getCurrentFile() != null && ext.equalsIgnoreCase("xml") && !dialogShown)){
							saveXml(editor, filename);
						}
				}
				catch (Throwable ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(graphComponent,ex.toString(), mxResources.get("error"),JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@SuppressWarnings("serial")
	public static class OpenAction extends AbstractAction
	{

		protected String lastDir;
		protected void resetEditor(BasicGraphEditor editor){
			editor.setModified(false);
			editor.getUndoManager().clear();
			editor.getGraphComponent().zoomAndCenter();
		}

		protected void openXml(BasicGraphEditor editor, File file) throws IOException{
			PetriNet petriNet = XmlInputOutput.readModel(file.toString());
			if (petriNet != null)
			{
				mxGraph graph = PetriNetUtil.convertPetriNetToMxGraph(petriNet);

				if (graph != null)
				{
					editor.getGraphComponent().setGraph(graph);
					
					
					
					editor.setCurrentFile(file);
					resetEditor(editor);
					
					
					return;
				}
			}
			JOptionPane.showMessageDialog(editor,mxResources.get("imageContainsNoDiagramData"));
		}

		public void actionPerformed(ActionEvent e){
			BasicGraphEditor editor = getEditor(e);
			if (editor != null){
				if (!editor.isModified() || JOptionPane.showConfirmDialog(editor,mxResources.get("loseChanges")) == JOptionPane.YES_OPTION){
					mxGraph graph = editor.getGraphComponent().getGraph();
					if (graph != null){
						String wd = (lastDir != null) ? lastDir : System.getProperty("user.dir");
						JFileChooser fc = new JFileChooser(wd);
						DefaultFileFilter defaultFilter = new DefaultFileFilter(".xml", mxResources.get("allSupportedFormats") + " (.xml)"){
							public boolean accept(File file){
								String lcase = file.getName().toLowerCase();
								return super.accept(file) || lcase.endsWith(".xml");
							}
						};
						fc.addChoosableFileFilter(defaultFilter);
						fc.setFileFilter(defaultFilter);
						int rc = fc.showDialog(null, mxResources.get("openFile"));
						if (rc == JFileChooser.APPROVE_OPTION){
							lastDir = fc.getSelectedFile().getParent();
							try{
								if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xml")){
									openXml(editor, fc.getSelectedFile());
								}
							} catch (IOException ex) {
								ex.printStackTrace();
								JOptionPane.showMessageDialog(editor.getGraphComponent(),ex.toString(),	mxResources.get("error"),JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		}
	}
}
