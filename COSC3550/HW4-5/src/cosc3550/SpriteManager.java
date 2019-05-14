package cosc3550;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cosc3550.gamemodes.PlayMode;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

/**
 * SpriteManager
 * Stores all sprites and sounds for easy access in the future.
 * @author Liam Fruzyna and Katy Weathington
 */
public class SpriteManager {
	public Image boss[] = new Image[4];
	public Image player[] = new Image[4];
	public Image enemy[] = new Image[4];
	public Image bullet[] = new Image[4];
	public Image wall;
	public Image floor;
	public Image door;
	public Image key;
	public Image keyIcon;
	public Image bulletIcon;
	
	public AudioClip swing;
	public AudioClip shoot;
	
	// Load in all sprites
	public SpriteManager() throws FileNotFoundException {
		int blockWidth = Main.WIDTH / PlayMode.WIDTH;
		int blockHeight = Main.HEIGHT / PlayMode.HEIGHT;
		
		wall = new Image(new FileInputStream("sprites/metalWall.png"), blockWidth, blockHeight, true, false);
		floor = new Image(new FileInputStream("sprites/grateFloor.png"), blockWidth, blockHeight, true, false);
		door = new Image(new FileInputStream("sprites/door.png"), blockWidth, blockHeight, true, false);
		key = new Image(new FileInputStream("sprites/key.png"), blockWidth, blockHeight, true, false);
		keyIcon = new Image(new FileInputStream("sprites/keyIcon.png"), 16, 16, true, false);
		bulletIcon = new Image(new FileInputStream("sprites/bulletIcon.png"), 16, 16, true, false);

		boss = entity("sprites/bigBad.png", blockWidth, blockHeight);
		player = entity("sprites/heroSprite.png", blockWidth, blockHeight);
		enemy = entity("sprites/guardSprite.png", blockWidth, blockHeight);
		bullet = entity("sprites/bullet.png", 8, 8);
		
		shoot = new AudioClip("file:sounds/shoot.wav");
		swing = new AudioClip("file:sounds/swing.wav");
	}
	
	// Break up a 2x2 image into an array of 4 images
	public Image[] entity(String file, int blockWidth, int blockHeight) throws FileNotFoundException {
		Image parts[] = new Image[4];
		Image img = new Image(new FileInputStream(file), 2*blockWidth, 2*blockHeight, true, false);
		PixelReader reader = img.getPixelReader();
		int w = (int) img.getWidth()/2;
		int h = (int) img.getHeight()/2;
		parts[0] = new WritableImage(reader, 0, 0, w, h);
		parts[1] = new WritableImage(reader, w, 0, w, h);
		parts[2] = new WritableImage(reader, 0, h, w, h);
		parts[3] = new WritableImage(reader, w, h, w, h);
		return parts;
	}

	// Get a translucent version of an image
	public Image fade(Image input, float opacity) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
    
		WritableImage output = new WritableImage(W, H);
    
		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();
    
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				Color c = reader.getColor(x, y);
				Color adjusted = new Color(c.getRed(), c.getBlue(), c.getGreen(), opacity);
				writer.setColor(x, y, adjusted);
			}
		}
    
		return output;
	}
}
