package cn.joker.common.tcp.client.handler;

import cn.joker.common.command.LocalCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. client 端使用的接收 server 返回的格式化命令处理结果
 * @author jintaoZou
 * @date 2023/4/4-14:39
 */
@Slf4j
public class LocalCommandInboundHandler extends SimpleChannelInboundHandler<LocalCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LocalCommand msg) throws Exception {
        log.info("{}",msg);
    }

}
