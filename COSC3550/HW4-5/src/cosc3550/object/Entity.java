package cosc3550.object;

import cosc3550.Main;
import cosc3550.Screen;
import cosc3550.gamemodes.PlayMode;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

/**
 * Entity
 * Movable object not locked to grid position.
 * Entity class isn't to be used on its own.
 * @author Liam Fruzyna and Katy Weathington
 */
public abstract class Entity {
	public static final char PLAYER = 'P';
	public static final char PLAYER_SPAWN = 'S';
	public static final char TEST = 'T';
	
	public char type;
	public int spawnX;
	public int spawnY;
	public double x;
	public double y;
	double width;
	double height;
	char direction = 'L';
	int health;
	int defaultHealth;
	public BoundingBox bb;
	public BoundingBox meleeb;
	
	// requires type character, grid position, and health
	public Entity(char type, int x, int y, int health) {
		this.type = type;
		defaultHealth = health;
		spawnX = x;
		spawnY = y;

		width = Main.WIDTH / PlayMode.WIDTH;
		height = Main.HEIGHT / PlayMode.HEIGHT;
		reset();
	}

	public abstract void update(Player player, Screen screen);

	public abstract void render(GraphicsContext gc);
	
	// reset every time a room is entered
	public void reset() {
		x = spawnX * width + width/2;
		y = spawnY * height + height/2;
		health = defaultHealth;
		createBB();
	}

	// creates a bounding box based on position and dimensions
	public void createBB() {
		double bw = 0.5 * width;
		double bh = 0.7 * height;
		bb = new BoundingBox(x - bw/2, y - bh/2, bw, bh);
		
		switch(direction) {
		case 'U':
			meleeb = new BoundingBox(getLeft(), y - height, width, height);
			break;
		case 'D':
			meleeb = new BoundingBox(getLeft(), y, width, height);
			break;
		case 'L':
			meleeb = new BoundingBox(x - width, getTop(), width, height);
			break;
		case 'R':
			meleeb = new BoundingBox(x, getTop(), width, height);
			break;
		}
	}
	
	// move entity based on input, undo reverses input
	public void move(boolean up, boolean down, boolean left, boolean right, int speed, boolean undo) {
		int xv = 0;
		int yv = 0;
		// move in the given directions
		if(up)
			yv -= speed;
		if(down)
			yv += speed;
		if(left)
			xv -= speed;
		if(right)
			xv += speed;
		
		// reverse if undoing movement
		if(undo) {
			xv *= -1;
			yv *= -1;
		}
		else {
			// determine direction player is facing
			if(xv > 0) {
				direction = 'R';
			}
			else if(xv < 0) {
				direction = 'L';
			}
			else if(yv > 0) {
				direction = 'D';
			}
			else if(yv < 0) {
				direction = 'U';
			}
		}
		
		// adjust the coordinates
		shift(xv, yv);
	}
	
	// actually move the entity
	public void shift(int dx, int dy) {
		x += dx;
		y += dy;
		
		// adjust the bounding box
		createBB();
	}

	// returns if entity is alive
	public boolean isAlive() {
		return health > 0;
	}
	
	// checks if entity is successfully attacked
	public boolean attacked(BoundingBox attackb, int pow) {
		if(isAlive()) {
			if(attackb.intersects(bb)) {
				health -= pow;
				if(health < 0)
					health = 0;
				return true;
			}
		}
		return false;
	}
	
	// gets the position in blocks
	public int[] getBlockPos() {
		int bx = (int) (x / width);
		int by = (int) (y / height);
		int block[] = {bx, by};
		return block;
	}
	
	// gets left side position
	public double getLeft() {
		return x - width/2;
	}
	
	// gets right side position
	public double getRight() {
		return x + width/2;
	}
	
	// gets top side position
	public double getTop() {
		return y - height/2;
	}
	
	// gets bottom side position
	public double getBottom() {
		return y + height/2;
	}
}
