package com.wj.confirm.asynchronization;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.wj.confirm.util.ConnectionUtil;

import java.io.IOException;
import java.util.*;


/**
 * @创建人 wj
 * @创建时间 2018/11/5
 * @描述 批量模式
 */
public class Send {

    private static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用confirmSelect 将channel设置为confirm模式
        channel.confirmSelect();
        //        未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        //        通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //            没有问题的handleAck
            @Override
            public void handleAck(long deliverTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleNack----multiple");
                    confirmSet.headSet(deliverTag + 1).clear();
                } else {
                    System.out.println("false");
                    confirmSet.remove(deliverTag);
                }
            }

            @Override
            public void handleNack(long deliverTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleNack----multiple");
                    confirmSet.headSet(deliverTag + 1).clear();
                } else {
                    System.out.println("false");
                    confirmSet.remove(deliverTag);
                }
            }
        });


        String msg = "hello confirm——ack! ";
        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            confirmSet.add(seqNo);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
    }
}
