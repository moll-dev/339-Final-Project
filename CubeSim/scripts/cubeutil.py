''' LED Cube Utilities file '''
import socket
from itertools import chain

class Cube():
    """ Cube data structure

        - Stores data in a 1D array
        - Use getIndex and getXYZ to convert coords
    """

    def __init__(self, side_length, connection):
        self.n = side_length
        self.buffer = bytearray(self.n*self.n*self.n)
        self.connection = connection

    def __str__(self):
        return str( ''.join(str(int(n)) for n in self.buffer))

    def startFrame(self):
        self.buffer = bytearray(self.n * self.n * self.n)
        
    def endFrame(self):
        self.connection.writeData(self.buffer)

    def turnOnLED(self, x, y, z):
        self.buffer[self.getIndex(x,y,z)] = 1

    def turnOffLED(self, x, y, z):
        self.buffer[self.getIndex(x,y,z)] = 0

    def getLEDVal(self, x, y, z):
        return self.buffer[self.getIndex(x,y,z)]

    def getIndex(self, x, y, z):
        return x + y * self.n + z * (self.n * self.n)

    def getXYZ(self, i):
        n = self.n
        return (i/(n*n), (i/n)%n, i%n)

class SerialConnection():
    """ Connection used to actually talk to the cube """

    def __init__(self):
        pass


    def writeData(self, data):
        pass


class SocketConnection():
    """ Connection used to talk to our simulator """

    def __init__(self, port, ip='localhost'):
        self.port = port
        self.ip = ip
        self.server = (ip, port)
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        self.socket.connect(self.server)

    def writeData(self, data):
        self.socket.sendall(data)



''' OLD CUBE

    DAVIES DIDN'T REALLY WORK WELL SO YEAH '''
class cube:
    def __init__(self):
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        self.ClearBuffer()


        server = ('localhost', 4444)

        print("Trying to connect")
        self.s.connect(server)
        
    def LedSet(self, x, y, z, v):
        i = 16 * z + 2 * y + (1 if x > 3 else 0)
        o = (x % 4) * 2
        self.buffer[i + 1] = ((self.buffer[i + 1] & ~(0x03 << o)) | v << o)
        

    def LedVal(self, x, y, z):
        i = 16 * z + 2 * y + (1 if x > 3 else 0)
        o = (x % 4) * 2
        return (self.buffer[i + 1] >> 0) & 0x03
        
    def LedOn(self, x, y, z):
        self.LedSet(x, y, z, 0x01)
        
    def LedOff(self, x, y, z):
        self.LedSet(x, y, z, 0x00)   
        
    def LedTog(self, x, y, z):
        self.LedSet(x, y, z, 0x01 - self.LedVal(x, y, z))
        
    def WriteBuffer(self):
        self.s.sendall(self.buffer)

        try:
            buff = bytearray(130)
            self.s.sendall(buff)

        finally:
            print("Done!")
        
    def ClearBuffer(self):
        self.buffer = bytearray(130)
        self.buffer[0] = 0x91
        self.buffer[129] = 0xFF

    def Close(self):
        self.s.close()
