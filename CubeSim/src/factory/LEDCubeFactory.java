package factory;

import cubesim.LEDCube;
import javafx.geometry.Point3D;

public class LEDCubeFactory {
	
	private static int SPACING = 100;
	public enum CubeType { BLANK, SQUARE, PLANE };
	
	public static LEDCube transmuteCube(LEDCube cube, CubeType type) {
	
		cube.clear();
		
		switch (type) {
			case BLANK:
				break;
			case SQUARE:
				cube = genSquare(cube);
				break;
			case PLANE:
				cube = genPlane(cube);
				break;
		}
		
		return cube;
	}
	
	private static LEDCube genSquare(LEDCube cube) {
		int size = cube.getN();
		for (int n=0; n<size; n++){
			cube.LedOn(n, 0, 0);
			cube.LedOn(0, n, 0);
			cube.LedOn(0, 0, n);
			cube.LedOn(size-1, 0, n);
			cube.LedOn(size-1, n, 0);
			cube.LedOn(0, size-1, n);
			cube.LedOn(n, size-1, 0);
			cube.LedOn(n, 0, size-1);
			cube.LedOn(0, n, size-1);
			cube.LedOn(size-1, size-1, n);
			cube.LedOn(size-1, n, size-1);
			cube.LedOn(n, size-1, size-1);
		}
		return cube;
	}
	
	private static LEDCube genPlane(LEDCube cube) {
		int size = cube.getN();
		for (int i=0; i<size; i++){
			for (int j=0; j<size; j++){
				cube.LedOn(i, j, 0);
			}
		}
		return cube;
	}
	
	public static LEDCube makeCube(int size, Point3D pos, CubeType type) {
		LEDCube cube = new LEDCube(size, pos, SPACING);
		transmuteCube(cube, type);
		return cube;
	}
}
