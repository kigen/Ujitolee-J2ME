/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lcduilwuit;

import com.sun.lwuit.Container;
import com.sun.lwuit.layouts.*;

/**
 *
 * @author anup.s
 */
import com.sun.lwuit.Image;
public class Label extends Item{
com.sun.lwuit.Label lbl;

    public Label(String lbl) {
     //  cntr = new Container(new BoxLayout(BoxLayout.X_AXIS));
       super(lcduilwuit.Display.boxLayout_Y);
        this.lbl = new com.sun.lwuit.Label (lbl) ;
        addComponent(this.lbl);
    }
    public Label(Image img) {
     //  cntr = new Container(new BoxLayout(BoxLayout.X_AXIS));
       super(lcduilwuit.Display.boxLayout_Y);
        this.lbl = new com.sun.lwuit.Label (img) ;
        addComponent(this.lbl);
    }
   public String getLabel() {
       return lbl.getText();
    }

    public void setItemStateListener(ItemStateListener i) {

    }

}
