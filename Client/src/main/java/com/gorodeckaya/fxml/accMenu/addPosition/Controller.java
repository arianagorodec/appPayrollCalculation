package com.gorodeckaya.fxml.accMenu.addPosition;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.ServerConnection;
import com.gorodeckaya.classes.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private TextField salary;
    @FXML
    private TextField post;
    @FXML
    private ComboBox<String> department;

    List<Object> postList= new ArrayList();

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Post post = new Post("allPost");
        Object obj = (Object) post;
        try {
            postList = (ServerConnection.send(obj));
            ObservableList<String> departments = FXCollections.observableArrayList();
            for (Object object : postList) {
                boolean state = true;
                Post post1 = (Post) object;
                for (String s: departments){
                    if(s.equals(post1.getDepartment()))
                        state=false;
                }
                if(state)
                    departments.add(post1.getDepartment());
            }
            department.setItems(departments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method for check statement
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public int checkStateText() {
        int stateForText = 0; //3

        if (!isNullOrEmpty(salary.getText())){
            stateForText++;
        }
        if (!isNullOrEmpty(post.getText())){
            stateForText++;
        }
        if (department.getSelectionModel().isEmpty()){
            if (!department.getEditor().getText().isEmpty()) {
                stateForText++;
            }
        }
        if (!department.getSelectionModel().isEmpty()){
                stateForText++;
        }

        return stateForText;
    }


    public void addButtonPushed(ActionEvent event) throws IOException {
        List<Object> list = new ArrayList();
        PreparedStatement ps = null;
        if (checkStateText() == 3) {
            boolean state = true;
            for (Object object : postList) {
                Post post1 = (Post) object;
                if(post1.getDepartment().equals(department.getValue())&&post1.getName().equals(post.getText())){
                    state=false;
                }
            }
            if (state) {
                Post newpost = new Post();
                if(department.getValue().isEmpty()) {
                    newpost = new Post(post.getText(), department.getEditor().getText(), Double.parseDouble(salary.getText()), "addPost");
                }
                else
                    newpost = new Post(post.getText(), department.getValue(), Double.parseDouble(salary.getText()), "addPost");
                Object obj = (Object) newpost;
                try {
                    list = (ServerConnection.send(obj));
                    if (Boolean.parseBoolean(list.get(0).toString())) {
                        JOptionPane.showMessageDialog(null, "Должность добавлена");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Такая должность уже существует!");
        }
        else
            JOptionPane.showMessageDialog(null, "Введены не все поля!");

    }

}