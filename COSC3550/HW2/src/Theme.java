import javafx.scene.paint.Color;

/**
 * Theme static class manages and keeps track of the current color scheme.
 * @author Liam Fruzyna
 */
public class Theme
{
	static Color background[] = {Color.PEACHPUFF, Color.WHITE, Color.web("#5CE800")};
	static Color foreground[] = {Color.DEEPSKYBLUE, Color.BLACK, Color.web("#FFCC00")};
	static int colorSet = 0;
	
	static void next()
	{
		colorSet++;
		if(colorSet >= background.length)
		{
			colorSet = 0;
		}
	}
	
	static Color getBackground()
	{
		return background[colorSet];
	}
	
	static Color getForeground()
	{
		return foreground[colorSet];
	}
}
