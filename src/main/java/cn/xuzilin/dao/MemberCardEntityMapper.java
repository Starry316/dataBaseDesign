package cn.xuzilin.dao;

import cn.xuzilin.po.MemberCardEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface MemberCardEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCardEntity record);

    int insertSelective(MemberCardEntity record);

    MemberCardEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberCardEntity record);

    int updateByPrimaryKey(MemberCardEntity record);

    @Select("select password" +
            "form member_card" +
            "where id = #{id}")
    String selectPassById(@Param("id") int id);

    @Update("update member_card" +
            "set balance = balance + #{money}" +
            "where id = #{id}")
    int addMoneyById(@Param("money")BigDecimal money,@Param("id")int id );

    @Select("select balance" +
            "form member_card" +
            "where id = #{id}")
    BigDecimal selectBalanceById(@Param("id") int id);
}