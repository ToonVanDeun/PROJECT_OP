package worms.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.model.ModelException;

public class WormTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private static Worm worm_position;
	private static Worm worm_radius;
	private static Worm worm_mass;
	private static Worm worm_name;
	private static Worm worm_actionpoints;
	private static Worm worm_move;
	private static Worm worm_turn;
	private static Worm worm_jump;
	
	
	
	@Before
	public void setUp() throws Exception {
		worm_position = new Worm(0, 0, 0, 5, "Position");
		worm_radius = new Worm(0, 0, 0, 5, "Radius");
		worm_mass = new Worm(0, 0, 0, 1, "Mass");
		worm_name = new Worm(0, 0, 0, 1, "Name");
		worm_actionpoints = new Worm(0, 0, 0, 1, "ActionPoints");
		worm_move = new Worm(0, 0, (Math.PI)/4, 1, "Move");
		worm_turn = new Worm(0, 0, 0, 1, "Turn");
		worm_jump = new Worm(0, 0, 3 * Math.PI / 2, 1, "Jump");
	}

	@After
	public void tearDown() throws Exception {
	}

	//position
	@Test
	public void test_setXpos_validXpos() {
		worm_position.setXpos(7);
		assert worm_position.getXpos() ==  7;	
	}
	
	@Test
	public void test_setYpos_validYpos() {
		worm_position.setYpos(-5);
		assert worm_position.getYpos() ==  -5;	
	}
	
	//direction
	
	//radius
	@Test
	public void test_setRadius_valid() {
		worm_radius.setRadius(5.3);
		assert worm_radius.getRadius() ==  5.3;	
	}
	@Test(expected = ModelException.class)
	public void test_setRadius_fails() {
		worm_radius.setRadius(0.1);	
	}
	
	//mass (mss niet echt nodig, vooral de ...fails niet)
	@Test
	public void test_setMass_valid() {
		worm_mass.setMass(worm_mass.getRadius());
		assert worm_mass.getMass() == 1062*((4.0/3.0)*Math.PI);
	}
	@Test(expected = ModelException.class)
	public void test_setMass_fails() {
		worm_mass.setMass(0.1);
	}
	
	//name
	@Test
	public void test_setName_validCase1() {
		worm_name.setName("Az");
		assert worm_name.getName() == "Az";
	}
	@Test
	public void test_setName_validCase2() {
		worm_name.setName("Ab cd ef g");
		assert worm_name.getName() == "Ab cd ef g";
	}
	@Test
	public void test_setName_validCase3() {
		worm_name.setName("Abcde'gh'hij");
		assert worm_name.getName() == "Abcde'gh'hij";
	}
	@Test(expected = ModelException.class)
	public void test_setName_failsCase1() {
		worm_name.setName("A");
	}
	@Test(expected = ModelException.class)
	public void test_setName_failsCase2() {
		worm_name.setName("azerty");
	}
	@Test(expected = ModelException.class)
	public void test_setName_failsCase3() {
		worm_name.setName("Azerty5");
	}
	@Test(expected = ModelException.class)
	public void test_setName_failsCase4() {
		worm_name.setName("Azerty///");
	}
	
	//actionpoints
	@Test
	public void test_setMaxActionPoints_valid() {
		worm_actionpoints.setMaxActionPoints();
		assert worm_actionpoints.getMaxActionPoints() == Math.round(worm_actionpoints.getMass());
	}
	@Test
	public void test_setActionPoints_validCase() {
		worm_actionpoints.setActionPoints(500);
		assert worm_actionpoints.getActionPoints() == 500;
	}
	@Test
	public void test_setActionPoints_validCaseTotal1() {
		worm_actionpoints.setActionPoints(-10);
		assert worm_actionpoints.getActionPoints() == 0;
	}
	@Test
	public void test_setActionPoints_validCaseTotal2() {
		int maxAPs = worm_actionpoints.getMaxActionPoints(); 
		int newActionPoints = (4448+500);
		worm_actionpoints.setActionPoints(newActionPoints);
		System.out.println("AP " + worm_actionpoints.getActionPoints());
		System.out.println("maxAP " + worm_actionpoints.getMaxActionPoints());
		assert worm_actionpoints.getActionPoints() == maxAPs;
	}
	
	//move
	@Test
	public void test_move_valid() {
		int initialActionPoints = worm_move.getActionPoints();
		double oldXpos = worm_move.getXpos();
		double oldYpos = worm_move.getYpos();
		worm_move.move(2);
		assert worm_move.getActionPoints() == (initialActionPoints
				- Math.round(5.0*Math.sqrt(2.0)));
		assert worm_move.getXpos() == (oldXpos + 2*Math.cos(Math.PI/4));
		assert worm_move.getYpos() == (oldYpos + 2*Math.sin(Math.PI/4));
	}
	
	//turn
	@Test
	public void test_turn_validCase1() {
		worm_turn.turn(Math.PI);
		assert worm_turn.getDirection() == Math.PI;
	}
	@Test
	public void test_turn_validCase2() {
		worm_turn.turn(3*Math.PI);
		assert worm_turn.getDirection() == 3*Math.PI;
	}
	@Test
	public void test_turn_validCase3() {
		worm_turn.turn(-Math.PI);
		assert worm_turn.getDirection() == -Math.PI;
	}
	@Test(expected = AssertionError.class)
	public void test_turn_failsNotEnoughActionPoints() {
		worm_turn.turn(170*Math.PI);
	}
	
	//jump
	@Test(expected = ModelException.class)
	public void test_jump_fail() {
		worm_jump.jump();
	}
	@Test
	public void test_jump_valid() {
		System.out.println("xpos" + worm_jump.getXpos());
		System.out.println("AP" + worm_jump.getActionPoints());
		System.out.println("mass:" + worm_jump.getMass());
		double oldXpos = worm_jump.getXpos();
		worm_jump.turn((3.0/4.0)*Math.PI);
		worm_jump.jump();
		System.out.println("xpos" + worm_jump.getXpos());
		System.out.println("AP" + worm_jump.getActionPoints());
		assert Math.abs(worm_jump.getXpos() - (oldXpos + 5.59)) <0.1 ;
	}
}
