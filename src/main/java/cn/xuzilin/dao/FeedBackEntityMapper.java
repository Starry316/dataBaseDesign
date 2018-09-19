package cn.xuzilin.dao;

import cn.xuzilin.po.FeedBackEntity;
import cn.xuzilin.vo.FeedbackVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface FeedBackEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedBackEntity record);

    int insertSelective(FeedBackEntity record);

    FeedBackEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedBackEntity record);

    int updateByPrimaryKeyWithBLOBs(FeedBackEntity record);

    int updateByPrimaryKey(FeedBackEntity record);

    @Update("update feedback set status = #{status} where id = #{id}")
    int updateStatusById(@Param("status") byte status,@Param("id") int id);

    @Update("update feedback set status = #{status} , dealTime = #{dealTime} ,managerId = #{managerId} where id = #{id}")
    int updateStatusDealTimeById(@Param("status") byte status, @Param("id") int id, @Param("managerId") int managerId,@Param("dealTime")Date dealTime);

    @Update("update feedback set managerId = #{managerId} where id = #{id}")
    int updateManagerById(@Param("managerId") int managerId,@Param("id") int id);

    @Select("select content from feedback where id = #{id}")
    String getContentById(@Param("id") int id);

    @Select("select * from feedback where userId = #{userId}")
    List<FeedBackEntity> getByUserId(@Param("userId") int userId);

    @Select("select f.id,f.status,f.submitTime,f.dealTime,m.managerName,u.userName " +
            "from (feedback f left join manager m on (f.userId = m.id)) , user u " +
            "where (userId = u.id) and (#{status} = 3 or status = #{status})")
    List<FeedbackVo> getData(@Param("status") byte status);

    @Select("select count(*) \n" +
            "from (feedback f left join manager m on (f.userId = m.id)) , user u " +
            "where (userId = u.id) and (#{status} = 3 or status = #{status})")
    int getCount(@Param("status") byte status);
}