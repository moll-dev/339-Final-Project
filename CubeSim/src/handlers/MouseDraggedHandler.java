package handlers;

import cubesim.CubeApp;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseDraggedHandler implements EventHandler<MouseEvent>{
    private static final double CONTROL_MULTIPLIER = 0.1;    
    private static final double SHIFT_MULTIPLIER = 10.0;    
    private static final double MOUSE_SPEED = 0.1;    
    private static final double ROTATION_SPEED = 0.5;    
    private static final double TRACK_SPEED = 1.0;
	
	@Override
	public void handle(MouseEvent me) {
		// Use the CubeApp Singleton
		CubeApp mApp = cubesim.CubeApp.instance();
		
        mApp.setOldMousePos(mApp.getMousePos());
        mApp.setMousePos(new Point2D(me.getSceneX(), me.getSceneY()));
        
        Point2D delta = mApp.getMousePos().subtract(mApp.getOldMousePos());
	    double modifier = 1.0;
	
 	    if (me.isControlDown()) {
 	        modifier = CONTROL_MULTIPLIER;
	    } 
	    if (me.isShiftDown()) {
	        modifier = SHIFT_MULTIPLIER;
	    }     
	    if (me.isPrimaryButtonDown()) {
	    	Point2D vector = delta.multiply(modifier*ROTATION_SPEED);
	    	mApp.orbitCamera(vector);
	    }
	    else if (me.isSecondaryButtonDown()) {
	    	double amount = delta.getX()*MOUSE_SPEED*modifier;
	    	mApp.zoomCamera(amount);
	    }
	    else if (me.isMiddleButtonDown()) {
	    	Point2D vector = delta.multiply(MOUSE_SPEED*modifier*TRACK_SPEED);
	    	mApp.panCamera(vector);
	    }
   }
}
