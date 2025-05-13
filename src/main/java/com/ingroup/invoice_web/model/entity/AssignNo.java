package com.ingroup.invoice_web.model.entity;

public class AssignNo {
    private Integer assignId;
    private String yearMonth; //期別
    private String invoiceTrack; //兩碼英文
    private Integer startNo; //起始號碼
    private Integer usedCount; //已使用數量
    private String lastUsedNo; //最後被使用號碼
    private Boolean isEnabled; //啟用

    private EditRecord editRecord;
}
