import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.net.*;
import java.io.*;

/*
 * Implement a simple 2-player
 * tank battle game.
 *
 * written by mike slattery - mar 2007
 * Ported to JavaFX - mcs, mar 2017
 */

public class NetTankWar extends Application {
	final String appName = "NetTankWar";
	final int FPS = 30; // frames per second
	final static int WIDTH = 800;
	final static int HEIGHT = 600;

	GraphicsContext gc; // declare here to use in handlers

	public static ArrayList<Rock> rocks; // obstacles on the field
	public static ArrayList<Tank> tanks;
	public static final int RED = 0;
	public static final int BLUE = 1;

	int playerID = -1; // my subscript in tank array

	Image redtank;
	Image bluetank;

	static boolean roundOver = true;
	static int loser;

	static boolean ready = false;

	Font font = Font.font("Monospaced", FontPosture.REGULAR, 30.0);

	private Socket sock;
	private static PrintWriter out;

	private static final int PORT = 1234; // server details
	private static final String HOST = "localhost";
	// private static final String HOST = "pascal.mscs.mu.edu";

	public void initialize() {
		redtank = new Image("redtank.png");
		bluetank = new Image("bluetank.png");

		makeContact();
	}

	private void makeContact()
	// contact the NetWarServer
	{
		try {
			sock = new Socket(HOST, PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true); // autoflush

			NetWarWatcher nww = new NetWarWatcher(this, in);
			nww.setDaemon(true); // tell Java this is a supplemental thread
			nww.start(); // start watching for server msgs
		} catch (Exception e) {
			System.out.println("Cannot contact the NetTankWar Server");
			System.exit(1);
		}
	} // end of makeContact()

	public static void send(String msg) {
		// send a message to the other player (via server)
		out.println(msg);
	}

	public void update() {
		if (!roundOver) { // Freeze the action between rounds
			tanks.get(playerID).update(true);
			tanks.get(1 - playerID).update(false);
		}
	}

	synchronized void resetRound() {
		// Build semi-random rock field
		rocks = new ArrayList<Rock>();
		int edge = (WIDTH + HEIGHT) / 20;
		int halfW = WIDTH / 2;
		int halfH = HEIGHT / 2;
		placeRocks(40, edge, halfH, halfW, edge, 0.2);
		placeRocks(40, halfW, HEIGHT - edge, WIDTH - edge, halfH, 0.2);
		placeRocks(10, halfW, 0, halfW, HEIGHT, 0.1);

		// Place tanks
		tanks = new ArrayList<Tank>();
		tanks.add(new Tank(WIDTH - edge, HEIGHT - edge, Math.PI, RED, redtank));
		tanks.add(new Tank(edge, edge, 0.0, BLUE, bluetank));

		roundOver = false;
		ready = true;
	}

	public void setPlayerID(int id) {
		playerID = id;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setRocks(String config) {
		// Read rock string from other player
		stringToRocks(config);
		// Place tanks
		int edge = (int) (WIDTH + HEIGHT) / 20;
		tanks = new ArrayList<Tank>();
		tanks.add(new Tank(WIDTH - edge, HEIGHT - edge, Math.PI, RED, redtank));
		tanks.add(new Tank(edge, edge, 0.0, BLUE, bluetank));

		roundOver = false;
		ready = true;
	}

	public void sendRocks() {
		// Lay out rocks and send to other player
		resetRound();
		out.println("rocks " + rocksToString());
	}

	synchronized void placeRocks(int n, int x1, int y1, int x2, int y2, double aspect) {
		// place n rocks randomly located within distance r of
		// the line from (x1,y1) to (x2,y2) where r is the length of
		// this line times aspect.
		int x, y;
		double s, tx, ty;

		double len = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		double r = len * aspect;
		for (int i = 0; i < n; i++) {
			s = Rock.rockGen.nextDouble();
			tx = r * (Rock.rockGen.nextDouble() - 0.5);
			ty = r * (Rock.rockGen.nextDouble() - 0.5);
			x = (int) (x1 + s * (x2 - x1) + tx);
			y = (int) (y1 + s * (y2 - y1) + ty);
			rocks.add(new Rock(x, y, (int) (r / 2), (int) r));
		}

	}

	public static synchronized void removeRock(int index) {
		// Deactivate the index-th rock in rocks
		rocks.get(index).demolish();
	}

	public static synchronized String rocksToString() {
		// Return a string describing the rocks: x1 y1 d1;x2 y2 d2; ...
		StringBuilder sb = new StringBuilder();
		for (Rock r : rocks)
			sb.append(r.getIntX() + " " + r.getIntY() + " " + r.getDiameter() + ";");
		return new String(sb);
	}

	public static void stringToRocks(String s) {
		// read the rocks string and fill in the ArrayList
		int x, y, d;

		rocks = new ArrayList<Rock>();
		String specs[] = s.split(";");
		for (String sp : specs) {
			Scanner sc = new Scanner(sp);
			x = sc.nextInt();
			y = sc.nextInt();
			d = sc.nextInt();
			sc.close();
			rocks.add(new Rock(x, y, d));
		}
	}

	public void processMove(String s) {
		// Send command to tank not controlled by
		// this player
		tanks.get(1 - playerID).processMove(s);
	}

	public void processHit(String s) {
		// Update movement parameters based on s
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(s);
		// Get the flag change
		String command = sc.next();
		int value = sc.nextInt();
		if (command.equals("rock"))
			removeRock(value);
		else if (command.equals("tank"))
			tankHit(value);
		else
			System.out.println("Unexpected move: " + command);
	}

	public static int hitAnItem(Ball b, ArrayList<? extends Ball> c) {
		// Check if b has run into any element of c. If so
		// return the index of the first item that was hit,
		// or -1 if nothing hit.
		// Use generics so the same code can check for collisions
		// with rocks and tanks.
		for (Ball r : c) {
			if (!r.isAlive())
				continue;
			double dx = b.getX() - r.getX();
			double dy = b.getY() - r.getY();
			double bound = b.getRadius() + r.getRadius();
			if ((dx * dx + dy * dy) < (bound * bound))
				return c.indexOf(r);
		}
		return -1;
	}

	public static void tankHit(int k) {
		// If a tank is hit, the round is over
		if (!roundOver) {
			roundOver = true;
			loser = k;
		}
	}
	
	double fade = 0.0;

	public void render(GraphicsContext gc) {

		// Draw a background
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		if (!ready) { // Just display the message and return
			gc.setFill(Color.BLACK);
			gc.setFont(font);
			gc.fillText("Waiting for setup...", 200, 250);
			return;
		}

		// Draw rocks
		synchronized (this) {
			for (Rock r : rocks)
				r.render(gc);
		}

		// Draw tanks (and their bullets)
		for (Tank t : tanks)
			t.render(gc);

		if (roundOver) {
			if(loser == RED)
				gc.setFill(Color.rgb(0, 0, 255, fade));
			else
				gc.setFill(Color.rgb(255, 0, 0, fade));
			
			gc.fillRect(0, 0, WIDTH, HEIGHT);
			gc.setFill(Color.BLACK);
			gc.setFont(font);
			gc.fillText("Round Over: You " + (loser == playerID ? "lose" : "win") + "!", 150, 200);
			
			if(fade < 0.75)
				fade += 0.05;
		}
	}

	void setHandlers(Scene scene) {
		scene.setOnKeyPressed(e -> {
			KeyCode c = e.getCode();
			switch (c) {
			case LEFT:
				tanks.get(playerID).turnL = true;
				break;
			case RIGHT:
				tanks.get(playerID).turnR = true;
				break;
			case UP:
				tanks.get(playerID).forth = true;
				break;
			case SPACE:
				tanks.get(playerID).fire = true;
				break;
			default:
				break;
			}
		});

		scene.setOnKeyReleased(e -> {
			KeyCode c = e.getCode();
			switch (c) {
			case LEFT:
				tanks.get(playerID).turnL = false;
				break;
			case RIGHT:
				tanks.get(playerID).turnR = false;
				break;
			case UP:
				tanks.get(playerID).forth = false;
				break;
			case SPACE:
				tanks.get(playerID).fire = false;
				break;
			default:
				break;
			}
		});
	}

	/*
	 * Begin boiler-plate code... [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

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
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
			update();
			// draw frame
			render(gc);
		});
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */

}

interface Ball {
	// Description of common features for Rock, Tank, and Bullet
	// Useful for NetTankWar.hitAnItem()
	double getX();

	double getY();

	double getRadius();

	boolean isAlive();
}

class Rock implements Ball {
	// Obstacles scattered about the play field.
	// Create a random generator to use for rock sizes and
	// placement
	public static Random rockGen = new Random();

	int locX, locY, diameter, radius; // int properties easier to
	boolean alive = true; // send over network

	public Rock(int x, int y, int minD, int maxD) {
		locX = x;
		locY = y;
		diameter = minD + rockGen.nextInt(maxD - minD + 1);
		radius = diameter / 2;
	}

	public Rock(int x, int y, int d) {
		locX = x;
		locY = y;
		diameter = d;
		radius = diameter / 2;
	}

	void demolish() {
		// "Turn off" this rock - won't paint or be hit
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public double getX() {
		return (double) locX;
	}

	public double getY() {
		return (double) locY;
	}

	public double getRadius() {
		return (double) radius;
	}

	public int getIntX() {
		return locX;
	}

	public int getIntY() {
		return locY;
	}

	public int getDiameter() {
		return diameter;
	}

	void render(GraphicsContext gc) {
		if (alive) {
			gc.setFill(Color.GRAY);
			gc.fillOval(locX - radius, locY - radius, diameter, diameter);
		}
	}
}

class Tank implements Ball {

	double locX, locY, radius, angle;
	int self; // index of this tank in NetTankWar.tanks
	public boolean turnL, turnR, forth, back, fire;
	boolean prevtL, prevtR, prevfo, prevfi;
	Color color;
	Image image;

	public static final double turnRate = 180.0 / 8;
	public static final double speed = 4.0;
	public static final int RELOAD = 8; // delay between bullets
	int count; // timer for reloading

	public static final int MAXBULLETS = 7; // max simultaneous shots
	Bullet bullets[] = new Bullet[MAXBULLETS];

	public Tank(double x, double y, double a, int index, Image im) {
		locX = x;
		locY = y;
		angle = a;
		self = index;
		image = im;
		radius = 22;
		// create bullets for this tank
		for (int i = 0; i < bullets.length; i++)
			bullets[i] = new Bullet(self);
	}

	public double getX() {
		return locX;
	}

	public double getY() {
		return locY;
	}

	public double getRadius() {
		return radius;
	}

	public boolean isAlive() {
		return true;
	}

	void update(Boolean local) {
		if (turnL)
			turnLeft(turnRate);
		if (turnR)
			turnRight(turnRate);
		if (forth) {
			moveForward();
			// Check for rocks
			if (NetTankWar.hitAnItem(this, NetTankWar.rocks) >= 0)
				backUp();
		}
		if (fire)
			fireBullet();
		if (local) {
			if (turnL != prevtL) {
				NetTankWar.send("turnL " + turnL + " " + locX + " " + locY + " " + angle);
				prevtL = turnL;
			}
			if (turnR != prevtR) {
				NetTankWar.send("turnR " + turnR + " " + locX + " " + locY + " " + angle);
				prevtR = turnR;
			}
			if (forth != prevfo) {
				NetTankWar.send("forth " + forth + " " + locX + " " + locY + " " + angle);
				prevfo = forth;
			}
			if (fire != prevfi) {
				NetTankWar.send("fire " + fire + " " + locX + " " + locY + " " + angle);
				prevfi = fire;
			}
		}
		// Update all of our bullets
		for (Bullet b : bullets)
			b.update(local);
	}

	public void processMove(String s) {
		// Update movement parameters based on s
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(s);
		// Get the flag change
		String command = sc.next();
		boolean value = sc.nextBoolean();
		if (command.equals("turnL"))
			turnL = value;
		else if (command.equals("turnR"))
			turnR = value;
		else if (command.equals("forth"))
			forth = value;
		else if (command.equals("fire"))
			fire = value;
		else
			System.out.println("Unexpected move: " + command);
		// then unpack position update
		locX = sc.nextDouble();
		locY = sc.nextDouble();
		angle = sc.nextDouble();
	}

	void render(GraphicsContext gc) {
		// Use the affine transform
		// to easily rotate the tank's image.
		gc.save();
		Affine trans = new Affine();
		trans.appendTranslation(locX, locY);
		trans.appendRotation(angle);
		// draw the image using the specified transform
		gc.setTransform(trans);
		gc.drawImage(image, -radius, -radius);
		// Reset the transform (this is important)
		gc.restore();
		// Then draw bullets
		for (Bullet b : bullets)
			b.render(gc);

	}

	void fireBullet() {
		// If it has been long enough since the last shot...
		count--;
		if (count > 0)
			return;
		// ...and if all the bullets aren't currently in use...
		int slot = getAvailableBullet();
		if (slot < 0)
			return;
		// ...then launch a new bullet
		bullets[slot].setLocation(locX, locY);
		bullets[slot].setDirection(angle);
		bullets[slot].reset();
		// Reset the timer
		count = RELOAD;
	}

	int getAvailableBullet() {
		for (int i = 0; i < bullets.length; i++)
			if (!bullets[i].isAlive())
				return i;
		return -1;
	}

	void turnRight(double a) {
		angle += a;
		if (angle > 360)
			angle -= 360;
	}

	void turnLeft(double a) {
		angle -= a;
		if (angle < 0.0)
			angle += 360;
	}

	void moveForward() {
		locX += speed * Math.cos(Math.PI * angle / 180);
		locY += speed * Math.sin(Math.PI * angle / 180);
	}

	void backUp() {
		locX -= speed * Math.cos(Math.PI * angle / 180);
		locY -= speed * Math.sin(Math.PI * angle / 180);
	}

}

class Bullet implements Ball {
	double locX, locY, dx, dy;
	int tank; // index of tank that fired this bullet
	boolean alive = false;
	int ttl; // time to live
	public static final int LIFETIME = 70;
	public static final double SPEED = 8.0;
	public static final int radius = 7;

	public Bullet(int t) {
		tank = t;
	}

	void update(boolean local) {
		int i;
		// Check if this bullet is worn out
		ttl--;
		if (ttl < 0)
			alive = false;
		if (!alive)
			return;
		// If not worn out, update position
		locX += SPEED * dx;
		locY += SPEED * dy;
		// check for collisions with rocks
		i = NetTankWar.hitAnItem(this, NetTankWar.rocks);
		if (i >= 0) {
			alive = false;
			// Ask the game to deactivate this rock
			if(local) {
				NetTankWar.removeRock(i);
				NetTankWar.send("rock " + i);
			}
		}
		// check for collisions with tanks (other than
		// our tank)
		i = NetTankWar.hitAnItem(this, NetTankWar.tanks);
		if ((i >= 0) && (i != tank)) {
			alive = false;
			// Tell game a tank was hit
			if(local) {
				NetTankWar.tankHit(i);
				NetTankWar.send("tank " + i);
			}
		}
	}

	public double getX() {
		return locX;
	}

	public double getY() {
		return locY;
	}

	public double getRadius() {
		return radius;
	}

	void setLocation(double x, double y) {
		locX = x;
		locY = y;
	}

	void setDirection(double angle) {
		dx = Math.cos(Math.PI * angle / 180);
		dy = Math.sin(Math.PI * angle / 180);
	}

	void render(GraphicsContext gc) {
		if (alive) {
			gc.setFill(Color.BLACK);
			gc.fillOval(locX - radius, locY - radius, 2 * radius, 2 * radius);
		}
	}

	public boolean isAlive() {
		return alive;
	}

	void reset() {
		ttl = LIFETIME;
		alive = true;
	}
}