package ru.nsu.fit.bro.algorithms;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This class provides an interface for embedding a text into an image
 */
public class Coder {
    private Random r = new SecureRandom();
    private Random pixelNumberGenerator;

    /**
     *
     * @param message the text for embedding
     * @param image the source image
     * @param key the secret key which is known only to sender and recipient
     * @return the image with the embedded text
     * @throws IOException if an error occurs during reading or writing the image
     */
    public byte[] code(String message, byte[] image, long key) throws IOException {
        pixelNumberGenerator = new Random(key);

        BufferedImage im = ImageIO.read(new ByteArrayInputStream(image));
        int bound = im.getHeight() * im.getWidth() * 3;

        byte[] m = message.getBytes();
        int byteNumber = nextByteNumber(bound);

        byte[] length = Bytes.intToBytes(m.length);

        for(int i = 0; i < length.length; i++){
            for(int j = 0; j < 8; j++){
                match(im, byteNumber, length[i], j);
                byteNumber = nextByteNumber(bound);
            }
        }

        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < 8; j++){
                match(im, byteNumber, m[i], j);
                byteNumber = nextByteNumber(bound);
            }
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(im, "bmp", stream);
        image = stream.toByteArray();
        stream.close();

        return image;
    }

    private int nextByteNumber(int bound){
        return pixelNumberGenerator.nextInt(bound);
    }

    private void match(BufferedImage image, int byteNumber, byte b, int offset){
        int width = image.getWidth();

        int pixelNumber = byteNumber / 3;
        int y = pixelNumber / width;
        int x = pixelNumber % width;

        int color = image.getRGB(x, y);

        color = matchLSB(color, byteNumber % 3, b, offset);
        image.setRGB(x, y, color);
    }

    private int matchLSB(int color, int colorOffset, byte b, int byteOffset){
        int messageBit = (b >> (7 - byteOffset)) & 1;

        int colorByte = (color >> ((2 - colorOffset) * 8)) & 0xFF;

        if((colorByte & 1) != messageBit){
            if(0 == colorByte){
                colorByte++;
            }
            else if(255 == colorByte){
                colorByte--;
            }
            else{
                if(r.nextBoolean()){
                    colorByte++;
                }
                else{
                    colorByte--;
                }
            }
        }

        int mask = 0;
        for(int i = 0; i < 3; i++){
            if(i != colorOffset){
                mask |= (0xFF << ((2 - i) * 8));
            }
        }

        color = color & mask | (colorByte << ((2 - colorOffset) * 8));
        return color;
    }
}
