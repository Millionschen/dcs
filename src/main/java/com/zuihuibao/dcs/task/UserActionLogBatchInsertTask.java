package com.zuihuibao.dcs.task;

import com.zuihuibao.dcs.mapper.UserActionLogMapper;
import com.zuihuibao.dcs.model.UserActionLog;
import java.util.List;

public class UserActionLogBatchInsertTask implements Runnable {
  private final UserActionLogMapper mapper;
  private final List<UserActionLog> logs;

  public UserActionLogBatchInsertTask(UserActionLogMapper mapper, List<UserActionLog> logs) {
    this.mapper = mapper;
    this.logs = logs;
  }

  @Override public void run() {
    mapper.batchInsert(logs);
  }
}
