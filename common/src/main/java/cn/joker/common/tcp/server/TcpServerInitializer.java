package cn.joker.common.tcp.server;

import cn.joker.common.tcp.handle.SimpleMsgInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.lang.instrument.Instrumentation;

/**
 * @author jintaoZou
 * @date 2022/9/14-9:13
 */
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

    private SimpleTcpServer simpleTcpServer;

    public TcpServerInitializer(SimpleTcpServer simpleTcpServer){
        this.simpleTcpServer = simpleTcpServer;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("idleStateHandler", new IdleStateHandler(5, 0, 0));
//        socketChannel.pipeline().addLast(new ClientIdleStateTrigger());
//        socketChannel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        socketChannel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
//        socketChannel.pipeline().addLast("decoder", new StringDecoder());
//        socketChannel.pipeline().addLast(new ProtoMsgCodec());
//        socketChannel.pipeline().addLast("idleStateTrigger", new ServerIdleStateTrigger());
////        socketChannel.pipeline().addLast("encoder", new StringEncoder());
//        socketChannel.pipeline().addLast("bizHandler", new ServerBizHandler());
        socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new SimpleMsgInboundHandler(simpleTcpServer));
    }
}
