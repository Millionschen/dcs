package com.zuihuibao.dcs.mapper;

import com.zuihuibao.dcs.model.UserActionLog;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActionLogMapper {
    void batchInsert(List<UserActionLog> logs);
}