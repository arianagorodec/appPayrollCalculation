package com.gorodeckaya.classes;

public class Charges {
    private static final long serialVersionUID = 29100008265466L;

    Integer code;
    String name;
    Double sum;
    String type;

    public Charges(String name, Double sum, String type) {
        this.name = name;
        this.sum = sum;
        this.type = type;
    }

    public Charges(String name, Double sum) {
        this.name = name;
        this.sum = sum;
    }

    public Charges(Integer code, String name, Double sum) {
        this.code = code;
        this.name = name;
        this.sum = sum;
    }

    public Charges(Integer code, String name, Double sum, String type) {
        this.code = code;
        this.name = name;
        this.sum = sum;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}


