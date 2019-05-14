package cosc3550.object;

import cosc3550.Main;
import cosc3550.Screen;
import cosc3550.gamemodes.PlayMode;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

/**
 * Bullet
 * Can be shot from entities for ranged attacks.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Bullet {
	double x;
	double y;
	int width = 8;
	int height = 8;
	int xv;
	int yv;
	
	int strength;
	
	// requires screen position, velocities, and strength
	public Bullet(double x, double y, int xv, int yv, int strength) {
		this.x = x;
		this.y = y;
		this.xv = xv;
		this.yv = yv;
		this.strength = strength;
	}

	// check for collisions on update
	public boolean update(Screen screen) {
		x += xv;
		y += yv;
		
		if(x < 0 || x > Main.WIDTH || y < 0 || y > Main.HEIGHT)
			return true;
		
		BoundingBox bb = new BoundingBox(x-width/2, y-height/2, width, height);
		for(Entity e : screen.entities) {
			if(e.attacked(bb, strength))
				return true;
		}
		for(Block b : screen.blocks) {
			if(!b.passable && b.bb.intersects(bb))
				return true;
		}
		return false;
	}
	
	// draw as goldenrod oval
	public void render(GraphicsContext gc) {
		if(xv > 0)
			gc.drawImage(PlayMode.sprites.bullet[2], x-width/2, y-height/2);
		else if(xv < 0)
			gc.drawImage(PlayMode.sprites.bullet[3], x-width/2, y-height/2);
		else if(yv > 0)
			gc.drawImage(PlayMode.sprites.bullet[1], x-width/2, y-height/2);
		else if(yv < 0)
			gc.drawImage(PlayMode.sprites.bullet[0], x-width/2, y-height/2);
	}
}
