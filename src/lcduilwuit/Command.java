/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lcduilwuit;

/**
 *
 * @author anup.s
 */
public class Command extends com.sun.lwuit.Command {
    // just dummy
    public static final int BACK = 2;
    public static final int CANCEL = 3;
    public static final int EXIT = 7;
    public static final int HELP = 5;
    public static final int ITEM = 8;
    public static final int OK = 4;
    public static final int SCREEN = 1;
    public static final int STOP = 6;
    private int type = OK;
    private int priority=Integer.MAX_VALUE;

    public Command(String label, int commandType, int priority) {
        super(label);
        type = commandType;
        if(!(type==BACK||type==EXIT)) // so that it always comes in last of menu
             this.priority = priority;

    }
    public Command(String label,int priority) {
        super(label);
        this.priority = priority;
    }
    public Command(String shortLabel, String longLabel, int commandType, int priority) {
        this(longLabel,commandType,priority);
    }

    public Command(String label) {
        super(label);
    }

    public int getCommandType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }
}
