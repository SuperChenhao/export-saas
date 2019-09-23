package cn.itcast.rabbitmq.routing;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由消息生产者：消息发送到交换机
 */
public class Producer {

    // 交换机名称
    public static final String DIRECT_EXCHANGE = "direct_exchange";

    // 队列名称
    public static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    // 队列名称
    public static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args)throws Exception {
        // 1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.创建频道
        Channel channel = connection.createChannel();

        /**
         * 3.声明交换机
         * 参数1: 交换机名称
         * 参数2：交换机类型
         */
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);

        /**
         * 4.创建队列
         * 1.队列名称
         * 2.是否定义持久化队列
         * 3.是否独占本次连接
         * 4.是否在不使用的时候自动删除队列
         * 5.队列其他参数
         */
        channel.queueDeclare(DIRECT_QUEUE_INSERT,true,false,false,null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE,true,false,false,null);

        // 队列绑定交换机
        channel.queueBind(DIRECT_QUEUE_INSERT,DIRECT_EXCHANGE,"insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE,DIRECT_EXCHANGE,"update");

        // 6.发送消息
        String message = "新增了商品，路由模式；routing key 为 insert";
        channel.basicPublish(DIRECT_EXCHANGE,"insert",null,message.getBytes());
        System.out.println("已发送消息:" + message);

        message = "新增了商品，路由模式；routing key 为 update";
        channel.basicPublish(DIRECT_EXCHANGE,"update",null,message.getBytes());
        System.out.println("已发送消息:" + message);
        // 7.关闭资源
        channel.close();
        connection.close();
    }
}
