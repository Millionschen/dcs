package com.zuihuibao.dcs.logger;

import com.alibaba.fastjson.JSON;
import com.zuihuibao.dcs.mapper.UserActionLogMapper;
import com.zuihuibao.dcs.model.UserActionLog;
import com.zuihuibao.dcs.task.UserActionLogBatchInsertTask;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by millions on 16/9/23.
 * 记录用户行为
 */

@Component(value = "UserActionLogger")
public class UserActionLogger implements BehaviorLogger {
  private static final Logger logger = LoggerFactory.getLogger(UserActionLogger.class);

  private final ThreadPoolTaskExecutor taskExecutor;

  private final UserActionLogMapper mapper;

  @Autowired
  public UserActionLogger(ThreadPoolTaskExecutor taskExecutor, UserActionLogMapper mapper) {
    this.taskExecutor = taskExecutor;
    this.mapper = mapper;
  }

  @Override public void log(List<String> contents) {
    if (null == contents || 0 == contents.size()) {
      return;
    }
    List<UserActionLog> logs = parseLogs(contents);
    if (null != logs && logs.size() > 0) {
      taskExecutor.execute(new UserActionLogBatchInsertTask(mapper, logs));
    }
  }

  private List<UserActionLog> parseLogs(List<String> contents) {
    List<UserActionLog> logs = new ArrayList<>(contents.size());
    for (String content : contents) {
      logger.info("to be log content : {}", content);
      UserActionLog userActionLog = JSON.parseObject(content, UserActionLog.class);
      if (null != userActionLog) {
        logs.add(userActionLog);
      }
    }
    return logs;
  }
}
