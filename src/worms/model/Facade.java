package worms.model;
import worms.model.IFacade;
import worms.model.Worm;

public class Facade implements IFacade {
	/**
	 * Creates a worm with a given x-and y-position, direction, radius and name. 
	 * Other values also get initialized.
	 */
	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) {
		return new Worm(x,y,direction,radius, name);
	}

	/**
	 * Checks whether a given worm can move over a given number of steps.
	 */
	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return worm.isValidStep(nbSteps);
	}

	/**
	 * Makes a given worm move over a given number of steps (in the current direction).
	 */
	@Override
	public void move(Worm worm, int nbSteps) {
		try{
			worm.move(nbSteps);
		} catch (IllegalArgumentException e) {
			throw new ModelException("not allowed to move");
		}
		
	}

	/**
	 * Checks whether a given worm can turn over a given angel.
	 */
	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.isValidTurn(angle);
	}

	/**
	 * Turns a given worm over a given angel.
	 */
	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	/**
	 * Makes a given worm jump.
	 */
	@Override
	public void jump(Worm worm) {
		try {
			worm.jump();	
		} catch (IllegalStateException e) {
			throw new ModelException("can't jump");
		}
		
	}

	/**
	 * Get the time it takes the given worm to jump.
	 */
	@Override
	public double getJumpTime(Worm worm) {
		try {
			return worm.jumpTime();
		} catch (IllegalStateException e) {
			throw new ModelException("can't jump");
		}
	}

	/**
	 * Get the x- and y-position of a given worm that's jumping at a given time t,
	 *  after the jump started.
	 */
	@Override
	public double[] getJumpStep(Worm worm, double t) {
		try {
			return worm.jumpStep(t);
		} catch (IllegalStateException e) {
			throw new ModelException("can't jump");
		}
	}

	/**
	 * Get a worm's x-position.
	 */
	@Override
	public double getX(Worm worm) {
		return worm.getXpos();
	}

	/**
	 * Get a worm's y-position.
	 */
	@Override
	public double getY(Worm worm) {
		return worm.getYpos();
	}

	/**
	 * Get a worm's orientation.
	 */
	@Override
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	/**
	 * Get a worms radius.
	 */
	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	/**
	 * Set the radius of the given worm to the given radius.
	 */
	@Override
	public void setRadius(Worm worm, double newRadius) {
		try {
			worm.setRadius(newRadius);
		} catch (IllegalArgumentException e) {
			throw new ModelException("not a valid radius");
		}
	}

	/**
	 * Get the minimal allowed radius of a given worm.
	 */
	@Override
	public double getMinimalRadius(Worm worm) {
		return 0.25;
	}

	/**
	 * Get the amount of actionpoints of a given worm.
	 */
	@Override
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}

	/**
	 * Get the maximum allowed amount of actionpoints of a given worm.
	 */
	@Override
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}

	/**
	 * Get the name of a given worm.
	 */
	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	/**
	 * Give a worm a new name.
	 */
	@Override
	public void rename(Worm worm, String newName) {
		try {
			worm.setName(newName);
		} catch (IllegalArgumentException e) {
			throw new ModelException("that name is not valid");
		}
	}

	/**
	 * Get the mass of a given worm.
	 */
	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
}
