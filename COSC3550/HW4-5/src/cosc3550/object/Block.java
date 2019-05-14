package cosc3550.object;

import cosc3550.Main;
import cosc3550.gamemodes.PlayMode;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

/**
 * Block
 * Top level object that is not movable.
 * Defaults are air and wall.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Block {
	public static final char WALL = 'x';
	public static final char AIR = '_';
	public static final char DOOR = 'd';

	char type;
	boolean passable;
	int x;
	int y;
	double width;
	double height;
	public BoundingBox bb;
	
	// Requires the character representation of the object and grid position
	public Block(char obj, int x, int y) {
		type = obj;
		this.x = x;
		this.y = y;

		width = Main.WIDTH / PlayMode.WIDTH;
		height = Main.HEIGHT / PlayMode.HEIGHT;
		bb = new BoundingBox(x * width, y * height, width, height);
		
		reset();
	}
	
	// Run every time a room is entered
	public void reset() {
		switch(type) {
		case WALL:
			passable = false;
			break;
		default:
			passable = true;
		}
	}
	
	// Default items don't update
	public void update(Player player) {
	}
	
	// Only draw if it is passable
	public void render(GraphicsContext gc) {
		switch(type) {
		case WALL:
			gc.drawImage(PlayMode.sprites.wall, x*width, y*height);
			break;
		case AIR:
			gc.drawImage(PlayMode.sprites.floor, x*width, y*height);
			break;
		}
	}
}
