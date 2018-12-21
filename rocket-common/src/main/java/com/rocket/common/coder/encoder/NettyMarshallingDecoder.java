package com.rocket.common.coder.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 7:22 PM 2018/11/26
 * @Modified By:
 */
public class NettyMarshallingDecoder extends MarshallingDecoder {

  public NettyMarshallingDecoder(UnmarshallerProvider provider) {
    super(provider);
  }

  public NettyMarshallingDecoder(UnmarshallerProvider provider, int maxObjectSize) {
    super(provider, maxObjectSize);
  }

  @Override
  public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    return super.decode(ctx, in);
  }
}
