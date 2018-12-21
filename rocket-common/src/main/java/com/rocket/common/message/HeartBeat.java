package com.rocket.common.message;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:36 PM 2018/11/25
 * @Modified By:
 */
public class HeartBeat {


  private long channelId;

  private String group;

  private String topic;

  private String tag;

  private String address;

  private int crcCode;

  public long getChannelId() {
    return channelId;
  }

  public void setChannelId(long channelId) {
    this.channelId = channelId;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public int getCrcCode() {
    return crcCode;
  }

  public void setCrcCode(int crcCode) {
    this.crcCode = crcCode;
  }
}
