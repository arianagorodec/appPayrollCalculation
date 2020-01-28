package com.gorodeckaya.fxml.employeeMenu;

import com.gorodeckaya.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/authorization.fxml", "Вход");
    }

    public void payListButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/payList.fxml", "Расчёт Лист");
    }
    public void editePLButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/editPL.fxml", "Смена логина и пароля");
    }
    public void editeEmpButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/editEmployee.fxml", "Изменения информации о себе");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}