package com.amran.dynamic.multitenant.tenant.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

   
    @Column(name = "full_name",nullable = false)
    private String fullName;

   
    @Column(name = "gender",nullable = false)
    private String gender;

   
    @Column(name = "user_name",nullable = false,unique = true)
    private String userName;
   
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "pass")
    private String pass;


    @Column(name = "status",nullable = false)
    private String status;

    public User() {
    }

    public User( String fullName, String gender, String userName, String password, String status) {
        this.fullName = fullName;
        this.gender = gender;
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
