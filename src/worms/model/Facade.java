package worms.model;
import worms.model.IFacade;
import worms.model.Worm;

public class Facade implements IFacade {

	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) {
		return new Worm(x,y,direction,radius, name);
	}

	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return worm.isValidStep(nbSteps);
	}

	@Override
	public void move(Worm worm, int nbSteps) {
		worm.move(nbSteps);
	}

	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.isValidTurn(angle);
	}

	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	@Override
	public void jump(Worm worm) {
		worm.jump();
		
	}

	@Override
	public double getJumpTime(Worm worm) {
		return worm.jumpTime();
	}

	@Override
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(t);
	}

	@Override
	public double getX(Worm worm) {
		return worm.getXpos();
	}

	@Override
	public double getY(Worm worm) {
		return worm.getYpos();
	}

	@Override
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	@Override
	public void setRadius(Worm worm, double newRadius) {
		worm.setRadius(newRadius);
	}

	@Override
	public double getMinimalRadius(Worm worm) {
		return 0.25;
	}

	@Override
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}

	@Override
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}

	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	@Override
	public void rename(Worm worm, String newName) {
		worm.setName(newName);
	}

	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
}
