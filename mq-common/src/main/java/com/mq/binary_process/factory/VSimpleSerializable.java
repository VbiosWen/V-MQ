package com.mq.binary_process.factory;

import com.alibaba.fastjson.JSON;
import java.nio.charset.Charset;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:58 PM 2018/12/21
 * @Modified By:
 */
public class VSimpleSerializable implements VSerializable {

  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  public byte[] encode(Object obj) {
    final String json = toJson(obj);
    return json.getBytes(DEFAULT_CHARSET);
  }

  private String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }

  public <T> T decode(byte[] bytes, Class<T> tClass) {
    final String json = new String(bytes, DEFAULT_CHARSET);
    return fromJson(json, tClass);
  }

  private <T> T fromJson(String json, Class<T> tClass) {
    return JSON.parseObject(json, tClass);
  }


}
