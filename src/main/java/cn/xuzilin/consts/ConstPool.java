package cn.xuzilin.consts;

import java.math.BigDecimal;

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

    /**
     * 会员卡类型
     */
    //白银会员
    public final static byte SILVER= 1;
    //黄金会员
    public final static byte GOLD = 2;
    //钻石会员
    public final static byte DIAMOND = 3;
//    //狂拽炫酷吊炸天会员
//    public final static byte DIAO = 4;
    //会员各阶段花费金额
    public final static BigDecimal GOLDCONSUMPTION = new BigDecimal("1000");
    public final static BigDecimal DIAMONDCONSUMPTION = new BigDecimal("5000");
    public final static BigDecimal DIAOCONSUMPTION = new BigDecimal("10000");


    /**
     * 反馈状态
     */
    public final static byte FEEDBACK_SUBMITED= 0;
    public final static byte FEEDBACK_DEALING= 1;
    public final static byte FEEDBACK_COMPLETE= 2;


    /**
     * 优惠券状态
     */
    public final static byte COUPON_UNUSED= 0;
    public final static byte COUPON_USED= 1;
    public final static byte COUPON_OUTOFDATE= 2;
}
