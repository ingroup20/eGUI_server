package com.ingroup.invoice_web.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "printer")
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer printerId;
    private Integer companyId;
    private String printerName;
    private String printerKey;
    private String printType;
    private Integer printerStatus;


    public Integer getPrinterId() {
        return printerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterKey() {
        return printerKey;
    }

    public void setPrinterKey(String printerKey) {
        this.printerKey = printerKey;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public Integer getPrinterStatus() {
        return printerStatus;
    }

    public void setPrinterStatus(Integer printerStatus) {
        this.printerStatus = printerStatus;
    }
}
