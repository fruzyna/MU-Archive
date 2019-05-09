import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Scoreboard object keeps track of and display the amount of balls to reach the end.
 * @author Liam Fruzyna
 */
public class Scoreboard
{
	int score;
	
	public Scoreboard()
	{
		score = 0;
	}
	
	void increase()
	{
		score++;
	}
	
	void render(GraphicsContext gc)
	{
		Font font = Font.font("SansSerif", FontWeight.BOLD, 24);
		gc.setFill(Theme.getForeground());
		gc.setFont(font);
		gc.fillText(""+score, 15, 25);
	}
}
