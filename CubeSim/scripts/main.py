import socket, time
from cubeutil import Cube, SocketConnection

def main():
    """ Simple prompt program for displaying on the sim """

    c = Cube(8, SocketConnection(4444))

    prompt = ''
    c.startFrame()

    while prompt != 'exit':
        prompt = input('>')

        if 'SEND' in prompt:
            c.endFrame()
            c.startFrame()

        elif 'ON' in prompt:
            c.turnOnLED(*[int(n) for n in prompt[2:].split(',')])

        elif 'OFF' in prompt:
            c.turnOffLED(*[int(n) for n in prompt[3:].split(',')])

        elif 'WAIT' in prompt:
            time.sleep(int(prompt[4:]))

        elif 'EXIT' in prompt:
            break

        else:
            print('Error: Command not recognized')

if __name__ == '__main__':
    main()