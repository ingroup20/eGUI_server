package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.Embedded;

public class UserAccount {
    private Integer id;
    private String account;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Integer role;
    private Integer status;

    @Embedded
    private EditRecord editRecord;
}
