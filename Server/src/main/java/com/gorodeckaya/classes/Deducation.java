package com.gorodeckaya.classes;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Deducation implements Serializable {
    private static final long serialVersionUID = 29138858265466L;

    Integer code;
    String name;
    Double percent;
    String type;

    public Deducation(String name, Double percent, String type) {
        this.name = name;
        this.percent = percent;
        this.type = type;
    }

    public Deducation(Integer code, String name, Double percent, String type) {
        this.code = code;
        this.name = name;
        this.percent = percent;
        this.type = type;
    }

    public Deducation(Integer code, String name, Double percent) {
        this.code = code;
        this.name = name;
        this.percent = percent;
    }

    public Deducation(String name, Double percent) {
        this.name = name;
        this.percent = percent;
    }

    public Deducation(String type) {
        this.type = type;
    }

    public Deducation() {
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

    public List<Object> getAllDeducation(Connection con) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `payrollsystem`.`reference_book_of_payroll_and_deductions` where `type`='d';");
            while (rs.next()) {
                    list.add(new Deducation(rs.getInt("code"),rs.getString("name"), rs.getDouble("percent")));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> editDeducation(Connection con, Deducation deducation) {
        List<Object> list = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement("UPDATE `payrollsystem`.`reference_book_of_payroll_and_deductions` SET `percent` ="+  deducation.getPercent() +"WHERE name ='"+ deducation.getName()+"';");
            st.executeUpdate();
            st.close();
            list.add("true");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Object> deleteDeducation(Connection con, Deducation deducation) {
        List<Object> list = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM `payrollsystem`.`reference_book_of_payroll_and_deductions` WHERE name ='"+ deducation.getName()+"';");
            st.executeUpdate();
            st.close();
            list.add("true");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
