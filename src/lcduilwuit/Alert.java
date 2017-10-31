/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcduilwuit;

/**
 *
 * @author anup.s
 */
import com.sun.lwuit.Dialog;
import com.sun.lwuit.TextArea;
import javax.microedition.lcdui.AlertType;
import com.sun.lwuit.Image;

public class Alert extends Dialog implements Displayable {

    TextArea body;
    public static final int FOREVER = 10000;

    public Alert(String title) {
        //   Constructs a new, empty Alert object with the given title.
        super(title);
        setLayout(lcduilwuit.Display.boxLayout_Y);
        body = new TextArea("", 5, 5,TextArea.UNEDITABLE);
        addComponent(body);
    }

    public Alert(String title, String alertText, Image alertImage, AlertType alertType) {
        this(title);
        body.setText(alertText);
    }

    public void setString(String str) {
        body.setText(str);
        revalidate();
    }

    public void setTimeout(long time) {
     //   if (time != FOREVER) {
   //         super.setTimeout(time);
     //   }
    }

    public void setType(AlertType type) {
        int myType = TYPE_WARNING;

        if (type == AlertType.ALARM) {
            myType = TYPE_ALARM;
        } else if (type == AlertType.CONFIRMATION) {
            myType = TYPE_CONFIRMATION;
        } else if (type == AlertType.ERROR) {
            myType = TYPE_ERROR;
        } else if (type == AlertType.INFO) {
            myType = TYPE_INFO;
        } else if (type == AlertType.WARNING) {
            myType = TYPE_WARNING;
        }
        setDialogType(myType);

    }
}
