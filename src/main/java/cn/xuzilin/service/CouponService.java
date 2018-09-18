package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.CouponEntityMapper;
import cn.xuzilin.po.CouponEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Service
public class CouponService {
    @Resource
    private CouponEntityMapper couponMapper;

    public BigDecimal getDiscountByCode(String code){
        return couponMapper.selectDiscountByCode(code);
    }

    public int create (int userId, BigDecimal discount, Date expiry){
        CouponEntity coupon = new CouponEntity();
        coupon.setDiscount(discount);
        coupon.setExpiry(expiry);
        coupon.setUserId(userId);
        coupon.setStatus(ConstPool.COUPON_UNUSED);
        String code = "";
        do {
            code = createRandomCode();
        }while (!validate(code));
        coupon.setCode(code);
        return couponMapper.insertSelective(coupon);
    }

    public void useCoupon(int recordId,String code){
        CouponEntity coupon = couponMapper.selectByCode(code);
        coupon.setStatus(ConstPool.COUPON_USED);
        coupon.setRecordId(recordId);
        couponMapper.updateByPrimaryKeySelective(coupon);
    }

    public boolean validate(String code){
        return couponMapper.selectCountByCode(code) == 1;
    }
    private static String createRandomCode(){
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String res="";
        Random random = new Random();
        for (int i = 0; i < 6; i++)
            res += chars.charAt(random.nextInt(36));
        return res;
    }
}
