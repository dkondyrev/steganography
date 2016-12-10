package ru.nsu.fit.bro.algorithms;

/**
 * This class provides an interface for converting int to bytes and bytes to int
 */
public class Bytes {
    public static int bytesToInt(byte[] bytes, int start){
        int result = 0;
        for(int i = start; i < start + 4; i++){
            result |= ((int)bytes[2 * start + 3 - i] & 255) << i * 8;
        }
        return result;
    }

    public static byte[] intToBytes(int num){
        byte[] bytes = new byte[4];
        for(int i = 0; i < 4; i++){
            bytes[i] = (byte)((num >> ((3 - i) * 8)) & 255);
        }
        return bytes;
    }
}
