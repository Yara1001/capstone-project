import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Sprite {

	// FIELDS

	private double x, y;
	private int width, height;
	private Image image;
	private double yVelocity;
	
	// CONSTRUCTORS

	public Sprite(String filename, int x, int y, int w, int h) {
		this((new ImageIcon(filename)).getImage(),x,y,w,h);
	}

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Sprite(Image img, int x, int y, int w, int h) {
		image = img;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}

	
	
	public void moveSideways(boolean direction, double amt) {
		if (direction == true)
		{
			x -= amt;
		} else if (direction == false)
		{
			x += amt;
		}
	}
	
	public void moveUp(double z) {
		y -= z;
		if (y < 0) {
			y += z;
		}
		
	}
	
	public void moveDown(double z) {
		y += z;
		if (y > 500) {
			y -= z;
		}
	}
	
	public boolean doesSpritePixelsCollide(Sprite other) {
		BufferedImage pic = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Make a new image that I can draw on
		Graphics g = pic.getGraphics(); // This Graphics will draw on to the image
		g.setColor(Color.BLUE); 
		g.fillRect(0, 0, pic.getWidth(), pic.getHeight()); // Make the whole image white
		g.drawImage(image,0,0,width,height,null);
		
		int overlapLeft = Math.max((int)x, (int)other.x); // Find the rectangle of space in which the 2 sprite images overlap with each other
		int overlapTop = Math.max((int)y, (int)other.y);
		int overlapRight = Math.min((int)(width+x), (int)(other.width+other.x));
		int overlapBottom = Math.min((int)(height+y), (int)(other.height+other.y));
		
		for (int i = overlapLeft; i < overlapRight; i++ ) {   // Look at every pixel coordinate in the rectangle
			for (int j = overlapTop; j < overlapBottom; j++ ) {
				if (pic.getRGB((int)(i-x), (int)(j-y)) != Color.BLUE.getRGB()) {  // If that pixel is not white (you can also look for a specific color instead of any non-white pixel)
					return true;  // There was a collision!
				}
			}  
		}
		
		return false;
	}
	
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(image, (int)x, (int)y, width, height, io);
	}
	
	public void resetCoordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void resetWidth(int width) {
		this.width = width;
	}
	
	public void resetHeight(int height) {
		this.height = height;
	}
	
	public void resetImage(Image img) {
		this.image = img;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


} 