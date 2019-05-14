package cosc3550.object;

import cosc3550.Main;
import cosc3550.gamemodes.PlayMode;
import javafx.scene.canvas.GraphicsContext;

/**
 * Door
 * Custom block object that closes on entry to room and opens when the player obtains a key.
 * Door maintains its existing state (open) on re-entry.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Door extends Block {
	enum DoorState {CLOSING, CLOSED, OPENING, OPENED}
	final int animateTime = (int)(Main.FPS * 1.0);
	DoorState state;
	int frameCounter;
	float opacity;
	int keyNum = -1;

	// requires only the object character and grid position
	public Door(char obj, int x, int y) {
		super(obj, x, y);
		frameCounter = 0;
		state = DoorState.CLOSING;
		
		if(obj != 'd')
			keyNum = obj + 10;
	}
	
	@Override
	public void update(Player player) {
		switch(state) {
		case CLOSED:
			opacity = 1;
			passable = false;
			if(player.hasKey(keyNum)) {
				frameCounter = 0;
				state = DoorState.OPENING;
			}
			break;
		case CLOSING:
			opacity = (float)frameCounter/animateTime;
			if(opacity >= 1) {
				state = DoorState.CLOSED;
			}
			passable = true;
			break;
		case OPENING:
			opacity = 1 - (float)frameCounter/animateTime;
			if(opacity <= 0) {
				state = DoorState.OPENED;
			}
			passable = true;
			break;
		case OPENED:
			opacity = 0;
			passable = true;
			break;
		}
		frameCounter++;
	}
	
	@Override
	// draw a wall image with opacity representing opening/closing
	public void render(GraphicsContext gc) {
		gc.drawImage(PlayMode.sprites.floor, x*width, y*height);
		gc.drawImage(PlayMode.sprites.fade(PlayMode.sprites.door, opacity), x*width, y*height);
	}
}
