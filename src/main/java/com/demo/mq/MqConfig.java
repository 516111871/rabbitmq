package com.demo.mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MqConfig {
    private static MqConfig mqConfig;
    private  ConnectionFactory factory;

    public static MqConfig getMqConfige() throws IOException, TimeoutException {
        if (mqConfig == null){
            synchronized (MqConfig.class) {
                if (mqConfig == null){
                    mqConfig = new MqConfig();
                    mqConfig.init();
                }
            }
        }
        return mqConfig;
    }

    private void init() throws IOException, TimeoutException {
        if (factory == null){
            factory = new ConnectionFactory();
            factory.setPassword("secshop");
            factory.setUsername("secshop");
            factory.setPort(5672);
            factory.setVirtualHost("secshop");
            factory.setHost("192.168.1.194");

        }


    }

    public  ConnectionFactory getFactory() {
        return factory;
    }
}
