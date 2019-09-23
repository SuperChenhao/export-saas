package cn.itcast.rabbitmq.ps;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消息生产者: 发布订阅模式
 * 发布订阅模式: Publish/subscribe
 *  1.需要设置类型为fanout的交换机
 *  2.并且交换机和队列进行绑定，当发送消息到交换机后，交换机会把消息发送到绑定的队列
 *
 */
public class Producer {

    // 交换机名称
    public static final String FANOUT_EXCHANGE = "fanout_exchange";

    public static final String FANOUT_QUEUE_1 = "fanout_queue_1";

    public static final String FANOUT_QUEUE_2 = "fanout_queue_2";

    public static void main(String[] args)throws Exception {

        // 1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        // 2.创建频道
        Channel channel = connection.createChannel();
        /**
         * 3.声明交换机
         * 参数1：交换机名称
         * 参数2: 交换机类型:fanout,direct,topic,headers
         *
         * */
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        /**
         * 4.声明创建队列
         * 参数1: 队列名称
         * 参数2：是否定义持久化队列
         * 参数3: 是否独占本次连接
         * 参数4: 是否在不使用的时候自动删除队列
         * 参数5: 队列其他参数
         */
        channel.queueDeclare(FANOUT_QUEUE_1,true,false,false,null);
        channel.queueDeclare(FANOUT_QUEUE_2,true,false,false,null);

        // 5.队列绑定交换机
        channel.queueBind(FANOUT_QUEUE_1,FANOUT_EXCHANGE,"");
        channel.queueBind(FANOUT_QUEUE_2,FANOUT_EXCHANGE,"");

        // 6.发送多个消息
        for (int i = 1; i <= 10 ; i++) {
            // 要发送的消息
            String message = "你好小兔子!" + i;
            /**
             * 参数1: 交换机名称 如果没有指定则使用默认DeafultExchange
             * 参数2: 路由key 简单模式可以传递队列名称
             * 参数3: 消息其他属性
             * 参数4: 消息内容
             */
            channel.basicPublish(FANOUT_EXCHANGE,"",null,message.getBytes());
            System.out.println("已发送消息:" + message);

        }
        // 7. 关闭资源
        channel.close();
        connection.close();


    }


}
