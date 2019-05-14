package cosc3550.gamemodes;

import cosc3550.Main;
import cosc3550.object.Alert;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

/**
 * WinMode
 * Displays that you won and the game is over.
 * Allows the player back to the main menu.
 * @author Liam Fruzyna and Katy Weathington
 */
public class WinMode extends GameMode {
	Alert win;
	String lines[] = {"You Won!", "Good Job!", "Time: ", "Press ENTER to return to menu."};
	
	public WinMode(String time) {
		lines[2] += time;
	}
	
	@Override
	// creates an alert
	public void init() {
		win = new Alert(lines, true, Alert.MID);
	}

	@Override
	// waits for enter to return to the menu
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
		win.render(gc);
	}

	@Override
	public void end() {}

}