package com.wj.transation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wj.transation.config.MqConfig;

/**
 * @创建人 wj
 * @创建时间 2018/11/5
 * @描述 用事务模式，进行消息确认机制
 */
public class TxSend {

    private static final String QUEUE_NAME = "test_queue_tx";

    public static void send() throws Exception {
        Connection connection = MqConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello tx!";
        try {
            // 用户将当前channel设置成transaction
            channel.txSelect();

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

//            int x=1/0;


            System.out.println("【发送端】消息："+msg);
            //提交事务
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("发生异常，回滚");
        }
        channel.close();
        connection.close();
    }
}
