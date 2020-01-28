package com.gorodeckaya.classes;

import com.gorodeckaya.ServerConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Deducation implements Serializable {
    private static final long serialVersionUID = 29138858265466L;

    Integer code;
    String name;
    Double percent;
    Double sum;
    String type;

    public Deducation(String name, Double percent, String type) {
        this.name = name;
        this.percent = percent;
        this.type = type;
    }

    public Deducation(String name, Double sum) {
        this.name = name;
        this.sum = sum;
    }

    public Deducation(Integer code, String name, Double percent, Double sum, String type) {
        this.code = code;
        this.name = name;
        this.percent = percent;
        this.sum = sum;
        this.type = type;
    }

    public Deducation(Integer code, String name, Double percent) {
        this.code = code;
        this.name = name;
        this.percent = percent;
    }

    public Deducation(Integer code, String name, Double percent, Double sum) {
        this.code = code;
        this.name = name;
        this.percent = percent;
        this.sum = sum;
    }

    public Deducation(String type) {
        this.type = type;
    }

    public Deducation() {

    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static List<Deducation> loadDeducation() {
        List<Object> list = new ArrayList();
        Deducation deducation = new Deducation("allDeducation");
        Object obj = (Object) deducation;
        List<Deducation> deducations = new ArrayList<>();
        try {
            list = (ServerConnection.send(obj));
            for (Object object : list) {
                Deducation deducation1 = (Deducation) object;
                deducations.add(deducation1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deducations;
    }
}
