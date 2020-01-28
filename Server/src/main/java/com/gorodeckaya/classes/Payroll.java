package com.gorodeckaya.classes;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

public class Payroll implements Serializable {
    private static final long serialVersionUID = 2912885823449157690L;

    int month;
    int year;
    int personalNumber;
    Post post;
    Double sum;
    int wasWorkedDays;
    int workingDays;
    Total total = new Total();
    String type;
    Map<String,Double> charges = new HashMap<>();
    Map<String,Double> deducations = new HashMap<>();

    public Payroll(int month, int year, int personalNumber, Post post, Double sum, int wasWorkedDays, int workingDays, String type, Map<String, Double> charges, Map<String, Double> deducations) {
        this.month = month;
        this.year = year;
        this.personalNumber = personalNumber;
        this.post = post;
        this.sum = sum;
        this.wasWorkedDays = wasWorkedDays;
        this.workingDays = workingDays;
        this.type = type;
        this.charges = charges;
        this.deducations = deducations;
    }

    public Payroll() {
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

    public static Map<Integer,String> getNameChargesAndDeducations(Connection con) {
        Map<Integer,String> map = new HashMap<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rsCharges = stmt.executeQuery("SELECT * FROM payrollsystem.reference_book_of_payroll_and_deductions ;");
            while (rsCharges.next()) {
                map.put(rsCharges.getInt("code"), rsCharges.getString("name"));
            }
            rsCharges.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Double CalculationPayroll(Payroll payroll) throws SQLException {
        String host = "jdbc:mysql://localhost:3306/payrollsystem?allowPublicKeyRetrieval=true&useSSL=false" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        Connection con = DriverManager.getConnection(host, "root", "28092003");
        Charges charges = new Charges();
        Deducation deducation = new Deducation();
        payroll.setSum(0.0);
        List<Object> listCharges = new ArrayList<>();
        listCharges = charges.getAllCharges(con);

        Iterator it = payroll.getCharges().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry ch = (Map.Entry) it.next();
            for (Object object : listCharges) {
                Charges charges1 = (Charges) object;
                if (charges1.getName().equals(ch.getKey())) {
                    payroll.setSum(payroll.getSum() + ((Double) ch.getValue()).doubleValue());
                }
            }
        }
        List<Object> listDeducations = new ArrayList<>();
        listDeducations = deducation.getAllDeducation(con);

        Iterator it1 = payroll.getDeducations().entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry de = (Map.Entry) it1.next();
            for (Object object : listDeducations) {
                Deducation deducation1 = (Deducation) object;
                if (deducation1.getName().equals(de.getKey())) {
                    payroll.setSum(payroll.getSum() - ((Double) de.getValue()).doubleValue());
                }
            }
        }
        con.close();
        return payroll.getSum();
    }

    public boolean checkRepeatPayroll(Connection con, Payroll payroll){
        boolean state = false;

        class Date{
            int month;
            int year;
            int personal_number;

            public Date(int month, int year, int personal_number) {
                this.month = month;
                this.year = year;
                this.personal_number = personal_number;
            }

            public Date() {
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

            public int getPersonal_number() {
                return personal_number;
            }

            public void setPersonal_number(int personal_number) {
                this.personal_number = personal_number;
            }
        }

      List<Date> charges = new ArrayList<>();
        List<Date>  deduc =new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rsDeducatuion = stmt.executeQuery("SELECT * FROM payrollsystem.deducation_sheet ;");
            while (rsDeducatuion.next()) {
                deduc.add(new Date(rsDeducatuion.getInt("month"), rsDeducatuion.getInt("year"), rsDeducatuion.getInt("personal_number")));
            }
            rsDeducatuion.close();
            ResultSet rsCharges = stmt.executeQuery("SELECT * FROM payrollsystem.payroll_sheet ;");
            while (rsCharges.next()) {
                charges.add(new Date(rsCharges.getInt("month"), rsCharges.getInt("year"), rsCharges.getInt("personal_number")));
            }
            rsCharges.close();

            for(Date date:deduc){
                if((date.getMonth()==payroll.getMonth())&&(date.getYear()==payroll.getYear())&&(date.getPersonal_number()==payroll.getPersonalNumber())){
                    state =true;
                    break;
                }
            }

            for(Date date:charges){
                if((date.getMonth()==payroll.getMonth())&&(date.getYear()==payroll.getYear())&&(date.getPersonal_number()==payroll.getPersonalNumber())){
                    state =true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }

    public boolean deletePayroll(Connection con, Payroll payroll){
        boolean state = false;
        Statement stmt = null;
        try {
            PreparedStatement statement = con.prepareStatement("DELETE FROM `payrollsystem`.`deducation_sheet` WHERE `personal_number`="+payroll.getPersonalNumber()+" AND `month` = "+payroll.getMonth()+" AND `year` = "+payroll.getYear()+";");
            statement.executeUpdate();
            statement.close();
            statement = con.prepareStatement("DELETE FROM `payrollsystem`.`payroll_sheet` WHERE `personal_number`="+payroll.getPersonalNumber()+" AND `month` = "+payroll.getMonth()+" AND `year` = "+payroll.getYear()+";");
            statement.executeUpdate();
            statement.close();
            statement = con.prepareStatement("DELETE FROM `payrollsystem`.`working_days` WHERE `personal_number`="+payroll.getPersonalNumber()+" AND `month` = "+payroll.getMonth()+" AND `year` = "+payroll.getYear()+";");
            statement.executeUpdate();
            statement.close();
            state=true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return state;
    }

    public List<Object> editPayroll(Connection con, Payroll payroll) throws SQLException {
        List<Object> list = new ArrayList<>();
        if(deletePayroll(con, payroll)){
            list=addPayroll(con, payroll);
        }
        else {
            payroll.setType("false");
            list.add(payroll);
        }
        return list;
    }

    public List<Object> addPayroll(Connection con, Payroll payroll) throws SQLException {
        List<Object> list = new ArrayList<>();
        Charges charges = new Charges();
        Deducation deducation = new Deducation();
        if(!checkRepeatPayroll(con, payroll)) {
            payroll.setSum(Payroll.CalculationPayroll(payroll));
            try {

                PreparedStatement statement = con.prepareStatement("INSERT INTO `payrollsystem`.`working_days` (`personal_number`, `month`, `year`, `wasWorkedDays`, `workingDays`) VALUES (" + payroll.getPersonalNumber() + ", " + payroll.getMonth() + ", " + payroll.getYear() + ", " + payroll.getWasWorkedDays() + ", " + payroll.getWorkingDays() + ");");
                statement.executeUpdate();
                statement.close();
                //Statement stmt = con.createStatement();
                //ResultSet rs = stmt.executeQuery("SELECT * FROM payrollsystem.organization_structure WHERE post = '" + employee.getPost().getName() + "' AND department = '"+employee.getPost().getDepartment()+"' ;");
                List<Object> listCharges = new ArrayList<>();
                listCharges = charges.getAllCharges(con);
                Iterator it = payroll.getCharges().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry ch = (Map.Entry) it.next();
                    for (Object object : listCharges) {
                        Charges charges1 = (Charges) object;
                        if (charges1.getName().equals(ch.getKey())) {
                            PreparedStatement statement1 = con.prepareStatement("INSERT INTO `payrollsystem`.`payroll_sheet` (`month`, `year`, `code_post`, `personal_number`, `code_charges`, `sum_charges`) VALUES (" + payroll.getMonth() + ", " + payroll.getYear() + ", " + payroll.getPost().getCode() + ", " + payroll.getPersonalNumber() + ", " + charges1.getCode() + ", " + (Double) ch.getValue() + ");");
                            statement1.executeUpdate();
                            statement1.close();
                        }
                    }
                }
                List<Object> listDeducations = new ArrayList<>();
                listDeducations = deducation.getAllDeducation(con);

                Iterator it1 = payroll.getDeducations().entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry de = (Map.Entry) it1.next();
                    for (Object object : listDeducations) {
                        Deducation deducation1 = (Deducation) object;
                        if (deducation1.getName().equals(de.getKey())) {
                            PreparedStatement st = con.prepareStatement("INSERT INTO `payrollsystem`.`deducation_sheet` (`month`, `year`, `code_post`, `personal_number`, `code_deducation`, `sum_deducation`) VALUES (" + payroll.getMonth() + ", " + payroll.getYear() + ", " + payroll.getPost().getCode() + ", " + payroll.getPersonalNumber() + ", " + deducation1.getCode() + ", " + (Double) de.getValue() + ");");
                            st.executeUpdate();
                            st.close();
                        }
                    }
                }
                list.add(payroll);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            payroll.setType("false");
            list.add(payroll);
        }
        return list;
    }

    public List<Payroll> getAllPayroll(Employee employee, int month, int year) throws SQLException {
        String host = "jdbc:mysql://localhost:3306/payrollsystem?allowPublicKeyRetrieval=true&useSSL=false" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        List<Payroll> list = new ArrayList<>();
        Charges charge = new Charges();
        Deducation deducation = new Deducation();
        Connection con = DriverManager.getConnection(host, "root", "28092003");
        Map<String,Double> chargesCurrent = new HashMap<>();
        Map<String,Double> deducationsCurrent = new HashMap<>();
        //payroll.setSum(Payroll.CalculationPayroll(payroll));
        try {
            Statement stmt = con.createStatement();
            ResultSet rscharges = stmt.executeQuery("SELECT * FROM payrollsystem.payroll_sheet WHERE personal_number = '" + employee.getId() + "' AND month = '" + month + "' AND year = '" + year + "';");
            while (rscharges.next()) {
                chargesCurrent.put(rscharges.getString("code_charges"), rscharges.getDouble("sum_charges"));
            }
            //stmt.close();
            stmt = con.createStatement();
            ResultSet rsdeducations = stmt.executeQuery("SELECT * FROM payrollsystem.deducation_sheet WHERE personal_number = '" + employee.getId() + "'AND month='" + month + "' AND year='" + year + "';");
            while (rsdeducations.next()) {
                deducationsCurrent.put(rsdeducations.getString("code_deducation"), rsdeducations.getDouble("sum_deducation"));
            }
            stmt.close();
            Payroll payroll = new Payroll();
            payroll.setMonth(month);
            payroll.setYear(year);
            payroll.setPersonalNumber(employee.getId());
            payroll.setCharges(chargesCurrent);
            payroll.setDeducations(deducationsCurrent);


            list.add(payroll);
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
