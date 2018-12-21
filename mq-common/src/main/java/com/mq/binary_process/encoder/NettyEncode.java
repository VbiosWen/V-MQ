package com.mq.binary_process.encoder;

import com.mq.message.VMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:54 PM 2018/12/21
 * @Modified By:
 */
public class NettyEncode extends MessageToByteEncoder<VMessage> {

  @Override
  protected void encode(ChannelHandlerContext ctx, VMessage msg, ByteBuf out) throws Exception {

  }
}
