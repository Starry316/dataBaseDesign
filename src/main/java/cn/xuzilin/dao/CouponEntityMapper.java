package cn.xuzilin.dao;

import cn.xuzilin.po.CouponEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

public interface CouponEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponEntity record);

    int insertSelective(CouponEntity record);

    CouponEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponEntity record);

    int updateByPrimaryKey(CouponEntity record);

    @Select("select count(*) from coupon where code = #{code} and status = 0")
    int selectCountByCode(@Param("code")String code);

    @Select("select * from coupon where code = #{code} and status = 0")
    CouponEntity selectByCode(@Param("code")String code);
    @Select("select discount from coupon where code = #{code} and status = 0")
    BigDecimal selectDiscountByCode(@Param("code")String code);
}