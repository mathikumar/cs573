package edu.bsu.petriNet.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.bsu.petriNet.controller.IController;
import edu.bsu.petriNet.controller.IStateListener;
import edu.bsu.petriNet.controller.StateSet;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractGraphNode;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;

public class CanvasPanel extends JPanel implements IStateListener {
	
	public static final Integer BUFFER=64;
	public static final Integer LINE_THICKNESS=2;
	
	Map<Integer,GElement> elements;
	BasicGraphEditorPanel mainPanel;
	IController controller;
	MouseListener moveBehavior;
	MouseListener newPlaceBehavior;
	MouseListener newTransitionBehavior;
	MouseListener newArcBehavior;
	MouseListener fireTransitionBehavior;
	MouseListener editBehavior;
	MouseListener selectBehavior;
	MouseMotionListener selectMotionListener;
	Boolean isRecentering;
	ElementSelection selection;
	
	Point lastMousePos;
	CopyBuffer clipboard;
	
	public CanvasPanel(BasicGraphEditorPanel p, IController c){
		elements = new ConcurrentHashMap<>();
		mainPanel = p;
		controller = c;
		isRecentering = false;
		
		selection = new ElementSelection(elements);		
		
		this.moveBehavior = new MouseListener(){
			
			AbstractGraphNode origin;
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() != MouseEvent.BUTTON1){
					Point pt = e.getPoint();
					for(Map.Entry<Integer, GElement> el: elements.entrySet()){
						if (el.getValue().containsPoint(pt) && el.getValue().getAbstractElement() instanceof AbstractGraphNode){
							origin = (AbstractGraphNode)el.getValue().getAbstractElement();
						}
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() != MouseEvent.BUTTON1 && origin != null){
					Point pt = e.getPoint();
					Boolean overlap = false;
					for(Map.Entry<Integer, GElement> el: elements.entrySet()){
						if (el.getValue().containsPoint(pt) && el.getValue().getAbstractElement() instanceof AbstractGraphNode){
							overlap = true;
						}
					}
					if(!overlap){
						origin.setX(pt.x);
						origin.setY(pt.y);
						controller.setLocation(origin);
					}
					origin = null;
				}
			}
		};
		
		
		this.editBehavior = new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() != MouseEvent.BUTTON1){
					Point pt = e.getPoint();
					for(Map.Entry<Integer, GElement> el: elements.entrySet()){
						if (el.getValue().containsPoint(pt)){
							el.getValue().editDialog((JFrame) SwingUtilities.getWindowAncestor(CanvasPanel.this), controller);
						}
					}
				}
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			
		};
		
		this.newPlaceBehavior = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					Point p  = e.getPoint();
					controller.addPlace(new AbstractPlace(p.x, p.y,1));
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
		
		this.newTransitionBehavior = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					Point p  = e.getPoint();
					controller.addTransition(new AbstractTransition(p.x, p.y));
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
		
		this.newArcBehavior = new MouseListener(){
			AbstractGraphNode origin;
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				Point pt = e.getPoint();
				for(Map.Entry<Integer, GElement> el: elements.entrySet()){
					if (el.getValue().containsPoint(pt) && el.getValue().getAbstractElement() instanceof AbstractGraphNode){
						origin = (AbstractGraphNode)el.getValue().getAbstractElement();
						System.out.println(origin);
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Point pt  = e.getPoint();
				System.out.println(pt);
				for(Map.Entry<Integer, GElement> el: elements.entrySet()){
					if (el.getValue().containsPoint(pt) && el.getValue().getAbstractElement() instanceof AbstractGraphNode  && origin != null){
						AbstractGraphNode target = (AbstractGraphNode)el.getValue().getAbstractElement();
						System.out.println(target);
						controller.addArc(new AbstractArc(1, origin.getID(), target.getID()));
					}
				}
				origin = null;
				
			}
		};
		
		this.fireTransitionBehavior = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p  = e.getPoint();
				for(Map.Entry<Integer, GElement> el: elements.entrySet()){
					if (el.getValue().containsPoint(p) && el.getValue().getAbstractElement() instanceof AbstractTransition){
						controller.fire((AbstractTransition)el.getValue().getAbstractElement());
					}
				}
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
		
		this.selectBehavior = new MouseListener(){
			Point moveOrigin;
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Point pt = e.getPoint();
					
					// If they clicked on an element they must want to move it
					for(Map.Entry<Integer, GElement> el: elements.entrySet()){
						if (el.getValue().containsPoint(pt) && el.getValue().getAbstractElement() instanceof AbstractGraphNode){
							moveOrigin = pt;
							// If it's not already selected, select it (clearing
							// any existing selection). It will be moved when
							// the mouse is released.
							//
							// If it is already selected, we do nothing - the
							// whole selection will be moved when the mouse is
							// released.
							if (!selection.contains(el.getValue())) {						
								selection.clear();
								selection.add(el.getValue());
							}
							return;
						}
					}
					
					// If they clicked on empty space they must want to make a
					// selection rectangle
					selection.beginRectangleSelect(e.getPoint());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// End a rectangle selection if applicable
					if (selection.pendingRectangleSelect()) {
						selection.endRectangleSelect(e.getPoint());
						moveOrigin = null;
						repaint();
					} else if (moveOrigin != null) {
					// Otherwise, if there's anything selected, move it
						Point moveEndpoint = e.getPoint();
						Point delta = new Point(moveEndpoint.x-moveOrigin.x,moveEndpoint.y-moveOrigin.y);
						for (Integer id : selection) {
							controller.translate(id,delta.x,delta.y);
						}
						
						moveOrigin = null;
					}
				}
			}
		};
		
		this.selectMotionListener = new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {}
		};
		
		this.addMouseListener(this.newPlaceBehavior);
	}
	
	public void purgeMouseListeners(){
		for(MouseListener ml: this.getMouseListeners()){
			this.removeMouseListener(ml);
		}
		for(MouseMotionListener mml: this.getMouseMotionListeners()){
			this.removeMouseMotionListener(mml);
		}
	}

	public void setNewPlaceState(){
		purgeMouseListeners();
		this.addMouseListener(this.newPlaceBehavior);
		this.addMouseListener(this.editBehavior);
		this.addMouseListener(this.moveBehavior);
	}
	
	public void setNewTransitionState(){
		purgeMouseListeners();
		this.addMouseListener(this.newTransitionBehavior);
		this.addMouseListener(this.editBehavior);		
		this.addMouseListener(this.moveBehavior);
	}
	
	public void setNewArcState(){
		purgeMouseListeners();
		this.addMouseListener(this.newArcBehavior);
		this.addMouseListener(this.editBehavior);
		this.addMouseListener(this.moveBehavior);
	}
	
	public void setSelectState(){
		purgeMouseListeners();
		this.addMouseListener(this.selectBehavior);
		// needed so the selection box updates while being dragged
		this.addMouseMotionListener(this.selectMotionListener);
		this.addMouseListener(this.editBehavior);
		this.addMouseListener(this.moveBehavior);
	}
	
	public void setFireBehavior(){
		purgeMouseListeners();
		this.addMouseListener(this.fireTransitionBehavior);
	}
	
	
	/**
	 * 
	 */
	public void paintComponent(Graphics g)
	{
			Rectangle rect = getVisibleRect();

			if (g.getClipBounds() != null)
			{
				rect = rect.intersection(g.getClipBounds());
			}

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.WHITE);
			//g2.setPaint(new GradientPaint(0, 0, getBackground(), getWidth(), 0,	gradientColor));
			g2.fill(rect);
			g2.setColor(Color.BLACK);
			for(GElement e: elements.values()){
				System.out.println(e);
				e.draw(g2, elements, selection);
			}
			
			Point mousePos = this.getMousePosition();
			// If the cursor is outside the JPanel, JPanel assumes that the
			// cursor has suddenly stopped existing, and getMousePosition()
			// therefore returns null. But we don't want to lose the selection
			// rectangle entirely if the user momentarily drags the cursor
			// outside the window, so we just store whatever the last mouse
			// position was and use it in this case.
			if (mousePos == null) {
				mousePos = lastMousePos;
			} else {
				lastMousePos = mousePos;
			}
			selection.draw(g2, mousePos);
	}

	@Override
	public void newState(StateSet ss) {
		int maxX=0;
		int maxY=0;
		int minX=0;
		int minY=0;
		elements.clear();
		for(AbstractPlace p: ss.getPlaces()){
			elements.put(p.getID(),new GPlace(p));
			maxX = Math.max(maxX,p.getX()+BUFFER);
			maxY = Math.max(maxY,p.getY()+BUFFER);
			minX = Math.min(minX,p.getX()-BUFFER);
			minY = Math.min(minY,p.getY()-BUFFER);
		}
		for(AbstractTransition p: ss.getTransitions()){
			elements.put(p.getID(),new GTransition(p));
			maxX = Math.max(maxX,p.getX()+BUFFER);
			maxY = Math.max(maxY,p.getY()+BUFFER);
			minX = Math.min(minX,p.getX()-BUFFER);
			minY = Math.min(minY,p.getY()-BUFFER);

		}
		for(AbstractArc p: ss.getArcs()){
			elements.put(p.getID(),new GArc(p));
		}
		if(minX < 0 || minY < 0){
			if(!isRecentering){
				isRecentering = true;
				reCenter(minX, minY);
			}
			return;
		}
		isRecentering = false;
		setPreferredSize(new Dimension(maxX,maxY));
		revalidate();
		this.repaint();
	}
	
	private void reCenter(int minX, int minY){
		List<AbstractGraphNode> workingset = new ArrayList<>();
		for(GElement e: elements.values()){
			if(e.getAbstractElement() instanceof AbstractGraphNode){
				workingset.add((AbstractGraphNode)e.getAbstractElement());
			}
		}
		for(AbstractGraphNode e: workingset){
			e.setX(e.getX()-minX);
			e.setY(e.getY()-minY);
			controller.setLocation(e);
		}
	}

	public void cut() {
		copy();
		for (Integer id : selection) {
			controller.delete(id);
		}
		selection.clear();
	}
	
	public void copy() {
		clipboard = new CopyBuffer(selection,elements);
	}
	
	public void paste() {
		if (clipboard != null) {
			clipboard.paste(controller, getMousePosition());
			clipboard = null;
		}
	}
}
