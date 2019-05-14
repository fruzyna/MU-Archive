package cosc3550.gamemodes;
import cosc3550.Main;
import cosc3550.object.Alert;
import cosc3550.object.Menu;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * MenuMode
 * First gamemode where game difficulty is selected.
 * @author Liam Fruzyna and Katy Weathington
 */
public class MenuMode extends GameMode {
	String options[] = {"Start Easy", "Start Hard", "Exit"};
	
	Alert title;
	Menu menu;
	
	@Override
	// initialize objects
	public void init() {
		String lines[] = {"Unnamed", "Dungeon", "Crawler"};
		title = new Alert(lines, true, Alert.MID);
		menu = new Menu(options, 0, 15, 15, Menu.MID);
	}

	@Override
	// handle menu controls
	public void setHandlers(Scene scene) {
		scene.setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case UP:
				menu.move(false);
				break;
			case DOWN:
				menu.move(true);
				break;
			case ENTER:
				select(menu.selected);
				break;
			default:
				break;
			}
		});
	}

	// responds to a menu item being selected
	public void select(int selection) {
		switch(selection) {
		case 0:
			// play game in easy mode
			Main.changeMode(new PlayMode(PlayMode.Difficulty.EASY));
			break;
		case 1:
			// play game in hard mode
			Main.changeMode(new PlayMode(PlayMode.Difficulty.HARD));
			break;
		case 2:
			// exit game
			Platform.exit();
			break;
		}
	}

	@Override
	// draw menu and title
	public void render(GraphicsContext gc) {
		gc.setFill(Color.INDIANRED);
		gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		menu.render(gc);
		title.render(gc);
	}

	@Override
	public void update() {}

	@Override
	public void end() {}

}
