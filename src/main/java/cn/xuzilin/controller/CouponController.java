package cn.xuzilin.controller;

import cn.xuzilin.po.UserEntity;
import cn.xuzilin.service.CouponService;
import cn.xuzilin.utils.ResponesUtil;
import cn.xuzilin.utils.SessionUtil;
import cn.xuzilin.vo.MessageVo;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CouponController {
    @Resource
    private CouponService couponService;

    @GetMapping("/getCouponList")
    public MessageVo getCouponList(HttpServletRequest request){
        UserEntity user = SessionUtil.get(request,"user",UserEntity.class);
        if (user == null)return ResponesUtil.systemError("登录信息失效！");
        JSONArray respData = couponService.getCouponListByUserId(user.getId());
        return ResponesUtil.success("success",respData);
    }

}
