package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.ReserveEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ReserveEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReserveEntity record);

    int insertSelective(ReserveEntity record);

    ReserveEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReserveEntity record);

    int updateByPrimaryKey(ReserveEntity record);
    @Update("UPDATE reserve SET status = #{status} WHERE roomId = #{roomId}")
    int updateStatusByRoomId(@Param("status") byte status, @Param("roomId") int roomId);

    @Select("SELECT * FROM reserve WHERE status = #{status}")
    List<ReserveEntity> getAllByStatus(@Param("status") byte status);

    @Select("SELECT * FROM reserve WHERE roomId = #{roomId} AND status = 1")
    ReserveEntity getByRoomId(@Param("roomId") int roomId);

    @Select("SELECT count(*) FROM reserve WHERE  status = 1")
    int getCount();
}