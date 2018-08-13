package cn.xuzilin.common.controller;

import cn.xuzilin.common.service.UserService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param map
     * @return
     */
    @PostMapping("/login")
    public MessageVo login(@RequestBody Map<String,String> map){
        String userName = map.get("userName");
        String password = map.get("password");
        boolean res = userService.login(userName,password);
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
    public MessageVo signUp(@RequestBody Map<String,String> map){
        String userName = map.get("userName");
        String password = map.get("password");
        String phone = map.get("phone");
        boolean res = userService.signUp(userName,password,phone);
        if (res){
            return ResponesUtil.success("success");
        }
        return ResponesUtil.systemError("注册失败！");
    }
}
