package com.rocket.common.message;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 9:47 PM 2018/11/26
 * @Modified By:
 */
public interface MessageType {

  byte BUSINESS_REQ = 0;

  byte BUSINESS_RESP = 1;

  byte BUSINESS_ONE_WAY = 2;

  byte LOGIN_REQ = 3;

  byte LOGIN_RESP = 4;

  byte HEARTBEAT_REQ = 5;

  byte HEARTBEAT_RESP = 6;


}
