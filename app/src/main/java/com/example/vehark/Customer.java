package com.example.vehark;


public class Customer {

    public String name, age, license, number;

    public Customer() {

    }


    public Customer(String name, String age, String license, String number) {
        this.name = name;
        this.age = age;
        this.license = license;
        this.number = number;
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
