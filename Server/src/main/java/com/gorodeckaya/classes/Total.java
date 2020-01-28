package com.gorodeckaya.classes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Object> CalculationTotal(Connection con, Total t) throws SQLException {
        List<Object> list = new ArrayList<>();
        Employee employee = new Employee();
        List<Object> employeeList = employee.getAllEmployee(con);
        Payroll payroll = new Payroll();

        Double salaryAccrualsD = 0.0;
        Double otherChargesD = 0.0;
        Double allChargesD = 0.0;
        Double deducationD = 0.0;
        Double allDeducationD = 0.0;
        Double totalD = 0.0;

        for (Object object : employeeList) {
            Employee employee1 = (Employee) object;
            List<Payroll> payrollList = payroll.getAllPayroll(employee1, t.getMonth(), t.getYear());
            employee1.setPayrollList((ArrayList<Payroll>) payrollList);
        }
        for (Object object : employeeList) {
            Employee employee1 = (Employee) object;

            for (Payroll payroll1 : employee1.getPayrollList()) {

                for (Map.Entry<String, Double> entry : payroll1.getCharges().entrySet()) {
                    if (entry.getKey().equals("3")) {
                        salaryAccrualsD += entry.getValue();

                    } else
                        otherChargesD += entry.getValue();

                    allChargesD += entry.getValue();
                    totalD += entry.getValue();
                }
                for (Map.Entry<String, Double> entry : payroll1.getDeducations().entrySet()) {
                    deducationD += entry.getValue();
                    allDeducationD = deducationD;
                    totalD -= entry.getValue();
                }
            }
        }
        t.setSalaryAccruals(salaryAccrualsD);
        t.setOtherCharges(otherChargesD);
        t.setAllCharges(allChargesD);
        t.setAllDeducation(allDeducationD);
        t.setDeducation(deducationD);
        t.setTotal(totalD);

        list.add(t);
        return list;
    }


    public void calculationTotalSum(List<Object> employeeList) throws SQLException {
        Payroll payroll =new Payroll();

        Double salaryAccrualsD = 0.0;
        Double otherChargesD = 0.0;
        Double allChargesD = 0.0;
        Double deducationD = 0.0;
        Double allDeducationD = 0.0;
        Double totalD = 0.0;

        for (Object object:employeeList) {
            Employee employee1 = (Employee) object;

            for (Payroll payroll1 : employee1.getPayrollList()) {

                for (Map.Entry<String, Double> entry : payroll1.getCharges().entrySet()) {
                    if(entry.getKey().equals("3")){
                        salaryAccrualsD+=entry.getValue();

                    }
                    else
                        otherChargesD += entry.getValue();

                    allChargesD+=entry.getValue();
                    totalD+=entry.getValue();
                }
                for (Map.Entry<String, Double> entry : payroll1.getDeducations().entrySet()) {
                    deducationD += entry.getValue();
                    allDeducationD=deducationD;
                    totalD-=entry.getValue();
                }
            }
        }
        this.setSalaryAccruals(salaryAccrualsD);
        this.setOtherCharges(otherChargesD);
        this.setAllCharges(allChargesD);
        this.setAllDeducation(allDeducationD);
        this.setDeducation(deducationD);
        this.setTotal(totalD);
    }
}
