package com.gorodeckaya.fxml.accMenu.grafic;

import com.gorodeckaya.SceneChanger;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControlersPayroll implements Initializable {
    @FXML
    private Label title;
    @FXML
    private ComboBox<Integer> year;
    @FXML
    private CategoryAxis xP;
    @FXML
    private NumberAxis yP;
    @FXML
    private LineChart<String ,Number> payrollLineChart;

    ObservableList<String> m = FXCollections.observableArrayList();
    Model model = new Model();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }

    public void yearButtonPushed() {
        Map<Integer, Number> payroll = new HashMap<>();
        XYChart.Series<String,Number> payrollLine = new XYChart.Series<>();
        payroll = model.drawPayroll(year.getValue());
        for (int i =0;i<12;i++) {
            payrollLine.getData().add(new XYChart.Data<>(m.get(i), payroll.get(i+1)));
        }
       // if (title.getText().equals("Средняя з/п за год")) {
           payrollLine.setName("Средняя з/п");
        payrollLineChart.getData().clear();
            payrollLineChart.getData().add(payrollLine);
       // }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> listYear = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        for (int i = 2015; i<= currentYear; i++){
            listYear.add(i);
        }
        year.setItems(listYear);
        title.setText("Выберети год");
        Map<Integer, String> monthS = new HashMap<>();

        monthS=model.monthPush();

        for (int i =1;i<13;i++) {
            m.add(monthS.get(i));
        }
        //xP.setCategories(m);
    }
}
