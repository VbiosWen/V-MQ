package com.mq.message;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 12:59 PM 2018/12/22
 * @Modified By:
 */
public class VBrokerRegisterMessage {

  private String brokerAddr;

  private String centerAddr;

  private TopicTable topicTable;

  public String getBrokerAddr() {
    return brokerAddr;
  }

  public void setBrokerAddr(String brokerAddr) {
    this.brokerAddr = brokerAddr;
  }

  public String getCenterAddr() {
    return centerAddr;
  }

  public void setCenterAddr(String centerAddr) {
    this.centerAddr = centerAddr;
  }

  public TopicTable getTopicTable() {
    return topicTable;
  }

  public void setTopicTable(TopicTable topicTable) {
    this.topicTable = topicTable;
  }
}
