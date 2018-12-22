package com.mq;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 1:03 PM 2018/12/22
 * @Modified By:
 */
public class VCenterStartUp {


  public static void main(String[] args){
    new VCenterManager("127.0.0.1",6666).startUp();
  }

}
