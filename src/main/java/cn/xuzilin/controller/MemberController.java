package cn.xuzilin.controller;

import cn.xuzilin.po.MemberCardEntity;
import cn.xuzilin.service.MemberService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@RestController
public class MemberController {
    @Resource
    private MemberService memberService;


    @PostMapping("/createMemberCard")
    public MessageVo createMemberCard(@RequestBody Map<String,String>map){
        String name = map.get("name");
        String idcardNo = map.get("idcardNo");
        String phoneNum = map.get("phoneNum");
        String pass = map.get("password");
        BigDecimal balance = new BigDecimal(map.get("balance"));
        int id = memberService.createNewCard(name,idcardNo,phoneNum,pass,balance);
        return ResponesUtil.success("success",id);
    }

    @PostMapping("/recharge")
    public MessageVo recharge(@RequestBody Map<String,String> map){
        int id = Integer.parseInt(map.get("memberCardId"));
        BigDecimal money = new BigDecimal(map.get("money"));
        BigDecimal balance = memberService.recharge(id,money);
        return ResponesUtil.success("success",balance);

    }

    @PostMapping("/validate")
    public MessageVo validate(@RequestBody Map<String,String> map){
        String id = map.get("memberCardId");
        String pass = map.get("password");
        boolean result = memberService.validate(Integer.parseInt(id),pass);
        return ResponesUtil.success("success",result);
    }

}
