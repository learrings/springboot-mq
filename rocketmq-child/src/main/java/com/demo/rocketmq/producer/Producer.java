package com.demo.rocketmq.producer;

import com.demo.rocketmq.constant.RocketConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Description:生产者
 */
@Component
@Order(2)
public class Producer {

    @Resource
    private RocketConfig rocketConfig;

    private DefaultMQProducer producer;

    /**
     * 构造方法后立即执行并只执行一次
     */
    @PostConstruct
    public void buildMQProducer() {
        producer = new DefaultMQProducer(rocketConfig.getProducerGroup());
        producer.setNamesrvAddr(rocketConfig.getServerAddr());
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            System.out.println("-------->:producer启动了");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param topic - 主题
     * @param tags  - 标签分组
     * @param body  - 消息
     */
    public String send(String topic, String tags, String body) throws Exception {
        Message message = new Message(topic, tags, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(message);
        System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
        return "{\"MsgId\":\"" + result.getMsgId() + "\"}";
    }

}
