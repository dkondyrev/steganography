package ru.nsu.fit.bro.algorithms;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;


public class DecoderTest {
    private String message = "This is text message for testing coder algorithm";
    private int width = 900;
    private int height = 1700;

    @Test
    public void testDecode() throws Exception {
        Coder coder = new Coder();
        Decoder decoder = new Decoder();
        Random r = new Random(1239875);

        byte[] whiteImage = createImage(Color.WHITE.getRGB());
        byte[] blackImage = createImage(Color.BLACK.getRGB());
        byte[] greyImage = createImage(Color.GRAY.getRGB());

        long key;
        for(int i = 0; i < 50; i++){
            key = r.nextLong();
            testImage(coder, decoder, key, whiteImage);
            testImage(coder, decoder, key, greyImage);
            testImage(coder, decoder, key, blackImage);
        }
    }

    private void testImage(Coder coder, Decoder decoder, long key, byte[] image) throws IOException {
        byte[] newImage = coder.code(message, image, key);
        String extractedMessage = decoder.decode(newImage, key);
        assertEquals("Incorrect extracted message", message, extractedMessage);
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
}