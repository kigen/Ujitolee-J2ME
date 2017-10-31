/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcduilwuit;

import com.sun.lwuit.Container;
import com.sun.lwuit.Label;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Component;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Button;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import java.util.Vector;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.events.FocusListener;
/**
 *
 * @author anup.s
 */
public class ChoiceGroup extends Item implements ActionListener, Choice{

    com.sun.lwuit.Label label;
    int chType;
    //Vector elements;
    ButtonGroup bg;
    DefaultListModel dlm;
    ComboBox cBox;
    ItemStateListener iListener;

    public String getLabel() {
        return label.getText();
    }
    public ChoiceGroup(String lbl, Vector items)
    {
        super(lcduilwuit.Display.boxLayout_Y);
        this.label = new com.sun.lwuit.Label(lbl);
        if (label.getStyle().getFont().stringWidth(label.getText()) > label.getWidth())
           this.label.setFocusable(true);
        else
            label.setFocusable(false);
        addComponent(this.label);
        chType = POPUP;
        dlm = new DefaultListModel(items);
        cBox = new ComboBox(dlm);
        addComponent(cBox);
     
    }
    public ChoiceGroup(String lbl, int choiceType) {
        super(lcduilwuit.Display.boxLayout_Y);
        if (!(choiceType == MULTIPLE || choiceType == EXCLUSIVE || choiceType == POPUP)) {
            throw new IllegalArgumentException("Only choice type MULTIPLE,EXCLUSIVE allowed");
        }

        this.label = new com.sun.lwuit.Label(lbl);
        if (label.getStyle().getFont().stringWidth(label.getText()) > label.getWidth())
           this.label.setFocusable(true);
        else
            label.setFocusable(false);
        chType = choiceType;
        addComponent(this.label);
        if (chType == EXCLUSIVE) {
            bg = new ButtonGroup();
        } else if (chType == POPUP) {
            cBox = new ComboBox();
            addComponent(cBox);
        }
        dlm = new DefaultListModel();

    }

    public void delete(int elementNum) {
        Component cmp = (Component) dlm.getItemAt(elementNum);
        dlm.removeItem(elementNum);
        switch (chType) {

            case EXCLUSIVE:
                bg.remove((RadioButton) cmp);
            case MULTIPLE:
                removeComponent(cmp);//for both RadioButton and CheckBox
                break;
            case POPUP:
                cBox.setModel(dlm);
                break;
        }

    }

    private Button getComponent(String txt) {

        switch (chType) {
            case MULTIPLE:
                return new CheckBox(txt);
            case EXCLUSIVE:
                return new RadioButton(txt);
        }
        return null;
    }

    public int append(String stringPart, javax.microedition.lcdui.Image imagePart) {
        if (chType == MULTIPLE || chType == EXCLUSIVE) {
            Button cmp = getComponent(stringPart);
            dlm.addItem(cmp);
            if (cmp instanceof RadioButton) {
                bg.add((RadioButton) cmp);
            }
            addComponent(cmp);
            cmp.addActionListener(this);
        //return elements.size()-1;
        } else {
            dlm.addItem(stringPart);
            cBox.setModel(dlm);
        }
        return dlm.getSize() - 1;
    }

    public int size() {
        return dlm.getSize();
    }

    public void actionPerformed(ActionEvent evt) {
        if (iListener != null) {
            if (evt.getSource() instanceof RadioButton || evt.getSource() instanceof ComboBox || evt.getSource() instanceof CheckBox) {
                iListener.itemStateChanged(this);
            }
        }
    }

    public void setItemStateListener(ItemStateListener i) {
        iListener = i;
    }

    public int getSelectedIndex() {
        switch (chType) {
            case MULTIPLE:
                return -1;
            case EXCLUSIVE:
                return bg.getSelectedIndex();
            case POPUP:
                return cBox.getSelectedIndex();
        }
        return -1;
    }

    public boolean isSelected(int elementNum) {
        if (chType == POPUP) {
            return cBox.getSelectedIndex() == elementNum ? true : false;
        }


        Object comp = dlm.getItemAt(elementNum);//elements.elementAt(elementNum);
        if (comp instanceof CheckBox) {
            return ((CheckBox) comp).isSelected();
        } else if (comp instanceof RadioButton) {
            return ((RadioButton) comp).isSelected();
        }
        return false;
    }

    public void setSelectedIndex(int elementNum, boolean selected) {
        if (chType == POPUP && selected) {
            cBox.setSelectedIndex(elementNum);
        } else if (chType == EXCLUSIVE && selected) {
            bg.setSelected(elementNum);
        } else if (chType == MULTIPLE && dlm.getItemAt(elementNum) instanceof CheckBox) {
            ((CheckBox) dlm.getItemAt(elementNum)).setSelected(selected);
        }
    }

    public void setLabel(String lbl) {
        label.setText(lbl);
        if (label.getStyle().getFont().stringWidth(label.getText()) > label.getWidth()) {
            label.setFocusable(true);
        }else
            label.setFocusable(false);

    }

    public String getString(int elementNum) {
        return chType == POPUP ? (String) dlm.getItemAt(elementNum) : ((Button) dlm.getItemAt(elementNum)).getText();
    }

    public void setSelectedFlags(boolean[] selectedArray) {
        if (selectedArray == null) {
            throw new NullPointerException("Selected Array is null");
        }
        if (selectedArray.length < dlm.getSize()) {
            throw new IllegalArgumentException("Selected Array is null");
        }

        if (chType == MULTIPLE) {
            for (int i = 0; i < dlm.getSize(); i++) {
                ((CheckBox) dlm.getItemAt(i)).setSelected(selectedArray[i]);
            }
            return;
        }
        outer:
        if (chType == EXCLUSIVE || chType == POPUP) {
            for (int i = 0; i < dlm.getSize(); i++) {
                if (selectedArray[i] == true) {

                    if (chType == POPUP) {
                        cBox.setSelectedIndex(i);
                    } else {
                        bg.setSelected(i);
                    }
                    break outer;
                }
            }
            if (chType == POPUP) {
                cBox.setSelectedIndex(0);
            } else {
                bg.setSelected(0);
            }
        }

    }

    public void set(int elementNum, String stringPart, com.sun.lwuit.Image imagePart) {
        switch (chType) {
            case EXCLUSIVE:
            case MULTIPLE:
                ((Button) dlm.getItemAt(elementNum)).setText(stringPart);
                return;
            case POPUP:
                dlm.setItem(elementNum, stringPart);
                cBox.setModel(dlm);
                return;

        }
    }

    public int getSelectedFlags(boolean[] selectedArray_return) {
        if (selectedArray_return == null) {
            throw new NullPointerException("selectedArray_return is null");
        }
        if (selectedArray_return.length < dlm.getSize()) {
            throw new IllegalArgumentException("selectedArray_return < dlm.getSize()");
        }
        int selectedCount = 0;
        if (chType == MULTIPLE) {
            for (int i = 0; i < dlm.getSize(); i++) {
                boolean isSelected = ((CheckBox) dlm.getItemAt(i)).isSelected();
                selectedArray_return[i] = isSelected;
                if (isSelected) {
                    selectedCount++;
                }
            }
        } else if (chType == EXCLUSIVE || chType == POPUP) {
            for (int i = 0; i < dlm.getSize(); i++) {
                selectedArray_return[i] = false;
            }
            int selectedIndex = 0;
            if (chType == EXCLUSIVE) {
                selectedIndex = bg.getSelectedIndex();
            } else if (chType == POPUP) {
                selectedIndex = cBox.getSelectedIndex();
            }
            if (selectedIndex >= 0) {
                selectedArray_return[selectedIndex] = true;
                selectedCount = 1;
            }
        }
        return selectedCount;
    }

    
}

