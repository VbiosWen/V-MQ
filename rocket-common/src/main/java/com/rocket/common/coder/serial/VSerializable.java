package com.rocket.common.coder.serial;

import com.alibaba.fastjson.JSON;
import java.nio.charset.Charset;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:50 PM 2018/12/20
 * @Modified By:
 */
public abstract class VSerializable {

  private final static Charset CHARSET_UTF8=Charset.forName("UTF-8");

  public static byte[] encode(final Object obj){
    final String json=toJson(obj);
    return json.getBytes(CHARSET_UTF8);
  }

  public static String toJson(final Object obj){
    return JSON.toJSONString(obj);
  }

  public static <T> T decode(final byte[] data,Class<T> clzz){
    final String json=new String(data,CHARSET_UTF8);
    return fromJson(json,clzz);
  }

  private static <T> T fromJson(String json, Class<T> clzz) {
    return JSON.parseObject(json,clzz);
  }

}
