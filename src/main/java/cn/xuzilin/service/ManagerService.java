package cn.xuzilin.service;

import cn.xuzilin.dao.ManagerEntityMapper;
import cn.xuzilin.po.ManagerEntity;
import cn.xuzilin.utils.PasswordUtil;
import cn.xuzilin.utils.SessionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class ManagerService {
    @Resource
    private ManagerEntityMapper managerMapper;

    /**
     * 管理员登录
     * @param request
     * @param managerName
     * @param password
     * @return
     */
    public boolean loginManager(HttpServletRequest request, String managerName,String password){
        String correctPassword = managerMapper.selectPasswordByManagerName(managerName);
        if (correctPassword == null) return false;
        if (PasswordUtil.validatePassword(password,correctPassword)){
            ManagerEntity manager = managerMapper.getByManagerName(managerName);
            SessionUtil.save(request,"managerToken",manager);
            return true;
        }
        return false;
    }
}
