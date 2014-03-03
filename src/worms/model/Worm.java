package worms.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.*;

/**
 *  Positie, radius en mass moeten defensief, de direction nominaal.
 *  De actionpoints op total manner
 * 	De naam ook defensief.
 */

/**
 * A class of worms involving x-position, y-position a radius and a direction.
 * 
 * @invar	The radius must be a valid radius for the worm.
 * 		  	| isValidRadius(this.getRadius())
 * 
 * @author Toon
 * @version 1.0
 */
public class Worm {
	
	public Worm(double xpos, double ypos, double direction, double radius, String name){
		this.setXpos(xpos);
		this.setYpos(ypos);
		this.setDirection(direction);
		this.setRadius(radius);
		this.setName(name);
		setMass(radius);
		this.setMaxActionPoints();
		this.setActionPoints(maxActionPoints);
		
	}
	/**
	 * Die met positie te maken hebben moeten nog defensief gemaakt worden, dus met exceptions 
	 * the throwen.
	 * @return
	 */
	@Basic
	public double getXpos(){
		return this.xpos;
	}
	public void setXpos(double xpos){
		this.xpos = xpos;
	}
	@Basic
	public double getYpos(){
		return this.ypos;
	}
	public void setYpos(double ypos){
		this.ypos = ypos;
	}
	/**
	 * 
	 * @return
	 */
	@Basic
	public double getDirection(){
		return this.direction;
	}
	public void setDirection(double direction){
		if (isValidDirection(direction)){
			this.direction=direction;
		}
	}
	/**
	 * Dit is nog niet af, maar ik weet niet wat pre conditie moet zijn.
	 * @param direction
	 * @return
	 */
	public static boolean isValidDirection(double direction){
		return true;
	}
	@Basic
	public double getRadius(){
		return this.radius;
	}	
	public void setRadius(double radius){
		if (isValidRadius(radius)){
			this.radius = radius;
		}
	}
	/**
	 * 
	 * @param radius
	 * @return	True if and only if the given radius is larger or equal to 0.25m.
	 * 			| result == (radius >= 0.25)
	 */
	public static boolean isValidRadius(double radius){
		return radius >= 0.25;
	}
	public void setMass(double radius){
		assert isValidRadius(radius);
		this.mass = density*((4/3)*Math.PI*Math.pow(radius, 3));
	}
	public double getMass(){
		return this.mass;
	}
	public String getName(){
		return this.name;
		
	}
	public void setName(String name){
		if (isValidName(name)){
			this.name = name;
		}
	}
	public static boolean isValidName(String name){
		String regx = "^[\\p{L} .'-]+${2,}";
	    Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(name);
	    return matcher.find();
	}
	/**
	 * Return the maximal amount of action points for this worm.
	 */
	@Basic
	public int getMaxActionPoints(){
		return this.maxActionPoints;
	}
	/**
	 * Set the maximal amount of action points of this worm.
	 * @param mass
	 * 			The action points change along with the mass.
	 * 
	 * @post	The amount of action points must be equal to the mass of the worm rounded
	 * 			to the nearest integer.
	 * 			| this.maxActionPoints = (int) Math.round(mass)
	 * 
	 * @post	If the mass of a worm changes, the maximal must be adjusted accordingly.
	 * 			| new.getMass() = mass
	 * 			| this.maxActionPoints = (int) Math.round(new.getMass())
	 * 
	 * @effect	The maximal amount of action points has been set.
	 */
	@Basic
	public void setMaxActionPoints(){
		 this.maxActionPoints = (int) Math.round(this.getMass());
	}
	/**
	 * Return the current amount of action points for this worm.
	 */
	public int getActionPoints(){
		return this.actionPoints;
	}
	/**
	 * Set a new amount of action points for this worm.
	 * @param actionPoints
	 * 			The new amount of action points.
	 * 
	 * @post	The current value of a worm's action points must always be 
	 * 			less then or equal to the maximum value. 
	 * 			|new.getActionPoint() <= new.getMaxActionPoints()
	 * 
	 * @post	The current value of a worm's action points must never be less then zero.
	 * 			|new.getActionPoint() > 0
	 */
	private void setActionPoints(int actionPoints){
		if (actionPoints >= this.getMaxActionPoints())
			this.actionPoints = this.getMaxActionPoints();
		if (actionPoints <0)
			this.actionPoints = 0;
		else 
			this.actionPoints = actionPoints;
	}
	public void move(int steps){
		if (isValidStep(steps)){
			this.setXpos(this.getXpos() + (Math.cos(this.getDirection())*this.getRadius()));
			this.setYpos(this.getYpos() + (Math.sin(this.getDirection())*this.getRadius()));
		}
		this.setActionPoints(this.getActionPoints()-this.computeCostStep(steps));
	}
	/**
	 * Returns the cost in actionpoints for a given number of steps in the current direction.
	 * @param steps
	 * 			the number of steps the worm is going to move.
	 * 
	 * @return	The cost of steps (integer) in the current direction, rounded up to the next integer.
	 * 			|(steps*(int) Math.round((Math.abs(Math.cos(this.getDirection()))
				|+Math.abs((4*Math.sin(this.getDirection()))))))
	 * 			
	 */
	public int computeCostStep(int steps){
		return (steps*(int) Math.round((Math.abs(Math.cos(this.getDirection()))
				+Math.abs((4*Math.sin(this.getDirection()))))));
	}
	/**
	 * Checks whether a given step in the current direction is a valid step.
	 * This means that there are still enough actionpoints to complete the step.
	 * 
	 * @param steps
	 * 			number of steps in the current direction.
	 * 		
	 * @return 	True if there are still enough actionpoints for the wished step.
	 * 			and return False if there aren't enough actionpoints for the wished step.
	 * 			| this.getActionPoints() >= this.computeCostStep(steps)
	 */
	public boolean isValidStep(int steps){
		return this.getActionPoints() >= this.computeCostStep(steps);
	}
	/**
	 * Returns the cost in actionpoints for a turn of a given angle
	 * .
	 * @param angle
	 * 			the angle over which the worm would like to turn in radians.
	 * 
	 * @return 	the cost in actionpoints for a turn of a given angle rounded up to the nearest integer.
	 * 			|(int) Math.abs(Math.round(((60*angle)/(2*Math.PI))))
	 * 
	 */
	public int computeCostTurn(double angle){
		return (int) Math.abs(Math.round(((60*angle)/(2*Math.PI))));
	}
	/**
	 * Checks whether turning over a given angle is valid.
	 * This means that there must be enough actionpoints to complete the turn.
	 * 
	 * @param angle
	 * 			the angle over which the worm would like to turn in radians.
	 * 
	 * @return 	True if there are still enough actionpoints for the wished turn.
	 * 			and return False if there aren't enough actionpoints for the wished turn.
	 * 			| this.getActionPoints() >= this.computeCostTurn(angle) 
	 */
	public boolean isValidTurn(double angle){
		return this.getActionPoints() >= this.computeCostTurn(angle);
	}
	/**
	 * Makes the worms turn over a given angle.
	 * @param angle
	 * @pre		There must be enough action point to complete the turn.
	 * 			| this.isvalidTurn(angle)
	 * @post	The worms must have turned over the given angle.
	 * 			|new.getDirection() == old.getDirection() + angle
	 * @post	The worm's actionpoints must be decreased accordingly.
	 * 			|new.getActionpoints() == old.getActionpoints() - old.computeCostTurn(angle)
	 */
	public void turn(double angle){
		this.setDirection(this.getDirection()+ angle);
		this.setActionPoints(this.getActionPoints()-this.computeCostTurn(angle));
		
	}
	
	private double xpos;
	private double ypos;
	private double direction;
	/**
	 * @pre	The radius of a worm must at all times be at least 0.25 m.
	 * 		| radius >= 0.25
	 */
	private double radius;
	private static final int density = 1602;
	private double mass;
	private int maxActionPoints;
	private int actionPoints;
	private String name;
	

}
