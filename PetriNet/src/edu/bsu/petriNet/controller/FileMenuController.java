package edu.bsu.petriNet.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;

import edu.bsu.petriNet.editor.BasicGraphEditor;
import edu.bsu.petriNet.editor.DefaultFileFilter;
import edu.bsu.petriNet.model.PetriNet;
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

	/**
	 *
	 */
	@SuppressWarnings("serial")
	public static class SaveAction extends AbstractAction
	{
		/**
		 * 
		 */
		protected boolean showDialog;

		/**
		 * 
		 */
		protected String lastDir = null;

		/**
		 * 
		 */
		public SaveAction(boolean showDialog)
		{
			this.showDialog = showDialog;
		}

		/**
		 * Saves XML format.
		 */

		protected void saveXml(BasicGraphEditor editor, String filename) throws IOException
		{
			mxGraphComponent graphComponent = editor.getGraphComponent();
			mxGraph graph = graphComponent.getGraph();
			
			// Creates the URL-encoded XML data
			mxCodec codec = new mxCodec();
			String xml = mxXmlUtils.getXml(codec.encode(graph.getModel()));
			FileWriter fWriter = null;
			PetriNet petriNet = PetriNetUtil.convertMxGraphToPetriNet(graph);
			BufferedWriter bWriter = null;
			
			try
			{
				 fWriter= new FileWriter(new File(
						filename));
				
				bWriter = new BufferedWriter(fWriter);
				bWriter.write(xml);
					
			}
			finally
			{
				
				bWriter.close();
				fWriter.close();
			}
		}

		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			BasicGraphEditor editor = getEditor(e);

			if (editor != null)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();
				mxGraph graph = graphComponent.getGraph();
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

					// Adds the default file format
					FileFilter defaultFilter = xmlFilter;
					fc.addChoosableFileFilter(defaultFilter);
					
					// Adds filter that accepts all supported image formats
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

}
