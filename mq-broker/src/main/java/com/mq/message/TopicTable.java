package com.mq.message;

import java.util.HashMap;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 1:01 PM 2018/12/22
 * @Modified By:
 */
public class TopicTable {

  private HashMap<String,String> topicTable=new HashMap<String, String>();

  public HashMap<String, String> getTopicTable() {
    return topicTable;
  }

  public void setTopicTable(HashMap<String, String> topicTable) {
    this.topicTable = topicTable;
  }
}
