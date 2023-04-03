package cn.joker.common.command;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * TODO 命令存储，需要通过 netty 实现简单通信机制，传递命令到 agent 代理端
 *
 * @author jintaoZou
 * @date 2023/4/3-12:33
 */
public class LocalCommand {


    static LinkedBlockingQueue<String> commandQueue = new LinkedBlockingQueue<>(1000);

    // 简单实现，通过本地缓存获取命令
    public static String popOne() throws IOException {
        return commandQueue.poll();
    }

    public static boolean addOne(String command) throws IOException {
        return commandQueue.add(command);
    }


}
