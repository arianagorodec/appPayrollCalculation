package com.gorodeckaya.classes;

import com.gorodeckaya.ServerConnection;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 2913035098780357690L;

    LocalDate birthday;
    Post post;
    Double rate;
    String bankAccount;
    String email;
    String type;
    int post_code;
    ArrayList<Payroll> payrollList=new ArrayList<>();


    public Employee(String surname, String name, String patronymic, String address, String mob_phone, String login, String password, int access, LocalDate birthday, Post post, Double rate, String bankAccount, String email, String type, int post_code, ArrayList<Payroll> payrollList) {
        super(surname, name, patronymic, address, mob_phone, login, password, access);
        this.birthday = birthday;
        this.post = post;
        this.rate = rate;
        this.bankAccount = bankAccount;
        this.email = email;
        this.type = type;
        this.post_code = post_code;
        this.payrollList = payrollList;
    }

    public Employee(LocalDate birthday, Post post, Double rate, String bankAccount, String email, String type, int post_code, ArrayList<Payroll> payrollList) {
        this.birthday = birthday;
        this.post = post;
        this.rate = rate;
        this.bankAccount = bankAccount;
        this.email = email;
        this.type = type;
        this.post_code = post_code;
        this.payrollList = payrollList;
    }

    public Employee() {
        super();

    }

    public Employee(Employee employee, String type) {
        super(employee.getSurname(), employee.getName(), employee.getPatronymic(), employee.getId(), employee.getAddress(), employee.getMob_phone(), employee.getLogin(), employee.getPassword(), employee.getAccess());
        this.birthday = employee.getBirthday();
        this.post = employee.getPost();
        this.rate = employee.getRate();
        this.bankAccount = employee.getBankAccount();
        this.email = employee.getEmail();
        this.payrollList = employee.getPayrollList();
        this.type = type;
        this.post_code=employee.getPost_code();
    }



    public Employee(String type) {
        this.type = type;
    }

    public int getPost_code() {
        return post_code;
    }

    public void setPost_code(int post_code) {
        this.post_code = post_code;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }



    public String getPostName(){
        if(post==null){
            return "";
        }
        else
            return post.getName();
    }
    public String getDepartment(){

        if(post==null){
            return "";
        }
        else
            return post.getDepartment();
    }

    public int getID(){
        return getId();
    }

    public void setNamePost(String namePost) {
        this.post.setName(namePost);
    }

    public void setDepartment(String department) {
        this.post.setDepartment(department);
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Payroll> getPayrollList() {
        return payrollList;
    }

    public void setPayrollList(ArrayList<Payroll> payrollList) {
        this.payrollList = payrollList;
    }

    public void addPayroll(Payroll payroll){
        payrollList.add(payroll);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWasWorkedDays() {
        return payrollList.get(0).getWasWorkedDays();
    }
    public Double getSalary(){

        if(post==null){
            return 0.0;
        }
        else
            return post.getSalary();
    }

    /*public Double getSalaryAccruals() {

        if (post == null) {
            return 0.0;
        } else
            return (post.getSalary() / payrollList.get(0).getWorkingDays() * payrollList.get(0).getWasWorkedDays() * this.rate);
    }*/


    public Double getSalaryAccruals() {
        return payrollList.get(0).getTotal().getSalaryAccruals();
    }

    public Double getOtherCharges() {
        return payrollList.get(0).getTotal().getOtherCharges();
    }

    public Double getAllCharges() {
        return payrollList.get(0).getTotal().getAllCharges();
    }

    public Double getDeducation() {
        return payrollList.get(0).getTotal().getDeducation();
    }

    public Double getAllDeducation() {
        return payrollList.get(0).getTotal().getAllDeducation();
    }

    public Double getTotal() {
        return payrollList.get(0).getTotal().getTotal();
    }


    public static List<Employee> loadEmployee() {
        List<Object> list = new ArrayList();
        Employee employee = new Employee("allEmployee");
        Object obj = (Object) employee;
        List<Employee> employeeList = new ArrayList<>();
        try {
            list = (ServerConnection.send(obj));
            if(list!=null) {
                for (Object object : list) {
                    Employee employee1 = (Employee) object;
                    employeeList.add(employee1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public static List<Object> loadEmployeeForTotal(int month,int year) {
        List<Object> list = new ArrayList();
        Employee employee = new Employee("allEmployeeForTotal");
        employee.setPayrollList(new ArrayList<Payroll>());
        employee.getPayrollList().add(new Payroll(month,year));
        Object obj = (Object) employee;
        List<Employee> employeeList = new ArrayList<>();
        try {
            list = (ServerConnection.send(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<Object> loadEmployeeForPaySheet(int month,int year,int id) {
        List<Object> list = new ArrayList();
        Employee employee = new Employee("EmployeeForPaySheet");
        employee.setId(id);
        employee.setPayrollList(new ArrayList<Payroll>());
        employee.getPayrollList().add(new Payroll(month,year));
        Object obj = (Object) employee;
        try {
            list = (ServerConnection.send(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
