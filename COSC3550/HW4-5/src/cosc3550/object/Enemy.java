package cosc3550.object;

import cosc3550.Main;
import cosc3550.Screen;
import cosc3550.gamemodes.PlayMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * Enemy
 * An extension of entity that can attack the player
 * @author Liam Fruzyna and Katy Weathington
 */
public class Enemy extends Entity {
	int meleeTimeout;
	int meleeCounter = 0;
	int meleeStrength;
	
	int speed;
	
	// generic enemy type
	public Enemy(char type, int x, int y) {
		this(type, x, y, 2, 2, 1, Main.FPS);
	}
	
	// requires char type, and grid position
	public Enemy(char type, int x, int y, int health, int speed, int meleeStrength, int meleeTimeout) {
		super(type, x, y, health);
		this.speed = speed;
		this.meleeStrength = meleeStrength;
		this.meleeTimeout = meleeTimeout;
	}
	
	@Override
	// draw enemy and direction
	public void render(GraphicsContext gc) {
		if(isAlive()) {
			switch(direction) {
			case 'U':
				gc.drawImage(PlayMode.sprites.enemy[1], getLeft(), getTop());
				break;
			case 'D':
				gc.drawImage(PlayMode.sprites.enemy[0], getLeft(), getTop());
				break;
			case 'L':
				gc.drawImage(PlayMode.sprites.enemy[2], getLeft(), getTop());
				break;
			case 'R':
				gc.drawImage(PlayMode.sprites.enemy[3], getLeft(), getTop());
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
	}

	// move in direction of player and attack every so often
	public void update(Player player, Screen screen) {
		if(isAlive()) {
			int pb[] = player.getBlockPos();
			Block pBlock = screen.getBlockAt(pb[0], pb[1]);
			int b[] = getBlockPos();
			Block block = screen.getBlockAt(b[0], b[1]);

			if(pBlock.x < block.x && screen.getBlockAt(b[0]-1, b[1]).passable) {
				// move left
				move(false, false, true, false, speed, false);
			}
			else if(pBlock.x > block.x && screen.getBlockAt(b[0]+1, b[1]).passable) {
				// move right
				move(false, false, false, true, speed, false);
			}
			if(pBlock.y < block.y && screen.getBlockAt(b[0], b[1]-1).passable) {
				// move up
				move(true, false, false, false, speed, false);
			}
			else if(pBlock.y > block.y && screen.getBlockAt(b[0], b[1]+1).passable) {
				// move down
				move(false, true, false, false, speed, false);
			}
			
			// attack if meleeCounter has run out
			if(meleeCounter == 0) {
				meleeCounter = meleeTimeout;
				player.attacked(meleeb, meleeStrength);
			}
			// decrease meleeCounter every iteration until 0
			else {
				meleeCounter--;
			}
		}
	}
}
