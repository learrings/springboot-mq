package com.demo.rocketmq.controller;

import com.demo.rocketmq.constant.RocketConfig;
import com.demo.rocketmq.producer.Producer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private RocketConfig rocketConfig;
    @Resource
    private Producer producer;

    /**
     * localhost:8080/push?msg=aaaa
     *
     * @param msg -
     */
    @RequestMapping("/push")
    public String pushMsg(String msg) {
        try {
            return producer.send(rocketConfig.getProducerTopic(), rocketConfig.getProducerTags(), msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
