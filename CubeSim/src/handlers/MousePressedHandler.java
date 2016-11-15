package handlers;

import cubesim.CubeApp;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MousePressedHandler implements EventHandler<MouseEvent> {
		
		private CubeApp mApp;
		
		public MousePressedHandler(CubeApp app) {
			mApp = app;
		}
		
        @Override public void handle(MouseEvent me) {
        	mApp.setMousePos(new Point2D(me.getSceneX(), me.getSceneY()));
            mApp.setOldMousePos(new Point2D(me.getSceneX(), me.getSceneY()));
        }
}
