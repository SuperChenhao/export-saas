package cn.itcast.rabbitmq.topic;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

    // 交换机名称
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    // 队列名称
    public static final String TOPIC_QUEUE_1 = "topic_queue_1";
    // 队列名称
    public static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws Exception {

        // 1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.创建频道
        Channel channel = connection.createChannel();

        // 3.创建交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        // 发送消息
        String message = "新增了商品，Topic模式，路由key为item.insert";

        channel.basicPublish(TOPIC_EXCHANGE,"item.insert",null,message.getBytes());
        System.out.println("已发送消息：" + message);


        message = "修改了商品，Topic模式，路由key为item.update";
        channel.basicPublish(TOPIC_EXCHANGE,"item.update",null,message.getBytes());
        System.out.println("已发送消息：" + message);

        message = "删除了商品，Topic模式，路由key为item.delete";
        channel.basicPublish(TOPIC_EXCHANGE,"item.delete",null,message.getBytes());
        System.out.println("已发送消息：" + message);

        // 7.关闭资源
        channel.close();
        connection.close();
    }

}
