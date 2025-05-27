package com.ingroup.invoice_web.adapter.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 處理資料完整性違反（如 NOT NULL、UNIQUE 等）
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = getRootCause(ex);
        String message = rootCause.getMessage();

        Map<String, Object> error = new HashMap<>();
        error.put("error", "資料驗證錯誤");
        error.put("message", parsePSQLMessage(message));
        error.put("timestamp", Instant.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    // 取得最根本的錯誤原因
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

    // 可根據錯誤訊息進行處理、過濾或轉換
    private String parsePSQLMessage(String rawMessage) {
        if (rawMessage.contains("buyer_identifier")) {
            return "買方統一編號不得為空白";
        }
        return "資料格式錯誤或缺少必要欄位";
    }
}