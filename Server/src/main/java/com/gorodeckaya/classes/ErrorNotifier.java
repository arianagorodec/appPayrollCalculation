package com.gorodeckaya.classes;

public class ErrorNotifier  extends Notifier {
    public ErrorNotifier(int mask) {
        super(mask);
    }

    protected void writeMessage(String msg) {
        System.out.println("Ошибка: " + msg);
    }
}