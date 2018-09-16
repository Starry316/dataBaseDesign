package cn.xuzilin.dao;

import cn.xuzilin.po.RecordEntity;
import cn.xuzilin.vo.DelayInfoVo;
import cn.xuzilin.vo.RecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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

    @Select("select a.id,a.roomId,a.checkInTime,a.checkOutTime,a.payment,b.roomType " +
            "from record a join room b on (a.roomId = b.roomId) " +
            "where a.status = -1 and (#{roomId} is null or a.roomId = #{roomId}) " +
            "and (#{checkInTime} is null or a.checkInTime >= #{checkInTime}) " +
            "and (#{checkOutTime} is null or a.checkOutTime <= #{checkOutTime}) " +
            "and (#{customerName} is null or exists ( select * from customer c where c.name = #{customerName} and c.recordId = a.id))")
    List<RecordVo> getCompleteRecord(@Param("roomId") String roomId, @Param("checkInTime")String checkInTime , @Param("checkOutTime") String checkOutTime, @Param("customerName")  String customerName);

    @Select("select count(*) " +
            "from record a join room b on (a.roomId = b.roomId) " +
            "where a.status = -1 and (#{roomId} is null or a.roomId = #{roomId}) " +
            "and (#{checkInTime} is null or a.checkInTime >= #{checkInTime}) " +
            "and (#{checkOutTime} is null or a.checkOutTime <= #{checkOutTime}) " +
            "and (#{customerName} is null or exists ( select * from customer c where c.name = #{customerName} and c.recordId = a.id))")
    int getRecordMaxPage(@Param("roomId") String roomId, @Param("checkInTime")String checkInTime , @Param("checkOutTime") String checkOutTime, @Param("customerName")  String customerName);

    @Select("select roomId,roomType,checkInTime,checkOutTime " +
            "from room natural join record " +
            "where status != -1 and roomId = #{roomId}")
    DelayInfoVo getDelayInfoByRoomId(@Param("roomId") int roomId);
}