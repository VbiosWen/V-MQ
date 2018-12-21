package com.rocket.common;

import com.rocket.common.coder.encoder.NettyMessageDecoder;
import com.rocket.common.coder.encoder.NettyMessageEncoder;
import com.rocket.common.handle.HeartBeatRespHandler;
import com.rocket.common.handle.LoginAuthRespHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:49 PM 2018/11/25
 * @Modified By:
 */
public class Main {




  public static void main(String[] args) throws InterruptedException {
    new Main().bind();
  }



  private void bind() throws InterruptedException {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
              pipeline.addLast(new NettyMessageEncoder());
              pipeline.addLast(new ReadTimeoutHandler(50));
              pipeline.addLast(new LoginAuthRespHandler());
              pipeline.addLast(new HeartBeatRespHandler());
            }
          });
      ChannelFuture future = bootstrap.bind(10001).sync();
      System.out.println("netty server start ok! port is 10001");
      future.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

}
