package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.exception.runtime.TryLockOverTimeException;
import com.ingroup.invoice_web.model.entity.Company;
import com.ingroup.invoice_web.util.constant.MigTypeEnum;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLockService {

    private final Logger logger = LoggerFactory.getLogger(RedisLockService.class);

    private final RedissonClient redissonClient;

    private final String INVOICE_KEY_WORLD = "i:";
    private final String ALLOWANCE_KEY_WORLD = "a:";

    public RedisLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    public boolean checkLockKeyExists(String orderNumber, Company company, String sourceMigType) {
        RLock lock = redissonClient.getLock(getLockKey(orderNumber, company, sourceMigType));
        return lock.isLocked();
    }

    public String getLockKey(String orderNumber, Company company, String sourceMigType) {
        String keyWorld = switch (sourceMigType) {
            case "A0401", "F0401" -> INVOICE_KEY_WORLD;
            case "B0401", "G0401" -> ALLOWANCE_KEY_WORLD;
            default -> INVOICE_KEY_WORLD;
        };
        return keyWorld + company.getIdentifier() + orderNumber;

    }

    //接queue才處理
    public void acquireInvoiceLock(String invoiceNumber, Company company) {
        String lockKey = INVOICE_KEY_WORLD + company.getIdentifier() + invoiceNumber;
        RLock lock = redissonClient.getLock(lockKey);

        boolean locked = false;
        try {
            locked = lock.tryLock(10, TimeUnit.SECONDS); //嘗試在10秒內取得鎖
            if (!locked) {
                logger.warn("Lock not acquired, key = {}", lock.getName());
                throw new TryLockOverTimeException("Lock not acquired, key = " + invoiceNumber);
            }

        } catch (InterruptedException e) {
            lock.unlock();
            Thread.currentThread().interrupt(); // 重設中斷狀態
            throw new RuntimeException("Thread interrupted during locking", e);
        }
    }

    public void acquireAllowanceLock(String allowanceNumber, Company company) {
        String lockKey = ALLOWANCE_KEY_WORLD + company.getIdentifier() + allowanceNumber;
        RLock lock = redissonClient.getLock(lockKey);

        boolean locked = false;
        try {
            locked = lock.tryLock(10, TimeUnit.SECONDS); //嘗試在10秒內取得鎖
            if (!locked) {
                logger.warn("Lock not acquired, key = {}", lock.getName());
                throw new TryLockOverTimeException("Lock not acquired, key = " + lock.getName());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重設中斷狀態
            lock.unlock();
            throw new RuntimeException("Thread interrupted during locking", e);
        }
    }

    public void releaseLock(MigTypeEnum migType, String olderNumber, String companyIdentifier) {
        String keyWorld = switch (migType) {
            case ISSUE_EXCHANGE_INVOICE, ISSUE_EVIDENCE_INVOICE -> INVOICE_KEY_WORLD;
            case ISSIE_EXCHANGE_ALLOWANCE, ISSUE_EVIDENCE_ALLOWANCE -> ALLOWANCE_KEY_WORLD;
            default -> "";
        };

        String lockKey = keyWorld + companyIdentifier + olderNumber;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.isLocked()) {
                lock.unlock();
                logger.info("Lock released: {}", lockKey);
            } else {
                logger.warn("Lock already released or expired: {}", lockKey);
            }
        } catch (IllegalMonitorStateException e) {
            // 若不是當前 thread 持有，會丟出錯誤（可略過或特別處理）
            logger.warn("Cannot unlock lock not held by current thread: {}", lockKey);
        }
    }


}
