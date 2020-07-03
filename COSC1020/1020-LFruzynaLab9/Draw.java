import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Draw.java
 * 
 * Basic MSPaint.exe like application for drawing shapes
 * 
 * @author mail929
 * @version 1 3/15/16
 */
public class Draw extends JFrame implements ActionListener, WindowListener, MouseListener, MouseMotionListener
{
	//buttons
	JButton color;
	JButton line;
	JButton circle;
	JButton rect;
	JButton arc;
	
	//shapes and color
	Color c = Color.BLACK;
	final int LINE = 0;
	final int CIRCLE = 1;
	final int RECTANGLE = 2;
	final int ARC = 3;
	int shape = LINE;
	
	//click positions
	int startx;
	int starty;
	int endx = -1;;
	int endy;
	
	public static void main(String[] args)
	{
		new Draw();
	}
	
	/**
	 * Constructor for Draw. Creates and fills out the window.
	 */
	public Draw()
	{
		//basic jframe setup
		super("Draw");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//set up of button panel on bottom
		JPanel container = new JPanel();
		//creates buttons
		color = new JButton("Color");
		line = new JButton("Line");
		circle = new JButton("Circle");
		rect = new JButton("Rectangle");
		arc = new JButton("Arc");
		//adds buttons to panel
		container.add(color);
		container.add(line);
		container.add(circle);
		container.add(rect);
		container.add(arc);
		//adds listeners to buttons
		color.addActionListener(this);
		line.addActionListener(this);
		circle.addActionListener(this);
		rect.addActionListener(this);
		arc.addActionListener(this);
		//adds panel to window
		add(container, BorderLayout.SOUTH);
		
		//adds necessary listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		addWindowListener(this);
		
		//makes the window visible
		setVisible(true);

	}

	/**
	 * Draws the current shape with the last pressed and released positions
	 * @param g Graphics used for drawing
	 */
	public void paint(Graphics g) {
		//super.paint(g);
		System.out.println("Painting");
		
		//switches x and y coords so startx/y is always top left
		if(shape != LINE) {
			if(startx > endx) {
				int temp = startx;
				startx = endx;
				endx = temp;
			}
			if(starty > endy) {
				int temp = starty;
				starty = endy;
				endy = temp;
			}
		}
		
		//sets the color to the last chosen
		g.setColor(c);
		
		switch(shape) {
		case LINE:
			//draws a line
			System.out.println("Line");
			g.drawLine(startx, starty, endx, endy);
			break;
		case CIRCLE:
			//draws a circle
			System.out.println("Circle");
			g.drawOval(startx, starty, endx - startx, endy - starty);
			break;
		case RECTANGLE:
			//draws a rectangle
			System.out.println("Rectangle");
			g.drawRect(startx, starty, endx - startx, endy - starty);
			break;
		case ARC:
			//draws an arc
			System.out.println("Arc");
			g.drawArc(startx, starty, endx - startx, endy - starty, 90, -90);
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//saves the x and y positions when mouse is pressed
		System.out.println("Mouse Presssed");
		startx = e.getX();
		starty = e.getY();
		endx = e.getX();
		endy = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//saves the x and y positions when mouse is released
		System.out.println("Mouse Released");
		endx = e.getX();
		endy = e.getY();
		repaint();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//creates a popup if the x button is pressed
		int confirm = JOptionPane.showConfirmDialog(this, "You sure?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
		
		//if ok is chosen program is closed
		if(confirm == 0){
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(color)) {
			//makes a color chooser if color is chosen
		      c = JColorChooser.showDialog(null, "Choose a Color", c);
		}
		else if(e.getSource().equals(line)) {
			//changes shape to a line
			shape = LINE;
		}
		else if(e.getSource().equals(circle)) {
			//changes shape to a circle
			shape = CIRCLE;
		}
		else if(e.getSource().equals(rect)) {
			//changes shape to a rectangle
			shape = RECTANGLE;
		}
		else if(e.getSource().equals(arc)) {
			//changes shape to an arc
			shape = ARC;
		}
	}
	
	

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		//draw every time the mouse is move if its a line
		if(shape == LINE) {
			startx = endx;
			starty = endy;
			endx = e.getX();
			endy = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

}
