package com.mq;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 12:59 PM 2018/12/22
 * @Modified By:
 */
public class VBrokerStartUp {


  public static void main(String[] args){
    VBrokerManager manager=new VBrokerManager(6666,"127.0.0.1");
    manager.startUp();
  }

}
