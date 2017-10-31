/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcduilwuit;

/**
 *
 * @author anup.s
 */
import java.util.Date;
import com.sun.lwuit.events.*;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.Button;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.plaf.UIManager;

public class DateField extends Item implements ActionListener {

    com.sun.lwuit.Label lbl;
//Calendar cldr;
    Button dateSelected;
    Date date;
    public static final int DATE = 1;
    public static final int DATE_TIME = 3;
    public static final int TIME = 2;
    ItemStateListener iListener;
    private static String[] MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DateField(String label, int mode) {
        super(lcduilwuit.Display.boxLayout_Y);
        if (mode != DATE) {
            throw new IllegalArgumentException("Only Implemented for DATE type");
        }
        lbl = new com.sun.lwuit.Label(label);
        date = new Date();
        dateSelected = new Button(getDateString());
        dateSelected.addActionListener(this);
        addComponent(lbl);
        addComponent(dateSelected);
    }

    public String getLabel() {
        return lbl.getText();
    }

    public Date getDate() {
        return date;
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == dateSelected) {
            final Dialog d = new Dialog("");
            d.setScrollable(false);
            final com.sun.lwuit.Calendar cldr = new com.sun.lwuit.Calendar();
            Style s = cldr.getMonthViewUnSelectedStyle() ;
            s.setMargin(0, 0, 0, 0);
            s.setPadding(0, 0, 0, 0);
            cldr.setMonthViewUnSelectedStyle(s);
            s = cldr.getMonthViewSelectedStyle() ;
            s.setMargin(0, 0, 0, 0);
            s.setPadding(0, 0, 0, 0);
            cldr.setMonthViewSelectedStyle(s) ;
            cldr.getStyle().setBgTransparency(255, true);
            d.addComponent(cldr);
            UIManager m = UIManager.getInstance();

            d.addCommand(new com.sun.lwuit.Command(m.localize("cancel", "Cancel")) {

                public void actionPerformed(ActionEvent evt) {
                    d.dispose();
                }
            });
            d.addCommand(new com.sun.lwuit.Command(m.localize("ok", "OK")) {

                public void actionPerformed(ActionEvent evt) {
                    setDate(cldr.getDate());
                    d.dispose();
                    if (iListener != null) {
                        iListener.itemStateChanged(DateField.this);
                    }
                }
            });
            d.show(0, 0, 0, 0, false);

        }
    }

    public void setDate(Date date) {
        this.date.setTime(date.getTime());
        dateSelected.setText(getDateString());
        revalidate();
    }

    public void setItemStateListener(ItemStateListener i) {
        iListener = i;
    }

    public String getDateString() {
        java.util.Calendar cl = java.util.Calendar.getInstance();
        cl.setTime(date);
        StringBuffer dateStr = new StringBuffer();
        dateStr.append(cl.get(java.util.Calendar.YEAR));
        dateStr.append("-");
        dateStr.append(MONTHS[cl.get(java.util.Calendar.MONTH)]);
        dateStr.append("-");
        dateStr.append(cl.get(java.util.Calendar.DAY_OF_MONTH));       
        return dateStr.toString();
    }
        public String getDateSimpleString() {
        java.util.Calendar cl = java.util.Calendar.getInstance();
        cl.setTime(date);
        StringBuffer dateStr = new StringBuffer();
        dateStr.append(cl.get(java.util.Calendar.YEAR));
        dateStr.append("-");
        dateStr.append(cl.get(java.util.Calendar.MONTH));
        dateStr.append("-");
        dateStr.append(cl.get(java.util.Calendar.DAY_OF_MONTH));
        return dateStr.toString();
    }
}
