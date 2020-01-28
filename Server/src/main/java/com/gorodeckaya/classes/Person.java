package com.gorodeckaya.classes;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 2912995098786457690L;

    private String surname;
    private String name;
    private String patronymic;
    private int id;
    private String address;
    private String mob_phone;
    private String type;

    public Person(String surname, String name, String patronymic, int id, String address, String mob_phone, String type) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.id = id;
        this.address = address;
        this.mob_phone = mob_phone;
        this.type = type;
    }

    public Person(String surname, String name, String patronymic, int id, String address, String mob_phone) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.id = id;
        this.address = address;
        this.mob_phone = mob_phone;
    }
    public Person(String surname, String name, String patronymic, String address, String mob_phone) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.address = address;
        this.mob_phone = mob_phone;
    }

    public Person() {

    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMob_phone() {
        return mob_phone;
    }

    public void setMob_phone(String mob_phone) {
        this.mob_phone = mob_phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
