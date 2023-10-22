import java.awt.Image;

public class Astronaut extends Sprite {
	private int speed = 7;
	
	public Astronaut(int x, int y) {
		super("astronaut.png", x, y, 87, 106);
	}
	
	public void up() {
		
		speed += 0.1;
		super.moveUp(speed);
		
	}
	
	public void down() {
		speed += 0.1;
		super.moveDown(speed);
		
	}


}
