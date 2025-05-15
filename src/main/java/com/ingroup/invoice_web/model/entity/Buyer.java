package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Buyer {
    @NotNull
    private String identifier; //識別碼(統一編號)
    @NotNull
    private String name;
    private String address;
    private String personInCharge; //負責人姓名
    private String telephoneNumber;
    private String facsimileNumber; //傳真號碼
    @Email(message = "e-mail error")
    private String emailAddress;
    private String customerNumber; //客戶編號
    private String roleRemark; //營業人角色註記

}
