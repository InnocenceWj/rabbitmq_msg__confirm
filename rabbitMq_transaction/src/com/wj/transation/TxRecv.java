package com.wj.transation;

import com.rabbitmq.client.*;
import com.wj.transation.config.MqConfig;

import java.io.IOException;

/**
 * @创建人 wj
 * @创建时间 2018/11/5
 * @描述
 */
public class TxRecv {
    private static final String QUEUE_NAME = "test_queue_tx";

    public static void recv() throws Exception {
        Connection connection = MqConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[接收端]消息：" + new String(body, "utf-8"));
            }
        });
    }
}
