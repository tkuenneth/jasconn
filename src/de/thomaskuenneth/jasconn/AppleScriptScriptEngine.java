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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.script.SimpleScriptContext;

public class AppleScriptScriptEngine implements ScriptEngine {

    private static final Logger LOGGER = Logger.getGlobal();

    private static final String MY_ENGINE_VERSION = "V0.03a";
    private static final String MY_NAME = "Java-AppleScript-Connector";
    private static final String MY_SHORT_NAME = "jasconn";
    private static final String STR_APPLESCRIPT = "AppleScript";
    private static final ScriptEngineFactory MY_FACTORY = new AppleScriptScriptEngineFactory();

    private ScriptContext defaultContext;

    public AppleScriptScriptEngine() {
        initialize();
    }

    private void initialize() {
        setContext(new SimpleScriptContext());
        put("javax.script.language_version", getLanguageVersion());
        put("javax.script.language", STR_APPLESCRIPT);
        put("javax.script.engine", MY_NAME);
        put("javax.script.engine_version", MY_ENGINE_VERSION);
        put("javax.script.argv", "");
        put("javax.script.filename", "");
        put("javax.script.name", MY_SHORT_NAME);
        put("THREADING", null);
    }

    @Override
    public Object eval(String script) throws ScriptException {
        return eval(script, getContext());
    }

    @Override
    public Object eval(String string, ScriptContext scriptContext) throws ScriptException {
        File f = Utilities.save(string);
        if (f != null) {
            Object result = Utilities.runScript(f);
            f.delete();
            return result;
        }
        throw new ScriptException("jasconn: unspecified error");
    }

    @Override
    public Object eval(String script, Bindings bindings) throws ScriptException {
        Bindings current = getContext().getBindings(100);
        getContext().setBindings(bindings, 100);
        Object result = eval(script);
        getContext().setBindings(current, 100);
        return result;
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        return eval(getScriptFromReader(reader));
    }

    @Override
    public Object eval(Reader reader, ScriptContext scriptContext) throws ScriptException {
        return eval(getScriptFromReader(reader), scriptContext);
    }

    @Override
    public Object eval(Reader reader, Bindings bindings) throws ScriptException {
        return eval(getScriptFromReader(reader), bindings);
    }

    @Override
    public void put(String key, Object value) {
        getBindings(100).put(key, value);
    }

    @Override
    public Object get(String key) {
        return getBindings(100).get(key);
    }

    @Override
    public Bindings getBindings(int scope) {
        return getContext().getBindings(scope);
    }

    @Override
    public void setBindings(Bindings bindings, int scope) {
        getContext().setBindings(bindings, scope);
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptContext getContext() {
        return this.defaultContext;
    }

    @Override
    public void setContext(ScriptContext scriptContext) {
        this.defaultContext = scriptContext;
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return MY_FACTORY;
    }

    private String getLanguageVersion() {
        String cmd = "get the version of AppleScript";
        try {
            return eval(cmd).toString();
        } catch (ScriptException e) {
            LOGGER.log(Level.SEVERE, "getLanguageVersion()", e);
        }
        return null;
    }

    private static String getScriptFromReader(Reader reader) {
        try {
            StringWriter script = new StringWriter();
            int data;
            while ((data = reader.read()) != -1) {
                script.write(data);
            }
            script.flush();
            return script.toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "getScriptFromReader()", e);
        }
        return null;
    }
}
