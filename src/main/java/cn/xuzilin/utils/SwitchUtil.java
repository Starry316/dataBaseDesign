package cn.xuzilin.utils;

import cn.xuzilin.consts.ConstPool;

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
    public static double switchTpyePayment(byte type){
        switch (type){
            case 1:return 189.99;
            case 2:return 199.99;
            case 3:return 299.99;
            default:return 399.99;
        }
    }
    public static double switchMemberDiscount(byte memberType){
        switch (memberType){
            case ConstPool.SILVER:
                return 0.9;
            case ConstPool.GOLD:
                return 0.8;
            case ConstPool.DIAMOND:
                return 0.7;
            case ConstPool.DIAO:
                return 0.6;
            default:return 1;
        }
    }
}
