package cn.itcast.rabbitmq.simple;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消息消费者
 * 消息模式 简单模式: 一个生产者 一个消费者 不需要设置交换机
 */
public class Consumer {

    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明创建队列
        channel.queueDeclare(Producer.QUEUE_NAME,true,false,false,null);

        // 创建消费者 并设置消息处理
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            /**
         * consumerTag  消费者标签 在channel.basicConsume时候可以指定
         * envelope 消息包的内容 可从中获取消息id
         * 消息routingKey 交换机 消息和重传标志(收到消息失败后是否需要重新发送)
         * properties 属性信息
         * body  消息
         */
        public void handleDelivery(String consumerTag , Envelope envelope,
                AMQP.BasicProperties properties,byte[] body)throws IOException{

            // 路由key
            System.out.println("路由key为:" + envelope.getRoutingKey());

            // 交换机
            System.out.println("交换机为" + envelope.getExchange());

            // 消息id
            System.out.println("消息id为:" + envelope.getDeliveryTag());

            // 收到的消息
            System.out.println("接受到的消息为:" + new String(body,"utf-8"));
        }

    };

        // 监听消息
        /**
         * 参数1: 队列名称
         * 参数2: 是否自动确认 设置为true为表示消息接收到自动想mq回复接受到了
         * mq接收到回复会删除消息 设置为false则需要手动修改
         * 参数3: 消息接收到后回调
         *
         */
        channel.basicConsume(Producer.QUEUE_NAME, true, consumer);
        //不关闭资源，应该一直监听消息


    }
}
