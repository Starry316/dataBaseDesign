package cn.xuzilin.dao;

import cn.xuzilin.po.ManagerEntity;
import cn.xuzilin.po.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ManagerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerEntity record);

    int insertSelective(ManagerEntity record);

    ManagerEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerEntity record);

    int updateByPrimaryKey(ManagerEntity record);

    @Select("SELECT password FROM manager WHERE managerName = #{managerName}")
    String selectPasswordByManagerName(@Param("managerName") String managerName);

    @Select("SELECT count(*) FROM manager WHERE managerName = #{managerName}")
    int getManagerNum(@Param("managerName") String managerName);

    @Select("SELECT id, managerName FROM manager WHERE managerName = #{managerName}")
    ManagerEntity getByManagerName(@Param("managerName") String managerName);
}