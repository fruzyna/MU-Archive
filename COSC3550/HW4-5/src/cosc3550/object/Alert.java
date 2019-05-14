package cosc3550.object;
import cosc3550.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Alert
 * Displays a message of lines to the player.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Alert
{
	public static final int TOP = 0;
	public static final int MID = 1;
	public static final int BTM = 2;
	
	String lines[];
	boolean visible;
	int pos;
	
	int height;
	int width;
	int widths[];
	Font font;
	int x;
	int y;
	
	// requires the lines of the message, if it should be displayed and its vertical position.
	public Alert(String lines[], boolean visible, int pos)
	{
		this.lines = lines;
		this.visible = visible;
		this.pos = pos;

		font = Font.font("SansSerif", FontWeight.BOLD, size);
		
		// calculate width and height of the dialog box
		height = (size + spacing) * lines.length + spacing*2;
		width = 0;
		widths = new int[lines.length];
		for(int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			Text t = new Text(line);
			t.setFont(font);
			int tWidth = (int) t.getLayoutBounds().getWidth();
			widths[i] = tWidth;
			if(tWidth > width)
			{
				width = tWidth;
			}
		}
		width += spacing*2;

		// calculate x and y of the box
		x = Main.WIDTH/2 - width/2;
		y = 0;
		if(pos == MID)
			y = Main.HEIGHT/2 - height/2;
		else if(pos == TOP)
			y = size;
		else if(pos == BTM)
			y = Main.HEIGHT - size - height;
	}
	
	// sets the visibility of the dialog
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	// toggles visibility
	public void toggle()
	{
		visible = !visible;
	}

	int size = 16;
	int spacing = 5;
	
	// draws the text with a background and border
	public void render(GraphicsContext gc)
	{
		if(visible)
		{
			// draw box background
			gc.setFill(Color.WHITE);
			gc.fillRect(x, y, width, height);

			// draw box border
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(5);
			gc.strokeRect(x, y, width, height);
			
			// setup text font
			gc.setFill(Color.BLACK);
			gc.setFont(font);
			
			// draw each line of text
			int yl = y;
			for(int i = 0; i < lines.length; i++)
			{
				int shift = (width - widths[i]) / 2;
				yl += size + spacing;
				gc.fillText(lines[i], x + shift, yl);
			}
		}
	}
}
