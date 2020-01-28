package com.gorodeckaya.classes;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class User extends Person implements Serializable {

    private static final long serialVersionUID = 2912885098745857690L;

    private String login;
    private String password;
    private int access=0;
    private String type;

    Notifier notifierIsEmployee, notifierIsAdmin,  notifierErr;


    public User(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, int access, String type) {
        super(surname, name, patronymic, id, address, mob_phone);
        this.login = login;
        this.password = password;
        this.access = access;
        this.type=type;
    }

    public User(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, String type) {
        super(surname, name, patronymic, id, address, mob_phone);
        this.login = login;
        this.password = password;
        this.type=type;
    }

    public User(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, int access) {
        super(surname, name, patronymic, id, address, mob_phone);
        this.login = login;
        this.password = password;
        this.access = access;

    }

    public User(String login, String password, int access){
        super();
        this.login=login;
        this.password=password;
        this.access=access;
        notifierIsEmployee=new IsEmployeeNotifier(Notifier.IsEmployee);
        notifierIsAdmin= new IsAdminNotifier(Notifier.IsAdmin);
        notifierErr = new ErrorNotifier(Notifier.Error);

        notifierIsEmployee.setNext(notifierIsAdmin);
    }

    public User(String surname, String name, String patronymic, String address, String mob_phone) {
        super(surname, name, patronymic, address, mob_phone);
    }

    public User() {
        super();
    }

    public User(String surname, String name, String patronymic, int id, String address, String mob_phone) {
        super(surname, name, patronymic, id, address, mob_phone);
    }

    static public List<Object> checkUser(Connection con, String login, String password, String type) {
        List<Object> list = new ArrayList<>();
        if(type.equals("checkEmp")) {
            Employee employee = new Employee();
            list = employee.getAllEmployee(con);
            int state = 0;
            for (Object object : list) {
                Employee e = (Employee) object;
                User user = new User(e.getLogin(), e.getPassword(), e.getAccess());
                if (e.getLogin().equals(login) && e.getPassword().equals(password)) {
                    if (e.getAccess() == 0) {
                        user.notifierIsEmployee.message("Вошёл пользователь", Notifier.IsEmployee);
                    }
                    if (e.getAccess() == 1) {
                        user.notifierIsEmployee.message("Вход в аккаунт сотрудника", Notifier.IsAdmin);
                    }
                    state++;
                    list.clear();
                    e.setType("0");
                    list.add(e);
                    break;
                }
            }
            if (state == 0) {
                User user = new User("","",0);
                Employee employee1 = new Employee();
                user.notifierErr.message("Попытка входа незарегистрированному пользователю", Notifier.Error);
                list.clear();
                employee1.setType("3");
                list.add(employee1);
            }
        }
        else if(type.equals("checkAdmin")){
                Employee employee=new Employee();
                list = employee.getAllEmployee(con);
                int state = 0;
                for (Object object : list) {
                    Employee e = (Employee) object;
                    User user = new User(e.getLogin(),e.getPassword(),e.getAccess());
                    if (e.getLogin().equals(login) && e.getPassword().equals(password)) {
                        if (e.getAccess() == 0) {
                            user.notifierIsEmployee.message("Попытка входа пользователя", Notifier.IsEmployee);
                        }
                        if (e.getAccess() == 1) {
                            user.notifierIsEmployee.message("Вход в аккаунт админа", Notifier.IsAdmin);
                        }
                        state++;
                        list.clear();
                        e.setType("0");
                        list.add(e);
                        break;
                    }
                }
                if(state==0){
                    User user =new User("","",0);
                    Employee employee1=new Employee();
                    user.notifierErr.message("Попытка входа незарегистрированному пользователю",Notifier.Error);
                    list.clear();
                    employee1.setType("3");
                    list.add(employee1);
                }
        }
        return list;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
