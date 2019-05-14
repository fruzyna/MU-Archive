package cosc3550.gamemodes;

import cosc3550.Main;
import cosc3550.object.Alert;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

/**
 * GameOverMode
 * Displays that you died and the game is over.
 * Allows the player back to the main menu.
 * @author Liam Fruzyna and Katy Weathington
 */
public class GameOverMode extends GameMode {
	Alert gameover;
	String lines[] = {"Game Over!", "Press ENTER to return to menu."};
	
	@Override
	// creates an alert
	public void init() {
		gameover = new Alert(lines, true, Alert.MID);
	}

	@Override
	// listens for enter
	public void setHandlers(Scene scene) {
		scene.setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case ENTER:
				Main.changeMode(new MenuMode());
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void update() {}

	@Override
	public void render(GraphicsContext gc) {
		gameover.render(gc);
	}

	@Override
	public void end() {}

}