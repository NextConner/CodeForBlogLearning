package cn.joker.common.advice;

/**
 *
 *  方法信息打印接口
 *
 * @author jintaoZou
 * @date 2023/3/31-14:47
 */
public interface IMethodLogAdvice {

    /**
     * 日志打印次数
     *
     * @return
     */
    default int printTimes() {
        return 1;
    }

    /**
     * 方法参数打印深度 , 控制对于集合类参数的遍历
     *
     * @return
     */
    default int printParamLevel() {
        return 1;
    }

    /**
     * 是否打印方法返回值
     *
     * @return
     */
     default  boolean printReturn() {
        return false;
    }

    public default Object advice(){
        int printTimes = printTimes();
        while(printTimes-- >1) {
            System.out.println(innerAdvice());
        }
        System.out.println(innerAdvice());
        return innerAdvice();
    }

    Object innerAdvice();

}
