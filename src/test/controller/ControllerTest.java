package test.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.bsu.petriNet.controller.BaseController;
import edu.bsu.petriNet.controller.IStateListener;
import edu.bsu.petriNet.controller.StateSet;
import edu.bsu.petriNet.model.AbstractArc;
import edu.bsu.petriNet.model.AbstractPlace;
import edu.bsu.petriNet.model.AbstractTransition;

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
		//Register as listener
		controller.registerStateListener(new IStateListener(){
			
			@Override
			public void newState(StateSet ss) {
				stateset = ss;
				
			}
		});
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
		controller.newNet();
		assertEquals(0, stateset.getArcs().size());
		assertEquals(0, stateset.getPlaces().size());
		assertEquals(0, stateset.getTransitions().size());
	}

	@Test
	public final void testAddTransition() {
		controller.addTransition(new AbstractTransition(0,3,"Some Transition"));
		assertEquals(1, stateset.getTransitions().size());
		assertTrue(stateset.getTransitions().get(0).getName().equals("Some Transition"));
		assertEquals((Integer)0, stateset.getTransitions().get(0).getX());
		assertEquals((Integer)3, stateset.getTransitions().get(0).getY());
	}

	@Test
	public final void testAddPlace() {
		controller.addPlace(new AbstractPlace(1,0,0,"Some Place"));
		assertEquals(1, stateset.getPlaces().size());
		assertTrue(stateset.getPlaces().get(0).getName().equals("Some Place"));
		assertEquals((Integer)0, stateset.getPlaces().get(0).getX());
		assertEquals((Integer)0, stateset.getPlaces().get(0).getY());
	}

	@Test
	public final void testAddArc() {
		controller.addPlace(new AbstractPlace(1,0,0,"Some Place"));
		controller.addTransition(new AbstractTransition(2,0,3,"Some Transition"));
		controller.addArc(new AbstractArc(3,1,1,2,"Some Arc"));
		assertEquals(1, stateset.getArcs().size());
		assertTrue(stateset.getArcs().get(0).getName().equals("Some Arc"));
		assertEquals((Integer)1, stateset.getArcs().get(0).getWeight());
		assertEquals((Integer)1, stateset.getArcs().get(0).getOrigin());
		assertEquals((Integer)2, stateset.getArcs().get(0).getTarget());
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

	public final StateSet setupAsyncCommunicationNet() {


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
			AbstractPlace p = new AbstractPlace(id++,0,0,1,name);
			controller.addPlace(p);
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
			AbstractTransition t = new AbstractTransition(id++,0,0,name);
			controller.addTransition(t);
			transitions.add(t);
		}
		//Arcs
		ArrayList<AbstractArc> arcs = new ArrayList<>();
		Integer[] originplaces = 			{0,1,2,3,4,5,6,7};
		Integer[] destinationtransitions =	{0,1,1,4,5,4,2,3};
		for(int i=0; i < originplaces.length; i++){
			Integer originID = originplaces[i]+1;
			Integer destinationID = placenames.length + destinationtransitions[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		Integer[] origintransitions =		{0,0,1,2,3,4,5,5};
		Integer[] destinationplaces = 		{2,3,4,0,1,5,6,7};
		for(int i=0; i < origintransitions.length; i++){
			Integer originID = placenames.length + origintransitions[i]+1;
			Integer destinationID = destinationplaces[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		StateSet output = new StateSet();
		output.addArcs(arcs);
		output.addPlaces(places);
		output.addTransitions(transitions);
		return output;
	}
		
	@Test
	public final void testAsyncCommunicationNet() {
		StateSet initial = setupAsyncCommunicationNet();
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	@Test
	public final void testAsyncCommunicationNetIO(){
		StateSet initial = setupAsyncCommunicationNet();
		controller.save("AsyncCommunication.xml");
		controller.load("AsyncCommunication.xml");
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	public final StateSet setupAsyncVendingMachineNet() {


		int id = 1;
		//places
		ArrayList<AbstractPlace> places = new ArrayList<>();
		String[] placenames = {"ZeroCents",
								"FiveCents", 
								"TenCents", 
								"FifteenCents",	
								"TwentyCents"};
		
		Integer[] tokens = {1, 0, 0, 0, 0};
		
		int index = 0;
		
		for(String name: placenames){
			AbstractPlace p = new AbstractPlace(id++,0,0,tokens[index],name);
			controller.addPlace(p);
			places.add(p);
			index++;
		}
	
		//transitions
		ArrayList<AbstractTransition> transitions = new ArrayList<>();
		String[] transitionnames = {"T1 Deposit5",
									"T2 Deposit10",
									"T3 Deposit5",
									"T4 Deposit10",
									"T5 Deposit5",
									"T6 Deposit10",
									"T7 Deposit5",
									"T8 Get15",
									"T9 Get20"
		};
		for(String name: transitionnames){
			AbstractTransition t = new AbstractTransition(id++,0,0,name);
			controller.addTransition(t);
			transitions.add(t);
		}
		//Arcs
		ArrayList<AbstractArc> arcs = new ArrayList<>();
		Integer[] originplaces = 			{0,0,1,1,2,2,3,3,4};
		Integer[] destinationtransitions =	{0,1,2,3,4,5,6,7,8};
		for(int i=0; i < originplaces.length; i++){
			Integer originID = originplaces[i]+1;
			Integer destinationID = placenames.length + destinationtransitions[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		Integer[] origintransitions =		{0,1,2,3,4,5,6,7,8};
		Integer[] destinationplaces = 		{1,2,2,3,3,4,4,0,0};
		for(int i=0; i < origintransitions.length; i++){
			Integer originID = placenames.length + origintransitions[i]+1;
			Integer destinationID = destinationplaces[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		StateSet output = new StateSet();
		output.addArcs(arcs);
		output.addPlaces(places);
		output.addTransitions(transitions);
		return output;
	}
	
	@Test
	public final void testAsyncVendingMachineNet() {
		StateSet initial = setupAsyncVendingMachineNet();
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	@Test
	public final void testAsyncVendingMachineNetIO(){
		StateSet initial = setupAsyncVendingMachineNet();
		controller.save("AsyncVendingMachine.xml");
		controller.load("AsyncVendingMachine.xml");
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	public final StateSet setupAsyncDiningPhilosophersNet() {


		int id = 1;
		//places
		ArrayList<AbstractPlace> places = new ArrayList<>();
		String[] placenames = {"P0 Fork",
								"P1 Thinking", 
								"P2 Fork", 
								"P3 Thinking",
								"P4 Fork",
								"P5 Thinking",
								"P6 Fork",
								"P7 Thinking",
								"P8 Fork",
								"P9 Thinking",
								"P10 Eating",
								"P11 Eating",
								"P12 Eating",
								"P13 Eating",
								"P14 Eating"};
		
		Integer[] tokens = {1,1,1,1,1,1,1,1,1,1,0,0,0,0,0};
		
		int index = 0;
		
		for(String name: placenames){
			AbstractPlace p = new AbstractPlace(id++,0,0,tokens[index],name);
			controller.addPlace(p);
			places.add(p);
			index++;
		}
	
		//transitions
		ArrayList<AbstractTransition> transitions = new ArrayList<>();
		String[] transitionnames = {"T0",
									"T1",
									"T2",
									"T3",
									"T4",
									"T5",
									"T6",
									"T7",
									"T8",
									"T9"
		};
		for(String name: transitionnames){
			AbstractTransition t = new AbstractTransition(id++,0,0,name);
			controller.addTransition(t);
			transitions.add(t);
		}
		//Arcs
		ArrayList<AbstractArc> arcs = new ArrayList<>();
		Integer[] originplaces = 			{0,0,1,2,2,3,4,4,5,6,6,7,8,8,9,10,11,12,13,14};
		Integer[] destinationtransitions =	{0,4,0,0,1,1,1,2,2,2,3,3,3,4,4,5,6,7,8,9};
		for(int i=0; i < originplaces.length; i++){
			Integer originID = originplaces[i]+1;
			Integer destinationID = placenames.length + destinationtransitions[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		Integer[] origintransitions =		{0,1,2,3,4,5,5,5,6,6,6,7,7,7,8,8,8,9,9,9};
		Integer[] destinationplaces = 		{10,11,12,13,14,0,1,2,2,3,4,4,5,6,6,7,8,8,9,0};
		for(int i=0; i < origintransitions.length; i++){
			Integer originID = placenames.length + origintransitions[i]+1;
			Integer destinationID = destinationplaces[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		StateSet output = new StateSet();
		output.addArcs(arcs);
		output.addPlaces(places);
		output.addTransitions(transitions);
		return output;
	}
	
	@Test
	public final void testAsyncDiningPhilosophersNet() {
		StateSet initial = setupAsyncDiningPhilosophersNet();
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	@Test
	public final void testAsyncDiningPhilosophersIO(){
		StateSet initial = setupAsyncDiningPhilosophersNet();
		controller.save("AsyncDiningPhilosophers.xml");
		controller.load("AsyncDiningPhilosophers.xml");
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	public final StateSet setupAsyncReadersWriterNet(int k) {


		int id = 1;
		//places
		ArrayList<AbstractPlace> places = new ArrayList<>();
		String[] placenames = {"P1 Processes",
								"P2 Reading", 
								"P3 SharedMemory", 
								"P4 Writing"};
		
		Integer[] tokens = {k,0,k,0};
		
		int index = 0;
		
		for(String name: placenames){
			AbstractPlace p = new AbstractPlace(id++,0,0,tokens[index],name);
			controller.addPlace(p);
			places.add(p);
			index++;
		}
	
		//transitions
		ArrayList<AbstractTransition> transitions = new ArrayList<>();
		String[] transitionnames = {"T1","T2","T3","T4"};
		
		for(String name: transitionnames){
			AbstractTransition t = new AbstractTransition(id++,0,0,name);
			controller.addTransition(t);
			transitions.add(t);
		}
		//Arcs
		ArrayList<AbstractArc> arcs = new ArrayList<>();
		Integer[] originplaces = 			{0,0,1,2,2,3};
		Integer[] destinationtransitions =	{0,1,2,0,1,3};
		for(int i=0; i < originplaces.length; i++){
			Integer originID = originplaces[i]+1;
			Integer destinationID = placenames.length + destinationtransitions[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		Integer[] origintransitions =		{0,1,2,2,3,3};
		Integer[] destinationplaces = 		{1,3,0,2,0,2};
		for(int i=0; i < origintransitions.length; i++){
			Integer originID = placenames.length + origintransitions[i]+1;
			Integer destinationID = destinationplaces[i] + 1;
			AbstractArc a  = new AbstractArc(id++,1,originID, destinationID );
			arcs.add(a);
			controller.addArc(a);
		}
		StateSet output = new StateSet();
		output.addArcs(arcs);
		output.addPlaces(places);
		output.addTransitions(transitions);
		return output;
	}
	
	@Test
	public final void testAsyncReadersWriterNet() {
		StateSet initial = setupAsyncReadersWriterNet(2);
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}
	
	@Test
	public final void testAsyncReadersWriterNetIO(){
		StateSet initial = setupAsyncReadersWriterNet(2);
		controller.save("AsyncReadersWriter.xml");
		controller.load("AsyncReadersWriter.xml");
		//Check the stateset
		for(AbstractPlace p: initial.getPlaces()){
			System.out.println(p);
			assertTrue(stateset.getPlaces().contains(p));
		}
		for(AbstractTransition t: initial.getTransitions()){
			System.out.println(t);
			assertTrue(stateset.getTransitions().contains(t));
		}
		for(AbstractArc a: initial.getArcs()){
			System.out.println(a);
			assertTrue(stateset.getArcs().contains(a));
		}
	}

}
