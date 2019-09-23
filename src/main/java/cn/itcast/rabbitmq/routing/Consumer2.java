package cn.itcast.rabbitmq.routing;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) throws Exception {

        // 1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.创建频道
        Channel channel = connection.createChannel();

        // 3.创建交换机
        channel.exchangeDeclare(Producer.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 4.创建队列
        channel.queueDeclare(Producer.DIRECT_QUEUE_UPDATE,true,false,false,null);

        // 5.队列绑定到交换机
        channel.queueBind(Producer.DIRECT_QUEUE_UPDATE,Producer.DIRECT_EXCHANGE,"update");

        // 6.穿件消费者 并设置消息处理
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key为：" + envelope.getRoutingKey());
                System.out.println("交换机为：" + envelope.getExchange());
                System.out.println("消息id为：" + envelope.getDeliveryTag());
                System.out.println("接收到的消息为：" + new String(body, "utf-8"));
            }
        };

        // 7. 监听消息
        channel.basicConsume(Producer.DIRECT_QUEUE_UPDATE,true,consumer);
    }
}
