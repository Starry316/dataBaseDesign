package cn.xuzilin.dao;

import cn.xuzilin.po.RoomEntity;
import cn.xuzilin.vo.RoomRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface RoomEntityMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(RoomEntity record);

    int insertSelective(RoomEntity record);

    RoomEntity selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(RoomEntity record);

    int updateByPrimaryKey(RoomEntity record);

    @Select("select * " +
            "from room " +
            "where checkIn = 0")
    List<RoomEntity> getEmptyRoomList();

    @Select("select count(*) " +
            "from room " +
            "where checkIn = 0")
    int getEmptyRoomCount();

    @Select("select * " +
            "from room")
    List<RoomEntity> getRoomList();

    @Select("select count(*) " +
            "from room")
    int  getCount();

    @Select("select a.roomId " +
            "from room a left join record b on (a.roomId = b.roomId  and b.status != -1) " +
            "where a.roomType=#{type} " +
            "and  (b.checkOutTime is null or b.checkOutTime < #{checkInTime}) " +
            "and not exists(" +
            "   select * " +
            "   from reserve" +
            "   where roomId = a.roomId and reserveCheckInTime >= #{checkInTime} and reserveCheckOutTime <= #{checkOutTime})")
    Integer[] getRoomIdListByTypeAndCheckInTime(@Param("type") byte type , @Param("checkInTime")Date checkInTime,@Param("checkOutTime")Date checkOutTime);

    @Select("select * \n" +
            "from room a left join record b on (a.roomId = b.roomId  and b.status != -1) \n" +
            "where (  #{roomIdFlag} = true or a.roomId = #{roomId})"  +
            "and (#{checkInFlag}= true or a.checkIn = #{checkIn})\n" +
            "and (#{checkInTimeFlag}= true or b.checkInTime = #{checkInTime}) " +
            "and (#{checkOutTimeFlag}= true or b.checkOutTime = #{checkOutTime})\n" +
            "and (exists(select * from customer c where c.recordId = b.id " +
            "           and  (#{customerNameFlag}= true or c.name = #{customerName}) " +
            "           and (#{idcardNoFlag}= true or c.idcardNo = #{idcardNo}) " +
            "           ) or ( #{idcardNoFlag}= true and #{customerNameFlag}= true))" +
            "order by a.roomId")
    List<RoomRecordVo> getData(@Param("roomId") int roomId,@Param("roomIdFlag") boolean roomIdFlag,
                               @Param("checkIn") byte checkIn,@Param("checkInFlag") boolean checkInFlag,
                               @Param("checkInTime") String checkInTime ,@Param("checkInTimeFlag") boolean checkInTimeFlag,
                               @Param("checkOutTime") String checkOutTime ,@Param("checkOutTimeFlag") boolean checkOutTimeFlag,
                               @Param("customerName") String customerName ,@Param("customerNameFlag") boolean customerNameFlag,
                               @Param("idcardNo") String idcardNo ,@Param("idcardNoFlag") boolean idcardNoFlag);

    @Select("select count(*) \n" +
            "from room a left join record b on (a.roomId = b.roomId  and b.status != -1) \n" +
            "where (  #{roomIdFlag} = true or a.roomId = #{roomId})"  +
            "and (#{checkInFlag}= true or a.checkIn = #{checkIn})\n" +
            "and (#{checkInTimeFlag}= true or b.checkInTime = #{checkInTime}) " +
            "and (#{checkOutTimeFlag}= true or b.checkOutTime = #{checkOutTime})\n" +
            "and (exists(select * from customer c where c.recordId = b.id " +
            "           and  (#{customerNameFlag}= true or c.name = #{customerName}) " +
            "           and (#{idcardNoFlag}= true or c.idcardNo = #{idcardNo}) " +
            "           ) or ( #{idcardNoFlag}= true and #{customerNameFlag}= true))" +
            "order by a.roomId")
    int getDataCount(@Param("roomId") int roomId,@Param("roomIdFlag") boolean roomIdFlag,
                     @Param("checkIn") byte checkIn,@Param("checkInFlag") boolean checkInFlag,
                     @Param("checkInTime") String checkInTime ,@Param("checkInTimeFlag") boolean checkInTimeFlag,
                     @Param("checkOutTime") String checkOutTime ,@Param("checkOutTimeFlag") boolean checkOutTimeFlag,
                     @Param("customerName") String customerName ,@Param("customerNameFlag") boolean customerNameFlag,
                     @Param("idcardNo") String idcardNo ,@Param("idcardNoFlag") boolean idcardNoFlag);
}