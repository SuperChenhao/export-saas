package cn.itcast.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *   消费生产者: 发送消息到队列
 *   消息模式: 简单模式: 一个生产者 一个消费者
 */
public class Producer {

    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 设置连接参数 主机地址默认为localhost
        connectionFactory.setHost("localhost");
        // 连接端口
        connectionFactory.setPort(5672);
        // 虚拟主机名称: 默认为/
        connectionFactory.setVirtualHost("/itcast");

        // 连接用户名
        connectionFactory.setUsername("admin");
        // 连接密码
        connectionFactory.setPassword("admin");

        // 2. 创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 创建频道
        Channel channel = connection.createChannel();

        /**
         * 4.声明创建队列
         * 参数1: 队列名称
         * 参数2: 是否定义持久化队列
         * 参数3: 是否独占本次连接
         * 参数4: 是否在不使用的时候自动删除队列
         * 参数5: 队列其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        // 5. 要发送的信息
        String message = "你好；小兔子!";
        /**
         * 参数1: 交换机名称，如果没有指定则使用默认
         * 参数2: 路由key 简单模式可以传递队列名称
         * 参数3: 消息其他属性
         * 参数4: 消息内容
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("已发送消息:" + message);

        // 6.关闭资源
        channel.close();
        connection.close();



    }
}
