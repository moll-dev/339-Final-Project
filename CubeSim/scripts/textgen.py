from cubeutil import Cube, SocketConnection
from PIL import Image, ImageFont, ImageDraw
import time, random, math

''' Text to Cube! '''


def main():

    n = 8
    billboard = [[0 for j in range(n*4)] for i in range(n)]
    c = Cube(n, SocketConnection(4444))

    for row in billboard:
        for col in row:
            print(col, end=",")
        print()

    # font = ImageFont.truetype("Arial-Bold.ttf",14)
    font = ImageFont.truetype("Minecraft.ttf",n)

    img=Image.new("RGBA", (n*4, n),(255,255,255))
    draw = ImageDraw.Draw(img)
    draw.text((1, 1),"HELLO!",(0,0,0),font=font)
    draw = ImageDraw.Draw(img)
    img.convert('LA')

    for i, pixel in enumerate(img.getdata()):
        lum = (0.2126*pixel[0] + 0.7152*pixel[1] + 0.0722*pixel[2])

        if lum < 200:
            x = int(i / (n*4))
            y = int(i % (n*4))

            billboard[x][y] = 1
    
    c.startFrame()
    for y in range(n):
        for x in range(n*4):
            print(billboard[y][x], end=",")

            if billboard[y][x] == 1:
                c.turnOnLED(x, y , 0)

        print()

    c.endFrame()

    img.save("a_test.png")



if __name__ == '__main__':
    main()