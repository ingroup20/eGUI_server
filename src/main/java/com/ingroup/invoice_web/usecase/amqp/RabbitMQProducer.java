package com.ingroup.invoice_web.usecase.amqp;

import com.ingroup.invoice_web.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendXmlToTurnkeyMessageRelay(String xmlContent, String migType) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/xml");
        messageProperties.setHeader("migType", migType);
        Message message = new Message(xmlContent.getBytes(StandardCharsets.UTF_8), messageProperties);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }
}
