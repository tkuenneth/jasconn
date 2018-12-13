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
package de.thomaskuenneth.dialogpanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;
import javax.swing.JComponent;

public class DialogSeparator extends JComponent {

    private final Dimension preferredSize;

    public DialogSeparator() {
        this.preferredSize = new Dimension(64, 10);
    }

    @Override
    public void setPreferredSize(Dimension s) {
        this.preferredSize.width = s.width;
        this.preferredSize.height = s.height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.preferredSize);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(32, 4);
    }

    @Override
    public void paint(Graphics g) {
        Dimension size = getSize();
        int y = size.height / 2;
        g.setColor(SystemColor.control);
        g.drawLine(0, y, size.width, y);
        g.setColor(SystemColor.controlShadow);
        g.drawLine(0, y + 1, size.width, y + 1);
    }
}
