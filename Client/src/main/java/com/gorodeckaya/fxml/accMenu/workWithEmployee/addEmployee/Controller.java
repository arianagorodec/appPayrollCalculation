package com.gorodeckaya.fxml.accMenu.workWithEmployee.addEmployee;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.ServerConnection;
import com.gorodeckaya.classes.Employee;
import com.gorodeckaya.classes.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private static ArrayList<Post> postList;

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
    private TextField rate;
    @FXML
    private Label stateRate;
    @FXML
    private ComboBox<String> post;
    @FXML
    private Label statePost;
    @FXML
    private ComboBox<String> department;
    @FXML
    private Label stateDepartment;
    @FXML
    private TextField mobPhone;
    @FXML
    private TextField mail;
    @FXML
    private TextField address;
    @FXML
    private Button addEmployee;

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/workEmployee.fxml", "Работа с сотрудниками");
    }

    public void addButtonPushed(ActionEvent event) throws Exception {
        AddEmployee();
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
        if (birthday.getValue() != null) {
            stateBirthday.setTextFill(Color.web("#000000"));
            stateForText++;
        } else
            stateBirthday.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(bank_account.getText())){
            stateBankAccount.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            stateBankAccount.setTextFill(Color.web("#ff0000"));

        if (!isNullOrEmpty(rate.getText())){
            stateRate.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            stateRate.setTextFill(Color.web("#ff0000"));

        if (!post.getSelectionModel().isEmpty()){
            statePost.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            statePost.setTextFill(Color.web("#ff0000"));
        if (!department.getSelectionModel().isEmpty()){
            stateDepartment.setTextFill(Color.web("#000000"));
            stateForText++;
        }
        else
            stateDepartment.setTextFill(Color.web("#ff0000"));

        return stateForText;
    }

    public Employee readEmployee() {
        Employee employee = new Employee();
        employee.setSurname(surname.getText());
        employee.setName(name.getText());
        employee.setPatronymic(patronymic.getText());
        employee.setBirthday(Date.valueOf(birthday.getValue().toString()).toLocalDate());
        employee.setBankAccount(bank_account.getText());
        Post newPost=new Post(post.getValue(),department.getValue(),Double.valueOf(rate.getText()));
        employee.setPost(newPost);
        employee.setRate(Double.valueOf(rate.getText()));
        employee.setMob_phone(mobPhone.getText());
        employee.setEmail(mail.getText());
        employee.setAddress(address.getText());
        //employee.setPayrollList(null);
        return employee;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public boolean checkRepeatEmployee(Employee newEmployee) throws Exception {
        List<Object> list = new ArrayList<>();

        Employee employee = new Employee(newEmployee,"checkEmployee");
        Object obj = (Object) employee;
        list = (ServerConnection.send(obj));

        return Boolean.parseBoolean(list.get(0).toString());
    }

    public void AddEmployee() throws Exception {
        int stateForText = checkStateText();
        if (stateForText == 8) {
            Employee employee = readEmployee();
            if (!checkRepeatEmployee(employee)) {
                List<Object> list = new ArrayList();
                Employee newEmployee = new Employee(employee,"addEmployee");
                Object obj = (Object) newEmployee;
                list = (ServerConnection.send(obj));
                if (Boolean.parseBoolean(list.get(0).toString())) {
                    JOptionPane.showMessageDialog(null, "Добавлен!");
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Такой сотрудник уже существует!");
        }
        else
            JOptionPane.showMessageDialog(null, "Пожалуйста, заполните все поля");
    }

    public void setPostPushed(ActionEvent event){
        if(department.getSelectionModel().isEmpty()) {
            ObservableList<String> departments = FXCollections.observableArrayList();
            for (Post newpost : postList) {
                if (post.getSelectionModel().getSelectedItem().equals(newpost.getName()) /*&& department.getSelectionModel().getSelectedItem().equals(newpost.getepartment())*/) {
                    departments.add(newpost.getDepartment());
                }
            }
            department.setItems(departments);
        }
    }

    public void setDepartmentPushed(ActionEvent event){
        if(post.getSelectionModel().isEmpty()) {
            ObservableList<String> posts = FXCollections.observableArrayList();
            for (Post newpost : postList) {
                if (department.getSelectionModel().getSelectedItem().equals(newpost.getDepartment()) /*&& department.getSelectionModel().getSelectedItem().equals(newpost.getepartment())*/) {
                    posts.add(newpost.getName());
                }
            }
            post.setItems(posts);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Object> list = new ArrayList();
        postList = new ArrayList<>();
        Post newpost = new Post("allPost");
        Object obj = (Object) newpost;
        try {
            list = (ServerConnection.send(obj));
            ObservableList<String> namePostList = FXCollections.observableArrayList();
            ObservableList<String> nameDepartmentList = FXCollections.observableArrayList();

            for (Object object : list) {
                Post post1 = (Post) object;
                postList.add(post1);
                namePostList.add(post1.getName());
                nameDepartmentList.add(post1.getDepartment());
            }
            post.setItems(namePostList);
            department.setItems(nameDepartmentList);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
