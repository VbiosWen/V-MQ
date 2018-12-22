package com.mq.binary_process.factory;

import com.mq.message.VMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:54 PM 2018/12/21
 * @Modified By:
 */
public class SerialTypeFactory {


  public VSerializable getSerialMethod(SerialTypeEnum typeEnum) {
    VSerializable vSerializable = null;
    switch (typeEnum) {
      case DEFAULT:
        vSerializable = new VSimpleSerializable();
        break;
      default:
        vSerializable = new VSimpleSerializable();
        break;
    }
    return vSerializable;
  }

  public VMessage decode(final ByteBuffer byteBuffer) {
    int originLength = byteBuffer.getInt();
    SerialTypeEnum serialTypeEnum = SerialTypeEnum.get((byte) ((originLength >> 24) & 0xFF));
    VSerializable serialMethod = getSerialMethod(serialTypeEnum);
    int length = byteBuffer.limit();
    byte[] bytes = new byte[originLength];
    byteBuffer.get(bytes);
    return serialMethod.decode(bytes, VMessage.class);
  }

  public ByteBuf encode(final Object obj) {
    VSerializable serialMethod = getSerialMethod(SerialTypeEnum.DEFAULT);
    byte[] encode = serialMethod.encode(obj);
    ByteBuf byteBuf = Unpooled.buffer(encode.length);
    byteBuf.writeBytes(encode);
    return byteBuf;
  }

}
