
from cubeutil import Cube, SocketConnection
import time, random, math

def main():

    cube = Cube(8, SocketConnection(4444))
    t = 0

    i = 0
    while True:
        cube.startFrame()

        make_slice(cube, i % cube.n)

        make_slice(cube, i % cube.n, 'y')

        i += 1
        cube.endFrame()
        time.sleep(.09)


def make_slice(cube, dist, var='x'):

    if var == 'x' :
        for y in range(cube.n):
            for z in range(cube.n):
                cube.turnOnLED(dist, y, z)

    if var == 'y' :
        for x in range(cube.n):
            for z in range(cube.n):
                cube.turnOnLED(x, dist, z)


if __name__ == '__main__':
    main()