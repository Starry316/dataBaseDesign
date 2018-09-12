package cn.xuzilin.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static BigDecimal add(Double a , Double b){
        BigDecimal A = new BigDecimal(a.toString());
        BigDecimal B = new BigDecimal(b.toString());
        return A.add(B).setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }

    public static BigDecimal multiply(Double a , Double b){
        BigDecimal A = new BigDecimal(a.toString());
        BigDecimal B = new BigDecimal(b.toString());
        return A.multiply(B).setScale(2,BigDecimal.ROUND_HALF_DOWN);
    }
}
