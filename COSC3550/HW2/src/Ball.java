import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Ball object moves across the screen when shot.
 * @author Liam Fruzyna
 */
public class Ball
{
	double x;
	double y;
	double width;
	double speed[];
	
	public Ball(double x, double y, double width, double speed[])
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.speed = speed;
	}
	
	boolean update()
	{
		// move the ball in the direction of its speed
		x += speed[0];
		y += speed[1];
		
		// score at the end
		if(x > HW2.WIDTH)
		{
			return true;
		}
		
		// bounce off the top/bottom
		if(y <= 0 || y >= HW2.HEIGHT)
		{
			speed[1] = -speed[1];
		}
		return false;
	}
	
	void render(GraphicsContext gc)
	{
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(width);
		
		gc.strokeLine(x, y, x, y);
	}
}
