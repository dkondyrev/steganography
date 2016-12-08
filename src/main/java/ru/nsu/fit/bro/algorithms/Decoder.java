package ru.nsu.fit.bro.algorithms;

import java.io.IOException;

/**
 * This class provides an interface for extracting a text from an image
 */
public class Decoder {

    /**
     *
     * @param image the image with the embedded text
     * @param key the secret key which is known only to sender and recipient
     * @return the extracted message
     * @throws IOException if an error occurs during reading the image
     */
    public String decode(byte[] image, long key) throws IOException {
        return null;
    }
}
