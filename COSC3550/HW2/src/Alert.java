import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Alert object displays a message of lines to the player.
 * @author Liam Fruzyna
 */
public class Alert
{
	String lines[];
	boolean visible;
	
	public Alert(String lines[], boolean visible)
	{
		this.lines = lines;
		this.visible = visible;
	}
	
	void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	void toggle()
	{
		visible = !visible;
	}

	int size = 16;
	int spacing = 5;
	
	void render(GraphicsContext gc)
	{
		if(visible)
		{
			// calculate width and height of the dialog box
			int height = (size + spacing) * lines.length + spacing*2;
			int width = 0;
			for(String line : lines)
			{
				if(line.length() > width)
				{
					width = line.length();
				}
			}
			width *= size/1.67;

			// calculate x and y of the box
			int x = HW2.WIDTH/2 - width/2;
			int y = HW2.HEIGHT/2 - height/2;

			// draw box background
			gc.setFill(Theme.getBackground());
			gc.fillRect(x, y, width, height);

			// draw box border
			gc.setStroke(Theme.getForeground());
			gc.setLineWidth(5);
			gc.strokeRect(x, y, width, height);
			
			// setup text font
			Font font = Font.font("SansSerif", FontWeight.BOLD, size);
			gc.setFill(Theme.getForeground());
			gc.setFont(font);
			
			// draw each line of text
			x += spacing;
			for(String line : lines)
			{
				y += size + spacing;
				gc.fillText(line, x, y);
			}
		}
	}
}
