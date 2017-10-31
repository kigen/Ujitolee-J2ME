/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lcduilwuit;

/**
 *
 * @author anup.s
 */
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.UIManager;
public class Display {
private static Display instance;
    private Display()
    {
        instance=this;
    }

    public void setCurrent(Displayable frm)
    {
        if(frm==null) return;
        if(frm instanceof Alert)
          setCurrent((Alert)frm, getCurrent() );
       else
        frm.show();
    }
    public Displayable getCurrent()
    {
       return (Displayable)com.sun.lwuit.Display.getInstance().getCurrent();
    }
    public static Display getDisplay(javax.microedition.midlet.MIDlet middlet)
    {
        if(instance==null)
            instance = new Display();
        return instance;
    }
     public static Display getDisplay()
     {
         return getDisplay(null);
     }
     public void setCurrent(final Alert alert, Displayable nextDisplayable)
     {
         UIManager  m = UIManager.getInstance();
         
         if(alert.getCommandCount()<=0)
         {
             alert.addCommand(new com.sun.lwuit.Command(m.localize("ok", "OK"))
             {
                 public void actionPerformed(com.sun.lwuit.events.ActionEvent ae)
                 {
                     alert.dispose();
                 }
             });
         
         }
         alert.showDialog();
         //alert.show();
         nextDisplayable.show();
     }

    public static final BoxLayout boxLayout_Y=new BoxLayout(BoxLayout.Y_AXIS);
    public static final BoxLayout boxLayout_X=new BoxLayout(BoxLayout.X_AXIS);
}
