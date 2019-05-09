import javafx.scene.canvas.GraphicsContext;

/**
 * Wall object loops down the screen, stopping balls.
 * @author Liam Fruzyna
 */
public class Wall
{
	double x;
	double y;
	
	double height;
	double width;
	
	double speed;
	
	public Wall(double x, double y, double width, double height, double speed)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
	}
	
	void update()
	{
		// move the wall in the direction of its speed
		y += speed;
		
		// move back to the bottom
		if(y > HW2.HEIGHT)
		{
			y -= HW2.HEIGHT;
		}
	}
	
	boolean hit(Ball ball)
	{
		if(ball.x >= x - width && ball.x <= x + width)
		{
			// the ball lines up horizontally with the wall
			if(y + height > HW2.HEIGHT)
			{
				// the wall wraps to the bottom
				if(ball.y >= y)
				{
					// the ball is above the start of the wall
					return true;
				}
				else if(ball.y <= (y + height - HW2.HEIGHT))
				{
					// the ball is below the end of the wall
					return true;
				}
			}
			else if(ball.y <= (y + height) && ball.y >= y)
			{
				// the ball lines up vertically with the wall
				return true;
			}
		}
		return false;
	}
	
	void render(GraphicsContext gc)
	{
		gc.setStroke(Theme.getForeground());
		gc.setLineWidth(width);
		
		gc.strokeLine(x, y, x, y+height);
		if(y+height > HW2.HEIGHT)
		{
			// wrap the wall around to the bottom
			gc.strokeLine(x, 0, x, (y+height) - HW2.HEIGHT);
		}
	}
}
