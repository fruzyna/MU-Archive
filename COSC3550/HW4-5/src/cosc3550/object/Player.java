package cosc3550.object;

import java.util.ArrayList;

import cosc3550.Main;
import cosc3550.Screen;
import cosc3550.gamemodes.GameOverMode;
import cosc3550.gamemodes.PlayMode;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * Player
 * Instance of entity that represents the player.
 * Not stored in a screen like other entities.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Player extends Entity {
	int meleeTimeout = Main.FPS / 3; // allow 3 hits per second
	int meleeCounter = 0;
	int meleeStrength;
	int rangedStrength = 2;
	int bulletsRem;
	
	ArrayList<Bullet> bullets;
	ArrayList<Integer> keys;
	public boolean hasKey;
	
	// requires only the grid position
	public Player(int x, int y) {
		super(Entity.PLAYER, x, y, 5);
		bulletsRem = 5;
		bullets = new ArrayList<>();
		keys = new ArrayList<>();
		
		if(PlayMode.difficulty == PlayMode.Difficulty.EASY) {
			meleeStrength = 3;
		}
		else {
			meleeStrength = 1;
		}
	}
	
	// doesn't use default update
	public void update(Player player, Screen screen) {}
	
	// custom update needs directional update and current screen
	public void update(boolean up, boolean down, boolean left, boolean right, Screen screen) {
		if(isAlive()) {
			// move vertically based on input
			move(up, down, false, false, 5, false);
			
			// attempt to block vertical movement
			for(Block block : screen.blocks) {
				if(bb.intersects(block.bb) && !block.passable) {
					move(up, down, false, false, 5, true); // undo movement
					break;
				}
			}
			
			// attempt to move horizontally
			move(false, false, left, right, 5, false);
			for(Block block : screen.blocks) {
				if(bb.intersects(block.bb) && !block.passable) {
					move(false, false, left, right, 5, true); // undo movement
					break;
				}
			}
	
			// update all bullets, remove if it has collided
			Bullet remove = null;
			for(Bullet b : bullets) {
				boolean hit = b.update(screen);
				if(hit)
					remove = b;
			}
			if(remove != null)
				bullets.remove(remove);
			
			// player has key if all entities in room are dead
			hasKey = true;
			for(Entity e : screen.entities) {
				if(e.isAlive()) {
					hasKey = false;
				}
			}
			
			// decrease meleeCounter until 0
			if(meleeCounter > 0)
				meleeCounter--;
		}
		else {
			Main.changeMode(new GameOverMode());
		}
	}
	
	// check all entities on screen when attacking
	public void melee(Screen screen) {
		if(meleeCounter == 0) {
			PlayMode.sprites.swing.play();
			meleeCounter = meleeTimeout;
			for(Entity e : screen.entities)
				e.attacked(meleeb, meleeStrength);
		}
	}
	
	// determines if the player can unlock a given key
	public boolean hasKey(int keyNum) {
		if(keyNum > 0) {
			return keys.contains(keyNum);
		}
		return hasKey;
	}
	
	// gives the player a key
	public boolean giveKey(BoundingBox keyb, int num) {
		if(keyb.intersects(bb) && !keys.contains(num)) {
			keys.add(num);
			return true;
		}
		return false;
	}
	
	// create a new bullet when shooting
	public void shoot() {
		if(bulletsRem > 0) {
			PlayMode.sprites.shoot.play();
			int xv = 0;
			int yv = 0;
			switch(direction) {
			case 'L':
				xv = -10;
				break;
			case 'R':
				xv = 10;
				break;
			case 'U':
				yv = -10;
				break;
			case 'D':
				yv = 10;
				break;
			}
			bullets.add(new Bullet(x, y, xv, yv, rangedStrength));
			bulletsRem--;
		}
	}

	@Override
	// draw player including its direction and attacks
	public void render(GraphicsContext gc) {
		if(isAlive()) {
			// draw player in direction
			switch(direction) {
			case 'U':
				gc.drawImage(PlayMode.sprites.player[1], getLeft(), getTop());
				break;
			case 'D':
				gc.drawImage(PlayMode.sprites.player[0], getLeft(), getTop());
				break;
			case 'L':
				gc.drawImage(PlayMode.sprites.player[2], getLeft(), getTop());
				break;
			case 'R':
				gc.drawImage(PlayMode.sprites.player[3], getLeft(), getTop());
				break;
			}
			
			// draw melee
			if(meleeCounter > 0) {
				gc.setFill(Color.rgb(0, 0, 255, meleeCounter/((double)meleeTimeout)));
				switch(direction) {
				case 'U':
					gc.fillArc(getLeft(), getTop() - height/10, width, height/10 * 2, 0, 180, ArcType.CHORD);
					break;
				case 'D':
					gc.fillArc(getLeft(), getBottom() - height/10, width, height/10 * 2, 180, 180, ArcType.CHORD);
					break;
				case 'L':
					gc.fillArc(getLeft() - width/10, getTop(), width/10 * 2, height, 90, 180, ArcType.CHORD);
					break;
				case 'R':
					gc.fillArc(getRight() - width/10, getTop(), width/10 * 2, height, 270, 180, ArcType.CHORD);
					break;
				}
			}
		}
		
		for(Bullet b : bullets) {
			b.render(gc);
		}
	}
}

