package cubesim;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * ClientTask:
 * 
 * Server Task that the JavaFX app uses to handle 
 * incoming input from the client script.
 * 
 * @author Thomas
 */
public class ClientTask extends Task<Void> {
	private ServerSocket s;
	private LEDCube cube;
	private byte[] frame;
	private int framesize;
	private Socket client;
	private DataInputStream in;

    public ClientTask(ServerSocket s, LEDCube cube) {
		System.out.println("Backend started!");
    	this.cube = cube;
    	framesize = cube.getN() * cube.getN() * cube.getN();
		frame = new byte[framesize];
		this.s = s;
    }
    
    public void handleFrame(byte[] frame){
    	if (frame == null) return;
		
    	// Get the size and clear our cube
		int size = cube.getN();
		cube.clear();
	
		// Iterate over the entire frame (1D) and 
		// set the corresponding LED on
		for (int i=0; i < framesize; i++){
			if (frame[i] == 1){
				int x = i / (size * size); 
				int y = (i/size) % size; 
				int z = i % size;
				cube.LedOn(x, y, z);
			}
		}
    }

    @Override protected Void call() throws Exception {
    	
    	PrintWriter out = null;
		try {
			System.out.println("Waiting on Client...");
			client = s.accept();
			out = new PrintWriter(client.getOutputStream(), true);
			System.out.println("Client Connected!");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		while(true){	
			
			out.println();
			if(out.checkError()){
				System.out.println("Client Disconnected");
				System.out.println("Waiting for new client...");
				client = s.accept();
				out = new PrintWriter(client.getOutputStream(), true);
			}
			in = new DataInputStream(client.getInputStream());

			try{
				// Read in our byte array
				in.readFully(frame, 0, frame.length);
		
				// Must run later in order for JavaFX to handle it
				Platform.runLater(new Runnable() {
					  @Override public void run() {
					    	handleFrame(frame);
					  }
				});

			} catch (IOException e){
				// Fail Gracefully if we have nothing to read
			}
	
		}
    }
}
