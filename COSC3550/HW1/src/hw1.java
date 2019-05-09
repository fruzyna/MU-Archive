import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * COSC 3550, Homework 1
 * Draws a spaceship flying through space
 * @author Liam Fruzyna
 */
public class hw1 extends Application
{
	// window variables
	final int width = 600;
	final int height = 500;
	final int frameRate = 60;
	int tick = 0;
	int seconds = 0;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("Homework 1");
		
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		Canvas canvas = new Canvas(width, height);
		root.getChildren().add(canvas);
		
		GraphicsContext paint = canvas.getGraphicsContext2D();
		render(paint);
		
		// creates loop for 60 fps animation
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / frameRate),
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					update();
					render(paint);
					tick++;
				}
		});
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();
		
		stage.show();
	}

	// ship body position
	double bodyX = 75;
	double bodyY = 100;
	double bodyW = 300;
	double bodyH = 150;

	// flame colors
	Color largeFlameColor = Color.YELLOW;
	Color mediumFlameColor = Color.ORANGE;
	Color smallFlameColor = Color.INDIANRED;
	
	// update loop to make changes
	void update()
	{
		// count seconds based on frame number (tick)
		if(tick % frameRate == 0)
		{
			seconds++;
		}
		
		// move the ship forwards and backwards
		if(seconds % 2 == 0)
		{
			bodyX -= 1;
		}
		else
		{
			bodyX += 1;
		}

		// alternate colors of the flame tiers (every second)
		if(seconds % 3 == 2)
		{
			largeFlameColor = Color.YELLOW;
			mediumFlameColor = Color.ORANGE;
			smallFlameColor = Color.INDIANRED;
		}
		else if(seconds % 3 == 1)
		{
			mediumFlameColor = Color.YELLOW;
			smallFlameColor = Color.ORANGE;
			largeFlameColor = Color.INDIANRED;
		}
		else
		{
			smallFlameColor = Color.YELLOW;
			largeFlameColor = Color.ORANGE;
			mediumFlameColor = Color.INDIANRED;
		}
	}
	
	// render the image on the screen
	void render(GraphicsContext paint)
	{
		// draw black space
		paint.setFill(Color.BLACK);
		paint.fillRect(0, 0, width, height);
		
		// draw 100 random stars zooming by
		paint.setFill(Color.WHITE);
		for(int i = 0; i < 100; i++)
		{
			paint.fillOval(Math.random() * width, Math.random() * width, 5, 1);
		}
		
		// draw ship body
		paint.setFill(Color.AZURE);
		paint.fillRect(bodyX, bodyY, bodyW, bodyH);
		
		// draw ship nose
		double noseX[] = {bodyX + bodyW, bodyX + bodyW, bodyX + bodyW + 150};
		double noseY[] = {bodyY, bodyY + bodyH, bodyY + bodyH/2};
		paint.fillPolygon(noseX, noseY, 3);
		
		// draw window
		paint.setFill(Color.STEELBLUE);
		paint.fillOval(bodyX + bodyW - 50, bodyY + (bodyH - 75)/2, 75, 75);
		
		// draw 3 tiers of rocket flames
		drawFlame(paint, 5, 50, largeFlameColor);
		drawFlame(paint, 10, 25, mediumFlameColor);
		drawFlame(paint, 15, 15, smallFlameColor);
	}
	
	// draw a flame (colored triangle on the back of the body)
	void drawFlame(GraphicsContext paint, int count, double flameW, Color color)
	{
		paint.setFill(color);
		double flameH = bodyH / (float) count;
		for(int i = 0; i < count; i++)
		{
			double y = bodyY + i * flameH;
			double flameX[] = {bodyX, bodyX, bodyX - flameW};
			double flameY[] = {y, y + flameH, y + flameH/2};
			paint.fillPolygon(flameX, flameY, 3);
		}
	}
}
