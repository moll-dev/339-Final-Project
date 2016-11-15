package cubesim;

import javafx.geometry.Point3D;
import javafx.scene.Group;

/**
 *  LEDCube Object:
 *  
 *  Object which holds a 3D array of LEDs
 *  Each LED is a JavaFX Model, with it's own
 *  methods.
 *  
 * @author Thomas
 */
public class LEDCube extends Group{
	
	private LED[][][] array; 
	private int nsize;
	private float edgeSize;
	private Group lights;
	private Group bulbs;
	private Point3D pos;
	private double offset;
	
	public LEDCube(int n, Point3D p, float size){
		super();
		nsize = n;
		edgeSize = size;
		pos = p;
		//lights = new Group();
		bulbs = new Group();
		array = new LED[n][n][n];
		
		offset = size/n/2;
	}
	
	public void clear(){
		for(int i=0; i<nsize; i++){
			for(int j=0; j<nsize; j++){
				for(int k=0; k<nsize; k++){
					array[i][j][k].turnOff();
				}
			}
		}
	}
	
	public int getN(){
		return nsize;
	}
	
	public void LedSet(int x, int y, int z, boolean val){
		if (val){
			array[x][y][z].turnOn();
		} else {
			array[x][y][z].turnOff();
		}
	}
	
	public boolean LedVal(int x, int y, int z){
		return array[x][y][z].isOn();
	}
	
	public void LedOn(int x, int y, int z){
		array[x][y][z].turnOn();
	}
	
	public void LedOff(int x, int y, int z){
		array[x][y][z].turnOff();;
	}
	
	public void LedTog(int x, int y, int z){
		array[x][y][z].toggle();
	}
		
	public void init(){
		double x, y, z;
		LED led;
		
		x = -edgeSize/2 + offset + pos.getX();
		for(int i=0; i<nsize; i++){
			y = -edgeSize/2 + offset + pos.getY();
			for(int j=0; j<nsize; j++){
				z = -edgeSize/2 + offset + pos.getZ();
				for(int k=0; k<nsize; k++){
					led = new LED(new Point3D(x,z,y));
					array[i][j][k] = led;
					//lights.getChildren().add(led.getLight());
					bulbs.getChildren().add(led.getBulb());
					z += edgeSize/nsize;
				}
				y += edgeSize/nsize;
			}
			x += edgeSize/nsize;
		}
		
		//getChildren().addAll(lights);
		getChildren().addAll(bulbs);
	}
}
