package com.winning.model;

public class Patient {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Patient(int age, String name, int status) {
        this.age = age;
        this.name = name;
        this.status = status;
    }
}
