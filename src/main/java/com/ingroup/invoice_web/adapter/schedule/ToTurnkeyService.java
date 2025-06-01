package com.ingroup.invoice_web.adapter.schedule;

import com.ingroup.invoice_web.config.FileProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@Service
public class ToTurnkeyService {

    private final static Logger logger = LoggerFactory.getLogger(ToTurnkeyService.class);

    private final FileProperties fileProperties;

    public ToTurnkeyService(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Scheduled(fixedRateString = "${schedule.turnkey.relay}") // 每 5 分鐘執行一次
    public void checkAndProcessFiles() {
        String inputDir = fileProperties.getInputDownCast();
        Objects.requireNonNull(inputDir, "inputDir 未設定！");
        logger.debug("Input directory: {}", inputDir);

        File folder = new File(inputDir);


        if (!folder.exists() || !folder.isDirectory()) {
            logger.error("資料夾不存在或不是目錄:{} ", inputDir);
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".xml"));
//        if (files == null || files.length == 0) {
//            logger.debug("沒有檔案需要處理");
//            return;
//        }

        for (File file : files) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                System.out.println("解析檔案: " + file.getName());
                System.out.println("內容:\n" + content);

                //TODO: 可在這裡加入 XML 解析邏輯（如使用 JAXB / DOM parser）

                // 處理完後移動檔案

            } catch (IOException e) {
                logger.error("處理檔案失敗: {}, {}", file.getName(), e.getMessage());
            }
        }
    }
}
