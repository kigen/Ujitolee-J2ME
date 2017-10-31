/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lcduilwuit;

/**
 *
 * @author anup.s
 */
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BoxLayout;
public class TextBox extends  com.sun.lwuit.Form implements Displayable {
TextArea txt;
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
    public static final int INITIAL_CAPS_SENTENCE =  com.sun.lwuit.TextField.INITIAL_CAPS_SENTENCE;

public TextBox(String title, String text, int maxSize, int constraints)
{
    super(title);
    setLayout(lcduilwuit.Display.boxLayout_Y);
    txt = new TextArea(text,5,5,constraints);
    addComponent(txt);

}
   /* public String getLabel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setItemStateListener(ItemStateListener i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    public int 	getMaxSize()
    {
        return txt.getMaxSize();
    }
     public int  setMaxSize(int size)
     {
         txt.setMaxSize(size);
         return size;
     }
     public void setString(String str)
     {
         txt.setText(str);
     }
     public int size()
     {
         return txt.getText().length();
     }
     public String getString()
     {
         return txt.getText();
     }
}
