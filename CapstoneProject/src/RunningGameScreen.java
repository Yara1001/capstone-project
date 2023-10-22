import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;


public class RunningGameScreen extends JPanel implements KeyListener, Runnable
{
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private boolean paused;
	private boolean upKey, downKey;
	
	public int score;
	private Astronaut spaceman;
	private ArrayList<Asteroid> asteroids;
	private JLabel scoreOutput;
			
	private Sprite background;

	private SpaceMan m;

	public RunningGameScreen (SpaceMan m) {
		super();
		
		this.m = m;
		paused = false;
		
		spaceman = new Astronaut(200, 260);
		asteroids = new ArrayList<Asteroid>();
		
		for (int i = 0; i < 2; i++) {
			Asteroid anAsteroid = new Asteroid(900, (int)(Math.random()*450));
			asteroids.add(anAsteroid);
		}
		
		while (Math.abs(asteroids.get(1).getY()- asteroids.get(0).getY()) < 150) {
			asteroids.get(1).changeY();
		}
		asteroids.get(1).changeX();
		
		background = new Sprite("background1.jpg", 0, 0, 800, 600);
		
		new Thread(this).start();
	}
	
	

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background
		

		int width = getWidth();
		int height = getHeight();

		double ratioX = (double)width/DRAWING_WIDTH;
		double ratioY = (double)height/DRAWING_HEIGHT;

		((Graphics2D)g).scale(ratioX,ratioY);  // This is an easy way to make the contents of the window scale
		
		background.draw(g, this); //SOMETHING WRONG HERE WON'T WORK
		
		spaceman.draw(g, this);
		
		setFont(new Font("Monospaced", Font.BOLD, 28));
		g.drawString("Score : " + score, 30, 30);

		for(int i = 0; i < asteroids.size(); i++) {
			Asteroid anAsteroid = asteroids.get(i);
			anAsteroid.draw(g, this);
		}

	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upKey = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downKey = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upKey = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downKey = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}


	public void run() {
		//Every part of the game is in this loop which is why we want it to be an infinite loop
		while(true) {
		  	
			for(int i = 0; i < asteroids.size(); i++) {
				Asteroid anAsteroid = asteroids.get(i);
				if(spaceman.doesSpritePixelsCollide(anAsteroid)) {
					paused = true;
					asteroids.get(1).resetRandomY();
					asteroids.get(0).resetRandomY();
					while (Math.abs(asteroids.get(1).getY()- asteroids.get(0).getY()) < 150) {
						asteroids.get(1).changeY();
					}
					asteroids.get(1).changeX();
					m.getPanel2().t.setInitialDelay(1000);
					m.getPanel2().t.start();
					m.changePanel("2");
					break;
				}
			}
			
			
			if(!paused) {
				
				if (upKey) 
			  		spaceman.up();
			  	if (downKey) 
			  		spaceman.down();
				
				for(int i = 0; i < asteroids.size(); i++) {
					Asteroid anAsteroid = asteroids.get(i);
					
					if (anAsteroid.getX() < -10 && !anAsteroid.getIsChecked()){
						score += 5;
						anAsteroid.setIsChecked(true);
					}
					
					if (anAsteroid.getX() > -150) {
						anAsteroid.move();
					} else {
						anAsteroid.resetRandomY();
						anAsteroid.setIsChecked(false);
						while (Math.abs(asteroids.get(1).getY()- asteroids.get(0).getY()) < 150) {
							anAsteroid.changeY();
							
						}
					}
					
				}
			}
			
			
	
			// SHOW THE CHANGE
			repaint();

			// WAIT
			//17 milliseconds is approx a 1/60 of a second so 60 fps
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void setPaused(boolean p) {
		this.paused = p;
	}


}
