package com.mq;

import com.mq.handler.VHeatBeatBrokerHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 9:54 PM 2018/12/22
 * @Modified By:
 */
public class VBrokerManager {

  private int port;

  private String address;

  private EventLoopGroup clientGroup;

  private Bootstrap bootstrap;

  public VBrokerManager(int port, String address) {
    this.port = port;
    this.address = address;
    this.clientGroup=new NioEventLoopGroup();
    this.bootstrap=new Bootstrap();
  }

  public void startUp(){
    bootstrap.group(clientGroup)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("handler",new IdleStateHandler(0,3,0, TimeUnit.SECONDS));
            pipeline.addLast("decoder",new StringDecoder());
            pipeline.addLast("encoder",new StringEncoder());
            pipeline.addLast(new VHeatBeatBrokerHandle());
          }
        });
    ChannelFuture future = null;
    try {
      future = bootstrap.connect(address, port).sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      clientGroup.shutdownGracefully();
    }

  }
}
