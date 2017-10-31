/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lcduilwuit;

/**
 *
 * @author anup.s
 */
import com.sun.lwuit.Container;
import com.sun.lwuit.layouts.*;
public abstract class Item extends Container {
public abstract String getLabel();
public abstract void setItemStateListener(ItemStateListener i);
public Item(Layout layout)
{
    super(layout);
    setWidth(100);
}
public Item()
{
    super();
   
}
}
