package com.gorodeckaya.fxml.accMenu.grafic;

import com.gorodeckaya.classes.Employee;
import com.gorodeckaya.classes.Payroll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    Model(){

    }

    public Map<Integer, String> monthPush(){
        Map<Integer, String> monthS = new HashMap<>();
        monthS.put(1, "январь");
        monthS.put(2, "февраль");
        monthS.put(3, "март");
        monthS.put(4, "апрель");
        monthS.put(5, "май");
        monthS.put(6, "июнь");
        monthS.put(7, "июль");
        monthS.put(8, "август");
        monthS.put(9, "сентябрь");
        monthS.put(10, "октябрь");
        monthS.put(11, "ноябрь");
        monthS.put(12, "декабрь");
        return  monthS;
    }

    public ObservableList<PieChart.Data> drawGraf(){

        List<Employee> list = Employee.loadEmployee();
        Map<String, Integer> post = new HashMap<>();

        for (Employee employee: list){
            if(post.get(employee.getPost().getName())==null)
                post.put(employee.getPost().getName(),1);
            else
                post.put(employee.getPost().getName(), (post.get(employee.getPost().getName()+1)));
        }
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for(Map.Entry<String, Integer> item : post.entrySet()){
            data.add(new PieChart.Data(item.getKey(),item.getValue()));
        }
        return  data;
    }

    public Map<Integer,Number> drawPayroll(Integer year) {
        List<Employee> list = Employee.loadEmployee();
        Map<Integer, Number> payroll = new HashMap<>();
        for (int i = 1; i < 13; i++) {
            java.lang.Integer k =0;
            java.lang.Double sum = 0.0;
            for (Employee employee : list) {
                for (Payroll p : employee.getPayrollList()) {
                    if (p.getMonth() == i && p.getYear() == year) {
                        for (Map.Entry<String, java.lang.Double> item : p.getCharges().entrySet()) {
                            sum += item.getValue();
                        }
                        for (Map.Entry<String, java.lang.Double> item : p.getDeducations().entrySet()) {
                            sum -= item.getValue();
                        }
                        k++;
                    }
                }
            }
            if(k==0)
                payroll.put(i,0.0);
            else
                payroll.put(i,(sum/k));
        }
        return payroll;
    }

    public Map<Integer,Double> drawWDays(Integer year){
        List<Employee> list = Employee.loadEmployee();
        Map<Integer, Double> workDays = new HashMap<>();
        Integer y = year;
        for (int i = 1; i < 13; i++) {
            Integer k =0;
            Double wDays = 0.0;
            for (Employee employee : list) {
                for (Payroll p : employee.getPayrollList()) {
                    if (p.getMonth() == i && p.getYear() == y) {
                        wDays+=p.getWorkingDays();
                        k++;
                    }
                }
            }
            if(k==0)
                workDays.put(i, 0.0);
            else
                workDays.put(i, (wDays / k));
        }
        return workDays;
    }

    public Map<Integer,Double> drawWWDays(Integer year) {
        List<Employee> list = Employee.loadEmployee();
        Map<Integer, Double> workedDays = new HashMap<>();
        Integer y = year;
        for (int i = 1; i < 13; i++) {
            Integer k = 0;
            Double wWDays = 0.0;
            for (Employee employee : list) {
                for (Payroll p : employee.getPayrollList()) {
                    if (p.getMonth() == i && p.getYear() == y) {
                        wWDays += p.getWasWorkedDays();
                        k++;
                    }
                }
            }
            if(k==0)
                workedDays.put(i, 0.0);
            else
                workedDays.put(i, (wWDays / k));
        }

        return workedDays;
    }
}
