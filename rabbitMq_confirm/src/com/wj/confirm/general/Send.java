package com.wj.confirm.general;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wj.confirm.util.ConnectionUtil;



/**
 * @创建人 wj
 * @创建时间 2018/11/5
 * @描述 普通模式
 */
public class Send {

    private static final String QUEUE_NAME="general_queue";

    public static void send() throws Exception {
        Connection connection=ConnectionUtil.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect 将channel设置为confirm模式
        channel.confirmSelect();

        String msg="hello confirm!";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if (!channel.waitForConfirms()){
            System.out.println("消息发送失败！");
        }else {
            System.out.println("消息发送成功！");
        }

    }
}
