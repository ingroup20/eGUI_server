package com.ingroup.invoice_web.usecase.service;

import com.ingroup.invoice_web.exception.TryLockOverTimeException;
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

    public RedisLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public boolean checkLockKeyExists(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }

    //接queue才處理
    public void issInvoiceNumberLock(String invoiceNumber) {
        RLock lock = redissonClient.getLock(invoiceNumber);

        boolean locked = false;
        try {
            locked = lock.tryLock(10, TimeUnit.SECONDS); //嘗試在10秒內取得鎖
            if (locked) {
                logger.info("Lock acquired, key = {}", invoiceNumber);
                //取回復檔 才解鎖
            } else {
                logger.warn("Lock not acquired, key = {}", invoiceNumber);
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
}
