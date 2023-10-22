
public class Asteroid extends Sprite {
	
	private double xVelocity;
	
	private boolean isChecked;

	public Asteroid(int x, int y) {
		super("asteroid.png", x, y, 150, 150);
		xVelocity = 0;
	}

	public void reset() {
		super.resetCoordinates(900, getY());
	}
	
	public void resetRandomY() {
		super.resetCoordinates(900, Math.random()*450);
	}
	
	public void changeY() {
		super.resetCoordinates(super.getX(), Math.random()*450);
	}
	
	public void changeX() {
		super.resetCoordinates(1100, getY());
	}
	
	public void move() {
		super.moveSideways(true, 3 + xVelocity);
		xVelocity += 0.003;
	}
	
	public boolean getIsChecked() {
		return isChecked;
	}
	
	public void setIsChecked(boolean isAsteroidChecked) {
		isChecked = isAsteroidChecked;
	}
	
}
