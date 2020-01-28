package com.gorodeckaya.fxml.accMenu.grafic;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.classes.Employee;
import com.gorodeckaya.classes.Payroll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private Label title;
    @FXML
    private PieChart pieChart;

    Model model = new Model();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }


    public void payrollButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/graficPayroll.fxml", "График средней заработной платы за год");
    }
    public void amountEmployeeButtonPushed(){
        title.setText("Количество работников");
        pieChart.setVisible(true);
        pieChart.setData(model.drawGraf()); //MVC
    }
    public void workedDaysButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/graficDays.fxml", "График отработанных дней");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pieChart.setVisible(false);
        title.setText("Выберети, какой график хотите увидеть");
    }
}
