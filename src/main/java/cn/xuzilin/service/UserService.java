package cn.xuzilin.service;

import cn.xuzilin.dao.UserEntityMapper;
import cn.xuzilin.po.UserEntity;
import cn.xuzilin.utils.PasswordUtil;
import cn.xuzilin.utils.SessionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Resource
    private UserEntityMapper userMapper;

    /**
     * 验证密码
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName,String password,HttpServletRequest request){
        String correctPass = userMapper.selectPasswordByUserName(userName);
        if (correctPass == null) return false;
        if (PasswordUtil.validatePassword(password,correctPass)){
            UserEntity user = userMapper.getByUserName(userName);
            SessionUtil.save(request,"user",user);
            return true;
        }
        return false;
    }

    /**
     * 注册新用户
     * @param userName
     * @param password
     * @param phone
     * @return
     */
    public boolean signUp(String userName , String password, String phone, HttpServletRequest request){
        if (!judgeUnique(userName))return false;
        //对密码加密
        String hashPass = PasswordUtil.createHash(password);
        UserEntity user = new UserEntity();
        user.setPassword(hashPass);
        user.setUserName(userName);
        user.setPhone(phone);
        try {
            userMapper.insertSelective(user);
            SessionUtil.save(request,"user",user);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断用户名是否重复
     * @param userName
     * @return
     */
    public boolean judgeUnique(String userName){
        int res = userMapper.getUserNum(userName);
        return res == 0 ? true:false;
    }
}
