package com.demo.rocketmq.consumer;

import com.demo.rocketmq.constant.RocketConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.boot.CommandLineRunner;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:消费者
 */
@Component
@Order(3)
public class Consumer implements CommandLineRunner {

    @Resource
    private RocketConfig rocketConfig;

    @Override
    public void run(String... args) {
        this.messageListener();
    }

    /**
     * 初始化RocketMq的监听信息，渠道信息
     */
    public void messageListener() {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketConfig.getConsumerGroup());
        consumer.setNamesrvAddr(rocketConfig.getServerAddr());
        try {

            // 订阅消息
            consumer.subscribe(rocketConfig.getProducerTopic(), rocketConfig.getProducerTags());

            // 程序第一次启动从消息队列头获取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //可以修改每次消费消息的数量，默认设置是每次消费一条
            consumer.setConsumeMessageBatchMaxSize(1);

            //在此监听中消费信息，并返回消费的状态信息
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                System.out.println("接收到了消息：" + new String(msgs.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

            consumer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
