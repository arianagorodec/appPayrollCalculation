package com.gorodeckaya.fxml.employeeMenu.payList;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class Controller implements Initializable{
    Employee employee = new Employee();
    Map<String,Double> deducat=new HashMap<>();
    List<Charges> listCharges = new ArrayList<>();
    List<Deducation> listDeducations = new ArrayList<>();
    Employee currentEmployee = new Employee();

    @FXML
    private ComboBox<Integer> month;
    @FXML
    private ComboBox<Integer> year;
    @FXML
    private TextField name;
    @FXML
    private TextField salary;
    @FXML
    private TextField workedDays;
    @FXML
    private TextField rate;
    @FXML
    private TextField namePost;
    @FXML
    private TextField department;
    @FXML
    private TextField total;
    @FXML
    private TextField pSum;
    @FXML
    private TextField dSum;
    @FXML
    private TableView tableCharges;
    @FXML
    private TableColumn<Charges,String> cColumnName;
    @FXML
    private TableColumn<Charges, Double> cColumnSum;
    @FXML
    private TableView tableDeducations;
    @FXML
    private TableColumn<Deducation,String> dColumnName;
    @FXML
    private TableColumn<Deducation, Double> dColumnSum;

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/employeePage.fxml", "Вход");
    }

    public void createDocumentPushed() throws IOException {
        CreateExcel.createPayrollSheetForEmployee(Integer.valueOf(month.getValue()),Integer.valueOf(year.getValue()),employee.getID());
    }

    public void monthPushed(ActionEvent event) throws IOException {
        int monthI,yearI;

        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();;
        if(year.getValue()!=null) {
            monthI = Integer.valueOf(month.getValue());
            yearI = Integer.valueOf(year.getValue());
            //в таблицу вставить
            List<Object> list = Employee.loadEmployeeForPaySheet(monthI, yearI,employee.getID());
            Employee newEmployee = new Employee();
            for (Object object : list) {
                listEmployee.add((Employee) object);
                newEmployee = (Employee) object;
            }
            employee=newEmployee;
            salary.setText(newEmployee.getSalary().toString());
            workedDays.setText(newEmployee.getWasWorkedDays().toString());
            namePost.setText(newEmployee.getPost().getName());
            department.setText(newEmployee.getPost().getDepartment());
            name.setText(newEmployee.getSurname()+" "+newEmployee.getName()+" "+newEmployee.getPatronymic());
            double sumCharges = 0.0;
            double sumDeduc = 0.0;
            double totalA=0.0;
            ObservableList<Charges> listChar =  FXCollections.observableArrayList();;
            ObservableList<Deducation> listDeduc = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : employee.getPayrollList().get(0).getCharges().entrySet()) {
                sumCharges += entry.getValue();
                totalA += entry.getValue();
                Charges charges=new Charges(entry.getKey(),entry.getValue());
                listChar.add(charges);
            }
            for (Map.Entry<String, Double> entry : employee.getPayrollList().get(0).getDeducations().entrySet()) {
                sumDeduc += entry.getValue();
                totalA -= entry.getValue();
                Deducation deducation=new Deducation(entry.getKey(),entry.getValue());
                listDeduc.add(deducation);
            }
            total.setText(String.valueOf(totalA));
            pSum.setText(String.valueOf(sumCharges));
            dSum.setText(String.valueOf(sumDeduc));


            tableCharges.setItems(listChar);
            tableDeducations.setItems(listDeduc);
        }

    }
    public void yearPushed(ActionEvent event) throws IOException {
        int monthI,yearI;

        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();;
        if(month.getValue()!=null) {
            monthI = Integer.valueOf(month.getValue());
            yearI = Integer.valueOf(year.getValue());
            //в таблицу вставить
            List<Object> list = Employee.loadEmployeeForPaySheet(monthI, yearI,employee.getID());
            Employee newEmployee = new Employee();
            for (Object object : list) {
                listEmployee.add((Employee) object);
                newEmployee = (Employee) object;
            }
            employee=newEmployee;
            salary.setText(newEmployee.getSalary().toString());
            workedDays.setText(newEmployee.getWasWorkedDays().toString());
            rate.setText(newEmployee.getRate().toString());
            namePost.setText(newEmployee.getPost().getName());
            department.setText(newEmployee.getPost().getDepartment());
            name.setText(newEmployee.getSurname()+" "+newEmployee.getName()+" "+newEmployee.getPatronymic());
            double sumCharges = 0.0;
            double sumDeduc = 0.0;
            double totalA=0.0;
            ObservableList<Charges> listChar =  FXCollections.observableArrayList();;
            ObservableList<Deducation> listDeduc = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : employee.getPayrollList().get(0).getCharges().entrySet()) {
                sumCharges += entry.getValue();
                totalA += entry.getValue();
                Charges charges=new Charges(entry.getKey(),entry.getValue());
                listChar.add(charges);
            }
            for (Map.Entry<String, Double> entry : employee.getPayrollList().get(0).getDeducations().entrySet()) {
                sumDeduc += entry.getValue();
                totalA -= entry.getValue();
                Deducation deducation=new Deducation(entry.getKey(),entry.getValue());
                listDeduc.add(deducation);
            }
            total.setText(String.valueOf(totalA));
            pSum.setText(String.valueOf(sumCharges));
            dSum.setText(String.valueOf(sumDeduc));



            tableCharges.setItems(listChar);
            tableDeducations.setItems(listDeduc);
        }
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
        dColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        dColumnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        cColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cColumnSum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        ObservableList<Integer> listMonth = FXCollections.observableArrayList();
        for (int i = 1; i< 13; i++){
            listMonth.add(i);
        }
        ObservableList<Integer> listYear = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        for (int i = 2015; i<= currentYear; i++){
            listYear.add(i);
        }
        month.setItems(listMonth);
        year.setItems(listYear);
    }

}