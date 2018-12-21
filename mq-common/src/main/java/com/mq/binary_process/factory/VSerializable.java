package com.mq.binary_process.factory;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:55 PM 2018/12/21
 * @Modified By:
 */
public interface VSerializable {


  public byte[] encode(final Object obj);

  public <T> T decode(final byte[] bytes, Class<T> tClass);


}
