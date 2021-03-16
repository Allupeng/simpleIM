package com.orange.simpleimserver.server;

import com.orange.simpleimserver.handler.SocketMsgHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

@Component
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ChunkedWriteHandler());
        ch.pipeline().addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast(new SocketMsgHandler());
    }
}
