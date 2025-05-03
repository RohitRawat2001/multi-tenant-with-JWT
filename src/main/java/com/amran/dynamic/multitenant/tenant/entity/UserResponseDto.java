package com.amran.dynamic.multitenant.tenant.entity;

public class UserResponseDto {

    private Integer userId;
    private String fullName;
    private String gender;
    private String userName;
    private String password;
    private String pass;
    private String status;

    public UserResponseDto() {
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserResponseDto(Integer userId, String fullName, String gender, String userName, String password,String pass, String status) {
        this.userId = userId;
        this.fullName = fullName;
        this.gender = gender;
        this.userName = userName;
        this.password = password;
        this.pass = pass;
        this.status = status;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}