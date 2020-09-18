package com.demo.mq;

import com.rabbitmq.client.*;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static com.demo.mq.rabbitMQ.NORMAL;

public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("secshop");
        factory.setHost("192.168.1.194");
        factory.setPort(5672);
        factory.setUsername("secshop");
        factory.setPassword("secshop");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(NORMAL, true, false, false, null);
        DefaultConsumer com = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                String reply = properties.getReplyTo();
                String corrId = properties.getCorrelationId();
                System.out.println("reply: "+reply);
                System.out.println("corrId: "+corrId);
                long tag = envelope.getDeliveryTag();
                if (1==1){
                    System.out.println("get it!  " + message);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.basicPublish("",reply,null,"处理成功了".getBytes());
                    channel.basicAck(tag,false);
                }else{
                    channel.basicReject(tag,true);
                }
            }

        };
        channel.basicConsume(NORMAL, false, com);
//        channel.close();
//        conn.close();

    }
}
