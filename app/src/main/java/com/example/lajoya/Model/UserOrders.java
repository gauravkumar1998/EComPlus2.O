package com.example.lajoya.Model;

public class UserOrders {
    private String oId, date,time,state,UserPhone, image;
    private int totalAmount;
    public UserOrders() {
    }

    public UserOrders(String oId, String state, String date, String time,String UserPhone, int totalAmount, String image) {
        this.oId=oId;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.UserPhone=UserPhone;
        this.image = image;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String UserPhone) {
        this.UserPhone = UserPhone;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() { return image; }
}
