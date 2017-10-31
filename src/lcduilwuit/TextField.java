/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcduilwuit;

import com.sun.lwuit.events.*;
import com.sun.lwuit.Component;
import com.sun.lwuit.Font;

/**
 *
 * @author anup.s
 */
public class TextField extends Item implements DataChangedListener, FocusListener {

    com.sun.lwuit.TextField tx;
    com.sun.lwuit.Label label;
    public static final int ANY = com.sun.lwuit.TextField.ANY;
    public static final int EMAILADDR = com.sun.lwuit.TextField.EMAILADDR;
    public static final int NUMERIC = com.sun.lwuit.TextField.NUMERIC;
    public static final int PHONENUMBER = com.sun.lwuit.TextField.PHONENUMBER;
    public static final int URL = com.sun.lwuit.TextField.URL;
    public static final int DECIMAL = com.sun.lwuit.TextField.DECIMAL;
    public static final int PASSWORD = com.sun.lwuit.TextField.PASSWORD;
    public static final int UNEDITABLE = com.sun.lwuit.TextField.UNEDITABLE;
    public static final int SENSITIVE = com.sun.lwuit.TextField.SENSITIVE;
    public static final int NON_PREDICTIVE = com.sun.lwuit.TextField.NON_PREDICTIVE;
    public static final int INITIAL_CAPS_WORD = com.sun.lwuit.TextField.INITIAL_CAPS_WORD;
    public static final int INITIAL_CAPS_SENTENCE = com.sun.lwuit.TextField.INITIAL_CAPS_SENTENCE;
    ItemStateListener iListener;

    public TextField(String lbl, String text, int maxSize, int constraints) {
        super(lcduilwuit.Display.boxLayout_Y);
        if (Font.getDefaultFont().stringWidth(lbl) < com.sun.lwuit.Display.getInstance().getDisplayWidth() / 2) {
            setLayout(lcduilwuit.Display.boxLayout_X);
        }

        label = new com.sun.lwuit.Label(lbl);
        addComponent(label);
        tx = new com.sun.lwuit.TextField(text);
        tx.setConstraint(constraints);
        tx.addDataChangeListener(this);
        switch(constraints)
        {
            case NUMERIC:
                tx.setInputModeOrder(new String[]{"123"});
                break;
            case EMAILADDR:
            case URL:
            case PASSWORD:
                tx.setInputModeOrder(new String[]{"abc","ABC","123"});
                break;
        }
        addComponent(tx);
        if (label.getStyle().getFont().stringWidth(label.getText()) > label.getWidth())
            tx.addFocusListener(this);

    }
    public void focusGained(Component cmp) {
        if (label.getStyle().getFont().stringWidth(label.getText()) > label.getWidth()) {
            label.startTicker(100, true);
        }

    }

    public void focusLost(Component cmp) {
        label.stopTicker();
    }
    public TextField(String text) {
        super(lcduilwuit.Display.boxLayout_X);
        tx = new com.sun.lwuit.TextField(text);
        addComponent(tx);


    }

    public String getString() {
        return tx.getText();
    }

    public void setString(String str) {
        tx.setText(str);
    }
    //public void actionPerformed(ActionEvent evt) {

    public void dataChanged(int type, int index) {
        if (iListener != null) {
            //if(evt.getSource() instanceof TextField)
            iListener.itemStateChanged(this);
        }
    }

    public void setLabel(String lbl) {

        label.setText(lbl);
        tx.removeFocusListener(this);
        if (label.getStyle().getFont().stringWidth(label.getText()) > label.getWidth())
         tx.addFocusListener(this);
    }

    public String getLabel() {
        return label.getText();
    }

    public void setItemStateListener(ItemStateListener i) {
        iListener = i;
    }

    public int getMaxSize() {
        return tx.getMaxSize();
    }

    public void setMaxSize(int size) {
        tx.setMaxSize(size);
    }

    public void insert(String src, int position) {
        StringBuffer sb = new StringBuffer(tx.getText());
        if(position<=0)
             sb.insert(0, src);
        else if(position>=sb.length())
            sb.append(src);
        else
             sb.insert(position, src);
        tx.setText(sb.toString());

    }

    public int getCaretPosition() {
        return tx.getCursorPosition();
    }

    public int size() {
        return tx.getText().length();
    }
}
