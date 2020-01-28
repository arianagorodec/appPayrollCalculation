package com.gorodeckaya.classes;

import javafx.scene.control.TextField;

public class NameTextField extends TextField {
    int maxLength=200;
    public  NameTextField(){
        super();
        //this.setPromptText("Введите");
    }
    @Override
    public void replaceText(int i,int i1,String string){
        if(validate(string)){
            super.replaceText(i, i1, string);
        }
    }
    private boolean validate(String text) {
        if (this.getText() != null) {
        }
        boolean status = ("".equals(text) || text.matches("[А-я]"));
        if (text.matches("[А-я]"))
            return (status && this.getText().length() < maxLength);
        else
            return status;
    }
}
