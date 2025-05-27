package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer companyId;
    private String account;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Integer role;
    private Integer printerId;
    private Integer status;
    private LocalDate pwSafeDate; //密碼上次更新時間

    public Integer getId() {
        return id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getPrinterId() {
        return printerId;
    }

    public void setPrinterId(Integer printerId) {
        this.printerId = printerId;
    }

    public LocalDate getPwSafeDate() {
        return pwSafeDate;
    }

    public void setPwSafeDate(LocalDate pwSafeDate) {
        this.pwSafeDate = pwSafeDate;
    }
}
