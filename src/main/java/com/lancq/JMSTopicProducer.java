package com.lancq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author lancq
 * @Description 消息发布
 * @Date 2018/7/6
 **/
public class JMSTopicProducer {
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
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            //创建目的地
            Destination destination = session.createTopic("MyTopic");

            //创建生产者
            MessageProducer producer = session.createProducer(destination);

            producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化消息

            //创建消息
            TextMessage message = session.createTextMessage("全体员工，明天早上9点开会!");

            //发送消息
            producer.send(message);

            session.commit();//消息提交

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
