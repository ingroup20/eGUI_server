package com.ingroup.invoice_web.util.constant;

public enum MigTypeEnum {

    ISSUE_EXCHANGE_INVOICE("A0101", "開立交換發票", "B2B"),
    CATCH_ISSUE_EXCHANGE_INVOICE("A0102", "交換發票接收確認", "B2B"),
    CANCEL_EXCHANGE_INVOICE("A0201", "作廢交換發票", "B2B"),
    CATCH_CANCEL_EXCHANGE_INVOICE("A0202", "作廢交換發票接收確認", "B2B"),
    REJECT_ISSUE_EXCHANGE_INVOICE("A0301", "退回(拒收)交換發票", "B2B"),
    CATCH_REJECT_EXCHANGE_INVOICE("A0302", "退回交換發票接收確認", "B2B"),
    ISSIE_EXCHANGE_ALLOWANCE("B0101", "開立交換發票折讓", "B2B"),
    CATCH_ISSUE_EXCHANGE_ALLOWANCE("B0102", "作廢交換發票折讓", "B2B"),
    CANCEL_EXCHANGE_ALLOWANCE("B0201", "作廢折讓證明單接收確認", "B2B"),
    CATCH_CANCEL_EXCHANGE_ALLOWANCE("B0202", "", "B2B"),
    ISSUE_EVIDENCE_INVOICE("F0401", "開立存證發票", "B2S"),
    VOID_EVIDENCE_INVOICE("F0501", "存證作廢發票", "B2S"),
    CANCEL_EVIDENCE_INVOICE("F0701", "存證註銷發票", "B2S"),
    ISSUE_EVIDENCE_ALLOWANCE("G0401", "開立存證發票折讓", "B2S"),
    CANCEL_EVIDENCE_ALLOWANCE("G0701", "存證作廢折讓", "B2S"),
    NO_USE_ASSIGN("E0402", "空白未使用字軌號碼", "B2S"),
    UNKNOWN("UNKNOWN", "未知", "UNKNOWN");

    private final String migTypeCode;
    private final String migTypeDesc;
    private final String storageType;

    MigTypeEnum(String migTypeCode, String migTypeDesc, String storageType) {
        this.migTypeCode = migTypeCode;
        this.migTypeDesc = migTypeDesc;
        this.storageType = storageType;
    }

    public String getMigTypeCode() {
        return migTypeCode;
    }

    public String getMigTypeDesc() {
        return migTypeDesc;
    }

    public String getStorageType() {
        return storageType;
    }

    public static MigTypeEnum fromCode(String code) {
        for (MigTypeEnum type : values()) {
            if (type.getMigTypeCode().equals(code)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
