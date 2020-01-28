package com.izarooni.wkem.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author izarooni
 * @author ProjectInfinity
 */
public class DataReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataReader.class);

    public static void createCache() throws IOException {
        ItemFactory.loadBaseItemData();
        LOGGER.info("{} 'baseitemdata' entries cached", ItemFactory.baseItemData.size());
        MapFactory.createCache();
        LOGGER.info("{} map entries cached", MapFactory.templateMaps.size());
    }

    public static FileInputStream openFile(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static void magic_xor(byte[] dat, int fileSize) {
        for (int i = 0; i < fileSize; ++i) {
            dat[i] = (byte) (dat[i] ^ 197);
        }
    }
}
