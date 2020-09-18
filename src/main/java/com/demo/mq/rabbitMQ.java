package com.demo.mq;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import com.rabbitmq.client.impl.AMQChannel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class rabbitMQ {
    protected final static String NORMAL = "normal";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("secshop");
        factory.setPassword("secshop");
        factory.setHost("192.168.1.194");
        factory.setPort(5672);
        factory.setVirtualHost("secshop");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String message = "I am comming now!";



        AMQP.BasicProperties props = new AMQP.BasicProperties().builder().replyTo("call.back").correlationId("call me back!").build();

        channel.basicPublish("",NORMAL,props,message.getBytes());
        channel.confirmSelect();

//        channel.addReturnListener(new ReturnListener() {
//            @Override
//            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
//
//            }
//        });

        boolean ret = channel.waitForConfirms();
        channel.waitForConfirmsOrDie();
        System.out.println(ret);
       if (ret){

           DefaultConsumer callBack = new DefaultConsumer(channel){
               @Override
               public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                   String message = new String(body, StandardCharsets.UTF_8);
                   System.out.println(message);
               }
           };
            channel.queueDeclare("call.back", true, false, false, null);
        channel.basicConsume("call.back",true,callBack);
//           channel.close();
//           conn.close();
       }
//        channel.addConfirmListener(new ConfirmListener() {
//            @Override
//            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
//               // 正常
//                System.out.println("ok了");
//                try {
//                    channel.close();
//                } catch (TimeoutException e) {
//                    e.printStackTrace();
//                }
//                conn.close();
//            }
//
//            @Override
//            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
//                //异常
//                System.out.println("异常了");
//                try {
//                    channel.close();
//                } catch (TimeoutException e) {
//                    e.printStackTrace();
//                }
//                conn.close();
//            }
//        });


    }
}
