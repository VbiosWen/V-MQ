package com.mq.message;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:53 PM 2018/12/21
 * @Modified By:
 */
public class VMessage {

  private transient VHeader vHeader;

  private transient byte[] body;

  public VHeader getvHeader() {
    return vHeader;
  }

  public void setvHeader(VHeader vHeader) {
    this.vHeader = vHeader;
  }

  public byte[] getBody() {
    return body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }
}
