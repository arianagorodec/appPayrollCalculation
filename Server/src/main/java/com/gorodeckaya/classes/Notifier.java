package com.gorodeckaya.classes;

public abstract class Notifier {
    public static int Error = 2;
    public static int IsEmployee = 0;
    public static int IsAdmin = 1;
    protected int mask;

    protected Notifier next;

    public Notifier(int mask){
        this.mask=mask;
    }

    public Notifier setNext(Notifier notifier){
        next=notifier;
        return notifier;
    }
    public void message(String msg, int priority){
        if(priority>=mask){
            writeMessage(msg);
        }
        if (next!=null){
            next.message(msg, priority);
        }
    }

    abstract protected void writeMessage(String msg);
}
