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
package de.thomaskuenneth.seb;

import de.thomaskuenneth.seb.gui.components.ScriptEngineInfopanel;
import de.thomaskuenneth.seb.gui.components.ScriptEngineSelector;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ScriptEngineBrowser extends JFrame {

    private JPanel scriptEngineSelector;
    private JPanel scriptEngineInfopanel;

    public ScriptEngineBrowser() {
        super("ScriptEngineBrowser");
        buildContentPane();
    }

    private void buildContentPane() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });
        JPanel cp = new JPanel(new BorderLayout());
        setContentPane(cp);
        scriptEngineInfopanel = new ScriptEngineInfopanel();
        scriptEngineSelector = new ScriptEngineSelector((ActionListener) scriptEngineInfopanel);
        cp.add(scriptEngineSelector, BorderLayout.NORTH);
        cp.add(scriptEngineInfopanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void quit() {
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ScriptEngineBrowser().setVisible(true);
        });
    }
}
