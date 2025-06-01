package com.ingroup.invoice_web.util.constant;

public enum UploadStatusEnum {

    FINISH("G", "上傳處理成功"),
    SERVER_CATCH("C", "伺服器確認"),
    IN_PROCESS("P", "處理中"),
    ERROR("E", "發生錯誤"),
    INTERRUPT("I", "中斷");

    private final String statusCode;
    private final String statusMessage;

    UploadStatusEnum(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

}
