package cosc3550.gamemodes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;

/**
 * Gamemode
 * Structure for a game mode.
 * @author Liam Fruzyna and Katy Weathington
 *
 */
public abstract class GameMode {

	// run when mode is entered
	public abstract void init();

	// used to setup mode's keyboard/mouse handlers
	public abstract void setHandlers(Scene scene);

	// runs on every loop iteration
	public abstract void update();
	
	// draws on every frame
	public abstract void render(GraphicsContext gc);
	
	// run when mode is left
	public abstract void end();
}
