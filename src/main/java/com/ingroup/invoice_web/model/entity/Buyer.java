package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Buyer {


    @Column(name = "buyer_identifier")
    private String identifier; //識別碼(統一編號)

//    @NotNull
    @Column(name = "buyer_name")
    private String name;

    @Column(name = "buyer_address")
    private String address;

    @Column(name = "buyer_person_in_charge")
    private String personInCharge; //負責人姓名

    @Column(name = "buyer_telephone_number")
    private String telephoneNumber;

    @Column(name = "buyer_facsimile_number")
    private String facsimileNumber; //傳真號碼

//    @Email(message = "e-mail error")
    @Column(name = "buyer_email_address")
    private String emailAddress;

    @Column(name = "buyer_customer_number")
    private String customerNumber; //客戶編號

    @Column(name = "buyer_role_remark")
    private String roleRemark; //營業人角色註記

}
