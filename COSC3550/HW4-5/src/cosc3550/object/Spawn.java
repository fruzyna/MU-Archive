package cosc3550.object;

import cosc3550.Screen;
import javafx.scene.canvas.GraphicsContext;

/**
 * Spawn
 * Custom entity representing player spawn point.
 * Doesn't actually do anything or appear.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Spawn extends Entity {
	// requires only the grid location
	public Spawn(int x, int y) {
		super(Entity.PLAYER_SPAWN, x, y, 0);
	}

	@Override
	public void update(Player player, Screen screen) {}

	@Override
	public void render(GraphicsContext gc) {}
}
