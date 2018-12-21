package com.rocket.common.coder.encoder;

import com.rocket.common.coder.MarshallingCodeCFactory;
import com.rocket.common.message.Header;
import com.rocket.common.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:28 PM 2018/11/26
 * @Modified By:
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

  private NettyMarshallingDecoder marshallingDecoder;

  public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
      int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
    super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,
        initialBytesToStrip);
    this.marshallingDecoder = MarshallingCodeCFactory.buildMarshallingDecoder();
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    ByteBuf frame = (ByteBuf) super.decode(ctx, in);
    if (Objects.isNull(frame)) {
      return null;
    }

    NettyMessage message = new NettyMessage();
    Header header = new Header();
    header.setCrcCode(frame.readInt());
    header.setLength(frame.readInt());
    header.setSessionID(frame.readLong());
    header.setType(frame.readByte());
    header.setPriority(frame.readByte());
    int size = frame.readInt();
    if (size > 0) {
      Map<String, Object> attachment = new HashMap<>(size);
      int keySize;
      byte[] keyArray;
      String key;
      for (int i = 0; i < size; i++) {
        keySize = frame.readInt();
        keyArray = new byte[keySize];
        in.readBytes(keyArray);
        key = new String(keyArray, CharsetUtil.UTF_8);
        attachment.put(key, marshallingDecoder.decode(ctx, frame));
      }
      header.setAttachment(attachment);
    }
    if (frame.readableBytes() > 0) {
      message.setBody(marshallingDecoder.decode(ctx, frame));
    }
    message.setHeader(header);
    return message;
  }
}
