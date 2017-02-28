package test.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.boisestate.cs573.spring2017.petrinet.controller.BaseController;
import edu.boisestate.cs573.spring2017.petrinet.controller.IStateListener;
import edu.boisestate.cs573.spring2017.petrinet.controller.StateSet;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractArc;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractPlace;
import edu.boisestate.cs573.spring2017.pretrinet.model.AbstractTransition;

public class ControllerTest {
	public BaseController controller;
	public StateSet stateset;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.controller = new BaseController();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testBaseController() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNewNet() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddTransition() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddPlace() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddArc() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDelete() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetArcWeight() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetPlaceTokenCount() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetName() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRegisterStateListener() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetLocation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSave() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testLoad() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testFire() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSimulate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testUndo() {
		fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testAsyncCommunicationNet() {
		//Register as listener
		controller.registerStateListener(new IStateListener(){
			
			@Override
			public void newState(StateSet ss) {
				stateset = ss;
				
			}
		});
		int id = 1;
		//places
		ArrayList<AbstractPlace> places = new ArrayList<>();
		String[] placenames = {"Ready to send",
								"Buffer full", 
								"Ready to receive", 
								"Wait for ack",	
								"Message received",
								"Ack recieved",
								"Buffer full",
								"Ack sent"};
		for(String name: placenames){
			AbstractPlace p = new AbstractPlace(0,0,1,name);
			controller.addPlace(p);
			p.setID(id++);
			places.add(p);
		}
	
		//transitions
		ArrayList<AbstractTransition> transitions = new ArrayList<>();
		String[] transitionnames = {"Send Message",
									"Receive message",
									"Process 1",
									"Process 2",
									"Receive Ack",
									"Send Ack"
		};
		for(String name: transitionnames){
			AbstractTransition t = new AbstractTransition(0,0,name);
			controller.addTransition(t);
			t.setID(id++);
			transitions.add(t);
		}
		//Arcs
		Integer[] originplaces = 			{0,1,2,3,4,5,6,7};
		Integer[] destinationtransitions =	{0,1,1,4,5,4,2,3};
		for(int i=0; i < originplaces.length; i++){
			Integer originID = originplaces[i]+1;
			Integer destinationID = placenames.length + destinationtransitions[i] + 1;
			controller.addArc(new AbstractArc(1,originID, destinationID ));
		}
		Integer[] origintransitions =		{0,0,1,2,3,4,5,5};
		Integer[] destinationplaces = 		{2,3,4,0,1,5,6,7};
		for(int i=0; i < origintransitions.length; i++){
			Integer originID = placenames.length + origintransitions[i]+1;
			Integer destinationID = destinationplaces[i] + 1;
			controller.addArc(new AbstractArc(1,originID, destinationID ));
		}
		
		//Check the stateset
		for(AbstractPlace p: places){
			assertTrue(stateset.getArcs().contains(p));
		}
	}


}
