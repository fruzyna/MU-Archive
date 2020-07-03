import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * House.java
 * A basic program that draws a basic house.
 * 
 * @author mail929
 * @version 1.0.0 3/8/2016
 */
public class House extends JFrame {
	final int WIDTH = 500;
	final int HEIGHT = 500;
	
	public static void main(String[] args) {
		new House();
	}
	
	/**
	 * Constructor for House, sets up the window.
	 */
	public House() {
		super("House");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		repaint();
	}

	/**
	 * Draws the house on the frame.
	 * 
	 * @param g Graphics used for drawing.
	 */
	public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			g.setColor(Color.BLACK);
			g.drawLine(250, 50, 150, 200);
			g.drawLine(250, 50, 350, 200);
			g.drawRect(150, 200, 200, 200);
			g.drawRect(200, 300, 50, 100);
			g.drawRect(275, 300, 50, 50);
	}
}