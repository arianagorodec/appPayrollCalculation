package com.gorodeckaya.classes;

import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberTextField extends TextField {
    private int maxLength;

    public NumberTextField() {
        this.maxLength = 30;
    }

    @Override
    public void replaceText(int i, int i1, String string) {
        if (validate(string)) {
            super.replaceText(i, i1, string);
        }
    }



    private boolean validate(String text) {
        boolean status = ("".equals(text) || text.matches("[0-9//.]"));
        if (text.matches("[0-9//.]"))
            return (status && this.getText().length() < maxLength);
        else
            return status;
    }
}