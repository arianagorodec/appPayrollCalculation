package com.gorodeckaya.fxml.authorization;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.ServerConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private Button admin;
    @FXML
    private Button user;
    @FXML
    private Button registration;
    @FXML
    private TextField port;

    public static ServerConnection connection=null;
    //192.168.56.1

    public void accEentryButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/login.fxml", "Вход");
    }

    public void empEentryButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/loginE.fxml", "Вход");
    }

    public void registrationButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/registration.fxml", "Регистрация");
    }

    public void setPort(){
        connection = new ServerConnection();
        connection.startConnection(Integer.valueOf(port.getText()));
        admin.setDisable(false);
        user.setDisable(false);
        registration.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (connection!=null)
                connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        admin.setDisable(true);
        user.setDisable(true);
        registration.setDisable(true);

    }
}
