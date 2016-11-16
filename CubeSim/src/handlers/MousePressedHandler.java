package handlers;

import cubesim.CubeApp;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MousePressedHandler implements EventHandler<MouseEvent> {
	
        @Override public void handle(MouseEvent me) {
        	CubeApp mApp = cubesim.CubeApp.instance();
        	
        	mApp.setMousePos(new Point2D(me.getSceneX(), me.getSceneY()));
            mApp.setOldMousePos(new Point2D(me.getSceneX(), me.getSceneY()));
        }
}
