/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kigen.changia;

import com.kigen.changia.Model.Project;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;

import com.sun.lwuit.table.TableLayout;

/**
 *
 * @author Seth
 */
public class ProjectsRenderer extends Container implements ListCellRenderer {

    private Label title = new Label("");
    private Label location = new Label("Nairobi");
    private Label distance = new Label("");
    private Label date = new Label("0");
    private Label focus = new Label("");

    public ProjectsRenderer() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        title.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        title.getStyle().setBgTransparency(0);
        location.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        location.getStyle().setBgTransparency(0);

        distance.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        distance.getStyle().setBgTransparency(0);
        distance.setRTL(true);
        date.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
        date.getStyle().setBgTransparency(0);

        Container c = new Container(new TableLayout(1, 2));
        c.addComponent(location);
        c.addComponent(distance);

        title.setFocusable(true);
        location.setFocusable(true);
        date.setFocusable(true);
        addComponent(title);
        addComponent(c);
        addComponent(date);
        this.setFocusable(true);
        this.getStyle().setBorder(Border.createCompoundBorder(Border.createEmpty(), Border.createDottedBorder(1), Border.createEmpty(),Border.createEmpty()));

        focus.getStyle().setBgTransparency(50, true);
    }

    public Component getListCellRendererComponent(List list, Object o, int i, boolean bln) {
        Project project = (Project) o;
        title.setText(project.getTitle());
        location.setText(project.getCity() + ", " + project.getLocality());
        distance.setText(project.getDistance());
        date.setText(project.getStart());
        return this;
    }

    public Component getListFocusComponent(List list) {

        //focus.getStyle().setBorder(Border.createEtchedRaised());
        focus.getStyle().setBgColor(CENTER, true);
        focus.getStyle().setBgTransparency(50);
        return focus;
    }
}
