package com.rocket.common.coder.encoder;

import com.rocket.common.coder.MarshallingCodeCFactory;
import com.rocket.common.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:44 PM 2018/11/25
 * @Modified By:
 */
public final class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

  private NettyMarshallingEncoder marshallingEncoder;

  public NettyMessageEncoder() throws IOException {
    marshallingEncoder = MarshallingCodeCFactory.buildMarshallingEncoder();
  }

  protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out)
      throws Exception {
    if (Objects.isNull(msg) || Objects.isNull(msg.getHeader())) {
      throw new Exception("the encode message is null");
    }
    ByteBuf sendBuf = Unpooled.buffer();
    sendBuf.writeInt(msg.getHeader().getCrcCode());
    sendBuf.writeInt(msg.getHeader().getLength());
    sendBuf.writeLong(msg.getHeader().getSessionID());
    sendBuf.writeByte(msg.getHeader().getType());
    sendBuf.writeByte(msg.getHeader().getPriority());
    sendBuf.writeInt(msg.getHeader().getAttachment().size());

    String key;
    byte[] keyArray;
    Object value;
    for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
      key = param.getKey();
      keyArray = key.getBytes(CharsetUtil.UTF_8);
      sendBuf.writeInt(keyArray.length);
      sendBuf.writeBytes(keyArray);
      value = param.getValue();
      marshallingEncoder.encode(ctx, value, sendBuf);
    }
    if (msg.getBody() != null) {
      marshallingEncoder.encode(ctx, msg.getBody(), sendBuf);
    }
    int readableBytes = sendBuf.readableBytes();
    sendBuf.setInt(4, readableBytes);
    out.add(sendBuf);
  }
}
