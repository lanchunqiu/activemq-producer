package com.lancq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author lancq
 * @Description
 * @Date 2018/7/6
 **/
public class PrefetchSizeQueueProducer {
    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.227.129:61616");

        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            //开启连接
            connection.start();

            //创建会话
            //Boolean.TRUE表时开启事务会话，后面需要调用commit提交事务；Boolean.FALSE开启非事务会话，后面不需要使用commit
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            //创建目的地
            Destination destination = session.createQueue("MyQueue");
            //创建生产者
            MessageProducer producer = session.createProducer(destination);
            //producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            for(int i=0; i<500; i++){

                //发送消息
                //创建消息,消息类型有text、bytes、map、object、stream
                TextMessage message = session.createTextMessage("Hello Word!" + (i+1));
                producer.send(message);

            }
            //session.commit();//消息提交
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
