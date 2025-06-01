package com.ingroup.invoice_web.usecase.amqp;

import com.ingroup.invoice_web.config.FileProperties;
import com.ingroup.invoice_web.config.RabbitMQConfig;
import com.ingroup.invoice_web.util.constant.MigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class RabbitMQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final FileProperties fileProperties; // 資料夾路徑

    public RabbitMQConsumer(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Message message) {
        String migType = (String) message.getMessageProperties().getHeaders().get("migType");
        String xml = new String(message.getBody(), StandardCharsets.UTF_8);

        MigTypeEnum migEnum = MigTypeEnum.fromCode(migType);

        String targetPath;
        switch (migEnum.getStorageType()) {
            case "B2B":
                targetPath = "/B2BSTORAGE/" + migType;
                break;
            case "B2S":
                targetPath = "/B2SSTORAGE/" + migType;
                break;
            default:
                targetPath = "/UNKNOWN/";
                break;
        }

        try {
            String outputDir = fileProperties.getOutputUpcast() + targetPath;
            String fileName = migType + "-" + LocalDate.now() + ".xml";
            Path filePath = Paths.get(outputDir, fileName);
            Files.writeString(filePath, xml, StandardCharsets.UTF_8);
            logger.info("寫入檔案成功: {}", filePath);
        } catch (IOException e) {
            logger.error("寫檔失敗: {}", e.getMessage());
        }
    }
}
