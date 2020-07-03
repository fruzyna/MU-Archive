import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * BullsEye.java
 * Basic class that draws a black and white bull's eye
 * 
 * @author mail929
 * @version 1.0 3/8/2016
 */
public class BullsEye extends JFrame {
	final int WIDTH = 500;
	final int HEIGHT = 500;
	
	public static void main(String[] args) {
		new BullsEye();
	}
	
	/**
	 * Constructor for the BullsEye, sets up the window.
	 */
	public BullsEye() {
		super("BullsEye");
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
			g.fillOval(0, 0, WIDTH, HEIGHT);
			
			g.setColor(Color.WHITE);
			g.fillOval(50, 50, WIDTH - 100, HEIGHT - 100);
			
			g.setColor(Color.BLACK);
			g.fillOval(100, 100, WIDTH - 200, HEIGHT - 200);
			
			g.setColor(Color.WHITE);
			g.fillOval(150, 150, WIDTH - 300, HEIGHT - 300);
			
			g.setColor(Color.BLACK);
			g.fillOval(200, 200, WIDTH - 400, HEIGHT - 400);
		
	}
}
