package com.gorodeckaya.fxml.employeeMenu.edit.employee;

import com.gorodeckaya.ServerConnection;
import com.gorodeckaya.classes.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.gorodeckaya.SceneChanger;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Employee employee = new Employee();

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
    private DatePicker birthday;
    @FXML
    private Label stateBirthday;
    @FXML
    private TextField bank_account;
    @FXML
    private Label stateBankAccount;
    @FXML
    private TextField mobPhone;
    @FXML
    private TextField mail;
    @FXML
    private TextField address;
    @FXML
    private Button saveButton;

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/employeePage.fxml", "Вход");
    }

    //This method for check statement
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public int checkStateText() {
        int stateForText = 0; //5
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
        if (birthday.getValue() != null) {
            stateBirthday.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateBirthday.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(bank_account.getText())) {
            stateBankAccount.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateBankAccount.setTextFill(Color.web("#ff0000"));
        return stateForText;
    }

    public boolean checkRepeatEmployee(Employee newEmployee) throws Exception {
        List<Object> list = new ArrayList<>();
        Employee employee1 = new Employee(newEmployee, "checkEmployee");
        Object obj = (Object) employee1;
        list = (ServerConnection.send(obj));

        return Boolean.parseBoolean(list.get(0).toString());
    }

    private void setEmployeeText() {
        surname.setText(employee.getSurname());
        name.setText(employee.getName());
        patronymic.setText(employee.getPatronymic());
        birthday.setValue(employee.getBirthday());
        bank_account.setText(employee.getBankAccount());
        mobPhone.setText(employee.getMob_phone());
        address.setText(employee.getAddress());
        mail.setText(employee.getEmail());
    }

    public void saveButtonPushed() throws Exception {
        List<Object> list = new ArrayList<>();
        if (checkStateText() == 5) {
            Employee newEmployee = new Employee(employee, "editEmployee");
            newEmployee.setSurname(surname.getText());
            newEmployee.setName(name.getText());
            newEmployee.setPatronymic(patronymic.getText());
            newEmployee.setBankAccount(bank_account.getText());
            newEmployee.setBirthday(Date.valueOf(birthday.getValue().toString()).toLocalDate());
            newEmployee.setMob_phone(mobPhone.getText());
            newEmployee.setEmail(mail.getText());
            newEmployee.setAddress(address.getText());
            if (!checkRepeatEmployee(newEmployee)) {
                newEmployee.setType("editEmployee");
                try {
                    list = (ServerConnection.send(newEmployee));
                    if (list.get(0).equals("true")) {
                        employee = newEmployee;
                        JOptionPane.showMessageDialog(null, "Данные изменены!");
                    } else {
                        setEmployeeText();
                        JOptionPane.showMessageDialog(null, "Данные не изменены");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                JOptionPane.showMessageDialog(null, "Данный лицевой счёт уже занят!");
        } else
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
        setEmployeeText();

        saveButton.setOnAction(e -> {
            try {
                saveButtonPushed();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

}