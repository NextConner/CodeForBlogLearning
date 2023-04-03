package cn.joker.common.tcp.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author jintaoZou
 * @date 2022/9/13-14:14
 */
@Slf4j
public class SimpleTcpServer {


    private Instrumentation instrumentation;

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);
    private NioEventLoopGroup workerGroup;
    private String serverName;
    private String host;
    private int port;

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    private SimpleTcpServer(String serverName, String host, int port, int bossNum, int workerNum) {
        serverBootstrap = new ServerBootstrap();
        this.bossGroup = bossNum > 0 ? new NioEventLoopGroup(bossNum) : this.bossGroup;
        this.workerGroup = new NioEventLoopGroup(workerNum);
        this.serverName = serverName;
        this.host = host;
        this.port = port;
    }

    public static SimpleTcpServer SERVER = new SimpleTcpServer("default", "127.0.0.1", 8088, 3, 10);

    private void init() {


        SERVER.serverBootstrap.
                group(SERVER.bossGroup, SERVER.workerGroup)
                .channel(NioServerSocketChannel.class)
               .option(ChannelOption.SO_RCVBUF, 1024 * 100)
                .childOption(ChannelOption.ALLOW_HALF_CLOSURE, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.AUTO_CLOSE, true)
                .childOption(ChannelOption.AUTO_READ,true)
                .childHandler(new TcpServerInitializer(SERVER));
    }


    public static ChannelFuture  start(int port,Instrumentation instrumentation ) {

        try {
            SERVER.setInstrumentation(instrumentation);
            SERVER.init();
            ChannelFuture channelFuture = SERVER.serverBootstrap.bind(port).sync();
            log.info("TCP SERVER start at {}:{}", SERVER.host, port);
            return  channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("TCP server start error : {}", e);
            SERVER.bossGroup.shutdownGracefully();
            SERVER.workerGroup.shutdownGracefully();
        }
        return null;
    }

    public void shutdown(){
        SERVER.bossGroup.shutdownGracefully();
        SERVER.workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        SimpleTcpServer.start(58733,null);
    }

}
