package cosc3550.object;

import cosc3550.Main;
import cosc3550.gamemodes.PlayMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * Boss
 * The strongest and final enemy in the game.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Boss extends Enemy {

	public Boss(char type, int x, int y) {
		super(type, x, y, 5, 1, 2, 3*Main.FPS/4);
	}
	
	@Override
	// draw boss and direction
	public void render(GraphicsContext gc) {
		if(isAlive()) {
			switch(direction) {
			case 'U':
				gc.drawImage(PlayMode.sprites.boss[1], getLeft(), getTop());
				break;
			case 'D':
				gc.drawImage(PlayMode.sprites.boss[0], getLeft(), getTop());
				break;
			case 'L':
				gc.drawImage(PlayMode.sprites.boss[2], getLeft(), getTop());
				break;
			case 'R':
				gc.drawImage(PlayMode.sprites.boss[3], getLeft(), getTop());
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
}
