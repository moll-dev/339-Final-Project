import socket, time

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

def main():

    c = cube()

    c.ClearBuffer()

    c.LedOn(0,0,0)
    c.LedOn(0,1,0)
    c.LedOn(1,0,0)

    c.WriteBuffer()

    time.sleep(1)

    c.ClearBuffer()

    c.LedOn(1,0,0)
    c.LedOn(0,1,0)
    c.LedOn(1,1,1)

    c.WriteBuffer()

    c.Close()

if __name__ == '__main__':
    main()