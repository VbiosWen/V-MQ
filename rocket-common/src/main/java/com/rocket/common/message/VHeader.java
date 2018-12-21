package com.rocket.common.message;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:24 PM 2018/12/20
 * @Modified By:
 */
public class VHeader {

  private int headPrefix;

  private int length;

  private long sessionID;

  private byte type;

  private byte priority;

  public int getHeadPrefix() {
    return headPrefix;
  }

  public void setHeadPrefix(int headPrefix) {
    this.headPrefix = headPrefix;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public long getSessionID() {
    return sessionID;
  }

  public void setSessionID(long sessionID) {
    this.sessionID = sessionID;
  }

  public byte getType() {
    return type;
  }

  public void setType(byte type) {
    this.type = type;
  }

  public byte getPriority() {
    return priority;
  }

  public void setPriority(byte priority) {
    this.priority = priority;
  }
}
