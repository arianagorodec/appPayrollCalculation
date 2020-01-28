package com.gorodeckaya.classes;


import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 2913035098780357690L;

    LocalDate birthday;
    int code_post;
    Post post;
    Double rate;
    String bankAccount;
    String email;
    String type;
    ArrayList<Payroll> payrollList = new ArrayList<>();

    public Employee(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, int access, LocalDate birthday, Post post, Double rate, int code_post, String bankAccount, String email, String type, ArrayList<Payroll> payrollList) {
        super(surname, name, patronymic, id, address, mob_phone, login, password, access);
        this.birthday = birthday;
        this.post = post;
        this.rate = rate;
        this.bankAccount = bankAccount;
        this.email = email;
        this.type = type;
        this.payrollList = payrollList;
        this.code_post = code_post;
    }

    public Employee(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, int access, LocalDate birthday, Post post, Double rate, int code_post, String bankAccount, String email, ArrayList<Payroll> payrollList) {
        super(surname, name, patronymic, id, address, mob_phone, login, password, access);
        this.birthday = birthday;
        this.post = post;
        this.rate = rate;
        this.bankAccount = bankAccount;
        this.email = email;
        this.payrollList = payrollList;
        this.code_post=code_post;
    }
    public Employee() {
        super();
    }

    public Employee(String surname, String name, String patronymic, int id, String address, String mob_phone, LocalDate birthday, double rate,  int code_post,String bank_account, String mail) {
        super(surname, name, patronymic, id, address, mob_phone);
        this.birthday = birthday;
        this.rate = rate;
        this.bankAccount = bank_account;
        this.email = mail;
        this.code_post=code_post;
    }

    public Employee(String surname, String name, String patronymic, int id, String address, String mob_phone, LocalDate birthday, double rate,  int code_post,String bank_account, String mail,String type) {
        super(surname, name, patronymic,id, address, mob_phone);
        this.birthday = birthday;
        this.rate = rate;
        this.bankAccount = bank_account;
        this.email = mail;
        this.code_post=code_post;
        this.type = type;
    }

    public int getCode_post() {
        return code_post;
    }

    public void setCode_post(int code_post) {
        this.code_post = code_post;
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

    public void addPayroll(Payroll payroll) {
        payrollList.add(payroll);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotal(Connection con) throws SQLException {
        ArrayList<Payroll> list = payrollList;


        for (Payroll payroll : payrollList) {

            Double salaryAccrualsD = 0.0;
            Double otherChargesD = 0.0;
            Double allChargesD = 0.0;
            Double deducationD = 0.0;
            Double allDeducationD = 0.0;
            Double totalD = 0.0;

            for (Map.Entry<String, Double> entry : payroll.getCharges().entrySet()) {
                if (entry.getKey().equals("по окладу")) {
                    salaryAccrualsD += entry.getValue();
                } else
                    otherChargesD += entry.getValue();

                allChargesD += entry.getValue();
                totalD += entry.getValue();
            }
            for (Map.Entry<String, Double> entry : payroll.getDeducations().entrySet()) {
                deducationD += entry.getValue();
                allDeducationD = deducationD;
                totalD += entry.getValue();
            }
            Total total = new Total();
            total.setSalaryAccruals(salaryAccrualsD);
            total.setOtherCharges(otherChargesD);
            total.setAllCharges(allChargesD);
            total.setAllDeducation(allDeducationD);
            total.setDeducation(deducationD);
            total.setTotal(totalD);
            payroll.setTotal(total);
        }
    }




    public List<Object> getAllEmployee(Connection con) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rsEm = stmt.executeQuery("SELECT * FROM payrollsystem.personal_account;");
            while (rsEm.next()) {
                list.add(new Employee(rsEm.getString("surname"),
                        rsEm.getString("name"),
                        rsEm.getString("patronymic"),
                        rsEm.getInt("personal_number"),
                        rsEm.getString("address"),
                        rsEm.getString("mob_phone"),
                        rsEm.getDate("birthday").toLocalDate(),
                        rsEm.getDouble("rate"),
                        rsEm.getInt("post_code"),
                        rsEm.getString("bank_account"),
                        rsEm.getString("mail")));
            }
            rsEm.close();
            for (Object object : list) {
                boolean ch = false;
                Employee employee = (Employee) object;
                ResultSet rsUs = stmt.executeQuery("SELECT * FROM payrollsystem.users WHERE idusers = " + employee.getId() + ";");
                while (rsUs.next()) {
                    employee.setLogin(rsUs.getString("login"));
                    employee.setPassword(rsUs.getString("password"));
                    employee.setAccess(rsUs.getInt("access"));
                    ch = true;
                }
                if (!ch) {
                    employee.setLogin("");
                    employee.setPassword("");
                    employee.setAccess(0);
                }
                rsUs.close();
            }
            for (Object ob : list) {
                Employee employee = (Employee) ob;
                ResultSet rsPost = stmt.executeQuery("SELECT * FROM payrollsystem.organization_structure WHERE code_post = " + employee.getCode_post() + ";");
                Post post = new Post();
                while (rsPost.next()) {
                    post.setName(rsPost.getString("post"));
                    post.setDepartment(rsPost.getString("department"));
                    post.setSalary(rsPost.getDouble("salary"));
                    post.setCode(rsPost.getInt("code_post"));
                }
                rsPost.close();
                employee.setPost(post);
            }

            for (Object ob : list) {
                Employee employee = (Employee) ob;
                Map<Integer, String> names = Payroll.getNameChargesAndDeducations(con);
                ArrayList<Payroll> payrollList = new ArrayList<>();
                ArrayList<Payroll> newPayrollList = new ArrayList<>();
                Payroll payroll = new Payroll();

                ResultSet rsDays = stmt.executeQuery("SELECT * FROM payrollsystem.working_days WHERE personal_number = '" + employee.getId()+"';");
                while (rsDays.next()){
                    payroll = new Payroll();
                    payroll.setWorkingDays(rsDays.getInt("workingDays"));
                    payroll.setWasWorkedDays(rsDays.getInt("wasWorkedDays"));
                    payroll.setMonth(rsDays.getInt("month"));
                    payroll.setYear(rsDays.getInt("year"));
                    payrollList.add(payroll);
                }
                rsDays.close();
                for (Payroll p: payrollList) {
                   ResultSet rsPayroll = stmt.executeQuery("SELECT * FROM payrollsystem.payroll_sheet WHERE personal_number = '" + employee.getId() + "' AND month='"+ p.getMonth()+"' AND year='"+ p.getYear() + "';");
                   while (rsPayroll.next()) {
                       p.addCharges(names.get(rsPayroll.getInt("code_charges")), rsPayroll.getDouble("sum_charges"));
                   }
                   rsPayroll.close();

                   ResultSet rsDeducations = stmt.executeQuery("SELECT * FROM payrollsystem.deducation_sheet WHERE personal_number = '" + employee.getId() + "' AND month='"+ p.getMonth()+"' AND year='"+ p.getYear() + "';");
                   while (rsDeducations.next()) {
                       p.addDeducations(names.get(rsDeducations.getInt("code_deducation")), rsDeducations.getDouble("sum_deducation"));
                   }
                   rsDeducations.close();
                }
               employee.setPayrollList(payrollList);
               employee.setTotal(con);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> getAllEmployeeForTotal(Connection con, Employee currEmployee) {
        List<Object> list = new ArrayList();
        List<Object> newlist = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rsEm = stmt.executeQuery("SELECT * FROM payrollsystem.personal_account;");
            while (rsEm.next()) {
                list.add(new Employee(rsEm.getString("surname"),
                        rsEm.getString("name"),
                        rsEm.getString("patronymic"),
                        rsEm.getInt("personal_number"),
                        rsEm.getString("address"),
                        rsEm.getString("mob_phone"),
                        rsEm.getDate("birthday").toLocalDate(),
                        rsEm.getDouble("rate"),
                        rsEm.getInt("post_code"),
                        rsEm.getString("bank_account"),
                        rsEm.getString("mail")));
            }
            rsEm.close();
            for(Object object:list) {
                boolean ch=false;
                Employee employee=(Employee) object;
                    ResultSet rsUs = stmt.executeQuery("SELECT * FROM payrollsystem.users WHERE idusers = " + employee.getId() + ";");
                    while (rsUs.next()) {
                        employee.setLogin(rsUs.getString("login"));
                        employee.setPassword(rsUs.getString("password"));
                        employee.setAccess(rsUs.getInt("access"));
                        ch = true;
                    }
                    if (!ch) {
                        employee.setLogin("");
                        employee.setPassword("");
                        employee.setAccess(0);
                    }
                    rsUs.close();
            }
            for(Object ob:list) {
                Employee employee=(Employee) ob;
                ResultSet rsPost = stmt.executeQuery("SELECT * FROM payrollsystem.organization_structure WHERE code_post = " + employee.getCode_post() + ";");
                Post post = new Post();
                while (rsPost.next()) {
                    post.setName(rsPost.getString("post"));
                    post.setDepartment(rsPost.getString("department"));
                    post.setSalary(rsPost.getDouble("salary"));
                    post.setCode(rsPost.getInt("code_post"));
                }
                rsPost.close();
                employee.setPost(post);
            }

            for (Object ob : list) {
                ArrayList<Payroll> payrollList = new ArrayList<>();
                Employee employee =new Employee();
                employee = (Employee) ob;
                Map<Integer, String> names = Payroll.getNameChargesAndDeducations(con);
                Payroll payroll = new Payroll();

                ResultSet rsDays = stmt.executeQuery("SELECT * FROM payrollsystem.working_days WHERE personal_number = " + employee.getId() + " AND month=" + currEmployee.getPayrollList().get(0).getMonth() + " AND year=" + currEmployee.getPayrollList().get(0).getYear() + ";");
                while (rsDays.next()) {
                    payroll.setPersonalNumber(employee.getId());
                    payroll.setMonth(rsDays.getInt("month"));
                    payroll.setYear(rsDays.getInt("year"));
                    payroll.setWorkingDays(rsDays.getInt("workingDays"));
                    payroll.setWasWorkedDays(rsDays.getInt("wasWorkedDays"));
                    //payrollList.add(payroll);
                }
                rsDays.close();

                boolean state = false;

                ResultSet rsPayroll = stmt.executeQuery("SELECT * FROM payrollsystem.payroll_sheet WHERE personal_number = '" + employee.getId() + "' AND month='" + currEmployee.getPayrollList().get(0).getMonth()  + "' AND year='" + currEmployee.getPayrollList().get(0).getYear()  + "';");
                while (rsPayroll.next()) {
                        payroll.addCharges(names.get(rsPayroll.getInt("code_charges")), rsPayroll.getDouble("sum_charges"));
                        state=true;
                }
                rsPayroll.close();


                ResultSet rsDeducations = stmt.executeQuery("SELECT * FROM payrollsystem.deducation_sheet WHERE personal_number = "+employee.getId()+ " AND month=" + currEmployee.getPayrollList().get(0).getMonth() + " AND year=" +currEmployee.getPayrollList().get(0).getYear() + ";");
                while (rsDeducations.next()) {
                        payroll.addDeducations(names.get(rsDeducations.getInt("code_deducation")), rsDeducations.getDouble("sum_deducation"));
                        state = true;
                }
                rsDeducations.close();

                if(state){
                    payrollList.add(payroll);
                    employee.setPayrollList(payrollList);
                    employee.setTotal(con);
                    newlist.add(employee);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return newlist;
    }

    public List<Object> editEmployee(Connection con, Employee employee) {
        List<Object> list = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement("UPDATE `payrollsystem`.`personal_account` SET `surname` = '"+employee.getSurname()+"', `name`='"+employee.getName()+"', `patronymic`='"+employee.getPatronymic()+"', `birthday`='"+employee.getBirthday()+"', `post_code`='"+employee.getPost().getCode()+"', `rate`="+employee.getRate()+", `bank_account`='"+employee.getBankAccount()+"', `mob_phone`='"+employee.getMob_phone()+"', `mail`='"+employee.getEmail()+"', `address`='"+employee.getAddress()+"' WHERE personal_number ='"+ employee.getId()+"';");
            st.executeUpdate();
            st.close();
            if(employee.getPost().getDepartment().equals("Бухгалтерия")) {
                st = con.prepareStatement("UPDATE `payrollsystem`.`users` SET `login` = '" + employee.getLogin() + "', `password`='" + employee.getPassword() + "', `access`='" + 1 + "' WHERE idusers ='" + employee.getId() + "';");
                st.executeUpdate();
                st.close();
            }
            else{
                st = con.prepareStatement("UPDATE `payrollsystem`.`users` SET `login` = '" + employee.getLogin() + "', `password`='" + employee.getPassword()+ "', `access`='" + 0 + "' WHERE idusers ='" + employee.getId() + "';");
                st.executeUpdate();
                st.close();
            }
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
        list.add("false");
        return list;
    }

    public List<Object> deleteEmployee(Connection con, Employee employee) {
        List<Object> list = new ArrayList<>();
       try {
            PreparedStatement st = con.prepareStatement("DELETE FROM `payrollsystem`.`personal_account` WHERE personal_number ='"+ employee.getId()+"';");
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

    public boolean checkEmployee(Connection con, Employee employee){
        boolean state = false;
        Employee newEmployee = new Employee();
        List<Object> list = new ArrayList<>();
        list=newEmployee.getAllEmployee(con);
        for (Object o: list){
            Employee employee1= (Employee) o;
            if(employee1.getBankAccount().equals(employee.getBankAccount())){
                if(employee1.getId()!=employee.getId()) {
                    state = true;
                    break;
                }
            }
        }
        return state;
    }

    public boolean checkLogin(Connection con, Employee employee){
        boolean state = false;
        Employee newEmployee = new Employee();
        List<Object> list = new ArrayList<>();
        list=newEmployee.getAllEmployee(con);
        for (Object o: list){
            Employee employee1= (Employee) o;
            if(employee1.getLogin().equals(employee.getLogin())){
                if(employee1.getId()!=employee.getId()) {
                    state = true;
                    break;
                }
            }
        }
        return state;
    }

    public List<Object> addEmployee(Connection con, Employee employee) {
        List<Object> list = new ArrayList<>();
        try {
            if(!checkEmployee(con,employee)) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM payrollsystem.organization_structure WHERE post = '" + employee.getPost().getName() + "' AND department = '" + employee.getPost().getDepartment() + "' ;");
                int code;
                if (rs.next()) {
                    code = rs.getInt("code_post");
                    rs.close();
                    stmt.close();
                    PreparedStatement st = con.prepareStatement("INSERT INTO payrollsystem.personal_account(surname,name,patronymic,birthday,post_code,rate,bank_account,mob_phone,mail,address) VALUES ('" + employee.getSurname() + "','" + employee.getName() + "','" + employee.getPatronymic() + "','" + employee.getBirthday() + "'," +/*rs.getInt("code_post")*/code + "," + employee.getRate() + ",'" + employee.getBankAccount() + "','" + employee.getMob_phone() + "','" + employee.getEmail() + "','" + employee.getAddress() + "');");
                    st.executeUpdate();
                    st.close();
                    list.add("true");
                }
            }
            else list.add("false");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        list.add("false");
        return list;
    }

    public List<Object> addUser(Connection con, Employee employee) {
        List<Object> list = new ArrayList<>();
        try {
            if (checkEmployee(con, employee)) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM payrollsystem.personal_account WHERE bank_account = '" + employee.getBankAccount() + "' ;");
                int code=0;
                int codePost = 0;
                String nameDepartment = "";
                if (rs.next()) {
                    code = rs.getInt("personal_number");
                    codePost = rs.getInt("post_code");
                    rs.close();

                    stmt = con.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM payrollsystem.organization_structure WHERE code_post = '" + codePost + "' ;");
                    if (rs.next()) {
                        nameDepartment=rs.getString("department");
                        rs.close();
                        if(nameDepartment.equals("Бухгалтерия")) {
                            PreparedStatement st = con.prepareStatement("INSERT INTO payrollsystem.users(idusers,login,password,access) VALUES ('" + code + "','" + employee.getLogin() + "','" + employee.getPassword() + "','1');");
                            st.executeUpdate();
                            st.close();
                            list.add("true");
                        }
                        else {
                            PreparedStatement st = con.prepareStatement("INSERT INTO payrollsystem.users(idusers,login,password,access) VALUES ('" + code + "','" + employee.getLogin() + "','" + employee.getPassword() + "','0');");
                            st.executeUpdate();
                            st.close();
                            list.add("true");
                        }
                    }
                    else
                        list.add("false");
                } else
                    list.add("false");
            }
            else
                list.add("false");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        list.add("false");
        return list;
    }


    public List<Object> searchEmployee(Connection con, Employee employee){
        Employee e = new Employee();
        List<Object> list = new ArrayList<>();
        List<Object> allEmployee = e.getAllEmployee(con);
        for (Object o: allEmployee) {
            e = (Employee) o;
            if (employee.getType().equals("Фамилия")) {
                if(e.getSurname().equals(employee.getSurname()))
                    list.add(e);
            } else if (employee.getType().equals("Имя")) {
                if(e.getName().equals(employee.getName()))
                    list.add(e);
            } else if (employee.getType().equals("Отчество")) {
                if(e.getPatronymic().equals(employee.getPatronymic()))
                    list.add(e);
            } else if (employee.getType().equals("Лицевой счёт")) {
                if(e.getBankAccount().equals(employee.getBankAccount()))
                    list.add(e);
            } else if (employee.getType().equals("Должность")) {
                if(e.getPost().getName().equals(employee.getPost().getName()))
                    list.add(e);
            } else if (employee.getType().equals("Отдел")) {
                if(e.getPost().getDepartment().equals(employee.getPost().getDepartment()))
                    list.add(e);
            } else if (employee.getType().equals("E-mail")) {
                if(e.getEmail().equals(employee.getEmail()))
                    list.add(e);
            }
        }
        return list;
    }


    public List<Object> getEmployeeForPaySheet(Connection con, Employee employee) {
        List<Object> list = new ArrayList();
        List<Object> newlist = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rsEm = stmt.executeQuery("SELECT * FROM payrollsystem.personal_account WHERE personal_number = " + employee.getId() + ";");
            while (rsEm.next()) {
                employee.setSurname(rsEm.getString("surname"));
                employee.setName(rsEm.getString("name"));
                employee.setPatronymic(rsEm.getString("patronymic"));
                employee.setId(rsEm.getInt("personal_number"));
                employee.setBirthday(rsEm.getDate("birthday").toLocalDate());
                employee.setRate(rsEm.getDouble("rate"));
                employee.setCode_post(rsEm.getInt("post_code"));
                employee.setBankAccount(rsEm.getString("bank_account"));
                employee.setEmail(rsEm.getString("mail"));
            }
            rsEm.close();
            boolean ch = false;
            ResultSet rsUs = stmt.executeQuery("SELECT * FROM payrollsystem.users WHERE idusers = " + employee.getId() + ";");
            while (rsUs.next()) {
                employee.setLogin(rsUs.getString("login"));
                employee.setPassword(rsUs.getString("password"));
                employee.setAccess(rsUs.getInt("access"));
                ch = true;
            }
            if (!ch) {
                employee.setLogin("");
                employee.setPassword("");
                employee.setAccess(0);
            }
            rsUs.close();

            ResultSet rsPost = stmt.executeQuery("SELECT * FROM payrollsystem.organization_structure WHERE code_post = " + employee.getCode_post() + ";");
            Post post = new Post();
            while (rsPost.next()) {
                post.setName(rsPost.getString("post"));
                post.setDepartment(rsPost.getString("department"));
                post.setSalary(rsPost.getDouble("salary"));
                post.setCode(rsPost.getInt("code_post"));
            }
            rsPost.close();
            employee.setPost(post);

            Map<Integer, String> names = Payroll.getNameChargesAndDeducations(con);

            ResultSet rsDays = stmt.executeQuery("SELECT * FROM payrollsystem.working_days WHERE personal_number = " + employee.getId() + " AND month=" + employee.getPayrollList().get(0).getMonth() + " AND year=" + employee.getPayrollList().get(0).getYear() + ";");
            while (rsDays.next()) {
               employee.getPayrollList().get(0).setPersonalNumber(employee.getId());
                employee.getPayrollList().get(0).setMonth(rsDays.getInt("month"));
                employee.getPayrollList().get(0).setYear(rsDays.getInt("year"));
                employee.getPayrollList().get(0).setWorkingDays(rsDays.getInt("workingDays"));
                employee.getPayrollList().get(0).setWasWorkedDays(rsDays.getInt("wasWorkedDays"));
                //payrollList.add(payroll);
            }
            rsDays.close();

            boolean state = false;

            ResultSet rsPayroll = stmt.executeQuery("SELECT * FROM payrollsystem.payroll_sheet WHERE personal_number = '" + employee.getId() + "' AND month='" + employee.getPayrollList().get(0).getMonth() + "' AND year='" + employee.getPayrollList().get(0).getYear() + "';");
            while (rsPayroll.next()) {
                employee.getPayrollList().get(0).addCharges(names.get(rsPayroll.getInt("code_charges")), rsPayroll.getDouble("sum_charges"));
                state = true;
            }
            rsPayroll.close();

            ResultSet rsDeducations = stmt.executeQuery("SELECT * FROM payrollsystem.deducation_sheet WHERE personal_number = " + employee.getId() + " AND month=" + employee.getPayrollList().get(0).getMonth() + " AND year=" + employee.getPayrollList().get(0).getYear() + ";");
            while (rsDeducations.next()) {
                employee.getPayrollList().get(0).addDeducations(names.get(rsDeducations.getInt("code_deducation")), rsDeducations.getDouble("sum_deducation"));
                state = true;
            }
            rsDeducations.close();

            if (state) {
                newlist.add(employee);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return newlist;
    }
}
