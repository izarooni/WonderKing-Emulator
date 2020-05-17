package com.izarooni.wkem.io;

import com.izarooni.wkem.service.Backbone;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author izarooni
 */
public class Config {

    public static boolean load(String fileName) {
        try {
            if (fileName == null) fileName = FILE_NAME;
            File file = new File(fileName);
            if (!file.exists() && file.createNewFile()) {
                wini = new Wini(Backbone.class.getClassLoader().getResource(fileName));
                wini.setFile(file);
                wini.store(wini.getFile());
            } else {
                (wini = new Wini(file)).load();
            }
            LOGGER.info("Created configuration file, server will be hosted on localhost:10001 by default");
            return true;
        } catch (IOException e) {
            LOGGER.error("Failed to config settings file '{}'", fileName, e);
        }
        return false;
    }

    public static void save() throws IOException {
        wini.store(wini.getFile());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private static final String FILE_NAME = "server.ini";
    private static Wini wini;

    private Config() {

    }

    public static class Server {

        static {
            Address = wini.get("server", "ip", String.class);
            Port = wini.get("server", "port", int.class);
            ChannelCount = wini.get("server", "channels", int.class);
            AutoRegister = wini.get("server", "auto_register", boolean.class);
        }

        public static String Address;
        public static int Port;
        public static int ChannelCount;
        public static boolean AutoRegister;
    }
}
