package com.gorodeckaya.fxml.employeeMenu.edit.pl;

import com.gorodeckaya.ServerConnection;
import com.gorodeckaya.classes.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.gorodeckaya.SceneChanger;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TextField login;
    @FXML
    private Label stateLogin;
    @FXML
    private TextField newLogin;
    @FXML
    private Label stateNewLogin;
    @FXML
    private PasswordField password;
    @FXML
    private Label statePassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Label stateNewPassword;
    @FXML
    private PasswordField newPasswordR;
    @FXML
    private Label stateNewPasswordR;

    Employee employee = new Employee();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/employeePage.fxml", "Вход");
    }

    //This method for check statement
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    public int checkStateForPassword() {
        int stateForText = 0; //4
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
        if (!isNullOrEmpty(newPassword.getText())) {
            stateNewPassword.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateNewPassword.setTextFill(Color.web("#ff0000"));
        if (!isNullOrEmpty(newPasswordR.getText())) {
            stateNewPasswordR.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateNewPasswordR.setTextFill(Color.web("#ff0000"));
        return stateForText;
    }

    public int checkStateForLogin() {
        int stateForText = 0; //3
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
        if (!isNullOrEmpty(newLogin.getText())) {
            stateNewLogin.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateNewLogin.setTextFill(Color.web("#ff0000"));

        return stateForText;
    }

    public boolean checkRepeatLogin(Employee newEmployee) throws Exception {
        List<Object> list = new ArrayList<>();
        Employee employee1 =new Employee(newEmployee,"checkLogin");
        Object obj = (Object) employee1;
        list = (ServerConnection.send(obj));

        return Boolean.parseBoolean(list.get(0).toString());
    }

    public void savePasswordButtonPushed(ActionEvent event) throws IOException {
        List<Object> list = new ArrayList<>();
        if(checkStateForPassword()==4) {
            if (employee.getLogin().equals(login.getText()) && employee.getPassword().equals(password.getText())) {
                Employee newEmployee = new Employee(employee, "editEmployee");
                newEmployee.setPassword(newPassword.getText());
                try {
                    list = (ServerConnection.send(newEmployee));
                    if (list.get(0).equals("true")) {
                        employee = newEmployee;
                        setText();
                        JOptionPane.showMessageDialog(null, "Данные изменены!");
                    } else {
                        setText();
                        JOptionPane.showMessageDialog(null, "Данные не изменены");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Неверный логин или пароль!");
        }
        else
            JOptionPane.showMessageDialog(null, "Заполните все поля");

    }

    private void setText() {
        login.setText("");
        password.setText("");
        newPassword.setText("");
        newPasswordR.setText("");
        newLogin.setText("");
    }

    public void saveLoginButtonPushed(ActionEvent event) throws Exception {
        List<Object> list = new ArrayList<>();
        if(checkStateForLogin()==3) {
            if (employee.getLogin().equals(login.getText()) && employee.getPassword().equals(password.getText())) {
                Employee newEmployee = new Employee(employee, "editEmployee");
                newEmployee.setLogin(newLogin.getText());
                if (!checkRepeatLogin(newEmployee)) {
                    newEmployee.setType("editEmployee");
                    try {
                        list = (ServerConnection.send(newEmployee));
                        if (list.get(0).equals("true")) {
                            employee = newEmployee;
                            setText();
                            JOptionPane.showMessageDialog(null, "Данные изменены!");
                        } else {
                            setText();
                            JOptionPane.showMessageDialog(null, "Данные не изменены");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Данный логин уже занят!");
            }
            else
                JOptionPane.showMessageDialog(null, "Неверный логин или пароль!");
        }
        else
            JOptionPane.showMessageDialog(null, "Заполните все поля");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Employee> list = new ArrayList<>();
        list = Employee.loadEmployee();
        for (Employee e : list) {
            if (e.getId() == com.gorodeckaya.fxml.authorization.loginEmp.Controller.personalNumber) {
                employee = e;
                break;
            }
        }
    }

}