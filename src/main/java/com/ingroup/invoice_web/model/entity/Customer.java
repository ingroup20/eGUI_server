package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

public class Customer {
    private Integer customerId;
    private String customerCode; //客戶代號
    private String identifier; //識別碼(統一編號)
    private String name;
    private String address;
    private String personInCharge; //負責人姓名
    private String telephoneNumber;
    private String facsimileNumber; //傳真號碼
    private String emailAddress;
    private String roleRemark; //營業人角色註記

    @Embedded
    private EditRecord editRecord;
}
