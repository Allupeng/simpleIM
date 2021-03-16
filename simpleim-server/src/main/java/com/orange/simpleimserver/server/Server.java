package com.orange.simpleimserver.server;

import com.orange.simpleimserver.config.SimpleIMServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class Server {

    @Resource
    private SimpleIMServerConfig config;

    @Autowired
    private SocketServerInitializer initializer;

    private ServerBootstrap server = new ServerBootstrap();

    private EventLoopGroup boss = new NioEventLoopGroup(1);//监听连接线程

    private EventLoopGroup worker = new NioEventLoopGroup();//传输IO进程

    public void run(){
        startServer();
    }

    private void startServer(){
        server.group(boss,worker)
                .channel(NioServerSocketChannel.class) //channel为非阻塞io
                .localAddress(config.getPort()) //设置端口
                //.option(ChannelOption.SO_KEEPALIVE,true)//心跳机制
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)//ByteBuf线程池
                .childOption(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT)//ByteBuf线程池
                .childHandler(initializer);
        try {
            log.info("Server start listen port :{} start listening....",config.getPort());
            ChannelFuture listenFuture = server.bind().sync();

            ChannelFuture closeFuture = listenFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            log.info("server closed." );
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


}
