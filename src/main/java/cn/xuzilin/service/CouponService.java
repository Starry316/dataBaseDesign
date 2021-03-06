package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.CouponEntityMapper;
import cn.xuzilin.po.CouponEntity;
import cn.xuzilin.po.UserEntity;
import cn.xuzilin.utils.DateUtil;
import cn.xuzilin.utils.SessionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
        }while (validate(code));
        coupon.setCode(code);
        return couponMapper.insertSelective(coupon);
    }

    /**
     * 新用户大礼包！
     * @param userId
     */
    public void newUserCoupon(int userId){
        create(userId,new BigDecimal("80"), DateUtil.addDays(DateUtil.getNowDate(),30));
        create(userId,new BigDecimal("50"), DateUtil.addDays(DateUtil.getNowDate(),30));
        create(userId,new BigDecimal("100"), DateUtil.addDays(DateUtil.getNowDate(),30));
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

    public JSONArray getCouponListByUserId(int userId){
        List<CouponEntity> list = couponMapper.selectByUserId(userId);
        JSONArray data = JSON.parseArray(JSON.toJSONString(list));
        return data;
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
