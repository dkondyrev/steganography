package ru.nsu.fit.bro.algorithms;

/**
 * This class provides an interface for embedding a text into an image
 */
public class Coder {

    /**
     *
     * @param message the text for embedding
     * @param image the source image
     * @param key the secret key which is known only to sender and recipient
     * @return the image with the embedded text
     */
    public byte[] code(String message, byte[] image, long key) {
        return null;
    }
}
