package com.wj.confirm.batch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wj.confirm.util.ConnectionUtil;


/**
 * @创建人 wj
 * @创建时间 2018/11/5
 * @描述 批量模式
 */
public class Send {

    private static final String QUEUE_NAME = "batch_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect 将channel设置为confirm模式
        channel.confirmSelect();

        //        批量
        for (int i = 0; i < 10; i++) {
            String msg = "hello confirm! [" + i + "]";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        //确认
        if (!channel.waitForConfirms()) {
            System.out.println("消息发送失败！");
        } else {
            System.out.println("消息发送成功！");
        }

    }
}
