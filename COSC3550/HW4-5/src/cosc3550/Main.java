package cosc3550;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import cosc3550.gamemodes.GameMode;
import cosc3550.gamemodes.MenuMode;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * Main
 * Sets up window and manage current mode.
 * @author Liam Fruzyna and Katy Weathington
 */
public class Main extends Application 
{
	// window variables
	final String appName = "Project";
	public static final int FPS = 60; // frames per second
	public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    static Scene theScene;
    
    // game mode variables
    static GameMode mode;
    static boolean ready = false;
    
    // attempts to switch to a new mode
    public static void changeMode(GameMode newMode) {
    	if(mode != null)
    		mode.end();
    	ready = false;
    	mode = newMode;
    	mode.init();
    	mode.setHandlers(theScene);
    	ready = true;
    }
    
    /*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					if(ready) {
						// update position
						mode.update();
						// draw frame
						mode.render(gc);
					}
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.setResizable(false);
		theStage.show();
		
		// start with the menu
		changeMode(new MenuMode());
	}
	/*
	 * ... End boiler-plate code
	 */
}