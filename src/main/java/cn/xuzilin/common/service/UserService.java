package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.UserEntityMapper;
import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.common.utils.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public boolean login(String userName,String password){
        String correctPass = userMapper.selectPasswordByUserName(userName);
        if (correctPass == null) return false;
        if (PasswordUtil.validatePassword(password,correctPass))return true;
        return false;
    }

    /**
     * 注册新用户
     * @param userName
     * @param password
     * @param phone
     * @return
     */
    public boolean signUp(String userName ,String password,String phone){
        if (!judgeUnique(userName))return false;
        //对密码加密
        String hashPass = PasswordUtil.createHash(password);
        UserEntity user = new UserEntity();
        user.setPassword(hashPass);
        user.setUserName(userName);
        user.setPhone(phone);
        try {
            userMapper.insertSelective(user);
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
