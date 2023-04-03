package cn.joker.common.tcp.client;


import cn.joker.common.command.Command;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;

/**
 * @author jintaoZou
 * @date 2022/9/14-9:52
 */
public class TcpClient {

    private String host;
    private int port;
    private Bootstrap bootstrap;
    private EventLoopGroup group;

    private Channel channel;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public  Channel start(String[] args) throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap  bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.AUTO_CLOSE,true)
                .handler(new ClientHandlerInitializer(null));
        return bootstrap.connect("localhost", 8088).channel();
    }

    public void shutdown(){
        this.group.shutdownGracefully();
    }

}