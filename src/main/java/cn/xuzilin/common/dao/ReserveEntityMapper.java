package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.ReserveEntity;

public interface ReserveEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReserveEntity record);

    int insertSelective(ReserveEntity record);

    ReserveEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReserveEntity record);

    int updateByPrimaryKey(ReserveEntity record);
}