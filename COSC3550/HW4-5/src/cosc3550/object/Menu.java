package cosc3550.object;
import cosc3550.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Menu
 * Displays a list of selectable options to the player.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Menu {
	public static final int TOP = 0;
	public static final int MID = 1;
	public static final int BTM = 2;
	
	Text options[];
	public int selected;
	Font font;
	int sx;
	int sy;
	
	double width;
	double height;

	int size = 16;
	int spacing = 5;
	
	// requires a list of options, the default selection offset position, and vertical position
	public Menu(String options[], int selected, int x, int y, int vPos)
	{
		this.selected = selected;
		
		// set up font and text dimensions
		this.options = new Text[options.length];
		font = Font.font("SansSerif", FontWeight.BOLD, size);
		width = 0;
		height = size + spacing*3;
		for(int i = 0; i < options.length; i++) {
			Text option = new Text(options[i]);
			option.setFont(font);
			this.options[i] = option;
			
			double tWidth = option.getLayoutBounds().getWidth();
			if(width < tWidth)
				width = tWidth;
		}
		width += spacing * 2;

		sx = x;
		sy = y;
		// set y position based on vertical selection
		if(vPos == MID)
			sy = (int) (Main.HEIGHT/2 - (height * (options.length * 2 - 1))/2);
		else if(vPos == TOP)
			sy = y;
		else if(vPos == BTM)
			sy = (int) (Main.HEIGHT - (height * (options.length * 2 - 1))) - y;
	}
	
	// shift the selection up/down
	public void move(boolean down) {
		if(down)
			selected++;
		else
			selected--;
		
		// limit at top/bottom
		if(selected < 0)
			selected = 0;
		else if(selected >= options.length)
			selected = options.length - 1;
	}
	
	// render each option similar to an alert
	public void render(GraphicsContext gc)
	{
		int y = sy;
		int x = sx;
		for(int i = 0; i < options.length; i++)
		{
			Text option = options[i];
			
			// calculate width and height of the dialog box
			double tWidth = option.getLayoutBounds().getWidth();

			// draw box background
			if(i == selected)
				gc.setFill(Color.GOLDENROD);
			else
				gc.setFill(Color.WHITE);
			gc.fillRect(x, y, width, height);

			// draw box border
			if(i == selected)
				gc.setStroke(Color.GOLD);
			else
				gc.setStroke(Color.BLACK);
			gc.setLineWidth(5);
			gc.strokeRect(x, y, width, height);
			
			double tx = x + width/2 - tWidth/2;
			double ty = y + size + spacing;
			
			// draw each line of text
			gc.setFill(Color.BLACK);
			gc.setFont(font);
			gc.fillText(option.getText(), tx, ty);
			
			y += height * 2;
		}
	}
}
