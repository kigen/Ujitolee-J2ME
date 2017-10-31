/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia;

import com.kigen.changia.Model.Discussion;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.table.TableLayout;

/**
 *
 * @author Seth
 */
public class DiscussionsRenderer extends Container implements ListCellRenderer {

    TextArea content = new TextArea();
    Container bottom = new Container(new TableLayout(1, 2));
    Label date = new Label("");
    Label user = new Label("");
    Label focus = new Label("");

    DiscussionsRenderer() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        content.setEditable(false);
        content.setFocusable(false);
        content.getStyle().setBorder(Border.createEmpty());
        content.setRows(3);     
        content.getStyle().setBgTransparency(0);
        date.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        date.getStyle().setBgTransparency(0);
        user.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_SMALL));
        user.getStyle().setBgTransparency(0);
        user.setRTL(true);
        bottom.addComponent(date);
        bottom.addComponent(user);
        this.getStyle().setBorder(Border.createCompoundBorder(Border.createEmpty(), Border.createDashedBorder(1), Border.createEmpty(), Border.createEmpty()));

        addComponent(content);
        addComponent(bottom);
        this.setFocusable(true);
        focus.getStyle().setBgTransparency(20, true);
    }

    public Component getListCellRendererComponent(List list, Object o, int i, boolean bln) {
        Discussion discuss = (Discussion) o;
        content.setText(discuss.getContent());
        user.setText(discuss.getUser());
        date.setText(discuss.getDate());
        return this;
    }

    public Component getListFocusComponent(List list) {
        //focus.getStyle().setBorder(Border.createEtchedRaised());
        focus.getStyle().setBgColor(CENTER, true);
        return focus;
    }
}
