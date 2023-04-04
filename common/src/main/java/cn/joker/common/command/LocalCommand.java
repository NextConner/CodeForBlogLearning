package cn.joker.common.command;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 *
 * 命令对象
 *
 * @author jintaoZou
 * @date 2023/4/3-12:33
 */
@Builder
@Data
public class LocalCommand {


    private Command type;
    private Date reqTime;
    private Date respTime;
    private Object result;

    public void setResult(Object result) {
        this.result = result;
        this.respTime = new Date();
    }

    @Override
    public String toString() {
        return "LocalCommand{" +
                "type=" + type.name() +
                ", reqTime=" + reqTime +
                ", respTime=" + respTime +
                ", result=" + result +
                '}';
    }
}
