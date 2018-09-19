package cn.xuzilin.controller;

import cn.xuzilin.po.UserEntity;
import cn.xuzilin.service.MemberService;
import cn.xuzilin.service.UserService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.utils.SessionUtil;
import cn.xuzilin.vo.MessageVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private MemberService memberService;

    /**
     * 用户登录
     * @param map
     * @return
     */
    @PostMapping("/login")
    public MessageVo login(HttpServletRequest request, @RequestBody Map<String, String> map){
        String userName = map.get("userName");
        String password = map.get("password");
        boolean res = userService.login(userName,password,request);
        if (res){
            return ResponesUtil.success("success");
        }
        return ResponesUtil.systemError("用户名或密码错误!");
    }

    /**
     * 用户注册
     * @param map
     * @return
     */
    @PostMapping("/signUp")
    public MessageVo signUp(HttpServletRequest request,@RequestBody Map<String,String> map){
        String userName = map.get("userName");
        String password = map.get("password");
        String phone = map.get("phone");
        boolean res = userService.signUp(userName,password,phone,request);
        if (res){
            return ResponesUtil.success("success");
        }
        return ResponesUtil.systemError("注册失败！");
    }
    @PostMapping("/bindMemberCard")
    public MessageVo bindMemberCard(HttpServletRequest request,@RequestBody Map<String,String> map){
        String memberCardId= map.get("memberCardId");
        String password = map.get("memberCardPass");
        UserEntity user = SessionUtil.get(request,"user", UserEntity.class);
        if (user==null)
            return ResponesUtil.systemError("用户未登录");
        boolean res = memberService.validate(Integer.parseInt(memberCardId),password);
        if (res){
            res = userService.bindMemberCard(Integer.parseInt(memberCardId),user.getId());
            if (res)
                return ResponesUtil.success("success");
            return ResponesUtil.systemError("该卡已经被绑定!");
        }
        return ResponesUtil.systemError("会员卡和密码不匹配！");
    }
    @GetMapping("/userInfo")
    public MessageVo signUp(HttpServletRequest request){

        UserEntity user = SessionUtil.get(request,"user", UserEntity.class);
        if (user==null)
            return ResponesUtil.systemError("用户未登录");
        JSONObject respData = new JSONObject();
        respData.put("infoName",user.getUserName());
        respData.put("infoPhone",user.getPhone());
        Integer memberCardId = userService.getMemberCardId(user.getId());
        if (memberCardId==null)
            respData.put("infoMemberCardId","尚未绑定会员卡");
        else respData.put("infoMemberCardId",memberCardId);
        return ResponesUtil.success("success",respData);
    }
}
