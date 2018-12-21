package com.rocket.common;

import com.rocket.common.coder.encoder.NettyMessageDecoder;
import com.rocket.common.coder.encoder.NettyMessageEncoder;
import com.rocket.common.handle.HeartBeatReqHandler;
import com.rocket.common.handle.LoginAuthReqHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:33 PM 2018/11/26
 * @Modified By:
 */
public class Client {

  private ScheduledExecutorService executorService= Executors.newScheduledThreadPool(1);

  public static void main(String[] args) throws InterruptedException {
    new Client().connect();
  }

  private void connect() throws InterruptedException {
    String host = "127.0.0.1";
    int port = 10001;
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
          .option(ChannelOption.TCP_NODELAY, true)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
              pipeline.addLast(new NettyMessageEncoder());
              pipeline.addLast(new ReadTimeoutHandler(50));
              pipeline.addLast(new LoginAuthReqHandler());
              pipeline.addLast(new HeartBeatReqHandler());
            }
          });
      ChannelFuture future = bootstrap.connect(host, port).sync();
      System.out.println("连接成功:"+host+":"+port);
      future.channel().closeFuture().sync();
    }finally {
      executorService.execute(()->{
        try {
          TimeUnit.SECONDS.sleep(5);
          connect();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

  }

}
