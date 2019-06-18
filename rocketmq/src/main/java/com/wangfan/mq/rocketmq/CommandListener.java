package com.wangfan.mq.rocketmq;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author : Chris Wang
 * @Description :
 * @Date : 2019/6/18 10:47
 */
@Component
public class CommandListener implements MessageListenerConcurrently {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt ext : list) {
            try {
                LOGGER.info(JSON.toJSONString(new String(ext.getBody(), RemotingHelper.DEFAULT_CHARSET)));
            } catch (UnsupportedEncodingException e) {
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
