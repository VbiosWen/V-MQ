package com.rocket.common.message;


/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:24 PM 2018/12/20
 * @Modified By:
 */
public class VMessage {

  private transient VHeader header;

  private transient byte[] body;


  public VHeader getHeader() {
    return header;
  }

  public void setHeader(VHeader header) {
    this.header = header;
  }

  public byte[] getBody() {
    return body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }
}
