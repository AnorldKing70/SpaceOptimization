package com.example.spaceoptima.Models;

public class User
{
    private String fullName;
    private String userEmail;
    private String userId;
    private String userPassword;
    private String confirm;


    public User() {
    }

    public User(String fullName, String userEmail, String userId, String userPassword, String confirm) {

        this.fullName = fullName;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userPassword = userPassword;
        this.confirm = confirm;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
