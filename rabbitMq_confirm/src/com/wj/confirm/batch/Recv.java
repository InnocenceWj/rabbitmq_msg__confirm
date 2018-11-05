package com.wj.confirm.batch;

import com.rabbitmq.client.*;
import com.wj.confirm.util.ConnectionUtil;

import java.io.IOException;

/**
 * @创建人 wj
 * @创建时间 2018/11/5
 * @描述
 */
public class Recv {

    private static final String QUEUE_NAME = "batch_queue";


    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[接收端]消息："+new String(body,"utf-8"));
            }
        });
    }


}
