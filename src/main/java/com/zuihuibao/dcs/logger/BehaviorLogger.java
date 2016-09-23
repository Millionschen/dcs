package com.zuihuibao.dcs.logger;

import java.util.List;

/**
 * Created by millions on 16/9/21.
 * 记录统计数据的接口
 */
public interface BehaviorLogger {
  void log(List<String> contents);
}
