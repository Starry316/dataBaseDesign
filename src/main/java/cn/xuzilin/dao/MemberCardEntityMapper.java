package cn.xuzilin.dao;

import cn.xuzilin.po.MemberCardEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

public interface MemberCardEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCardEntity record);

    int insertSelective(MemberCardEntity record);

    MemberCardEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberCardEntity record);

    int updateByPrimaryKey(MemberCardEntity record);

    @Select("select password " +
            "from member_card " +
            "where id = #{id}")
    String selectPassById(@Param("id") int id);

    @Select("select * " +
            "from member_card " +
            "where (#{idFlag} = false or id = #{id}) " +
            "and (#{levelFlag} = false or type = #{level}) " +
            "and (#{nameFlag} = false or name = #{name}) " +
            "and (#{phoneFlag} = false or phoneNum = #{phone}) ")
    List<MemberCardEntity> getData(@Param("id") int id, @Param("idFlag") boolean idFlag,
                 @Param("level") byte level, @Param("levelFlag") boolean levelFlag,
                 @Param("name") String name, @Param("nameFlag") boolean nameFlag,
                 @Param("phone") String phone, @Param("phoneFlag") boolean phoneFlag);
    @Select("select count(*) " +
            "from member_card " +
            "where (#{idFlag} = false or id = #{id}) " +
            "and (#{levelFlag} = false or type = #{level}) " +
            "and (#{nameFlag} = false or name = #{name}) " +
            "and (#{phoneFlag} = false or phoneNum = #{phone}) ")
    int  getCount(@Param("id") int id, @Param("idFlag") boolean idFlag,
                                   @Param("level") byte level, @Param("levelFlag") boolean levelFlag,
                                   @Param("name") String name, @Param("nameFlag") boolean nameFlag,
                                   @Param("phone") String phone, @Param("phoneFlag") boolean phoneFlag);



    @Update("update member_card set balance = balance + #{money} where id = #{id}")
    int addMoneyById(@Param("money")BigDecimal money,@Param("id")int id );

    @Select("select balance " +
            "from member_card " +
            "where id = #{id}")
    BigDecimal selectBalanceById(@Param("id") int id);
}