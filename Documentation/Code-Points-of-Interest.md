**Two locations where you implement defensive programming**

1. One location where we used defensive programming (design by contract) is in the LEDCube.java file. We use asserts to not only ensure that the cube resolution is at least 1, we check to see that each value passed for setting and toggling LED’s are within the bounds of the cube.
2. In ClientTask.java we used the fail-fast programming to immediately return when a given frame is malformed. This ensures that the LEDCube does not get updated with partial data.


**Two locations where you might have corrected code smells**

1. One code smell that we corrected was Feature Envy. The Handlers have to know a lot about the CubeApp class in order to manipulate the screen. We implemented a singleton to make accessing the cube easier for the handler code, we also implemented methods for moving and modifying the code via simplified parameters. These methods were then accessed by the handlers to only update what is explicitly needed by the handler.
2. Another code smell that we corrected was the Large Class smell. Our handlers were implemented in place as static classes within the CubeApp, meaning that our CubeApp class was massive, since it contained all the drawing logic AND classes to represent event handling. So we fixed this by breaking out the handlers to their own files and having them implement the handler interface.

**Locations that are responsible for inter-processes/threads communication**

1. CubeApp ServerThread - used in the CubeApp start routine to create a ServerSocket on port 4444.
2. Python Client Socket - spins up client TCP Socket to send frames to the CubeApp.

**Three locations where you implement three design patterns**

1. Command Design Pattern - src/factory/KeyboardHandler.java, this location is where we implemented our command pattern. The CubeApp uses the command interface to correctly attach handlers to the corresponding calling methods.
2. Singleton Design Pattern - src/CubeApp.java, this class implements the singleton pattern, mainly so that the handlers can statically reference the LEDCube at run time.
3. Factory Design Pattern - scr/factory/LEDCubeFactory.java, this factory pattern is used to easily initialize the cube to have different starting configurations.
