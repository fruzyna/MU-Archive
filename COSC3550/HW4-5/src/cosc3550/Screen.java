package cosc3550;

import java.util.ArrayList;

import cosc3550.gamemodes.PlayMode;
import cosc3550.object.Block;
import cosc3550.object.Boss;
import cosc3550.object.Door;
import cosc3550.object.Enemy;
import cosc3550.object.Entity;
import cosc3550.object.Key;
import cosc3550.object.Player;
import cosc3550.object.Spawn;
import javafx.scene.canvas.GraphicsContext;

/**
 * Screen
 * Contains and manages all blocks and entities contained.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Screen {
	public ArrayList<Block> blocks;
	public ArrayList<Entity> entities;
	
	// requires the 2D char array of blocks start block position and screen dimensions
	public Screen(char chars[][], int x1, int y1, int width, int height) {
		blocks = new ArrayList<>();
		entities = new ArrayList<>();
		// search for contained blocks
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				char c = chars[y + y1][x + x1];
				
				if(c == Block.DOOR || (c >= '0'-10 && c <= '9'-10))
					blocks.add(new Door(c, x, y));
				else if(c >= 'a' && c <= 'z') {
					// block
					blocks.add(new Block(c, x, y));
				}
				else {
					blocks.add(new Block(Block.AIR, x, y));
					if(c == Entity.PLAYER_SPAWN)
						entities.add(new Spawn(x, y));
					else if(c >= '0' && c <= '9')
						entities.add(new Key(c, x, y));
					else if(c == 'B')
						// boss
						entities.add(new Boss(c, x, y));
					else if(c >= 'A' && c <= 'Z')
						// entity
						entities.add(new Enemy(c, x, y));
				}
			}
		}
	}
	
	// used to restore original blocks and entity states when re-entered
	public void reset() {
		for(Entity e : entities)
			e.reset();
		for(Block b : blocks)
			b.reset();
	}
	
	// draw all contained blocks and entities
	public void render(GraphicsContext gc) {
		for(Block block : blocks)
			block.render(gc);
		for(Entity entity : entities)
			entity.render(gc);
	}
	
	// update all contained blocks and entities
	public void update(Player player) {
		for(Entity e : entities)
			e.update(player, this);
		for(Block b : blocks)
			b.update(player);
	}
	
	// returns the location of the player spawn point if it is contained
	public int[] getPlayerSpawn() {
		for(Entity e : entities) {
			if(e.type == Entity.PLAYER_SPAWN) {
				int coords[] =  {e.spawnX, e.spawnY};
				return coords;
			}
		}
		int empty[] =  {};
		return empty;
	}
	
	// return the block at a given point
	public Block getBlockAt(int x, int y) {
		if(x < PlayMode.WIDTH && y < PlayMode.HEIGHT)
			return blocks.get(x * PlayMode.HEIGHT + y);
		// when entering a new room things can be off for a second
		return blocks.get(0);
	}
}
