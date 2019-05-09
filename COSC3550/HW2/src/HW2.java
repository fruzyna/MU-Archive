import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Assignment 2, Ball shooting game
 * Main class, controls all systems.
 * @author Liam Fruzyna
 */
public class HW2 extends Application
{
	// window variables
	static final int FPS = 60;
	static final int WIDTH = 600;
	static final int HEIGHT = 500;
	
	// game objects
	ArrayList<Wall> walls;
	ArrayList<Ball> balls;
	Cannon cannon;
	Scoreboard score;
	Alert help;
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		balls = new ArrayList<>();
		walls = new ArrayList<>();
		walls.add(new Wall(100, 0, 10, 200, 1));
		walls.add(new Wall(200, 100, 10, 200, 2));
		walls.add(new Wall(300, 200, 10, 200, 3));
		walls.add(new Wall(400, 300, 10, 200, 4));
		walls.add(new Wall(500, 400, 10, 200, 5));
		cannon = new Cannon();
		score = new Scoreboard();
		
		String helpLines[] = {
				"HOMEWORK 2 - Ball Shooter",
				"by: Liam Fruzyna",
				"Score by shooting a ball across the screen.",
				"Avoid moving walls.",
				"Bounce balls off top and bottom",
				"Aim with the mouse cursor.",
				"Shoot by pressing the mouse, space, or enter.",
				"Change the theme with c.",
				"Show/hide this dialog with h."
		};
		help = new Alert(helpLines, true);
	}
	
	void setHandlers(Scene scene)
	{
		// shoot a ball in the direction of the cursor on mouse press
		scene.setOnMousePressed(
				e -> {
						double xDir = (int)e.getX();
						double yDir = (int)e.getY() - HEIGHT/2;
						if(xDir != 0)
						{
							balls.add(cannon.shoot(0, HEIGHT/2, xDir, yDir));
						}
					}
				);
		
		// move the crosshair with the mouse cursor
		scene.setOnMouseMoved(
				e -> {
						int x = (int)e.getX();
						int y = (int)e.getY();
						cannon.update(x, y);
					}
				);
		
		// respond to keyboard input
		scene.setOnKeyPressed(
				e -> {
						switch(e.getCode())
						{
						case ENTER:
						case SPACE:
							// shoot the ball with enter or space
							double xDir = cannon.x;
							double yDir = cannon.y - HEIGHT/2;
							if(xDir != 0)
							{
								balls.add(cannon.shoot(0, HEIGHT/2, xDir, yDir));
							}
							break;
						case C:
							// change the theme with c
							Theme.next();
							break;
						case H:
							// toggle help dialog
							help.toggle();
						default:
							break;
						}
					}
				);
	}

	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		ArrayList<Ball> remove = new ArrayList<>();
		for(Wall wall : walls)
		{
			// update all walls
			wall.update();
			for(Ball ball : balls)
			{
				// check if any balls have hit any walls
				if(wall.hit(ball))
				{
					remove.add(ball);
				}
			}
		}
		
		for(Ball ball : balls)
		{
			// update all balls
			if(ball.update())
			{
				score.increase();
				remove.add(ball);
			}
		}
		
		// remove any scored or collided balls
		for(Ball ball : remove)
		{
			balls.remove(ball);
		}
	}

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc)
	{
		// fill background
		gc.setFill(Theme.getBackground());
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		// render all walls
		for(Wall wall : walls)
		{
			wall.render(gc);
		}
		
		// render all balls
		for(Ball ball : balls)
		{
			ball.render(gc);
		}
		
		// render crosshair, score, and help
		cannon.render(gc);
		score.render(gc);
		help.render(gc);
	}

	/*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage theStage)
	{
		theStage.setTitle("Homework 2");
		theStage.setResizable(false);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */
}
