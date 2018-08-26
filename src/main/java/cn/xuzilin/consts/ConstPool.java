package cn.xuzilin.consts;

/**
 * Created by Starry on 2018/7/18.
 */
public class ConstPool {
    /**
     * room表 房间状态
     */
    //当前已经入住
    public final static byte FULL = 1;
    //当前房间为空
    public final static byte EMPTY = 0;

    /**
     * record表 入住状态
     */
    //已经结账，完成入住
    public final static byte CHECK_OUT= -1;
    //正在入住
    public final static byte CHECK_IN = 1;
    //延期 超过预计退房时间
    public final static byte OVERDUE= 2;

    /**
     * 预定状态管理
     */
    //预定取消
    public final static byte CANCEL= -1;
    //预定完成
    public final static byte COMPLETE = 0;
    //预定正在进行
    public final static byte ACTIVE = 1;
}
