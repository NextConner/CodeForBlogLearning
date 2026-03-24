package cn.joker.common.tcp.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

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

    public SimpleTcpServer(int port) {
        this("local server", "localhost", port, 3, 10);
    }

    public SimpleTcpServer(String serverName, String host, int port, int bossNum, int workerNum) {
        this.serverBootstrap = new ServerBootstrap();
        this.bossGroup = bossNum > 0 ? new NioEventLoopGroup(bossNum) :new NioEventLoopGroup(3);
        this.workerGroup = new NioEventLoopGroup(workerNum);
        this.serverName = serverName;
        this.host = host;
        this.port = port;
    }

    private void init() {

        this.serverBootstrap.
                group(this.bossGroup, this.workerGroup)
                .channel(NioServerSocketChannel.class)
               .option(ChannelOption.SO_RCVBUF, 1024 * 100)
                .childOption(ChannelOption.ALLOW_HALF_CLOSURE, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.AUTO_CLOSE, true)
                .childOption(ChannelOption.AUTO_READ,true)
                .childHandler(new TcpServerInitializer(this));
    }


    public  ChannelFuture  start(Instrumentation instrumentation ) {

        try {
            this.setInstrumentation(instrumentation);
            this.init();
            ChannelFuture channelFuture = this.serverBootstrap.bind(port).sync();
            log.info("TCP SERVER start at {}:{}", this.host, this.port);
            return  channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("TCP server start error : {}", e);
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }
        return null;
    }

    public void shutdown(){
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        new SimpleTcpServer(8088).start(null);
    }



}
