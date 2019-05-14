package cosc3550.object;

import cosc3550.gamemodes.PlayMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Scoreboard
 * Displays the player's health and remaining ammunition.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Scoreboard {
	int x;
	int y;
	int width;
	int height;
	
	int health;
	int ammo;
	int keys;
	long startTime;
	
	// Requires top left position and dimensions of points
	public Scoreboard(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		startTime = System.currentTimeMillis();
	}
	
	// Fetches the current health and ammunition from the player
	public void update(Player player) {
		health = player.health;
		ammo = player.bulletsRem;
		keys = player.keys.size();
	}
	
	// Draws red dots for health and gray dots for ammo
	public void render(GraphicsContext gc) {
		// write time since starting
		gc.setFill(Color.YELLOW);
		gc.fillText(getTime(), x, y+height);
		
		drawStat(gc, (int)(y+height*1.5), health, Color.RED);
		drawStat(gc, (int)(y+height*3), ammo, PlayMode.sprites.bulletIcon);
		drawStat(gc, (int)(y+height*4.5), keys, PlayMode.sprites.keyIcon);
	}
	
	// Gets the time since starting as a string
	public String getTime() {
		long delta = System.currentTimeMillis() - startTime;
		String sec = "" + ((delta/1000)%60);
		if(Integer.parseInt(sec) < 10) {
			sec = "0" + sec;
		}
		return (delta/60000) + ":" + sec;
	}

	// Draws a given stat with a given color
	public void drawStat(GraphicsContext gc, int y, int stat, Color color) {
		gc.setFill(color);
		int hx = x;
		for(int i = 0; i < stat; i++) {
			gc.fillRect(hx, y, width, height);
			hx += 1.5*width;
		}
	}

	// Draws a given stat with a given image
	public void drawStat(GraphicsContext gc, int y, int stat, Image img) {
		int hx = x;
		for(int i = 0; i < stat; i++) {
			gc.drawImage(img, hx, y);
			hx += 1.5*width;
		}
	}
}
