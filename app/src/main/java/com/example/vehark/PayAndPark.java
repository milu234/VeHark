package com.example.vehark;

public class PayAndPark {
    public String name , gst , address, oh;

    public PayAndPark(){

    }

    public PayAndPark(String name, String gst, String address, String oh) {
        this.name = name;
        this.gst = gst;
        this.address = address;
        this.oh = oh;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOh() {
        return oh;
    }

    public void setOh(String oh) {
        this.oh = oh;
    }


}
