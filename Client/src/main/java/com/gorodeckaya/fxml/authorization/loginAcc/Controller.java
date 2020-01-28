package com.gorodeckaya.fxml.authorization.loginAcc;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.classes.Employee;
import com.gorodeckaya.classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private TextField login;
    @FXML
    private Label stateLogin;
    @FXML
    private PasswordField password;
    @FXML
    private Label statePassword;

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/authorization.fxml", "Авторизация");
    }

    public void entryButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        if (checkStateText() == 2) {
            List<Employee> list = new ArrayList<>();
            list = Employee.loadEmployee();
            if (list.isEmpty()) {
                if (login.getText().equals("admin") && password.getText().equals("admin"))
                    sc.changeScene(event, "/view/accPage.fxml", "Меню");
                else
                    JOptionPane.showMessageDialog(null, "Данные введены неверно!");
            } else {
                if (User.checkUser(login.getText(), password.getText(),"checkAdmin") == 1)
                    sc.changeScene(event, "/view/accPage.fxml", "Меню");
            }
            // sc.changeScene(event, "fxml/employeeMenu/employeePage.fxml", "Меню");
        }
        else
            JOptionPane.showMessageDialog(null, "Введены не все поля!");

    }

    //This method for check statement
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public int checkStateText() {
        int stateForText = 0; //6
        if (!isNullOrEmpty(login.getText())) {
            stateLogin.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateLogin.setTextFill(Color.web("#ff0000"));
        if (!isNullOrEmpty(password.getText())) {
            statePassword.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            statePassword.setTextFill(Color.web("#ff0000"));

        return stateForText;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
