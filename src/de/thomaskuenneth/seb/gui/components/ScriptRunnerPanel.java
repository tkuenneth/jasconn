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

import de.thomaskuenneth.jasconn.Utilities;
import static de.thomaskuenneth.seb.gui.components.ScriptEngineInfopanel.PROPERTY_ENGINE_CHANGED;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ScriptRunnerPanel extends JSplitPane
        implements ActionListener, PropertyChangeListener {

    private static final String CMD_RUN = "Run";
    private static final String CMD_CLEAR = "Clear";

    private ScriptEngine scriptEngine;
    private JTextArea textareaInput;
    private JTextArea textareaOutput;

    public ScriptRunnerPanel(JComponent parent) {
        super(VERTICAL_SPLIT);
        initialize(parent);
    }

    private void initialize(JComponent parent) {
        parent.addPropertyChangeListener(this);
        scriptEngine = null;
        textareaInput = new JTextArea(10, 40);
        JPanel panelInput = new JPanel(new BorderLayout());
        JScrollPane scrollpaneInput = new JScrollPane(textareaInput);
        panelInput.add(scrollpaneInput, BorderLayout.CENTER);
        JPanel panelButtons = new JPanel();
        panelInput.add(panelButtons, BorderLayout.SOUTH);
        JButton buttonRun = new JButton(CMD_RUN);
        buttonRun.addActionListener(this);
        panelButtons.add(buttonRun);
        JButton buttonClear = new JButton(CMD_CLEAR);
        buttonClear.addActionListener(this);
        panelButtons.add(buttonClear);
        textareaOutput = new JTextArea(10, 40);
        textareaOutput.setEditable(false);
        JScrollPane scrollpaneOutput = new JScrollPane(textareaOutput);
        setTopComponent(panelInput);
        setBottomComponent(scrollpaneOutput);
    }

    private static String getExceptionText(ScriptException e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (CMD_RUN.equals(cmd)) {
            textareaOutput.setText(Utilities.EMPTY_STRING);
            String script = textareaInput.getText();
            try {
                Object result = scriptEngine.eval(script);
                String msg = "result is null";
                if (result != null) {
                    msg = result.toString();
                }
                textareaOutput.setText(msg);
            } catch (ScriptException ex) {
                textareaOutput.setText(getExceptionText(ex));
            } catch (Throwable thr) {
                textareaOutput.setText(thr.toString());
            }
        } else if (CMD_CLEAR.equals(cmd)) {
            textareaInput.setText(Utilities.EMPTY_STRING);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (PROPERTY_ENGINE_CHANGED.equals(evt.getPropertyName())) {
            Object o = evt.getNewValue();
            if ((o instanceof ScriptEngine)) {
                scriptEngine = ((ScriptEngine) o);
            }
        }
    }
}
