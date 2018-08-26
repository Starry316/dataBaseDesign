package cn.xuzilin.dao;

import cn.xuzilin.po.RecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecordEntity record);

    int insertSelective(RecordEntity record);

    RecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecordEntity record);

    int updateByPrimaryKey(RecordEntity record);

    @Select("select * " +
            "from record " +
            "where roomId = #{roomId} AND status != -1")
    RecordEntity getByRoomId(@Param("roomId") int roomId);

    @Select("select * " +
            "from record " +
            "where status = 1")
    List<RecordEntity> getAll();
}