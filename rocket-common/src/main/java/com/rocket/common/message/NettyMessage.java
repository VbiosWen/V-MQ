package com.rocket.common.message;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:42 PM 2018/11/25
 * @Modified By:
 */
public class NettyMessage {

  private Header header;

  private Object body;


  public final Header getHeader() {
    return header;
  }

  public final void setHeader(Header header) {
    this.header = header;
  }

  public final Object getBody() {
    return body;
  }

  public final void setBody(Object body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "NettyMessage{" +
        "header=" + header +
        ", body=" + body +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    NettyMessage that = (NettyMessage) o;

    return new org.apache.commons.lang.builder.EqualsBuilder()
        .append(header, that.header)
        .append(body, that.body)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new org.apache.commons.lang.builder.HashCodeBuilder(17, 37)
        .append(header)
        .append(body)
        .toHashCode();
  }
}
