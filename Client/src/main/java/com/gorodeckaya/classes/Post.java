package com.gorodeckaya.classes;

import com.gorodeckaya.ServerConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {

    private static final long serialVersionUID = 2912885090749157690L;

    int code;
    String name;
    String department;
    Double salary;
    String type;

    public Post(int code, String name, String department, Double salary, String type) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.type = type;
    }

    public Post( String name, String department, Double salary) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.type = type;
    }
    public Post(String name, String department, Double salary, String type) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.type = type;
    }

    public Post(String type) {
        this.type = type;
    }


    public Post() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<Object> loadPost() throws Exception {
        List<Object> list = new ArrayList();
        Post post = new Post("allPost");
        Object obj = (Object) post;
        try {
            list = (ServerConnection.send(obj));
            ObservableList<String> departments = FXCollections.observableArrayList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
