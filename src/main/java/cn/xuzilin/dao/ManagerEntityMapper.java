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

    @Select("select password " +
            "from manager " +
            "where managerName = #{managerName}")
    String selectPasswordByManagerName(@Param("managerName") String managerName);

    @Select("select count(*) " +
            "from manager " +
            "where managerName = #{managerName}")
    int getManagerNum(@Param("managerName") String managerName);

    @Select("select id, managerName " +
            "from manager " +
            "where managerName = #{managerName}")
    ManagerEntity getByManagerName(@Param("managerName") String managerName);
}