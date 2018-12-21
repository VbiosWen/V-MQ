package com.vbiso.encode;

import com.rocket.common.message.HeartBeat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import java.util.Random;
import org.junit.Test;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 9:09 PM 2018/12/9
 * @Modified By:
 */
public class TestEncode {


  @Test
  public void test(){
    HeartBeat heartBeat=new HeartBeat();

    heartBeat.setChannelId(new Random().nextLong());

    heartBeat.setCrcCode(0);

    heartBeat.setGroup("vbiso");

    heartBeat.setTopic("test");

    heartBeat.setTag("_001");

    encode(heartBeat);
  }

  private void encode(HeartBeat heartBeat) {
    ByteBuf buf= Unpooled.buffer();

    buf.writeInt(heartBeat.getCrcCode());
    buf.writeLong(heartBeat.getChannelId());
    buf.writeBytes(heartBeat.getGroup().getBytes(CharsetUtil.UTF_8));
    buf.writeBytes(heartBeat.getTopic().getBytes(CharsetUtil.UTF_8));
    buf.writeBytes(heartBeat.getTag().getBytes(CharsetUtil.UTF_8));
  }
}
