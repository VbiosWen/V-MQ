package com.rocket.common.coder;

import com.rocket.common.coder.encoder.NettyMarshallingDecoder;
import com.rocket.common.coder.encoder.NettyMarshallingEncoder;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import java.io.IOException;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 10:52 PM 2018/11/25
 * @Modified By:
 */
public class MarshallingCodeCFactory {

  public static NettyMarshallingDecoder buildMarshallingDecoder(){
    final MarshallerFactory marshallerFactory= Marshalling.getProvidedMarshallerFactory("serial");
    final MarshallingConfiguration configuration=new MarshallingConfiguration();
    configuration.setVersion(5);
    UnmarshallerProvider provider=new DefaultUnmarshallerProvider(marshallerFactory,configuration);
    return new NettyMarshallingDecoder(provider, 1024);
  }

  public static NettyMarshallingEncoder buildMarshallingEncoder(){
    final MarshallerFactory marshallerFactory=Marshalling.getProvidedMarshallerFactory("serial");
    final MarshallingConfiguration configuration=new MarshallingConfiguration();
    configuration.setVersion(5);
    MarshallerProvider provider=new DefaultMarshallerProvider(marshallerFactory,configuration);
    return new NettyMarshallingEncoder(provider);
  }

  public static Marshaller buildMarshaller() throws IOException {
    final MarshallerFactory marshallerFactory=Marshalling.getProvidedMarshallerFactory("serial");
    final MarshallingConfiguration configuration=new MarshallingConfiguration();
    configuration.setVersion(5);
    return marshallerFactory.createMarshaller(configuration);
  }


}
