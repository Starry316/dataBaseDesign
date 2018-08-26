package cn.xuzilin.dao;

import cn.xuzilin.po.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    @Select("select password " +
            "from user " +
            "where userName = #{userName}")
    String selectPasswordByUserName(@Param("userName") String userName);

    @Select("select count(*) " +
            "from user " +
            "where userName = #{userName}")
    int getUserNum(@Param("userName") String userName);

    @Select("select id, userName, phone " +
            "from user " +
            "where userName = #{userName}")
    UserEntity getByUserName(@Param("userName") String userName);
}