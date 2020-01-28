package com.gorodeckaya.fxml.accMenu.statementWage;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.classes.CreateExcel;
import com.gorodeckaya.classes.Employee;
import com.gorodeckaya.classes.Total;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
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
    private TableColumn<Employee, Double> rate;
    @FXML
    private TableColumn<Employee, Integer> wasWorkedDays;
    @FXML
    private TableColumn<Employee, Double> salary;
    @FXML
    private TableColumn<Employee, Double> salary_accruals;
    @FXML
    private TableColumn<Employee, Double> other_charges;
    @FXML
    private TableColumn<Employee, Double> all_charges;
    @FXML
    private TableColumn<Employee, Double> deducation;
    @FXML
    private TableColumn<Employee, Double> all_deducation;
    @FXML
    private TableColumn<Employee, Double> total;
    @FXML
    private ComboBox<Integer> month;
    @FXML
    private ComboBox<Integer> year;


    @FXML
    private TextField textFieldTotal;
    @FXML
    private TextField allSalaryAcc;
    @FXML
    private TextField taxDeducation;
    @FXML
    private TextField addCharges;
    @FXML
    private TextField textAllCharges;
    @FXML
    private TextField textAllDeducations;


    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }

    public void createReport() throws IOException {
        if(year.getValue()!=null && month.getValue()!=null) {
            CreateExcel.createPayrollSheet(Integer.valueOf(month.getValue()), Integer.valueOf(year.getValue()));
        }
        else
            JOptionPane.showMessageDialog(null,"Выберете дату");
    }

    public void monthPushed(ActionEvent event) throws IOException {
        int monthI,yearI;
        List<Object> listTotal = new ArrayList<>();
        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
        if(year.getValue()!=null) {
            monthI=Integer.valueOf(month.getValue());
            yearI=Integer.valueOf(year.getValue());
            listTotal = Total.loadTotal(monthI, yearI);
            for (Object object:listTotal) {
                Total total1 = (Total) object ;
                allSalaryAcc.setText(new DecimalFormat("#0.00").format(total1.getSalaryAccruals()).replace(",","."));
                addCharges.setText(new DecimalFormat("#0.00").format(total1.getOtherCharges()).replace(",","."));
                textAllCharges.setText(new DecimalFormat("#0.00").format(total1.getAllCharges()).replace(",","."));
                taxDeducation.setText(new DecimalFormat("#0.00").format(total1.getDeducation()).replace(",","."));
                textAllDeducations.setText(new DecimalFormat("#0.00").format(total1.getAllDeducation()).replace(",","."));
                textFieldTotal.setText(new DecimalFormat("#0.00").format(total1.getTotal()).replace(",","."));
            }
            //в таблицу вставить
            List<Object> list =Employee.loadEmployeeForTotal(monthI, yearI);

            for (Object object:list) {
                listEmployee.add((Employee) object);
                //Employee employee = (Employee) object;
            }
            table.setItems(listEmployee);
        }

    }
    public void yearPushed(ActionEvent event) throws IOException {
        int monthI,yearI;
        List<Object> listTotal = new ArrayList<>();
        ObservableList<Employee> listEmployee = FXCollections.observableArrayList();
        if(month.getValue()!=null) {
            monthI=Integer.valueOf(month.getValue());
            yearI=Integer.valueOf(year.getValue());
            listTotal = Total.loadTotal(monthI, yearI);
            for (Object object:listTotal) {
                Total total1 = (Total) object ;
                allSalaryAcc.setText(new DecimalFormat("#0.00").format(total1.getSalaryAccruals()).replace(",","."));
                addCharges.setText(new DecimalFormat("#0.00").format(total1.getOtherCharges()).replace(",","."));
                textAllCharges.setText(new DecimalFormat("#0.00").format(total1.getAllCharges()).replace(",","."));
                taxDeducation.setText(new DecimalFormat("#0.00").format(total1.getDeducation()).replace(",","."));
                textAllDeducations.setText(new DecimalFormat("#0.00").format(total1.getAllDeducation()).replace(",","."));
                textFieldTotal.setText(new DecimalFormat("#0.00").format(total1.getTotal()).replace(",","."));
            }
            //в таблицу вставить
            List<Object> list =Employee.loadEmployeeForTotal(monthI, yearI);

            for (Object object:list) {
                listEmployee.add((Employee) object);
                //Employee employee = (Employee) object;
            }
            table.setItems(listEmployee);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        number.setCellValueFactory(new PropertyValueFactory<>("ID"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        post.setCellValueFactory(new PropertyValueFactory<>("postName"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        wasWorkedDays.setCellValueFactory(new PropertyValueFactory<>("wasWorkedDays"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salary_accruals.setCellValueFactory(new PropertyValueFactory<>("salaryAccruals"));
        other_charges.setCellValueFactory(new PropertyValueFactory<>("otherCharges"));
        all_charges.setCellValueFactory(new PropertyValueFactory<>("allCharges"));
        deducation.setCellValueFactory(new PropertyValueFactory<>("deducation"));
        all_deducation.setCellValueFactory(new PropertyValueFactory<>("allDeducation"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        List<Employee> list = new ArrayList();

        ObservableList<Integer> listMonth = FXCollections.observableArrayList();
        for (int i = 1; i< 13; i++){
            listMonth.add(i);
        }
        ObservableList<Integer> listYear = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        //int currentYear = 2021;
        for (int i = 2015; i<= currentYear; i++){
            listYear.add(i);
        }
        month.setItems(listMonth);
        year.setItems(listYear);
        //list = Employee.loadEmployee();
        //table.getItems().addAll(list);
    }

}
