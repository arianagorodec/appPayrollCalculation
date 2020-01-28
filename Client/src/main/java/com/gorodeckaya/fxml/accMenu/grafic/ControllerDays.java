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
import java.util.*;

public class ControllerDays  implements Initializable {
    @FXML
    private Label title;
    @FXML
    private ComboBox<Integer> year;
    @FXML
    private CategoryAxis x ;
    @FXML
    private BarChart<String, Double> dayLineChart;
    @FXML
    private NumberAxis y;

    ObservableList<String> m = FXCollections.observableArrayList();
    ObservableList<Double> num = FXCollections.observableArrayList();
    ObservableList<Double> num1 = FXCollections.observableArrayList();

    Model model = new Model();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }


    public void yearButtonPushed() {

        Map<Integer, Double> workDays = new HashMap<>();
        Map<Integer, Double> workedDays = new HashMap<>();

        XYChart.Series<String, Double> wDaysLine = new XYChart.Series<>();
        XYChart.Series<String, Double> wwDaysLine = new XYChart.Series<>();

        wDaysLine.setName("Рабочие дик");
        wwDaysLine.setName("Было отработано");

        workDays = model.drawWDays(year.getValue());
        workedDays=model.drawWWDays(year.getValue());

        dayLineChart.getData().clear();
        for (int i =1;i<13;i++) {
                num.add(workDays.get(i));
                num1.add(workedDays.get(i));
        }

        for (Integer i =0;i<12;i++) {
            wDaysLine.getData().add(new XYChart.Data<String, Double>(m.get(i),num.get(i)));
            wwDaysLine.getData().add(new XYChart.Data<String, Double>(m.get(i), num1.get(i)));
        }
        dayLineChart.getData().clear();
            dayLineChart.getData().addAll(wDaysLine,wwDaysLine);

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
        Map<Integer, String> monthS = new HashMap<>();

        monthS=model.monthPush();

        for (int i =1;i<13;i++) {
            m.add(monthS.get(i));
        }
        x.setCategories(m);
    }
}
