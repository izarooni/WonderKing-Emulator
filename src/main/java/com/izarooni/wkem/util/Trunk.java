package com.izarooni.wkem.util;

import com.izarooni.wkem.service.Backbone;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author izarooni
 */
public final class Trunk {

    public static Connection getConnection() throws SQLException {
        if (pool == null) {
            pool = JdbcConnectionPool.create("jdbc:h2:./storage/wkem;AUTO_SERVER=true;AUTO_RECONNECT=true", "root", "");
        }
        return pool.getConnection();
    }

    public static void migrate(Connection con) throws IOException, SQLException {
        URL resource = Backbone.class.getClassLoader().getResource("db/init.sql");
        assert resource != null;
        try (InputStream st = resource.openStream()) {
            try (InputStreamReader reader = new InputStreamReader(st)) {
                ScriptRunner.eval(con, reader);
                con.commit();
            }
        }
    }

    private static JdbcConnectionPool pool;

    private Trunk() {
    }
}
