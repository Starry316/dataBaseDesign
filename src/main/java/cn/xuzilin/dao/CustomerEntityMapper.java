package cn.xuzilin.dao;

import cn.xuzilin.po.CustomerEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerEntity record);

    int insertSelective(CustomerEntity record);

    CustomerEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerEntity record);

    int updateByPrimaryKey(CustomerEntity record);

    @Select("select * " +
            "from customer " +
            "where recordId = #{recordId}")
    List<CustomerEntity> getCustomerByRecordId(@Param("recordId") int recordId);
}