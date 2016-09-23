package com.zuihuibao.dcs.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

public class UserActionLog {

  @JSONField(name = "id")
  private Long id;

  @JSONField(name = "user_id")
  private Long userId;

  @JSONField(name = "event_code")
  private String eventCode;

  @JSONField(name = "event_id")
  private String eventId;

  @JSONField(name = "event_time")
  private Date eventTime;

  @JSONField(name = "relate_user_id")
  private Long relateUserId;

  @JSONField(name = "channel_id")
  private String channelId;

  @JSONField(name = "ep1")
  private String ep1;

  @JSONField(name = "ep2")
  private String ep2;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getEventCode() {
    return eventCode;
  }

  public void setEventCode(String eventCode) {
    this.eventCode = eventCode;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public Date getEventTime() {
    return eventTime;
  }

  public void setEventTime(Date eventTime) {
    this.eventTime = eventTime;
  }

  public Long getRelateUserId() {
    return relateUserId;
  }

  public void setRelateUserId(Long relateUserId) {
    this.relateUserId = relateUserId;
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public String getEp1() {
    return ep1;
  }

  public void setEp1(String ep1) {
    this.ep1 = ep1;
  }

  public String getEp2() {
    return ep2;
  }

  public void setEp2(String ep2) {
    this.ep2 = ep2;
  }
}