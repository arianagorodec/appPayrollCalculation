package com.gorodeckaya.classes;

public class IsAdminNotifier extends Notifier {
    public IsAdminNotifier(int mask) {
        super(mask);
    }

    protected void writeMessage(String msg) {
        System.out.println("Админ вошёл: " + msg);
    }
}
