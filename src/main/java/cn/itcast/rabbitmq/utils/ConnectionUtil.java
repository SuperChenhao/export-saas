package cn.itcast.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws Exception{

        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 主机地址
        connectionFactory.setHost("localhost");
        // 连接端口
        connectionFactory.setPort(5672);
        // 虚拟主机名称
        connectionFactory.setVirtualHost("/itcast");
        // 连接用户名
        connectionFactory.setUsername("admin");
        // 连接密码
        connectionFactory.setPassword("admin");
        // 创建连接

        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
