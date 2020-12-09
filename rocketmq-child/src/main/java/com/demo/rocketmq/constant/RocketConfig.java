package com.demo.rocketmq.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Getter
@Setter
public class RocketConfig {

    /**
     * NameServer地址，多个地址以 ; 隔开
     */
    @Value("${com.demo.rocketmq.server.addr}")
    private String serverAddr;

    @Value("${com.demo.rocketmq.producer.group}")
    private String producerGroup;
    @Value("${com.demo.rocketmq.producer.topic}")
    private String producerTopic;
    @Value("${com.demo.rocketmq.producer.tags}")
    private String producerTags;

    @Value("${com.demo.rocketmq.consumer.group}")
    private String consumerGroup;

}
