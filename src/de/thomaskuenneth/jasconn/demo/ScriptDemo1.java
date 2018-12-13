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
package de.thomaskuenneth.jasconn.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptDemo1 {

    private static final Logger LOGGER = Logger.getGlobal();

    public static void main(String[] args) {
        ScriptEngineManager sem = new ScriptEngineManager();
        List<ScriptEngineFactory> list = sem.getEngineFactories();
        System.out.println("installed script engines");
        System.out.println("------------------------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println();
            ScriptEngineFactory f = (ScriptEngineFactory) list.get(i);
            String engineName = f.getEngineName();
            String engineVersion = f.getEngineVersion();
            String langName = f.getLanguageName();
            String langVersion = f.getLanguageVersion();
            System.out.println(1 + i + ": " + engineName + " " + engineVersion + " " + langName + " " + langVersion + "\n");

            List<String> mimeTypes = f.getMimeTypes();
            outputList(mimeTypes, "mime types:");

            List<String> names = f.getNames();
            outputList(names, "names     :");

            List<String> exts = f.getExtensions();
            outputList(exts, "extensions:");

            ScriptEngine e = f.getScriptEngine();

            String statement = f.getOutputStatement("'hello, world'");
            System.out.println("getOutputStatement: " + statement);
            System.out.println("run the script");
            try {
                e.eval(statement);
            } catch (ScriptException ex) {
                LOGGER.log(Level.SEVERE, "eval()", ex);
            }

            String program = f.getProgram(new String[]{statement});
            System.out.println("\nbuild a program: " + program);
            System.out.println("run the script");
            try {
                e.eval(program);
            } catch (ScriptException ex) {
                LOGGER.log(Level.SEVERE, "eval()", ex);
            }

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat();
            String method = f.getMethodCallSyntax("sdf", "format", new String[]{"date"});
            e.put("sdf", sdf);
            e.put("date", date);
            if (method != null) {
                System.out.println("\nshow method calls: " + method);
                try {
                    System.out.println("result: " + e.eval(method));
                } catch (ScriptException ex) {
                    LOGGER.log(Level.SEVERE, "eval()", ex);
                }
            }
        }
    }

    private static void outputList(List<String> list, String info) {
        if (list != null) {
            System.out.println(info + " " + String.join(" ", list));
        }
    }
}
