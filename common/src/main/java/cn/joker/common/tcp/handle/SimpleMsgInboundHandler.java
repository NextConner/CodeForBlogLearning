package cn.joker.common.tcp.handle;

import cn.joker.common.advice.impl.LogMethodInfoAdvice;
import cn.joker.common.command.Command;
import cn.joker.common.enhancer.MethodExistEnhancer;
import cn.joker.common.tcp.server.SimpleTcpServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.bytebuddy.agent.Installer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jintaoZou
 * @date 2023/4/3-14:30
 */
public class SimpleMsgInboundHandler extends SimpleChannelInboundHandler<String> {

    private SimpleTcpServer simpleTcpServer;

    public SimpleMsgInboundHandler(SimpleTcpServer simpleTcpServer) {
        this.simpleTcpServer = simpleTcpServer;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("msg read : " + msg);
        if (msg.startsWith(Command.WATCH.name())) {
            String args = msg;
            String[] split = args.split(" ");
            String className = split[1];
            if (args.contains("-n")) {
                int printTimes = Integer.valueOf(split[2].split("-n")[1]);
                LogMethodInfoAdvice.printCounter = new AtomicInteger(printTimes);
            }
            if (args.contains("-x")) {
                int printParamLevel = Integer.valueOf(split[3].split("-x")[1]);
                LogMethodInfoAdvice.printParamLevel = new AtomicInteger(printParamLevel);
            }
            LogMethodInfoAdvice.isPrintReturn = new AtomicBoolean(args.contains("-r"));

            Installer.agentmain(args, simpleTcpServer.getInstrumentation());
            //do enhance
            MethodExistEnhancer.enhance(Class.forName(className), LogMethodInfoAdvice.class);

        } else if (msg.toUpperCase().equals(Command.EXIT.name())) {
            simpleTcpServer.shutdown();
        }
    }

}
