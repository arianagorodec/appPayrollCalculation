package com.gorodeckaya.classes;

import com.gorodeckaya.ServerConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Total implements Serializable {

    private static final long serialVersionUID = 2913035010010357690L;

    int month;
    int year;
    Double salaryAccruals;
    Double otherCharges;
    Double allCharges;
    Double deducation;
    Double allDeducation;
    Double total;
    String type;

    public Total(int month, int year, Double salaryAccruals, Double otherCharges, Double allCharges, Double deducation, Double allDeducation, Double total) {
        this.month = month;
        this.year = year;
        this.salaryAccruals = salaryAccruals;
        this.otherCharges = otherCharges;
        this.allCharges = allCharges;
        this.deducation = deducation;
        this.allDeducation = allDeducation;
        this.total = total;
    }

    public Total(int month, int year, String type) {
        this.month = month;
        this.year = year;
        this.type = type;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSalaryAccruals() {
        return salaryAccruals;
    }

    public void setSalaryAccruals(Double salaryAccruals) {
        this.salaryAccruals = salaryAccruals;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getAllCharges() {
        return allCharges;
    }

    public void setAllCharges(Double allCharges) {
        this.allCharges = allCharges;
    }

    public Double getDeducation() {
        return deducation;
    }

    public void setDeducation(Double deducation) {
        this.deducation = deducation;
    }

    public Double getAllDeducation() {
        return allDeducation;
    }

    public void setAllDeducation(Double allDeducation) {
        this.allDeducation = allDeducation;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public static List<Object> loadTotal(int month, int year){
        List<Object> list = new ArrayList();
        Total total = new Total(month,year,"total");
        Object obj = (Object) total;
        try {
            list = (ServerConnection.send(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
