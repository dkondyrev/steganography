package ru.nsu.fit.bro.algorithms;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * This class provides an interface for extracting a text from an image
 */
public class Decoder {
    private Random pixelNumberGenerator;

    /**
     *
     * @param image the image with the embedded text
     * @param key the secret key which is known only to sender and recipient
     * @return the extracted message
     * @throws IOException if an error occurs during reading the image
     */
    public String decode(byte[] image, long key) throws IOException {
        pixelNumberGenerator = new Random(key);
        BufferedImage im = ImageIO.read(new ByteArrayInputStream(image));
        int bound = im.getHeight() * im.getWidth() * 3;

        int byteNumber = nextByteNumber(bound);
        byte[] messageLength = new byte[4];

        for(int i = 0; i < messageLength.length; i++){
            for(int j = 0; j < 8; j++){
                messageLength[i] = extract(im, byteNumber, messageLength[i], j);
                byteNumber = nextByteNumber(bound);
            }
        }

        byte[] message = new byte[Bytes.bytesToInt(messageLength, 0)];
        for(int i = 0; i < message.length; i++){
            for(int j = 0; j < 8; j++){
                message[i] = extract(im, byteNumber, message[i], j);
                byteNumber = nextByteNumber(bound);
            }
        }

        return new String(message);
    }

    private byte extract(BufferedImage image, int byteNumber, byte b, int offset){
        int width = image.getWidth();

        int pixelNumber = byteNumber / 3;
        int y = pixelNumber / width;
        int x = pixelNumber % width;

        int color = image.getRGB(x, y);

        return extractLSB(color, byteNumber % 3, b, offset);
    }

    private byte extractLSB(int color, int colorOffset, byte b, int byteOffset){
        int colorByte = (color >> ((2 - colorOffset) * 8)) & 0xFF;
        int messageBit = (colorByte & 1) << (7 - byteOffset);
        b |= messageBit;
        return b;
    }

    private int nextByteNumber(int bound){
        return pixelNumberGenerator.nextInt(bound);
    }
}
