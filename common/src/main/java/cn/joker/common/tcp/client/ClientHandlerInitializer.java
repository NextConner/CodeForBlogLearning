package cn.joker.common.tcp.client;

import cn.joker.common.tcp.client.handler.LocalCommandInboundHandler;
import cn.joker.common.tcp.server.SimpleTcpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author jintaoZou
 * @date 2022/9/14-9:54
 */
public class ClientHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private SimpleTcpServer simpleTcpServer;

    public ClientHandlerInitializer( SimpleTcpServer simpleTcpServer){
        this.simpleTcpServer = simpleTcpServer;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
//        pipeline.addLast(new ClientIdleStateTrigger());
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        pipeline.addLast(new LengthFieldPrepender(4));
//        pipeline.addLast(new IdleStateHandler(0,5,0));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new LocalCommandInboundHandler());
//        pipeline.addLast(new ProtoMsgCodec());
//        pipeline.addLast(new ProtoMsgHandler());
//        pipeline.addLast(new Pinger());
    }
}
