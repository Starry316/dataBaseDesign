package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.MemberCardEntityMapper;
import cn.xuzilin.po.MemberCardEntity;
import cn.xuzilin.utils.PasswordUtil;
import cn.xuzilin.utils.SwitchUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MemberService {
    @Resource
    private MemberCardEntityMapper memberCardMapper;

    public int createNewCard(String name ,String idcardNo,String phone,String password,BigDecimal balance){
        MemberCardEntity memberCard = memberCardBuilder(name,idcardNo,phone,password,balance);
        memberCardMapper.insertSelective(memberCard);
        return memberCard.getId();
    }

    public int update (MemberCardEntity memberCardEntity){
        return memberCardMapper.updateByPrimaryKeySelective(memberCardEntity);
    }

    /**
     * 充值，返回充值后余额
     * @param id
     * @param money
     * @return 余额
     */
    public BigDecimal recharge(int id ,BigDecimal money){
        memberCardMapper.addMoneyById(money,id);
        return memberCardMapper.selectBalanceById(id);
    }

    public boolean validate (int id , String password){
        String correctHash = memberCardMapper.selectPassById(id);
        return PasswordUtil.validatePassword(password,correctHash);
    }

    public void updateConsumption(int id ,BigDecimal consumption){
        MemberCardEntity memberCard = memberCardMapper.selectByPrimaryKey(id);
        memberCard.setConsumption(memberCard.getConsumption().add(consumption));
        memberCard.setType(judgeLevel(memberCard.getConsumption()));
        memberCardMapper.updateByPrimaryKeySelective(memberCard);
    }

    public JSONArray getData(int page,String id , String name ,String phone ,String level){
        boolean idFlag = false;
        boolean nameFlag = false;
        boolean phoneFlag = false;
        boolean levelFlag = false;
        int memberId = 0 ;
        byte type = 0 ;
        if (!id.equals("")){
            memberId = Integer.parseInt(id);
            idFlag = true;
        }
        if (!name.equals("")){
            nameFlag = true;
        }
        if (!phone.equals("")){
            phoneFlag = true;
        }
        if (!level.equals("")&&!level.equals("0")){
            type = Byte.parseByte(level);
            levelFlag = true;
        }
        PageHelper.startPage(page,15);
        List<MemberCardEntity> list = memberCardMapper.getData(memberId,idFlag,type,levelFlag,name,nameFlag,phone,phoneFlag);
        for(MemberCardEntity i : list){
            i.setTypeName(SwitchUtil.switchMemberCardTypeName(i.getType()));
        }
        JSONArray data = JSON.parseArray(JSON.toJSONString(list));
        return data;
    }

    public MemberCardEntity getMemberCardById(int id){
        return memberCardMapper.selectByPrimaryKey(id);
    }

    public int getMaxPages(int page,String id , String name ,String phone ,String level){
        boolean idFlag = false;
        boolean nameFlag = false;
        boolean phoneFlag = false;
        boolean levelFlag = false;
        int memberId = 0 ;
        byte type = 0 ;
        if (!id.equals("")){
            memberId = Integer.parseInt(id);
            idFlag = true;
        }
        if (!name.equals("")){
            nameFlag = true;
        }
        if (!phone.equals("")){
            phoneFlag = true;
        }
        if (!level.equals("")&&!level.equals("0")){
            type = Byte.parseByte(level);
            levelFlag = true;
        }
        int maxPage = memberCardMapper.getCount(memberId,idFlag,type,levelFlag,name,nameFlag,phone,phoneFlag);
        return (maxPage+14)/15;
    }

    public int deleteCard(int id){
        return memberCardMapper.deleteByPrimaryKey(id);
    }
    public byte judgeLevel(BigDecimal totalConsumption){
//        if (totalConsumption.compareTo(ConstPool.DIAOCONSUMPTION) > 0)return ConstPool.DIAO;
        if (totalConsumption.compareTo(ConstPool.DIAMONDCONSUMPTION) > 0)return ConstPool.DIAMOND;
        if (totalConsumption.compareTo(ConstPool.GOLDCONSUMPTION) > 0)return ConstPool.GOLD;
        return ConstPool.SILVER;
    }
    private MemberCardEntity memberCardBuilder(String name,String idcardNo ,String phone,String password,BigDecimal balance){
        MemberCardEntity memberCardEntity = new MemberCardEntity();
        memberCardEntity.setName(name);
        memberCardEntity.setIdcardNo(idcardNo);
        memberCardEntity.setPhoneNum(phone);
        memberCardEntity.setType(ConstPool.SILVER);
        memberCardEntity.setConsumption(BigDecimal.ZERO);
        memberCardEntity.setBalance(balance);
        memberCardEntity.setPassword(PasswordUtil.createHash(password));
        return memberCardEntity;
    }

}
