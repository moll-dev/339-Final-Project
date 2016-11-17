package handlers;

import cubesim.CubeApp;
import factory.LEDCubeFactory;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class KeyboardHandler implements EventHandler<KeyEvent> {
	
	@Override
	public void handle(KeyEvent event) {
		CubeApp mApp = cubesim.CubeApp.instance();
		switch (event.getCode()) {
			case Z:
				mApp.resetCamera();
				break;
			case X:
				mApp.toggleAxis();
				break;
			case C:
				mApp.clearCube();
				break;
			default:
				LEDCubeFactory.CubeType type = LEDCubeFactory.CubeType.BLANK;
				
				if (event.getCode() == KeyCode.DIGIT1){
					type = LEDCubeFactory.CubeType.SQUARE;
				} else if (event.getCode() == KeyCode.DIGIT2){
					type = LEDCubeFactory.CubeType.PLANE;
				}
				
				LEDCubeFactory.transmuteCube(mApp.getCube(), type);
		}
		
	}
}
