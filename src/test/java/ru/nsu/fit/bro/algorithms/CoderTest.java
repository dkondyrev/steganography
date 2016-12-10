package ru.nsu.fit.bro.algorithms;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;


public class CoderTest {
    private String text = "This is text message for testing coder algorithm";
    private byte[] message = text.getBytes();
    byte[] length = Bytes.intToBytes(message.length);
    private int width = 900;
    private int height = 1700;
    private int bound = width * height * 3;

    @Test
    public void testCode() throws Exception {
        Coder coder = new Coder();
        Random r = new Random(1239875);

        byte[] whiteImage = createImage(Color.WHITE.getRGB());
        byte[] blackImage = createImage(Color.BLACK.getRGB());
        byte[] greyImage = createImage(Color.GRAY.getRGB());

        long key;
        for(int i = 0; i < 50; i++){
            key = r.nextLong();
            testImage(coder, key, whiteImage);
            testImage(coder, key, greyImage);
            testImage(coder, key, blackImage);
        }
    }

    private byte[] createImage(int color) throws IOException {
        BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                im.setRGB(i, j, color);
            }
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(im, "bmp", stream);
        byte[] image = stream.toByteArray();
        stream.close();
        return image;
    }

    private void testImage(Coder coder, long key, byte[] image) throws IOException {
        Random random = new Random(key);

        byte[] newImage = coder.code(new String(message), image, key);
        BufferedImage newIm = ImageIO.read(new ByteArrayInputStream(newImage));

        assertEquals("Incorrect new image size", image.length, newImage.length);

        testBytes(random, newIm, length);
        testBytes(random, newIm, message);
    }

    private void testBytes(Random random, BufferedImage newIm, byte[] buffer){
        for(int i = 0; i < buffer.length; i++){
            for(int j = 0; j < 8; j++) {
                int index = random.nextInt(bound);

                int pixelNumber = index / 3;
                int y = pixelNumber / width;
                int x = pixelNumber % width;
                byte newBit = extractLSB(newIm.getRGB(x, y), index % 3);

                assertEquals("Incorrect LSB", extractLSB(buffer[i], j), newBit);
            }
        }
    }

    private byte extractLSB(int color, int colorOffset){
        int colorByte = (color >> ((2 - colorOffset) * 8)) & 0xFF;
        return (byte)(colorByte & 1);
    }

    private byte extractLSB(byte b, int position){
        return (byte)((b >> (7 - position)) & 1);
    }
}