package cosc3550.object;

import cosc3550.Screen;
import cosc3550.gamemodes.PlayMode;
import javafx.scene.canvas.GraphicsContext;

/**
 * Key
 * An extension of an entity to represent a key.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Key extends Entity {
	boolean grabbed;
	
	// requires the type and grid position
	public Key(char type, int x, int y) {
		super(type, x, y, 0);
		grabbed = false;
	}

	@Override
	// attempt to give user key
	public void update(Player player, Screen screen) {
		boolean success = player.giveKey(bb, type);
		if(success)
			grabbed = true;
	}

	@Override
	// draw key if not grabbed
	public void render(GraphicsContext gc) {
		if(!grabbed)
			gc.drawImage(PlayMode.sprites.key, getLeft(), getTop());
	}
}