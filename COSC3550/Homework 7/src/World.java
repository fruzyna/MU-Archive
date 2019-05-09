
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Homework 7: 3D World
 * @author Liam Fruzyna
 */
public class World extends Application {

	private PerspectiveCamera camera;
	private Group cameraDolly;
	private final double cameraQuantity = 10.0;
	private final double sceneWidth = 600;
	private final double sceneHeight = 600;

	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;

	private void constructWorld(Group root) {
		// create a super faint ambient light so it never makes the defaults
		int brightness = 50;
		AmbientLight light = new AmbientLight(Color.rgb(2*brightness, 2*brightness, brightness));
		root.getChildren().add(light);

		// create a point light above
		PointLight pl = new PointLight();
		pl.setTranslateX(-250);
		pl.setTranslateY(-250);
		pl.setTranslateZ(0);
		
		// move the above light left and right
		Group upperRail = new Group();
		upperRail.getChildren().add(pl);
		TranslateTransition upperTrans = new TranslateTransition(Duration.millis(5000), upperRail);
		upperTrans.setByX(500);
		upperTrans.setInterpolator(Interpolator.LINEAR);
		upperTrans.setCycleCount(Animation.INDEFINITE);
		upperTrans.setAutoReverse(true);
		upperTrans.play();
		root.getChildren().add(upperRail);

		// create a point light below
		PointLight plb = new PointLight();
		plb.setTranslateX(250);
		plb.setTranslateY(250);
		plb.setTranslateZ(0);
		
		// move the below light left and right
		Group lowerRail = new Group();
		lowerRail.getChildren().add(plb);
		TranslateTransition lowerTrans = new TranslateTransition(Duration.millis(5000), lowerRail);
		lowerTrans.setByX(-500);
		lowerTrans.setInterpolator(Interpolator.LINEAR);
		lowerTrans.setCycleCount(Animation.INDEFINITE);
		lowerTrans.setAutoReverse(true);
		lowerTrans.play();
		root.getChildren().add(lowerRail);

		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.RED);
		redMaterial.setSpecularColor(Color.TOMATO);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.FORESTGREEN);
		greenMaterial.setSpecularColor(Color.LIMEGREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.MEDIUMSLATEBLUE);
		blueMaterial.setSpecularColor(Color.DODGERBLUE);
		
		// create a box on the left
		Box left = new Box(250, 10, 10);
		left.setMaterial(blueMaterial);
		left.setTranslateX(-250);
		
		// rotate the left box
		Group leftWheel = new Group();
		leftWheel.getChildren().add(left);
		RotateTransition leftRoll = new RotateTransition(Duration.millis(5000), leftWheel);
		leftRoll.setByAngle(-360);
		leftRoll.setAxis(Rotate.X_AXIS);
		leftRoll.setInterpolator(Interpolator.LINEAR);
		leftRoll.setCycleCount(Animation.INDEFINITE);
		leftRoll.setAutoReverse(false);
		leftRoll.play();
		root.getChildren().add(leftWheel);
		
		// create a box on the right
		Box right = new Box(250, 10, 10);
		right.setMaterial(blueMaterial);
		right.setTranslateX(250);
		
		// rotate the right box
		Group rightWheel = new Group();
		rightWheel.getChildren().add(right);
		RotateTransition rightRoll = new RotateTransition(Duration.millis(5000), rightWheel);
		rightRoll.setByAngle(-360);
		rightRoll.setAxis(Rotate.X_AXIS);
		rightRoll.setInterpolator(Interpolator.LINEAR);
		rightRoll.setCycleCount(Animation.INDEFINITE);
		rightRoll.setAutoReverse(false);
		rightRoll.play();
		root.getChildren().add(rightWheel);

		// create a green sphere
		final Sphere topSphere = new Sphere(25);
		topSphere.setMaterial(greenMaterial);
		topSphere.setTranslateX(0);
		topSphere.setTranslateY(-150);
		topSphere.setTranslateZ(0);
		root.getChildren().add(topSphere);
		
		// create a red sphere
		final Sphere botSphere = new Sphere(25);
		botSphere.setMaterial(redMaterial);
		botSphere.setTranslateX(0);
		botSphere.setTranslateY(150);
		botSphere.setTranslateZ(0);
		root.getChildren().add(botSphere);
		
		// create a tetrahedron
		TriangleMesh topTetraMesh = new TriangleMesh();
		topTetraMesh.getTexCoords().addAll(0, 0);
		// define vertices
		float h = 100;                    // Height
		float a = 200;                    // Base hypotenuse
		float R = a / (float) Math.sqrt(3);
		topTetraMesh.getPoints().addAll(
		        0,    0,    0,            // Point 0 - Top
		        0,    h,    -R,           // Point 1 - Front
		        -a/2, h,    R/2,          // Point 2 - Left
		        a/2,  h,    R/2           // Point 3 - Right
		    );
		// define faces
		topTetraMesh.getFaces().addAll(
		        0,0,  2,0,  1,0,          // Front left face
		        0,0,  1,0,  3,0,          // Front right face
		        0,0,  3,0,  2,0,          // Back face
		        1,0,  2,0,  3,0           // Bottom face
		    ); 
		topTetraMesh.getFaceSmoothingGroups().addAll(
				1, 2, 3, 4);
		MeshView topTetra = new MeshView(topTetraMesh);
		//pyramid.setDrawMode(DrawMode.LINE);
		topTetra.setMaterial(greenMaterial);
		topTetra.setTranslateX(0);
		topTetra.setTranslateY(-3*h);
		topTetra.setTranslateZ(0);
		root.getChildren().add(topTetra);
		
		// create a tetrahedron
		TriangleMesh botTetraMesh = new TriangleMesh();
		botTetraMesh.getTexCoords().addAll(0, 0);
		// define vertices
		botTetraMesh.getPoints().addAll(
		        0,    h,    0,            // Point 0 - Bottom
		        0,    0,    -R,           // Point 1 - Front
		        -a/2, 0,    R/2,          // Point 2 - Left
		        a/2,  0,    R/2           // Point 3 - Right
		    );
		// define faces
		botTetraMesh.getFaces().addAll(
		        2,0,  1,0,  3,0,          // Top face
		        2,0,  0,0,  1,0,          // Front left face
		        1,0,  0,0,  3,0,          // Front right face
		        3,0,  0,0,  2,0           // Back face
		    ); 
		botTetraMesh.getFaceSmoothingGroups().addAll(
				1, 2, 3, 4);
		MeshView botTetra = new MeshView(botTetraMesh);
		//pyramid.setDrawMode(DrawMode.LINE);
		botTetra.setMaterial(redMaterial);
		botTetra.setTranslateX(0);
		botTetra.setTranslateY(2*h);
		botTetra.setTranslateZ(0);
		root.getChildren().add(botTetra);
		
		// create a diamond
		TriangleMesh diamondMesh = new TriangleMesh();
		diamondMesh.getTexCoords().addAll(0, 0);
		// define vertices
		float s = 200;                    // Base hypotenuse
		diamondMesh.getPoints().addAll(
		        0,    0,    0,            // Point 0 - Top
		        0,    h,    -s/2,         // Point 1 - Front
		        -s/2, h,    0,            // Point 2 - Left
		        s/2,  h,    0,            // Point 3 - Right
		        0,    h,    s/2,          // Point 4 - Back
		        0,    h*2,  0             // Point 5 - Bottom
		    );
		// define faces
		diamondMesh.getFaces().addAll(
		        0,0,  2,0,  1,0,          // Front left face
		        0,0,  1,0,  3,0,          // Front right face
		        0,0,  3,0,  4,0,          // Back right face
		        0,0,  4,0,  2,0,          // Back left face
		        1,0,  2,0,  5,0,          // Front left face
		        3,0,  1,0,  5,0,          // Front right face
		        4,0,  3,0,  5,0,          // Back right face
		        2,0,  4,0,  5,0           // Back left face
		    ); 
		diamondMesh.getFaceSmoothingGroups().addAll(
				1, 2, 3, 4, 5, 6, 7, 8);
		MeshView diamond = new MeshView(diamondMesh);
		//pyramid.setDrawMode(DrawMode.LINE);
		diamond.setMaterial(blueMaterial);
		diamond.setTranslateX(0);
		diamond.setTranslateY(-h);
		diamond.setTranslateZ(0);
		
		// rotate the diamond along the x axis
		Group centerWheel = new Group();
		centerWheel.getChildren().add(diamond);
		RotateTransition centerRoll = new RotateTransition(Duration.millis(5000), centerWheel);
		centerRoll.setByAngle(-360);
		centerRoll.setAxis(Rotate.X_AXIS);
		centerRoll.setInterpolator(Interpolator.LINEAR);
		centerRoll.setCycleCount(Animation.INDEFINITE);
		centerRoll.setAutoReverse(false);
		centerRoll.play();
		root.getChildren().add(centerWheel);
	}

	@Override
	public void start(Stage primaryStage) {
		// Build your Scene and Camera
		Group sceneRoot = new Group();
		constructWorld(sceneRoot);

		Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true);
		scene.setFill(Color.DARKGOLDENROD);
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		scene.setCamera(camera);
		// translations through dolly
		cameraDolly = new Group();
		cameraDolly.setTranslateZ(-1000);
		cameraDolly.setTranslateX(200);
		cameraDolly.getChildren().add(camera);
		sceneRoot.getChildren().add(cameraDolly);
		// rotation transforms
		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		camera.getTransforms().addAll(xRotate, yRotate);

		// Use keyboard to control camera position
		scene.setOnKeyPressed(event -> {
			double change = cameraQuantity;
			// What key did the user press?
			KeyCode keycode = event.getCode();

			Point3D delta = null;
			if (keycode == KeyCode.COMMA) {
				delta = new Point3D(0, 0, change);
			}
			if (keycode == KeyCode.PERIOD) {
				delta = new Point3D(0, 0, -change);
			}
			if (keycode == KeyCode.A) {
				delta = new Point3D(-change, 0, 0);
			}
			if (keycode == KeyCode.D) {
				delta = new Point3D(change, 0, 0);
			}
			if (keycode == KeyCode.W) {
				delta = new Point3D(0, -change, 0);
			}
			if (keycode == KeyCode.S) {
				delta = new Point3D(0, change, 0);
			}
			if (keycode == KeyCode.UP) {
				xRotate.setAngle(((xRotate.getAngle() + 1) % 360 + 540) % 360 - 180);
			}
			if (keycode == KeyCode.DOWN) {
				xRotate.setAngle(((xRotate.getAngle() - 1) % 360 + 540) % 360 - 180);
			}
			if (keycode == KeyCode.RIGHT) {
				yRotate.setAngle(((yRotate.getAngle() + 1) % 360 + 540) % 360 - 180);
			}
			if (keycode == KeyCode.LEFT) {
				yRotate.setAngle(((yRotate.getAngle() - 1) % 360 + 540) % 360 - 180);
			}
			if (delta != null) {
				Point3D delta2 = camera.localToParent(delta);
				cameraDolly.setTranslateX(cameraDolly.getTranslateX() + delta2.getX());
				cameraDolly.setTranslateY(cameraDolly.getTranslateY() + delta2.getY());
				cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + delta2.getZ());

			}
		});

		// Use mouse to control camera rotation
		scene.setOnMousePressed(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
		});

		scene.setOnMouseDragged(me -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX);
			mouseDeltaY = (mousePosY - mouseOldY);

			yRotate.setAngle(((yRotate.getAngle() - mouseDeltaX * 0.2) % 360 + 540) % 360 - 180); // +
			xRotate.setAngle(((xRotate.getAngle() + mouseDeltaY * 0.2) % 360 + 540) % 360 - 180); // -
		});

		primaryStage.setTitle("World3");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}