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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.HashMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DialogPanel extends JPanel implements LayoutManager {

    private HashMap<Component, DialogLabel> ht;
    private int hgap;
    private int vgap;

    public DialogPanel() {
        this(10, 4);
    }

    public DialogPanel(int hgap, int vgap) {
        this.ht = new HashMap<>();
        this.hgap = hgap;
        this.vgap = vgap;
        initialize();
    }

    private void initialize() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(this);
    }

    public JTextField createAndAddTextField(String label) {
        JTextField tf = DialogPanelComponentFactory.createReadOnlyTextField();
        addDialogComponent(tf, label);
        return tf;
    }

    public void addDialogComponent(JComponent c) {
        addDialogComponent(c, null);
    }

    public JComponent addDialogComponent(JComponent c, String name) {
        super.add(c);
        if (name != null) {
            DialogLabel dl = new DialogLabel(name + ":");
            super.add(dl);
            this.ht.put(c, dl);
        }
        return c;
    }

    @Override
    public Component add(Component c) {
        if ((c instanceof JComponent)) {
            addDialogComponent((JComponent) c);
            return c;
        }
        return null;
    }

    private Dimension calcOrLayout(Container container, boolean calcOnly) {
        Insets insets = container.getInsets();
        Dimension dMaxSize = new Dimension(0, 0);
        int insetsWidth = insets.left + insets.right;
        int insetsHeight = insets.top + insets.bottom;
        int offset = 0;
        int numberOfElements = 0;
        int componentCount = container.getComponentCount();
        for (int i = 0; i < componentCount; i++) {
            Component c = container.getComponent(i);
            if ((c.isVisible()) && (!(c instanceof DialogLabel))) {
                numberOfElements++;
                Dimension sizeComponent = c.getPreferredSize();
                Component label = ht.get(c);
                if (label != null) {
                    Dimension sizeLabel = label.getPreferredSize();
                    if (sizeLabel.width > offset) {
                        offset = sizeLabel.width;
                    }
                    sizeComponent.width += this.hgap + sizeLabel.width;
                    if (sizeLabel.height > sizeComponent.height) {
                        sizeComponent.height = sizeLabel.height;
                    }
                }
                if (sizeComponent.width > dMaxSize.width) {
                    dMaxSize.width = sizeComponent.width;
                }
                dMaxSize.height += sizeComponent.height;
            }
        }
        dMaxSize.height += (numberOfElements - 1) * this.vgap;
        dMaxSize.width += insetsWidth;
        dMaxSize.height += insetsHeight;
        offset += this.hgap + insets.left;
        if (!calcOnly) {
            int y = insets.top;
            container.getSize(dMaxSize);
            int maxWidth = dMaxSize.width - insetsWidth;
            for (int i = 0; i < container.getComponentCount(); i++) {
                Component c = container.getComponent(i);
                if ((c.isVisible()) && (!(c instanceof DialogLabel))) {
                    Dimension size = c.getPreferredSize();
                    if (numberOfElements == 1) {
                        dMaxSize.height -= insetsHeight;
                    }
                    int h = size.height;
                    Component label = (Component) this.ht.get(c);
                    int x;
                    if (label != null) {
                        Dimension sizeLabel = label.getPreferredSize();
                        label.setBounds(insets.left, y, sizeLabel.width, sizeLabel.height);
                        if (sizeLabel.height > h) {
                            h = sizeLabel.height;
                        }
                        size.width = (maxWidth - offset);
                        x = offset;
                    } else {
                        x = insets.left;
                        size.width = maxWidth;
                    }
                    c.setBounds(x, y, size.width, size.height);
                    y += this.vgap + h;
                }
            }
        }
        return dMaxSize;
    }

    @Override
    public void addLayoutComponent(String str, Component component) {
    }

    @Override
    public void removeLayoutComponent(Component component) {
    }

    @Override
    public void layoutContainer(Container container) {
        calcOrLayout(container, false);
    }

    @Override
    public Dimension minimumLayoutSize(Container container) {
        return preferredLayoutSize(container);
    }

    @Override
    public Dimension preferredLayoutSize(Container container) {
        return calcOrLayout(container, true);
    }
}
