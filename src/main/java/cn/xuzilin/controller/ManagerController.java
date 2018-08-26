package cn.xuzilin.controller;

import cn.xuzilin.service.ManagerService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.vo.MessageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ManagerController {
    @Resource
    private ManagerService managerService;

    @PostMapping("/loginManager")
    public MessageVo loginManager(HttpServletRequest request, @RequestBody Map<String,String> map){
        String managerName = map.get("managerName");
        String pass = map.get("password");
        boolean res = managerService.loginManager(request,managerName,pass);
        if (res){
            return ResponesUtil.success("success");
        }
        return ResponesUtil.systemError("用户名或密码错误!");
    }
}
