import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Cannon object shoots balls in the direction of the mouse cursor.
 * @author Liam Fruzyna
 */
public class Cannon
{
	double x;
	double y;
	
	public Cannon()
	{
		x = -10;
		y = -10;
	}
	
	void update(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	Ball shoot(double x, double y, double xDir, double yDir)
	{
		if(xDir > Math.abs(yDir))
		{
			// scale the y proportionally to the x
			yDir = 3 * yDir / xDir;
			xDir = 3;
		}
		else
		{
			// scale the x proportionally to the y
			xDir = 3 * xDir / Math.abs(yDir);
			yDir = 3 * yDir / Math.abs(yDir);
		}
		
		// generate a new ball at the start with the calculated speed
		double speed[] = {xDir, yDir};
		return new Ball(x, y, 10, speed);
	}
	
	void render(GraphicsContext gc)
	{
		gc.setStroke(Color.RED);
		gc.setLineWidth(3);
		gc.strokeLine(x - 5, y, x + 5, y);
		gc.strokeLine(x, y - 5, x, y + 5);
	}
}
