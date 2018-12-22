package com.mq;

import com.mq.binary_process.decoder.NettyDecode;
import com.mq.binary_process.encoder.NettyEncode;
import com.mq.handle.VHeartBeatServerHandler;
import com.mq.handle.VNettyMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 1:04 PM 2018/12/22
 * @Modified By:
 */
public class VCenterManager {

  private final String addr;

  private final int port;

  private EventLoopGroup selectorGroup;

  private EventLoopGroup wokerGroup;

  private ServerBootstrap serverBootstrap;

  public VCenterManager(String addr, int port) {
    this.addr = addr;
    this.port = port;
    init();
  }

  private void init() {
    selectorGroup=new NioEventLoopGroup();
    wokerGroup=new NioEventLoopGroup();
    serverBootstrap=new ServerBootstrap();
  }

  public void startUp(){
    serverBootstrap.group(selectorGroup,wokerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast("handler",new IdleStateHandler(3,0,0, TimeUnit.SECONDS));
            ch.pipeline().addLast("decoder",new StringDecoder());
            ch.pipeline().addLast("encoder",new StringEncoder());
            ch.pipeline().addLast(new VHeartBeatServerHandler());
          }
        });
    try {
      ChannelFuture future = serverBootstrap.bind(port).sync();
      future.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally {
      selectorGroup.shutdownGracefully();
      wokerGroup.shutdownGracefully();
    }
  }
}
