package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.RoomEntity;

public interface RoomEntityMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(RoomEntity record);

    int insertSelective(RoomEntity record);

    RoomEntity selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(RoomEntity record);

    int updateByPrimaryKey(RoomEntity record);
}