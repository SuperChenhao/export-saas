package cn.itcast.rabbitmq.work;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer1 {
    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args)throws Exception {

        // 创建连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建频道
        Channel channel = connection.createChannel();
        // 创建队列
        channel.queueDeclare(Producer.QUEUE_NAME,true,false,false,null);

        // 创建消费者 并设置消息处理
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key为:" + envelope.getRoutingKey());
                System.out.println("交换机为:" + envelope.getExchange());
                System.out.println("消息id为:" + envelope.getDeliveryTag());
                System.out.println("接受到的消息为:" + new String(body,"utf-8"));
            }
        };

        // 监听消息
        channel.basicConsume(Producer.QUEUE_NAME,true,consumer);
    }
}
