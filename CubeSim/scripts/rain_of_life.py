
from cubeutil import Cube, SocketConnection
import time, random, math

def main():

    cube = Cube(8, SocketConnection(4444))
    t = 0

    cube.startFrame()

    for i in range(8):
        cube.turnOnLED(0,i,0)

    cube.endFrame()

    count = 0
    while count < 500:
        cube.startFrame()
        
        t += 1

        for z in range(8):
            for y in range(8):
                x = round(3.5 + 3.5 * math.sin(z / 4.0 + y / 4.0 + t / 24))
                cube.turnOnLED(x,y,z)

        cube.endFrame()
        time.sleep(.016)
        count += 1



if __name__ == '__main__':
    main()