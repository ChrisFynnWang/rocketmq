package com.wangfan.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : Chris Wang
 * @Description :
 * @Date : 2019/6/18 10:28
 */
@Configuration
public class ProducerConfig {

    @Bean(name = "producer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQProducer getProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(GroupEnum.COMMANDER.toString());
        producer.setNamesrvAddr(Constants.NAME_SERVER);
        return producer;
    }
}
