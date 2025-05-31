package com.ingroup.invoice_web.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "web-xml-queue";
    public static final String EXCHANGE_NAME = "web-xml-exchange";
    public static final String ROUTING_KEY = "web.toTurnkey";

    public static final String DLX_EXCHANGE_NAME = "dlx-exchange";
    public static final String DLX_ROUTING_KEY = "dlx-routing-key";

    // 註冊 Queue 並加入 Dead Letter Exchange 設定
    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable(QUEUE_NAME) // 設為持久化
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DLX_ROUTING_KEY)
                .build();
    }

    // 註冊主 Exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 綁定 Queue 到 Exchange 並指定 Routing Key
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // 可選：建立 DLX（死信交換器）本身（假設你會用 default direct）
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }

    // 可選：建立 DLX Queue（用來接收錯誤訊息）
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dlx-queue").build();
    }

    // 可選：綁定 DLX Queue
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLX_ROUTING_KEY);
    }
}