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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class AppleScriptScriptEngineFactory implements ScriptEngineFactory {

    protected static final String FILEEXT = ".scpt";
    private static final String[] MIMETYPES = {"text/plain", "text/applescript", "application/applescript"};
    private static final String[] NAMES = {"jasconn", "Java-AppleScript-Connector"};

    private final ScriptEngine myScriptEngine;
    private final List<String> extensions;
    private final List<String> mimeTypes;
    private final List<String> names;

    public AppleScriptScriptEngineFactory() {
        myScriptEngine = new AppleScriptScriptEngine();
        extensions = Collections.nCopies(1, ".scpt");
        mimeTypes = Arrays.asList(MIMETYPES);
        names = Arrays.asList(NAMES);
    }

    @Override
    public String getEngineName() {
        return getScriptEngine().get(ScriptEngine.ENGINE).toString();
    }

    @Override
    public String getEngineVersion() {
        return getScriptEngine().get(ScriptEngine.ENGINE_VERSION).toString();
    }

    @Override
    public List<String> getExtensions() {
        return extensions;
    }

    @Override
    public List<String> getMimeTypes() {
        return mimeTypes;
    }

    @Override
    public List<String> getNames() {
        return names;
    }

    @Override
    public String getLanguageName() {
        return getScriptEngine().get(ScriptEngine.LANGUAGE).toString();
    }

    @Override
    public String getLanguageVersion() {
        return getScriptEngine().get(ScriptEngine.LANGUAGE_VERSION).toString();
    }

    @Override
    public Object getParameter(String key) {
        return getScriptEngine().get(key).toString();
    }

    @Override
    public String getMethodCallSyntax(String string, String string0, String... string1) {
        return null;
    }

    @Override
    public String getOutputStatement(String string) {
        String cmd = "tell application \"Finder\" to display dialog \"" + string + "\" buttons {\"OK\"}";
        return cmd;
    }

    @Override
    public String getProgram(String... statements) {
        StringBuilder sb = new StringBuilder();
        int len = statements.length;
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append(statements[i]);
        }
        return sb.toString();
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return myScriptEngine;
    }
}
