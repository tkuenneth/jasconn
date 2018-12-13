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
package de.thomaskuenneth.seb.gui.components;

import de.thomaskuenneth.dialogpanel.DialogPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.script.Compilable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class ScriptEngineInfopanel extends JPanel implements ActionListener {

    public static final String PROPERTY_ENGINE_CHANGED = "propertyEngineChanged";

    private ScriptEngine scriptEngine;

    private final JTabbedPane cp;
    private final DialogPanel generalTab;
    private final JTextField languageNameAndVersion;
    private final JTextField engineNameAndVersion;
    private final JTextField names;
    private final JTextField extensions;
    private final JTextField mimeTypes;
    private final JTextField implementsInvocable;
    private final JTextField implementsCompilable;
    private final ScriptRunnerPanel scriptRunnerPanel;

    public ScriptEngineInfopanel() {
        super(new BorderLayout());
        scriptEngine = null;

        cp = new JTabbedPane();
        generalTab = new DialogPanel();
        cp.addTab("General", generalTab);
        languageNameAndVersion = generalTab.createAndAddTextField("language name and version");

        engineNameAndVersion = generalTab.createAndAddTextField("engine name and version");

        names = generalTab.createAndAddTextField("additional short names");
        extensions = generalTab.createAndAddTextField("file extensions");
        mimeTypes = generalTab.createAndAddTextField("mime types");
        implementsInvocable = generalTab.createAndAddTextField("implements Invocable");

        implementsCompilable = generalTab.createAndAddTextField("implements Compilable");

        scriptRunnerPanel = new ScriptRunnerPanel(this);
        initialize();
    }

    private void initialize() {
        add(cp, BorderLayout.CENTER);
        cp.addTab("ScriptRunner", scriptRunnerPanel);
    }

    private void updateFields(ScriptEngineFactory f) {
        ScriptEngine oldEngine = scriptEngine;
        languageNameAndVersion.setText(f.getLanguageName() + " " + f.getLanguageVersion());
        engineNameAndVersion.setText(f.getEngineName() + " " + f.getEngineVersion());
        names.setText(toString(f.getNames()));
        extensions.setText(toString(f.getExtensions()));
        mimeTypes.setText(toString(f.getMimeTypes()));
        scriptEngine = f.getScriptEngine();
        updateTextComponent(implementsInvocable, implementsInterface(scriptEngine, Invocable.class));
        updateTextComponent(implementsCompilable, implementsInterface(scriptEngine, Compilable.class));
        firePropertyChange(PROPERTY_ENGINE_CHANGED, oldEngine, scriptEngine);
    }

    private static void updateTextComponent(JTextComponent tc, boolean val) {
        if (val) {
            tc.setText("Yes");
        } else {
            tc.setText("No");
        }
    }

    private static boolean implementsInterface(ScriptEngine engine, Class clazz) {
        return clazz.isAssignableFrom(engine.getClass());
    }

    private static String toString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (String entry : list) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(entry);
            }
        }
        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if ((o instanceof JComboBox)) {
            JComboBox cb = (JComboBox) o;
            o = cb.getSelectedItem();
            if ((o instanceof ScriptEngineFactory)) {
                ScriptEngineFactory f = (ScriptEngineFactory) o;
                updateFields(f);
            }
        }
    }
}
