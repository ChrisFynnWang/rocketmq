import com.wangfan.mq.rocketmq.GroupEnum;
import com.wangfan.mq.rocketmq.TopicEnum;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @Author : Chris Wang
 * @Description :
 * @Date : 2019/6/18 10:32
 */
public class RocketTest {

    @Test
    public void producer() throws Exception {
        //创建一个消息生产者，并设置一个消息生产者组
        DefaultMQProducer producer = new DefaultMQProducer(GroupEnum.COMMANDER.toString());

        //指定 NameServer 地址
        producer.setNamesrvAddr("localhost:9876");

        //初始化 Producer，整个应用生命周期内只需要初始化一次
        producer.start();

        for (int i = 0; i < 100; i++) {
            //创建一条消息对象，指定其主题、标签和消息内容
            Message msg = new Message(
                    TopicEnum.COMMAND.toString()/* 消息主题名 */,
                    "TagA" /* 消息标签 */,
                    ("Hello Java demo RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* 消息内容 */
            );

            //发送消息并返回结果
            SendResult sendResult = producer.send(msg);

            System.out.printf("%s%n", sendResult);
        }

        // 一旦生产者实例不再被使用则将其关闭，包括清理资源，关闭网络连接等
        producer.shutdown();
    }

    @Test
    public void consumer() throws Exception {
        //创建一个消息消费者，并设置一个消息消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("niwei_consumer_group");
        //指定 NameServer 地址
        consumer.setNamesrvAddr("localhost:9876");
        //设置 Consumer 第一次启动时从队列头部开始消费还是队列尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //订阅指定 Topic 下的所有消息
        consumer.subscribe("topic_example_java", "*");
        //注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                //默认 list 里只有一条消息，可以通过设置参数来批量接收消息
                if (list != null) {
                    for (MessageExt ext : list) {
                        try {
                            System.out.println(new Date() + new String(ext.getBody(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 消费者对象在使用之前必须要调用 start 初始化
        consumer.start();
        System.out.println("消息消费者已启动");
    }
}
