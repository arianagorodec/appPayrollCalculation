package com.gorodeckaya.fxml.registration;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.ServerConnection;
import com.gorodeckaya.classes.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class Controller implements Initializable{

    @FXML
    private TextField surname;
    @FXML
    private Label stateSurname;
    @FXML
    private TextField name;
    @FXML
    private Label stateName;
    @FXML
    private TextField patronymic;
    @FXML
    private Label statePatronymic;
    @FXML
    private TextField bank_account;
    @FXML
    private Label stateBankAccount;
    @FXML
    private TextField login;
    @FXML
    private Label stateLogin;
    @FXML
    private PasswordField password;
    @FXML
    private Label statePassword;
    @FXML
    private PasswordField passwordR;
    @FXML
    private Label statePasswordR;

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/authorization.fxml", "Вход");
    }


    //This method for check statement
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public int checkStateText() {
        int stateForText = 0; //6
        if (!isNullOrEmpty(surname.getText())) {
            stateSurname.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateSurname.setTextFill(Color.web("#ff0000"));
        if (!isNullOrEmpty(name.getText())) {
            stateName.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateName.setTextFill(Color.web("#ff0000"));
        if (!isNullOrEmpty(patronymic.getText())) {
            statePatronymic.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            statePatronymic.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(bank_account.getText())){
            stateBankAccount.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            stateBankAccount.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(login.getText())){
            stateLogin.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            stateLogin.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(password.getText())){
            statePassword.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            statePassword.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(passwordR.getText())){
            statePasswordR.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            statePasswordR.setTextFill(Color.web("#ff0000"));

        return stateForText;
    }


    public Employee readEmployee() {
        Employee employee = new Employee();
        employee.setSurname(surname.getText());
        employee.setName(name.getText());
        employee.setPatronymic(patronymic.getText());
        employee.setBankAccount(bank_account.getText());
        employee.setLogin(login.getText());
        employee.setPassword(password.getText());
        return employee;
    }

    public boolean checkPassword(){
        if (!password.getText().equals(passwordR.getText())){
            JOptionPane.showMessageDialog(null, "Вы ввели несовпадающие пароли!");
            password.setText("");
            passwordR.setText("");
            return false;
        }
        else
            return true;
    }

    public void AddUser() throws Exception {
        int stateForText = checkStateText();
        if (stateForText == 7) {
            if (checkPassword()) {
                Employee employee = readEmployee();
                List<Object> list = new ArrayList();
                Employee newEmployee = new Employee(employee, "addUser");
                Object obj = (Object) newEmployee;
                list = (ServerConnection.send(obj));
                if (Boolean.parseBoolean(list.get(0).toString())) {
                    JOptionPane.showMessageDialog(null, "Зарегистрирован!");
                } else
                    JOptionPane.showMessageDialog(null, "Такой пользователь уже существует или сотрудник не найден!");
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Пожалуйста, заполните все поля");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
