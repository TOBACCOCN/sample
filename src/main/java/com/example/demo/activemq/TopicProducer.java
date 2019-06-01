package com.example.demo.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicProducer {
	public static void main(String[] args) {
		try {
			String url = "tcp://localhost:61616";
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
			Connection connection = activeMQConnectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			String topicName = "topic_test";
			Topic topic = session.createTopic(topicName);
			MessageProducer producer = session.createProducer(topic);
			for (int i = 0; i < 100; i++) {
				TextMessage textMessage = session.createTextMessage("test" + i);
				producer.send(textMessage);
			}
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
