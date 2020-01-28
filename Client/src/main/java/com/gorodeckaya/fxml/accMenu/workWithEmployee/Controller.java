package com.gorodeckaya.fxml.accMenu.workWithEmployee;

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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, Integer> number;
    @FXML
    private TableColumn<Employee, String> surname;
    @FXML
    private TableColumn<Employee, String> name;
    @FXML
    private TableColumn<Employee, String> patronymic;
    @FXML
    private TableColumn<Employee, String> post;
    @FXML
    private TableColumn<Employee, String> department;
    @FXML
    private TableColumn<Employee, LocalDate> birthday;
    @FXML
    private TableColumn<Employee, Double> rate;
    @FXML
    private TableColumn<Employee, String> bankAccount;
    @FXML
    private TableColumn<Employee, String> email;
    @FXML
    private TableColumn<Employee, String> address;
    @FXML
    private TableColumn<Employee, String> mobphone;
    @FXML
    private TableColumn<Employee, String> login;
    @FXML
    private TableColumn<Employee, String> password;
    @FXML
    private ChoiceBox<String> field_for_search;
    @FXML
    private TextField text_for_search;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    private static ArrayList<Post> postList = new ArrayList<>();
    private static List<Employee> list = new ArrayList();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }

    public void addButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/addEmployee.fxml", "Добавить сотрудника");
    }

    public void saveButtonPushed() {
        List<Object> newlist = new ArrayList<>();
        Employee employee= new Employee();
        employee = table.getSelectionModel().getSelectedItem();
        employee.setType("editEmployee");
        //newlist.add(employee);
        try {
            newlist = (ServerConnection.send(employee));
            if(newlist.get(0).equals("true")){
                JOptionPane.showMessageDialog(null, "Данные изменены!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Данные не изменены");
            }
            list = Employee.loadEmployee();
            table.getItems().clear();
            table.getItems().addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchButtonPushed(ActionEvent event) throws IOException {
        String field = field_for_search.getValue();
        String name = text_for_search.getText();
        List<Employee> employeeList = new ArrayList<>();
        if(field.equals("")) {
            table.getItems().addAll(list);
        }
        else {
            List<Object> list = new ArrayList();
            Employee search = new Employee();
            search.setPost(new Post());
            search.setType(field);
            switch (field){
                case "Фамилия":  search.setSurname(name);
                case "Имя":  search.setName(name);
                case "Отчество": search.setPatronymic(name);
                case "Должность":  search.setNamePost(name);
                case "Отдел":  search.setDepartment(name);
                case "Лицевой счёт":  search.setBankAccount(name);
                case "E-mail":  search.setEmail(name);
            }
            Object obj = (Object) search;
            try {
                list = (ServerConnection.send(obj));
                if(!list.isEmpty()){
                    for (Object o:list){
                        Employee employee = (Employee) o;
                        employeeList.add(employee);
                    }
                    table.getItems().clear();
                    table.getItems().addAll(employeeList);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Ничего не найдено!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteButtonPushed() throws IOException {
        ObservableList<Employee> employeesSelected, allEmployees;
        allEmployees=table.getItems();
        employeesSelected=table.getSelectionModel().getSelectedItems();
        for(Employee employee:employeesSelected) {
            List<Object> newlist = new ArrayList<>();
            employee.setType("deleteEmployee");
            try {
                newlist = (ServerConnection.send(employee));
                if(newlist.get(0).equals("true")){
                    JOptionPane.showMessageDialog(null, "Удалено!");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Ошибка! не удалено");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        employeesSelected.forEach(allEmployees::remove);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        number.setCellValueFactory(new PropertyValueFactory<>("ID"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        post.setCellValueFactory(new PropertyValueFactory<>("postName"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        bankAccount.setCellValueFactory(new PropertyValueFactory<>("bankAccount"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        mobphone.setCellValueFactory(new PropertyValueFactory<>("mob_phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

        list = Employee.loadEmployee();

        List<Object> posts = new ArrayList<>();
        try {
            posts=Post.loadPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<String> listPost = FXCollections.observableArrayList();
        ObservableList<String> departmentList = FXCollections.observableArrayList();
        ObservableList<String> fieldList = FXCollections.observableArrayList("Фамилия", "Имя","Отчество","Должность","Отдел","Лицевой счёт","E-mail");
        postList = new ArrayList<>();
        for (Object object:posts) {
            Post post = (Post) object;
            listPost.add(post.getName());
            postList.add(post);
            //departmentList.add(post.getDepartment());
        }

        field_for_search.setItems(fieldList);


        table.getItems().addAll(list);
        table.setEditable(true);
        surname.setCellFactory(TextFieldTableCell.forTableColumn());
        surname.setOnEditCommit(e -> onEditSurname(e));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(e -> onEditName(e));
        patronymic.setCellFactory(TextFieldTableCell.forTableColumn());
        patronymic.setOnEditCommit(e -> onEditPatronymic(e));
        birthday.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        birthday.setOnEditCommit(e -> onEditBirthday(e));
        post.setCellFactory(ComboBoxTableCell.forTableColumn(listPost));
        post.setOnEditCommit(e -> onEditNamePost(e));
        bankAccount.setCellFactory(TextFieldTableCell.forTableColumn());
        bankAccount.setOnEditCommit(e -> onEditBankAccount(e));
        rate.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        rate.setOnEditCommit(e -> onEditRate(e));
        mobphone.setCellFactory(TextFieldTableCell.forTableColumn());
        mobphone.setOnEditCommit(e -> onEditMobPhone(e));
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(e -> onEditEmail(e));
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setOnEditCommit(e -> onEditAddress(e));
        login.setCellFactory(TextFieldTableCell.forTableColumn());
        login.setOnEditCommit(e -> onEditLogin(e));
        password.setCellFactory(TextFieldTableCell.forTableColumn());
        password.setOnEditCommit(e -> onEditPassword(e));


        deleteButton.setOnAction(e -> {
            try {
                deleteButtonPushed();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        saveButton.setOnAction(e -> {
            saveButtonPushed();
        });
    }

    public void onEditNamePost(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();
        employee.setNamePost(clientStringCellEditEvent.getNewValue());
        ObservableList<String > departmentList = FXCollections.observableArrayList();

        for (Post newpost : postList) {
            boolean state = true;
            if (table.getSelectionModel().getSelectedItem().getPostName().equals(newpost.getName()) /*&& department.getSelectionModel().getSelectedItem().equals(newpost.getepartment())*/) {
                departmentList.add(newpost.getDepartment());
                if(state) {
                    employee.getPost().setCode(newpost.getCode());
                    state=false;
                }
            }
        }
        department.setCellFactory(ComboBoxTableCell.forTableColumn(departmentList));
        department.setOnEditCommit(e -> onEditDepartment(e));
    }

    public void onEditDepartment(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();
        employee.setDepartment(clientStringCellEditEvent.getNewValue());

        for (Post newpost : postList) {
            if (table.getSelectionModel().getSelectedItem().getDepartment().equals(newpost.getDepartment()) && table.getSelectionModel().getSelectedItem().getPostName().equals(newpost.getName())) {
                employee.getPost().setCode(newpost.getCode());
            }
        }
    }

    public void onEditSurname(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();

        if(clientStringCellEditEvent.getNewValue().matches("[А-я]+"))
            employee.setSurname(clientStringCellEditEvent.getNewValue());
        else {
            table.getItems().clear();
            table.getItems().addAll(list);
            JOptionPane.showMessageDialog(null, "Должны быть только русские буквы!");
        }
    }

    public void onEditName(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();

        if(clientStringCellEditEvent.getNewValue().matches("[А-я]+"))
            employee.setName(clientStringCellEditEvent.getNewValue());
        else {
            table.getItems().clear();
            table.getItems().addAll(list);
            JOptionPane.showMessageDialog(null, "Должны быть только русские буквы!");
        }
    }
    public void onEditPatronymic(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();

        if(clientStringCellEditEvent.getNewValue().matches("[А-я]+"))
            employee.setPatronymic(clientStringCellEditEvent.getNewValue());
        else {
            table.getItems().clear();
            table.getItems().addAll(list);
            JOptionPane.showMessageDialog(null, "Должны быть только русские буквы!");
        }
    }
    public void onEditBankAccount(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();

        boolean state = true;
        for (Employee employee1: list) {
            if (clientStringCellEditEvent.getNewValue().matches(employee1.getBankAccount())){
                table.getItems().clear();
                table.getItems().addAll(list);
                JOptionPane.showMessageDialog(null, "Такой счёт уже занят!");
                state=false;
                break;
            }
        }
        if(state)
            employee.setBankAccount(clientStringCellEditEvent.getNewValue());
    }

    public void onEditBirthday(TableColumn.CellEditEvent<Employee, LocalDate> clientLocalDateCellEditEvent) {
        Employee employee = table.getSelectionModel().getSelectedItem();
        employee.setBirthday(clientLocalDateCellEditEvent.getNewValue());
    }

    public void onEditRate(TableColumn.CellEditEvent<Employee, Double> clientDoubleCellEditEvent){
        Employee employee=table.getSelectionModel().getSelectedItem();

        if(clientDoubleCellEditEvent.getNewValue()>0 && clientDoubleCellEditEvent.getNewValue()<=2) {
            employee.setRate(clientDoubleCellEditEvent.getNewValue());
        }
        else {
            table.getItems().clear();
            table.getItems().addAll(list);
            JOptionPane.showMessageDialog(null, "Значение должно быть в диапозоне от 0 до 2!");
        }

    }
    public void onEditEmail(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();
        employee.setEmail(clientStringCellEditEvent.getNewValue());
    }

    public void onEditMobPhone(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();
        employee.setMob_phone(clientStringCellEditEvent.getNewValue());
    }

    public void onEditAddress(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();
        employee.setAddress(clientStringCellEditEvent.getNewValue());
    }

    public void onEditLogin(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();

        boolean state = true;
        for (Employee employee1: list) {
            if (clientStringCellEditEvent.getNewValue().matches(employee1.getLogin())){
                table.getItems().clear();
                table.getItems().addAll(list);
                JOptionPane.showMessageDialog(null, "Такой логин уже занят!");
                state=false;
                break;
            }
        }
        if(state)
            employee.setLogin(clientStringCellEditEvent.getNewValue());
    }

    public void onEditPassword(TableColumn.CellEditEvent<Employee, String> clientStringCellEditEvent) {
        Employee employee=table.getSelectionModel().getSelectedItem();
        employee.setPassword(clientStringCellEditEvent.getNewValue());
    }
}
