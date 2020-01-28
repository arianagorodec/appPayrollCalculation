package com.gorodeckaya.classes;

import com.gorodeckaya.ServerConnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User extends Person implements Serializable, Access {

    private static final long serialVersionUID = 2912885098745857690L;

    private String login;
    private String password;
    private int access;
    private String type;


    public User(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, int access, String type) {
        super(surname, name, patronymic, id, address, mob_phone);
        this.login = login;
        this.password = password;
        this.access = access;
        this.type = type;

    }
    public User(String surname, String name, String patronymic, int id, String address, String mob_phone, String login, String password, int access) {
        super(surname, name, patronymic, id, address, mob_phone);
        this.login = login;
        this.password = password;
        this.access = access;

    }
    public User(String surname, String name, String patronymic, String address, String mob_phone, String login, String password, int access) {
        super(surname, name, patronymic, address, mob_phone);
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public User() {
        super();
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

    public void setAccess(int access) {
        this.access = access;
    }

    @Override
    public int getAccess() {
        return access;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean checkAccess() {
        if(access == 1)
            return true;
        else
            return false;
    }

    static public int checkUser(String login, String password, String type) {
        List<Object> list = new ArrayList<>();
        User user= new User("","","","","",login, password,2);
        user.setType(type);
        //list = Employee.loadEmployee();
        int state = 2;
        Object obj = (Object) user;
        try {
            list = (ServerConnection.send(obj));
            User newUser = (User) list.get(0);
            if(!newUser.getType().equals("3")) {
                if (newUser.getAccess() == 0)
                    return 0;
                else if (newUser.getAccess() == 1)
                    return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        for (Employee e : list) {
//            User user = new User(e.getSurname(),e.getName(),e.getPatronymic(),e.getAddress(),e.getMob_phone(),e.getLogin(),e.getPassword(),e.getAccess());
//            if (e.getLogin().equals(login) && e.getPassword().equals(password)) {
//                if (e.checkAccess()) {
//                    if (e.getAccess() == 0) {
//                        user.notifierIsEmployee.message("Вошёл пользователь", Notifier.IsEmployee);
//                    }
//                    if (e.getAccess() == 1) {
//                        user.notifierIsEmployee.message("Вход в аккаунт сотрудника", Notifier.IsAdmin);
//                    }
//                }
//                return 1;
//            } else {
//                return 0;
//            }
//        }


        return state;
    }
    static public int findUser(String login, String password) {
        List<Employee> list = new ArrayList<>();
        list = Employee.loadEmployee();
        for (Employee e : list) {
            if (e.getLogin().equals(login) && e.getPassword().equals(password)) {
                return e.getId();
            }
        }

        return 0;
    }

}
