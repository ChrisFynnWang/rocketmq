package com.wangfan.mq.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : Chris Wang
 * @Description :
 * @Date : 2019/6/18 10:39
 */
@Configuration
public class ConsumerConfig {

    @Bean(name = "commandConsumer", initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer getConsumer(CommandListener listener) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GroupEnum.MUSKETEER.toString());
        consumer.setNamesrvAddr(Constants.NAME_SERVER);
        consumer.subscribe(TopicEnum.COMMAND.toString(), "*");
        consumer.registerMessageListener(listener);
        consumer.setMaxReconsumeTimes(1);
        return consumer;
    }
}
