
from cubeutil import Cube, SocketConnection
import time, random, math, itertools

def main():

    cube = Cube(8, SocketConnection(4444))
    t = 0

    cube.startFrame()

    points = set()
    for blah in itertools.permutations([0,0,0,7,7,7], 3):
        points.add(blah[:3])

    for point in points:
        cube.turnOnLED(*point)


    cube.endFrame()
    

if __name__ == '__main__':
    main()