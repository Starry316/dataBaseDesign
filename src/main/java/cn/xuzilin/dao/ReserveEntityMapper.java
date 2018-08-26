package cn.xuzilin.dao;

import cn.xuzilin.po.ReserveEntity;
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
    @Update("update reserve set status = #{status} " +
            "where roomId = #{roomId}")
    int updateStatusByRoomId(@Param("status") byte status, @Param("roomId") int roomId);

    @Update("update reserve set status = #{status} " +
            "where id = #{id}")
    int updateStatusById(@Param("status") byte status, @Param("id") int id);

    @Select("select * " +
            "from reserve " +
            "where status = #{status}")
    List<ReserveEntity> getAllByStatus(@Param("status") byte status);

    @Select("select * " +
            "from reserve " +
            "where roomId = #{roomId} and status = 1")
    List<ReserveEntity> getByRoomId(@Param("roomId") int roomId);


    @Select("select count(*) " +
            "from reserve " +
            "where  status = 1")
    int getCount();

    @Select("select * " +
            "from reserve " +
            "where userId = #{userId} and status = 1")
    List<ReserveEntity> getActiveByUserId(@Param("userId") int userId);

}