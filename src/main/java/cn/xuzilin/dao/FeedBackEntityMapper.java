package cn.xuzilin.dao;

import cn.xuzilin.po.FeedBackEntity;

public interface FeedBackEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedBackEntity record);

    int insertSelective(FeedBackEntity record);

    FeedBackEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedBackEntity record);

    int updateByPrimaryKeyWithBLOBs(FeedBackEntity record);

    int updateByPrimaryKey(FeedBackEntity record);
}