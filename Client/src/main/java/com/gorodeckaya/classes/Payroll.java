package com.gorodeckaya.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Payroll implements Serializable {
    private static final long serialVersionUID = 2912885823449157690L;

    int month;
    int year;
    int personalNumber;
    Post post;
    Double sum;
    int wasWorkedDays;
    int workingDays;
    Total total;
    String type;
    Map<String,Double> charges=new HashMap<>();
    Map<String,Double> deducations=new HashMap<>();

    public Payroll(int month, int year, int personalNumber, Post post, Double sum, int wasWorkedDays, int workingDays, String type, Map<String, Double> charges, Map<String, Double> deducations) {
        this.month = month;
        this.year = year;
        this.personalNumber = personalNumber;
        this.post = post;
        this.sum = sum;
        this.wasWorkedDays = wasWorkedDays;
        this.workingDays=workingDays;
        this.type = type;
        this.charges = charges;
        this.deducations = deducations;
    }

    public Payroll(int month, int year, int personalNumber, Post post, Double sum, int wasWorkedDays, int workingDays, Total total, Map<String, Double> charges, Map<String, Double> deducations) {
        this.month = month;
        this.year = year;
        this.personalNumber = personalNumber;
        this.post = post;
        this.sum = sum;
        this.wasWorkedDays = wasWorkedDays;
        this.workingDays = workingDays;
        this.total = total;
        this.charges = charges;
        this.deducations = deducations;
    }

    public Payroll(int month, int year, int personalNumber, Post post, Double sum, int wasWorkedDays, int workingDays, String type) {
        this.month = month;
        this.year = year;
        this.personalNumber = personalNumber;
        this.post = post;
        this.sum = sum;
        this.wasWorkedDays = wasWorkedDays;
        this.workingDays = workingDays;
        this.type = type;

    }

    public Payroll(int month, int year) {
        this.month = month;
        this.year = year;

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

    public int getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public int getWasWorkedDays() {
        return wasWorkedDays;
    }

    public void setWasWorkedDays(int wasWorkedDays) {
        this.wasWorkedDays = wasWorkedDays;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public Map<String, Double> getCharges() {
        return charges;
    }

    public void setCharges(Map<String, Double> charges) {
        this.charges = charges;
    }

    public Map<String, Double> getDeducations() {
        return deducations;
    }

    public void setDeducations(Map<String, Double> deducations) {
        this.deducations = deducations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public void addCharges(String name, Double sum){
        charges.put(name,sum);
    }
    public void addDeducations(String name, Double sum){
        deducations.put(name,sum);
    }
}
