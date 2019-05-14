package cosc3550.gamemodes;

import java.io.IOException;
import java.util.Map;

import cosc3550.GameReader;
import cosc3550.Main;
import cosc3550.Screen;
import cosc3550.SpriteManager;
import cosc3550.object.Alert;
import cosc3550.object.Player;
import cosc3550.object.Scoreboard;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * PlayMode
 * Main gamemode where the game is actually played.
 * @author Liam Fruzyna and Katy Weathington
 */
public class PlayMode extends GameMode {
	public enum Difficulty {EASY, HARD}
	
	public static int WIDTH = 10;
	public static int HEIGHT = 10;
	
	int BOSS_X = -1;
	int BOSS_Y = -1;
	
	Screen screens[][];
	int currentX = 0;
	int currentY = 0;
	int xTotal = 0;
	int yTotal = 0;
	
	Player player;
	public static Difficulty difficulty;
	Scoreboard board;
	Alert ruleBoard;
	
	public static SpriteManager sprites;
	
	// Requires only the difficulty level
	public PlayMode(Difficulty difficulty) {
		PlayMode.difficulty = difficulty;
	}
	
	@Override
	// Read in level file an initialize any objects
	public void init() {
		try {
			// prepare sprite map
			sprites = new SpriteManager();
			
			// read rules.txt
			Map<String, Integer> rules = GameReader.readRules("rules.txt");
			WIDTH = rules.get("ROOM_WIDTH");
			HEIGHT = rules.get("ROOM_HEIGHT");
			BOSS_X = rules.get("BOSS_ROOM_X");
			BOSS_Y = rules.get("BOSS_ROOM_Y");
			
			// read level.txt
			char blocks[][] = GameReader.readLevel("level.txt", rules.get("CHEAT_MODE") == 1);
			
			// build screen objects
			xTotal = blocks[0].length/WIDTH;
			yTotal = blocks.length/HEIGHT;
			screens = new Screen[xTotal][yTotal];
			for(int y = 0; y < blocks.length; y += HEIGHT) {
				for(int x = 0; x < blocks[y].length; x += WIDTH) {
					Screen s = new Screen(blocks, x, y, WIDTH, HEIGHT);
					screens[x/WIDTH][y/HEIGHT] = s;
					int spawn[] = s.getPlayerSpawn();
					if(spawn.length > 0) {
						player = new Player(x + spawn[0], y + spawn[1]);
					}
				}
			}
			
			// create rule board, show if default is to
			String lines[] = {"UNAMED DUNGEON CRAWLER", "CONTROLS", "Arrow Keys - Move", "Z - Shoot", "X - Melee", "R - Show/Hide Rules", "Esc - Quit to Menu", "STATUS ICONS (Top Left)", "Time, Health, Ammo, Keys"};
			ruleBoard = new Alert(lines, rules.get("DISP_RULES") == 1, Alert.MID);
		} catch (IOException e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
		}
		
		board = new Scoreboard(10, 10, 16, 16);
	}

	// input states
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;
	
	@Override
	// Listen for keyboard input for player controls
	public void setHandlers(Scene scene) {
		// listen for press
		scene.setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case DOWN:
				down = true;
				break;
			case UP:
				up = true;
				break;
			case LEFT:
				left = true;
				break;
			case RIGHT:
				right = true;
				break;
			case X:
				player.melee(screens[currentX][currentY]);
				break;
			case Z:
				player.shoot();
				break;
			case ESCAPE:
				Main.changeMode(new MenuMode());
				break;
			case R:
				ruleBoard.toggle();
			default:
				break;
			}
		});
		// listen for release
		scene.setOnKeyReleased(e -> {
			switch(e.getCode()) {
			case DOWN:
				down = false;
				break;
			case UP:
				up = false;
				break;
			case LEFT:
				left = false;
				break;
			case RIGHT:
				right = false;
				break;
			default:
				break;
			}
		});
	}

	boolean def = false;
	
	@Override
	public void update() {
		// update player and screen
		player.update(up, down, left, right, screens[currentX][currentY]);
		screens[currentX][currentY].update(player);
		
		// switch to win mode if game won
		if(def) {
			Main.changeMode(new WinMode(board.getTime()));
		}
		// check if boss room is defeated (allow one more loop)
		def = player.hasKey && currentX == BOSS_X && currentY == BOSS_Y;
		
		// shift screen when the player reaches the edge
		boolean switched = true;
		if(player.y < 0 && currentY > 0) {
			// hit top
			currentY--;
			player.shift(0, Main.HEIGHT);
		}
		else if(player.y > Main.HEIGHT && currentY < yTotal-1) {
			// hit bottom
			currentY++;
			player.shift(0, 0-Main.HEIGHT);
		}
		else if(player.x < 0 && currentX > 0) {
			// hit left
			currentX--;
			player.shift(Main.WIDTH, 0);
		}
		else if(player.x > Main.WIDTH && currentX < xTotal-1) {
			// hit right
			currentX++;
			player.shift(0-Main.WIDTH, 0);
		}
		else
			switched = false;
		
		if(switched)
			screens[currentX][currentY].reset();
		
		board.update(player);
	}

	@Override
	public void render(GraphicsContext gc) {
		// draw background
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		
		// draw objects
		screens[currentX][currentY].render(gc);
		player.render(gc);
		board.render(gc);
		
		ruleBoard.render(gc);
	}

	@Override
	public void end() {
	}
}
