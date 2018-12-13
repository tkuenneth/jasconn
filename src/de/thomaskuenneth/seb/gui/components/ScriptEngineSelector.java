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

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScriptEngineSelector extends JPanel {

    private final JLabel label;
    private final JComboBox combobox;

    private static final ScriptEngineManager SEM = new ScriptEngineManager();

    public ScriptEngineSelector(ActionListener l) {
        super(new BorderLayout());
        this.label = new JLabel("Available script engines:");
        this.combobox = new JComboBox();
        this.combobox.setRenderer(new ScriptEngineSelectorListCellRenderer());
        this.combobox.addActionListener(l);
        List<ScriptEngineFactory> list = SEM.getEngineFactories();
        for (ScriptEngineFactory entry : list) {
            this.combobox.addItem(entry);
        }
        initialize();
    }

    private void initialize() {
        add(this.label, BorderLayout.WEST);
        add(this.combobox, BorderLayout.CENTER);
    }
}
