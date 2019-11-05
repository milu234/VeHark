package com.example.vehark;


public class Customer {

    public String name, number, license, age;

    public Customer() {

    }


    public Customer(String name, String number, String license, String age) {
        this.name = name;
        this.number = number;
        this.license = license;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
