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

import java.awt.Component;
import javax.script.ScriptEngineFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ScriptEngineSelectorListCellRenderer extends DefaultListCellRenderer
        implements ListCellRenderer<Object> {

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if ((value instanceof ScriptEngineFactory)) {
            value = toString((ScriptEngineFactory) value);
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

    public static String toString(ScriptEngineFactory f) {
        return f.getEngineName() + " " + f.getEngineVersion();
    }
}
