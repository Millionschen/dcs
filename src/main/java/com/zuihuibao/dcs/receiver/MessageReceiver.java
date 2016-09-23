package com.zuihuibao.dcs.receiver;

import org.springframework.context.ApplicationContext;

/**
 * Created by millions on 16/9/21.
 */
public interface MessageReceiver {
  void receive(String topic, ApplicationContext ctx);
}
