/*
 * Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
 * from the iBATIS Apache project. Only removed dependency on Resource class
 * and a constructor
 */
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.izarooni.wkem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptRunner.class);
    private static final String DELIMITER = ";";

    private ScriptRunner() {
    }

    /**
     * Runs an SQL script (read in using the Reader parameter) using the
     * connection passed in
     *
     * @param con    - the connection to use for the script
     * @param reader - the source of the script
     * @throws SQLException if any SQL errors occur
     * @throws IOException  if there is an error reading from the Reader
     */
    public static void eval(Connection con, Reader reader) throws IOException, SQLException {
        LineNumberReader lineReader = new LineNumberReader(reader);
        StringBuilder sql = new StringBuilder();
        for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("--") || line.startsWith("//")) continue;
            if (line.endsWith(DELIMITER) || line.equals(DELIMITER)) {
                sql.append(line, 0, line.lastIndexOf(DELIMITER)).append(" ");
                try (Statement stmt = con.createStatement()) {
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }
                Thread.yield();
            } else {
                sql.append(line).append(" ");
            }
        }
    }
}

