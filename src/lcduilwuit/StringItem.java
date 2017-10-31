/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcduilwuit;

/**
 *
 * @author anup.s
 */
import com.sun.lwuit.Component;
import com.sun.lwuit.Font;
import com.sun.lwuit.events.FocusListener;

public class StringItem extends Item {

    com.sun.lwuit.Label l1, l2;

    public StringItem(String label, String text) {
        super(lcduilwuit.Display.boxLayout_Y);
        if (Font.getDefaultFont().stringWidth(label) < com.sun.lwuit.Display.getInstance().getDisplayWidth() / 2) {
            setLayout(lcduilwuit.Display.boxLayout_X);
        }
        setScrollable(false);
        l1 = new com.sun.lwuit.Label(label);
        l2 = new com.sun.lwuit.Label(text);
        addComponent(l1);
        addComponent(l2);
        setFocusable(true);
        addFocusListener(new FocusListener() {

            public void focusGained(Component cmp) {
                try {
                    if (l2.getStyle().getFont().stringWidth(l2.getText()) > l2.getWidth()) {
                        l2.startTicker(100, true);
                    }
                }
                catch (Exception ex)
                {
                }

            }

            public void focusLost(Component cmp) {
                l2.stopTicker();
            }
        });

    }

    public String getLabel() {
        return l1.getText();
    }

    public String getText() {
        return l2.getText();
    }

    public void setText(String txt) {
        l2.setText(txt);
    }

    public void setItemStateListener(ItemStateListener i) {
    }
}
