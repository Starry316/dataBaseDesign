package cn.xuzilin.common.dao;

import cn.xuzilin.common.po.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    @Select("SELECT password FROM user WHERE userName = #{userName}")
    String selectPasswordByUserName(@Param("userName") String userName);

    @Select("SELECT count(*) FROM user WHERE userName = #{userName}")
    int getUserNum(@Param("userName") String userName);
}