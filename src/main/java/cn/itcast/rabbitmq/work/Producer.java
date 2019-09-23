package cn.itcast.rabbitmq.work;

import cn.itcast.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消息生产者
 */
public class Producer {

    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args)throws Exception {

        // 创建连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建平道
        Channel channel = connection.createChannel();
        // 声明创建队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        for (int i = 1; i <= 30 ; i++) {

            // 要发送的信息
            String message = "你好，小兔子" + i;
            /**
             * 参数1: 交换机名称 如果没有指定则默认defaultExchage
             * 参数2: 路由key，简单模式可以传递队列名称
             * 参数3：其他属性
             * 参数4：消息内容
             */
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("已发送消息:" + message);

        }

        // 关闭资源
        channel.close();
        connection.close();
    }
}


