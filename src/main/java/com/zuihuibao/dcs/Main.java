package com.zuihuibao.dcs;

import com.zuihuibao.dcs.receiver.MessageReceiver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by millions on 16/9/21.
 * 入口 作为kafka consumer 记录用户行为
 */
public class Main {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("参数错误，请输入topic名");
      System.exit(-1);
    }
    String topic = args[0];
    String beanXml = "context/spring-beans.xml";
    ApplicationContext ctx = new ClassPathXmlApplicationContext(beanXml);
    MessageReceiver receiver = ctx.getBean("receiver", MessageReceiver.class);
    receiver.receive(topic, ctx);
  }
}
