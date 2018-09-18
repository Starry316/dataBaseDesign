package cn.xuzilin.utils;

import cn.xuzilin.consts.ConstPool;

import java.math.BigDecimal;

public class SwitchUtil {
    public static  String switchType(byte type){
        switch (type){
            case 1:return "标准间";
            case 2:return "大床房";
            case 3:return "商务套房";
            default:return "豪华套房";
        }
    }
    public static String switchStatus(byte status){
        switch (status){
            case 0:return "空房";
            case 1:return "已入住";
            case 2:return "已预定";
            default:return "暂不可用";
        }
    }
    public static BigDecimal switchTpyePayment(byte type){
        switch (type){
            case 1:return BigDecimalUtil.create(189.99);
            case 2:return BigDecimalUtil.create(199.99);
            case 3:return BigDecimalUtil.create(299.99);
            default:return BigDecimalUtil.create(399.99);
        }
    }
    public static String switchMemberCardTypeName(byte memberType ){
        switch (memberType){
            case ConstPool.SILVER:
                return "银卡会员";
            case ConstPool.GOLD:
                return "金卡会员";
            case ConstPool.DIAMOND:
                return "钻石卡会员";
            default:return "";
        }
    }
    public static BigDecimal switchMemberDiscount(byte memberType){
        switch (memberType){
            case ConstPool.SILVER:
                return new BigDecimal("0.9");
            case ConstPool.GOLD:
                return new BigDecimal("0.8");
            case ConstPool.DIAMOND:
                return new BigDecimal("0.7");
            default:return new BigDecimal("1");
        }
    }
    public static String switchFeedbackStatusName(byte status){
        switch (status){
            case ConstPool.FEEDBACK_COMPLETE:
                return "已处理";
            case ConstPool.FEEDBACK_DEALING:
                return "处理中";
            case ConstPool.FEEDBACK_SUBMITED:
                return "未处理";
        }
        return "未处理";
    }
}
