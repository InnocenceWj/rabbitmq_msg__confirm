package com.wj.transation.config;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @创建人 wj
 * @创建时间 2018/11/2
 * @描述
 */
public class MqConfig {
    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection= factory.newConnection();
        return connection;
    }

}
