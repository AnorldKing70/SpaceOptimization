package com.example.spaceoptima.Models;

public class UserType
{
    private String fullName;
    private String userNum;
    private String email;
    private String password;
    private String confirm;
    private String course;

    public UserType() {
    }

    public UserType(String fullName, String userNum, String email, String password, String confirm, String course) {
        this.fullName = fullName;
        this.userNum = userNum;
        this.email = email;
        this.password = password;
        this.confirm = confirm;
        this.course = course;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
