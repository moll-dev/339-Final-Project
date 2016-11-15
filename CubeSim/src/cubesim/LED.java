package cubesim;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.geometry.Point3D;

/**
 *  LED Class
 *  
 *  Represents an LED, can be toggled using
 *  JavaFX materials. Working on lighting though....
 * @author Thomas
 */
public class LED {
	private PointLight light;
	private Sphere bulb;
	private Point3D loc;
	private boolean state;
	private PhongMaterial on;
	private PhongMaterial off;
	
	public LED(Point3D location){
		loc = location;
		light = new PointLight();
		light.setTranslateX(location.getX());
		light.setTranslateY(location.getY());
		light.setTranslateZ(location.getZ());
		
		bulb = new Sphere();
		bulb.setTranslateX(location.getX());
		bulb.setTranslateY(location.getY());
		bulb.setTranslateZ(location.getZ());
		bulb.setRadius(2);
		
		off = new PhongMaterial();
		off.setDiffuseColor(new Color(0.2, 0.2, 1.0, 0.1));
		off.setSpecularPower(0);
		
		on = new PhongMaterial();
		on.setDiffuseColor(new Color(0.2, 1, 1.0, 0.1));
		
		on.setSpecularPower(0);
		turnOff();
	}
	
	public void toggle(){
		if (state){
			turnOff();
		} else {
			turnOn();
		}
	}
	
	public boolean isOn(){
		return state;
	}
	
	public void turnOn(){
		state = true;
		bulb.setMaterial(on);
		light.setVisible(true);
	}
	
	public void turnOff(){
		state = false;
		bulb.setMaterial(off);
		light.setVisible(true);
	}
	
	public String toString(){
		return loc.toString();
	}
	
	public PointLight getLight(){
		return light;
	}
	
	public Sphere getBulb(){
		return bulb;
	}
}
