package com.izarooni.wkem.io;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author izarooni
 */
public class Config {

    private final Wini wini;

    public Config(File file) throws IOException {
        wini = new Wini(file);
    }

    public Config(String fileName, URL resource) throws IOException {
        wini = new Wini(resource);
        wini.setFile(new File(fileName));
        save();
    }

    public <T> T getProperty(String section, String propertyName, Class<T> clazz) {
        return wini.get(section, propertyName, clazz);
    }

    public void setProperty(String section, String propertyName, Object value) {
        wini.put(section, propertyName, value);
    }

    public void save() throws IOException {
        wini.store(wini.getFile());
    }
}
