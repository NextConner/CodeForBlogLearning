package cn.joker.common.tcp.server.handler;

import cn.joker.common.advice.impl.LogMethodInfoAdvice;
import cn.joker.common.command.Command;
import cn.joker.common.command.LocalCommand;
import cn.joker.common.enhancer.MethodExistEnhancer;
import cn.joker.common.tcp.server.SimpleTcpServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.bytebuddy.agent.Installer;

import java.lang.instrument.Instrumentation;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jintaoZou
 * @date 2023/4/3-14:30
 */
public class SimpleMsgInboundHandler extends SimpleChannelInboundHandler<String> {

    private SimpleTcpServer simpleTcpServer;

    public static  ThreadLocal<LocalCommand> resultCache = new ThreadLocal<>();

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
            LocalCommand command = LocalCommand.builder().reqTime(new Date())
                    .type(Command.WATCH).build();
            resultCache.set(command);
            Installer.agentmain(args, simpleTcpServer.getInstrumentation());
            try {
                command.setAgentType(Class.forName(className));
            } catch (Exception e) {
            } finally {
                while (null == resultCache.get().getResult()) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ctx.writeAndFlush(resultCache.get());
                resultCache.set(null);
            }

        } else if (msg.toUpperCase().equals(Command.EXIT.name())) {
            simpleTcpServer.shutdown();
        }
    }

    static ThreadLocal<SimpleTcpServer> threadLocal = new ThreadLocal<>();

    public static void agentmain(String arguments, Instrumentation instrumentation) {


        if(arguments.startsWith(Command.ATTACH.name()) && null == threadLocal.get()){
            final int port = Integer.valueOf(arguments.split(":")[1]);
            new Thread(() ->{
                SimpleTcpServer server = new SimpleTcpServer(port);
                server.start(instrumentation);
                threadLocal.set(server);
            }).start();
        }else{

            Installer.agentmain(arguments, instrumentation);
            try {
                while(null != resultCache.get() && null != resultCache.get().getAgentType()) {
                    MethodExistEnhancer.enhance(resultCache.get().getAgentType(), LogMethodInfoAdvice.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
