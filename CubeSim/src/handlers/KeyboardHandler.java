package handlers;

import cubesim.CubeApp;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

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
			case T:
				mApp.clearCube();
				break;
			case G:
				mApp.getCube().LedTog(0, 0, 0);
				break;
			default:
				break;
		}
	}
}
