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
	 * 		The action points change along with the mass.
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
	public int getActionPoints(){
		return this.actionPoints;
	}
	public void setActionPoints(int actionPoints){
		this.actionPoints = actionPoints;
	}
	public void move(int steps){
		if (isValidStep(steps)){
			this.setXpos(this.getXpos() + (Math.cos(this.getDirection())*this.getRadius()));
			this.setYpos(this.getYpos() + (Math.sin(this.getDirection())*this.getRadius()));
		}
		this.setActionPoints(this.getActionPoints()-this.computeCostStep(steps));
	}
	public int computeCostStep(int steps){
		return (steps*(int) Math.round((Math.abs(Math.cos(this.getDirection()))
				+Math.abs((4*Math.sin(this.getDirection()))))));
	}
	public boolean isValidStep(int steps){
		return this.getActionPoints() >= this.computeCostStep(steps);
	}
	public int computeCostTurn(double angle){
		return (int) Math.abs(Math.round(((60*angle)/(2*Math.PI))));
	}
	public boolean isValidTurn(double angle){
		return this.getActionPoints() >= this.computeCostTurn(angle);
	}
	public void turn(double angle){
		if (isValidTurn(angle)){
			this.setDirection(this.getDirection()+ angle);
			this.setActionPoints(this.getActionPoints()-this.computeCostTurn(angle));
		}
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
