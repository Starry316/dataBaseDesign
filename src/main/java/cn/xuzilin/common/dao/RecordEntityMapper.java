package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.RecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecordEntity record);

    int insertSelective(RecordEntity record);

    RecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecordEntity record);

    int updateByPrimaryKey(RecordEntity record);

    @Select("SELECT * FROM record WHERE roomId = #{roomId} AND status != -1")
    RecordEntity getByRoomId(@Param("roomId") int roomId);
}