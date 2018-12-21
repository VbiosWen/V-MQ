package com.rocket;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:14 PM 2018/12/20
 * @Modified By:
 */
public class VBrokerMain {

  private final String remoteAddress;

  private final int port;

  private EventLoopGroup brokerGroup;

  private  Bootstrap bootstrap;

  public VBrokerMain(String remoteAddress, int port) {
    this.remoteAddress = remoteAddress;
    this.port = port;
    this.brokerGroup = new NioEventLoopGroup();
    this.bootstrap = new Bootstrap();
  }

  public static VBrokerMain init(final String remoteAddress,final int port){
    return new VBrokerMain(remoteAddress,port);
  }

  public void startUp(){
    bootstrap.group(brokerGroup);
    bootstrap.option(ChannelOption.TCP_NODELAY,true);
    bootstrap.channel(SocketChannel.class);
  }

}
