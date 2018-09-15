package cn.xuzilin.controller;

import cn.xuzilin.po.MemberCardEntity;
import cn.xuzilin.service.MemberService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
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
        String phoneNum = map.get("phone");
        String pass = map.get("password");
        BigDecimal balance = new BigDecimal(map.get("firstMoney"));
        int id = memberService.createNewCard(name,idcardNo,phoneNum,pass,balance);
        return ResponesUtil.success("success",id);
    }

    @PostMapping("/recharge")
    public MessageVo recharge(@RequestBody Map<String,String> map){
        int id = Integer.parseInt(map.get("id"));
        BigDecimal money = new BigDecimal(map.get("money"));
        BigDecimal balance = memberService.recharge(id,money);
        return ResponesUtil.success("success",balance);

    }

    @PostMapping("/deleteCard")
    public MessageVo deleteCard(@RequestBody Map<String,String> map){
        int id = Integer.parseInt(map.get("id"));
        try {
            memberService.deleteCard(id);
        }catch (Exception e){
            return ResponesUtil.systemError(e.getMessage());
        }

        return ResponesUtil.success("success");
    }

    @PostMapping("/memberDataList")
    public MessageVo memberDataList(@RequestBody Map<String,String>map){
        String page = map.get("page");
        String level = map.get("level");
        String memberCardId = map.get("memberCardId");
        String memberName = map.get("memberName");
        String memberPhone = map.get("memberPhone");
        JSONArray respData = memberService.getData(Integer.parseInt(page),memberCardId,memberName,memberPhone,level);
        return ResponesUtil.success("success",respData);
    }
    @PostMapping("/getMemberMaxPage")
    public MessageVo getMemberMaxPage(@RequestBody Map<String,String>map){
        String page = map.get("page");
        String level = map.get("level");
        String memberCardId = map.get("memberCardId");
        String memberName = map.get("memberName");
        String memberPhone = map.get("memberPhone");
        int respData = memberService.getMaxPages(Integer.parseInt(page),memberCardId,memberName,memberPhone,level);
        return ResponesUtil.success("success",respData);
    }

    @PostMapping("/validate")
    public MessageVo validate(@RequestBody Map<String,String> map){
        String id = map.get("memberCardId");
        String pass = map.get("password");
        boolean result = memberService.validate(Integer.parseInt(id),pass);
        return ResponesUtil.success("success",result);
    }

}
