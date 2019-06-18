import com.alibaba.fastjson.JSON;
import com.wangfan.mq.rocketmq.SpringbootApplication;
import com.wangfan.mq.rocketmq.TopicEnum;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;


/**
 * @Author : Chris Wang
 * @Description :
 * @Date : 2019/6/18 10:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class MqTest {

    @Autowired
    private DefaultMQProducer producer;

    @Test
    public void productMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        Message message = new Message(TopicEnum.COMMAND.toString(), "ice".getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult send = producer.send(message);
        System.out.println(JSON.toJSONString(send));
    }
}
