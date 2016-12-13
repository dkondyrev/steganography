package ru.nsu.fit.bro.client.model;

/**
 * Created by konstantin on 13.12.2016.
 */
public class CoderResultInfo {

    private String path;
    private String key;

    public CoderResultInfo(String key, String path) {
        this.key = key;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getKey() {
        return key;
    }
}
