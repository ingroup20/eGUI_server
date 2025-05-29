package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.exception.TryLockOverTimeException;
import com.ingroup.invoice_web.model.entity.Company;
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
        String keyWorld;
        switch (sourceMigType) {
            case "A0401":
            case "F0401":
                keyWorld = INVOICE_KEY_WORLD;
                break;
            case "B0401":
            case "G0401":
                keyWorld = ALLOWANCE_KEY_WORLD;
                break;
            default:
                keyWorld = INVOICE_KEY_WORLD;
        }
        return keyWorld + company.getIdentifier() + orderNumber;

    }

    //接queue才處理
    public void issInvoiceNumberLock(String invoiceNumber, Company company) {
        RLock lock = redissonClient.getLock(INVOICE_KEY_WORLD + company.getIdentifier() + invoiceNumber);

        boolean locked = false;
        try {
            locked = lock.tryLock(10, TimeUnit.SECONDS); //嘗試在10秒內取得鎖
            if (locked) {
                logger.info("Lock acquired, key = {}", lock.getName());
                //取回復檔 才解鎖
            } else {
                logger.warn("Lock not acquired, key = {}", lock.getName());
                throw new TryLockOverTimeException("Lock not acquired, key = " + invoiceNumber);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重設中斷狀態
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public void issAllowanceNumberLock(String allowanceNumber, Company company) {
        RLock lock = redissonClient.getLock(ALLOWANCE_KEY_WORLD + company.getIdentifier() + allowanceNumber);

        boolean locked = false;
        try {
            locked = lock.tryLock(10, TimeUnit.SECONDS); //嘗試在10秒內取得鎖
            if (locked) {
                logger.info("Lock acquired, key = {}", lock.getName());
                //取回復檔 才解鎖
            } else {
                logger.warn("Lock not acquired, key = {}", lock.getName());
                throw new TryLockOverTimeException("Lock not acquired, key = " + lock.getName());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重設中斷狀態
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
