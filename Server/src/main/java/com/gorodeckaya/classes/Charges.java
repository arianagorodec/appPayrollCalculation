package com.gorodeckaya.classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public Charges(Integer code, String name, Double sum, String type) {
        this.code = code;
        this.name = name;
        this.sum = sum;
        this.type = type;
    }

    public Charges(Integer code, String name) {
        this.name = name;
        this.code = code;
    }

    public Charges(Integer code, String name, Double sum) {
        this.code = code;
        this.name = name;
        this.sum = sum;
    }

    public Charges(String name, Double sum) {
        this.name = name;
        this.sum = sum;
    }

    public Charges() {

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

    public List<Object> getAllNameCharges(Connection con) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `payrollsystem`.`reference_book_of_payroll_and_deductions` WHERE type = 'c';");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Object> getAllCharges(Connection con) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `payrollsystem`.`reference_book_of_payroll_and_deductions` WHERE type = 'c';");
            while (rs.next()) {
                list.add(new Charges(rs.getInt("code"),rs.getString("name")));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}


