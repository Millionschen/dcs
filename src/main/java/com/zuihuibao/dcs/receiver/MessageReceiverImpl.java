package com.zuihuibao.dcs.receiver;

import com.zuihuibao.dcs.logger.BehaviorLogger;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class MessageReceiverImpl implements MessageReceiver {
  private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

  private Properties kafkaConsumerProps;
  private int periodInSeconds;
  private int kafkaPollTime;

  public MessageReceiverImpl(Properties kafkaConsumerProps, int periodInSeconds, int kafkaPollTime) {
    this.kafkaConsumerProps = kafkaConsumerProps;
    this.periodInSeconds = periodInSeconds;
    this.kafkaPollTime = kafkaPollTime;
  }

  @Override public void receive(String topic, ApplicationContext ctx) {
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaConsumerProps);
    consumer.subscribe(Collections.singletonList(topic));

    try {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(periodInSeconds);
          logger.info("poll for records");
          ConsumerRecords<String, String> records = consumer.poll(kafkaPollTime);
          Map<String, List<String>> behaviorMap = new HashMap<>();
          for (ConsumerRecord<String, String> record : records) {
            logger.info("got record:" + record);
            behaviorMap.putIfAbsent(record.key(), new LinkedList<>());
            behaviorMap.get(record.key()).add(record.value());
          }
          for (String key : behaviorMap.keySet()) {
            String beanName = key + "Logger";
            try {
              BehaviorLogger behaviorLogger = ctx.getBean(beanName, BehaviorLogger.class);
              behaviorLogger.log(behaviorMap.get(key));
            } catch (BeansException e) {
              logger.warn("bean not found:" + beanName);
            }
          }
        } catch (InterruptedException e) {
          logger.warn("===== interrupted =====");
        }
      }
    } catch (Throwable e) {
      logger.error("program exit cause by: " + e.getCause());
    } finally {
      consumer.close();
    }
  }
}
