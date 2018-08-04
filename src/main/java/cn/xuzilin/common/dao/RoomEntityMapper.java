package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.RoomEntity;
import cn.xuzilin.common.vo.RoomRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoomEntityMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(RoomEntity record);

    int insertSelective(RoomEntity record);

    RoomEntity selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(RoomEntity record);

    int updateByPrimaryKey(RoomEntity record);

    @Select("SELECT * FROM room WHERE checkIn = 0")
    List<RoomEntity> getEmptyRoomList();

    @Select("SELECT count(*) FROM room WHERE checkIn = 0")
    int getEmptyRoomCount();

    @Select("SELECT * FROM room")
    List<RoomEntity> getRoomList();

    @Select("SELECT count(*) FROM room")
    int  getCount();


    @Select("SELECT * \n" +
            "FROM room a left join record b on (a.roomId = b.roomId  and b.status != -1) \n" +
            "where (a.roomId = #{roomId} or #{roomIdFlag} = 0) and (a.checkIn = #{checkIn} or #{checkInFlag} = 0)\n" +
            "and (b.checkInTime = #{checkInTime} or #{checkInTimeFlag} = 0) and (b.checkOutTime = #{checkOutTime} or #{checkOutTimeFlag} = 0)\n" +
            "and (b.customerName = #{customerName} or #{customerNameFlag} = 0) and (b.idcardNo = #{idcardNo} or #{idcardNoFlag} = 0) " +
            "ORDER BY a.roomId")
    List<RoomRecordVo> getData(@Param("roomId") int roomId,@Param("roomIdFlag") int roomIdFlag,
                                  @Param("checkIn") byte checkIn,@Param("checkInFlag") int checkInFlag,
                                  @Param("checkInTime") String checkInTime ,@Param("checkInTimeFlag") int checkInTimeFlag,
                                  @Param("checkOutTime") String checkOutTime ,@Param("checkOutTimeFlag") int checkOutTimeFlag,
                                  @Param("customerName") String customerName ,@Param("customerNameFlag") int customerNameFlag,
                                  @Param("idcardNo") String idcardNo ,@Param("idcardNoFlag") int idcardNoFlag);

    @Select("SELECT count(*) \n" +
            "FROM room a left join record b on (a.roomId = b.roomId and b.status != -1) \n" +
            "where (a.roomId = #{roomId} or #{roomIdFlag} = 0) and (a.checkIn = #{checkIn} or #{checkInFlag} = 0)\n" +
            "and (b.checkInTime = #{checkInTime} or #{checkInTimeFlag} = 0) and (b.checkOutTime = #{checkOutTime} or #{checkOutTimeFlag} = 0)\n" +
            "and (b.customerName = #{customerName} or #{customerNameFlag} = 0) and (b.idcardNo = #{idcardNo} or #{idcardNoFlag} = 0)" +
            "ORDER BY a.roomId")
    int getDataCount(@Param("roomId") int roomId,@Param("roomIdFlag") int roomIdFlag,
                                  @Param("checkIn") byte checkIn,@Param("checkInFlag") int checkInFlag,
                                  @Param("checkInTime") String checkInTime ,@Param("checkInTimeFlag") int checkInTimeFlag,
                                  @Param("checkOutTime") String checkOutTime ,@Param("checkOutTimeFlag") int checkOutTimeFlag,
                                  @Param("customerName") String customerName ,@Param("customerNameFlag") int customerNameFlag,
                                  @Param("idcardNo") String idcardNo ,@Param("idcardNoFlag") int idcardNoFlag);
}