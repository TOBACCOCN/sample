package com.example.sample.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQProducer {

    public static void main(String[] args) throws JMSException {
        String url = "tcp://localhost:61616";
        Session session = getSession(url);

        String topicName = "topic_test";
        generateProducer(session, session.createTopic(topicName));

        String queueName = "queue_test";
        generateProducer(session, session.createQueue(queueName));
    }

    private static Session getSession(String url) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private static void generateProducer(Session session, Destination destination) throws JMSException {
        MessageProducer producer = session.createProducer(destination);
        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("test" + i);
            producer.send(textMessage);
        }
    }
}
