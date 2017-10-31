/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Container;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.GridLayout;

/**
 *
 * @author Seth
 */
public class TimeSelector extends Container {

    String[] _day = {"AM", "PM"};
    ComboBox day_combo, hour, minute;

    public TimeSelector() {
        Container contain = new Container(new GridLayout(1, 3));       
        hour = new ComboBox();
        minute = new ComboBox();
        for (int i = 0; i < 60; i++) {
            minute.addItem(i<10? "0"+i : i+ "");
        }
        for (int i = 1; i <= 12; i++) {
            hour.addItem(i<10? "0"+i : i+ "");
        }

        day_combo = new ComboBox(_day);
        contain.addComponent(hour);
        contain.addComponent(minute);
        contain.addComponent(day_combo);
        addComponent(contain);
    }

    public String GetTime() {
        int _hour = Integer.parseInt(hour.getSelectedItem().toString());
        if (day_combo.getSelectedItem().equals("PM")) {
            if (_hour != 12) {
                _hour += 12;
            }

        } else {
            if (_hour == 12) {
                _hour = 0;
            }
        }
        return _hour + ":" + minute.getSelectedItem() + ":00";
    }
}
