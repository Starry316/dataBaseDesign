package cn.xuzilin.dao;

import cn.xuzilin.po.CouponEntity;

public interface CouponEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponEntity record);

    int insertSelective(CouponEntity record);

    CouponEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponEntity record);

    int updateByPrimaryKey(CouponEntity record);
}