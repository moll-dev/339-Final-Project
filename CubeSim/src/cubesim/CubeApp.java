package cubesim;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import factory.LEDCubeFactory;
import handlers.KeyboardHandler;
import handlers.MouseDraggedHandler;
import handlers.MousePressedHandler;
import javafx.concurrent.Task;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Main Cube Application:
 * 
 * 	This one runs JavaFX for all the fancy graphics
 * 	The setup() methods simple handle setting up lights
 *  axes, etc. The handleKeyboard(), handleMouse() handle input.
 *  
 * USE THIS FOR TASK AND THREADING
 * @author Tom
 *
 */

public class CubeApp extends Application{
	
	final Group root = new Group();
	final Xform axisGroup = new Xform();
	final Xform lightGroup = new Xform();
	final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
   
    private static final double AXIS_LENGTH = 250.0;
    private static final double CAMERA_INITIAL_DISTANCE = -500;
    private static final double CAMERA_INITIAL_X_ANGLE = 40.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;

    
    private static CubeApp instance = null;
    private Thread serverThread;
    private Point2D mousePos;
    private Point2D mouseOld;
    
    
    LEDCube cube;
    
   
	@Override
	public void start(Stage primaryStage) throws Exception {

		root.getChildren().add(world);
	    root.setDepthTest(DepthTest.ENABLE);
		
		buildCamera();
		buildAxes();
		buildPlane();
		buildLEDS();
		buildLights();
		
		Scene scene = new Scene(root, 800, 600, true);
		scene.setFill(Color.BLACK);
		
		// Setup handlers for GUI - Command Pattern
		scene.setOnKeyPressed(new KeyboardHandler());
		scene.setOnMousePressed(new MousePressedHandler());
		scene.setOnMouseDragged(new MouseDraggedHandler());
		
		mousePos = new Point2D(0.0, 0.0);
		mouseOld = new Point2D(0.0, 0.0);

		primaryStage.setTitle("CubeSim");
		primaryStage.setScene(scene);
		primaryStage.show();		
		
		scene.setCamera(camera);
		
		ClientTask task = new ClientTask(new ServerSocket(4444), cube);
		serverThread = new Thread(task);
		serverThread.setDaemon(true);
		serverThread.start();
		instance = this;
	}
	
	public static CubeApp instance() {
		if (instance == null) {
			instance = new CubeApp();
		}
		return instance;
	}
	public void stop(){
		serverThread.interrupt();
	}

	public void resetCamera() {
		cameraXform2.t.setX(0.0);
		cameraXform2.t.setY(0.0);
		cameraXform2.t.setZ(3.0);
		cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
		camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
	}
	
	public void toggleAxis() {
		axisGroup.setVisible(!axisGroup.isVisible());
	}
	
	public LEDCube getCube() {
		return cube;
	}
	
	public void setCube() {
	}
	
	public void clearCube() {
		cube.clear();
	}
	
	public Point2D getMousePos() {
		return mousePos;
	}
	
	public Point2D getOldMousePos() {
		return mouseOld;
	}
	
	public void setOldMousePos(Point2D point) {
		mouseOld = point;
	}
	
	public void setMousePos(Point2D point) {
		mousePos = point;
	}
	
	public void zoomCamera(Double amount) {
		double z = camera.getTranslateZ();
        double newZ = z + amount;
        camera.setTranslateZ(newZ);
	}
	
	public void orbitCamera(Point2D vector) {
		cameraXform.ry.setAngle(cameraXform.ry.getAngle() - vector.getX());
	    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + vector.getY());
	}
	
	public void panCamera(Point2D vector) {
        cameraXform2.t.setX(cameraXform2.t.getX() + vector.getX());
        cameraXform2.t.setY(cameraXform2.t.getY() + vector.getY());
	}
	
	private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);
 
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }
	
	private void buildPlane() {
		Box plane = new Box(100, 1, 100);
		final PhongMaterial planeMaterial = new PhongMaterial();
		planeMaterial.setDiffuseColor(Color.DARKSLATEGRAY);
		planeMaterial.setSpecularColor(Color.BLACK);
		plane.setTranslateY(-5.0);
		plane.setMaterial(planeMaterial);
		plane.setVisible(true);
		world.getChildren().addAll(plane);
	}
	
	private void buildLights(){
		PointLight light = new PointLight();
		light.setTranslateY(100);
		light.setTranslateX(100);
		light.setTranslateX(100);
		light.setColor(Color.WHITE);
				
		lightGroup.getChildren().add(light);
		
		//world.getChildren().addAll(lightGroup.getChildren());
	}
	
	private void buildLEDS() {
		// Build a new LED Cube object with 8 as the dimention
		cube = LEDCubeFactory.makeCube(8, new Point3D(0, 0, 50), LEDCubeFactory.CubeType.BLANK);
		world.getChildren().addAll(cube.getChildren());
	}
	
	private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
 
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
 
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
 
        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
 
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
