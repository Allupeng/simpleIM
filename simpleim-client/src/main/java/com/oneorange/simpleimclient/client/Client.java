package com.oneorange.simpleimclient.client;

import com.oneorange.simpleimclient.config.SimpleIMClientConfig;
import com.oneorange.simpleimclient.type.FunctionType;
import com.oneorange.simpleimclient.util.ByteBufUtil;
import com.oneorange.simpleimclient.util.DateUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Scanner;

@Component
@Slf4j
public class Client {

    @Resource
    private SimpleIMClientConfig config;


    private FunctionType type;

    @Autowired
    private SocketInitializer initializer ;

    private ByteBuf buffer;

     Bootstrap client = new Bootstrap();

     EventLoopGroup worker = new NioEventLoopGroup();


     public void run(){//服务器运行
        startClient();
     }

     private void startClient(){
        client.group(worker)
                .channel(NioSocketChannel.class)
                .remoteAddress(config.getHostname(),config.getPort())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(initializer);
         ChannelFuture connectFuture = client.connect();
         connectFuture.addListener((ChannelFuture future)->{
             if (future.isSuccess()){
                 log.info("client connect success!");
             }else {
                 log.info("client connect failed!");
             }
         });

         try {
             connectFuture.sync();
             Channel channel = connectFuture.channel();
             ClientInput(channel);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }finally {
             log.info("client lost connection");
             worker.shutdownGracefully();
         }
     }

     private void ClientInput(Channel channel){
         log.info("menu:    [................This is a Simple Client .................]");
         log.info("function:[................[0]SimpleChat(Server Test)...............]");
         log.info("function:[................[1]Login.................................]");

         Scanner scanner = new Scanner(System.in);
         while (scanner.hasNext()){
             String content = scanner.next();
             byte[] arr = ("张三" + DateUtil.getNow()+ " >> " + content).getBytes();
             buffer = channel.alloc().buffer();
             buffer.writeBytes(arr);
             channel.writeAndFlush(buffer);
         }
         ByteBufUtil.release(buffer);
     }
}
