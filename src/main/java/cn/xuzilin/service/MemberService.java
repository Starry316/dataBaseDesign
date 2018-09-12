package cn.xuzilin.service;

import cn.xuzilin.consts.ConstPool;
import cn.xuzilin.dao.MemberCardEntityMapper;
import cn.xuzilin.po.MemberCardEntity;
import cn.xuzilin.utils.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class MemberService {
    @Resource
    private MemberCardEntityMapper memberCardMapper;

    public int createNewCard(String name ,String idcardNo,String phone,String password,BigDecimal balance){
        MemberCardEntity memberCard = memberCardBuilder(name,idcardNo,phone,password,balance);
        memberCardMapper.insertSelective(memberCard);
        return memberCard.getId();
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
    private byte judgeLevel(BigDecimal totalConsumption){
        if (totalConsumption.compareTo(ConstPool.DIAOCONSUMPTION) > 0)return ConstPool.DIAO;
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
