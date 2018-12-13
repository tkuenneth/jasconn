/*
 *    This file is part of jasconn.
 *
 *    jasconn is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    jasconn is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.thomaskuenneth.jasconn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

public class Utilities {

    private static final Logger LOGGER = Logger.getGlobal();

    private static final String CMD = "/usr/bin/osascript";
    private static final String ARGS = "-ss";

    public static final String EMPTY_STRING = "";

    public static File save(String script) {
        File f = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            f = File.createTempFile("jasconn_", ".scpt");
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write(script);
        } catch (IOException e) {
            f = null;
            LOGGER.log(Level.SEVERE, "Failed to create file", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "close() failed", e);
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "close() failed", e);
                }
            }
        }
        return f;
    }

    public static Object runScript(File f) throws ScriptException {
        int exit = 0;
        StringBuilder sbIS = new StringBuilder();
        StringBuilder sbES = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder((new String[]{CMD, ARGS, f.getAbsolutePath()}));
        try {
            Process p = pb.start();
            InputStream is = p.getInputStream();
            InputStream es = p.getErrorStream();
            while (p.isAlive()) {
                int isData;
                if (is.available() > 0) {
                    isData = is.read();
                } else {
                    isData = -1;
                }
                int esData;
                if (es.available() > 0) {
                    esData = es.read();
                } else {
                    esData = -1;
                }
                if (isData != -1) {
                    sbIS.append(new Character((char) isData));
                }
                if (esData != -1) {
                    sbES.append(new Character((char) esData));
                }
            }
            try {
                exit = p.exitValue();
            } catch (IllegalThreadStateException e) {
                LOGGER.log(Level.SEVERE, "Could not retrieve exit value", e);
            }
        } catch (IOException e) {
            throw new ScriptException(e.getMessage());
        }
        if (exit == 0) {
            return sbIS;
        } else {
            throw new ScriptException(sbES.toString());
        }
    }
}
