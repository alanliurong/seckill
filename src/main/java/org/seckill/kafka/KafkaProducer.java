package org.seckill.kafka;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * 写入数据到 topic
 * @author cheng
 * @version Id: KafkaProducer.java, v 0.1 2016/9/9 14:12 cheng Exp $$
 */
public class KafkaProducer extends Thread {

    private String topic;

    public KafkaProducer(String topic) {
        this.topic = topic;
    }

    @Override
    public void run() {
        Producer producer = createProducer();
        int i = 0;
        while (true) {
            producer.send(new KeyedMessage<Integer, String>(topic, "message：" + i++));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Producer createProducer() {
        Properties properties = new Properties();
        //声明zk
        properties.put("zookeeper.connect",
            "192.168.131.128:2181,192.168.131.128:2182,192.168.131.128:2183");
        properties.put("serializer.class", StringEncoder.class.getName());
        // 声明kafka broker
        properties.put("metadata.broker.list",
            "192.168.131.128:9092,192.168.131.128:9093,192.168.131.128:9094");

        return new Producer<Integer, String>(new ProducerConfig(properties));
    }

    public static void main(String[] args) {
        new KafkaProducer("chengchenrui").start();
    }
}
